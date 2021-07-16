package com.shags.lodge.service;

import com.shags.lodge.dto.DeptInfoDto;
import com.shags.lodge.dto.DeptInfoListDto;
import com.shags.lodge.dto.TreeJson;
import com.shags.lodge.util.Message;
import com.shags.lodge.util.Pager;
import com.shags.lodge.util.SelectJson;
import com.shags.lodge.primary.dao.IDeptInfoDao;
import com.shags.lodge.primary.entity.DeptInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 部门信息管理业务层Service接口实现类
 *
 * @author 雷智荣
 */
@Service("deptInfoService")
public class DeptInfoService implements IDeptInfoService {

    private IDeptInfoDao deptInfoDao;

    @Autowired
    public void setDeptInfoDao(IDeptInfoDao deptInfoDao) {
        this.deptInfoDao = deptInfoDao;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message addDeptInfo(DeptInfo deptInfo, String pid) {
        Message msg = new Message(0, "部门添加失败");
        //检测部门编号是否唯一
        Integer o = deptInfoDao.countDeptID(deptInfo.getId(), pid, deptInfo.getDeptID());
        if (o > 0) {
            msg.setMessage("部门编号:【" + deptInfo.getDeptID() + "】已在使用中，请检测后重试！");
            return msg;
        }
        int orders = deptInfoDao.getMaxOrderByParent(pid);
        String ids = null;
        DeptInfo pc = null;
        if (pid != null && !pid.isEmpty()) {
            pc = deptInfoDao.queryDeptInfo(pid);
            if (pc == null) {
                return new Message(0, "上级部门信息不正确");
            }
            ids = pc.getIds();
            deptInfo.setParent(pc);
        }
        deptInfo.setOrders(orders + 1);
        DeptInfo mast = deptInfoDao.addDeptInfo(deptInfo);
        if (mast != null) {
            if (ids != null && !ids.isEmpty()) {
                mast.setIds(mast.getId() + "," + ids);
            } else {
                mast.setIds(mast.getId());
            }
            deptInfoDao.updateDeptInfo(mast);
            //检测上级目录标识
            if (pc != null && "F".equals(pc.getContents())) {
                pc.setContents("T");
                deptInfoDao.updateDeptInfo(pc);
            }
            msg.setData(pid);
            msg.setCode(1);
            msg.setMessage("部门添加成功");
        }
        return msg;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message updateDeptInfo(DeptInfo deptInfo, String pid) {
        Message msg = new Message(0, "部门信息更新失败");
        //检测部门编号是否唯一
        Integer o = deptInfoDao.countDeptID(deptInfo.getId(), pid, deptInfo.getDeptID());
        if (o > 0) {
            msg.setMessage("部门编号:【" + deptInfo.getDeptID() + "】已在使用中，请检测后重试！");
            return msg;
        }
        //复制原部门上级部门
        DeptInfo oldparent = deptInfo.getParent();
        DeptInfo parent = null;
        //如果上级部门不等同于原上级部门时获取新上级部门和排序号
        if (pid != null && !pid.isEmpty()) {
            if (oldparent == null || !pid.equals(oldparent.getId())) {
                parent = deptInfoDao.queryDeptInfo(pid);
                deptInfo.setParent(parent);
                deptInfo.setOrders(deptInfoDao.getMaxOrderByParent(pid)+1);
            }
        }
        //更新部门信息，上级部门处理
        if (deptInfoDao.updateDeptInfo(deptInfo)) {
            msg.setCode(1);
            msg.setMessage("更新成功");
            if (parent != null && "F".equals(parent.getContents())) {
                parent.setContents("T");
                deptInfoDao.updateDeptInfo(parent);
            }
            if (pid != null && !pid.isEmpty()) {
                if (oldparent != null && parent != null && !oldparent.getId().equals(parent.getId())) {
                    deptInfoDao.executeIds(deptInfo.getId(), oldparent.getIds(), parent.getIds());
                }
            }
            if (oldparent != null && !oldparent.getId().equals(pid)) {
                if (deptInfoDao.getCountDeptInfoByPid(oldparent.getId()) == 0) {
                    oldparent.setContents("F");
                    deptInfoDao.updateDeptInfo(oldparent);
                    msg.setData(pid);
                }
            }
        }
        return msg;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message deleteDeptInfo(String id) {
        Message msg = new Message(0, "部门信息删除失败");
        //检测参数是否为空
        if (id == null || id.isEmpty()) {
            msg.setMessage("参数错误");
            return msg;
        }
        //检测是否有下级部门
        int o = deptInfoDao.getCountDeptInfoByPid(id);
        if (o > 0) {
            msg.setMessage("该部门已被引用" + o + "次，禁止删除");
            return msg;
        }
        DeptInfo deptInfo = deptInfoDao.queryDeptInfo(id);
        //执行删除
        if (deptInfoDao.deleteDeptInfo(id)) {
            if (deptInfo.getParent() != null) {
                DeptInfo oldparent = deptInfo.getParent();
                if (deptInfoDao.getCountDeptInfoByPid(oldparent.getId()) == 0) {
                    oldparent.setContents("F");
                    deptInfoDao.updateDeptInfo(oldparent);
                    msg.setData(oldparent.getParent() != null ? oldparent.getParent().getId() : "all");
                }
            }
            msg.setCode(1);
            msg.setMessage("部门信息删除成功");
        }
        return msg;
    }

    @Override
    @Transactional(value = "primaryTransactionManager",readOnly = true)
    public DeptInfo queryDeptInfo(String id) {
        return deptInfoDao.queryDeptInfo(id);
    }

    @Override
    @Transactional(value = "primaryTransactionManager",readOnly = true)
    public List<DeptInfo> listByParent(String pid) {
        return deptInfoDao.listByParent(pid);
    }

    @Override
    @Transactional(value = "primaryTransactionManager",readOnly = true)
    public Pager<DeptInfo> findDeptInfo(String pid) {
        return deptInfoDao.findDeptInfo(pid);
    }


    @Override
    @Transactional(value = "primaryTransactionManager",readOnly = true)
    public Pager<DeptInfoDto> findDeptInfoDto(String pid, String value) {
        return deptInfoDao.findDeptInfoDto(pid, value);
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public void updateSort(String[] ids) {
        deptInfoDao.updateSort(ids);
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public List<TreeJson> getDeptInfo2TreeJson(String keyword) {
        return deptInfoDao.getMenuInfo2TreeJson(keyword);
    }

    @Override
    @Transactional(value = "primaryTransactionManager",readOnly = true)
    public List<DeptInfoDto> listDeptInfoDto(String pid, String value) {
        return deptInfoDao.listDeptInfoDto(pid, value);
    }

    @Override
    @Transactional(value = "primaryTransactionManager",readOnly = true)
    public List<DeptInfoListDto> listDeptInfoListDto(String pid, String value) {
        return deptInfoDao.listDeptInfoListDto(pid, value);
    }

    @Override
    @Transactional(value = "primaryTransactionManager",readOnly = true)
    public DeptInfo queryByDeptID(String deptID) {
        return deptInfoDao.queryByDeptID(deptID);
    }

    @Override
    @Transactional(value = "primaryTransactionManager",readOnly = true)
    public DeptInfo queryByDeptDeptIdORName(String value) {
        return deptInfoDao.queryByDeptDeptIdORName(value);
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public String listNotInDeptName(String deptIDs) {
        List<Map> list = deptInfoDao.listNotInDeptName(deptIDs);
        if (list != null && list.size() > 0) {
            List<String> strings = new ArrayList<>();
            for (Map map : list) {
                strings.add((String) map.get("deptID"));
            }
            return String.join(";", strings);
        } else {
            return null;
        }
    }

    @Override
    @Transactional(value = "primaryTransactionManager",readOnly = true)
    public List<SelectJson> listUserByDeptIDS(String deptIDS) {
        return deptInfoDao.listUserByDeptIDS(deptIDS);
    }

    @Override
    @Transactional(value = "primaryTransactionManager",readOnly = true)
    public List<TreeJson> listUserSetllDept(String deptIDS) {
        return deptInfoDao.listUserSetllDept(deptIDS);
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public void batchSave(List<DeptInfo> list) {
        deptInfoDao.batchUpdate(list);
    }


}
