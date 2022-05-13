package com.shags.lodge.dto.business;

import com.shags.lodge.business.entity.AssetsInfo;
import org.apache.commons.lang3.StringUtils;

/**
 * @author yglei
 * @classname AssetsInfoFrom
 * @description TODO
 * @date 2021-12-03 14:55
 */
public class AssetsInfoForm {

    /**
     * 关键字
     */
    private String id;

    /**
     * 部门ID
     */
    private String deptId;

    /**
     * 部门编号
     */
    private String deptBh;


    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 卡片编号
     */
    private String cardNumber;

    /**
     * 公司ID
     */
    private String companyId;

    /**
     * 公司编号
     */
    private String companyBh;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 房产类型ID
     */
    private String assetsTypeId;

    /**
     * 房产类型名称
     */
    private String assetsTypeName;

    /**
     * 管理处ID
     */
    private String managePointId;

    /**
     * 管理处名称
     */
    private String managePointName;

    /**
     * 坐落位置ID
     */
    private String sitDownId;


    /**
     * 坐落位置名称
     */
    private String sitDownName;

    /**
     * 资产名称
     */
    private String assetName;

    /**
     * 现用名称
     */
    private String nowName;

    /**
     * 计量单位
     */
    private String assetUnit;

    /**
     * 数量
     */
    private Double assetArea;

    /**
     * 总层数
     */
    private Double totalLayers;

    /**
     * 使用层数
     */
    private Double usedLayers;

    /**
     * 使用房间数
     */
    private Double usedRooms;

    /**
     * 攀钢自编名称
     */
    private String housingNumber;

    /**
     * 社区名称
     */
    private String communityNumber;

    /**
     * 结构
     */
    private String structure;

    /**
     * 产权证号
     */
    private String propertyCertificateNo;

    /**
     * 土地资产编号
     */
    private String landAssetNo;

    /**
     * 土地证号
     */
    private String landCertificateNo;

    /**
     * 状态标志
     */
    private String ztBz;

    /**
     * 备注
     */
    private String bz;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeptBh() {
        return deptBh;
    }

