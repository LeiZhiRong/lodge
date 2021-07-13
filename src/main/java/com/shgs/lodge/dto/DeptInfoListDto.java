package com.shgs.lodge.dto;

import com.shgs.lodge.primary.entity.DeptInfo;

import java.util.ArrayList;
import java.util.List;

public class DeptInfoListDto {

    /**
     * 关键字
     */
    private String id;

    /**
     * 科目编号
     */
    private String deptID;

    /**
     * 科目名称
     */
    private String deptName;

    /**
     * 上级科目ID
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

    public String getDeptID() {
        return deptID;
    }

    public void setDeptID(String deptID) {
        this.deptID = deptID;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
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

    public DeptInfoListDto() {
        super();
    }

    public DeptInfoListDto(DeptInfo deptInfo) {
        String pid;
        this.setId(deptInfo.getId());
        this.setDeptName(deptInfo.getDeptName());
        this.setDeptID(deptInfo.getDeptID());
        this.setContents(deptInfo.getContents());
        if (deptInfo.getParent() != null) {
            DeptInfo parent = deptInfo.getParent();
            this.setParent_id(parent.getId());
            pid = parent.getId();
        } else {
            this.setParent_id(null);
            pid = "";
        }
        String str = deptInfo.getDeptID() + "\\" + deptInfo.getDeptName();
        String _str=str.replace("\\","\\\\");
        if ("T".equals(deptInfo.getContents())) {
            this.setDeptName("<a href='javascript:void(0)' onclick='findDeptInfo(&quot;" + deptInfo.getId() + "&quot;,&quot;" + pid + "&quot;);'><i class='fa fa-caret-right fa-lg'></i> " + str + "</a>");
        } else {
            this.setDeptName(str);
        }
        this.setHandle("<div style='padding-right:10px;'><a href='javascript:void(0)' onclick='getDeptInfoIdAndName(&quot;" + deptInfo.getId() + "&quot;,&quot;" +  _str + "&quot;);'>选择</a></div>");


    }

    public List<DeptInfoListDto> listDeptInfoListDto(List<DeptInfo> deptInfoList) {
        List<DeptInfoListDto> list = new ArrayList<>();
        if (deptInfoList.size() > 0) {
            for (DeptInfo mast : deptInfoList) {
                list.add(new DeptInfoListDto(mast));
            }
        }
        return list;
    }
}
