package com.shgs.lodge.dto;

import com.shgs.lodge.primary.entity.ProceedType;

import java.util.ArrayList;
import java.util.List;

public class ProceedTypeListDto {

    /**
     * 关键字
     */
    private String id;

    /**
     * 科目编号
     */
    private String proceedBh;

    /**
     * 科目名称
     */
    private String name;

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

    public String getName() {
        return name;
    }

    public String getProceedBh() {
        return proceedBh;
    }

    public void setProceedBh(String proceedBh) {
        this.proceedBh = proceedBh;
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

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }


    public ProceedTypeListDto() {
        super();
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public ProceedTypeListDto(ProceedType proceedType) {
        String pid = null;
        this.setId(proceedType.getId());
        this.setProceedBh(proceedType.getProceedBh());
        this.setContents(proceedType.getContents());
        if (proceedType.getParent() != null) {
            ProceedType parent = proceedType.getParent();
            this.setParent_id(parent.getId());
            pid = parent.getId();
        } else {
            this.setParent_id(null);
            pid = "";
        }
        if ("T".equals(proceedType.getContents())) {
            this.setName("<a href='javascript:void(0)' onclick='findProceed(&quot;" + proceedType.getId() + "&quot;,&quot;" + pid + "&quot;);'><i class='fa fa-caret-right fa-lg'></i> " + proceedType.getProceedBh() + "\\" + proceedType.getName() + "</a>");
        } else {
            String str = proceedType.getParent() != null ? proceedType.getProceedBh() + "\\" + proceedType.getParent().getName() + "\\" + proceedType.getName() : proceedType.getProceedBh() + "\\" + proceedType.getName();
            this.setName(str);
            this.setHandle("<div style='padding-right:10px;'><a href='javascript:void(0)' onclick='getProceedIdAndBh(&quot;" + proceedType.getId() + "&quot;,&quot;" + proceedType.getName() + "&quot;);'>选择</a></div>");
        }
    }

    public List<ProceedTypeListDto> listProceedTypeListDto(List<ProceedType> proceedTypeList) {
        List<ProceedTypeListDto> list = new ArrayList<ProceedTypeListDto>();
        if (proceedTypeList.size() > 0) {
            for (ProceedType mast : proceedTypeList) {
                list.add(new ProceedTypeListDto(mast));
            }
        }
        return list;
    }
}
