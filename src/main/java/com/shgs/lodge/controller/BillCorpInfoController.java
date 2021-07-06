package com.shgs.lodge.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.shgs.lodge.Vo.BillCorpInfoVo;
import com.shgs.lodge.auth.AuthClass;
import com.shgs.lodge.auth.AuthMethod;
import com.shgs.lodge.dto.HeaderColumns;
import com.shgs.lodge.dto.User;
import com.shgs.lodge.excel.ExcelUtils;
import com.shgs.lodge.exception.exception.PageException;
import com.shgs.lodge.primary.entity.BankAccount;
import com.shgs.lodge.primary.entity.BillCorpInfo;
import com.shgs.lodge.service.IBankAccountService;
import com.shgs.lodge.service.IBillCorpInfoService;
import com.shgs.lodge.service.ICustomService;
import com.shgs.lodge.service.ITableHeaderService;
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
import java.util.List;

@RestController
@RequestMapping("/corp/")
@Scope("prototype")
@AuthClass("login")
public class BillCorpInfoController {

    @Autowired
    private IBillCorpInfoService billCorpInfoService;

    @Autowired
    private ITableHeaderService tableHeaderService;

    @Autowired
    private ICustomService customService;

    @Autowired
    private IBankAccountService bankAccountService;

    @AuthMethod(role = "ROLE_CORP")
    @GetMapping("index")
    public ModelAndView index(Model model, HttpSession session) throws JsonProcessingException {
        User user = (User) session.getAttribute("user");
        List<HeaderColumns> columns = tableHeaderService.listHeaderColumns(user.getId(), "corpGrid", "com.shgs.lodge.primary.entity.BillCorpInfo");
        List<SelectJson> corpType = customService.listCustomParame("CORPTYPE", null, true);
        corpType.add(0, new SelectJson("", "-【全部】", "all"));
        model.addAttribute("columns", columns);
        model.addAttribute("corpType", corpType);
        ModelAndView view = new ModelAndView("corp/index");
        return view;
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
        Pager<BillCorpInfo> mast = billCorpInfoService.listBillCorpInfo(corpType, keyword, null);
        return mast;
    }

    @AuthMethod(role = "ROLE_CORP")
    @GetMapping("dialog")
    public ModelAndView dialog(String id, Model model) throws JsonProcessingException {
        BillCorpInfo billCorpInfo = new BillCorpInfo();
        List<SelectJson> coryType = customService.listCustomParame("CORPTYPE", null, true);
        if (id != null && !id.isEmpty()) {
            billCorpInfo = billCorpInfoService.queryBillCorpInfoById(id);
        } else {
            billCorpInfo.setStatus(1);
        }
        List<HeaderColumns> bankColumns = CmsUtils.getHeaderColumns("com.shgs.lodge.primary.entity.BankAccount");
        model.addAttribute("billCorpInfo", billCorpInfo);
        model.addAttribute("corpType", coryType);
        model.addAttribute("bankColumns", bankColumns);
        ModelAndView view = new ModelAndView("corp/dialog");
        return view;
    }

