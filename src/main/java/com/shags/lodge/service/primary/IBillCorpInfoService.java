package com.shags.lodge.service.primary;

import com.shags.lodge.dto.CorpInfoListDto;
import com.shags.lodge.dto.TreeJson;
import com.shags.lodge.util.Message;
import com.shags.lodge.util.Pager;
import com.shags.lodge.primary.entity.BillCorpInfo;

import java.util.List;

public interface IBillCorpInfoService {

    /**
     * 保存
     * @param billCorpInfo
     * @return
     */
    Message saveBillCorpInfo(BillCorpInfo billCorpInfo);

    /**
     * 更新
     * @param billCorpInfo
     * @return
     */
    Message updateBillCorpInfo(BillCorpInfo billCorpInfo);

    /**
     * 按ID删除
     * @param id
     * @return
     */
    Message deleteBillCorpInfo(String id);

    /**
     * 获取分页数据
     * @param corpType 客户类型
     * @param keyword 关键字
     * @param ztbz 状态标识
     * @return
     */
    Pager<BillCorpInfo> listBillCorpInfo(String corpType, String keyword, String ztbz);

    /**
     * 按关键字获取分页数据
     * @param keyword
     * @return
     */
    Pager<BillCorpInfo> listBillCorpInfo(String keyword);

    /**
     * 按ID查询
     * @param id
     * @return
     */
    BillCorpInfo queryBillCorpInfoById(String id);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    Message batchDeleteCorp(String ids);

    /**
     * 批量保存
     * @param list
     * @return
     */
    boolean batchSaveBillCorpInfo(List<BillCorpInfo> list);

    /**
     * 批量更新
     * @param list
     * @return
     */
    @SuppressWarnings("UnusedReturnValue")
    boolean batchUpdateBillCorpInfo(List<BillCorpInfo> list);

    /**
     * 按客户编号关键字查询
     * @param corpBM
     * @return
     */
   BillCorpInfo queryBillCorpInfoByCorpBM(String corpBM);

   List<BillCorpInfo> listBillCorpInfoByKeyWord(String corpType,String keyword);

    List<CorpInfoListDto> listCorpInfoListDto(String keyword, String corpType, Integer status);

   List<CorpInfoListDto> listCorpInfoListDto(String keyword, String pid);

    /**
     * 获取客商目录树
     * @param keyword 已选择客商，多个以“;”隔开
     * @param corpType 客商类型
     * @param status 状态标识
     * @return list
     */
    List<TreeJson> listCorpInfoToTreeJson(String keyword, String corpType, Integer status);



}
