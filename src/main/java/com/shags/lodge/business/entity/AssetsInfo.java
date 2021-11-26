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
     * 部门编号
     */
    private String deptID;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    /**
     * 管辖部门
     */
    private String deptName;

    /**
     * 卡片编号
     */
    private String cardNumber;

    /**
     * 公司名称
     */
    private String corporateName;

    /**
     * 房屋类型
     */
    private AssetsType assetsType;

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
     * 资产面积
     */
    private Double assetArea;

    /**
     * 攀钢自编名称
     */
    private String housingNumber;

    /**
     * 社区名称
     */
    private String communityNumber;

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

    @Id
    @GeneratedValue(generator = "uuid2")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeptID() {
        return deptID;
    }

    public void setDeptID(String deptID) {
        this.deptID = deptID;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCorporateName() {
        return corporateName;
    }

    public void setCorporateName(String corporateName) {
        this.corporateName = corporateName;
    }

    @ManyToOne
    @JoinColumn(name = "assetsType_id")
    public AssetsType getAssetsType() {
        return assetsType;
    }

    public void setAssetsType(AssetsType assetsType) {
        this.assetsType = assetsType;
    }

    @ManyToOne
    @JoinColumn(name = "sitDown_id")
    public SitDown getSitDown() {
        return sitDown;
    }

    public void setSitDown(SitDown sitDown) {
        this.sitDown = sitDown;
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

    public String getBookSet() {
        return bookSet;
    }

    public void setBookSet(String bookSet) {
        this.bookSet = bookSet;
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

    public String getsVTime() {
        return sVTime;
    }

    public void setsVTime(String sVTime) {
        this.sVTime = sVTime;
    }

    public int getCustomCardNumber() {
        return customCardNumber;
    }

    public void setCustomCardNumber(int customCardNumber) {
        this.customCardNumber = customCardNumber;
    }

    public AssetsInfo() {
        super();
    }

}
