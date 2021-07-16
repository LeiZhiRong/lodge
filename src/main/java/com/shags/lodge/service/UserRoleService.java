package com.shags.lodge.service;

import com.shags.lodge.dto.Role;
import com.shags.lodge.util.CmsUtils;
import com.shags.lodge.util.Message;
import com.shags.lodge.util.SelectJson;
import com.shags.lodge.primary.dao.IRoleInfoDao;
import com.shags.lodge.primary.dao.IUserInfoDao;
import com.shags.lodge.primary.dao.IUserRoleDao;
import com.shags.lodge.primary.entity.RoleInfo;
import com.shags.lodge.primary.entity.UserInfo;
import com.shags.lodge.primary.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("userRoleService")
public class UserRoleService implements IUserRoleService {


    private IUserRoleDao userRoleDao;


    private IUserInfoDao userInfoDao;


    private IRoleInfoDao roleInfoDao;

    @Autowired
    public void setUserRoleDao(IUserRoleDao userRoleDao) {
        this.userRoleDao = userRoleDao;
    }

    @Autowired
    public void setUserInfoDao(IUserInfoDao userInfoDao) {
        this.userInfoDao = userInfoDao;
    }

    @Autowired
    public void setRoleInfoDao(IRoleInfoDao roleInfoDao) {
        this.roleInfoDao = roleInfoDao;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message saveUserRole(String user_id, String role_ids) {
        Message msg = new Message(0, "保存失败，错误原因有可能是用户不存在，请刷新页面后重试");
        if (user_id == null || user_id.isEmpty()) {
            msg.setMessage("用户参数错误");
            return msg;
        }
        if (role_ids == null || role_ids.isEmpty()) {
            msg.setMessage("角色参数错误");
            return msg;
        }
        List<String> list = CmsUtils.string2Array(role_ids, ",");
        UserInfo userInfo = userInfoDao.queryUserInfoById(user_id);
        List<RoleInfo> roleInfoList = roleInfoDao.listRoleInfo(list);
        List<UserRole> userRoleList = new ArrayList<>();
        if (roleInfoList != null && roleInfoList.size() > 0 && userInfo != null) {
            for (RoleInfo roleInfo : roleInfoList) {
                userRoleList.add(new UserRole(roleInfo, userInfo));
            }
            if (userRoleDao.batchSaveUserRole(userRoleList)) {
                msg.setCode(1);
                msg.setMessage("保存成功");
            }
        }

        return msg;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message deleteUserRole(String id) {
        Message msg = new Message(0, "删除失败");
        if (userRoleDao.deleteUserRole(id)) {
            msg.setMessage("删除成功");
            msg.setCode(1);
        }
        return msg;
    }

    @Override
    public List<SelectJson> listRoleInfo(String userId, String bookSet_code, String keyword) {
        return userRoleDao.listRoleInfo(userId, bookSet_code, keyword);
    }

    @Override
    public List<Role> getRoleById(String userId, String bookSet_code) {
        return userRoleDao.getRoleById(userId, bookSet_code);
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message batchDeleteUserRole(String ids, String user_id) {
        Message msg = new Message(0, "删除失败");
        if (userRoleDao.batchDeleteUserRole(ids, user_id) > 0) {
            msg.setCode(1);
            msg.setMessage("删除成功");
        }
        return msg;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message batchSaveUserRole(String userIds, String roleIds) {
        Message msg = new Message(0, "保存失败，错误原因有可能是用户不存在，请刷新页面重试");
        if (userIds != null && !userIds.isEmpty() && roleIds != null && !roleIds.isEmpty()) {
            List<String> r_ids = CmsUtils.string2Array(roleIds, ",");
            List<String> u_ids = CmsUtils.string2Array(userIds, ",");
            List<RoleInfo> roleInfoList = roleInfoDao.listRoleInfo(r_ids);
            List<UserInfo> userInfoList = userInfoDao.lisetUserInfo(u_ids);
            List<UserRole> userRoleList = new ArrayList<>();
            for (UserInfo userInfo : userInfoList) {
                for (RoleInfo roleInfo : roleInfoList) {
                    userRoleList.add(new UserRole(roleInfo, userInfo));
                }
            }
            if (userRoleDao.batchSaveUserRole(userRoleList)) {
                msg.setCode(1);
                msg.setMessage("保存成功");
            }
        } else {
            msg.setMessage("参数错误");
        }
        return msg;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public int batchDeleteUserRole(String userIds) {
        if (userIds != null && !userIds.isEmpty()) {
            List<String> list = CmsUtils.string2Array(userIds, ",");
            List<String> userRoleIds = userRoleDao.getUserRoleIDS(list, null);
            if (userRoleIds != null && userRoleIds.size() > 0)
                return userRoleDao.batchDeleteUserRole(userRoleIds);
        }
        return 0;
    }

    @Override
    public List<String> getUserRoleID(String userId, String bookSet_code) {
        return userRoleDao.getUserRoleID(userId, bookSet_code);
    }

}
