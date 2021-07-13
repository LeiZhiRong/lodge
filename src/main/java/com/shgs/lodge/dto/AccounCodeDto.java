package com.shgs.lodge.dto;

import com.shgs.lodge.primary.entity.AccounCode;
import com.shgs.lodge.util.CmsUtils;
import com.shgs.lodge.util.HeaderEnum;

import java.util.ArrayList;
import java.util.List;

public class AccounCodeDto {

    /**
     * ID
     */
    private String id;
    /**
     * 账套code
     */
    private String bookSet;
    /**
     * 类型
     */

    private String codeType;

    @HeaderEnum(field = "codeTypeName", title = "编号类型", sortable = false)
    private String codeTypeName;
    /**
     * 前缀一
     */

    private String prefixOne;

    @HeaderEnum(field = "preFixOneName", title = "前缘一", sortable = false)
    private String preFixOneName;
    /**
     * 前缀一编码
     */
    @HeaderEnum(field = "prefixOneCode", title = "前缀一编码", sortable = false)
    private String prefixOneCode;

    /**
     * 前缀二
     */

    private String prefixTwo;

    @HeaderEnum(field = "prefixTwoName", title = "前缀二", sortable = false)
    private String prefixTwoName;
    /**
     * 前缀二编码
     */
    @HeaderEnum(field = "prefixTwoCode", title = "前缀二编码", sortable = false)
    private String prefixTwoCode;
    /**
     * 前缀三
     */

    private String prefixThree;

    @HeaderEnum(field = "prefixThreeName", title = "前缀三", sortable = false)
    private String prefixThreeName;


    /**
     * 前缀三编码
     */
    @HeaderEnum(field = "prefixThreeCode", title = "前缀三编码", sortable = false)
    private String prefixThreeCode;

    private String prefixFour;

    /**
     * 前缀四
     */
    @HeaderEnum(field = "prefixFourName", title = "前缀四", sortable = false)
    private String prefixFourName;

    /**
     * 前缀四编码
     */
    @HeaderEnum(field = "prefixFourCode", title = "前缀四编码", sortable = false)
    private String prefixFourCode;

    /**
     * 流水号
     */
    @HeaderEnum(field = "jhSerialLength", title = "流水号", width = 80, sortable = false)
    private Integer jhSerialLength = 4;
    /**
     * 分隔符
     */
    private String separator;

    @HeaderEnum(field = "separatorName", title = "分隔符", sortable = false)
    private String separatorName;

    @HeaderEnum(field = "handle", title = "关联操作", width = 150, sortable = false)
    private String handle;

    public String getPrefixFour() {
        return prefixFour;
    }

    public void setPrefixFour(String prefixFour) {
        this.prefixFour = prefixFour;
    }

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

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public String getPrefixOne() {
        return prefixOne;
    }

    public void setPrefixOne(String prefixOne) {
        this.prefixOne = prefixOne;
    }

    public String getPrefixOneCode() {
        return prefixOneCode;
    }

    public void setPrefixOneCode(String prefixOneCode) {
        this.prefixOneCode = prefixOneCode;
    }

    public String getPrefixTwo() {
        return prefixTwo;
    }

    public void setPrefixTwo(String prefixTwo) {
        this.prefixTwo = prefixTwo;
    }

    public String getPrefixTwoCode() {
        return prefixTwoCode;
    }

    public void setPrefixTwoCode(String prefixTwoCode) {
        this.prefixTwoCode = prefixTwoCode;
    }

    public String getPrefixThree() {
        return prefixThree;
    }

    public void setPrefixThree(String prefixThree) {
        this.prefixThree = prefixThree;
    }

    public String getPrefixThreeCode() {
        return prefixThreeCode;
    }

    public void setPrefixThreeCode(String prefixThreeCode) {
        this.prefixThreeCode = prefixThreeCode;
    }

    public Integer getJhSerialLength() {
        return jhSerialLength;
    }

