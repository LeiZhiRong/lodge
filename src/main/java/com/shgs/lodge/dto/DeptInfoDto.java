package com.shgs.lodge.dto;

import com.shgs.lodge.primary.entity.DeptInfo;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

public class DeptInfoDto {
    /**
     * 关键字
     */
    private String id;
    /**
     * 部门编号
     */
    @NotEmpty(message = "部门编号不能为空")
    private String deptID;

    /**
     * 部门名称
     */
    @NotEmpty(message = "部门名称不能为空")
    private String deptName;
    /**
     * 上级部门ID
     */
    private String parent_id;

    /**
     * 上级部门名称
     */
    private String parent_name;

    /**
     * 部门关联ids
     */
    private String ids;

    /**
     * 排序号
     */
    private Integer orders;

    /**
     * 启用状态
     */
    private Integer status = 0;

    /**
     * 状态格式化
     */
    private String status2str;
    /**
     * 是否为目录
     */
    private String contents;

    public DeptInfoDto() {
        super();
    }

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatus2str() {
        return status2str;
    }

    public void setStatus2str(String status2str) {
        this.status2str = status2str;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }


    public DeptInfoDto(DeptInfo deptInfo) {
        this.setId(deptInfo.getId());
        this.setStatus2str(deptInfo.getStatus() == 1 ? "<i class='fa fa-check  fa-lg  '></i>" : "<i class='fa fa-close fa-lg '></i>");
        this.setContents(deptInfo.getContents());
        if ("T".equals(deptInfo.getContents())) {
            this.setDeptName("<a href='javascript:void(0)' onclick='findDeptInfo(&quot;" + deptInfo.getId() + "&quot;);'><i class='fa fa-folder-open-o fa-lg'></i> " + deptInfo.getDeptName() + "</a>");
        } else {
            this.setDeptName(deptInfo.getDeptName());
        }
        this.setOrders(deptInfo.getOrders());
        this.setDeptID(deptInfo.getDeptID());
         if (deptInfo.getParent() != null) {
            DeptInfo parent = deptInfo.getParent();
            this.setParent_id(parent.getId());
            if (parent.getParent() != null) {
                this.setParent_name("<a href='javascript:void(0)' onclick='findDeptInfo(&quot;" + parent.getParent().getId() + "&quot;);'><i class='fa  fa-folder-open-o  fa-lg'></i> " + parent.getDeptName() + "</a>");
            } else {
                this.setParent_name("<a href='javascript:void(0)' onclick='findDeptInfo(null);'><i class='fa fa-folder-open-o fa-lg'></i> " + parent.getDeptName() + "</a>");
            }
        } else {
            this.setParent_id(null);
            this.setParent_name("");
        }
    }

    public List<DeptInfoDto> listDeptInfoDto(List<DeptInfo> deptInfoList) {
        List<DeptInfoDto> list = new ArrayList<DeptInfoDto>();
        if (deptInfoList.size() > 0) {
            for (DeptInfo mast : deptInfoList) {
                list.add(new DeptInfoDto(mast));
            }
        }
        return list;
    }

    public DeptInfo getDeptInfo(DeptInfoDto dto) {
        DeptInfo deptInfo = new DeptInfo();
        if (dto != null) {
            deptInfo.setId(dto.getId() != null && !dto.getId().isEmpty() ? dto.getId() : null);
            deptInfo.setOrders(dto.getOrders());
            deptInfo.setStatus(dto.getStatus());
            deptInfo.setContents(dto.getContents());
            deptInfo.setDeptName(dto.getDeptName());
            deptInfo.setDeptID(dto.getDeptID());
         }
        return deptInfo;

    }
    
}
