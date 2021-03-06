package com.shags.lodge.controller.lodge;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shags.lodge.auth.AuthClass;
import com.shags.lodge.auth.AuthMethod;
import com.shags.lodge.dto.AccounCodeDto;
import com.shags.lodge.dto.HeaderColumns;
import com.shags.lodge.dto.User;
import com.shags.lodge.util.Message;
import com.shags.lodge.util.SelectJson;
import com.shags.lodge.primary.entity.AccounCode;
import com.shags.lodge.primary.entity.CustomParame;
import com.shags.lodge.service.primary.IAccounCodeService;
import com.shags.lodge.service.primary.ICustomService;
import com.shags.lodge.service.primary.ITableHeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("SpellCheckingInspection")
@RestController
@RequestMapping(value = "/accoun/")
@Scope("prototype")
@AuthClass("login")
public class AccounCodeController {


    private IAccounCodeService accounCodeService;


    private ICustomService customService;


    private ITableHeaderService tableHeaderService;

    @Autowired
    public void setAccounCodeService(IAccounCodeService accounCodeService) {
        this.accounCodeService = accounCodeService;
    }

    @Autowired
    public void setCustomService(ICustomService customService) {
        this.customService = customService;
    }

    @Autowired
    public void setTableHeaderService(ITableHeaderService tableHeaderService) {
        this.tableHeaderService = tableHeaderService;
    }

    @AuthMethod(role = "ROLE_ACCOUN")
    @RequestMapping(value = "index")
    public ModelAndView index(Model model, HttpSession session) throws JsonProcessingException {
        User user = (User) session.getAttribute("user");
        List<HeaderColumns> columns = tableHeaderService.listHeaderColumns(user.getId(), "accounGrid", "com.shags.lodge.dto.AccounCodeDto");
        model.addAttribute("columns", columns);
        return new ModelAndView("accoun/index");
    }


    @AuthMethod(role = "ROLE_ACCOUN")
    @RequestMapping(value = "list")
    public List<AccounCodeDto> list(HttpSession session) {
        User user = (User) session.getAttribute("user");
        String bookSet = user.getBookSet();
        List<AccounCode> accounCode = accounCodeService.listAccounCode(bookSet);
        return new AccounCodeDto().listAccounCodeDto(accounCode);
    }


    @RequestMapping(value = "getDateFormat")
    @AuthMethod(role = "ROLE_ACCOUN")
    public List<SelectJson> getDateFormat() {
        return customService.listCustomParameByCode("DateFormat");
    }

    @AuthMethod(role = "ROLE_ACCOUN")
    @RequestMapping(value = "saveAccounCode")
    public Message saveAccounCode(@Validated AccounCodeDto dto, BindingResult br, HttpSession session) {
        if (br.hasErrors()) {
            return new Message(0, Objects.requireNonNull(br.getFieldError()).getDefaultMessage());
        }
        try {
            AccounCode accoun = new AccounCodeDto().getAccounCode(dto);

            if (dto.getCodeType() != null && !dto.getCodeType().isEmpty()) {
                CustomParame type = customService.queryCustomParame(dto.getCodeType());
                accoun.setCodeType(type);
            }
            //?????????
            if (dto.getPrefixOne() != null && !dto.getPrefixOne().isEmpty()) {
                CustomParame one = customService.queryCustomParame(dto.getPrefixOne());
                accoun.setPrefixOne(one);
            }
            //?????????
            if (dto.getPrefixTwo() != null && !dto.getPrefixTwo().isEmpty()) {
                CustomParame two = customService.queryCustomParame(dto.getPrefixTwo());
                accoun.setPrefixTwo(two);
            }
            //?????????
            if (dto.getPrefixThree() != null && !dto.getPrefixThree().isEmpty()) {
                CustomParame three = customService.queryCustomParame(dto.getPrefixThree());
                accoun.setPrefixThree(three);
            }
            //?????????
            if (dto.getPrefixFour() != null && !dto.getPrefixFour().isEmpty()) {
                CustomParame four = customService.queryCustomParame(dto.getPrefixFour());
                accoun.setPrefixFour(four);
            }
            //?????????
            if (dto.getSeparator() != null && !dto.getSeparator().isEmpty()) {
                CustomParame separator = customService.queryCustomParame(dto.getSeparator());
                accoun.setSeparator(separator);
            }
            String id = accoun.getId();
            if (id == null || id.isEmpty()) {
                User user = (User) session.getAttribute("user");
                accoun.setBookSet(user.getBookSet());
                accoun.setId(null);
                return accounCodeService.addAccounCode(accoun);
            } else {
                AccounCode accounCode = accounCodeService.queryAccounCodeByID(id);
                accoun.setCodeType(accounCode.getCodeType());
                return accounCodeService.updateAccounCode(accoun);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(0, e.getMessage());
        }
    }

    @AuthMethod(role = "ROLE_ACCOUN")
    @RequestMapping(value = "addAccounCode")
    public ModelAndView addAccounCode(String id, Model model) {
        AccounCodeDto dto = new AccounCodeDto();
        boolean edit = false;
        if (id != null && !id.isEmpty()) {
            AccounCode accounCode = accounCodeService.queryAccounCodeByID(id);
            if (accounCode != null) {
                dto = new AccounCodeDto(accounCode);
                edit = true;
            }
        }
        List<SelectJson> separator = customService.listCustomParameByCode("Separator");
        List<SelectJson> documentNumber = customService.listCustomParameByCode("DocumentNumber");
        List<SelectJson> codeType = customService.listCustomParame("DOCUMENT-CODE-TYPE", null, true);
        codeType.add(0, new SelectJson("", "?????????..."));
        model.addAttribute("accounCodeDto", dto);
        model.addAttribute("documentNumber", documentNumber);
        model.addAttribute("separator", separator);
        model.addAttribute("codeType", codeType);
        model.addAttribute("edit", edit);
        return new ModelAndView("accoun/addAccounCode");
    }

    @AuthMethod(role = "ROLE_ACCOUN")
    @PostMapping(value = "deleteAccoun")
    public Message deleteAccoun(String id) {
        return accounCodeService.deleteAccounCodeByID(id);
    }

}
