package com.shgs.lodge.dto;

import com.shgs.lodge.primary.entity.ManagePoint;
import com.shgs.lodge.util.CmsUtils;
import com.shgs.lodge.util.HeaderEnum;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yglei
 * @Title: 管理处实体类Dto
 * @date 2021-07-09 11:05
 */
public class ManagePointDto {

    /**
     * 关键字
     */
    private String id;

    /**
     * 编号
     */
    @NotEmpty(message = "编号不能为空")
    @HeaderEnum(field = "bh", title = "编号",  sortable = false)
    private String bh;

    /**
     * 名称
     */
    @NotEmpty(message = "名称不能为空")
    @HeaderEnum(field = "name", title = "名称", width = 200, sortable = false)
    private String name;

    /**
     * 状态标识
     */
    @HeaderEnum(field = "ztbz", title = "状态标识", sortable = false)
    private String ztbz;

    /**
     * 时间戳
     */
    private String rVTime;

    /**
     * 账套编号
     */
    private String bookSet;

    @HeaderEnum(field = "handle", title = "关联操作", width = 150, sortable = false, hidden = false)
    private String handle;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBh() {
        return bh;
    }

    public void setBh(String bh) {
        this.bh = bh;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZtbz() {
        return ztbz;
    }

    public void setZtbz(String ztbz) {
        this.ztbz = ztbz;
    }

    public String getrVTime() {
        return rVTime;
    }

    public void setrVTime(String rVTime) {
        this.rVTime = rVTime;
    }

    public String getBookSet() {
        return bookSet;
    }

    public void setBookSet(String bookSet) {
        this.bookSet = bookSet;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public ManagePointDto() {
        super();
    }

    public ManagePointDto(ManagePoint managePoint) {
        this.setId(managePoint.getId());
        this.setBh(managePoint.getBh());
        if("T".equals(managePoint.getZtbz())){
           this.setZtbz("<i class='fa fa-check fa-fw green '></i>");
        }else{
            this.setZtbz("<i class='fa fa-close fa-fw red'></i>");
        }
        this.setBookSet(managePoint.getBookSet());
        this.setName(managePoint.getName());
        this.setHandle(CmsUtils.formatHandle(this.id));
    }

    public List<ManagePointDto> listManagePointDto(List<ManagePoint> list) {
        List<ManagePointDto> dto = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (ManagePoint mast : list) {
                dto.add(new ManagePointDto(mast));
            }
        }
        return dto;
    }

    public ManagePoint getManagePoint(ManagePointDto dto) {
        ManagePoint managePoint = new ManagePoint();
        if (dto != null) {
            if (StringUtils.isNotEmpty(dto.getId()))
                managePoint.setId(dto.getId());
            managePoint.setBh(dto.getBh());
            managePoint.setName(dto.getName());
            managePoint.setBookSet(dto.getBookSet());
            managePoint.setZtbz(dto.getZtbz());
        }
        return managePoint;
    }


}
