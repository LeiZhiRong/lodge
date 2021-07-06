package com.shgs.lodge.primary.dao;

import com.shgs.lodge.dto.AncillaryProjectsDto;
import com.shgs.lodge.dto.AncillaryProjectsListDto;
import com.shgs.lodge.dto.TreeJson;
import com.shgs.lodge.primary.dao.basic.IBaseDAO;
import com.shgs.lodge.primary.entity.AncillaryProjects;
import com.shgs.lodge.util.Pager;

import java.util.List;

public interface IAncillaryProjectsDao extends IBaseDAO<AncillaryProjects,String> {

    /**
     * 获取最大序号
     * @param pid
     * @return
     */
    int getMaxOrderByParent(String pid);

    /**
     * 按ID删除项目信息
     * @param id
     * @return
     */
    boolean deleteAncillaryProjects(String id);

    /**
     * 按ID查询项目信息
     * @param id
     * @return
     */
    AncillaryProjects queryAncillaryProjects(String id);



    /**
     * 获取下属部门总数
     * @param pid
     * @return
     */
    int getCountAncillaryProjectsByPid(String pid);

    /**
     * 按上级ID获取分页数据
     * @param pid
     * @param value
     * @return
     */
    Pager<AncillaryProjectsDto> findAncillaryProjectsDto(String pid, String value,String t_id);


    /**
     * 获取项目选择器数据
     * @param pid
     * @param value
     * @return
     */
    List<AncillaryProjectsListDto> listAncillaryProjectsListDto(String pid, String value,String t_id);


    /**
     * 按上级项目ID获取列表
     * @param pid
     * @param value
     * @return
     */
    List<AncillaryProjects> listAncillaryProjects(String pid, String value);

    /**
     * 更新排序号
     * @param ids
     */
    void updateSort(String[] ids);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    int batchDelete(String ids);

    /**
     * 更新关联信息
     * @param id
     * @param oldIds
     * @param newIds
     */
    void executeIds(String id, String oldIds, String newIds);



    /**
     * 获取目录树
     * @return
     */
    List<TreeJson> getAncillaryProjects2TreeJson(String keyword,String t_id);

}
