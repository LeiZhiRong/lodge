package com.shgs.lodge.dto;

import com.shgs.lodge.primary.entity.CashBank;

import java.util.ArrayList;
import java.util.List;

public class CashBankListDto {
    /**
     * 关键字
     */
    private String id;

    /**
     * 科目编号
     */
    private String kmBH;

    /**
     * 科目名称
     */
    private String kmMC;

    /**
     * 上级科目ID
     */
    private String parent_id;

    /**
     * 是否为目录
     */
    private String contents;

    private String handle;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public CashBankListDto() {
        super();
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public CashBankListDto(CashBank cashBank) {
        String pid = null;
        this.setId(cashBank.getId());
        this.setKmBH(cashBank.getKmBH());
        this.setContents(cashBank.getContents());
        if (cashBank.getParent() != null) {
            CashBank parent = cashBank.getParent();
            this.setParent_id(parent.getId());
            pid = parent.getId();
        } else {
            this.setParent_id(null);
            pid = "";
        }
        if ("T".equals(cashBank.getContents())) {
            this.setKmMC("<a href='javascript:void(0)' onclick='findCashBank(&quot;" + cashBank.getId() + "&quot;,&quot;" + pid + "&quot;);'><i class='fa fa-caret-right fa-lg'></i> " + cashBank.getKmBH() + "\\" + cashBank.getKmMC() + "</a>");
            if (cashBank.getParent() != null)
                this.setHandle("<div style='padding-right:10px;'><a href='javascript:void(0)' onclick='getCashBnkIdAndBh(&quot;" + cashBank.getId() + "&quot;,&quot;" + cashBank.getKmBH() + "&quot;);'>选择</a></div>");
        } else {
            String str = cashBank.getParent() != null ? cashBank.getKmBH() + "\\" + cashBank.getParent().getKmMC() + "\\" + cashBank.getKmMC() : cashBank.getKmBH() + "\\" + cashBank.getKmMC();
            this.setKmMC(str);
            this.setHandle("<div style='padding-right:10px;'><a href='javascript:void(0)' onclick='getCashBnkIdAndBh(&quot;" + cashBank.getId() + "&quot;,&quot;" + cashBank.getKmBH() + "&quot;);'>选择</a></div>");
        }


    }

    public List<CashBankListDto> listCashBankListDto(List<CashBank> cashBankList) {
        List<CashBankListDto> list = new ArrayList<CashBankListDto>();
        if (cashBankList.size() > 0) {
            for (CashBank mast : cashBankList) {
                list.add(new CashBankListDto(mast));
            }
        }
        return list;
    }


}
