package com.shags.lodge.service.primary;

import com.shags.lodge.dto.ProceedTypeListDto;
import com.shags.lodge.dto.ReveExpeItemDto;
import com.shags.lodge.dto.ReveExpeItemListDto;
import com.shags.lodge.dto.TreeJson;
import com.shags.lodge.util.Message;
import com.shags.lodge.util.SelectJson;
import com.shags.lodge.primary.entity.ProceedType;
import com.shags.lodge.primary.entity.ReveExpeItem;

import java.util.List;

/**
 * 收支项目业务接口层
 * @author 雷智荣
 */
public interface IReveExpeItemService {
    /**
     * 添加款项类型
     * @param proceedType
     * @return
     */
    Message addProceedType(ProceedType proceedType, String pid);

    /**
     * 更新款项类型
     * @param proceedType
     * @return
     */
    Message updateProceedType(ProceedType proceedType, String pid);

    /**
     * 删除款项类型
     * @param id
     * @return
     */
    Message deleteProceedType(String id);

    /**
     * 按ID查询款项分类
     * @param id
     * @return
     */
    ProceedType queryProceedTypeById(String id);

    /**
     * 按费项名称或编号查询费项
     * @param keyword
     * @return
     */
    ProceedType queryProceedTypeByNameAndProceedBh(String keyword);


    /**
     * 获取款项类型下拉列表
     * @return
     */
    List<SelectJson> listProceedType();



    List<TreeJson> listProceedTypeToTreeJson(String keyword);

    /**
     * 添加入账规则
     * @param reveExpeItem
     * @param proceedTypeId 所属费项ID
     * @param cashBankId 入账科目ID
     * @param saleCorpId  客商绑定ID
     * @param deptId 部门绑定ID
     * @return
     */
    Message addReveExpeItem(ReveExpeItem reveExpeItem,String proceedTypeId,String cashBankId,String saleCorpId,String deptId);

    /**
     * 更新入账规则
     * @param reveExpeItem
     * @param proceedTypeId 所属费项ID
     * @param cashBankId 入账科目ID
     * @param saleCorpId  客商绑定ID
     * @param deptId 部门绑定ID
     * @return
     */
    Message updateReveExpeItem(ReveExpeItem reveExpeItem,String proceedTypeId,String cashBankId,String saleCorpId,String deptId);

    /**
     * 按ID删除收支项目
     * @param id
     * @return
     */
    Message deleteReveExpeItem(String id);


    /**
     * 查询分支项目
     * @param proceedTypeId
     * @return
     */
    List<ReveExpeItemDto> listReveExpeItemDto(String proceedTypeId);

    /**
     * 按ID查询收支项目Dto
     * @param id
     * @return
     */
    ReveExpeItemDto queryReveExpeItemDtoById(String id);

    /**
     * 按收支编号获取收支项目Dto
     * @param reBh
     * @return
     */
    ReveExpeItemDto queryReveExpeItemDtoByReBh(String reBh);

    /**
     * 按ID获取实体对象
     * @param id
     * @return
     */
    ReveExpeItem queryReveExpeItemById(String id);

    /**
     * 按收支编号获取实体对象
     * @param reBh
     * @return
     */
    ReveExpeItem queryReveExpeItemByReBh(String reBh);

    List<ProceedTypeListDto> listProceedTypeListDto(String pid, String value);

    /**
     * 按费项获取入账规则
     * @param proceedId  *费项ID*
     * @param auditStatus *表单状态*
     * @param onAccount *是否挂账*
     * @param paymentMethod *结算方式*
     * @return
     */
    List<ReveExpeItemListDto> listReveExpeItemListDto(String proceedId, String auditStatus, String onAccount, String paymentMethod);





}
