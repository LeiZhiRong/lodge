package com.shags.lodge.business.dao;

import com.shags.lodge.business.dao.basic.IBusinessBaseDAO;
import com.shags.lodge.business.entity.AssetsType;

/**
 * @author yglei
 * @date 2021-07-1916:22
 */
public interface IAssetsTypeDao extends IBusinessBaseDAO<AssetsType> {
    /**
     * 获取最大序号
     * @param pid 上级类型编号
     * @return int
     */
    int getMaxOrderByParent(String bookSet,String pid);

    /**
     * 更新ids
     * @param id 关键字
     * @param oldIds 原来ids
     * @param newIds 更新后的ids
     */
    void executeIds(String id, String oldIds, String newIds);

    /**
     * 获取下级分类数量
     * @param pid 上级分类ID
     * @return int
     */
    int getCountByPid(String pid);

    /**
     * @description: 检测
     * @param: [bookSet, bh, name, id]
     * @author: ygLei
     * @return: {@link boolean}
     * @date: 2021-07-23 17:11
     */
    boolean checkAssetsType(String bookSet,String bh,String name,String id);
}
