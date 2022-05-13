package com.shags.lodge.dto.business;

import com.shags.lodge.business.entity.AssetsType;

import java.util.ArrayList;
import java.util.List;

public class AssetsTypeListDto {

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
     * 上级ID
     */
    private String parent_id;

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

    /**
     * 是否为目录
     */
    private String contents;

    private String handle;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public AssetsTypeListDto() {
        super();
    }

    public AssetsTypeListDto(AssetsType assetsType) {
        String pid;
        this.setId(assetsType.getId());
        this.setName(assetsType.getName());
        this.setBh(assetsType.getBh());
        this.setContents(assetsType.getContents());
        if (assetsType.getParent() != null) {
            AssetsType parent = assetsType.getParent();
            this.setParent_id(parent.getId());
            pid = parent.getId();
        } else {
            this.setParent_id(null);
            pid = "";
        }
        if ("T".equals(assetsType.getContents())) {
            this.setName("<a href='javascript:void(0)' onclick='findAssetsTypeInfo(&quot;" + assetsType.getId() + "&quot;,&quot;" + pid + "&quot;);'><i class='fa fa-caret-right fa-lg'></i> " + assetsType.getName() + "</a>");
        } else {
            this.setHandle("<div style='padding-right:10px;'><a href='javascript:void(0)' onclick='getAssetsTypeIdAndName(&quot;" + assetsType.getId() + "&quot;,&quot;" + assetsType.getName() + "&quot;,&quot;" + assetsType.getBh() + "&quot;);'><i class='fa fa-caret-right fa-lg'></i>选择</a></div>");
        }


    }

    public List<AssetsTypeListDto> listAssetsTypeListDto(List<AssetsType> assetsTypeList) {
        List<AssetsTypeListDto> list = new ArrayList<>();
        if (assetsTypeList.size() > 0) {
            for (AssetsType mast : assetsTypeList) {
                list.add(new AssetsTypeListDto(mast));
            }
        }
        return list;
    }


}
