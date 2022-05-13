package com.shags.lodge.dto.business;

import com.shags.lodge.business.entity.SitDown;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yglei
 * @Title: 坐落位置实体类Dto
 * @date 2021-07-09 11:05
 */
public class SitDownInfoListDto {

    /**
     * 关键字
     */
    private String id;

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

    public SitDownInfoListDto() {
        super();
    }

    public SitDownInfoListDto(SitDown sitDown) {
        this.setId(sitDown.getId());
        this.setName(sitDown.getName());
        this.setHandle("<div style='padding-right:10px;'><a href='javascript:void(0)' onclick='getSitDownInfoIdAndName(&quot;" + sitDown.getId() + "&quot;,&quot;" +  sitDown.getName() + "&quot;);'><i class='fa fa-caret-right fa-lg'></i>选择</a></div>");
    }

    public List<SitDownInfoListDto> listSitDownInfoListDto(List<SitDown> list) {
        List<SitDownInfoListDto> dto = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (SitDown mast : list) {
                dto.add(new SitDownInfoListDto(mast));
            }
        }
        return dto;
    }

}
