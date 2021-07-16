package com.shags.lodge.dto;

import com.shags.lodge.primary.entity.AncillaryProjects;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class AncillaryProjectsDto {
    /**
     * 关键字
     */
    private String id;

    /**
     * 项目编号
     */
    private String projectsBh;

    /**
     * 项目名称
     */
    private String projectsName;

    /**
     * 上级ID
     */
    private String parent_id;

    /**
     * 上级名称
     */
    private String parent_name;

    /**
     * 所属分类
     */
    private String  t_name;

    /**
     * 项目关联ids
     */
    private String ids;

    /**
     * 排序号
     */
    private Integer orders;

    /**
     * 是否为目录
     */
    private String contents;

    private String ztbz;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectsName() {
        return projectsName;
    }

    public void setProjectsName(String projectsName) {
        this.projectsName = projectsName;
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

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public Integer getOrders() {
        return orders;
    }

    public void setOrders(Integer orders) {
        this.orders = orders;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getZtbz() {
        return ztbz;
    }

    public void setZtbz(String ztbz) {
        this.ztbz = ztbz;
    }

    public AncillaryProjectsDto() {
        super();
    }

    public String getProjectsBh() {
        return projectsBh;
    }

    public void setProjectsBh(String projectsBh) {
        this.projectsBh = projectsBh;
    }

    public String getT_name() {
        return t_name;
    }

    public void setT_name(String t_name) {
        this.t_name = t_name;
    }

    public AncillaryProjectsDto(AncillaryProjects ancillaryProjects) {
        this.setId(ancillaryProjects.getId());
        this.setZtbz("T".equals(ancillaryProjects.getZtbz()) ? "<i class='fa fa-check  fa-lg green '></i>" : "<i class='fa fa-close fa-lg red '></i>");
        this.setContents(ancillaryProjects.getContents());
        if ("T".equals(ancillaryProjects.getContents())) {
            this.setProjectsName("<a href='javascript:void(0)' onclick='findAncillaryProjects(&quot;" + ancillaryProjects.getId() + "&quot;);'><i class='fa fa-folder-open-o fa-lg'></i> " + ancillaryProjects.getProjectsName() + "</a>");
        } else {
            this.setProjectsName(ancillaryProjects.getProjectsName());
        }
        this.setOrders(ancillaryProjects.getOrders());
        this.setProjectsBh(ancillaryProjects.getProjectsBh());
        if(ancillaryProjects.getProjectType()!=null)
            this.setT_name(ancillaryProjects.getProjectType().getParameName());
        if (ancillaryProjects.getParent() != null) {
            AncillaryProjects parent = ancillaryProjects.getParent();
            this.setParent_id(parent.getId());
            if (parent.getParent() != null) {
                this.setParent_name("<a href='javascript:void(0)' onclick='findAncillaryProjects(&quot;" + parent.getParent().getId() + "&quot;);'><i class='fa  fa-folder-open-o  fa-lg'></i> " + parent.getProjectsName() + "</a>");
            } else {
                this.setParent_name("<a href='javascript:void(0)' onclick='findAncillaryProjects(null);'><i class='fa fa-folder-open-o fa-lg'></i> " + parent.getProjectsName() + "</a>");
            }
        } else {
            this.setParent_id(null);
            this.setParent_name("");
        }
    }

    public List<AncillaryProjectsDto> listAncillaryProjectsDto(List<AncillaryProjects> ancillaryProjects) {
        List<AncillaryProjectsDto> list = new ArrayList<>();
        if (ancillaryProjects.size() > 0) {
            for (AncillaryProjects mast : ancillaryProjects) {
                list.add(new AncillaryProjectsDto(mast));
            }
        }
        return list;
    }

    public AncillaryProjects getAncillaryProjects(AncillaryProjectsDto dto) {
        AncillaryProjects mast = new AncillaryProjects();
        if (dto != null) {
            mast.setId(StringUtils.isNotEmpty(dto.getId()) ? dto.getId() : null);
            mast.setZtbz(dto.getZtbz());
            mast.setContents(dto.getContents());
            mast.setProjectsName(dto.getProjectsName());
        }
        return mast;
    }


}
