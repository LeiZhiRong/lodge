package com.shags.lodge.service;

import com.shags.lodge.dto.DeptInfoDto;
import com.shags.lodge.dto.DeptInfoListDto;
import com.shags.lodge.dto.TreeJson;
import com.shags.lodge.util.Message;
import com.shags.lodge.util.Pager;
import com.shags.lodge.util.SelectJson;
import com.shags.lodge.primary.entity.DeptInfo;

import java.util.List;

/**
 * 部门信息管理业务层Service接口类
 * @author  雷智荣
 */
public interface IDeptInfoService {

    /**
     * 添加
     * @param deptInfo
     * @return Message
     */
    Message addDeptInfo(DeptInfo deptInfo, String pid);

    /**
     * 更新
     * @param deptInfo
     * @return Message
     */
    Message updateDeptInfo(DeptInfo deptInfo,String pid);

    /**
     * 按ID删除
     * @param id
     * @return Message
     */
    Message deleteDeptInfo(String id);

    /**
     * 按ID查询
     * @param id
     * @return meunInfo
     */
    DeptInfo queryDeptInfo(String id);

     /**
     * 根据PID获取下级部门
     * @param pid
     * @return list
     */
    List<DeptInfo> listByParent(String pid);

    /**
     * 分页查询
     * @param pid
     * @return Pager
     */
    Pager<DeptInfo> findDeptInfo(String pid);

     /**
     * 根据PID查询下级部门
     * @param pid
     * @return
     */
    Pager<DeptInfoDto> findDeptInfoDto(String pid, String value);

    /**
     * 更新排序号
     * @param ids
     */
    void updateSort(String[] ids);

    /**
     * 正序获取目录树
     * @return
     */
    List<TreeJson> getDeptInfo2TreeJson(String keyword);

    /**
     * 获取部门列表
     * @param pid
     * @param value
     * @return
     */
    List<DeptInfoDto> listDeptInfoDto(String pid, String value);

    List<DeptInfoListDto> listDeptInfoListDto(String pid, String value);

    /**
     * 按部门编号查询部门信息
     * @param deptID
     * @return
     */
    DeptInfo queryByDeptID(String deptID);

    /**
     * 按部门名称或编号查询部门信息
     * @param value
     * @return
     */
    DeptInfo queryByDeptDeptIdORName(String value);

    /**
     * 按部门编号查询，返回不存在的部门编号
     * @param deptIDs
     * @return
     */
    String listNotInDeptName(String deptIDs);

    /**
     * 获取用户可管理(结算)部门
     * @param deptIDS
     * @return
     */
    List<SelectJson> listUserByDeptIDS(String deptIDS);

    List<TreeJson> listUserSetllDept(String deptIDS);

    void batchSave(List<DeptInfo> list);

}