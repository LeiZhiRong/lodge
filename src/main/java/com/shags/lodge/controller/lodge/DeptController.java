package com.shags.lodge.controller.lodge;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shags.lodge.auth.AuthClass;
import com.shags.lodge.auth.AuthMethod;
import com.shags.lodge.dto.DeptInfoDto;
import com.shags.lodge.dto.TreeJson;
import com.shags.lodge.util.CmsUtils;
import com.shags.lodge.util.Message;
import com.shags.lodge.util.Pager;
import com.shags.lodge.util.SystemContext;
import com.shags.lodge.primary.entity.DeptInfo;
import com.shags.lodge.service.IDeptInfoService;
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

/**
 * 部门信息管理视图层-View接口
 *
 * @author 雷智荣
 */
@RestController
@RequestMapping("/dept/")
@Scope("prototype")
@AuthClass("login")
public class DeptController {


    private IDeptInfoService deptInfoService;

    @Autowired
    public void setDeptInfoService(IDeptInfoService deptInfoService) {
        this.deptInfoService = deptInfoService;
    }

    /**
     * 部门管理首页
     *
     * @return String
     * @throws JsonProcessingException
     */
    @AuthMethod(role = "ROLE_DEPT")
    @GetMapping("index")
    public ModelAndView index() throws JsonProcessingException {
        return new ModelAndView("dept/index");
    }

    /**
     * 部门信息编辑页dialog
     *
     * @param model
     * @return String
     */
    @AuthMethod(role = "ROLE_DEPT")
    @GetMapping("dialog")
    public ModelAndView dialog(String id, String pid, Model model) throws JsonProcessingException {
        DeptInfo deptInfo = new DeptInfo();
        boolean disabled;
        if (id != null && !id.isEmpty()) {
            deptInfo = deptInfoService.queryDeptInfo(id);
            if ("all".equals(pid)) {
                disabled = true;
            } else {
                disabled = pid == null || pid.isEmpty();
            }
        } else {
            deptInfo.setStatus(1);
            deptInfo.setContents("F");
            disabled = false;
        }
        model.addAttribute("disabled", disabled);
        model.addAttribute("deptInfo", deptInfo);
        model.addAttribute("pid", deptInfo.getParent() != null ? deptInfo.getParent().getId() : pid);
        model.addAttribute("select", CmsUtils.getRoleType());
        return new ModelAndView("dept/dialog");
    }

    /**
     * 保存(更新)部门信息
     *
     * @param deptInfoDto
     * @param br
     * @param pid         上级目录ID
     * @return Message
     */
    @AuthMethod(role = "ROLE_DEPT")
    @PostMapping("save")
    public Message save(@Validated DeptInfoDto deptInfoDto, BindingResult br, String pid) {
        if (br.hasErrors()) {
            return new Message(0, Objects.requireNonNull(br.getFieldError()).getDefaultMessage());
        }
        try {
            DeptInfo deptInfo = new DeptInfoDto().getDeptInfo(deptInfoDto);
            String id = deptInfo.getId();
            if (id == null || deptInfo.getId().isEmpty()) {
                return deptInfoService.addDeptInfo(deptInfo, pid);
            } else {
                //更新
                DeptInfo mast = deptInfoService.queryDeptInfo(id);
                mast.setContents(deptInfo.getContents());
                mast.setDeptID(deptInfo.getDeptID());
                mast.setDeptName(deptInfo.getDeptName());
                mast.setStatus(deptInfo.getStatus());
                mast.setDeptJc(deptInfo.getDeptJc());
                return deptInfoService.updateDeptInfo(mast, pid);
            }
        } catch (Exception e) {
            return new Message(0, e.getMessage());
        }

    }

    /**
     * 获取部门分页数据
     *
     * @param order
     * @param sort
     * @param page
     * @param rows
     * @param pid
     * @return
     */
    @AuthMethod(role = "ROLE_DEPT")
    @RequestMapping("list")
    public Pager<DeptInfoDto> findMenuInfoDto(String order, String sort, int page, int rows, String pid, String value) {
        SystemContext.setPageSize(rows);
        SystemContext.setPageNumber(page);
        SystemContext.setOrder(order);
        SystemContext.setSort(sort);
        return deptInfoService.findDeptInfoDto(pid, value);
    }

    /**
     * 更新排序
     *
     * @param ids
     * @return
     */
    @AuthMethod(role = "ROLE_DEPT")
    @RequestMapping("uporders")
    public Message uporders(String[] ids) {
        try {
            deptInfoService.updateSort(ids);
            return new Message(1, "success");
        } catch (Exception e) {
            return new Message(0, e.getMessage());
        }
    }

    /**
     * 部门信息目录树
     *
     * @return
     */
    @AuthMethod(role = "ROLE_DEPT")
    @RequestMapping(value = "listParent")
    public List<TreeJson> listParent() {
        SystemContext.setOrder(null);
        SystemContext.setSort(null);
        return deptInfoService.getDeptInfo2TreeJson(null);
    }

    /**
     * 删除部门信息
     *
     * @param id
     * @return
     */
    @AuthMethod(role = "ROLE_DEPT")
    @PostMapping(value = "delete")
    public Message delete(String id) {
        return deptInfoService.deleteDeptInfo(id);

    }


}
