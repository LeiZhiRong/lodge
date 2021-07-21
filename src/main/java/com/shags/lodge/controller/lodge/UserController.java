package com.shags.lodge.controller.lodge;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.shags.lodge.Vo.UserInfoVo;
import com.shags.lodge.auth.AuthClass;
import com.shags.lodge.auth.AuthMethod;
import com.shags.lodge.dto.HeaderColumns;
import com.shags.lodge.dto.TreeJson;
import com.shags.lodge.dto.User;
import com.shags.lodge.excel.ExcelUtils;
import com.shags.lodge.service.primary.*;
import com.shags.lodge.util.*;
import com.shags.lodge.primary.entity.StationInfo;
import com.shags.lodge.primary.entity.TableHeader;
import com.shags.lodge.primary.entity.UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 用户信息视图层接口
 *
 * @author 雷智荣
 */
@RestController
@RequestMapping("/user/")
@Scope("prototype")
@AuthClass("login")
public class UserController {

    private IUserInfoService userInfoService;
    private IDeptInfoService deptInfoService;
    private ITableHeaderService tableHeaderService;
    private IUserRoleService userRoleService;
    private IStationInfoService stationInfoService;
    private IManagePointService managePointService;

    @Autowired
    public void setUserInfoService(IUserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @Autowired
    public void setDeptInfoService(IDeptInfoService deptInfoService) {
        this.deptInfoService = deptInfoService;
    }

    @Autowired
    public void setTableHeaderService(ITableHeaderService tableHeaderService) {
        this.tableHeaderService = tableHeaderService;
    }

    @Autowired
    public void setUserRoleService(IUserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @Autowired
    public void setStationInfoService(IStationInfoService stationInfoService) {
        this.stationInfoService = stationInfoService;
    }

    @Autowired
    public void setManagePointService(IManagePointService managePointService) {
        this.managePointService = managePointService;
    }

    /**
     * 首页
     *
     * @param model
     * @return
     * @throws JsonProcessingException
     */
    @AuthMethod(role = "ROLE_USER")
    @RequestMapping("index")
    public ModelAndView index(Model model, HttpSession session) throws JsonProcessingException {
        User user = (User) session.getAttribute("user");
        List<HeaderColumns> columns = tableHeaderService.listHeaderColumns(user.getId(), "userGrid", "com.shags.lodge.primary.entity.UserInfo");
        model.addAttribute("columns", columns);
        return new ModelAndView("user/index");
    }


    /**
     * 获取用户信息
     *
     * @param order
     * @param sort
     * @param page
     * @param rows
     * @param keyword
     * @return
     */
    @AuthMethod(role = "ROLE_USER")
    @RequestMapping("getUserInfo")
    public Pager<UserInfo> getUserInfo(String order, String sort, int page, int rows, String keyword, HttpSession session) {
        SystemContext.setPageSize(rows);
        SystemContext.setPageNumber(page);
        SystemContext.setOrder(order);
        SystemContext.setSort(sort);
        User user = (User) session.getAttribute("user");
        return userInfoService.listUserInfo(keyword, user.isAdmin());
    }

    /**
     * 用户添加页
     *
     * @param id
     * @param model
     * @return
     * @throws JsonProcessingException
     */
    @AuthMethod(role = "ROLE_USER")
    @GetMapping("dialog")
    public ModelAndView dialog(String id, Model model, HttpSession session) throws JsonProcessingException {
        User user = (User) session.getAttribute("user");
        UserInfo userInfo = new UserInfo();
        if (id != null && !id.isEmpty()) {
            userInfo = userInfoService.queryUserInfoById(id);
            userInfo.setLoginPassword(null);
        } else {
            userInfo.setStatus(1);
        }
        //岗位
        List<SelectJson> stations = stationInfoService.listStationInfoToSelectJson(null, "T");
        stations.add(0, new SelectJson("", "请选择...", ""));
        //管理处
        List<SelectJson> managePointList = managePointService.listManagePointToSelectJson(user.getBookSet());
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("stations", stations);
        model.addAttribute("managePointList", managePointList);
        return new ModelAndView("user/dialog");
    }

    /**
     * 部门列表dialog跳转
     *
     * @param keyword 已选择部门编号
     * @param model
     * @return
     */
    @AuthMethod(role = "ROLE_USER")
    @GetMapping("getDeptInfo")
    public ModelAndView deptDialog(String keyword, Model model) {
        model.addAttribute("keyword", keyword);
        return new ModelAndView("user/getDeptInfo");
    }

    /**
     * 获取部门列表
     *
     * @param keyword 已选择部门编号
     * @return
     */
    @AuthMethod(role = "ROLE_USER")
    @RequestMapping(value = "getDeptList")
    public List<TreeJson> getDeptList(String keyword) {
        return deptInfoService.getDeptInfo2TreeJson(keyword);
    }

    /**
     * 保存用户信息
     *
     * @param userInfo
     * @param br
     * @return
     */
    @AuthMethod(role = "ROLE_USER")
    @PostMapping("saveUserInfo")
    public Message saveUserInfo(@Validated UserInfo userInfo, BindingResult br) {
        if (br.hasErrors()) {
            return new Message(0, Objects.requireNonNull(br.getFieldError()).getDefaultMessage());
        }
        try {
            String id = userInfo.getId();
            String pwd = userInfo.getLoginPassword();
            if (id == null || userInfo.getId().isEmpty()) {
                //添加
                userInfo.setId(null);
                userInfo.setRegistTime(BeanUtil.strToTimestampTime(CmsUtils.getNowDate()));
                if (pwd != null && !pwd.isEmpty()) {
                    userInfo.setLoginPassword(new MyJasyptStringEncryptor().encrypt(pwd));
                } else {
                    userInfo.setLoginPassword(new MyJasyptStringEncryptor().encrypt("123456"));
                }
                return userInfoService.saveUserINfo(userInfo);
            } else {
                UserInfo mast = userInfoService.queryUserInfoById(id);
                if (mast != null) {
                    mast.setStatus(userInfo.getStatus());
                    mast.setLoginAccount(userInfo.getLoginAccount());
                    mast.setSettID(userInfo.getSettID());
                    mast.setDeptID(userInfo.getDeptID());
                    mast.setUserName(userInfo.getUserName());
                    mast.setStation(userInfo.getStation());
                    if (pwd != null && !pwd.isEmpty()) {
                        mast.setLoginPassword(new MyJasyptStringEncryptor().encrypt(pwd));
                    }
                    return userInfoService.updateUserinfo(mast);
                } else {
                    return new Message(0, "该用户不存在或已被删除");
                }
            }
        } catch (Exception e) {
            return new Message(0, e.getMessage());
        }
    }

    /**
     * 列设置页跳转
     *
     * @param action
     * @param model
     * @return
     * @throws JsonProcessingException
     */
    @AuthMethod(role = "ROLE_USER")
    @GetMapping("getColumns")
    public ModelAndView getColumns(String action, Model model) throws JsonProcessingException {
        model.addAttribute("action", action);
        return new ModelAndView("columns/getColumns");
    }

    /**
     * 获取列设置信息
     *
     * @param action
     * @return
     */
    @AuthMethod(role = "ROLE_USER")
    @RequestMapping("getColumnsInfo")
    public List<TableHeader> getColumnsInfo(String action, HttpSession session) {
        User user = (User) session.getAttribute("user");
        return tableHeaderService.listTableHeader(user.getId(), action);
    }

    /**
     * 更新列排序
     *
     * @param ids
     * @return
     */
    @AuthMethod(role = "ROLE_USER")
    @RequestMapping("upordersHeader")
    public Message upordersHeader(String ids) {
        try {
            tableHeaderService.updateSort(ids);
            return new Message(1, "更新成功");
        } catch (Exception e) {
            return new Message(0, e.getMessage());
        }
    }

    /**
     * 更新列排序
     *
     * @param rows
     * @return
     */
    @AuthMethod(role = "ROLE_USER")
    @PostMapping(value = "saveTableHeader")
    public Message saveTableHeader(String rows) {
        if (StringUtils.isNotEmpty(rows)) {
            rows = CmsUtils.decryptBASE64(rows);
            JSONArray jsonArray = JSONObject.parseArray(rows);
            List<TableHeader> dtos = JSONArray.parseArray(jsonArray.toString(), TableHeader.class);
            if (dtos.size() > 0) {
                return tableHeaderService.batchUpdateTableHeader(dtos);
            }
        }
        return new Message(0, "更新失败");
    }

    /**
     * 删除用户信息
     *
     * @param id
     * @return
     */
    @AuthMethod(role = "ROLE_USER")
    @PostMapping("deleteUser")
    public Message deleteUser(String id) {

        //第一步：删除用户角色关联表
        userRoleService.batchDeleteUserRole(id);

        //第二步：删除用户信息表
        return userInfoService.deleteUserInfo(id);
    }


    @GetMapping("importUser")
    @AuthMethod(role = "ROLE_USER")
    public ModelAndView importUser() {
        return new ModelAndView("user/import");
    }

    /**
     * 批量删除用户信息
     *
     * @param ids
     * @return
     */
    @AuthMethod(role = "ROLE_USER")
    @PostMapping("batchDeleteUser")
    public Message batchDeleteUser(String ids) {
        //第一步：删除用户角色关联表
        userRoleService.batchDeleteUserRole(ids);
        //第二步：删除用户信息表
        return userInfoService.batchDeleteUser(ids);
    }


    /**
     * 模板上传处理
     *
     * @param file
     * @param headerRows
     * @return
     * @throws Exception
     */
    @PostMapping(value = "importUpload", produces = "text/html;charset=utf-8")
    @AuthMethod(role = "ROLE_USER")
    public String importUpload(MultipartFile file, Integer headerRows) throws Exception {
        Message msg = new Message(0, "导入失败");
        if (file == null || file.isEmpty()) {
            msg.setMessage("上传文件不能为空，请重新选择模板文件，然后重试。");
            return JsonUtil.objectToString(msg);
        }
        List<UserInfoVo> vo;
        List<UserInfoVo> list = ExcelUtils.importExcel(file, 0, headerRows, UserInfoVo.class);
        if (list != null && list.size() > 0) {
            vo = list;
            msg.setCode(1);
            msg.setMessage("导入成功");
            msg.setData(vo);
        }
        return JsonUtil.objectToString(msg);
    }

    /**
     * 下载模板
     *
     * @param response
     */
    @AuthMethod(role = "ROLE_USER")
    @RequestMapping("downloadExcel")
    public void downloadExcel(HttpServletResponse response) throws IOException {
        //需要导出的数据列表。
        List<UserInfoVo> list = new ArrayList<>();
        UserInfoVo vo = new UserInfoVo();
        vo.setBh("（必填项）");
        vo.setName("（必填项）");
        vo.setPassword("默认为123456");
        vo.setGlBh("部门编号,多个部门编号以分号相隔（必填项）");
        vo.setJsBh("");
        vo.setStatus("T或F（T=正常,F=禁用）");
        list.add(vo);
        ExcelUtils.exportExcel(list, null, "用户基本信息", UserInfoVo.class, "用户基本信息导入模板", response);
    }

    /**
     * 检测并保存模板数据，返回错误数据
     *
     * @param rows
     * @return
     */
    @AuthMethod(role = "ROLE_USER")
    @PostMapping(value = "batchsaveExcel")
    public Message batchsaveExcel(String rows,HttpSession session) {
        if (rows != null && !rows.isEmpty()) {
            User user = (User) session.getAttribute("user");
            rows = CmsUtils.decryptBASE64(rows);
            JSONArray jsonArray = JSONObject.parseArray(rows);
            List<UserInfoVo> dtos = JSONArray.parseArray(jsonArray.toString(), UserInfoVo.class);
            List<UserInfoVo> array = new ArrayList<>();
            List<UserInfo> listUserInfo = new ArrayList<>();
            if (dtos.size() > 0) {
                for (UserInfoVo mast : dtos) {
                    List<String> err = new ArrayList<>();
                    UserInfo userInfo = new UserInfo();
                    //检测用户编号
                    if (mast.getBh() == null || mast.getBh().isEmpty()) {
                        err.add("【用户编号】不能为空");
                    } else {
                        if (userInfoService.queryByAccount(mast.getBh()) != null) {
                            err.add("用户编号【" + mast.getBh() + "】已存在");
                        }
                    }
                    //检测管理部门
                    if (mast.getGlBh() == null || mast.getGlBh().isEmpty()) {
                        err.add("【管理部门】不能为空");
                    } else {
                        String str = deptInfoService.listNotInDeptName(mast.getGlBh());
                        if (str != null && !str.isEmpty()) {
                            err.add("管理部门【" + str + "】不存在");
                        }
                    }
                    //检测岗位
                    if (StringUtils.isNotEmpty(mast.getStation())) {
                        StationInfo stationInfo = stationInfoService.queryStationInfoByStationName(mast.getStation());
                        if (stationInfo == null) {
                            err.add("岗位【" + mast.getStation() + "】不存在");
                        }
                    }
                    //检测管理处
                    if (StringUtils.isNotEmpty(mast.getJsBh())) {
                        String str = managePointService.listNotInManagePoint(user.getBookSet(),mast.getJsBh());
                        if (str != null && !str.isEmpty()) {
                            err.add("管理处【" + str + "】不存在");
                        }
                    }
                    if (err.size() > 0) {
                        mast.setRemarks(String.join("；", err));
                        mast.setCheckStr("F");
                        array.add(mast);
                    } else {
                        userInfo.setUserName(CmsUtils.strToTrim(mast.getName()));
                        userInfo.setDeptID(CmsUtils.strToTrim(mast.getGlBh()));
                        userInfo.setSettID(CmsUtils.strToTrim(mast.getJsBh()));
                        userInfo.setLoginAccount(CmsUtils.strToTrim(mast.getBh()));
                        userInfo.setStation(mast.getStation());
                        userInfo.setStatus("T".equals(mast.getStatus()) ? 1 : 0);
                        userInfo.setRegistTime(BeanUtil.strToTimestampTime(CmsUtils.getNowDate()));
                        userInfo.setManager("F");
                        if (mast.getPassword() != null && !mast.getPassword().isEmpty()) {
                            userInfo.setLoginPassword(new MyJasyptStringEncryptor().encrypt(mast.getPassword()));
                        } else {
                            userInfo.setLoginPassword(new MyJasyptStringEncryptor().encrypt("123456"));
                        }
                        listUserInfo.add(userInfo);
                    }
                }
                userInfoService.batchSaveUserInfo(listUserInfo);
                return new Message(1, "检测完成", array);
            } else {
                return new Message(0, "数据解析错误，请联系管理员");
            }
        }
        return new Message(0, "检测数据为空");
    }

}
