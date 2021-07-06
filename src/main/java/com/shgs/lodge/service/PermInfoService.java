package com.shgs.lodge.service;

import com.shgs.lodge.dto.ListGroup;
import com.shgs.lodge.dto.Permissions;
import com.shgs.lodge.primary.dao.IMenuInfoDao;
import com.shgs.lodge.primary.dao.IPermInfoDao;
import com.shgs.lodge.primary.entity.MenuInfo;
import com.shgs.lodge.primary.entity.PermInfo;
import com.shgs.lodge.util.CmsUtils;
import com.shgs.lodge.util.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色权限管理业务层Service接口实现类
 *
 * @author 雷智荣
 */
@Service("permInfoService")
public class PermInfoService implements IPermInfoService {

    @Autowired
    private IPermInfoDao permInfoDao;

    @Autowired
    private IMenuInfoDao menuInfoDao;

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message batchSavePermInfo(String role_id, String menu_ids) {
        Message msg = new Message(0, "角色权限新增失败");
        if (role_id == null || role_id.isEmpty()) {
            return msg;
        } else {
            List<MenuInfo> list = menuInfoDao.listMenuInfoByIds(menu_ids);
            List<PermInfo> mast = new ArrayList<PermInfo>();
            if (list != null && list.size() > 0) {
                for (MenuInfo menuInfo : list) {
                    mast.add(new PermInfo(role_id, menuInfo));
                }
                if (permInfoDao.batchSavePermInfo(mast)) {
                    msg.setCode(1);
                    msg.setMessage("角色权限新增成功");
                }
            }
        }
        return msg;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message batchDelePermInfo(String role_id, String perm_ids) {
        Message msg = new Message(0, "角色权限取消失败");
        if (perm_ids == null || perm_ids.isEmpty()) {
            msg.setMessage("角色参数错误");
        } else {
            List<String> ids = CmsUtils.string2Array(perm_ids, ",");
            Integer o = permInfoDao.batchDelePermInfo(role_id, ids);
            if (o > 0) {
                msg.setCode(1);
                msg.setMessage("角色权限取消成功");
            }
        }
        return msg;
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public List<ListGroup> listPermInfo2ListGroup(String role_id, String keyword) {
        List<PermInfo> list = permInfoDao.listPermInfo(role_id, keyword);
        List<ListGroup> group = new ArrayList<ListGroup>();
        if (list != null && list.size() > 0) {
            for (PermInfo temp : list) {
                group.add(new ListGroup(temp.getId(), temp.getMenuInfo().getName(), temp.getMenuInfo().getParent() != null ? temp.getMenuInfo().getParent().getName() : ""));
            }
        }
        return group;
    }


    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public List<ListGroup> ListMeunInfo2ListGroup(List<String> menuIds,String role_id, String keyword,boolean isAdmin) {
        List<String> ids = permInfoDao.getMenuIds(role_id);
        return menuInfoDao.getMenuInfo2ListGroup(menuIds,keyword, ids, true,isAdmin);
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public List<Permissions> listRoleType(String role_id) {
        List<Permissions> permissions = new ArrayList<Permissions>();
        List<PermInfo> permInfoList = permInfoDao.listPermInfo(role_id, null);
        if (permInfoList != null && permInfoList.size() > 0) {
            for(PermInfo permInfo:permInfoList){
                permissions.add(new Permissions(permInfo.getId(),permInfo.getMenuInfo().getName(),permInfo.getMenuInfo().getType()!=null?permInfo.getMenuInfo().getType().toString():null,permInfo.getMenuInfo().getIds()));
            }
        }
        return permissions;
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public List<String> getMenuIds(String role_id) {
        return permInfoDao.getMenuIds(role_id);
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Integer delePermInfoBYRoleID(String role_id) {
        return permInfoDao.delePermInfoBYRoleID(role_id);
    }
}
