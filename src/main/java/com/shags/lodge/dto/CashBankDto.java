package com.shags.lodge.dto;

import com.shags.lodge.util.PinyinUtil;
import com.shags.lodge.primary.entity.CashBank;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

public class CashBankDto {
    /**
     * 关键字
     */
    private String id;
    /**
     * 科目编号
     */
    @NotEmpty(message = "科目编号不能为空")
    private String kmBH;

    /**
     * 科目名称
     */
    @NotEmpty(message = "科目名称不能为空")
    private String kmMC;
    /**
     * 上级科目ID
     */
    private String parent_id;

    /**
     * 上级科目名称
     */
    private String parent_name;

    /**
     * 科目关联ids
     */
    private String ids;

    /**
     * 排序号
     */
    private Integer orders;

    /**
     * 科目简称
     */
    private String kmJC;

    /**
     * 科目拼音码
     */
    private String kmPYM;

    /**
     * 状态标识
     */
    private String ztbz;

    /**
     * 备注
     */
    private String remarks;
    /**
     * 是否为目录
     */
    private String contents;

    /**
     * 余额方向
     */
    private String balance;

    public CashBankDto() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getParent_name() {
        return parent_name;
    }

    public void setParent_name(String parent_name) {
        this.parent_name = parent_name;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public Integer getOrders() {
        return orders;
    }

    public void setOrders(Integer orders) {
        this.orders = orders;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getKmBH() {
        return kmBH;
    }

    public void setKmBH(String kmBH) {
        this.kmBH = kmBH;
    }

    public String getKmMC() {
        return kmMC;
    }

    public void setKmMC(String kmMC) {
        this.kmMC = kmMC;
    }

    public String getKmJC() {
        return kmJC;
    }

    public void setKmJC(String kmJC) {
        this.kmJC = kmJC;
    }

    public String getKmPYM() {
        return kmPYM;
    }

    public void setKmPYM(String kmPYM) {
        this.kmPYM = kmPYM;
    }

    public String getZtbz() {
        return ztbz;
    }

    public void setZtbz(String ztbz) {
        this.ztbz = ztbz;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public CashBankDto(CashBank cashBank) {
        this.setId(cashBank.getId());
        this.setZtbz("T".equals(cashBank.getZtbz()) ? "<i class='fa fa-check  fa-lg  '></i>" : "<i class='fa fa-close fa-lg '></i>");
        this.setContents(cashBank.getContents());
        if ("T".equals(cashBank.getContents())) {
            this.setKmMC("<a href='javascript:void(0)' onclick='findDeptInfo(&quot;" + cashBank.getId() + "&quot;);'><i class='fa fa-folder-open-o fa-lg'></i> " + cashBank.getKmMC() + "</a>");
        } else {
            this.setKmMC(cashBank.getKmMC());
        }
        if("1".equals(cashBank.getBalance())){
            this.setBalance("贷");
        }else if("0".equals(cashBank.getBalance())){
            this.setBalance("借");
        }else{
            this.setBalance("无");
        }
        this.setOrders(cashBank.getOrders());
        this.setKmJC(cashBank.getKmJC());
        this.setKmPYM(cashBank.getKmPYM());
        this.setKmBH(cashBank.getKmBH());
        this.setRemarks(cashBank.getRemarks());
        if (cashBank.getParent() != null) {
            CashBank parent = cashBank.getParent();
            this.setParent_id(parent.getId());
            if (parent.getParent() != null) {
                this.setParent_name("<a href='javascript:void(0)' onclick='findDeptInfo(&quot;" + parent.getParent().getId() + "&quot;);'><i class='fa  fa-folder-open-o  fa-lg'></i> " + parent.getKmMC() + "</a>");
            } else {
                this.setParent_name("<a href='javascript:void(0)' onclick='findDeptInfo(null);'><i class='fa fa-folder-open-o fa-lg'></i> " + parent.getKmMC() + "</a>");
            }
        } else {
            this.setParent_id(null);
            this.setParent_name("");
        }
    }

    public List<CashBankDto> listCashBankDto(List<CashBank> cashBankList) {
        List<CashBankDto> list = new ArrayList<>();
        if (cashBankList.size() > 0) {
            for (CashBank mast : cashBankList) {
                list.add(new CashBankDto(mast));
            }
        }
        return list;
    }

    public CashBank getCashBank(CashBankDto dto) {
        CashBank cashBank = new CashBank();
        if (dto != null) {
            cashBank.setId(dto.getId() != null && !dto.getId().isEmpty() ? dto.getId() : null);
            cashBank.setZtbz(dto.getZtbz());
            cashBank.setRemarks(dto.getRemarks());
            cashBank.setContents(dto.getContents());
            cashBank.setKmPYM(dto.getKmMC() != null && !dto.getKmMC().isEmpty() ? PinyinUtil.strFirst2Pinyin(dto.getKmMC()) : "");
            cashBank.setKmMC(dto.getKmMC());
            cashBank.setKmJC(dto.getKmJC() != null && !dto.getKmJC().isEmpty() ? dto.getKmJC() : dto.getKmMC());
            cashBank.setContents(dto.getContents());
            cashBank.setKmBH(dto.getKmBH());
            cashBank.setBalance(dto.getBalance());
        }
        return cashBank;
    }

}
