package com.shgs.lodge.controller;

import com.shgs.lodge.auth.AuthClass;
import com.shgs.lodge.auth.AuthMethod;
import com.shgs.lodge.dto.*;
import com.shgs.lodge.primary.entity.ProceedType;
import com.shgs.lodge.primary.entity.ReveExpeItem;
import com.shgs.lodge.service.*;
import com.shgs.lodge.util.Message;
import com.shgs.lodge.util.SelectJson;
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
 * 收支项目视图接口类
 *
 * @author 雷智荣
 */
@RestController
@RequestMapping("/reve/")
@Scope("prototype")
@AuthClass("login")
public class ReveExpeItemController {


    private IReveExpeItemService reveExpeItemService;


    private ICashBankService cashBankService;


    private ITableHeaderService tableHeaderService;


    private ICustomService customService;


    private IBillCorpInfoService billCorpInfoService;


    private IAncillaryProjectsService ancillaryProjectsService;


    private IDeptInfoService deptInfoService;


    private IPaymentMethodService paymentMethodService;

    @Autowired
    public void setReveExpeItemService(IReveExpeItemService reveExpeItemService) {
        this.reveExpeItemService = reveExpeItemService;
    }

    @Autowired
    public void setCashBankService(ICashBankService cashBankService) {
        this.cashBankService = cashBankService;
    }

    @Autowired
    public void setTableHeaderService(ITableHeaderService tableHeaderService) {
        this.tableHeaderService = tableHeaderService;
    }

    @Autowired
    public void setCustomService(ICustomService customService) {
        this.customService = customService;
    }

    @Autowired
    public void setBillCorpInfoService(IBillCorpInfoService billCorpInfoService) {
        this.billCorpInfoService = billCorpInfoService;
    }

    @Autowired
    public void setAncillaryProjectsService(IAncillaryProjectsService ancillaryProjectsService) {
        this.ancillaryProjectsService = ancillaryProjectsService;
    }

    @Autowired
    public void setDeptInfoService(IDeptInfoService deptInfoService) {
        this.deptInfoService = deptInfoService;
    }

    @Autowired
    public void setPaymentMethodService(IPaymentMethodService paymentMethodService) {
        this.paymentMethodService = paymentMethodService;
    }

    /**
     * 首页
     *
     * @param model
     * @return
     */
    @AuthMethod(role = "ROLE_REVE_EXPE_ITEM")
    @GetMapping("index")
    public ModelAndView index(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<HeaderColumns> columns = tableHeaderService.listHeaderColumns(user.getId(), "reveExpeItemGrid", "com.shgs.lodge.dto.ReveExpeItemDto");
        model.addAttribute("columns", columns);
        return new ModelAndView("reve/index");
    }

    /**
     * 款项类型 Dialog
     *
     * @param id
     * @param model
     * @return
     */
    @AuthMethod(role = "ROLE_REVE_EXPE_ITEM")
    @GetMapping("proceedTypeDialog")
    public ModelAndView proceedTypeDialog(String id, String pid, Model model) {
        ProceedType proceedType = new ProceedType();
        boolean disabled;
        if (id != null && !id.isEmpty()) {
            proceedType = reveExpeItemService.queryProceedTypeById(id);
            if ("all".equals(pid)) {
                disabled = true;
            } else {
                disabled = pid == null || pid.isEmpty();
            }
        } else {
            proceedType.setContents("F");
            disabled = false;
        }
        model.addAttribute("disabled", disabled);
        model.addAttribute("proceedType", proceedType);
        model.addAttribute("pid", proceedType.getParent() != null ? proceedType.getParent().getId() : pid);
        return new ModelAndView("reve/proceedTypeDialog");
    }

    /**
     * 收支项目添加(编辑)页面
     *
     * @param id
     * @param proceedTypeId
     * @param action
     * @param model
     * @return
     */
    @AuthMethod(role = "ROLE_REVE_EXPE_ITEM")
    @GetMapping("reveExpeItemDialog")
    public ModelAndView reveExpeItemDialog(String id, String proceedTypeId, String action, Model model) {
        ReveExpeItemDto reveExpeItemDto = new ReveExpeItemDto();
        if (id != null && !id.isEmpty()) {
            reveExpeItemDto = reveExpeItemService.queryReveExpeItemDtoById(id);
            if ("copy".equals(action))
                reveExpeItemDto.setId(null);
        } else {
            reveExpeItemDto.setZtbz("T");
            reveExpeItemDto.setProceedTypeId(proceedTypeId);
            reveExpeItemDto.setOnAccount("F");
            reveExpeItemDto.setSyntropy("F");
        }
        List<SelectJson> projectTypes = customService.listCustomParameByCode("projectType");
        List<SelectJson> retypelist = customService.listCustomParameByCode("ACCOUNTYPE");
        List<SelectJson> balance = customService.listCustomParameByCode("BALANCE");
        List<SelectJson> auditStatus = customService.listCustomParameByCode("DOCUMENT-STATUS");

        auditStatus.add(0, new SelectJson("", "请选择...", ""));
        model.addAttribute("reveExpeItemDto", reveExpeItemDto);
        model.addAttribute("retypelist", retypelist);
        model.addAttribute("projectTypes", projectTypes);
        model.addAttribute("balance", balance);
        model.addAttribute("auditStatus", auditStatus);
        return new ModelAndView("reve/reveExpeItemDialog");
    }

