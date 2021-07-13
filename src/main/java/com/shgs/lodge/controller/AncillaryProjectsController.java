package com.shgs.lodge.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shgs.lodge.auth.AuthClass;
import com.shgs.lodge.auth.AuthMethod;
import com.shgs.lodge.dto.AncillaryProjectsDto;
import com.shgs.lodge.dto.AncillaryProjectsForm;
import com.shgs.lodge.dto.TreeJson;
import com.shgs.lodge.primary.entity.AncillaryProjects;
import com.shgs.lodge.service.IAncillaryProjectsService;
import com.shgs.lodge.service.ICustomService;
import com.shgs.lodge.util.Message;
import com.shgs.lodge.util.Pager;
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

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

/**
 * 辅助项目View层
 *
 * @author 雷智荣
 */
@RestController
@RequestMapping("/projects/")
@Scope("prototype")
@AuthClass("login")
public class AncillaryProjectsController {


    private IAncillaryProjectsService ancillaryProjectsService;


    private ICustomService customService;

    @Autowired
    public void setAncillaryProjectsService(IAncillaryProjectsService ancillaryProjectsService) {
        this.ancillaryProjectsService = ancillaryProjectsService;
    }

    @Autowired
    public void setCustomService(ICustomService customService) {
        this.customService = customService;
    }

    /**
     * 首页
     *
     * @param model
     * @return String
     */
    @AuthMethod(role = "ROLE_PROJECTS")
    @GetMapping("index")
    public ModelAndView index(Model model, HttpSession session) {
        return new ModelAndView("projects/index");
    }

    @AuthMethod(role = "ROLE_PROJECTS")
    @GetMapping("dialog")
    public ModelAndView dialog(String id, String pid, String t_id, Model model) throws JsonProcessingException {
        AncillaryProjectsForm ancillaryProjects = new AncillaryProjectsForm();
        boolean disabled;
        if (id != null && !id.isEmpty()) {
            AncillaryProjects temp = ancillaryProjectsService.queryAncillaryProjects(id);
            ancillaryProjects = new AncillaryProjectsForm(temp);
            if ("all".equals(pid)) {
                disabled = true;
            } else {
                disabled = pid == null || pid.isEmpty();
            }
        } else {
            ancillaryProjects.setZtbz("T");
            ancillaryProjects.setContents("F");
            ancillaryProjects.setT_id(t_id);
            ancillaryProjects.setPid(pid);
            disabled = false;
        }
        model.addAttribute("disabled", disabled);
        model.addAttribute("ancillaryProjects", ancillaryProjects);
        model.addAttribute("pid", ancillaryProjects.getPid());
        return new ModelAndView("projects/dialog");
    }


    @AuthMethod(role = "ROLE_PROJECTS")
    @PostMapping("save")
    public Message save(@Validated AncillaryProjectsForm ancillaryProjectsDto, BindingResult br, String pid, String t_id, HttpSession session) {
        if (br.hasErrors()) {
            return new Message(0, Objects.requireNonNull(br.getFieldError()).getDefaultMessage());
        }
        try {
            AncillaryProjects ancillaryProjects = new AncillaryProjectsForm().getAncillaryProjects(ancillaryProjectsDto);
            String id = ancillaryProjects.getId();
            if (id == null || id.isEmpty()) {
                //添加
                return ancillaryProjectsService.addAncillaryProjects(ancillaryProjects, pid, t_id);
            } else {
                //更新
                AncillaryProjects mast = ancillaryProjectsService.queryAncillaryProjects(id);
                mast.setContents(ancillaryProjects.getContents());
                mast.setProjectsName(ancillaryProjects.getProjectsName());
                mast.setZtbz(ancillaryProjects.getZtbz());
                mast.setProjectsBh(ancillaryProjects.getProjectsBh());
                return ancillaryProjectsService.updateAncillaryProjects(mast, pid, t_id);
            }
        } catch (Exception e) {
            return new Message(0, e.getMessage());
        }

    }

    @AuthMethod(role = "ROLE_PROJECTS")
    @RequestMapping("list")
    public Pager<AncillaryProjectsDto> findAncillaryProjectsDto(String order, String sort, int page, int rows, String pid, String t_id, String value) {
        SystemContext.setPageSize(rows);
        SystemContext.setPageNumber(page);
        SystemContext.setOrder(order);
        SystemContext.setSort(sort);
        return ancillaryProjectsService.findAncillaryProjectsDto(pid, value, t_id);
    }

    @AuthMethod(role = "ROLE_PROJECTS")
    @RequestMapping("uporders")
    public Message uporders(String[] ids) {
        try {
            ancillaryProjectsService.updateSort(ids);
            return new Message(1, "success");
        } catch (Exception e) {
            return new Message(0, e.getMessage());
        }
    }

    /**
     * 科目信息目录树
     *
     * @return
     */
    @AuthMethod(role = "ROLE_PROJECTS")
    @RequestMapping(value = "listParent")
    public List<TreeJson> listParent(String t_id) {
        return ancillaryProjectsService.getAncillaryProjects2TreeJson(null, t_id);
    }

    /**
     * 删除部门信息
     *
     * @param id
     * @return
     */
    @AuthMethod(role = "ROLE_PROJECTS")
    @PostMapping(value = "delete")
    public Message delete(String id) {
        return ancillaryProjectsService.deleteAncillaryProjects(id);

    }

    /**
     * 获取辅助项目分类列表
     *
     * @return
     */
    @AuthMethod(role = "ROLE_PROJECTS")
    @PostMapping(value = "listProjectsType")
    public List<SelectJson> listProjectsType() {
        return customService.listCustomParameByCode("projectType");
    }


}
