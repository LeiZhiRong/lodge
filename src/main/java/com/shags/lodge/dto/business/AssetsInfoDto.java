package com.shags.lodge.dto.business;

import com.shags.lodge.business.entity.AssetsInfo;
import com.shags.lodge.business.entity.SitDown;
import com.shags.lodge.util.BeanUtil;
import com.shags.lodge.util.HeaderEnum;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yglei
 * @classname AssetsInfoDto
 * @description TODO
 * @date 2021-11-23 11:37
 */
public class AssetsInfoDto {

    /**
     * 关键字
     */
    private String id;

    /**
     * 部门编号
     */
    private String deptID;

    /**
     * 管辖部门
     */
    @HeaderEnum(field = "deptName", title = "管辖部门", width = 150)
    private String deptName;

    /**
     * 卡片编号
     */
    @HeaderEnum(field = "cardNumber", title = "卡片编号",width = 150)
    private String cardNumber;

    /**
     * 公司名称
     */
    @HeaderEnum(field = "corporateName", title = "公司名称" ,width = 250)
    private String corporateName;

    /**
     * 房屋类型ID
     */
    private String assetsTypeId;

    /**
     * 房屋类型名称
     */
    @HeaderEnum(field = "assetsTypeName", title = "房屋类型", width = 100)
    private String assetsTypeName;

    /**
     * 坐落位置
     */
    @HeaderEnum(field = "sitDown", title = "坐落位置", width = 100)
    private SitDown sitDown;

    /**
     * 资产名称
     */
    @HeaderEnum(field = "assetName", title = "资产名称", width = 250)
    private String assetName;

    /**
     * 现用名
     */
    @HeaderEnum(field = "nowName", title = "现用名称", width = 250, hidden = true)
    private String nowName;

    /**
     * 攀钢自编名称
     */
    @HeaderEnum(field = "housingNumber", title = "攀钢自编名称", width = 250, hidden = true)
    private String housingNumber;

    /**
     * 社区名称
     */
    @HeaderEnum(field = "communityNumber", title = "社区名称", width = 250, hidden = true)
    private String communityNumber;

    /**
     * 资产面积
     */
    @HeaderEnum(field = "assetArea", title = "资产面积", width = 80)
    private Double assetArea;

    /**
     * 产权证号
     */
    @HeaderEnum(field = "propertyCertificateNo", title = "产权证号", hidden = true, width = 100)
    private String propertyCertificateNo;

    /**
     * 土地资产编号
     */
    @HeaderEnum(field = "landAssetNo", title = "土地资产编号", hidden = true, width = 100)
    private String landAssetNo;

    /**
     * 土地证号
     */
    @HeaderEnum(field = "landCertificateNo", title = "土地证号", hidden = true, width = 100)
    private String landCertificateNo;

    /**
     * 帐套编号
     */
    private String bookSet;

    /**
     * 创建用户
     */
    @HeaderEnum(field = "createUser", title = "创建用户", width = 150, hidden = true)
    private String createUser;

    /**
     * 创建时间
     */
    @HeaderEnum(field = "createTime", title = "创建时间", width = 150, hidden = true)
    private String createTime;

    /**
     * 最后更新用户
     */
    @HeaderEnum(field = "updateUser", title = "更新用户", width = 150, hidden = true)
    private String updateUser;

    /**
     * 最后更新时间
     */
    @HeaderEnum(field = "updateTime", title = "更新时间", width = 150, hidden = true)
    private String updateTime;

    /**
     * 状态标志
     */
    @HeaderEnum(field = "ztBz", title = "状态标识", width = 80)
    private String ztBz;

    /**
     * 关联操作
     */
    @HeaderEnum(field = "handle", title = "关联操作", width = 150,sortable = false)
    private String handle;


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

    public String getCorporateName() {
        return corporateName;
    }

    public void setCorporateName(String corporateName) {
        this.corporateName = corporateName;
    }

    public String getAssetsTypeName() {
        return assetsTypeName;
    }

    public void setAssetsTypeName(String assetsTypeName) {
        this.assetsTypeName = assetsTypeName;
    }

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

    public Double getAssetArea() {
        return assetArea;
    }

