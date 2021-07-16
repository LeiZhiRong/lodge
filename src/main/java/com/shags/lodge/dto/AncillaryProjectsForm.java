package com.shags.lodge.dto;

import com.shags.lodge.primary.entity.AncillaryProjects;
import org.apache.commons.lang3.StringUtils;

public class AncillaryProjectsForm {

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
    private String pid;

    /**
     * 所属类型ID
     */
    private String t_id;

    /**
     * 是否为目录
     */
    private String contents;

    /**
     * 状态标识
     */
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

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getT_id() {
        return t_id;
    }

    public void setT_id(String t_id) {
        this.t_id = t_id;
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

    public AncillaryProjectsForm() {
        super();
    }

    public String getProjectsBh() {
        return projectsBh;
    }

    public void setProjectsBh(String projectsBh) {
        this.projectsBh = projectsBh;
    }

    public AncillaryProjectsForm(AncillaryProjects cts) {
        this.setId(cts.getId());
        this.setContents(cts.getContents());
        this.setZtbz(cts.getZtbz());
        this.setProjectsBh(cts.getProjectsBh());
        this.setProjectsName(cts.getProjectsName());
        if (cts.getParent() != null)
            this.setPid(cts.getParent().getId());
        if (cts.getProjectType() != null)
            this.setT_id(cts.getProjectType().getId());

    }

    public AncillaryProjects getAncillaryProjects(AncillaryProjectsForm dto) {
        AncillaryProjects mast = new AncillaryProjects();
        if (dto != null) {
            mast.setId(StringUtils.isNotEmpty(dto.getId()) ? dto.getId() : null);
            mast.setZtbz(dto.getZtbz());
            mast.setContents(dto.getContents());
            mast.setProjectsName(dto.getProjectsName());
            mast.setProjectsBh(dto.getProjectsBh());
        }
        return mast;
    }
}
