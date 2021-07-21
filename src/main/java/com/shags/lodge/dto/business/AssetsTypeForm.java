package com.shags.lodge.dto.business;

import com.shags.lodge.business.entity.AssetsType;
import org.apache.commons.lang3.StringUtils;

public class AssetsTypeForm {

    /**
     * 关键字
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 所属大类ID
     */
    private String pid;

    /**
     * 是否为目录
     */
    private String contents;

    /**
     * 状态标志
     */
    private String ztBz;


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

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getZtBz() {
        return ztBz;
    }

    public void setZtBz(String ztBz) {
        this.ztBz = ztBz;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public AssetsTypeForm() {
        super();
    }


    public AssetsTypeForm(AssetsType assetsType) {
        this.setId(assetsType.getId());
        this.setContents(assetsType.getContents());
        this.setZtBz(assetsType.getZtBz());
        this.setName(assetsType.getName());
        if(assetsType.getParent()!=null){
           this.setPid(assetsType.getParent().getId());
        }
    }
    public  AssetsType getAssetsType(AssetsTypeForm dto){
        AssetsType assetsType=new AssetsType();
        if(dto!=null){
            assetsType.setId(StringUtils.isNotEmpty(dto.getId())?dto.getId():null);
            assetsType.setZtBz(dto.getZtBz());
            assetsType.setName(dto.getName());
            assetsType.setContents(dto.getContents());
        }
        return assetsType;
    }


}