    public void setAssetArea(Double assetArea) {
        this.assetArea = assetArea;
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

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getZtBz() {
        return ztBz;
    }

    public void setZtBz(String ztBz) {
        this.ztBz = ztBz;
    }

    public String getAssetsTypeId() {
        return assetsTypeId;
    }

    public void setAssetsTypeId(String assetsTypeId) {
        this.assetsTypeId = assetsTypeId;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public AssetsInfoDto() {
        super();
    }

    public AssetsInfoDto(AssetsInfo assetsInfo) {
        if (assetsInfo != null) {
            this.setId(assetsInfo.getId());
            this.setDeptID(assetsInfo.getDeptID());
            this.setDeptName(assetsInfo.getDeptName());
            this.setCardNumber(assetsInfo.getCardNumber());
            this.setCorporateName(assetsInfo.getCorporateName());
            this.setAssetsTypeName(assetsInfo.getAssetsType() != null ? assetsInfo.getAssetsType().getName() : "");
            this.setAssetsTypeId(assetsInfo.getAssetsType() != null ? assetsInfo.getAssetsType().getId() : "");
            this.setSitDown(assetsInfo.getSitDown());
            this.setAssetName(assetsInfo.getAssetName());
            this.setNowName(assetsInfo.getNowName());
            this.setHousingNumber(assetsInfo.getHousingNumber());
            this.setCommunityNumber(assetsInfo.getCommunityNumber());
            this.setAssetArea(assetsInfo.getAssetArea());
            this.setPropertyCertificateNo(assetsInfo.getPropertyCertificateNo());
            this.setLandAssetNo(assetsInfo.getLandAssetNo());
            this.setLandCertificateNo(assetsInfo.getLandCertificateNo());
            this.setBookSet(assetsInfo.getBookSet());
            this.setCreateUser(assetsInfo.getCreateUser());
            this.setCreateTime(assetsInfo.getCreateUser() != null ? BeanUtil.timestampToStr(assetsInfo.getCreateTime(), "yyyy-MM-dd HH:mm") : "");
            this.setUpdateTime(assetsInfo.getUpdateTime() != null ? BeanUtil.timestampToStr(assetsInfo.getUpdateTime(), "yyyy-MM-dd HH:mm") : "");
            this.setUpdateUser(assetsInfo.getUpdateUser());
            this.setZtBz(assetsInfo.getZtBz());
        }
    }

    public List<AssetsInfoDto> listAssetsInfoDto(List<AssetsInfo> list) {
        List<AssetsInfoDto> dto = new ArrayList<>();
        if (list.size() > 0) {
            for (AssetsInfo mast : list) {
                dto.add(new AssetsInfoDto(mast));
            }
        }
        return dto;
    }


    public AssetsInfo getAssetsInfoDto(AssetsInfoDto dto) {
        AssetsInfo assetsInfo = new AssetsInfo();
        if (dto != null) {
            assetsInfo.setId(StringUtils.isNotEmpty(dto.getId()) ? dto.getId() : null);
            assetsInfo.setDeptID(dto.getDeptID());
            assetsInfo.setDeptName(dto.getDeptName());
            assetsInfo.setCardNumber(dto.getCardNumber());
            assetsInfo.setCorporateName(dto.getCorporateName());
            assetsInfo.setSitDown(dto.getSitDown());
            assetsInfo.setAssetName(dto.getAssetName());
            assetsInfo.setNowName(dto.getNowName());
            assetsInfo.setHousingNumber(dto.getHousingNumber());
            assetsInfo.setCommunityNumber(dto.getCommunityNumber());
            assetsInfo.setAssetArea(dto.getAssetArea());
            assetsInfo.setPropertyCertificateNo(dto.getPropertyCertificateNo());
            assetsInfo.setLandAssetNo(dto.getLandAssetNo());
            assetsInfo.setLandCertificateNo(dto.getLandCertificateNo());
            assetsInfo.setBookSet(dto.getBookSet());
            assetsInfo.setCreateUser(dto.getCreateUser());
            assetsInfo.setUpdateUser(dto.getUpdateUser());
            assetsInfo.setZtBz(dto.getZtBz());
        }
        return assetsInfo;
    }


}
