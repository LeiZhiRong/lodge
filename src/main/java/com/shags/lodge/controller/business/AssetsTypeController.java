package com.shags.lodge.controller.business;

import com.shags.lodge.auth.AuthClass;
import com.shags.lodge.auth.AuthMethod;
import com.shags.lodge.business.entity.AssetsType;
import com.shags.lodge.dto.TreeJson;
import com.shags.lodge.dto.User;
import com.shags.lodge.dto.business.AssetsTypeForm;
import com.shags.lodge.dto.business.AssetsTypePage;
import com.shags.lodge.service.business.IAssetsTypeService;
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
 * @Title: 资产分类View接口层
 * @date 2021-07-2013:56
 */
@RestController
@RequestMapping(value = "/asset/")
@Scope("prototype")
@AuthClass("login")
public class AssetsTypeController {

    private IAssetsTypeService assetsTypeService;

    @Autowired
    public void setAssetsTypeService(IAssetsTypeService assetsTypeService) {
        this.assetsTypeService = assetsTypeService;
    }

    /**
     * 资产分类-首页
     *
     * @return html
     */
    @AuthMethod(role = "ROLE_AssEto")
    @GetMapping(value = "index")
    public ModelAndView index() {
        return new ModelAndView("asset/index");
    }


    /**
     * 资产分类-添加（编辑）页面
     *
     * @param id    id
     * @param pid   上级ID
     * @param model model
     * @return html
     */
    @AuthMethod(role = "ROLE_AssEto")
    @GetMapping(value = "dialog")
    public ModelAndView dialog(String id, String pid, Model model) {
        AssetsTypeForm assetsType = new AssetsTypeForm();
        boolean disabled;
        if (StringUtils.isNotEmpty(id)) {
            assetsType = new AssetsTypeForm(assetsTypeService.queryAssetsTypeById(id));
            if ("all".equals(pid)) {
                disabled = true;
            } else {
                disabled = pid == null || pid.isEmpty();
            }
        } else {
            assetsType.setZtBz("T");
            assetsType.setContents("F");
            disabled = false;
        }
        model.addAttribute("disabled", disabled);
        model.addAttribute("assetsType", assetsType);
        model.addAttribute("pid", assetsType.getPid() != null ? assetsType.getPid() : pid);
        return new ModelAndView("asset/dialog");
    }

    /**
     * 资产分类-保存
     *
     * @param assetsTypeDto 实体类
     * @param br            字段验证
     * @return message
     */
    @AuthMethod(role = "ROLE_AssEto")
    @PostMapping("save")
    public Message save(@Validated AssetsTypeForm assetsTypeDto, BindingResult br, HttpSession session) {
        if (br.hasErrors()) {
            return new Message(0, Objects.requireNonNull(br.getFieldError()).getDefaultMessage());
        }
        try {
            User user = (User) session.getAttribute("user");
            AssetsType assetsType = new AssetsTypeForm().getAssetsType(assetsTypeDto);
            String id = assetsType.getId();
            if (StringUtils.isEmpty(id)) {
                assetsType.setBookSet(user.getBookSet());
                assetsType.setrVTime(CmsUtils.getTimeMillis());
                return assetsTypeService.addAssetsType(assetsType, assetsTypeDto.getPid());
            } else {
                //更新
                AssetsType mast = assetsTypeService.queryAssetsTypeById(id);
                mast.setContents(assetsTypeDto.getContents());
                mast.setName(assetsTypeDto.getName());
                mast.setZtBz(assetsTypeDto.getZtBz());
                mast.setrVTime(CmsUtils.getTimeMillis());
                return assetsTypeService.updateAssetsType(mast, assetsTypeDto.getPid());
            }
        } catch (Exception e) {
            return new Message(0, e.getMessage());
        }

    }

    /**
     * 资产分类-删除
     *
     * @param id id
     * @return message
     */
    @AuthMethod(role = "ROLE_AssEto")
    @PostMapping(value = "delete")
    public Message delete(String id) {
        return assetsTypeService.deleteAssetsTypeById(id);

    }

    /**
     * 获取分页数据
     *
     * @param order   排序方向
     * @param sort    排序字段
     * @param page    页码
     * @param rows    每页显示条数
     * @param pid     上级ID
     * @param value   过滤关键字
     * @param session session
     * @return pager
     */
    @AuthMethod(role = "ROLE_AssEto")
    @RequestMapping(value = "list")
    public Pager<AssetsTypePage> findAssetsTypeDto(String order, String sort, int page, int rows, String pid, String value, HttpSession session) {
        User user = (User) session.getAttribute("user");
        SystemContext.setPageSize(rows);
        SystemContext.setPageNumber(page);
        SystemContext.setOrder(order);
        SystemContext.setSort(sort);
        return assetsTypeService.findAssetsTypeDto(user.getBookSet(), pid, value);
    }


    /**
     * 更新排序
     *
     * @param ids ids
     * @return message
     */
    @AuthMethod(role = "ROLE_AssEto")
    @RequestMapping(value = "updateOrders")
    public Message updateOrders(String[] ids) {
        try {
            assetsTypeService.updateSort(ids);
            return new Message(1, "success");
        } catch (Exception e) {
            return new Message(0, e.getMessage());
        }
    }

    /**
     * 获取目录列表
     *
     * @return list
     */
    @AuthMethod(role = "ROLE_AssEto")
    @RequestMapping(value = "listParent")
    public List<TreeJson> listParent(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return assetsTypeService.listTreeJson(user.getBookSet());
    }


}
