package com.shags.lodge.controller.lodge;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.shags.lodge.Vo.BillCorpInfoVo;
import com.shags.lodge.auth.AuthClass;
import com.shags.lodge.auth.AuthMethod;
import com.shags.lodge.dto.HeaderColumns;
import com.shags.lodge.dto.User;
import com.shags.lodge.excel.ExcelUtils;
import com.shags.lodge.util.*;
import com.shags.lodge.exception.exception.PageException;
import com.shags.lodge.primary.entity.BankAccount;
import com.shags.lodge.primary.entity.BillCorpInfo;
import com.shags.lodge.service.primary.IBankAccountService;
import com.shags.lodge.service.primary.IBillCorpInfoService;
import com.shags.lodge.service.primary.ICustomService;
import com.shags.lodge.service.primary.ITableHeaderService;
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

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/corp/")
@Scope("prototype")
@AuthClass("login")
public class BillCorpInfoController {


    private IBillCorpInfoService billCorpInfoService;


    private ITableHeaderService tableHeaderService;


    private ICustomService customService;


    private IBankAccountService bankAccountService;

    @Autowired
    public void setBillCorpInfoService(IBillCorpInfoService billCorpInfoService) {
        this.billCorpInfoService = billCorpInfoService;
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
    public void setBankAccountService(IBankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @AuthMethod(role = "ROLE_CORP")
    @GetMapping("index")
    public ModelAndView index(Model model, HttpSession session) throws JsonProcessingException {
        User user = (User) session.getAttribute("user");
        List<HeaderColumns> columns = tableHeaderService.listHeaderColumns(user.getId(), "corpGrid", "com.shags.lodge.primary.entity.BillCorpInfo");
        List<SelectJson> corpType = customService.listCustomParameByCode("CORPTYPE");
        corpType.add(0, new SelectJson("", "-????????????", "all"));
        model.addAttribute("columns", columns);
        model.addAttribute("corpType", corpType);
        return new ModelAndView("corp/index");
    }

    @AuthMethod(role = "ROLE_CORP")
    @RequestMapping("list")
    public Pager<BillCorpInfo> list(String order, String sort, int page, int rows, String keyword, String corpType) {
        SystemContext.setPageSize(rows);
        SystemContext.setPageNumber(page);
        SystemContext.setOrder(order);
        SystemContext.setSort(sort);
        if ("all".equals(corpType))
            corpType = null;
        return billCorpInfoService.listBillCorpInfo(corpType, keyword, null);
    }

    @AuthMethod(role = "ROLE_CORP")
    @GetMapping("dialog")
    public ModelAndView dialog(String id, Model model) throws JsonProcessingException {
        BillCorpInfo billCorpInfo = new BillCorpInfo();
        List<SelectJson> coryType = customService.listCustomParameByCode("CORPTYPE");
        if (id != null && !id.isEmpty()) {
            billCorpInfo = billCorpInfoService.queryBillCorpInfoById(id);
        } else {
            billCorpInfo.setStatus(1);
        }
        List<HeaderColumns> bankColumns = CmsUtils.getHeaderColumns("com.shags.lodge.primary.entity.BankAccount");
        model.addAttribute("billCorpInfo", billCorpInfo);
        model.addAttribute("corpType", coryType);
        model.addAttribute("bankColumns", bankColumns);
        return new ModelAndView("corp/dialog");
    }

    /**
     * ??????????????????
     *
     * @param billCorpInfo
     * @param br
     * @param session
     * @return
     */
    @AuthMethod(role = "ROLE_CORP")
    @PostMapping("saveCorpInfo")
    public Message saveCorpInfo(@Validated BillCorpInfo billCorpInfo, BindingResult br, String bankAccount, HttpSession session) {
        if (br.hasErrors()) {
            return new Message(0, Objects.requireNonNull(br.getFieldError()).getDefaultMessage());
        }
        try {
            User user = (User) session.getAttribute("user");
            String id = billCorpInfo.getId();
            if (id == null || billCorpInfo.getId().isEmpty()) {
                //??????
                billCorpInfo.setId(null);
                billCorpInfo.setUserID(user.getAccount());
                billCorpInfo.setZtrq(BeanUtil.strToTimestampTime(CmsUtils.getNowDate()));
                Message msg = billCorpInfoService.saveBillCorpInfo(billCorpInfo);
                if (msg.getCode() == 1) {
                    if (StringUtils.isNotEmpty(bankAccount)) {
                        bankAccount = CmsUtils.decryptBASE64(bankAccount);
                        JSONArray jsonArray = JSONObject.parseArray(bankAccount);
                        List<BankAccount> bankAccountList = JSONArray.parseArray(jsonArray.toString(), BankAccount.class);
                        if (bankAccountList != null && bankAccountList.size() > 0) {
                            List<BankAccount> list = new ArrayList<>();
                            for (BankAccount cts : bankAccountList) {
                                list.add(new BankAccount((String) msg.getData(), cts.getKhyh(), cts.getYhzh(), cts.getZtbz()));
                            }
                            bankAccountService.batchSaveBankAccount(list);
                        }
                    }
                }
                return msg;
            } else {
                BillCorpInfo mast = billCorpInfoService.queryBillCorpInfoById(id);
                if (mast != null) {
                    mast.setStatus(billCorpInfo.getStatus());
                    mast.setCorpBM(billCorpInfo.getCorpBM());
                    mast.setCorpType(billCorpInfo.getCorpType());
                    mast.setDz(billCorpInfo.getDz());
                    mast.setLxdh(billCorpInfo.getLxdh());
                    mast.setRemark(billCorpInfo.getRemark());
                    mast.setCorpMC(billCorpInfo.getCorpMC());
                    mast.setNsrNum(billCorpInfo.getNsrNum());
                    mast.setUserID(user.getAccount());
                    return billCorpInfoService.updateBillCorpInfo(mast);
                } else {
                    return new Message(0, "?????????????????????????????????");
                }
            }
        } catch (Exception e) {
            return new Message(0, e.getMessage());
        }
    }

    /**
     * ???ID??????
     *
     * @param id
     * @return
     */
    @AuthMethod(role = "ROLE_CORP")
    @PostMapping("deleteCorpInfo")
    public Message deleteCorpInfo(String id) {
        return billCorpInfoService.deleteBillCorpInfo(id);
    }

    /**
     * ????????????
     *
     * @param ids
     * @return
     */
    @AuthMethod(role = "ROLE_CORP")
    @PostMapping("batchDeleteCorp")
    public Message batchDeleteCorp(String ids) {
        return billCorpInfoService.batchDeleteCorp(ids);

    }

    @GetMapping("importUpload")
    @AuthMethod(role = "ROLE_USER")
    public ModelAndView importUpload() throws JsonProcessingException {
        return new ModelAndView("corp/import");
    }

    /**
     * ??????????????????
     *
     * @param file
     * @param headerRows
     * @return
     * @throws Exception
     */
    @PostMapping(value = "importUpload", produces = "text/html;charset=utf-8")
    @AuthMethod(role = "ROLE_CORP")
    public String importUpload(MultipartFile file, Integer headerRows) throws Exception {
        Message msg = new Message(0, "????????????");
        if (file == null || file.isEmpty()) {
            msg.setMessage("????????????????????????????????????????????????????????????????????????");
            return JsonUtil.objectToString(msg);
        }
        List<BillCorpInfoVo> vo;
        List<BillCorpInfoVo> list = ExcelUtils.importExcel(file, 0, headerRows, BillCorpInfoVo.class);
        if (list != null && list.size() > 0) {
            vo = list;
            msg.setCode(1);
            msg.setMessage("????????????");
            msg.setData(vo);
        }
        return JsonUtil.objectToString(msg);
    }

    /**
     * ????????????
     *
     * @param response
     */
    @AuthMethod(role = "ROLE_CORP")
    @RequestMapping("downloadExcel")
    public void downloadExcel(HttpServletResponse response) throws IOException {
        //??????????????????????????????
        List<BillCorpInfoVo> list = new ArrayList<>();
        BillCorpInfoVo vo = new BillCorpInfoVo();
        vo.setCorpType("1=????????????0=?????????(?????????)");
        vo.setCorpBM("???????????????");
        vo.setCorpMC("???????????????");
        vo.setStatus("T=?????????F=???????????????????????????F");
        list.add(vo);
        ExcelUtils.exportExcel(list, null, "??????????????????", BillCorpInfoVo.class, "??????????????????????????????", response);
    }

    /**
     * ????????????????????????????????????????????????
     *
     * @param rows
     * @return
     */
    @AuthMethod(role = "ROLE_CORP")
    @PostMapping(value = "batchsaveExcel")
    public Message batchsaveExcel(String rows, Integer saveUpdate, HttpSession session) {
        if (rows != null && !rows.isEmpty()) {
            rows = CmsUtils.decryptBASE64(rows);
            JSONArray jsonArray = JSONObject.parseArray(rows);
            List<BillCorpInfoVo> dtos = JSONArray.parseArray(jsonArray.toString(), BillCorpInfoVo.class);
            List<BillCorpInfoVo> array = new ArrayList<>();
            List<BillCorpInfo> listBillCorpInfo = new ArrayList<>();
            List<BillCorpInfo> listUpdate = new ArrayList<>();
            List<BankAccount> addBankAccountData = new ArrayList<>();
            List<BankAccount> updateBankAccountData = new ArrayList<>();
            User user = (User) session.getAttribute("user");
            if (dtos.size() > 0) {
                for (BillCorpInfoVo mast : dtos) {
                    List<String> err = new ArrayList<>();
                    //??????????????????
                    if (mast.getCorpType() == null || mast.getCorpType().isEmpty()) {
                        err.add("??????????????????????????????");
                    } else {
                        List<SelectJson> list = customService.listCustomParame("CORPTYPE", mast.getCorpType(), true);
                        if (list == null || list.size() == 0) {
                            err.add("???????????????" + mast.getCorpType() + "????????????");
                        }
                    }
                    //??????????????????
                    if (StringUtils.isEmpty(mast.getStatus())) {
                        err.add("??????????????????????????????????????????T?????????F???");
                    }
                    //??????????????????
                    if (mast.getCorpBM() == null || mast.getCorpBM().isEmpty()) {
                        err.add("??????????????????????????????");
                    } else {
                        //????????????????????????????????????????????????????????????????????????
                        BillCorpInfo info = billCorpInfoService.queryBillCorpInfoByCorpBM(mast.getCorpBM());
                        if (info != null) {
                            if (saveUpdate == 0) {
                                err.add("???????????????" + mast.getCorpBM() + "????????????");
                            }
                            if (err.size() == 0) {
                                BillCorpInfo billCorpInfo = new BillCorpInfoVo().getBillCorpInfo(mast);
                                if (mast.getStatus() != null && !mast.getStatus().isEmpty())
                                    info.setStatus("T".equals(mast.getStatus()) ? 1 : 0);
                                if (billCorpInfo.getCorpType() != null && !billCorpInfo.getCorpType().isEmpty())
                                    info.setCorpType(billCorpInfo.getCorpType());
                                if (billCorpInfo.getDz() != null && !billCorpInfo.getDz().isEmpty())
                                    info.setDz(billCorpInfo.getDz());
                                if (billCorpInfo.getLxdh() != null && !billCorpInfo.getLxdh().isEmpty())
                                    info.setLxdh(billCorpInfo.getLxdh());
                                if (billCorpInfo.getRemark() != null && !billCorpInfo.getRemark().isEmpty())
                                    info.setRemark(billCorpInfo.getRemark());
                                if (billCorpInfo.getCorpMC() != null && !billCorpInfo.getCorpMC().isEmpty())
                                    info.setCorpMC(billCorpInfo.getCorpMC());
                                if (billCorpInfo.getNsrNum() != null && !billCorpInfo.getNsrNum().isEmpty())
                                    info.setNsrNum(billCorpInfo.getNsrNum());
                                billCorpInfo.setUserID(user.getAccount());
                                info.setZtrq(BeanUtil.strToTimestampTime(CmsUtils.getNowDate()));
                                listUpdate.add(info);
                                //??????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                                if (StringUtils.isNotEmpty(mast.getYhzh()) && StringUtils.isNotEmpty(mast.getKhyh())) {
                                    BankAccount bankAccount = bankAccountService.queryBankAccount(info.getId(), mast.getKhyh(), mast.getYhzh());
                                    if (bankAccount != null) {
                                        if (!mast.getYhzh().equals(bankAccount.getYhzh()) || !mast.getKhyh().equals(bankAccount.getKhyh())) {
                                            bankAccount.setYhzh(mast.getYhzh());
                                            bankAccount.setKhyh(mast.getKhyh());
                                            bankAccount.setZtbz(mast.getStatus());
                                            updateBankAccountData.add(bankAccount);
                                        }
                                    } else {
                                        BankAccount temp=new BankAccount();
                                        temp.setYhzh(mast.getYhzh());
                                        temp.setKhyh(mast.getKhyh());
                                        temp.setCorpId(info.getId());
                                        temp.setZtbz(mast.getStatus());
                                        addBankAccountData.add(temp);
                                    }
                                }
                                continue;
                            }
                        }
                    }
                    if (err.size() > 0) {
                        mast.setRemarks(String.join("???", err));
                        mast.setCheckStr("F");
                        array.add(mast);
                    } else {
                        BillCorpInfo billCorpInfo = new BillCorpInfoVo().getBillCorpInfo(mast);
                        billCorpInfo.setUserID(user.getAccount());
                        billCorpInfo.setKhyh(mast.getKhyh());
                        billCorpInfo.setYhzh(mast.getYhzh());
                        billCorpInfo.setZtrq(BeanUtil.strToTimestampTime(CmsUtils.getNowDate()));
                        billCorpInfo.setStatus("T".equals(mast.getStatus()) ? 1 : 0);
                        listBillCorpInfo.add(billCorpInfo);
                    }
                }
                //??????????????????
                if (listUpdate.size() > 0)
                    billCorpInfoService.batchUpdateBillCorpInfo(listUpdate);
                //??????????????????
                if (updateBankAccountData.size() > 0)
                    bankAccountService.batchUpdateBankAccount(updateBankAccountData);
                //???????????????????????????????????????
                if (listBillCorpInfo.size() > 0) {
                    for (BillCorpInfo billCorpInfo : listBillCorpInfo) {
                        Message msg = billCorpInfoService.saveBillCorpInfo(billCorpInfo);
                        if (msg.getCode() == 1) {
                            if (StringUtils.isNotEmpty(billCorpInfo.getYhzh()) && StringUtils.isNotEmpty(billCorpInfo.getKhyh())) {
                                String ztbz = billCorpInfo.getStatus() == 1 ? "T" : "F";
                                addBankAccountData.add(new BankAccount((String) msg.getData(), billCorpInfo.getKhyh(), billCorpInfo.getYhzh(), ztbz));
                            }
                        }
                    }
                }
                //??????????????????
                if (addBankAccountData.size() > 0)
                    bankAccountService.batchSaveBankAccount(addBankAccountData);
                return new Message(1, "????????????", array);
            } else {
                return new Message(0, "???????????????????????????????????????");
            }
        }
        return new Message(0, "??????????????????");
    }


    @AuthMethod(role = "ROLE_CORP")
    @RequestMapping("exportDown")
    public void exportDown(String keyword, String corpType, HttpServletResponse response) throws IOException {
        //??????????????????????????????
        try {
            List<BillCorpInfo> mast = billCorpInfoService.listBillCorpInfoByKeyWord(corpType, keyword);
            List<BillCorpInfoVo> list = new BillCorpInfoVo().listBillCorpInfoVo(mast);
            ExcelUtils.exportExcel(list, null, "??????????????????", BillCorpInfoVo.class, "??????????????????", response);
        } catch (Exception e) {
            throw new PageException(403, e.getMessage());
        }

    }

    @AuthMethod(role = "ROLE_CORP")
    @RequestMapping("findBankAccountInfo")
    public List<BankAccount> findBankAccountInfo(String corpId) {
        return bankAccountService.listBankAccount(corpId, null);
    }

    /**
     * ????????????????????????
     *
     * @param bankAccount
     * @param model
     * @return
     */
    @AuthMethod(role = "ROLE_CORP")
    @RequestMapping("corpBankdialog")
    public ModelAndView corpBankdialog(BankAccount bankAccount, Model model) {
        if (StringUtils.isEmpty(bankAccount.getZtbz())) {
            bankAccount.setZtbz("T");
            bankAccount.setMoren("F");
        }
        model.addAttribute("bankAccount", bankAccount);
        return new ModelAndView("corp/corpBankdialog");
    }

    /**
     * ??????????????????
     *
     * @param bankAccount
     * @param br
     * @return
     */
    @AuthMethod(role = "ROLE_CORP")
    @PostMapping("saveCorpBank")
    public Message saveCorpBank(@Validated BankAccount bankAccount, BindingResult br) {
        if (br.hasErrors()) {
            return new Message(0, Objects.requireNonNull(br.getFieldError()).getDefaultMessage());
        }
        try {
            String id = bankAccount.getId();
            if (id == null || bankAccount.getId().isEmpty()) {
                //??????
                bankAccount.setId(null);
                return bankAccountService.addBankAccount(bankAccount);
            } else {
                BankAccount mast = bankAccountService.queryBankAccount(id);
                if (mast != null) {
                    mast.setZtbz(bankAccount.getZtbz());
                    mast.setYhzh(bankAccount.getYhzh());
                    mast.setKhyh(bankAccount.getKhyh());
                    mast.setMoren(bankAccount.getMoren());
                    return bankAccountService.updateBankAccount(mast);
                } else {
                    return new Message(0, "???????????????????????????????????????");
                }
            }
        } catch (Exception e) {
            return new Message(0, e.getMessage());
        }
    }

    /**
     * ??????????????????
     *
     * @param id
     * @return
     */
    @AuthMethod(role = "ROLE_CORP")
    @PostMapping("deleteCorpBank")
    public Message deleteCorpBank(String id) {
        return bankAccountService.deleteBankAccountById(id);
    }

}
