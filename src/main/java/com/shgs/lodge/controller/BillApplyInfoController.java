package com.shgs.lodge.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.shgs.lodge.Vo.BillApplyInfoExportVo;
import com.shgs.lodge.Vo.BillApplyInfoImportVo;
import com.shgs.lodge.auth.AuthClass;
import com.shgs.lodge.auth.AuthMethod;
import com.shgs.lodge.dto.*;
import com.shgs.lodge.excel.ExcelUtils;
import com.shgs.lodge.exception.exception.PageException;
import com.shgs.lodge.primary.entity.*;
import com.shgs.lodge.service.*;
import com.shgs.lodge.util.*;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 开票申请视图接口层
 *
 * @author 雷智荣
 */

@RestController
@RequestMapping("/apply/")
@Scope("prototype")
@AuthClass("login")
public class BillApplyInfoController {


    private ITableHeaderService tableHeaderService;


    private IBillApplyInfoService billApplyInfoService;


    private ICustomService customService;


    private IDeptInfoService deptInfoService;


    private IBillCorpInfoService billCorpInfoService;


    private IAccounCodeService accounCodeService;


    private IAccounPeriodService accounPeriodService;


    private IBankAccountService bankAccountService;


    private IReveExpeItemService reveExpeItemService;


    private IPaymentMethodService paymentMethodService;


    private IAncillaryProjectsService ancillaryProjectsService;

    @Autowired
    public void setTableHeaderService(ITableHeaderService tableHeaderService) {
        this.tableHeaderService = tableHeaderService;
    }

    @Autowired
    public void setBillApplyInfoService(IBillApplyInfoService billApplyInfoService) {
        this.billApplyInfoService = billApplyInfoService;
    }

