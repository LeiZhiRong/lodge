package com.shgs.lodge.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shgs.lodge.auth.AuthClass;
import com.shgs.lodge.auth.AuthMethod;
import com.shgs.lodge.dto.PaymentMethodDto;
import com.shgs.lodge.dto.TreeJson;
import com.shgs.lodge.primary.entity.PaymentMethod;
import com.shgs.lodge.service.IPaymentMethodService;
import com.shgs.lodge.util.Message;
import com.shgs.lodge.util.Pager;
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
 * 收款方式VIEW接口
 *
 * @author 雷智荣
 */
@RestController
@RequestMapping("/payment/")
@Scope("prototype")
@AuthClass("login")
public class PaymentMethodController {


    private IPaymentMethodService paymentMethodService;

    @Autowired
    public void setPaymentMethodService(IPaymentMethodService paymentMethodService) {
        this.paymentMethodService = paymentMethodService;
    }


    /**
     * 首页
     *
     * @return String
     */
    @AuthMethod(role = "ROLE_PAYMENT")
    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView("payment/index");
    }

    @AuthMethod(role = "ROLE_PAYMENT")
    @GetMapping("dialog")
    public ModelAndView dialog(String id, String pid, Model model) throws JsonProcessingException {
        PaymentMethod paymentMethod = new PaymentMethod();
        boolean disabled;
        if (id != null && !id.isEmpty()) {
            paymentMethod = paymentMethodService.queryPaymentMethod(id);
            if ("all".equals(pid)) {
                disabled = true;
            } else {
                disabled = pid == null || pid.isEmpty();
            }
        } else {
            paymentMethod.setZtbz("T");
            paymentMethod.setContents("F");
            disabled = false;
        }
        model.addAttribute("disabled", disabled);
        model.addAttribute("paymentMethod", paymentMethod);
        model.addAttribute("pid", paymentMethod.getParent() != null ? paymentMethod.getParent().getId() : pid);
        return new ModelAndView("payment/dialog");
    }


    @AuthMethod(role = "ROLE_PAYMENT")
    @PostMapping("save")
    public Message save(@Validated PaymentMethodDto paymentMethodDto, BindingResult br, String pid) {
        if (br.hasErrors()) {
            return new Message(0, Objects.requireNonNull(br.getFieldError()).getDefaultMessage());
        }
        try {
            PaymentMethod paymentMethod = new PaymentMethodDto().getPaymentMethod(paymentMethodDto);
            String id = paymentMethod.getId();
            if (id == null || id.isEmpty()) {
                //添加
                return paymentMethodService.addPaymentMethod(paymentMethod, pid);
            } else {
                //更新
                PaymentMethod mast = paymentMethodService.queryPaymentMethod(id);
                mast.setContents(paymentMethod.getContents());
                mast.setPaymentName(paymentMethod.getPaymentName());
                mast.setPaymentBh(paymentMethod.getPaymentBh());
                mast.setZtbz(paymentMethod.getZtbz());
                return paymentMethodService.updatePaymentMethod(mast, pid);
            }
        } catch (Exception e) {
            return new Message(0, e.getMessage());
        }

    }

    @AuthMethod(role = "ROLE_PAYMENT")
    @RequestMapping("list")
    public Pager<PaymentMethodDto> findAncillaryProjectsDto(String order, String sort, int page, int rows, String pid, String value) {
        SystemContext.setPageSize(rows);
        SystemContext.setPageNumber(page);
        SystemContext.setOrder(order);
        SystemContext.setSort(sort);
        return paymentMethodService.findPaymentMethodDto(pid, value);
    }

    @AuthMethod(role = "ROLE_PAYMENT")
    @RequestMapping("uporders")
    public Message uporders(String[] ids) {
        try {
            paymentMethodService.updateSort(ids);
            return new Message(1, "success");
        } catch (Exception e) {
            return new Message(0, e.getMessage());
        }
    }

    /**
     * 目录树
     *
     * @return
     */
    @AuthMethod(role = "ROLE_PAYMENT")
    @RequestMapping(value = "listParent")
    public List<TreeJson> listParent() {
        return paymentMethodService.getPaymentMethod2TreeJson(null);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @AuthMethod(role = "ROLE_PAYMENT")
    @PostMapping(value = "delete")
    public Message delete(String id) {
        return paymentMethodService.deletePaymentMethod(id);

    }


}
