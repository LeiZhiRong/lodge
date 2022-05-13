package com.shags.lodge.controller.business;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shags.lodge.auth.AuthClass;
import com.shags.lodge.auth.AuthMethod;
import com.shags.lodge.business.entity.AssetsInfo;
import com.shags.lodge.dto.*;
import com.shags.lodge.dto.business.*;
import com.shags.lodge.service.business.IAssetsInfoService;
import com.shags.lodge.service.business.IAssetsTypeService;
import com.shags.lodge.service.business.ISitDownService;
import com.shags.lodge.service.primary.*;
import com.shags.lodge.util.*;
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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

/**
 * @author yglei
 * @classname AssetsInfoController
 * @description TODO
 * @date 2021-11-23 14:09
 */

@RestController
@RequestMapping(value = "/assetsInfo/")
@Scope("prototype")
@AuthClass("login")
public class AssetsInfoController {

    private IAssetsInfoService assetsInfoService;

    private ITableHeaderService tableHeaderService;

    private IAssetsTypeService assetsTypeService;

    private IManagePointService managePointService;

    private IDeptInfoService deptInfoService;

    private ISitDownService sitDownService;

    private IBillCorpInfoService billCorpInfoService;

    private ICustomService customService;

    @Autowired
    public void setCustomService(ICustomService customService) {
        this.customService = customService;
    }

    @Autowired
    public void setAssetsInfoService(IAssetsInfoService assetsInfoService) {
        this.assetsInfoService = assetsInfoService;
    }

    @Autowired
    public void setTableHeaderService(ITableHeaderService tableHeaderService) {
        this.tableHeaderService = tableHeaderService;
    }

    @Autowired
    public void setAssetsTypeService(IAssetsTypeService assetsTypeService) {
        this.assetsTypeService = assetsTypeService;
    }

    @Autowired
    public void setManagePointService(IManagePointService managePointService) {
        this.managePointService = managePointService;
    }

    @Autowired
    public void setDeptInfoService(IDeptInfoService deptInfoService) {
        this.deptInfoService = deptInfoService;
    }

    @Autowired
    public void setSitDownService(ISitDownService sitDownService) {
        this.sitDownService = sitDownService;
    }

    @Autowired
    public void setBillCorpInfoService(IBillCorpInfoService billCorpInfoService) {
        this.billCorpInfoService = billCorpInfoService;
    }

    /**
     * 首页
     *
     * @param model
     * @param session
     * @return
     * @throws JsonProcessingException
     */
    @AuthMethod(role = "ROLE_ASSETSINFO")
    @RequestMapping("index")
    public ModelAndView index(Model model, HttpSession session) throws JsonProcessingException {
        User user = (User) session.getAttribute("user");
        List<HeaderColumns> columns = tableHeaderService.listHeaderColumns(user.getId(), "assetsInfoGrid", "com.shags.lodge.dto.business.AssetsInfoDto");
        //管理处
        List<SelectJson> managePointList = managePointService.getClientManagePointByUser(user);
        model.addAttribute("managePointList", managePointList);
        model.addAttribute("columns", columns);
        return new ModelAndView("assetsInfo/index");
    }

    /**
     * 获取分页数据
     *
     * @param order
     * @param sort
     * @param page
     * @param rows
     * @param session
     * @return
     */
    @AuthMethod(role = "ROLE_ASSETSINFO")
    @RequestMapping("list")
    public Pager<AssetsInfoDto> list(String order, String sort, int page, int rows, String codeType, String codeValue,String corp_Name,String dept_ID,String managePoint_Name,String sitDown_Name,String assetsType_Name, HttpSession session) {
        SystemContext.setPageSize(rows);
        SystemContext.setPageNumber(page);
        SystemContext.setOrder(order);
        SystemContext.setSort(sort);
        User user = (User) session.getAttribute("user");
        return assetsInfoService.listAssetsInfoDto(codeType,codeValue,corp_Name,dept_ID,managePoint_Name,sitDown_Name,assetsType_Name,user);
    }



    /**
     * 获取房屋类型
     *
     * @param session
     * @return
     */
    @AuthMethod(role = "ROLE_ASSETSINFO")
    @RequestMapping(value = "listAssetsType")
    public List<TreeJson> listAssetsType(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return assetsTypeService.listTreeJson(user.getBookSet());
    }

    /**
     * 获取管理部门信息跳转
     *
     * @param keyword 关键字
     * @param model   model
     * @return json
     */
    @AuthMethod(role = "ROLE_ASSETSINFO")
    @GetMapping("getDeptInfo")
    public ModelAndView getDeptInfo(String keyword, Model model) {
        model.addAttribute("keyword", keyword);
        return new ModelAndView("assetsInfo/getDeptInfo");
    }

    @AuthMethod(role = "ROLE_ASSETSINFO")
    @RequestMapping(value = "getDeptList")
    public List<TreeJson> getDeptList(String keyword, HttpSession session) {
        User user = (User) session.getAttribute("user");
        return deptInfoService.getClientTreeJson(keyword, 1, user.getManageDept());
    }