    /**
     * 保存款项类型
     *
     * @param proceedType
     * @param br
     * @return
     */
    @AuthMethod(role = "ROLE_REVE_EXPE_ITEM")
    @PostMapping("saveProceedType")
    public Message saveProceedType(@Validated ProceedType proceedType, BindingResult br, String pid) {
        if (br.hasErrors()) {
            return new Message(0, Objects.requireNonNull(br.getFieldError()).getDefaultMessage());
        }
        try {
            ProceedType mast = new ProceedType();
            String id = proceedType.getId();
            if (id == null || proceedType.getId().isEmpty()) {
                //添加
                mast.setName(proceedType.getName());
                mast.setRemarks(proceedType.getRemarks());
                mast.setProceedBh(proceedType.getProceedBh());
                return reveExpeItemService.addProceedType(mast, pid);
            } else {
                //更新
                mast = reveExpeItemService.queryProceedTypeById(id);
                mast.setName(proceedType.getName());
                mast.setRemarks(proceedType.getRemarks());
                mast.setProceedBh(proceedType.getProceedBh());
                return reveExpeItemService.updateProceedType(mast, pid);
            }
        } catch (Exception e) {
            return new Message(0, e.getMessage());
        }
    }

    /**
     * 删除款项类型
     *
     * @param id
     * @return
     */
    @AuthMethod(role = "ROLE_REVE_EXPE_ITEM")
    @PostMapping(value = "deleteProceedType")
    public Message deleteProceedType(String id) {
        return reveExpeItemService.deleteProceedType(id);

    }

    /**
     * 获取款项列表
     *
     * @param keyword
     * @return
     */
    @AuthMethod(role = "ROLE_REVE_EXPE_ITEM")
    @RequestMapping("listProceedType")
    public List<TreeJson> listProceedType(String keyword) {
        return reveExpeItemService.listProceedTypeToTreeJson(keyword);
    }


    /**
     * 查询收支项目
     *
     * @param proceedTypeId 款项类型ID
     * @return
     */
    @AuthMethod(role = "ROLE_REVE_EXPE_ITEM")
    @RequestMapping("findReveExpeItem")
    public List<ReveExpeItemDto> findReveExpeItem(String proceedTypeId) {
        return reveExpeItemService.listReveExpeItemDto(proceedTypeId);
    }

    /**
     * 保存收支项目
     *
     * @param reveExpeItemDto
     * @param br
     * @return
     */
    @AuthMethod(role = "ROLE_REVE_EXPE_ITEM")
    @PostMapping("saveReveExpeItem")
    public Message saveReveExpeItem(@Validated ReveExpeItemDto reveExpeItemDto, BindingResult br, String[] auditStatus) {
        if (br.hasErrors()) {
            return new Message(0, Objects.requireNonNull(br.getFieldError()).getDefaultMessage());
        }
        try {
            if (auditStatus.length > 0)
                reveExpeItemDto.setAuditStatus(StringUtils.join(auditStatus, ","));
            ReveExpeItem mast = new ReveExpeItemDto().getReveExpeItem(reveExpeItemDto);
            String id = mast.getId();
            if (StringUtils.isEmpty(id)) {
                return reveExpeItemService.addReveExpeItem(mast, reveExpeItemDto.getProceedTypeId(), reveExpeItemDto.getCashBankId(), reveExpeItemDto.getSaleCorpId(), reveExpeItemDto.getDeptId());
            } else {
                //更新
                ReveExpeItem temp = reveExpeItemService.queryReveExpeItemById(id);
                temp.setZtbz(mast.getZtbz());
                temp.setAuditStatus(mast.getAuditStatus());
                temp.setReType(mast.getReType());
                temp.setBalance(mast.getBalance());
                temp.setOnAccount(mast.getOnAccount());
                temp.setSyntropy(mast.getSyntropy());
                temp.setSzxmName(mast.getSzxmName());
                temp.setYinyexmName(mast.getYinyexmName());
                temp.setWlfyxmName(mast.getWlfyxmName());
                temp.setWljbflName(mast.getWljbflName());
                temp.setDqflmName(mast.getDqflmName());
                temp.setJxsdaName(mast.getJxsdaName());
                temp.setPaymentMethod(mast.getPaymentMethod());
                temp.setDescribe(mast.getDescribe());
                return reveExpeItemService.updateReveExpeItem(temp, reveExpeItemDto.getProceedTypeId(), reveExpeItemDto.getCashBankId(), reveExpeItemDto.getSaleCorpId(), reveExpeItemDto.getDeptId());
            }
        } catch (Exception e) {
            return new Message(0, e.getMessage());
        }
    }

