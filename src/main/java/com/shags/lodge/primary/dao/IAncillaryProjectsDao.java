package com.shags.lodge.primary.dao;

import com.shags.lodge.dto.AncillaryProjectsDto;
import com.shags.lodge.dto.AncillaryProjectsListDto;
import com.shags.lodge.dto.TreeJson;
import com.shags.lodge.primary.dao.basic.IBaseDAO;
import com.shags.lodge.primary.entity.AncillaryProjects;
import com.shags.lodge.util.Pager;

import java.util.List;

public interface IAncillaryProjectsDao extends IBaseDAO<AncillaryProjects> {

    /**
     * 获取最大序号
     * @param pid 上级分类ID
     * @return int
     */
    int getMaxOrderByParent(String pid);

    /**
     * 按ID查询项目信息
     * @param id 关键字
     * @return object
     */
    AncillaryProjects queryAncillaryProjects(String id);

    /**
     * 获取下属部门总数
     * @param pid 上级分类ID
     * @return int
     */
    int getCountAncillaryProjectsByPid(String pid);

    /**
     * 按上级ID获取分页数据
     * @param pid 上级分类ID
     * @param value 过滤内容
     * @return pager
     */
    Pager<AncillaryProjectsDto> findAncillaryProjectsDto(String pid, String value, String t_id);


    /**
     * 获取项目选择器数据
     * @param pid 上级分类ID
     * @param value 过滤内容
     * @return list
     */
    List<AncillaryProjectsListDto> listAncillaryProjectsListDto(String pid, String value, String t_id);


    /**
     * 按上级项目ID获取列表
     * @param pid 上级分类ID
     * @param value 过滤内容
     * @return list
     */
    List<AncillaryProjects> listAncillaryProjects(String pid, String value);

    /**
     * 更新排序号
     * @param ids string[]ids
     */
    void updateSort(String[] ids);

    /**
     * 批量删除
     * @param ids String ids
     * @return int
     */
    int batchDelete(String ids);

    /**
     * 更新关联信息
     * @param id 关键字
     * @param oldIds String oldIDS
     * @param newIds String newIds
     */
    void executeIds(String id, String oldIds, String newIds);



    /**
     * 获取目录树
     * @return list
     */
    List<TreeJson> getAncillaryProjects2TreeJson(String keyword, String t_id);

}
