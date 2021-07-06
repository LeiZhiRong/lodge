package com.shgs.lodge.service;

import com.shgs.lodge.primary.dao.IUserInfoDao;
import com.shgs.lodge.primary.entity.UserInfo;
import com.shgs.lodge.util.Message;
import com.shgs.lodge.util.MyJasyptStringEncryptor;
import com.shgs.lodge.util.Pager;
import com.shgs.lodge.util.SelectJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户信息业务接口Service实现类
 *
 * @author 雷智荣
 */
@Service("userInfoService")
public class UserInfoService implements IUserInfoService {

    @Autowired
    private IUserInfoDao userInfoDao;

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message saveUserINfo(UserInfo userInfo) {
        Message msg = new Message(0, "保存失败");
        if (userInfoDao.add(userInfo) != null) {
            msg.setMessage("保存成功");
            msg.setCode(1);
        }
        return msg;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message updateUserinfo(UserInfo userInfo) {
        Message msg = new Message(0, "保存失败");
        if (userInfoDao.update(userInfo)) {
            msg.setMessage("保存成功");
            msg.setCode(1);
        }
        return msg;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message deleteUserInfo(String id) {
        Message msg = new Message(0, "删除失败");
        //第一步：删除用户角色关联表
        if (userInfoDao.deleteUserInfo(id)) {
            msg.setMessage("删除成功");
            msg.setCode(1);
        }
        return msg;
    }

    @Override
    public Pager<UserInfo> listUserInfo(String keyword,boolean isAdmin) {
        return userInfoDao.listUserInfo( keyword,isAdmin);
    }

    @Override
    public UserInfo queryUserInfoById(String id) {
        return userInfoDao.queryUserInfoById(id);
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message batchDeleteUser(String ids) {
        Message msg = new Message(0, "删除失败");
        if (userInfoDao.batchDeleteUser(ids) > 0) {
            msg.setCode(1);
            msg.setMessage("删除成功");
        }
        return msg;
    }

    @Override
    public UserInfo queryByAccount(String loginAccount) {
        return userInfoDao.queryByAccount(loginAccount);
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public boolean batchSaveUserInfo(List<UserInfo> list) {
        return userInfoDao.batchSaveUserInfo(list);
    }

    @Override
    public Message loginUser(String loginAccount, String password) {
        Message msg = new Message(0, "登录失败");
        UserInfo userInfo = userInfoDao.queryByAccount(loginAccount);
        if (userInfo != null) {
            if (userInfo.getStatus() == 0) {
                msg.setMessage("当前帐号已删除或已被禁用，请联系管理员");
            } else {
                String decrypt=new MyJasyptStringEncryptor().decrypt(userInfo.getLoginPassword());
                if (password.equals(decrypt)) {
                    msg.setCode(1);
                    msg.setData(userInfo);
                    msg.setMessage("登录成功");
                } else {
                    msg.setMessage("登录密码错误");
                }
            }
        } else {
            msg.setMessage("当前帐号不存在或已被删除，请联系管理员");
        }
        return msg;
    }

    @Override
    public List<SelectJson> listToSelectJson(String keyword) {
        return userInfoDao.listToSelectJson(keyword);
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public void updateUserStation(String oldStation, String newStation) {
        String jqpl = "update UserInfo u set u.station =:newStation where u.station =:oldStation ";
        Map<String, Object> alias = new HashMap<String, Object>();
        alias.put("newStation", newStation);
        alias.put("oldStation", oldStation);
        userInfoDao.executeByAliasJpql(jqpl, alias);
    }

}
