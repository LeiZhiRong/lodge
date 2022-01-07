package com.shags.lodge.primary.dao;

import com.shags.lodge.dto.DeptInfoDto;
import com.shags.lodge.dto.DeptInfoListDto;
import com.shags.lodge.dto.TreeJson;
import com.shags.lodge.primary.dao.basic.IBaseDAO;
import com.shags.lodge.primary.entity.DeptInfo;
import com.shags.lodge.util.Pager;
import com.shags.lodge.util.SelectJson;

import java.util.List;
import java.util.Map;

/**
 * 部门信息管理持久层Dao接口类
 * @author 雷智荣
 */
public interface IDeptInfoDao extends IBaseDAO<DeptInfo> {

    /**
     * 获取最大序号
     * @param pid
     * @return
     */
    int getMaxOrderByParent(String pid);

    /**
     * 添加部门信息
     * @param deptInfo
     * @return
     */
    DeptInfo addDeptInfo(DeptInfo deptInfo);

    /**
     * 更新部门信息
     * @param deptInfo
     * @return
     */
    boolean updateDeptInfo(DeptInfo deptInfo);

    /**
     * 按ID删除部门信息
     * @param id
     * @return
     */
    boolean deleteDeptInfo(String id);

    /**
     * 按ID查询部门信息
     * @param id
     * @return
     */
    DeptInfo queryDeptInfo(String id);

    /**
     * 按部门编号或名称查询部门信息
     * @param value
     * @return
     */
    DeptInfo queryByDeptDeptIdORName(String value);

    /**
     * 获取部门信息列表
     * @param pid
     * @return
     */
    List<DeptInfo> listByParent(String pid);

    /**
     * 获取部门信息分页数据
     * @param pid
     * @return
     */
    Pager<DeptInfo> findDeptInfo(String pid);

    /**
     * 获取下属部门总数
     * @param pid
     * @return
     */
    int getCountDeptInfoByPid(String pid);

    /**
     * 获取部门分页数据
     * @param pid
     * @param value
     * @return
     */
    Pager<DeptInfoDto> findDeptInfoDto(String pid, String value);

    /**
     * 更新排序号
     * @param ids
     */
    void updateSort(String[] ids);

    /**
     * 获取部门目录树
     * @return
     */
    List<TreeJson> getMenuInfo2TreeJson(String keyword);


    /**
     * 客户端获取管理部门树
     * @param keyword 已选择部门，多个以";"隔开
     * @param status 状态标识
     * @return list
     */
    List<TreeJson> getClientTreeJson(String keyword, Integer status,String userDeptID);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    int batchDelete(String ids);

    /**
     * 更新部门关联信息
     * @param id
     * @param oldIds
     * @param newIds
     */
    void executeIds(String id, String oldIds, String newIds);

    /**
     * 检测部门编号是否重复
     * @param id
     * @param pid
     * @param deptId
     * @return
     */
    Integer countDeptID(String id,String pid,String deptId );

    /**
     * 获取部门列表
     * @param pid
     * @param value
     * @return
     */
    List<DeptInfoDto> listDeptInfoDto(String pid, String value);

    /**
     * 获取管理部门列表
     * @param pid
     * @param value
     * @param bh
     * @return
     */
    List<DeptInfoListDto> listDeptInfoListDto(String pid, String value,boolean bh);

    /**
     * 获取用户授权管理部门列表
     * @param pid
     * @param value
     * @param bh
     * @param userDeptID
     * @return
     */
    List<DeptInfoListDto> listDeptInfoListDto(String pid, String value,boolean bh, String userDeptID);

    /**
     * 按部门编号查询部门信息
     * @param deptID
     * @return
     */
    DeptInfo queryByDeptID(String deptID);

    /**
     * 按部门编号查询返回不存在的部门编号
     * @param deptIDs
     * @return
     */
    List<Map> listNotInDeptName(String deptIDs);

    /**
     * 获取用户可管理部门
     * @param deptIDS
     * @return
     */
    List<SelectJson> listUserByDeptIDS(String deptIDS);

    List<TreeJson> listUserSetllDept(String deptIDS);

}
