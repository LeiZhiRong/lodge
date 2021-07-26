package com.shags.lodge.controller.business;

import com.shags.lodge.auth.AuthClass;
import com.shags.lodge.auth.AuthMethod;
import com.shags.lodge.business.entity.SitDown;
import com.shags.lodge.dto.User;
import com.shags.lodge.service.business.ISitDownService;
import com.shags.lodge.service.primary.IManagePointService;
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
 * @classname SitDownController
 * @description 资产管理-坐落位置View接口层
 * @date 2021-07-23 9:59
 */

@RestController
@RequestMapping(value = "/sitDown/")
@Scope("prototype")
@AuthClass("login")
public class SitDownController {

    private ISitDownService sitDownService;

    private IManagePointService managePointService;

    @Autowired
    public void setManagePointService(IManagePointService managePointService) {
        this.managePointService = managePointService;
    }

    @Autowired
    public void setSitDownService(ISitDownService sitDownService) {
        this.sitDownService = sitDownService;
    }

    /**
     * @description: 首页
     * @param: []
     * @author: ygLei
     * @return: {@link ModelAndView}
     * @date: 2021-07-23 10:03
     */
    @AuthMethod(role = "ROLE_SitDown")
    @GetMapping(value = "index")
    public ModelAndView index(Model model,HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<SelectJson> managePoint = managePointService.listManagePointToSelectJson(user.getBookSet());
        managePoint.add(0,new SelectJson("","<全部>"));
        model.addAttribute("pitList", managePoint);
        return new ModelAndView("sitDown/index");
    }


    /**
     * @description: 添加/编辑页面
     * @param: [id, point_id, model, session]
     * @author: ygLei
     * @return: {@link ModelAndView}
     * @date: 2021-07-23 13:28
     */
    @AuthMethod(role = "ROLE_SitDown")
    @GetMapping(value = "dialog")
    public ModelAndView dialog(String id,String point_id, Model model, HttpSession session) {
        SitDown sitDown = new SitDown();
        User user = (User) session.getAttribute("user");
        if (StringUtils.isNotEmpty(id)) {
            sitDown = sitDownService.querySitDown(id);
        } else {
            sitDown.setZtBz("T");
            sitDown.setManagerPoint(StringUtils.isNotEmpty(point_id)?point_id:null);
        }
        List<SelectJson> managePoint = managePointService.listManagePointToSelectJson(user.getBookSet());
        model.addAttribute("sitDown", sitDown);
        model.addAttribute("managePoint", managePoint);
        return new ModelAndView("sitDown/dialog");
    }

    /**
     * @description: 分页数据
     * @param: [order, sort, page, rows, point_id, value, session]
     * @author: ygLei
     * @return: {@link Pager<SitDown>}
     * @date: 2021-07-23 10:25
     */
    @AuthMethod(role = "ROLE_SitDown")
    @RequestMapping(value = "list")
    public Pager<SitDown> findSitDown(String order, String sort, int page, int rows, String point_id, String value, HttpSession session) {
        User user = (User) session.getAttribute("user");
        SystemContext.setPageSize(rows);
        SystemContext.setPageNumber(page);
        SystemContext.setOrder(order);
        SystemContext.setSort(sort);
        return sitDownService.findSitDown(user.getBookSet(), point_id, value);
    }

    /**
     * @description: 保存
     * @param: [SitDown, br, session]
     * @author: ygLei
     * @return: {@link Message}
     * @date: 2021-07-23 10:29
     */
    @AuthMethod(role = "ROLE_SitDown")
    @PostMapping("save")
    public Message save(@Validated SitDown sitDown, BindingResult br, HttpSession session) {
        if (br.hasErrors()) {
            return new Message(0, Objects.requireNonNull(br.getFieldError()).getDefaultMessage());
        }
        try {
            User user = (User) session.getAttribute("user");
            String id = sitDown.getId();
            if (StringUtils.isEmpty(id)) {
                sitDown.setId(null);
                sitDown.setBookSet(user.getBookSet());
                sitDown.setrVTime(CmsUtils.getTimeMillis());
                return sitDownService.addSitDown(sitDown);
            } else {
                //更新
                SitDown mast = sitDownService.querySitDown(id);
                mast.setName(sitDown.getName());
                mast.setZtBz(sitDown.getZtBz());
                mast.setManagerPoint(sitDown.getManagerPoint());
                mast.setrVTime(CmsUtils.getTimeMillis());
                return sitDownService.updateSitDown(mast);
            }
        } catch (Exception e) {
            return new Message(0, e.getMessage());
        }
    }

    /**
     * @description: 删除
     * @param: [id]
     * @author: ygLei
     * @return: {@link Message}
     * @date: 2021-07-23 10:30
     */
    @AuthMethod(role = "ROLE_SitDown")
    @PostMapping(value = "delete")
    public Message delete(String id) {
        return sitDownService.deleteSitDown(id);
    }

}
