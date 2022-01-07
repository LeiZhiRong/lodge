package com.shags.lodge.business.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author yglei
 * @classname BuildingInfo
 * @description 楼栋信息实体类
 * @date 2021-07-26 16:20
 */
@Entity
@Table(name = "assets_info")
@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
public class AssetsInfo {

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
     * 房产类型
     */
    private AssetsType assetsType;

    /**
     * 管理处ID
     */
    private String managePointId;

    /**
     * 管理处名称
     */
    private String managePointName;

    /**
     * 坐落位置
     */
    private SitDown sitDown;

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
     * 帐套编号
     */
    private String bookSet;

    /**
     * 创建用户
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 最后更新用户
     */
    private String updateUser;

    /**
     * 最后更新时间
     */
    private Timestamp updateTime;

    /**
     * 时间戳
     */
    private String sVTime;

    /**
     * 状态标志
     */
    private String ztBz;

    /**
     * 自定义卡片编号
     */
    private int customCardNumber;

    /**
     * 备注
     */
    private String bz;

    @Id
    @GeneratedValue(generator = "uuid2")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getBookSet() {
        return bookSet;
    }

    public void setBookSet(String bookSet) {
        this.bookSet = bookSet;
    }



    public String getsVTime() {
        return sVTime;
    }

    public void setsVTime(String sVTime) {
        this.sVTime = sVTime;
    }

    public String getZtBz() {
        return ztBz;
    }

    public void setZtBz(String ztBz) {
        this.ztBz = ztBz;
    }

    public int getCustomCardNumber() {
        return customCardNumber;
    }

    public void setCustomCardNumber(int customCardNumber) {
        this.customCardNumber = customCardNumber;
    }

    @ManyToOne
    @JoinColumn(name = "assetsTypeId")
    public AssetsType getAssetsType() {
        return assetsType;
    }

    public void setAssetsType(AssetsType assetsType) {
        this.assetsType = assetsType;
    }

    @ManyToOne
    @JoinColumn(name = "sitDownId")
    public SitDown getSitDown() {
        return sitDown;
    }

    public void setSitDown(SitDown sitDown) {
        this.sitDown = sitDown;
    }

    public String getDeptBh() {
        return deptBh;
    }

    public void setDeptBh(String deptBh) {
        this.deptBh = deptBh;
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

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
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

    public void setAssetUnit(String assetUnit) {
        this.assetUnit = assetUnit;
    }


    public AssetsInfo() {
        super();
    }



}
