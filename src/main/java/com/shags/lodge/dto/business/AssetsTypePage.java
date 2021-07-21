package com.shags.lodge.dto.business;

import com.shags.lodge.business.entity.AssetsType;
import com.shags.lodge.dto.DeptInfoListDto;
import com.shags.lodge.primary.entity.DeptInfo;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yglei
 * @Title: 资产分类-分页
 * @date 2021-07-219:21
 */
public class AssetsTypePage {

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
    private String parent_id;

    /**
     * 所属大类名称
     */
    private String parent_name;

    /**
     * 是否为目录
     */
    private String contents;

    /**
     * 状态标志
     */
    private String ztBz;


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

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getParent_name() {
        return parent_name;
    }

    public void setParent_name(String parent_name) {
        this.parent_name = parent_name;
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

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public AssetsTypePage() {
        super();
    }

    public AssetsTypePage(AssetsType assetsType) {
        this.setId(assetsType.getId());
        this.setZtBz("T".equals(assetsType.getZtBz()) ? "<i class='fa fa-check  fa-lg green '></i>" : "<i class='fa fa-close fa-lg red '></i>");
        this.setContents(assetsType.getContents());
        if ("T".equals(assetsType.getContents())) {
            this.setName("<a href='javascript:void(0)' onclick='findAssetsType(&quot;" + assetsType.getId() + "&quot;);'><i class='fa fa-folder-open-o fa-lg'></i> " + assetsType.getName() + "</a>");
        } else {
            this.setName(assetsType.getName());
        }
        if (assetsType.getParent() != null) {
            AssetsType parent = assetsType.getParent();
            this.setParent_id(parent.getId());
            if (parent.getParent() != null) {
                this.setParent_name("<a href='javascript:void(0)' onclick='findAssetsType(&quot;" + parent.getParent().getId() + "&quot;);'><i class='fa  fa-folder-open-o  fa-lg'></i> " + parent.getName() + "</a>");
            } else {
                this.setParent_name("<a href='javascript:void(0)' onclick='findAssetsType(null);'><i class='fa fa-folder-open-o fa-lg'></i> " + parent.getName() + "</a>");
            }
        } else {
            this.setParent_id(null);
            this.setParent_name("");
        }

    }

    public List<AssetsTypePage> listAssetsTypePage(List<AssetsType> assetsTypeList) {
        List<AssetsTypePage> list = new ArrayList<>();
        if (assetsTypeList.size() > 0) {
            for (AssetsType mast : assetsTypeList) {
                list.add(new AssetsTypePage(mast));
            }
        }
        return list;
    }



}
