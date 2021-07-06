package com.shgs.lodge.primary.dao;

import com.shgs.lodge.dto.CorpInfoListDto;
import com.shgs.lodge.primary.dao.basic.IBaseDAO;
import com.shgs.lodge.primary.entity.BillCorpInfo;
import com.shgs.lodge.util.Pager;

import java.util.List;

/**
 * 团购挂单-公司信息持久接口类Dao
 * @author 雷智荣
 */
public interface IBillCorpInfoDao extends IBaseDAO<BillCorpInfo,String> {

    /**
     * 按用户ID删除实例
     *
     * @param id ID
     * @return
     */
    boolean deleteBillCorpInfo(String id);

    /**
     * 按用户ID查询公司信息
     *
     * @param id ID
     * @return
     */
    BillCorpInfo queryBillCorpInfoById(String id);

    /**
     * 查询公司分页信息
     * @param corpType 公司类型
     * @param keyword 关键字
     * @param ztbz 状态标识
     * @return
     */
    Pager<BillCorpInfo> listBillCorpInfo(String corpType, String keyword, String ztbz);

    /**
     *
     * 批量删除公司信息
     * @param ids
     * @return
     */
    int batchDeleteCorp(String ids);


    /**
     *
     * 批量添加公司信息
     * @param list
     * @return
     */
    boolean batchSaveBillCorpInfo(List<BillCorpInfo> list);

    /**
     * 批量更新
     * @param list
     * @return
     */
    boolean batchUpdateBillCorpInfo(List<BillCorpInfo> list);

    /**
     * 按客户编号查询
     * @param corpBM
     * @return
     */
    BillCorpInfo queryBillCorpInfoByCorpBM(String corpBM);

    /**
     * 检测客户编号是否重复
     * @param corpBm
     * @param id
     * @return
     */
    boolean ChickBillCorpOtherByCorpBM(String corpBm,String id);


    List<BillCorpInfo> listBillCorpInfo(String corpType,String keyword);

    List<CorpInfoListDto> listCorpInfoListDto(String keyword, String pid);



}
