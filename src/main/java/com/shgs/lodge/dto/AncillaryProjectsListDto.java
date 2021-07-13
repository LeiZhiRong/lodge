package com.shgs.lodge.dto;

import com.shgs.lodge.primary.entity.AncillaryProjects;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class AncillaryProjectsListDto {
    /**
     * 关键字
     */
    private String id;

    /**
     * 项目名称
     */
    private String projectsName;


    /**
     * 上级ID
     */
    private String parent_id;

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

    public AncillaryProjectsListDto() {
        super();
    }

    public AncillaryProjectsListDto(AncillaryProjects mast) {
        String pid;
        String name = mast.getProjectsName().replace("\\", "\\\\");
        this.setId(mast.getId());
        this.setContents(mast.getContents());
        if (mast.getParent() != null) {
            AncillaryProjects parent = mast.getParent();
            this.setParent_id(parent.getId());
            pid = parent.getId();
        } else {
            this.setParent_id(null);
            pid = "";
        }
        String str = mast.getProjectsName();
        if (StringUtils.isNotEmpty(mast.getProjectsBh())) {
            str = mast.getProjectsBh() + "\\" + mast.getProjectsName();
        }
        if ("T".equals(mast.getContents())) {
            this.setProjectsName("<a href='javascript:void(0)' onclick='findProjects(&quot;" + mast.getId() + "&quot;,&quot;" + pid + "&quot;);'><i class='fa fa-caret-right fa-lg'></i> " + str + "</a>");
        } else {
            String _str = str.replace("\\", "\\\\");
            this.setProjectsName(str);
            this.setHandle("<div style='padding-right:10px;'><a href='javascript:void(0)' onclick='getCashBnkIdAndBh(&quot;" + _str + "&quot;);'>选择</a></div>");
        }

    }

    public List<AncillaryProjectsListDto> listAncillaryProjectsListDto(List<AncillaryProjects> list) {
        List<AncillaryProjectsListDto> cts = new ArrayList<>();
        if (list.size() > 0) {
            for (AncillaryProjects mast : list) {
                cts.add(new AncillaryProjectsListDto(mast));
            }
        }
        return cts;
    }

}
