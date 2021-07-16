package com.shags.lodge.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shags.lodge.util.BeanUtil;
import com.shags.lodge.util.CmsUtils;
import com.shags.lodge.util.HeaderEnum;
import com.shags.lodge.primary.entity.AccounPeriod;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

public class AccounPeriodDto {

    /**
     * 关键字
     */
    private String id;

    /**
     * 会计月份
     */
    @HeaderEnum(field = "month", title = "会计期间")
    @NotEmpty(message = "会计期间不能为空")
    private String month;

    /**
     * 开始时间
     */
    @HeaderEnum(field = "startTime", title = "开始日期", width = 120)
    @NotEmpty(message = "开始日期不能为空")
    private String startTime;

    /**
     * 结束时间
     */
    @HeaderEnum(field = "endTime", title = "结束日期", width = 120)
    @NotEmpty(message = "开始日期不能为空")
    private String endTime;

    /**
     * 状态标识
     */
    @JsonIgnore
    private String ztbz;


    @JsonProperty("ztbz")
    @HeaderEnum(field = "ztbz", title = "状态", width = 60)
    private String formatter;


    @HeaderEnum(field = "handle", title = "关联操作", width = 120, sortable = false)
    private String handle;

    public AccounPeriodDto() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getZtbz() {
        return ztbz;
    }

    public void setZtbz(String ztbz) {
        this.ztbz = ztbz;
    }

    public String getHandle() {
        return CmsUtils.formatHandle(this.id);
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getFormatter() {
        return formatter;
    }

    public void setFormatter(String formatter) {
        this.formatter = formatter;
    }

    public AccounPeriodDto(AccounPeriod accounPeriod) {
        this.setId(accounPeriod.getId());
        this.setMonth(accounPeriod.getMonth());
        this.setZtbz(accounPeriod.getZtbz());
        if (accounPeriod.getStartTime() != null)
            this.setStartTime(BeanUtil.timestampToStr(accounPeriod.getStartTime(), "yyyy-MM-dd"));
        if (accounPeriod.getEndTime() != null)
            this.setEndTime(BeanUtil.timestampToStr(accounPeriod.getEndTime(), "yyyy-MM-dd"));
        if ("T".equals(accounPeriod.getZtbz())) {
            this.setFormatter("<i class='fa fa-check fa-fw green '></i>");
        } else {
            this.setFormatter("<i class='fa fa-close fa-fw red'></i>");
        }

    }

    public List<AccounPeriodDto> listAccounPeriodDto(List<AccounPeriod> list) {
        List<AccounPeriodDto> cts = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (AccounPeriod mast : list) {
                cts.add(new AccounPeriodDto(mast));
            }
        }
        return cts;
    }

    public AccounPeriod getAccounPeriod(AccounPeriodDto dto) {
        AccounPeriod mast = new AccounPeriod();
        if (dto != null) {
            mast.setMonth(dto.getMonth());
            mast.setZtbz(dto.getZtbz());
            mast.setId(StringUtils.isNotEmpty(dto.getId()) ? dto.getId() : null);
            if (StringUtils.isNotEmpty(dto.getStartTime()))
                mast.setStartTime(BeanUtil.strToTimestampDay(dto.getStartTime()));
            if (StringUtils.isNotEmpty(dto.getEndTime()))
                mast.setEndTime(BeanUtil.strToTimestampDay(dto.getEndTime()));
        }
        return mast;
    }

}
