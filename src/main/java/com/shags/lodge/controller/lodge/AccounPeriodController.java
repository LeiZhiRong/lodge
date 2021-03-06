package com.shags.lodge.controller.lodge;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shags.lodge.auth.AuthClass;
import com.shags.lodge.auth.AuthMethod;
import com.shags.lodge.dto.AccounPeriodDto;
import com.shags.lodge.dto.HeaderColumns;
import com.shags.lodge.util.CmsUtils;
import com.shags.lodge.util.Message;
import com.shags.lodge.util.Pager;
import com.shags.lodge.util.SystemContext;
import com.shags.lodge.primary.entity.AccounPeriod;
import com.shags.lodge.service.primary.IAccounPeriodService;
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

import java.util.List;
import java.util.Objects;

/**
 * 会计期间view层
 *
 * @author 雷智荣
 */
@SuppressWarnings("SpellCheckingInspection")
@RestController
@RequestMapping(value = "/period/")
@Scope("prototype")
@AuthClass("login")
public class AccounPeriodController {


    private IAccounPeriodService accounPeriodService;

    @Autowired
    public void setAccounPeriodService(IAccounPeriodService accounPeriodService) {
        this.accounPeriodService = accounPeriodService;
    }

    @AuthMethod(role = "ROLE_ACCOUN_PERIOD")
    @RequestMapping(value = "index")
    public ModelAndView index(Model model) {
        List<HeaderColumns> columns = CmsUtils.getHeaderColumns("com.shags.lodge.dto.AccounPeriodDto");
        model.addAttribute("columns", columns);
        return new ModelAndView("period/index");
    }


    @AuthMethod(role = "ROLE_ACCOUN_PERIOD")
    @RequestMapping(value = "list")
    public Pager<AccounPeriodDto> list(String order, String sort, int page, int rows) {
        SystemContext.setPageSize(rows);
        SystemContext.setPageNumber(page);
        SystemContext.setOrder(order);
        SystemContext.setSort(sort);
        return accounPeriodService.findAccounPeriodDto();
    }


    @AuthMethod(role = "ROLE_ACCOUN_PERIOD")
    @GetMapping(value = "dialog")
    public ModelAndView dialog(String id, Model model) throws JsonProcessingException {
        AccounPeriodDto dto = new AccounPeriodDto();
        if (id != null && !id.isEmpty()) {
            AccounPeriod accounPeriod = accounPeriodService.load(id);
            if (accounPeriod != null)
                dto = new AccounPeriodDto(accounPeriod);
        } else {
            dto.setZtbz("T");
        }
        model.addAttribute("accounPeriodDto", dto);
        return new ModelAndView("period/dialog");
    }


    @AuthMethod(role = "ROLE_ACCOUN_PERIOD")
    @PostMapping(value = "saveAccounPeriodDto")
    public Message saveAccounPeriodDto(@Validated AccounPeriodDto accounPeriodDto, BindingResult br) {
        if (br.hasErrors()) {
            return new Message(0, Objects.requireNonNull(br.getFieldError()).getDefaultMessage());
        }
        AccounPeriod accounPeriod = new AccounPeriodDto().getAccounPeriod(accounPeriodDto);
        String id = accounPeriod.getId();
        if (StringUtils.isNotEmpty(id)) {
            return accounPeriodService.updateAccounPeriod(accounPeriod);
        } else {
            return accounPeriodService.addAccounPeriod(accounPeriod);
        }
    }

    @AuthMethod(role = "ROLE_ACCOUN_PERIOD")
    @PostMapping(value = "delete")
    public Message deleteAccounPeriod(String id) {
        return accounPeriodService.deleteAccounPeriod(id);
    }

}
