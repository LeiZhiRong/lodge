package com.shgs.lodge.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shgs.lodge.auth.AuthClass;
import com.shgs.lodge.auth.AuthMethod;
import com.shgs.lodge.dto.MenuInfoDto;
import com.shgs.lodge.dto.TreeJson;
import com.shgs.lodge.primary.entity.MenuInfo;
import com.shgs.lodge.service.IMenuService;
import com.shgs.lodge.util.*;
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

/**
 * 模块管理 角色权限 开发人员
 * @author 雷智荣
 *
 */
@RestController
@RequestMapping("/module/")
@Scope("prototype")
@AuthClass("login")
public class MenuController {

    @Autowired
    private IMenuService menuService;

    /**
     * 模块管理首页跳转
     *
     * @param model
     * @return
     * @throws JsonProcessingException
     */
    @GetMapping("index")
    @AuthMethod(role = "ROLE_MENU")
    public ModelAndView list(Model model,HttpSession session) throws JsonProcessingException {
        ModelAndView view = new ModelAndView("menu/index");
        return view;
    }

    /**
     * 模块编辑页跳转
     *
     * @param model
     * @return
     */
    @GetMapping("mergeRow")
    @AuthMethod(role = "ROLE_MENU")
    public ModelAndView mergeRow(String id, String pid, Model model) throws JsonProcessingException {
        MenuInfo menuInfo = new MenuInfo();
        boolean disabled = true;
        if (id != null && !id.isEmpty()) {
            menuInfo = menuService.queryMenuInfo(id);
            if("all".equals(pid)){
                disabled=true;
            }else {
                disabled = pid != null && !pid.isEmpty() ? false : true;
            }
        } else {
            menuInfo.setStatus(1);
            menuInfo.setContents("F");
            disabled = false;
        }
        List<SelectJson> select=CmsUtils.getRoleType();
        model.addAttribute("disabled", disabled);
        model.addAttribute("menuInfo", menuInfo);
        model.addAttribute("pid", menuInfo.getParent() != null ? menuInfo.getParent().getId() : pid);
        model.addAttribute("select", select);
        ModelAndView view = new ModelAndView("menu/mergeFrom");
        return view;
    }

    /**
     * 保存(更新)模块数据入库
     *
     * @param menuInfoDto
     * @param br
     * @param pid
     * @param session
     * @return Message
     */
    @PostMapping("save")
    @AuthMethod(role = "ROLE_MENU")
    public Message save(@Validated MenuInfoDto menuInfoDto, BindingResult br, String pid, HttpSession session) {
        if (br.hasErrors()) {
            return new Message(0, br.getFieldError().getDefaultMessage());
        }
        try {
            MenuInfo menuInfo = new MenuInfoDto().getMenuInfo(menuInfoDto);
            String id = menuInfo.getId();
            if (id == null || menuInfo.getId().isEmpty()) {
                //添加
                return menuService.addMenuInfo(menuInfo, pid);
            } else {
                //更新
                MenuInfo mast = menuService.queryMenuInfo(id);
                mast.setContents(menuInfo.getContents());
                mast.setIcons(menuInfo.getIcons());
                mast.setType(menuInfo.getType());
                mast.setName(menuInfo.getName());
                mast.setStatus(menuInfo.getStatus());
                mast.setPathUrl(menuInfo.getPathUrl());
                mast.setHompPage(menuInfo.getHompPage());
                mast.setModelGrant(menuInfo.getModelGrant());
                return menuService.updateMenuInfo(mast, pid);
            }
        } catch (Exception e) {
            return new Message(0, e.getMessage());
        }

    }

    /**
     * 获取模块管理分页数据
     *
     * @param order
     * @param sort
     * @param page
     * @param rows
     * @param pid
     * @return
     */
    @RequestMapping("list")
    @AuthMethod(role = "ROLE_MENU")
    public Pager<MenuInfoDto> findMenuInfo(String order, String sort, int page, int rows, String pid,String value) {
        SystemContext.setPageSize(rows);
        SystemContext.setPageNumber(page);
        SystemContext.setOrder(order);
        SystemContext.setSort(sort);
        Pager<MenuInfoDto> test = menuService.findMenuInfoDto(pid,value);
        return test;

    }

    /**
     * 更新拖动排序
     *
     * @param ids
     * @param type
     * @return
     */
    @RequestMapping("uporders")
    @AuthMethod(role = "ROLE_MENU")
    public Message uporders(String[] ids, String type) {
        try {
            menuService.updateSort(ids);
            return new Message(1, "success");
        } catch (Exception e) {
            return new Message(0, e.getMessage());
        }
    }

    @RequestMapping(value = "listParent")
    @AuthMethod(role = "ROLE_MENU")
    public List<TreeJson> listParent() {
        SystemContext.setOrder(null);
        SystemContext.setSort(null);
        return menuService.getMenuInfo2TreeJson();
    }

    @PostMapping(value = "delete")
    @AuthMethod(role = "ROLE_MENU")
    public Message delete(String id) {
        return menuService.deleteMenuInfo(id);

    }

}