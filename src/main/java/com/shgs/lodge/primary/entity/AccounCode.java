package com.shgs.lodge.primary.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "accoun_code")
@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
public class AccounCode {

    /**
     * ID
     */
    private String id;

    /**
     * 系统名称
     */
    private String bookSet;

    /**
     * 编号类型
     */
    private CustomParame codeType;

    /**
     * 前缀一
     */
    private CustomParame prefixOne;

    /**
     * 前缀一编码
     */
    private String prefixOneCode;

    /**
     * 前缀二
     */
    private CustomParame prefixTwo;

    /**
     * 前缀二编码
     */
    private String prefixTwoCode;

    /**
     * 前缀三
     */
    private CustomParame prefixThree;

    /**
     * 前缀三编码
     */
    private String prefixThreeCode;

    /**
     * 前缀四
     */
    private CustomParame prefixFour;

    /**
     * 前缀四编码
     */
    private String prefixFourCode;

    /**
     * 流水号
     */
    private int jhSerialLength = 3;

    /**
     * 分隔符
     */
    private CustomParame separator;


    @Id
    @GeneratedValue(generator = "uuid2")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookSet() {
        return bookSet;
    }

    public void setBookSet(String bookSet) {
        this.bookSet = bookSet;
    }

    @ManyToOne
    @JoinColumn(name = "codeType")
    public CustomParame getCodeType() {
        return codeType;
    }

    public void setCodeType(CustomParame codeType) {
        this.codeType = codeType;
    }

    @ManyToOne
    @JoinColumn(name = "prefixOne")
    public CustomParame getPrefixOne() {
        return prefixOne;
    }

    public void setPrefixOne(CustomParame prefixOne) {
        this.prefixOne = prefixOne;
    }

    public String getPrefixOneCode() {
        return prefixOneCode;
    }

    public void setPrefixOneCode(String prefixOneCode) {
        this.prefixOneCode = prefixOneCode;
    }

    @ManyToOne
    @JoinColumn(name = "prefixTwo")
    public CustomParame getPrefixTwo() {
        return prefixTwo;
    }

    public void setPrefixTwo(CustomParame prefixTwo) {
        this.prefixTwo = prefixTwo;
    }

    public String getPrefixTwoCode() {
        return prefixTwoCode;
    }

    public void setPrefixTwoCode(String prefixTwoCode) {
        this.prefixTwoCode = prefixTwoCode;
    }

    @ManyToOne
    @JoinColumn(name = "prefixThree")
    public CustomParame getPrefixThree() {
        return prefixThree;
    }

    public void setPrefixThree(CustomParame prefixThree) {
        this.prefixThree = prefixThree;
    }

    public String getPrefixThreeCode() {
        return prefixThreeCode;
    }

    public void setPrefixThreeCode(String prefixThreeCode) {
        this.prefixThreeCode = prefixThreeCode;
    }

    public int getJhSerialLength() {
        return jhSerialLength;
    }

    public void setJhSerialLength(int jhSerialLength) {
        this.jhSerialLength = jhSerialLength;
    }

    @ManyToOne
    @JoinColumn(name = "separator")
    public CustomParame getSeparator() {
        return separator;
    }

    public void setSeparator(CustomParame separator) {
        this.separator = separator;
    }

    @ManyToOne
    @JoinColumn(name = "prefixFour")
    public CustomParame getPrefixFour() {
        return prefixFour;
    }

    public void setPrefixFour(CustomParame prefixFour) {
        this.prefixFour = prefixFour;
    }

    public String getPrefixFourCode() {
        return prefixFourCode;
    }

    public void setPrefixFourCode(String prefixFourCode) {
        this.prefixFourCode = prefixFourCode;
    }

    public AccounCode() {
        super();
    }
}
