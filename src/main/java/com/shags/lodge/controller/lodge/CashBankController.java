package com.shags.lodge.controller.lodge;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shags.lodge.auth.AuthClass;
import com.shags.lodge.auth.AuthMethod;
import com.shags.lodge.dto.CashBankDto;
import com.shags.lodge.dto.TreeJson;
import com.shags.lodge.util.Message;
import com.shags.lodge.util.Pager;
import com.shags.lodge.util.SelectJson;
import com.shags.lodge.util.SystemContext;
import com.shags.lodge.primary.entity.CashBank;
import com.shags.lodge.service.ICashBankService;
import com.shags.lodge.service.ICustomService;
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
 * 科目信息管理视图层-View接口
 *
 * @author 雷智荣
 */
@RestController
@RequestMapping("/cashbank/")
@Scope("prototype")
@AuthClass("login")
public class CashBankController {


    private ICashBankService cashBankService;


    private ICustomService customService;

    @Autowired
    public void setCashBankService(ICashBankService cashBankService) {
        this.cashBankService = cashBankService;
    }

    @Autowired
    public void setCustomService(ICustomService customService) {
        this.customService = customService;
    }

    /**
     * 科目管理首页
     *
     * @return String
     * @throws JsonProcessingException
     */
    @AuthMethod(role = "ROLE_CASHBANK")
    @GetMapping("index")
    public ModelAndView index() throws JsonProcessingException {
        return new ModelAndView("cashbank/index");
    }

    /**
     * 科目信息编辑页dialog
     *
     * @param model
     * @return String
     */
    @AuthMethod(role = "ROLE_CASHBANK")
    @GetMapping("dialog")
    public ModelAndView dialog(String id, String pid, Model model) throws JsonProcessingException {
        CashBank cashBank = new CashBank();
        boolean disabled;
        if (id != null && !id.isEmpty()) {
            cashBank = cashBankService.queryCashBank(id);
            if ("all".equals(pid)) {
                disabled = true;
            } else {
                disabled = pid == null || pid.isEmpty();
            }
        } else {
            cashBank.setZtbz("T");
            cashBank.setContents("F");
            disabled = false;
        }
        List<SelectJson> balance = customService.listCustomParameByCode("BALANCE");
        balance.add(0, new SelectJson("", "无", null));
        model.addAttribute("disabled", disabled);
        model.addAttribute("cashBank", cashBank);
        model.addAttribute("balance", balance);
        model.addAttribute("pid", cashBank.getParent() != null ? cashBank.getParent().getId() : pid);
        return new ModelAndView("cashbank/dialog");
    }

    /**
     * 保存(更新)科目信息
     *
     * @param cashBankDto
     * @param br
     * @param pid         上级科目ID
     * @return Message
     */
    @AuthMethod(role = "ROLE_CASHBANK")
    @PostMapping("save")
    public Message save(@Validated CashBankDto cashBankDto, BindingResult br, String pid) {
        if (br.hasErrors()) {
            return new Message(0, Objects.requireNonNull(br.getFieldError()).getDefaultMessage());
        }
        try {
            CashBank cashBank = new CashBankDto().getCashBank(cashBankDto);
            String id = cashBank.getId();
            if (id == null || id.isEmpty()) {
                //添加
                return cashBankService.addCashBank(cashBank, pid);
            } else {
                //更新
                CashBank mast = cashBankService.queryCashBank(id);
                mast.setContents(cashBank.getContents());
                mast.setKmBH(cashBank.getKmBH());
                mast.setKmMC(cashBank.getKmMC());
                mast.setZtbz(cashBank.getZtbz());
                mast.setKmJC(cashBank.getKmJC());
                mast.setKmPYM(cashBank.getKmPYM());
                mast.setRemarks(cashBank.getRemarks());
                mast.setBalance(cashBank.getBalance());
                return cashBankService.updateCashBank(mast, pid);
            }
        } catch (Exception e) {
            return new Message(0, e.getMessage());
        }

    }

    /**
     * 获取科目分页数据
     *
     * @param order
     * @param sort
     * @param page
     * @param rows
     * @param pid
     * @return
     */
    @AuthMethod(role = "ROLE_CASHBANK")
    @RequestMapping("list")
    public Pager<CashBankDto> findCashBankDto(String order, String sort, int page, int rows, String pid, String value) {
        SystemContext.setPageSize(rows);
        SystemContext.setPageNumber(page);
        SystemContext.setOrder(order);
        SystemContext.setSort(sort);
        return cashBankService.findCashBankDto(pid, value);
    }

    /**
     * 更新排序
     *
     * @param ids
     * @return
     */
    @AuthMethod(role = "ROLE_CASHBANK")
    @RequestMapping("uporders")
    public Message uporders(String[] ids) {
        try {
            cashBankService.updateSort(ids);
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
    @AuthMethod(role = "ROLE_CASHBANK")
    @RequestMapping(value = "listParent")
    public List<TreeJson> listParent() {
        return cashBankService.getCashBank2TreeJson(null);
    }

    /**
     * 删除部门信息
     *
     * @param id
     * @return
     */
    @AuthMethod(role = "ROLE_CASHBANK")
    @PostMapping(value = "delete")
    public Message delete(String id) {
        return cashBankService.deleteCashBank(id);

    }

}
