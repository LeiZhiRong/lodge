package com.shgs.lodge.dto;

import com.shgs.lodge.primary.entity.PaymentMethod;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class PaymentMethodDto {

    /**
     * 关键字
     */
    private String id;

    /**
     * 编号
     */
    private String paymentBh;

    /**
     * 名称
     */
    private String paymentName;

    /**
     * 上级ID
     */
    private String parent_id;

    /**
     * 上级名称
     */
    private String parent_name;

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

    public String getPaymentBh() {
        return paymentBh;
    }

    public void setPaymentBh(String paymentBh) {
        this.paymentBh = paymentBh;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
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

    public PaymentMethodDto() {
        super();
    }

    public PaymentMethodDto(PaymentMethod mast) {
        this.setId(mast.getId());
        this.setZtbz("T".equals(mast.getZtbz()) ? "<i class='fa fa-check  fa-lg  '></i>" : "<i class='fa fa-close fa-lg '></i>");
        this.setContents(mast.getContents());
        if ("T".equals(mast.getContents())) {
            this.setPaymentName("<a href='javascript:void(0)' onclick='findPaymentMethod(&quot;" + mast.getId() + "&quot;);'><i class='fa fa-folder-open-o fa-lg'></i> " + mast.getPaymentName() + "</a>");
        } else {
            this.setPaymentName(mast.getPaymentName());
        }
        this.setOrders(mast.getOrders());
        this.setPaymentBh(mast.getPaymentBh());
        this.setOrders(mast.getOrders());
        if (mast.getParent() != null) {
            PaymentMethod parent = mast.getParent();
            this.setParent_id(parent.getId());
            if (parent.getParent() != null) {
                this.setParent_name("<a href='javascript:void(0)' onclick='findPaymentMethod(&quot;" + parent.getParent().getId() + "&quot;);'><i class='fa  fa-folder-open-o  fa-lg'></i> " + parent.getPaymentName() + "</a>");
            } else {
                this.setParent_name("<a href='javascript:void(0)' onclick='findPaymentMethod(null);'><i class='fa fa-folder-open-o fa-lg'></i> " + parent.getPaymentName() + "</a>");
            }
        } else {
            this.setParent_id(null);
            this.setParent_name("");
        }
    }

    public List<PaymentMethodDto> listPaymentMethodDto(List<PaymentMethod> list) {
        List<PaymentMethodDto> cts = new ArrayList<PaymentMethodDto>();
        if (list.size() > 0) {
            for (PaymentMethod mast : list) {
                cts.add(new PaymentMethodDto(mast));
            }
        }
        return cts;
    }

    public PaymentMethod getPaymentMethod(PaymentMethodDto dto) {
        PaymentMethod mast = new PaymentMethod();
        if (dto != null) {
            mast.setId(StringUtils.isNotEmpty(dto.getId()) ? dto.getId() : null);
            mast.setZtbz(dto.getZtbz());
            mast.setContents(dto.getContents());
            mast.setPaymentBh(dto.getPaymentBh());
            mast.setPaymentName(dto.getPaymentName());
        }
        return mast;
    }

}