    @Autowired
    public void setCustomService(ICustomService customService) {
        this.customService = customService;
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
    public void setAccounCodeService(IAccounCodeService accounCodeService) {
        this.accounCodeService = accounCodeService;
    }

    @Autowired
    public void setAccounPeriodService(IAccounPeriodService accounPeriodService) {
        this.accounPeriodService = accounPeriodService;
    }

    @Autowired
    public void setBankAccountService(IBankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @Autowired
    public void setReveExpeItemService(IReveExpeItemService reveExpeItemService) {
        this.reveExpeItemService = reveExpeItemService;
    }

    @Autowired
    public void setPaymentMethodService(IPaymentMethodService paymentMethodService) {
        this.paymentMethodService = paymentMethodService;
    }

    @Autowired
    public void setAncillaryProjectsService(IAncillaryProjectsService ancillaryProjectsService) {
        this.ancillaryProjectsService = ancillaryProjectsService;
    }

    /**
     * 首页
     *
     * @param model
     * @param session
     * @return
     * @throws JsonProcessingException
     */
    @AuthMethod(role = "ROLE_APPLY")
    @GetMapping("index")
    public ModelAndView index(Model model, HttpSession session) throws JsonProcessingException {
        String endDate = CmsUtils.getNowDate();
        String starDate = BeanUtil.addDayStart(BeanUtil.formatDate(endDate), -31);
        String month = "";
        AccounPeriod accounPeriod = accounPeriodService.getNowMonth();
        if (accounPeriod != null) {
            endDate = BeanUtil.timestampToStr(accounPeriod.getEndTime(), "yyyy-MM-dd");
            starDate = BeanUtil.timestampToStr(accounPeriod.getStartTime(), "yyyy-MM-dd");
            month = accounPeriod.getMonth();
        }

        User user = (User) session.getAttribute("user");
        List<HeaderColumns> columns = tableHeaderService.listHeaderColumns(user.getId(), "applyGrid", "com.shgs.lodge.dto.BillApplyInfoDto");
        List<HeaderColumns> history = tableHeaderService.listHeaderColumns(user.getId(), "applyHistoryGrid", "com.shgs.lodge.dto.BillApplyHistoryInfoDto");
        model.addAttribute("columns", columns);
        model.addAttribute("history", history);
        model.addAttribute("starDate", starDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("month", month);
        model.addAttribute("applyDepts", deptInfoService.listUserByDeptIDS(user.getManageDept()));
        return new ModelAndView("apply/index");
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
    @AuthMethod(role = "ROLE_APPLY")
    @RequestMapping("list")
    public Pager<BillApplyInfoDto> list(String order, String sort, int page, int rows, String field, String value, HttpSession session) {
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
        User user = (User) session.getAttribute("user");
        String bookSet = user.getBookSet();
        String userBh = user.isAdmin() ? null : user.getAccount();
        SystemContext.setPageSize(rows);
        SystemContext.setPageNumber(page);
        SystemContext.setOrder(order);
        SystemContext.setSort(sort);
        return billApplyInfoService.listBillApplyInfoDto(field, value, "DF", bookSet, userBh);
    }

    /**
     * 获取历史数据
     *
     * @param order
     * @param sort
     * @param page
     * @param rows
     * @param field
     * @param value
     * @param starDate
     * @param endDate
     * @param ztbz
     * @param session
     * @return
     */
    @AuthMethod(role = "ROLE_APPLY")
    @RequestMapping("listHistoryInfo")
    public Pager<BillApplyHistoryInfoDto> listHistoryInfo(String order, String sort, int page, int rows, String field, String value, String starDate, String endDate, String ztbz, HttpSession session) {
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
        List<String> _ztbz = new ArrayList<>();
        if (ztbz != null && !ztbz.isEmpty())
            _ztbz = CmsUtils.string2Array(ztbz, ",");
        if (starDate != null && !starDate.isEmpty())
            starDate = starDate + " 00:00:00";
        if (endDate != null && !endDate.isEmpty())
            endDate = endDate + " 23:59:59";
        User user = (User) session.getAttribute("user");
        String bookSet = user.getBookSet();
        String userBh = user.isAdmin() ? null : user.getAccount();
        SystemContext.setPageSize(rows);
        SystemContext.setPageNumber(page);
        SystemContext.setOrder(order);
        SystemContext.setSort(sort);
        return billApplyInfoService.listHistoryInfoDto(field, value, starDate, endDate, _ztbz, bookSet, userBh);
    }

    /**
     * 添加开票申请单
     *
     * @param id
     * @param model
     * @return
     * @throws JsonProcessingException
     */
    @AuthMethod(role = "ROLE_APPLY")
    @GetMapping("dialog")
    public ModelAndView dialog(String id, String cation, Model model, HttpSession session) throws JsonProcessingException {
        User user = (User) session.getAttribute("user");
        String orderNum = billApplyInfoService.getOrderCode(user.getBookSet(), "TG", "DEPTBH", "CORPBH");
        BillApplyInfoDto billApplyInfoDto = new BillApplyInfoDto();
        List<SelectJson> billType = customService.listCustomParame("FPTYPE", null, true);
        if (id != null && !id.isEmpty()) {
            BillApplyInfo info = billApplyInfoService.queryBillApplyInfoById(id);
            billApplyInfoDto = new BillApplyInfoDto(info);
        } else {
            billApplyInfoDto.setPeriodMonth(accounPeriodService.getMonth());
            billApplyInfoDto.setOnAccount("F");
            billApplyInfoDto.setApplyUserName(user.getUserName());
        }
        if ("copy".equals(cation)) {
            billApplyInfoDto.setId(null);
            billApplyInfoDto.setApplyBH(null);
            billApplyInfoDto.setPeriodMonth(accounPeriodService.getMonth());
            billApplyInfoDto.setApplyUserName(user.getUserName());
            billApplyInfoDto.setBillNumber(null);
            billApplyInfoDto.setBillCode(null);
            billApplyInfoDto.setDrawerUser(null);
            billApplyInfoDto.setJxsdaName(null);
        }
        model.addAttribute("billApplyInfoDto", billApplyInfoDto);
        model.addAttribute("billType", billType);
        model.addAttribute("applyDept", deptInfoService.listUserByDeptIDS(user.getManageDept()));
        model.addAttribute("setllDept", deptInfoService.listUserByDeptIDS(user.getBalanceDept()));
        return new ModelAndView("apply/dialog");
    }

    /**
     * 获取往来客户跳转页
     *
     * @param keyword
     * @param corpType
     * @param model
     * @return
     */
    @AuthMethod(role = "ROLE_APPLY")
    @GetMapping("getCorpInfo")
    public ModelAndView getCorpInfo(String keyword, String corpType, Model model) {
        model.addAttribute("keyword", keyword);
        model.addAttribute("corpType", corpType);
        return new ModelAndView("apply/getCorpInfo");
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
    @AuthMethod(role = "ROLE_APPLY")
    @RequestMapping("getCorpList")
    public Pager<BillCorpInfo> getCorpList(String order, String sort, int page, int rows, String keyword, String corpType) {
        SystemContext.setPageSize(rows);
        SystemContext.setPageNumber(page);
        SystemContext.setOrder(order);
        SystemContext.setSort(sort);
        return billCorpInfoService.listBillCorpInfo(corpType, keyword, "T");
    }

    @AuthMethod(role = "ROLE_APPLY")
    @PostMapping("saveApplyInfo")
    public Message saveApplyInfo(@Validated BillApplyInfoDto dto, BindingResult br, HttpSession session) {
        if (br.hasErrors()) {
            return new Message(0, Objects.requireNonNull(br.getFieldError()).getDefaultMessage());
        }
        try {
            User user = (User) session.getAttribute("user");
            String id = dto.getId();
            if (id == null || id.isEmpty()) {
                BillApplyInfo info = new BillApplyInfo();
                info.setApplyDate(BeanUtil.strToTimestampTime(CmsUtils.getNowDate()));
                info.setTaxRate(dto.getTaxRate());
                info.setSaleKhyh(dto.getSaleKhyh());
                info.setSaleYhzh(dto.getSaleYhzh());
                info.setBuyKhyh(dto.getBuyKhyh());
                info.setBuyYhzh(dto.getBuyYhzh());
                info.setBillCode(dto.getBillCode());
                info.setBillNumber(dto.getBillNumber());
                info.setTaxMoney(dto.getTaxMoney());
                info.setAmountMoney(dto.getAmountMoney());
                info.setBalanceMoney(dto.getBillMoney());
                info.setProceedId(dto.getProceedId());
                info.setProceedName(dto.getProceedName());
                info.setOnAccount(dto.getOnAccount());
                info.setPaymentMethod(dto.getPaymentMethod());
                info.setPeriodMonth(dto.getPeriodMonth());
                info.setRemarks(dto.getRemarks());
                info.setBillMoney(dto.getBillMoney());
                info.setZtbz("DF");
                info.setBookSet(user.getBookSet());
                info.setDescribe(dto.getDescribe());
                info.setApplyUserBH(user.getAccount());
                info.setApplyUserName(user.getUserName());
                info.setrVTime(CmsUtils.getTimeMillis());
                List<String> setll = CmsUtils.string2Array(dto.getSetllDeptBH(), ",");
                List<String> _setll = new ArrayList<>();
                for (String str : setll) {
                    if (str != null && !str.isEmpty())
                        _setll.add(str);
                }
                info.setSetllDeptBH(String.join(";", _setll));
                info.setBillType(dto.getBillType());
                if (dto.getBuyerCorpID() != null && !dto.getBuyerCorpID().isEmpty()) {
                    BillCorpInfo buyerCorp = billCorpInfoService.queryBillCorpInfoById(dto.getBuyerCorpID());
                    if (buyerCorp != null) {
                        info.setBuyerCorp(buyerCorp);
                    }
                }
                if (StringUtils.isNotEmpty(dto.getApplyDeptBH())) {
                    DeptInfo deptInfo = deptInfoService.queryByDeptID(dto.getApplyDeptBH());
                    if (deptInfo != null) {
                        info.setApplyDept(deptInfo);
                    }
                }
                if (dto.getSaleCorpID() != null && !dto.getSaleCorpID().isEmpty()) {
                    BillCorpInfo saleCorp = billCorpInfoService.queryBillCorpInfoById(dto.getSaleCorpID());
                    if (saleCorp != null)
                        info.setSaleCorp(saleCorp);
                }
                return billApplyInfoService.saveBillApplyInfo(info);

            } else {
                BillApplyInfo info = billApplyInfoService.queryBillApplyInfoById(id);
                if (info != null) {
                    String proccedID = info.getProceedId();
                    info.setApplyDate(BeanUtil.strToTimestampTime(CmsUtils.getNowDate()));
                    info.setTaxRate(dto.getTaxRate());
                    info.setSaleKhyh(dto.getSaleKhyh());
                    info.setSaleYhzh(dto.getSaleYhzh());
                    info.setBuyKhyh(dto.getBuyKhyh());
                    info.setBuyYhzh(dto.getBuyYhzh());
                    info.setBillCode(dto.getBillCode());
                    info.setBillNumber(dto.getBillNumber());
                    info.setTaxRate(dto.getTaxRate());
                    info.setTaxMoney(dto.getTaxMoney());
                    info.setAmountMoney(dto.getAmountMoney());
                    info.setBalanceMoney(dto.getBillMoney());
                    info.setProceedId(dto.getProceedId());
                    info.setProceedName(dto.getProceedName());
                    info.setOnAccount(dto.getOnAccount());
                    info.setPaymentMethod(dto.getPaymentMethod());
                    info.setPeriodMonth(dto.getPeriodMonth());
                    info.setRemarks(dto.getRemarks());
                    info.setBillMoney(dto.getBillMoney());
                    info.setZtbz("DF");
                    info.setBookSet(user.getBookSet());
                    info.setApplyUserBH(user.getAccount());
                    info.setApplyUserName(user.getUserName());
                    info.setrVTime(CmsUtils.getTimeMillis());
                    info.setDescribe(dto.getDescribe());
                    info.setBillType(dto.getBillType());
                    if (dto.getBuyerCorpID() != null && !dto.getBuyerCorpID().isEmpty()) {
                        BillCorpInfo buyerCorp = billCorpInfoService.queryBillCorpInfoById(dto.getBuyerCorpID());
                        if (buyerCorp != null) {
                            info.setBuyerCorp(buyerCorp);
                        }
                    }
                    if (StringUtils.isNotEmpty(dto.getApplyDeptBH())) {
                        DeptInfo deptInfo = deptInfoService.queryByDeptID(dto.getApplyDeptBH());
                        if (deptInfo != null) {
                            info.setApplyDept(deptInfo);
                        }
                    }
                    if (dto.getSaleCorpID() != null && !dto.getSaleCorpID().isEmpty()) {
                        BillCorpInfo saleCorp = billCorpInfoService.queryBillCorpInfoById(dto.getSaleCorpID());
                        if (saleCorp != null)
                            info.setSaleCorp(saleCorp);
                    }
                    return billApplyInfoService.updateBillApplyInfo(info, proccedID);
                } else {
                    return new Message(0, "该单据不存在或已被删除");
                }
            }
        } catch (Exception e) {
            return new Message(0, e.getMessage());
        }
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AuthMethod(role = "ROLE_APPLY")
    @PostMapping("batchDeleteApply")
    public Message batchDeleteApply(String ids) {
        return billApplyInfoService.batchDeleteApply(ids);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @AuthMethod(role = "ROLE_APPLY")
    @PostMapping("deleteApplyInfo")
    public Message deleteApplyInfo(String id) {
        return billApplyInfoService.deleteBillApplyInfo(id);
    }

    /**
     * 模板导入页
     *
     * @param model
     * @return
     * @throws JsonProcessingException
     */
    @GetMapping("importUpload")
    @AuthMethod(role = "ROLE_APPLY")
    public ModelAndView importUpload(Model model) throws JsonProcessingException {
        return new ModelAndView("apply/import");
    }

    /**
     * 模板下载
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @AuthMethod(role = "ROLE_APPLY")
    @RequestMapping("downloadExcel")
    public void downloadExcel(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        //需要导出的数据列表。
        List<BillApplyInfoImportVo> list = new ArrayList<>();
        BillApplyInfoImportVo vo = new BillApplyInfoImportVo();
        vo.setBillMoney("0.00");
        vo.setTaxRate("0");
        vo.setOnAccount("T=挂账;F=不挂账");
        list.add(vo);
        ExcelUtils.exportExcel(list, null, "开票信息", BillApplyInfoImportVo.class, "开票信息导入模板_" + BeanUtil.formatDate(new Date(), "yyyyMMdd"), response);
    }

    /**
     * 模板上传处理
     *
     * @param file
     * @param headerRows
     * @return
     * @throws Exception
     */
    @PostMapping(value = "importUpload", produces = "text/html;charset=utf-8")
    @AuthMethod(role = "ROLE_APPLY")
    public String importUpload(MultipartFile file, Integer headerRows) throws Exception {
        Message msg = new Message(0, "导入失败");
        if (file == null || file.isEmpty()) {
            msg.setMessage("上传文件不能为空，请重新选择模板文件，然后重试。");
            return JsonUtil.objectToString(msg);
        }
        List<BillApplyInfoImportVo> vo;
        List<BillApplyInfoImportVo> list = ExcelUtils.importExcel(file, 0, headerRows, BillApplyInfoImportVo.class);
        if (list != null && list.size() > 0) {
            vo = list;
            msg.setCode(1);
            msg.setMessage("导入成功");
            msg.setData(vo);
        }
        return JsonUtil.objectToString(msg);
    }

    /**
     * 导出数据
     *
     * @param field
     * @param value
     * @param starDate
     * @param endDate
     * @param ztbz
     * @param request
     * @param response
     * @throws IOException
     */
    @AuthMethod(role = "ROLE_APPLY")
    @RequestMapping("exportDown")
    public void exportDown(String field, String value, String starDate, String endDate, String ztbz, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        //需要导出的数据列表。
        if (starDate != null && !starDate.isEmpty())
            starDate = starDate + " 00:00:00";
        if (endDate != null && !endDate.isEmpty())
            endDate = endDate + " 23:59:59";
        try {
            User user = (User) session.getAttribute("user");
            String bookSet = user.getBookSet();
            String userBh = user.isAdmin() ? null : user.getAccount();
            List<BillApplyInfo> mast = billApplyInfoService.exportBillApplyInfo(field, value, starDate, endDate, ztbz, bookSet, userBh);
            List<BillApplyInfoExportVo> list = new BillApplyInfoExportVo().listBillApplyInfoExportVo(mast);
            ExcelUtils.exportExcel(list, "开票信息", "开票信息", BillApplyInfoExportVo.class, "客户开票信息", response);
        } catch (Exception e) {
            throw new PageException(403, e.getMessage());
        }

    }

    /**
     * 检测并保存模板数据，返回错误数据
     *
     * @param rows
     * @return
     */
    @AuthMethod(role = "ROLE_APPLY")
    @PostMapping(value = "batchsaveExcel")
    public Message batchsaveExcel(String rows, HttpSession session) {
        User user = (User) session.getAttribute("user");
        //管理部门
        List<String> manageDept = CmsUtils.string2Array(user.getManageDept(), ";");
        //结算部门
        List<String> balanceDept = CmsUtils.string2Array(user.getBalanceDept(), ";");
        if (rows != null && !rows.isEmpty()) {
            rows = CmsUtils.decryptBASE64(rows);
            JSONArray jsonArray = JSONObject.parseArray(rows);
            List<BillApplyInfoImportVo> dtos = JSONArray.parseArray(jsonArray.toString(), BillApplyInfoImportVo.class);
            List<BillApplyInfoImportVo> array = new ArrayList<>();
            List<BillApplyInfo> listInfo = new ArrayList<>();
            String month = accounPeriodService.getMonth();
            if (dtos.size() > 0) {
                int orderNum = billApplyInfoService.getMaxOrders();
                AccounCode accounCode = accounCodeService.queryAccounCode(user.getBookSet(), "TG");
                for (BillApplyInfoImportVo temp : dtos) {
                    List<String> err = new ArrayList<>();
                    BillApplyInfo billApplyInfo = new BillApplyInfo();
                    BillApplyInfoImportVo mast = new BillApplyInfoImportVo().formatBillApplyInfoDto(temp);
                    //检测申请部门
                    if (mast.getApplyDept() == null || mast.getApplyDept().isEmpty()) {
                        err.add("【申请部门】不能为空");
                    } else {
                        DeptInfo deptInfo = deptInfoService.queryByDeptDeptIdORName(mast.getApplyDept());
                        if (deptInfo != null) {
                            if (!manageDept.contains(deptInfo.getDeptID())) {
                                err.add("申请部门【" + mast.getApplyDept() + "】不在授权范围内");
                            } else {
                                billApplyInfo.setApplyDept(deptInfo);
                                billApplyInfo.setSetllDeptBH(deptInfo.getDeptID());
                            }
                        } else {
                            err.add("申请部门【" + mast.getApplyDept() + "】不存在");
                        }
                    }
                    //检测购买方
                    if (mast.getBuyerCorp() == null || mast.getBuyerCorp().isEmpty()) {
                        err.add("【购买方】不能为空");
                    } else {
                        BillCorpInfo billCorpInfo = billCorpInfoService.queryBillCorpInfoByCorpBM(mast.getBuyerCorp());
                        if (billCorpInfo != null) {
                            billApplyInfo.setBuyerCorp(billCorpInfo);
                            //检测购买方银行账号
                            if (StringUtils.isNotEmpty(mast.getBuyKhyh()) && StringUtils.isNotEmpty(mast.getBuyYhzh())) {
                                BankAccount bankAccount = bankAccountService.queryBankAccount(billCorpInfo.getId(), mast.getBuyKhyh(), mast.getBuyYhzh());
                                if (bankAccount != null) {
                                    billApplyInfo.setBuyYhzh(bankAccount.getYhzh());
                                    billApplyInfo.setBuyKhyh(bankAccount.getKhyh());
                                } else {
                                    err.add("销售方【" + mast.getBuyKhyh() + " " + mast.getBuyYhzh() + "】不存在");
                                }
                            } else {
                                BankAccount bankAccount = bankAccountService.queryBankAccountByCorpId(billCorpInfo.getId());
                                if (bankAccount != null) {
                                    billApplyInfo.setBuyYhzh(bankAccount.getYhzh());
                                    billApplyInfo.setBuyKhyh(bankAccount.getKhyh());
                                }
                            }

                        } else {
                            err.add("购买方【" + mast.getBuyerCorp() + "】不存在");
                        }
                    }

                    //检测销售方
                    if (mast.getSaleCorp() == null || mast.getSaleCorp().isEmpty()) {
                        err.add("【销售方】不能为空");
                    } else {
                        BillCorpInfo billCorpInfo = billCorpInfoService.queryBillCorpInfoByCorpBM(mast.getSaleCorp());
                        if (billCorpInfo != null) {
                            billApplyInfo.setSaleCorp(billCorpInfo);
                            //检测销售方银行账号
                            if (StringUtils.isNotEmpty(mast.getSaleKhyh()) && StringUtils.isNotEmpty(mast.getSaleYhzh())) {
                                BankAccount bankAccount = bankAccountService.queryBankAccount(billCorpInfo.getId(), mast.getSaleKhyh(), mast.getSaleYhzh());
                                if (bankAccount != null) {
                                    billApplyInfo.setSaleYhzh(bankAccount.getYhzh());
                                    billApplyInfo.setSaleKhyh(bankAccount.getKhyh());
                                } else {
                                    err.add("销售方【" + mast.getSaleKhyh() + " " + mast.getSaleYhzh() + "】不存在");
                                }
                            } else {
                                BankAccount bankAccount = bankAccountService.queryBankAccountByCorpId(billCorpInfo.getId());
                                if (bankAccount != null) {
                                    billApplyInfo.setSaleYhzh(bankAccount.getYhzh());
                                    billApplyInfo.setSaleKhyh(bankAccount.getKhyh());
                                } else {
                                    err.add("销售方【开户银行、银行账号】不能为空");
                                }
                            }
                        } else {
                            err.add("销售方【" + mast.getSaleCorp() + "】不存在");
                        }
                    }
                    //检测费项名称
                    if (StringUtils.isNotEmpty(mast.getProceed())) {
                        ProceedType proceedType = reveExpeItemService.queryProceedTypeByNameAndProceedBh(mast.getProceed());
                        if (proceedType != null) {
                            billApplyInfo.setProceedId(proceedType.getId());
                            billApplyInfo.setProceedName(proceedType.getName());
                        } else {
                            err.add("费项【" + mast.getProceed() + "】不存在");
                        }
                    } else {
                        err.add("【费项名称】不能为空");
                    }
                    //检测结算方式
                    if (StringUtils.isNotEmpty(mast.getPaymentMethod())) {
                        PaymentMethod paymentMethod = paymentMethodService.queryByPaymentBh(mast.getPaymentMethod());
                        if (paymentMethod != null) {
                            billApplyInfo.setPaymentMethod(paymentMethod.getPaymentName());
                        } else {
                            err.add("结算方式【" + mast.getPaymentMethod() + "】不存在");
                        }

                    } else {
                        err.add("【结算方式】不能为空");
                    }
                    //检测发票类型
                    if (mast.getBillType() == null || mast.getBillType().isEmpty()) {
                        err.add("【发票类型】不能为空");
                    } else {
                        CustomParame customParame = customService.queryCustomParame("FPTYPE", mast.getBillType());
                        if (customParame != null) {
                            billApplyInfo.setBillType(mast.getBillType());
                        } else {
                            err.add("发票类型【" + mast.getBillType() + "】不存在");
                        }
                    }
                    //检测发票金额
                    if (mast.getBillMoney() == null || mast.getBillMoney().isEmpty()) {
                        err.add("【发票金额】不能为空");
                    } else {
                        if (CmsUtils.isNumeric(mast.getBillMoney())) {
                            billApplyInfo.setBillMoney(Double.valueOf(mast.getBillMoney()));
                        } else {
                            err.add("【发票金额】不合法");
                        }
                    }
                    //检测税率
                    if (mast.getTaxRate() == null || mast.getTaxRate().isEmpty()) {
                        err.add("【税率】不能为空");
                    } else {
                        if (CmsUtils.isNumeric(mast.getTaxRate())) {
                            billApplyInfo.setTaxRate(Double.valueOf(mast.getTaxRate()));
                        } else {
                            err.add("【税率】不合法");
                        }
                    }
                    if (err.size() > 0) {
                        mast.setDescribe(String.join("；", err));
                        mast.setCheckStr("F");
                        array.add(mast);
                    } else {
                        billApplyInfo.setBookSet(user.getBookSet());
                        orderNum = orderNum + 1;
                        billApplyInfo.setOnAccount("T".equals(mast.getOnAccount()) ? "T" : "F");
                        String applyBH = CmsUtils.getAccounCode(accounCode, billApplyInfo.getApplyDept().getDeptID(), billApplyInfo.getBuyerCorp().getCorpBM(), orderNum);
                        billApplyInfo.setApplyBH(applyBH);
                        billApplyInfo.setOrders(orderNum);
                        billApplyInfo.setTaxMoney(CmsUtils.mul(billApplyInfo.getBillMoney(), billApplyInfo.getTaxRate()) / 100);
                        billApplyInfo.setAmountMoney(CmsUtils.subtract(billApplyInfo.getBillMoney(), billApplyInfo.getTaxMoney()));
                        billApplyInfo.setBalanceMoney(billApplyInfo.getBillMoney());
                        billApplyInfo.setRemarks(mast.getRemarks());
                        billApplyInfo.setBookSet(user.getBookSet());
                        billApplyInfo.setApplyUserBH(user.getAccount());
                        billApplyInfo.setApplyUserName(user.getUserName());
                        billApplyInfo.setBillCode(mast.getBillCode());
                        billApplyInfo.setBillNumber(mast.getBillNumber());
                        billApplyInfo.setApplyDate(BeanUtil.strToTimestampTime(CmsUtils.getNowDate()));
                        billApplyInfo.setrVTime(CmsUtils.getTimeMillis());
                        billApplyInfo.setZtbz("DF");
                        billApplyInfo.setPeriodMonth(month);
                        billApplyInfo.setApplyUserBH(user.getAccount());
                        billApplyInfo.setDescribe(mast.getSummary());
                        listInfo.add(billApplyInfo);
                    }
                }
                billApplyInfoService.batchSaveBillApplyInfo(listInfo);
                return new Message(1, "检测完成", array);
            } else {
                return new Message(0, "数据解析错误，请联系管理员");
            }
        }
        return new Message(0, "检测数据为空");
    }


    /**
     * 审核提交
     *
     * @param ids
     * @return
     */
    @AuthMethod(role = "ROLE_APPLY")
    @PostMapping("batchExamine")
    public Message batchExamine(String ids) {
        return billApplyInfoService.batchExamine(ids, "DT");
    }


    /**
     * 按客商编号获取银行列表
     *
     * @param corpId
     * @return
     */
    @AuthMethod(role = "ROLE_APPLY")
    @PostMapping("listBankAccount")
    public List<BankAccount> listBankAccount(String corpId) {

        return bankAccountService.listBankAccount(corpId, "T");
    }

    /**
     * 获取收支费项
     *
     * @param value
     * @param model
     * @return
     */
    @AuthMethod(role = "ROLE_APPLY")
    @GetMapping("getProceed")
    public ModelAndView getProceed(String value, Model model) {
        model.addAttribute("proceedValue", value);
        return new ModelAndView("apply/getProceed");
    }

    /**
     * 查询收支费项信息
     *
     * @param pid
     * @param keyword 过滤关键字
     * @return
     */
    @AuthMethod(role = "ROLE_APPLY")
    @RequestMapping("findProceed")
    public List<ProceedTypeListDto> findProceed(String pid, String keyword) {
        return reveExpeItemService.listProceedTypeListDto(pid, keyword);
    }

    /**
     * 获取结算方式
     *
     * @param value
     * @param model
     * @return
     */
    @AuthMethod(role = "ROLE_APPLY")
    @GetMapping("getPayment")
    public ModelAndView getPayment(String value, Model model) {
        model.addAttribute("paymentValue", value);
        return new ModelAndView("apply/getPayment");
    }

    /**
     * 查询结算方式
     *
     * @param pid
     * @param keyword 过滤关键字
     * @return
     */
    @AuthMethod(role = "ROLE_APPLY")
    @RequestMapping("findPayment")
    public List<PaymentMethodListDto> findPayment(String pid, String keyword) {
        return paymentMethodService.listPaymentMethodDto(pid, keyword);
    }

    @AuthMethod(role = "ROLE_APPLY")
    @RequestMapping("findProjects")
    public List<AncillaryProjectsListDto> findProjects(String pid, String keyword, String t_id) {
        return ancillaryProjectsService.listAncillaryProjectsDto(pid, keyword, t_id);
    }

    /**
     * 辅助项目选择器
     *
     * @param model
     * @return
     */
    @AuthMethod(role = "ROLE_APPLY")
    @GetMapping("getProjects")
    public ModelAndView getProjects(String value, Model model) {
        model.addAttribute("projectType", value);
        return new ModelAndView("reve/getProjects");
    }

    /**
     * 获取商家银行账号
     *
     * @param action
     * @param model
     * @param corpId
     * @return
     */
    @AuthMethod(role = "ROLE_APPLY")
    @GetMapping("getBankAccount")
    public ModelAndView getBankAccount(String action, String corpId, Model model) {
        model.addAttribute("bankAccountValue", action);
        List<BankAccountListDto> bankAccountListDtoList = bankAccountService.listBankAccountListDto(corpId);
        model.addAttribute("bankAccountListDtoList", bankAccountListDtoList);
        return new ModelAndView("apply/getBankAccount");
    }

}
