package com.shags.lodge.service.primary;


import com.shags.lodge.dto.AncillaryProjectsDto;
import com.shags.lodge.dto.AncillaryProjectsListDto;
import com.shags.lodge.dto.TreeJson;
import com.shags.lodge.util.Message;
import com.shags.lodge.util.Pager;
import com.shags.lodge.primary.entity.AncillaryProjects;

import java.util.List;

public interface IAncillaryProjectsService {

    /**
     * 添加
     * @param ancillaryProjects
     * @return Message
     */
    Message addAncillaryProjects(AncillaryProjects ancillaryProjects, String pid, String t_id);

    /**
     * 更新
     * @param ancillaryProjects
     * @return Message
     */
    Message updateAncillaryProjects(AncillaryProjects ancillaryProjects,String pid,String t_id);

    /**
     * 按ID删除
     * @param id
     * @return Message
     */
    Message deleteAncillaryProjects(String id);

    /**
     * 按ID查询
     * @param id
     * @return AncillaryProjects
     */
    AncillaryProjects queryAncillaryProjects(String id);

    /**
     * 根据PID获取下级项目
     * @param pid
     * @return list
     */
    List<AncillaryProjects> listByParent(String pid, String value);

    /**
     * 分页查询
     * @param pid
     * @return Pager
     */
    Pager<AncillaryProjectsDto> findAncillaryProjectsDto(String pid, String value, String t_id);


    List<AncillaryProjectsListDto> listAncillaryProjectsDto(String pid, String value, String t_id);


    /**
     * 更新排序号
     * @param ids
     */
    void updateSort(String[] ids);

    /**
     * 获取项目目录树
     * @return
     */
    List<TreeJson> getAncillaryProjects2TreeJson(String keyword, String t_id);
}
