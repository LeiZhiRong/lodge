package com.shgs.lodge.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shgs.lodge.Vo.BillConfirmInfoVo;
import com.shgs.lodge.auth.AuthClass;
import com.shgs.lodge.auth.AuthMethod;
import com.shgs.lodge.dto.BillConfirmInfoDto;
import com.shgs.lodge.dto.HeaderColumns;
import com.shgs.lodge.dto.User;
import com.shgs.lodge.excel.ExcelUtils;
import com.shgs.lodge.exception.exception.JsonException;
import com.shgs.lodge.primary.entity.BillAccounInfo;
import com.shgs.lodge.primary.entity.BillApplyInfo;
import com.shgs.lodge.primary.entity.BillCorpInfo;
import com.shgs.lodge.service.*;
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

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 开票确认视图接口层
 *
 * @author 雷智荣
 */
@RestController
@RequestMapping("/confirm/")
@Scope("prototype")
@AuthClass("login")
public class BillConfirmInfoController {


    private ITableHeaderService tableHeaderService;


    private IBillApplyInfoService billApplyInfoService;




    private IDeptInfoService deptInfoService;


    private IBillCorpInfoService billCorpInfoService;



    private IBillAccounInfoService billAccounInfoService;

    @Autowired
    public void setTableHeaderService(ITableHeaderService tableHeaderService) {
        this.tableHeaderService = tableHeaderService;
    }

    @Autowired
    public void setBillApplyInfoService(IBillApplyInfoService billApplyInfoService) {
        this.billApplyInfoService = billApplyInfoService;
    }



    @Autowired
    public void setDeptInfoService(IDeptInfoService deptInfoService) {
        this.deptInfoService = deptInfoService;
    }

    @Autowired
    public void setBillCorpInfoService(IBillCorpInfoService billCorpInfoService) {
        this.billCorpInfoService = billCorpInfoService;
    }


    @Autowired
    public void setBillAccounInfoService(IBillAccounInfoService billAccounInfoService) {
        this.billAccounInfoService = billAccounInfoService;
    }