    /**
     * @description: 获取房产类型信息
     * @param: [keyword, model]
     * @author: ygLei
     * @return: {@link ModelAndView}
     * @date: 2021-11-26 17:10
     */
    @AuthMethod(role = "ROLE_ASSETSINFO")
    @GetMapping("getAssetsType")
    public ModelAndView getAssetsType(String keyword, Model model) {
        model.addAttribute("keyword", keyword);
        return new ModelAndView("assetsInfo/getAssetsType");
    }

    @AuthMethod(role = "ROLE_ASSETSINFO")
    @RequestMapping(value = "getAssetsTypeList")
    public List<TreeJson> getAssetsTypeList(String keyword, HttpSession session) {
        User user = (User) session.getAttribute("user");
        return assetsTypeService.getClientTreeJson(keyword, user.getBookSet(), "T");
    }


    /**
     * @description: 获取座落位置
     * @param: [keyword, model]
     * @author: ygLei
     * @return: {@link ModelAndView}
     * @date: 2021-11-26 17:14
     */
    @AuthMethod(role = "ROLE_ASSETSINFO")
    @GetMapping("getSitDownInfo")
    public ModelAndView getSitDownInfo(String keyword, Model model) {
        model.addAttribute("keyword", keyword);
        return new ModelAndView("assetsInfo/getSitDownInfo");
    }

    @AuthMethod(role = "ROLE_ASSETSINFO")
    @RequestMapping(value = "getSitDownList")
    public List<TreeJson> getSitDownList(String keyword, HttpSession session) {
        User user = (User) session.getAttribute("user");
        return sitDownService.getClientSitDownToTreeJson(keyword, user.getBookSet());
    }


    /**
     * @description: 获取管理处
     * @param: [keyword, model]
     * @author: ygLei
     * @return: {@link ModelAndView}
     * @date: 2022-01-18 14:04
     */
    @AuthMethod(role = "ROLE_ASSETSINFO")
    @GetMapping("getManagePointInfo")
    public ModelAndView getManagePointInfo(String keyword, Model model) {
        model.addAttribute("keyword", keyword);
        return new ModelAndView("assetsInfo/getManagePointInfo");
    }


    @AuthMethod(role = "ROLE_ASSETSINFO")
    @RequestMapping(value = "getManagePointList")
    public List<TreeJson> getManagePointList(String keyword, HttpSession session) {
        User user = (User) session.getAttribute("user");
        return managePointService.getClientManagePointToTreeJson(user.getBookSet(),keyword,"T",user.getBalanceDept());
    }


    /**
     * 获取客商目录树
     *
     * @param keyword
     * @param model
     * @return
     */
    @AuthMethod(role = "ROLE_ASSETSINFO")
    @GetMapping("getBillCorpInfo")
    public ModelAndView getBillCorpInfo(String keyword, String corpType, Integer status, Model model) {
        model.addAttribute("keyword", keyword);
        model.addAttribute("corpType", corpType);
        model.addAttribute("status", status);
        return new ModelAndView("assetsInfo/getBillCorpInfo");
    }

    @AuthMethod(role = "ROLE_ASSETSINFO")
    @RequestMapping(value = "getBillCorpList")
    public List<TreeJson> getBillCorpList(String keyword, String corpType, Integer status, HttpSession session) {
        return billCorpInfoService.listCorpInfoToTreeJson(keyword, corpType, status);
    }

    /**
     * @description: 添加编辑页面
     * @param: [id, pid, model]
     * @author: ygLei
     * @return: {@link ModelAndView}
     * @date: 2021-12-02 13:27
     */
    @AuthMethod(role = "ROLE_ASSETSINFO")
    @RequestMapping(value = "add_Dialog")
    public ModelAndView add_Dialog(String id, Model model) {
        AssetsInfoForm assetsInfo = new AssetsInfoForm();
        if (StringUtils.isNotEmpty(id)) {
            assetsInfo = new AssetsInfoForm(assetsInfoService.queryAssetsInfo(id));
        } else {
            assetsInfo.setZtBz("T");
        }
        //房产状态
        List<SelectJson> houseState = customService.listCustomParameByCode("HouseState");
        //房产结构
        List<SelectJson> structure = customService.listCustomParameByCode("structure");
        //计量单位
        List<SelectJson> assetUnit = customService.listCustomParameByCode("assetUnit");
        model.addAttribute("houseState", houseState);
        model.addAttribute("assetsInfo", assetsInfo);
        model.addAttribute("structure", structure);
        model.addAttribute("assetUnit", assetUnit);
        return new ModelAndView("assetsInfo/add_Dialog");
    }

