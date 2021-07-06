package com.shgs.lodge.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shgs.lodge.auth.AuthClass;
import com.shgs.lodge.auth.AuthMethod;
import com.shgs.lodge.dto.AccounPeriodDto;
import com.shgs.lodge.dto.HeaderColumns;
import com.shgs.lodge.dto.User;
import com.shgs.lodge.primary.entity.AccounPeriod;
import com.shgs.lodge.service.IAccounPeriodService;
import com.shgs.lodge.util.CmsUtils;
import com.shgs.lodge.util.Message;
import com.shgs.lodge.util.Pager;
import com.shgs.lodge.util.SystemContext;
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

/**
 * 会计期间view层
 *
 * @author 雷智荣
 */
@RestController
@RequestMapping("/period/")
@Scope("prototype")
@AuthClass("login")
public class AccounPeriodController {

    @Autowired
    private IAccounPeriodService accounPeriodService;

    /**
     * 首页
     *
     * @param model
     * @param session
     * @return
     * @throws JsonProcessingException
     */
    @AuthMethod(role = "ROLE_ACCOUN_PERIOD")
    @RequestMapping("index")
    public ModelAndView index(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<HeaderColumns> columns = CmsUtils.getHeaderColumns("com.shgs.lodge.dto.AccounPeriodDto");
        model.addAttribute("columns", columns);
        ModelAndView mv = new ModelAndView("period/index");
        return mv;
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
    @AuthMethod(role = "ROLE_ACCOUN_PERIOD")
    @RequestMapping("list")
    public Pager<AccounPeriodDto> list(String order, String sort, int page, int rows, HttpSession session) {
        SystemContext.setPageSize(rows);
        SystemContext.setPageNumber(page);
        SystemContext.setOrder(order);
        SystemContext.setSort(sort);
        return accounPeriodService.findAccounPeriodDto();
    }


    /**
     * 添加（编辑）页面跳转
     *
     * @param id
     * @param model
     * @param session
     * @return
     * @throws JsonProcessingException
     */
    @AuthMethod(role = "ROLE_ACCOUN_PERIOD")
    @GetMapping("dialog")
    public ModelAndView dialog(String id, Model model, HttpSession session) throws JsonProcessingException {
        AccounPeriodDto dto = new AccounPeriodDto();
        if (id != null && !id.isEmpty()) {
            AccounPeriod accounPeriod = accounPeriodService.load(id);
            if (accounPeriod != null)
                dto = new AccounPeriodDto(accounPeriod);
        } else {
            dto.setZtbz("T");
        }
        model.addAttribute("accounPeriodDto", dto);
        ModelAndView view = new ModelAndView("period/dialog");
        return view;
    }

    /**
     * 新增（编辑）保存
     *
     * @param accounPeriodDto
     * @param br
     * @param session
     * @return
     */
    @AuthMethod(role = "ROLE_ACCOUN_PERIOD")
    @PostMapping("saveAccounPeriodDto")
    public Message saveAccounPeriodDto(@Validated AccounPeriodDto accounPeriodDto, BindingResult br, HttpSession session) {
        if (br.hasErrors()) {
            return new Message(0, br.getFieldError().getDefaultMessage());
        }
        AccounPeriod accounPeriod = new AccounPeriodDto().getAccounPeriod(accounPeriodDto);
        String id = accounPeriod.getId();
        if (StringUtils.isNotEmpty(id)) {
            return accounPeriodService.updateAccounPeriod(accounPeriod);
        } else {
            return accounPeriodService.addAccounPeriod(accounPeriod);
        }
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @AuthMethod(role = "ROLE_ACCOUN_PERIOD")
    @PostMapping("delete")
    public Message deleteAccounPeriod(String id) {
        return accounPeriodService.deleteAccounPeriod(id);
    }

}
