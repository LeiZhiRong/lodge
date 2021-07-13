package com.shgs.lodge.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shgs.lodge.auth.AuthClass;
import com.shgs.lodge.auth.AuthMethod;
import com.shgs.lodge.dto.AccounInfoDto;
import com.shgs.lodge.dto.HeaderColumns;
import com.shgs.lodge.dto.User;
import com.shgs.lodge.primary.entity.AccounInfo;
import com.shgs.lodge.service.IAccounInfoService;
import com.shgs.lodge.service.ITableHeaderService;
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
import java.util.Objects;

/**
 * 账套信息示图接口层
 *
 * @author 雷智荣
 */
@RestController
@RequestMapping("/bookset/")
@Scope("prototype")
@AuthClass("login")
public class AccounInfoController {


    private IAccounInfoService accounInfoService;


    private ITableHeaderService tableHeaderService;

    @Autowired
    public void setAccounInfoService(IAccounInfoService accounInfoService) {
        this.accounInfoService = accounInfoService;
    }

    @Autowired
    public void setTableHeaderService(ITableHeaderService tableHeaderService) {
        this.tableHeaderService = tableHeaderService;
    }

    /**
     * 账套管理首页
     *
     * @param model
     * @param session
     * @return
     * @throws JsonProcessingException
     */
    @AuthMethod(role = "ROLE_BOOKSET")
    @RequestMapping("index")
    public ModelAndView index(Model model, HttpSession session) throws JsonProcessingException {
        User user = (User) session.getAttribute("user");
        List<HeaderColumns> columns = tableHeaderService.listHeaderColumns(user.getId(), "bookSetGrid", "com.shgs.lodge.dto.AccounInfoDto");
        model.addAttribute("columns", columns);
        return new ModelAndView("bookset/index");
    }

    /**
     * 获取账套分页数据
     *
     * @param order
     * @param sort
     * @param page
     * @param rows
     * @param value
     * @return
     */
    @AuthMethod(role = "ROLE_BOOKSET")
    @RequestMapping("list")
    public Pager<AccounInfoDto> findAccounInfoDto(String order, String sort, int page, int rows, String value) {
        SystemContext.setPageSize(rows);
        SystemContext.setPageNumber(page);
        SystemContext.setOrder(order);
        SystemContext.setSort(sort);
        return accounInfoService.findAccounInfoDto(value, null);
    }

    /**
     * 添加页面dialog
     *
     * @param id    关键字
     * @param model
     * @return
     * @throws JsonProcessingException
     */
    @AuthMethod(role = "ROLE_BOOKSET")
    @GetMapping("dialog")
    public ModelAndView dialog(String id, Model model) throws JsonProcessingException {
        AccounInfoDto dto = new AccounInfoDto();
        boolean disabled = false;
        if (id != null && !id.isEmpty()) {
            AccounInfo accounInfo = accounInfoService.queryAccounInfoByID(id);
            if (accounInfo != null) {
                dto = new AccounInfoDto(accounInfo);
                disabled = true;
            }
        } else {
            dto.setZtbz("T");
        }
        model.addAttribute("accounInfo", dto);
        model.addAttribute("disabled", disabled);
        return new ModelAndView("bookset/dialog");
    }

    /**
     * 保存（更新）
     *
     * @param dto
     * @param br
     * @param session
     * @return
     */
    @AuthMethod(role = "ROLE_BOOKSET")
    @PostMapping("save")
    public Message save(@Validated AccounInfoDto dto, BindingResult br, HttpSession session) {
        if (br.hasErrors()) {
            return new Message(0, Objects.requireNonNull(br.getFieldError()).getDefaultMessage());
        }
        try {
            AccounInfo info = new AccounInfoDto().getAccounInfo(dto);
            String id = info.getId();
            if (id == null || id.isEmpty()) {
                //添加
                info.setrVTime(CmsUtils.getTimeMillis());
                info.setRegTime(BeanUtil.strToTimestampTime(CmsUtils.getNowDate()));
                return accounInfoService.addAccounInfo(info);
            } else {
                //更新
                AccounInfo mast = accounInfoService.queryAccounInfoByID(id);
                mast.setAccounName(info.getAccounName());
                mast.setRemarks(info.getRemarks());
                mast.setZtbz(info.getZtbz());
                mast.setCorpName(info.getCorpName());
                mast.setIcons(info.getIcons());
                return accounInfoService.updateAccounInfo(mast);
            }
        } catch (Exception e) {
            return new Message(0, e.getMessage());
        }

    }

    /**
     * 按ID删除账套
     *
     * @param id
     * @return
     */
    @AuthMethod(role = "ROLE_BOOKSET")
    @PostMapping(value = "delete")
    public Message delete(String id) {
        AccounInfo accounInfo = accounInfoService.queryAccounInfoByID(id);
        if (accounInfo != null) {
            //先检测角色中是否已存在
            return accounInfoService.deleteAccounInfo(id);
        } else {
            return new Message(0, "账套不存在或已被删除");
        }

    }


}
