package com.shags.lodge.controller.business;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shags.lodge.auth.AuthClass;
import com.shags.lodge.auth.AuthMethod;
import com.shags.lodge.dto.HeaderColumns;
import com.shags.lodge.dto.TreeJson;
import com.shags.lodge.dto.User;
import com.shags.lodge.dto.business.AssetsInfoDto;
import com.shags.lodge.service.business.IAssetsInfoService;
import com.shags.lodge.service.business.IAssetsTypeService;
import com.shags.lodge.service.business.ISitDownService;
import com.shags.lodge.service.primary.IDeptInfoService;
import com.shags.lodge.service.primary.IManagePointService;
import com.shags.lodge.service.primary.ITableHeaderService;
import com.shags.lodge.util.Pager;
import com.shags.lodge.util.SelectJson;
import com.shags.lodge.util.SystemContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

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

    /**
     * 首页
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
     * @param order
     * @param sort
     * @param page
     * @param rows
     * @param session
     * @return
     */
    @AuthMethod(role = "ROLE_ASSETSINFO")
    @RequestMapping("list")
    public Pager<AssetsInfoDto> list(String order, String sort, int page, int rows, String field, String value, HttpSession session) {
        SystemContext.setPageSize(rows);
        SystemContext.setPageNumber(page);
        SystemContext.setOrder(order);
        SystemContext.setSort(sort);
        Pager<AssetsInfoDto> dto=new Pager<AssetsInfoDto>();
        return dto;
    }

    /**
     * 获取房屋类型
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
     * @param keyword 关键字
     * @param model model
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
    public List<TreeJson> getDeptList(String keyword,HttpSession session) {
        User user = (User) session.getAttribute("user");
        return deptInfoService.getClientTreeJson(keyword,1,user.getManageDept());
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
    public List<TreeJson> getAssetsTypeList(String keyword,HttpSession session) {
        User user = (User) session.getAttribute("user");
        return  assetsTypeService.getClientTreeJson(keyword,user.getBookSet(),"T");
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
    public List<TreeJson> getSitDownList(String keyword,HttpSession session) {
        User user = (User) session.getAttribute("user");
        return  sitDownService.getClientSitDownToTreeJson(keyword,user.getBookSet());
    }



}
