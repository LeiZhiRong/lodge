package com.shgs.lodge.dto;

import com.shgs.lodge.primary.entity.CustomType;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

public class CustomTypeDto {
    /**
     * 关键字
     */
    private String id;
    /**
     * 类型名称
     */
    @NotEmpty(message = "名称不能为空")
    private String typeName;

    /**
     * 类型编码
     */
    @NotEmpty(message = "编码不能为空")
    private String typeCode;

    /**
     * 所属类型
     */
    private String pid;

    /**
     * 排序号
     */
    private Integer orders;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Integer getOrders() {
        return orders;
    }

    public void setOrders(Integer orders) {
        this.orders = orders;
    }

    public CustomTypeDto() {
        super();
    }

    public CustomTypeDto(CustomType type) {
        this.setId(type.getId());
        this.setTypeCode(type.getTypeCode());
        this.setTypeName(type.getTypeName());
        this.setOrders(type.getOrders());
        if (type.getParent() != null)
            this.setPid(type.getParent().getId());
    }

    public List<CustomTypeDto> listCustomTypeDto(List<CustomType> list) {
        List<CustomTypeDto> dts = new ArrayList<>();
        if (list.size() > 0) {
            for (CustomType mast : list) {
                dts.add(new CustomTypeDto(mast));
            }
        }
        return dts;
    }

    public CustomType getCustomType(CustomTypeDto dto) {
        CustomType customType = new CustomType();
        if (dto != null) {
            if (StringUtils.isNotEmpty(dto.getId()))
                customType.setId(dto.getId());
            customType.setTypeName(dto.getTypeName());
            customType.setTypeCode(dto.getTypeCode());
            if (dto.getOrders() != null)
                customType.setOrders(dto.getOrders());
        }
        return customType;
    }

}
