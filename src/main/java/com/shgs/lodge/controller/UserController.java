package com.shgs.lodge.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.shgs.lodge.Vo.UserInfoVo;
import com.shgs.lodge.auth.AuthClass;
import com.shgs.lodge.auth.AuthMethod;
import com.shgs.lodge.dto.HeaderColumns;
import com.shgs.lodge.dto.TreeJson;
import com.shgs.lodge.dto.User;
import com.shgs.lodge.excel.ExcelUtils;
import com.shgs.lodge.primary.entity.StationInfo;
import com.shgs.lodge.primary.entity.TableHeader;
import com.shgs.lodge.primary.entity.UserInfo;
import com.shgs.lodge.service.*;
import com.shgs.lodge.util.*;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private IDeptInfoService deptInfoService;

    @Autowired
    private ITableHeaderService tableHeaderService;

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private IStationInfoService stationInfoService;

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
        List<HeaderColumns> columns = tableHeaderService.listHeaderColumns(user.getId(), "userGrid", "com.shgs.lodge.primary.entity.UserInfo");
        model.addAttribute("columns", columns);
        ModelAndView view = new ModelAndView("user/index");
        return view;
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
        UserInfo userInfo = new UserInfo();
        if (id != null && !id.isEmpty()) {
            userInfo = userInfoService.queryUserInfoById(id);
            userInfo.setLoginPassword(null);
        } else {
            userInfo.setStatus(1);
        }
        List<SelectJson> stations = stationInfoService.listStationInfoToSelectJson(null, "T");
        stations.add(0, new SelectJson("", "请选择...", ""));
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("stations", stations);
        ModelAndView view = new ModelAndView("user/dialog");
        return view;
    }

    /**
     * 部门列表dialog跳转
     *
     * @param keyword 已选择部门编号
     * @param model
     * @return
     * @throws JsonProcessingException
     */
    @AuthMethod(role = "ROLE_USER")
    @GetMapping("getDeptInfo")
    public ModelAndView deptDialog(String keyword, Model model) throws JsonProcessingException {
        model.addAttribute("keyword", keyword);
        ModelAndView view = new ModelAndView("user/getDeptInfo");
        return view;
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
     * @param session
     * @return
     */
    @AuthMethod(role = "ROLE_USER")
    @PostMapping("saveUserInfo")
    public Message saveUserInfo(@Validated UserInfo userInfo, BindingResult br, HttpSession session) {
        if (br.hasErrors()) {
            return new Message(0, br.getFieldError().getDefaultMessage());
        }
        try {
            User user = (User) session.getAttribute("user");
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
        ModelAndView view = new ModelAndView("columns/getColumns");
        return view;
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
     * @param session
     * @return
     */
    @AuthMethod(role = "ROLE_USER")
    @PostMapping(value = "saveTableHeader")
    public Message saveTableHeader(String rows, HttpSession session) {
        if (rows != null && !rows.isEmpty()) {
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
    public ModelAndView importUser(Model model) throws JsonProcessingException {
        ModelAndView view = new ModelAndView("user/import");
        return view;
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
        List<UserInfoVo> vo = new ArrayList<UserInfoVo>();
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
     * @param request
     * @param response
     */
    @AuthMethod(role = "ROLE_USER")
    @RequestMapping("downloadExcel")
    public void downloadExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //需要导出的数据列表。
        List<UserInfoVo> list = new ArrayList<UserInfoVo>();
        UserInfoVo vo = new UserInfoVo();
        vo.setBh("职工工号或手机号码（必填项）");
        vo.setName("用户名（必填项）");
        vo.setPassword("不填默认为123456");
        vo.setGlBh("部门编号,多个部门编号以分号相隔（必填项）");
        vo.setJsBh("不填默认为管理部门");
        vo.setStatus("T或F（T=正常,F=禁用,注意是大写），默认为F");
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
    public Message batchsaveExcel(String rows, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (rows != null && !rows.isEmpty()) {
            rows = CmsUtils.decryptBASE64(rows);
            JSONArray jsonArray = JSONObject.parseArray(rows);
            List<UserInfoVo> dtos = JSONArray.parseArray(jsonArray.toString(), UserInfoVo.class);
            List<UserInfoVo> array = new ArrayList<UserInfoVo>();
            List<UserInfo> listUserInfo = new ArrayList<UserInfo>();
            if (dtos.size() > 0) {
                for (UserInfoVo mast : dtos) {
                    List<String> err = new ArrayList<String>();
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
                    //检测结算部门
                    if (StringUtils.isNotEmpty(mast.getJsBh())) {
                        String str = deptInfoService.listNotInDeptName(mast.getJsBh());
                        if (str != null && !str.isEmpty()) {
                            err.add("结算部门【" + str + "】不存在");
                        }
                    } else {
                        mast.setJsBh(mast.getGlBh());
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