    public void setJhSerialLength(Integer jhSerialLength) {
        this.jhSerialLength = jhSerialLength;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public AccounCodeDto() {
        super();
    }

    public String getHandle() {
        return handle;
    }

    public String getPreFixOneName() {
        return preFixOneName;
    }

    public void setPreFixOneName(String preFixOneName) {
        this.preFixOneName = preFixOneName;
    }

    public String getPrefixTwoName() {
        return prefixTwoName;
    }

    public void setPrefixTwoName(String prefixTwoName) {
        this.prefixTwoName = prefixTwoName;
    }

    public String getPrefixThreeName() {
        return prefixThreeName;
    }

    public void setPrefixThreeName(String prefixThreeName) {
        this.prefixThreeName = prefixThreeName;
    }

    public String getSeparatorName() {
        return separatorName;
    }

    public void setSeparatorName(String separatorName) {
        this.separatorName = separatorName;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getCodeTypeName() {
        return codeTypeName;
    }

    public void setCodeTypeName(String codeTypeName) {
        this.codeTypeName = codeTypeName;
    }

    public String getPrefixFourName() {
        return prefixFourName;
    }

    public void setPrefixFourName(String prefixFourName) {
        this.prefixFourName = prefixFourName;
    }

    public String getPrefixFourCode() {
        return prefixFourCode;
    }

    public void setPrefixFourCode(String prefixFourCode) {
        this.prefixFourCode = prefixFourCode;
    }

    public AccounCodeDto(AccounCode code) {
        this.setId(code.getId());
        this.setBookSet(code.getBookSet());
        this.setPrefixOneCode(code.getPrefixOneCode());
        this.setPrefixTwoCode(code.getPrefixTwoCode());
        this.setPrefixThreeCode(code.getPrefixThreeCode());
        this.setJhSerialLength(code.getJhSerialLength());
        if(code.getCodeType()!=null){
            this.setCodeType(code.getCodeType().getId());
            this.setCodeTypeName(code.getCodeType().getParameName());
        }
        if (code.getPrefixOne() != null) {
            this.setPrefixOne(code.getPrefixOne().getId());
            this.setPreFixOneName(code.getPrefixOne().getParameName());
        }
        if (code.getPrefixThree() != null) {
            this.setPrefixThree(code.getPrefixThree().getId());
            this.setPrefixThreeName(code.getPrefixThree().getParameName());
        }
        if (code.getPrefixTwo() != null) {
            this.setPrefixTwo(code.getPrefixTwo().getId());
            this.setPrefixTwoName(code.getPrefixTwo().getParameName());
        }
        if (code.getPrefixFour() != null) {
            this.setPrefixFour(code.getPrefixFour().getId());
            this.setPrefixFourName(code.getPrefixFour().getParameName());
        }

        if (code.getSeparator() != null) {
            this.setSeparator(code.getSeparator().getId());
            this.setSeparatorName(code.getSeparator().getParameName());
        }
        this.setHandle(CmsUtils.formatHandle(this.id));
    }


    public AccounCode getAccounCode(AccounCodeDto code) {
        AccounCode accounCode = new AccounCode();
        accounCode.setId(code.getId());
        accounCode.setBookSet(code.getBookSet());
        accounCode.setPrefixOneCode(code.getPrefixOneCode());
        accounCode.setPrefixTwoCode(code.getPrefixTwoCode());
        accounCode.setPrefixThreeCode(code.getPrefixThreeCode());
        accounCode.setPrefixFourCode(code.getPrefixFourCode());
        accounCode.setJhSerialLength(code.getJhSerialLength());
        return accounCode;
    }

    public List<AccounCodeDto> listAccounCodeDto(List<AccounCode> accounCodeList) {
        List<AccounCodeDto> list = new ArrayList<>();
        if (accounCodeList != null && accounCodeList.size() > 0) {
            for (AccounCode mast : accounCodeList) {
                list.add(new AccounCodeDto(mast));
            }
        }
        return list;

    }


}