    /**
     * 删除收支项目
     *
     * @param id      收支项目ID
     * @return
     */
    @AuthMethod(role = "ROLE_REVE_EXPE_ITEM")
    @PostMapping("deleteReveExpeItem")
    public Message deleteReveExpeItem(String id) {
        return reveExpeItemService.deleteReveExpeItem(id);
    }

    /**
     * 科目选择器
     *
     * @param value
     * @param model
     * @return
     */
    @AuthMethod(role = "ROLE_REVE_EXPE_ITEM")
    @GetMapping("getCashBank")
    public ModelAndView getCashBank(String value, Model model) {
        model.addAttribute("cashBankValue", value);
        return new ModelAndView("reve/getCashBank");
    }

    /**
     * 查询科目信息
     *
     * @param pid
     * @param keyword 过滤关键字
     * @return
     */
    @AuthMethod(role = "ROLE_REVE_EXPE_ITEM")
    @RequestMapping("findCashBank")
    public List<CashBankListDto> findCashBank(String pid, String keyword) {
        return cashBankService.listCashBankDto(pid, keyword);
    }

    /**
     * 客商选择器
     *
     * @return
     */
    @AuthMethod(role = "ROLE_REVE_EXPE_ITEM")
    @GetMapping("getCorpInfo")
    public ModelAndView getCorpInfo() {
        return new ModelAndView("reve/getCorpInfo");
    }


    /**
     * 查询客商
     *
     * @param pid     客商类型
     * @param keyword 过滤关键字
     * @return
     */
    @AuthMethod(role = "ROLE_REVE_EXPE_ITEM")
    @RequestMapping("findCorpInfo")
    public List<CorpInfoListDto> findCorpInfo(String keyword, String pid) {
        return billCorpInfoService.listCorpInfoListDto(keyword, pid);
    }

    /**
     * 辅助项目选择器
     *
     * @param model
     * @return
     */
    @AuthMethod(role = "ROLE_REVE_EXPE_ITEM")
    @GetMapping("getProjects")
    public ModelAndView getProjects(String value, Model model) {
        model.addAttribute("projectType", value);
        return new ModelAndView("reve/getProjects");
    }

    @AuthMethod(role = "ROLE_REVE_EXPE_ITEM")
    @RequestMapping("findProjects")
    public List<AncillaryProjectsListDto> findProjects(String pid, String keyword, String t_id) {
        return ancillaryProjectsService.listAncillaryProjectsDto(pid, keyword, t_id);
    }

    /**
     * 部门选择器
     *
     * @return
     */
    @AuthMethod(role = "ROLE_REVE_EXPE_ITEM")
    @GetMapping("getDeptInfo")
    public ModelAndView getDeptInfo() {
        return new ModelAndView("reve/getDeptInfo");
    }

    /**
     * 查询部门信息
     *
     * @param pid
     * @param keyword
     * @return
     */
    @AuthMethod(role = "ROLE_REVE_EXPE_ITEM")
    @RequestMapping("findDeptInfo")
    public List<DeptInfoListDto> findDeptInfo(String pid, String keyword) {
        return deptInfoService.listDeptInfoListDto(pid, keyword);
    }

    /**
     * 查询摘要信息
     *
     * @return
     */
    @AuthMethod(role = "ROLE_REVE_EXPE_ITEM")
    @RequestMapping("getDescribe")
    public List<SelectJson> getDescribe() {
        return customService.listCustomParameByCode("describe");
    }

    /**
     * 获取结算方式
     *
     * @return
     */
    @AuthMethod(role = "ROLE_REVE_EXPE_ITEM")
    @RequestMapping("getPaymentMethod")
    public List<TreeJson> getPaymentMethod() {
        return paymentMethodService.listPaymentMethod();
    }


}
