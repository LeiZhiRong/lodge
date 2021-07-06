package com.shgs.lodge.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shgs.lodge.auth.AuthClass;
import com.shgs.lodge.auth.AuthMethod;
import com.shgs.lodge.dto.CustomTypeDto;
import com.shgs.lodge.dto.TreeJson;
import com.shgs.lodge.primary.entity.CustomParame;
import com.shgs.lodge.primary.entity.CustomType;
import com.shgs.lodge.service.ICustomService;
import com.shgs.lodge.util.Message;
import com.shgs.lodge.util.SelectJson;
import com.shgs.lodge.util.SystemContext;
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

import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/custom/")
@Scope("prototype")
@AuthClass("login")
public class CustomController {


    private ICustomService customService;

    @Autowired
    public void setCustomService(ICustomService customService) {
        this.customService = customService;
    }

    @AuthMethod(role = "ROLE_CUSTOM")
    @GetMapping("index")
    public ModelAndView index() throws JsonProcessingException {
        return new ModelAndView("custom/index");
    }

    @AuthMethod(role = "ROLE_CUSTOM")
    @GetMapping("cusTypeDialog")
    public ModelAndView cusTypeDialog(String id, Model model) {
        CustomTypeDto customType = new CustomTypeDto();
        if (id != null && !id.isEmpty()) {
            CustomType mast = customService.getCustomType(id);
            customType = new CustomTypeDto(mast);
        }
        List<SelectJson> parent= customService.listSelectJson();
        parent.add(0,new SelectJson(null,"一级分类"));
        model.addAttribute("customType", customType);
        model.addAttribute("parent", parent);
        return new ModelAndView("custom/cusTypeDialog");
    }


    @AuthMethod(role = "ROLE_CUSTOM")
    @GetMapping("cusParamDialog")
    public ModelAndView cusParamDialog(String id, String type_id, Model model) {
        CustomParame customParame = new CustomParame();
        if (id != null && !id.isEmpty()) {
            customParame = customService.queryCustomParame(id);
        } else {
            customParame.setTypeId(type_id);
            customParame.setStatus(1);
        }
        model.addAttribute("customParam", customParame);
        return new ModelAndView("custom/cusParamDialog");
    }


    @AuthMethod(role = "ROLE_CUSTOM")
    @PostMapping("saveCusType")
    public Message saveCusType(@Validated CustomTypeDto dto, BindingResult br) {
        if (br.hasErrors()) {
            return new Message(0, Objects.requireNonNull(br.getFieldError()).getDefaultMessage());
        }
        try {
            CustomType mast = new CustomTypeDto().getCustomType(dto);
            String id = mast.getId();
            if (id == null || dto.getId().isEmpty()) {
                return customService.savaCustomType(mast, dto.getPid());
            } else {
                //更新
                CustomType dts = customService.getCustomType(id);
                dts.setTypeCode(mast.getTypeCode());
                dts.setTypeName(mast.getTypeName());
                dts.setOrders(mast.getOrders());
                return customService.updateCustomType(dts, dto.getPid());
            }
        } catch (Exception e) {
            return new Message(0, e.getMessage());
        }
    }


    @AuthMethod(role = "ROLE_CUSTOM")
    @PostMapping(value = "deleteCusType")
    public Message deleteCusType(String id) {
        return customService.deleteCustomType(id);

    }


    @AuthMethod(role = "ROLE_CUSTOM")
    @RequestMapping("list")
    public List<CustomType> list(String keyword) {
        //关闭系统默认排序
        SystemContext.setOrder(null);
        SystemContext.setSort(null);
        return customService.listCustomType(keyword);
    }


    @AuthMethod(role = "ROLE_CUSTOM")
    @RequestMapping("getCusParam")
    public List<CustomParame> getCusParam(String order, String sort, String type_id, String keyword) {
        SystemContext.setOrder(order);
        SystemContext.setSort(sort);
        return customService.listCustomParame(type_id, keyword);
    }

    @AuthMethod(role = "ROLE_CUSTOM")
    @PostMapping("saveCusParam")
    public Message saveCusParam(@Validated CustomParame customParame, BindingResult br) {
        if (br.hasErrors()) {
            return new Message(0, Objects.requireNonNull(br.getFieldError()).getDefaultMessage());
        }
        try {
            CustomParame mast = new CustomParame();
            String id = customParame.getId();
            if (id == null || customParame.getId().isEmpty()) {
                //添加
                mast.setDescribe(customParame.getDescribe());
                mast.setParameCode(customParame.getParameCode());
                mast.setParameName(customParame.getParameName());
                mast.setTypeId(customParame.getTypeId());
                mast.setStatus(customParame.getStatus());
                return customService.savaCustomParame(mast);
            } else {
                //更新
                mast = customService.queryCustomParame(id);
                mast.setDescribe(customParame.getDescribe());
                mast.setParameCode(customParame.getParameCode());
                mast.setParameName(customParame.getParameName());
                mast.setTypeId(customParame.getTypeId());
                mast.setStatus(customParame.getStatus());
                return customService.updateCustomParame(mast);
            }
        } catch (Exception e) {
            return new Message(0, e.getMessage());
        }
    }

    @AuthMethod(role = "ROLE_CUSTOM")
    @PostMapping("BatchDeleteCusParam")
    public Message BatchDeleteCusParam(String ids, String type_id) {
        return customService.batchDeleCustomParame(type_id, ids);
    }


    @AuthMethod(role = "ROLE_CUSTOM")
    @PostMapping("deleteCusParam")
    public Message deleteCusParam(String id) {
        return customService.deleteCustomParame(id);
    }

    @AuthMethod(role = "ROLE_CUSTOM")
    @RequestMapping("uporders")
    public Message upOrders(String[] ids) {
        try {
            customService.updateCusTomParamSort(ids);
            return new Message(1, "success");
        } catch (Exception e) {
            return new Message(0, e.getMessage());
        }
    }

    @AuthMethod(role = "ROLE_CUSTOM")
    @RequestMapping("listCustomType")
    public List<TreeJson> listCustomType() {
        return customService.findTreeJson();
    }

}
