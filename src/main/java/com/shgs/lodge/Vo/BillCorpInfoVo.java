package com.shgs.lodge.Vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.shgs.lodge.primary.entity.BillCorpInfo;
import com.shgs.lodge.util.CmsUtils;

import java.util.ArrayList;
import java.util.List;

public class BillCorpInfoVo {

    /**
     * 客商类型
     */
    @Excel(name = "客商类型", orderNum = "0", width = 10)
    private String corpType;


    /**
     * 客商名称
     */
    @Excel(name = "客商名称", orderNum = "1", width = 40)
    private String corpMC;

    /**
     * 客商编码
     */
    @Excel(name = "客商编号", orderNum = "2", width = 10)
    private String corpBM;


    /**
     * 纳税人识别号
     */
    @Excel(name = "纳税人识别号", orderNum = "3", width = 20)
    private String nsrNum;

    /**
     * 联系地址
     */
    @Excel(name = "联系地址", orderNum = "4", width = 40)
    private String dz;

    /**
     * 联系电话
     */
    @Excel(name = "联系电话", orderNum = "5", width = 15)
    private String lxdh;

    /**
     * 开户银行
     */
    @Excel(name = "开户银行", orderNum = "6", width = 40)
    private String khyh;

    /**
     * 银行帐号
     */
    @Excel(name = "银行帐号", orderNum = "7", width = 25)
    private String yhzh;

    /**
     * 备注
     */
    @Excel(name = "备注", orderNum = "8", width = 10)
    private String remark;

    /**
     * 状态标识
     */
    @Excel(name = "状态标识", orderNum = "9", width = 10)
    private String status;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 检测结果：“T”通过检测，“F”未通过检测
     */
    private String checkStr;

    private String UserID;


    public String getCorpType() {
        return corpType;
    }

    public void setCorpType(String corpType) {
        this.corpType = corpType;
    }

    public String getCorpMC() {
        return corpMC;
    }

    public void setCorpMC(String corpMC) {
        this.corpMC = corpMC;
    }

    public String getCorpBM() {
        return corpBM;
    }

    public void setCorpBM(String corpBM) {
        this.corpBM = corpBM;
    }

    public String getNsrNum() {
        return nsrNum;
    }

    public void setNsrNum(String nsrNum) {
        this.nsrNum = nsrNum;
    }

    public String getDz() {
        return dz;
    }

    public void setDz(String dz) {
        this.dz = dz;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    public String getKhyh() {
        return khyh;
    }

    public void setKhyh(String khyh) {
        this.khyh = khyh;
    }

    public String getYhzh() {
        return yhzh;
    }

    public void setYhzh(String yhzh) {
        this.yhzh = yhzh;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BillCorpInfoVo() {
        super();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCheckStr() {
        return checkStr;
    }

    public void setCheckStr(String checkStr) {
        this.checkStr = checkStr;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public BillCorpInfoVo(BillCorpInfo info) {
        this.setCorpMC(info.getCorpMC());
        this.setCorpType(info.getCorpType());
        this.setCorpBM(info.getCorpBM());
        this.setLxdh(info.getLxdh());
        this.setNsrNum(info.getNsrNum());
        this.setRemark(info.getRemark());
        this.setUserID(info.getUserID());
        this.setDz(info.getDz());
        this.setYhzh(info.getYhzh());
        this.setKhyh(info.getKhyh());
        this.setStatus(info.getStatus() == 1 ? "T" : "F");

    }

    public List<BillCorpInfoVo> listBillCorpInfoVo(List<BillCorpInfo> info) {
        List<BillCorpInfoVo> dto = new ArrayList<BillCorpInfoVo>();
        if (info.size() > 0) {
            for (BillCorpInfo mast : info) {
                dto.add(new BillCorpInfoVo(mast));
            }
        }
        return dto;
    }

    public BillCorpInfo getBillCorpInfo(BillCorpInfoVo vo) {
        BillCorpInfo info = new BillCorpInfo();
        if (vo != null) {
            info.setCorpMC(CmsUtils.strToTrim(vo.getCorpMC()));
            info.setCorpType(CmsUtils.strToTrim(vo.getCorpType()));
            info.setCorpBM(CmsUtils.strToTrim(vo.getCorpBM()));
            info.setLxdh(CmsUtils.strToTrim(vo.getLxdh()));
            info.setNsrNum(CmsUtils.strToTrim(vo.getNsrNum()));
            info.setRemark(CmsUtils.strToTrim(vo.getRemark()));
            info.setUserID(CmsUtils.strToTrim(vo.getUserID()));
            info.setDz(CmsUtils.strToTrim(vo.getDz()));
        }
        return info;
    }
}