    public void setDeptBh(String deptBh) {
        this.deptBh = deptBh;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyBh() {
        return companyBh;
    }

    public void setCompanyBh(String companyBh) {
        this.companyBh = companyBh;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAssetsTypeId() {
        return assetsTypeId;
    }

    public void setAssetsTypeId(String assetsTypeId) {
        this.assetsTypeId = assetsTypeId;
    }

    public String getManagePointId() {
        return managePointId;
    }

    public void setManagePointId(String managePointId) {
        this.managePointId = managePointId;
    }

    public String getManagePointName() {
        return managePointName;
    }

    public void setManagePointName(String managePointName) {
        this.managePointName = managePointName;
    }

    public String getSitDownId() {
        return sitDownId;
    }

    public void setSitDownId(String sitDownId) {
        this.sitDownId = sitDownId;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getNowName() {
        return nowName;
    }

    public void setNowName(String nowName) {
        this.nowName = nowName;
    }

    public Double getAssetArea() {
        return assetArea;
    }

    public void setAssetArea(Double assetArea) {
        this.assetArea = assetArea;
    }

    public String getHousingNumber() {
        return housingNumber;
    }

    public void setHousingNumber(String housingNumber) {
        this.housingNumber = housingNumber;
    }

    public String getCommunityNumber() {
        return communityNumber;
    }

    public void setCommunityNumber(String communityNumber) {
        this.communityNumber = communityNumber;
    }

    public String getStructure() {
        return structure;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }

    public String getPropertyCertificateNo() {
        return propertyCertificateNo;
    }

    public void setPropertyCertificateNo(String propertyCertificateNo) {
        this.propertyCertificateNo = propertyCertificateNo;
    }

    public String getLandAssetNo() {
        return landAssetNo;
    }

    public void setLandAssetNo(String landAssetNo) {
        this.landAssetNo = landAssetNo;
    }

    public String getLandCertificateNo() {
        return landCertificateNo;
    }

    public void setLandCertificateNo(String landCertificateNo) {
        this.landCertificateNo = landCertificateNo;
    }

    public String getZtBz() {
        return ztBz;
    }

    public void setZtBz(String ztBz) {
        this.ztBz = ztBz;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getAssetUnit() {
        return assetUnit;
    }

    public void setAssetUnit(String assetUnit) {
        this.assetUnit = assetUnit;
    }

    public String getAssetsTypeName() {
        return assetsTypeName;
    }

    public void setAssetsTypeName(String assetsTypeName) {
        this.assetsTypeName = assetsTypeName;
    }

    public String getSitDownName() {
        return sitDownName;
    }

    public void setSitDownName(String sitDownName) {
        this.sitDownName = sitDownName;
    }

    public Double getTotalLayers() {
        return totalLayers;
    }

    public void setTotalLayers(Double totalLayers) {
        this.totalLayers = totalLayers;
    }

    public Double getUsedLayers() {
        return usedLayers;
    }

    public void setUsedLayers(Double usedLayers) {
        this.usedLayers = usedLayers;
    }

    public Double getUsedRooms() {
        return usedRooms;
    }

    public void setUsedRooms(Double usedRooms) {
        this.usedRooms = usedRooms;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public AssetsInfoForm() {
        super();
    }


    public AssetsInfoForm(AssetsInfo info) {
        if (info != null) {
            this.setId(info.getId());
            this.setDeptId(info.getDeptId());
            this.setDeptBh(info.getDeptBh());
            this.setDeptName(info.getDeptName());
            this.setCardNumber(info.getCardNumber());
            this.setCompanyBh(info.getCompanyBh());
            this.setCompanyId(info.getCompanyId());
            this.setCompanyName(info.getCompanyName());
            this.setAssetsTypeId(info.getAssetsTypeId());
            this.setAssetsTypeName(info.getAssetsTypeName());
            this.setSitDownId(info.getSitDownId());
            this.setSitDownName(info.getSitDownName());
            this.setManagePointId(info.getManagePointId());
            this.setManagePointName(info.getManagePointName());
            this.setAssetName(info.getAssetName());
            this.setNowName(info.getNowName());
            this.setAssetArea(info.getAssetArea());
            this.setCommunityNumber(info.getCommunityNumber());
            this.setStructure(info.getStructure());
            this.setPropertyCertificateNo(info.getPropertyCertificateNo());
            this.setLandAssetNo(info.getLandAssetNo());
            this.setLandCertificateNo(info.getLandCertificateNo());
            this.setZtBz(info.getZtBz());
            this.setBz(info.getBz());
            this.setAssetUnit(info.getAssetUnit());
            this.setUsedLayers(info.getUsedLayers());
            this.setUsedRooms(info.getUsedRooms());
            this.setTotalLayers(info.getTotalLayers());
        }

    }

    public AssetsInfo getAssetsInfo(AssetsInfoForm info) {
        AssetsInfo temp = new AssetsInfo();
        if (info != null) {
            temp.setId(StringUtils.isNotEmpty(info.getId()) ? info.getId() : null);
            temp.setDeptBh(info.getDeptBh());
            temp.setDeptId(info.getDeptId());
            temp.setDeptName(info.getDeptName());
            temp.setCardNumber(info.getCardNumber());
            temp.setCompanyBh(info.getCompanyBh());
            temp.setCompanyId(info.getCompanyId());
            temp.setCompanyName(info.getCompanyName());
            temp.setManagePointId(info.getManagePointId());
            temp.setManagePointName(info.getManagePointName());
            temp.setAssetName(info.getAssetName());
            temp.setNowName(info.getNowName());
            temp.setAssetArea(info.getAssetArea());
            temp.setCommunityNumber(info.getCommunityNumber());
            temp.setStructure(info.getStructure());
            temp.setPropertyCertificateNo(info.getPropertyCertificateNo());
            temp.setLandAssetNo(info.getLandAssetNo());
            temp.setLandCertificateNo(info.getLandCertificateNo());
            temp.setZtBz(info.getZtBz());
            temp.setAssetUnit(info.getAssetUnit());
            temp.setBz(info.getBz());
            temp.setTotalLayers(info.getTotalLayers());
            temp.setUsedRooms(info.getUsedRooms());
            temp.setUsedLayers(info.getUsedLayers());
            temp.setAssetsTypeId(info.getAssetsTypeId());
            temp.setAssetsTypeName(info.getAssetsTypeName());
            temp.setSitDownId(info.getSitDownId());
            temp.setSitDownName(info.getSitDownName());
            temp.setHousingNumber(info.getHousingNumber());
        }
        return temp;
    }

}
