package com.shags.lodge.dto;

import com.shags.lodge.util.BeanUtil;
import com.shags.lodge.util.CmsUtils;
import com.shags.lodge.util.HeaderEnum;
import com.shags.lodge.primary.entity.AccounInfo;

import java.util.ArrayList;
import java.util.List;

public class AccounInfoDto {

    /**
     * 关键字
     */
    private String id;

    /**
     * 账套编号(唯一)
     */
    @HeaderEnum(field = "bookSet", title = "账套编号", sortable = false)
    private String bookSet;

    /**
     * 账套名称
     */
    @HeaderEnum(field = "accounName", title = "账套名称", width = 200, sortable = false)
    private String accounName;

    /**
     * 系统图标
     */
    private String icons;

    /**
     * 注册时间
     */
    @HeaderEnum(field = "regTime", title = "注册时间", width = 120, sortable = false)
    private String regTime;

    /**
     * 客户名称
     */
    @HeaderEnum(field = "corpName", title = "注册单位", width = 200, sortable = false)
    private String corpName;

    /**
     * 描述
     */
    @HeaderEnum(field = "remarks", title = "描述", width = 200, sortable = false)
    private String remarks;

    /**
     * 状态标志
     */
    @HeaderEnum(field = "ztbz", title = "单据状态", width = 80, hidden = true)
    private String ztbz;

    /**
     * 关联操作
     */
    @HeaderEnum(field = "handle", title = "关联操作", width = 150, sortable = false)
    private String handle;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookSet() {
        return bookSet;
    }

    public void setBookSet(String bookSet) {
        this.bookSet = bookSet;
    }

    public String getAccounName() {
        return accounName;
    }

    public void setAccounName(String accounName) {
        this.accounName = accounName;
    }

    public String getRegTime() {
        return regTime;
    }

    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public AccounInfoDto() {
        super();
    }

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public String getHandle() {
        return CmsUtils.formatHandle(this.id);
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getZtbz() {
        return ztbz;
    }

    public void setZtbz(String ztbz) {
        this.ztbz = ztbz;
    }

    public String getIcons() {
        return icons;
    }

    public void setIcons(String icons) {
        this.icons = icons;
    }

    public AccounInfoDto(AccounInfo info) {
        if (info != null) {
            if (info.getId() != null && !info.getId().isEmpty())
                this.setId(info.getId());
            this.setAccounName(info.getAccounName());
            this.setBookSet(info.getBookSet());
            this.setRemarks(info.getRemarks());
            this.setZtbz(info.getZtbz());
            this.setCorpName(info.getCorpName());
            this.setIcons(info.getIcons());
            if (info.getRegTime() != null)
                this.setRegTime(BeanUtil.timestampToStr(info.getRegTime(), "yyyy-MM-dd HH:mm"));
        }
    }

    public List<AccounInfoDto> listAccounInfoDto(List<AccounInfo> accounInfoList) {
        List<AccounInfoDto> list = new ArrayList<>();
        if (accounInfoList.size() > 0) {
            for (AccounInfo mast : accounInfoList) {
                list.add(new AccounInfoDto(mast));
            }
        }
        return list;
    }

    public AccounInfo getAccounInfo(AccounInfoDto dto) {
        AccounInfo info = new AccounInfo();
        if (dto != null) {
            if (dto.getId() != null && !dto.getId().isEmpty())
                info.setId(dto.getId());
            info.setAccounName(dto.getAccounName());
            info.setBookSet(dto.getBookSet());
            info.setRemarks(dto.getRemarks());
            info.setZtbz(dto.getZtbz());
            info.setCorpName(dto.getCorpName());
            info.setIcons(dto.getIcons());
        }
        return info;
    }


}
