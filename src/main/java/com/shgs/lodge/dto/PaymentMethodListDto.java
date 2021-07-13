package com.shgs.lodge.dto;

import com.shgs.lodge.primary.entity.PaymentMethod;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class PaymentMethodListDto {
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

    public PaymentMethodListDto() {
        super();
    }

    public PaymentMethodListDto(PaymentMethod mast) {
        String pid;
        this.setId(mast.getId());
        this.setPaymentBh(mast.getPaymentBh());
        this.setContents(mast.getContents());
        if (mast.getParent() != null) {
            PaymentMethod parent = mast.getParent();
            this.setParent_id(parent.getId());
            pid = parent.getId();
        } else {
            this.setParent_id(null);
            pid = "";
        }
        String name = StringUtils.isNotEmpty(mast.getPaymentBh())?mast.getPaymentBh()+":"+mast.getPaymentName():mast.getPaymentName();
        if ("T".equals(mast.getContents())) {
            this.setPaymentName("<a href='javascript:void(0)' onclick='findPayment(&quot;" + mast.getId() + "&quot;,&quot;" + pid + "&quot;);'><i class='fa fa-caret-right fa-lg'></i> " +name + "</a>");
        } else {
            this.setPaymentName(name);
            this.setHandle("<div style='padding-right:10px;'><a href='javascript:void(0)' onclick='getPaymentIdAndName(&quot;" + mast.getPaymentName() + "&quot;);'>选择</a></div>");
        }

    }

    public List<PaymentMethodListDto> listPaymentMethodListDto(List<PaymentMethod> list) {
        List<PaymentMethodListDto> cts = new ArrayList<>();
        if (list.size() > 0) {
            for (PaymentMethod mast : list) {
                cts.add(new PaymentMethodListDto(mast));
            }
        }
        return cts;
    }
}
