package com.shags.lodge.dto.business;

import com.shags.lodge.primary.entity.ManagePoint;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yglei
 * @Title: 管理处实体类Dto
 * @date 2021-07-09 11:05
 */
public class ManagePointListDto {

    /**
     * 关键字
     */
    private String id;

    /**
     * 编号
     */
    private String bh;

    /**
     * 名称
     */
    private String name;

    /**
     * 操作
     */
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

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public ManagePointListDto() {
        super();
    }

    public ManagePointListDto(ManagePoint managePoint) {
        this.setId(managePoint.getId());
        this.setBh(managePoint.getBh());
        this.setName(managePoint.getName());
        this.setHandle("<div style='padding-right:10px;'><a href='javascript:void(0)' onclick='getManagePointInfoIdAndName(&quot;" + managePoint.getId() + "&quot;,&quot;" +  managePoint.getName() + "&quot;,&quot;" +  managePoint.getBh() + "&quot;);'><i class='fa fa-caret-right fa-lg'></i>选择</a></div>");
    }

    public List<ManagePointListDto> listManagePointDto(List<ManagePoint> list) {
        List<ManagePointListDto> dto = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (ManagePoint mast : list) {
                dto.add(new ManagePointListDto(mast));
            }
        }
        return dto;
    }


}
