package com.shags.lodge.primary.dao;

import com.shags.lodge.primary.dao.basic.LodgeBaseDAO;
import com.shags.lodge.primary.entity.UserInfo;
import com.shags.lodge.util.CmsUtils;
import com.shags.lodge.util.Pager;
import com.shags.lodge.util.SelectJson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户信息持久接口DAO实现类
 *
 * @author 雷智荣
 */
@Repository("userInfoDao")
public class UserInfoDao extends LodgeBaseDAO<UserInfo, String> implements IUserInfoDao {

    @Override
    public boolean deleteUserInfo(String id) {
        Object o = super.executeByJpql("delete from UserInfo u where u.id =?0", id);
        return o != null;
    }

    @Override
    public UserInfo queryUserInfoById(String id) {
        return (UserInfo) super.queryObject("from UserInfo u where u.id =?0", id);
    }

    @Override
    public Pager<UserInfo> listUserInfo(String keyword, boolean isAdmin) {
        StringBuilder jpql = new StringBuilder();
        Map<String, Object> alias = new HashMap<>();
        jpql.append(" from UserInfo u where 1=1 ");
        if (StringUtils.isNotEmpty(keyword)) {
            jpql.append(" and ( u.userName like:keyword or u.loginAccount like:keyword ) ");
            alias.put("keyword", "%" + keyword + "%");
        }
        if (!isAdmin) {
            jpql.append(" and u.manager ='F'");
        }
        return super.find(jpql.toString(), alias);
    }

    @Override
    public int batchDeleteUser(String ids) {
        if (ids != null && !ids.isEmpty()) {
            List<String> _ids = CmsUtils.string2Array(ids, ",");
            Map<String, Object> alias = new HashMap<>();
            alias.put("ids", _ids);
            Object o = super.executeByAliasJpql("delete from UserInfo t where t.id in( :ids) ", alias);
            if (o != null)
                return (int) o;
        }
        return 0;
    }

    @Override
    public UserInfo queryByAccount(String loginAccount) {
        return (UserInfo) super.queryObject("from UserInfo u where u.loginAccount =?0 ", loginAccount);
    }

    @Override
    public boolean batchSaveUserInfo(List<UserInfo> list) {
        return super.batchSave(list);
    }

    @Override
    public List<UserInfo> lisetUserInfo(List<String> userIDS) {
        Map<String, Object> alias = new HashMap<>();
        StringBuilder jpql = new StringBuilder();
        jpql.append("from UserInfo u where 1=1 ");
        if (userIDS != null && userIDS.size() > 0) {
            jpql.append(" and u.id in:(ids) ");
            alias.put("ids", userIDS);
        }
        return super.listByAlias(jpql.toString(), alias);
    }

    @Override
    public List<SelectJson> listToSelectJson(String keyword) {
        List<SelectJson> selectJsonList = new ArrayList<>();
        StringBuilder jpql = new StringBuilder();
        Map<String, Object> alias = new HashMap<>();
        jpql.append(" from UserInfo u where 1=1 ");
        if (keyword != null && !keyword.isEmpty()) {
            jpql.append(" and ( u.userName like:keyword or u.loginAccount like:keyword ) ");
            alias.put("keyword", "%" + keyword + "%");
        }
        List<UserInfo> userInfoList = super.listByAlias(jpql.toString(), alias);
        if (userInfoList != null && userInfoList.size() > 0) {
            for (UserInfo userInfo : userInfoList) {
                selectJsonList.add(new SelectJson(userInfo.getId(), userInfo.getLoginAccount() + " " + userInfo.getUserName()));
            }
        }
        return selectJsonList;
    }

}