    /**
     * 保存客户信息
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
            return new Message(0, br.getFieldError().getDefaultMessage());
        }
        try {
            User user = (User) session.getAttribute("user");
            String id = billCorpInfo.getId();
            if (id == null || billCorpInfo.getId().isEmpty()) {
                //添加
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
                    return new Message(0, "该客户不存在或已被删除");
                }
            }
        } catch (Exception e) {
            return new Message(0, e.getMessage());
        }
    }

    /**
     * 按ID删除
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
     * 批量删除
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
    public ModelAndView importUpload(Model model) throws JsonProcessingException {
        ModelAndView view = new ModelAndView("corp/import");
        return view;
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
    @AuthMethod(role = "ROLE_CORP")
    public String importUpload(MultipartFile file, Integer headerRows) throws Exception {
        Message msg = new Message(0, "导入失败");
        if (file == null || file.isEmpty()) {
            msg.setMessage("上传文件不能为空，请重新选择模板文件，然后重试。");
            return JsonUtil.objectToString(msg);
        }
        List<BillCorpInfoVo> vo = new ArrayList<BillCorpInfoVo>();
        List<BillCorpInfoVo> list = ExcelUtils.importExcel(file, 0, headerRows, BillCorpInfoVo.class);
        if (list != null && list.size() > 0) {
            vo = list;
            msg.setCode(1);
            msg.setMessage("导入成功");
            msg.setData(vo);
        }
        return JsonUtil.objectToString(msg);
    }

    /**
     * 下载模板
     *
     * @param request
     * @param response
     */
    @AuthMethod(role = "ROLE_CORP")
    @RequestMapping("downloadExcel")
    public void downloadExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //需要导出的数据列表。
        List<BillCorpInfoVo> list = new ArrayList<BillCorpInfoVo>();
        BillCorpInfoVo vo = new BillCorpInfoVo();
        vo.setCorpType("1=购买方，0=销售方(必填项)");
        vo.setCorpBM("（必填项）");
        vo.setCorpMC("（必填项）");
        vo.setStatus("T=正常，F=停用，不填写默认为F");
        list.add(vo);
        ExcelUtils.exportExcel(list, null, "客商基本信息", BillCorpInfoVo.class, "客商基本信息导入模板", response);
    }

    /**
     * 检测并保存模板数据，返回错误数据
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
            List<BillCorpInfoVo> array = new ArrayList<BillCorpInfoVo>();
            List<BillCorpInfo> listBillCorpInfo = new ArrayList<BillCorpInfo>();
            List<BillCorpInfo> listUpdate = new ArrayList<BillCorpInfo>();
            List<BankAccount> addBankAccountData = new ArrayList<>();
            List<BankAccount> updateBankAccountData = new ArrayList<>();
            User user = (User) session.getAttribute("user");
            if (dtos.size() > 0) {
                for (BillCorpInfoVo mast : dtos) {
                    List<String> err = new ArrayList<String>();
                    //检测客户类型
                    if (mast.getCorpType() == null || mast.getCorpType().isEmpty()) {
                        err.add("【客户类型】不能为空");
                    } else {
                        List<SelectJson> list = customService.listCustomParame("CORPTYPE", mast.getCorpType(), true);
                        if (list == null || list.size() == 0) {
                            err.add("客户类型【" + mast.getCorpType() + "】不存在");
                        }
                    }
                    //检测状态标识
                    if (mast.getStatus() == null && mast.getStatus().isEmpty()) {
                        err.add("【状态标识】不能为空，应为‘T’或‘F’");
                    }
                    //检测客户编号
                    if (mast.getCorpBM() == null || mast.getCorpBM().isEmpty()) {
                        err.add("【客户编号】不能为空");
                    } else {
                        //按客商编号查询客商信息，若存在则更新导入相关内容
                        BillCorpInfo info = billCorpInfoService.queryBillCorpInfoByCorpBM(mast.getCorpBM());
                        if (info != null) {
                            if (saveUpdate == 0) {
                                err.add("客户编号【" + mast.getCorpBM() + "】已存在");
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
                                //检测银行信息，若已存在就检测是否有差异，有差异则更新；若银行信息不存在则新增
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
                                        bankAccount.setYhzh(mast.getYhzh());
                                        bankAccount.setKhyh(mast.getKhyh());
                                        bankAccount.setCorpId(info.getId());
                                        bankAccount.setZtbz(mast.getStatus());
                                        addBankAccountData.add(bankAccount);
                                    }
                                }
                                continue;
                            }
                        }
                    }
                    if (err.size() > 0) {
                        mast.setRemarks(String.join("；", err));
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
                //更新客商信息
                if (listUpdate != null && listUpdate.size() > 0)
                    billCorpInfoService.batchUpdateBillCorpInfo(listUpdate);
                //更新银行信息
                if (updateBankAccountData != null && updateBankAccountData.size() > 0)
                    bankAccountService.batchUpdateBankAccount(updateBankAccountData);
                //新增客商信息并增加银行信息
                if (listBillCorpInfo != null && listBillCorpInfo.size() > 0) {
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
                //新增银行信息
                if (addBankAccountData != null && addBankAccountData.size() > 0)
                    bankAccountService.batchSaveBankAccount(addBankAccountData);
                return new Message(1, "检测完成", array);
            } else {
                return new Message(0, "数据解析错误，请联系管理员");
            }
        }
        return new Message(0, "检测数据为空");
    }


    @AuthMethod(role = "ROLE_CORP")
    @RequestMapping("exportDown")
    public void exportDown(String keyword,String corpType,HttpServletRequest request, HttpServletResponse response) throws IOException {
        //需要导出的数据列表。
        try {
            List<BillCorpInfo> mast = billCorpInfoService.listBillCorpInfoByKeyWord(corpType,keyword);
            List<BillCorpInfoVo> list = new BillCorpInfoVo().listBillCorpInfoVo(mast);
            ExcelUtils.exportExcel(list, null, "客商基本信息", BillCorpInfoVo.class, "客商基本信息", response);
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
     * 银行账号添加页面
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
        ModelAndView view = new ModelAndView("corp/corpBankdialog");
        return view;
    }

    /**
     * 保存银行账号
     *
     * @param bankAccount
     * @param br
     * @param session
     * @return
     */
    @AuthMethod(role = "ROLE_CORP")
    @PostMapping("saveCorpBank")
    public Message saveCorpBank(@Validated BankAccount bankAccount, BindingResult br, HttpSession session) {
        if (br.hasErrors()) {
            return new Message(0, br.getFieldError().getDefaultMessage());
        }
        try {
            String id = bankAccount.getId();
            if (id == null || bankAccount.getId().isEmpty()) {
                //添加
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
                    return new Message(0, "该银行账号不存在或已被删除");
                }
            }
        } catch (Exception e) {
            return new Message(0, e.getMessage());
        }
    }

    /**
     * 删除银行账号
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