    /**
     * @description: 保存/更新 房产信息
     * @param: [assetsInfoForm, br, session]
     * @author: ygLei
     * @return: {@link Message}
     * @date: 2022-01-18 9:17
     */
    @AuthMethod(role = "ROLE_ASSETSINFO")
    @PostMapping("save")
    public Message save(@Validated AssetsInfoForm assetsInfoForm, BindingResult br, HttpSession session) {
        if (br.hasErrors()) {
            return new Message(0, Objects.requireNonNull(br.getFieldError()).getDefaultMessage());
        }
        try {
            User user = (User) session.getAttribute("user");
            AssetsInfo assetsInfo = new AssetsInfoForm().getAssetsInfo(assetsInfoForm);
            String id = assetsInfo.getId();
            if (StringUtils.isEmpty(id)) {
                assetsInfo.setBookSet(user.getBookSet());
                assetsInfo.setrVTime(CmsUtils.getTimeMillis());
                assetsInfo.setCreateUser(user.getUserName());
                assetsInfo.setCreateTime(BeanUtil.strToTimestampTime(CmsUtils.getNowDate()));
                return assetsInfoService.addAssetsInfo(assetsInfo);
            } else {
                //更新
               assetsInfo.setUpdateTime(BeanUtil.strToTimestampTime(CmsUtils.getNowDate()));
               assetsInfo.setUpdateUser(user.getUserName());
               return assetsInfoService.updateAssetsInfo(assetsInfo);
            }
        } catch (Exception e) {
            return new Message(0, e.getMessage());
        }

    }


    /**
     * @description: 部门选择器
     * @param: []
     * @author: ygLei
     * @return: {@link ModelAndView}
     * @date: 2022-01-06 9:52
     */
    @AuthMethod(role = "ROLE_ASSETSINFO")
    @GetMapping("findDeptInfo")
    public ModelAndView findDeptInfo() {
        return new ModelAndView("assetsInfo/findDeptInfo");
    }


    @AuthMethod(role = "ROLE_ASSETSINFO")
    @PostMapping("listDeptInfo")
    public List<DeptInfoListDto> listDeptInfo(String pid, String keyword, HttpSession session) {
        User user = (User) session.getAttribute("user");
        return deptInfoService.listDeptInfoListDto(pid, keyword, false, user.getManageDept());
    }

    /**
     * @description: 公司选择器
     * @param: []
     * @author: ygLei
     * @return: {@link ModelAndView}
     * @date: 2022-01-06 9:52
     */
    @AuthMethod(role = "ROLE_ASSETSINFO")
    @GetMapping("findBillCorpInfo")
    public ModelAndView findBillCorpInfo() {
        return new ModelAndView("assetsInfo/findBillCorpInfo");
    }


    @AuthMethod(role = "ROLE_ASSETSINFO")
    @PostMapping("listBillCorpInfo")
    public List<CorpInfoListDto> listBillCorpInfo(String keyword, String corpType, Integer status) {
        return billCorpInfoService.listCorpInfoListDto(keyword, corpType, status);
    }

    /**
     * @description: 管理处选择器
     * @param: []
     * @author: ygLei
     * @return: {@link ModelAndView}
     * @date: 2022-01-07 16:05
     */
    @AuthMethod(role = "ROLE_ASSETSINFO")
    @GetMapping("findManagePointInfo")
    public ModelAndView findManagePointInfo() {
        return new ModelAndView("assetsInfo/findManagePointInfo");
    }


    @AuthMethod(role = "ROLE_ASSETSINFO")
    @PostMapping("listManagePointInfo")
    public List<ManagePointListDto> listManagePointInfo(String keyword, String ztbz, HttpSession session) {
        User user = (User) session.getAttribute("user");
        return managePointService.listManagePointListDto(user.getBookSet(), keyword, ztbz, user.getBalanceDept());
    }

    /**
     * @description: 坐落位置选择器
     * @param: []
     * @author: ygLei
     * @return: {@link ModelAndView}
     * @date: 2022-01-10 14:56
     */
    @AuthMethod(role = "ROLE_ASSETSINFO")
    @GetMapping("findSitDownInfo")
    public ModelAndView findSitDownInfo() {
        return new ModelAndView("assetsInfo/findSitDownInfo");
    }


    @AuthMethod(role = "ROLE_ASSETSINFO")
    @PostMapping("listSitDownInfo")
    public List<SitDownInfoListDto> listSitDownInfo(String keyword, String ztbz, HttpSession session) {
        User user = (User) session.getAttribute("user");
        return sitDownService.listSitDownInfoDto(user.getBookSet(), keyword, ztbz);
    }


    /**
     * 资产类型选择器
     *
     * @return
     */
    @AuthMethod(role = "ROLE_ASSETSINFO")
    @GetMapping("findAssetsTypeInfo")
    public ModelAndView findAssetsTypeInfo() {
        return new ModelAndView("assetsInfo/findAssetsTypeInfo");
    }


    @AuthMethod(role = "ROLE_ASSETSINFO")
    @PostMapping("listAssetsTypeInfo")
    public List<AssetsTypeListDto> listAssetsTypeInfo(String pid, String keyword, HttpSession session) {
        User user = (User) session.getAttribute("user");
        return assetsTypeService.listAssetsTypeListDto(user.getBookSet(), pid, keyword, "T");
    }


}
