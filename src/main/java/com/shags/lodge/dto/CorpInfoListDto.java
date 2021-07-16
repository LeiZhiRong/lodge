package com.shags.lodge.dto;

import com.shags.lodge.primary.entity.BillCorpInfo;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CorpInfoListDto {

    /**
     * 客商ID
     */
    private String saleCorpId;

    /**
     * 客商编号
     */
    private String saleCorpBh;

    /**
     * 客商名称
     */
    private String saleCorpName;

    /**
     * 上级目录ID
     */
    private String pid;

    /**
     * 是否为目录
     */
    private String contents;

    /**
     * 关键操作
     */
    private String handle;

    public String getSaleCorpId() {
        return saleCorpId;
    }

    public void setSaleCorpId(String saleCorpId) {
        this.saleCorpId = saleCorpId;
    }

    public String getSaleCorpBh() {
        return saleCorpBh;
    }

    public void setSaleCorpBh(String saleCorpBh) {
        this.saleCorpBh = saleCorpBh;
    }

    public String getSaleCorpName() {
        return saleCorpName;
    }

    public void setSaleCorpName(String saleCorpName) {
        this.saleCorpName = saleCorpName;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public CorpInfoListDto() {
        super();
    }

    public CorpInfoListDto(BillCorpInfo billCorpInfo) {
        this.setSaleCorpId(billCorpInfo.getId());
        this.setPid(billCorpInfo.getCorpType());
        this.setSaleCorpName(billCorpInfo.getCorpBM() + "\\" + billCorpInfo.getCorpMC());
        this.setSaleCorpBh(billCorpInfo.getCorpBM());
        if (StringUtils.isNotEmpty(billCorpInfo.getCorpType())) {
            String str = billCorpInfo.getCorpBM() + "\\" + billCorpInfo.getCorpMC();
            String _str=str.replace("\\","\\\\");
            this.setSaleCorpName("<div style='padding-right:10px;'><a href='javascript:void(0)' onclick='getCorpIdAndName(&quot;" + billCorpInfo.getId() + "&quot;,&quot;" + _str + "&quot;);'>" + str + "</a></div>");
        } else {
            this.setSaleCorpName("<a href='javascript:void(0)' onclick='findCorpInfoListDto(&quot;" + billCorpInfo.getId() + "&quot;);'><i class='fa fa-caret-right fa-lg'></i> " + billCorpInfo.getCorpBM() + "\\" + billCorpInfo.getCorpMC() + "</a>");
        }
    }

    public List<CorpInfoListDto> listCorpInfoListDto(List<BillCorpInfo> billCorpInfoList, String contents) {
        List<CorpInfoListDto> list = new ArrayList<>();
        if (billCorpInfoList.size() > 0) {
            for (BillCorpInfo mast : billCorpInfoList) {
                CorpInfoListDto temp = new CorpInfoListDto(mast);
                temp.setContents(contents);
                list.add(temp);
            }
        }
        return list;
    }


}
