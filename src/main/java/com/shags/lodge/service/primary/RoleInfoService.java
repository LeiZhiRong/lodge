package com.shags.lodge.service.primary;

import com.shags.lodge.util.Message;
import com.shags.lodge.util.SelectJson;
import com.shags.lodge.primary.dao.IRoleInfoDao;
import com.shags.lodge.primary.dao.IUserRoleDao;
import com.shags.lodge.primary.entity.RoleInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息管理业务层Service接口实现类
 * @author 雷智荣
 */
@Service("roleInfoService")
public class RoleInfoService implements IRoleInfoService {


    private IRoleInfoDao roleInfoDao;


    private IUserRoleDao userRoleDao;

    @Autowired
    public void setRoleInfoDao(IRoleInfoDao roleInfoDao) {
        this.roleInfoDao = roleInfoDao;
    }

    @Autowired
    public void setUserRoleDao(IUserRoleDao userRoleDao) {
        this.userRoleDao = userRoleDao;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message addRoleInfo(RoleInfo roleInfo) {
        Message msg=new Message(0,"角色信息保存失败");
        if(roleInfoDao.addRoleInfo(roleInfo)!=null){
            msg.setMessage("角色信息保存成功");
            msg.setCode(1);
        }
        return msg;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message updateRoleInfo(RoleInfo roleInfo) {
        Message msg=new Message(0,"角色信息更新失败");
        if(roleInfoDao.updateRoleInfo(roleInfo)){
            msg.setCode(1);
            msg.setMessage("角色信息更新成功");
        }
        return msg;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message delRoleINfo(String id) {
        Message msg=new Message(0,"角色信息删除失败");
        //检测是否用户角色关联表中被引用，若引用，禁止删除
        List<String> roleIDS= new ArrayList<>();
        roleIDS.add(id);
        List<String> userRoleList=userRoleDao.getUserRoleIDS(null, roleIDS);
        if(userRoleList!=null&& userRoleList.size()>0){
            msg.setMessage("该角色已在【角色授权】中被引用"+userRoleList.size()+"次，禁止删除");
            return msg;
        }
        if(roleInfoDao.delRoleINfo(id)){
            msg.setMessage("角色信息删除成功");
            msg.setCode(1);
        }
        return msg;
    }

    @Override
    public List<RoleInfo> listRoleInfo(String keyword) {
        return roleInfoDao.listRoleInfo(keyword);
    }

    @Override
    public List<RoleInfo> listRoleInfo(String keyword, String bookSet) {
        return roleInfoDao.listRoleInfo(keyword, bookSet);
    }

    @Override
    public RoleInfo getRoleInfo(String id) {
        return roleInfoDao.getRoleInfo(id);
    }

    @Override
    public RoleInfo queryByRoleName(String roleName) {
        return roleInfoDao.queryByRoleName(roleName);
    }

    @Override
    public List<SelectJson> listRoleInfo(List<String> roleIds, String bookSet, String keyword) {
        return roleInfoDao.listRoleInfo(roleIds,bookSet, keyword);
    }
}