    /**
     * 首页
     *
     * @param model
     * @param session
     * @return
     * @throws JsonProcessingException
     */
    @AuthMethod(role = "ROLE_CONFIRM")
    @GetMapping("index")
    public ModelAndView index(Model model, HttpSession session) throws JsonProcessingException {
        String endDate = CmsUtils.getNowDate();
        String starDate = BeanUtil.addDayStart(BeanUtil.formatDate(endDate), -31);
        User user = (User) session.getAttribute("user");
        List<HeaderColumns> columns = tableHeaderService.listHeaderColumns(user.getId(), "confirmGrid", "com.shgs.lodge.dto.BillConfirmInfoDto");
        model.addAttribute("columns", columns);
        model.addAttribute("starDate", starDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("applyDept", deptInfoService.listUserByDeptIDS(user.getManageDept()));
        return new ModelAndView("confirm/index");
    }

    /**
     * 获取开票申请单分页数据
     *
     * @param order
     * @param sort
     * @param page
     * @param rows
     * @param field
     * @param value
     * @return
     */
    @AuthMethod(role = "ROLE_CONFIRM")
    @RequestMapping("list")
    public Pager<BillConfirmInfoDto> list(String order, String sort, int page, int rows, String field, String value, String starDate, String endDate, String applyDeptBH, String saleCorpID, String buyerCorpID, HttpSession session) {
        if ("saleCorpName".equals(sort)) {
            sort = "b.saleCorp.corpMC ";
        } else if ("buyerCorpName".equals(sort)) {
            sort = "b.buyerCorp.corpMC ";
        } else if ("billTypeName".equals(sort)) {
            sort = "b.billType.parameName ";
        }
        if ("billMoney".equals(field)) {
            if (!CmsUtils.isNumeric(value))
                return new Pager<>();
        }
        if (starDate != null && !starDate.isEmpty())
            starDate = starDate + " 00:00:00";
        if (endDate != null && !endDate.isEmpty())
            endDate = endDate + " 23:59:59";
        User user = (User) session.getAttribute("user");
        String bookSet = user.getBookSet();
        SystemContext.setPageSize(rows);
        SystemContext.setPageNumber(page);
        SystemContext.setOrder(order);
        SystemContext.setSort(sort);
        return billApplyInfoService.listBillConfirmInfoDto(field, value, starDate, endDate, "T", bookSet, applyDeptBH, saleCorpID, buyerCorpID);
    }


    /**
     * 获取往来客户跳转页
     *
     * @param keyword
     * @param corpType
     * @param model
     * @return
     */
    @AuthMethod(role = "ROLE_CONFIRM")
    @GetMapping("getCorpInfo")
    public ModelAndView getCorpInfo(String keyword, String corpType, Model model) {
        model.addAttribute("keyword", keyword);
        model.addAttribute("corpType", corpType);
        return new ModelAndView("confirm/getCorpInfo");
    }

    /**
     * 获取往来客户信息
     *
     * @param order
     * @param sort
     * @param page
     * @param rows
     * @param keyword
     * @param corpType
     * @return
     */
    @AuthMethod(role = "ROLE_CONFIRM")
    @RequestMapping("getCorpList")
    public Pager<BillCorpInfo> getCorpList(String order, String sort, int page, int rows, String keyword, String corpType) {
        SystemContext.setPageSize(rows);
        SystemContext.setPageNumber(page);
        SystemContext.setOrder(order);
        SystemContext.setSort(sort);
        return billCorpInfoService.listBillCorpInfo(corpType, keyword, "T");
    }


    /**
     * 驳回开票申请
     *
     * @param ids 申请单ID，多个以“，”隔开
     * @return
     */
    @AuthMethod(role = "ROLE_CONFIRM")
    @PostMapping("rejectApply")
    public Message batchExamine(String ids) {
        return billApplyInfoService.batchExamine(ids, "F");
    }

    /**
     * 开票确认dialog
     *
     * @param id    申请单ID
     * @param model
     * @return
     */
    @AuthMethod(role = "ROLE_CONFIRM")
    @GetMapping("dialog")
    public ModelAndView dialog(String id, Model model) {
        BillApplyInfo info = billApplyInfoService.queryBillApplyInfoById(id);
        BillConfirmInfoDto billApplyInfoDto = new BillConfirmInfoDto(info);
        model.addAttribute("billApplyInfoDto", billApplyInfoDto);
        return new ModelAndView("confirm/dialog");
    }


    /**
     * 开票申请单确认
     *
     * @param dto
     * @param br
     * @param session
     * @return
     */
    @AuthMethod(role = "ROLE_CONFIRM")
    @PostMapping("saveConfirmInfo")
    public Message saveConfirmInfo(@Validated BillConfirmInfoDto dto, BindingResult br, HttpSession session) {
        if (br.hasErrors()) {
            return new Message(0, Objects.requireNonNull(br.getFieldError()).getDefaultMessage());
        }
        try {
            User user = (User) session.getAttribute("user");
            String id = dto.getId();
            BillApplyInfo info = billApplyInfoService.queryBillApplyInfoById(id);
            if (info != null) {
                String proccedID = info.getProceedId();
                info.setInvoiceDate(BeanUtil.strToTimestampTime(CmsUtils.getNowDate()));
                info.setBillCode(dto.getBillCode());
                info.setBillNumber(dto.getBillNumber());
                info.setTaxMoney(dto.getTaxMoney());
                info.setTaxRate(dto.getTaxRate());
                info.setAmountMoney(dto.getAmountMoney());
                info.setDrawerUser(user.getUserName());
                info.setBalanceMoney(info.getBillMoney());
                info.setZtbz("O");//更新状态标识
                Message msg = billApplyInfoService.updateBillApplyInfo(info, proccedID);
                if (msg.getCode() == 1) {
                    BillAccounInfo billAccounInfo = new BillAccounInfo();
                    billAccounInfo.setBookSet(info.getBookSet());
                    billAccounInfo.setEntryTime(BeanUtil.strToTimestampTime(CmsUtils.getNowDate()));//入账时间
                    billAccounInfo.setApplyBH(info.getApplyBH());//团购单号
                    billAccounInfo.setDeptInfo(info.getApplyDept());//申请部门
                    billAccounInfo.setAccounType("交易/预收");
                    billAccounInfo.setAmountTaxMoney(info.getBillMoney());//收入含税金额
                    billAccounInfo.setAmountMoney(info.getAmountMoney());//收入不含税金额
                    billAccounInfo.setTaxRate(info.getTaxRate());//税率
                    billAccounInfo.setCountTaxMoney(info.getTaxMoney());//合计税额
                    billAccounInfo.setSaleCorp(info.getSaleCorp());//销售方信息
                    billAccounInfo.setBuyerCorp(info.getBuyerCorp());//购买方信息
                    billAccounInfo.setZtbz("F");//是否已做凭证处理
                    billAccounInfo.setBillType(info.getBillType());//发票类型
                    billAccounInfo.setrVTime(CmsUtils.getTimeMillis());
                    BillAccounInfo temp = billAccounInfoService.queryMaxBillAccounInfo(info.getSaleCorp().getId(), info.getBuyerCorp().getId(), "F");
                    if (temp != null) {
                        billAccounInfo.setBalanceTaxMoney(CmsUtils.addDouble(temp.getBalanceTaxMoney(), info.getBillMoney()));//含税余额
                        billAccounInfo.setBalanceMoney(CmsUtils.addDouble(temp.getBalanceMoney(), info.getAmountMoney()));//不含税余额
                    } else {
                        billAccounInfo.setBalanceTaxMoney(info.getBillMoney());
                        billAccounInfo.setBalanceMoney(info.getAmountMoney());
                    }
                    billAccounInfoService.addBillAccounInfo(billAccounInfo);
                }
                return msg;
            } else {
                return new Message(0, "该单据不存在或已被删除");
            }

        } catch (Exception e) {
            return new Message(0, e.getMessage());
        }
    }

    /**
     * 模板导入页
     *
     * @return
     * @throws JsonProcessingException
     */
    @GetMapping("importUpload")
    @AuthMethod(role = "ROLE_CONFIRM")
    public ModelAndView importUpload() throws JsonProcessingException {
        return new ModelAndView("confirm/import");
    }

    /**
     * 模板下载
     *
     * @param response
     * @throws IOException
     */
    @AuthMethod(role = "ROLE_CONFIRM")
    @RequestMapping("downloadExcel")
    public void downloadExcel(HttpServletResponse response, HttpSession session) throws IOException {
        //需要导出的数据列表。
        User user = (User) session.getAttribute("user");
        List<BillConfirmInfoVo> list = new ArrayList<>();
        BillConfirmInfoVo vo = new BillConfirmInfoVo();
        vo.setSaleCorpName("销方编号或名称（必填项）");
        vo.setBuyerCorpName("购方编号或名称（必填项）");
        vo.setApplyDeptBH("申请部门名称或编号(必填项)");
        vo.setSetllDeptBH("结算部门编号，多个以';'隔开,不填写默认为申请部门编号");
        vo.setTaxRate("默认为13");
        vo.setBillMoney("0.00");
        vo.setBillType("（必填项）");
        vo.setInvoiceDate("时间格式如：" + BeanUtil.formatDate(new Date(), "yyyy-MM-dd") + "，不填写默认当前时间");
        vo.setApplyUserBH("申请用户编号如：" + user.getAccount() + "，不填写默认为当前操作用户");
        vo.setRemarks("此行为说明行，导入数据前请删除");
        list.add(vo);
        ExcelUtils.exportExcel(list, null, "开票信息", BillConfirmInfoVo.class, "开票确认导入模板_" + BeanUtil.formatDate(new Date(), "yyyyMMdd"), response);
    }

    /**
     * 导出开票信息
     *
     * @param field
     * @param value
     * @param starDate
     * @param endDate
     * @param applyDeptBH
     * @param saleCorpID
     * @param buyerCorpID
     * @param response
     * @param session
     * @throws IOException
     */
    @AuthMethod(role = "ROLE_CONFIRM")
    @RequestMapping("exportDown")
    public void exportDown(String field, String value, String starDate, String endDate, String applyDeptBH, String saleCorpID, String buyerCorpID, HttpServletResponse response, String ztbz, HttpSession session) throws IOException {
        if ("billMoney".equals(field)) {
            if (!CmsUtils.isNumeric(value))
                throw new JsonException(500, "开票金额格式不合法");
        }
        if (starDate != null && !starDate.isEmpty())
            starDate = starDate + " 00:00:00";
        if (endDate != null && !endDate.isEmpty())
            endDate = endDate + " 23:59:59";
        User user = (User) session.getAttribute("user");
        String bookSet = user.getBookSet();
        List<BillConfirmInfoVo> list = billApplyInfoService.listBillConfirmInfoVo(field, value, starDate, endDate, ztbz, bookSet, applyDeptBH, saleCorpID, buyerCorpID);
        ExcelUtils.exportExcel(list, null, "开票信息", BillConfirmInfoVo.class, "客户开票信息_" + BeanUtil.formatDate(new Date(), "yyyyMMdd"), response);
    }

}
