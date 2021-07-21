package com.shags.lodge.service.primary;

import com.shags.lodge.dto.ListGroup;
import com.shags.lodge.dto.MenuInfoDto;
import com.shags.lodge.dto.MenuShortcut;
import com.shags.lodge.dto.TreeJson;
import com.shags.lodge.util.Message;
import com.shags.lodge.util.Pager;
import com.shags.lodge.primary.dao.IMenuInfoDao;
import com.shags.lodge.primary.dao.IPermInfoDao;
import com.shags.lodge.primary.entity.MenuInfo;
import com.shags.lodge.primary.entity.PermInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 菜单管理业务处理类
 *
 * @author 雷智荣
 */
@Service("menuService")
public class MenuService implements IMenuService {


    private IMenuInfoDao menuInfoDao;


    private IPermInfoDao permInfoDao;

    @Autowired
    public void setMenuInfoDao(IMenuInfoDao menuInfoDao) {
        this.menuInfoDao = menuInfoDao;
    }

    @Autowired
    public void setPermInfoDao(IPermInfoDao permInfoDao) {
        this.permInfoDao = permInfoDao;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message addMenuInfo(MenuInfo menuInfo, String pid) {
        int orders = menuInfoDao.getMaxOrderByParent(pid);
        Message msg = new Message(0, "模块添加失败");
        String ids = null;
        MenuInfo pc = null;
        if (pid != null && !pid.isEmpty()) {
            pc = menuInfoDao.queryMenuInfo(pid);
            if (pc == null) {
                return new Message(0, "要添加模块的上级模块不正确");
            }
            ids = pc.getIds();
            menuInfo.setParent(pc);
        }
        menuInfo.setOrders(orders + 1);
        MenuInfo mast = menuInfoDao.add(menuInfo);
        if (mast != null) {
            if (ids != null && !ids.isEmpty()) {
                mast.setIds(mast.getId() + "," + ids);
            } else {
                mast.setIds(mast.getId());
            }
            menuInfoDao.update(mast);
            //检测上级目录标识
            if (pc != null && "F".equals(pc.getContents())) {
                pc.setContents("T");
                menuInfoDao.update(pc);
            }
            msg.setData(pid);
            msg.setCode(1);
            msg.setMessage("模块添加成功");
        }
        return msg;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message updateMenuInfo(MenuInfo menuInfo, String pid) {
        Message msg = new Message(0, "模块更新失败");
        //复制原模块上级目录
        MenuInfo oldparent = menuInfo.getParent();
        MenuInfo parent = null;
        //如果上级目录不等同于原上级目录时获取新上级目录和排序号
        if (pid != null && !pid.isEmpty()) {
            if (oldparent == null || !pid.equals(oldparent.getId())) {
                parent = menuInfoDao.queryMenuInfo(pid);
                menuInfo.setParent(parent);
                menuInfo.setOrders(menuInfoDao.getMaxOrderByParent(pid));
            }
        }
        //更新数据，上级目录处理
        if (menuInfoDao.updateMenuInfo(menuInfo)) {
            msg.setCode(1);
            msg.setMessage("更新成功");
            if (parent != null && "F".equals(parent.getContents())) {
                parent.setContents("T");
                menuInfoDao.updateMenuInfo(parent);
            }
            if (pid != null && !pid.isEmpty()) {
                if (oldparent != null && parent != null && !oldparent.getId().equals(parent.getId())) {
                    menuInfoDao.executeIds(menuInfo.getId(), oldparent.getIds(), parent.getIds());
                }
            }
            if (oldparent != null && !oldparent.getId().equals(pid)) {
                if (menuInfoDao.getCountMenuInfoByPid(oldparent.getId()) == 0) {
                    oldparent.setContents("F");
                    menuInfoDao.update(oldparent);
                    msg.setData(pid);
                }
            }
        }
        return msg;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message deleteMenuInfo(String id) {
        Message msg = new Message(0, "模块删除失败");
        //检测参数是否为空
        if (id == null || id.isEmpty()) {
            msg.setMessage("参数错误");
            return msg;
        }
        //检测是否有下级目录
        int o = menuInfoDao.getCountMenuInfoByPid(id);
        if (o > 0) {
            msg.setMessage("该模块检测已被【子菜单】引用" + o + "次，禁止删除");
            return msg;
        }
        //检测角色模块关联表是否已引用
        List<PermInfo> permInfoList = permInfoDao.listPermInfoByMenuID(id);
        if (permInfoList != null && permInfoList.size() > 0) {
            msg.setMessage("该模块已在【角色管理】中被引用" + permInfoList.size() + "次，禁止删除");
            return msg;
        }
        MenuInfo menuInfo = menuInfoDao.queryMenuInfo(id);
        //执行删除
        if (menuInfoDao.deleteMenuInfo(id)) {
            if (menuInfo.getParent() != null) {
                MenuInfo oldparent = menuInfo.getParent();
                if (menuInfoDao.getCountMenuInfoByPid(oldparent.getId()) == 0) {
                    oldparent.setContents("F");
                    menuInfoDao.update(oldparent);
                    msg.setData(oldparent.getParent() != null ? oldparent.getParent().getId() : "all");
                }
            }
            msg.setCode(1);
            msg.setMessage("删除成功");
        }
        return msg;
    }

    @Override
    public MenuInfo queryMenuInfo(String id) {
        return menuInfoDao.queryMenuInfo(id);
    }

    @Override
    public List<MenuInfo> listByParent(String pid) {
        return menuInfoDao.listByParent(pid);
    }

    @Override
    public Pager<MenuInfo> findMenuInfo(String pid) {
        return menuInfoDao.findMenuInfo(pid);
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public List<TreeJson> listMenuInfoTreeByIDS(List<String> ids, boolean isAdmin) {
        return menuInfoDao.listMenuInfoTreeByIDS(ids, isAdmin);
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public List<MenuShortcut> listMenuShortcutByIDS(List<String> ids, boolean isAdmin) {
        return menuInfoDao.listMenuShortcutByIDS(ids, isAdmin);
    }

    @Override
    public Pager<MenuInfoDto> findMenuInfoDto(String pid, String value) {
        return menuInfoDao.findMenuInfoDto(pid, value);
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public void updateSort(String[] ids) {
        menuInfoDao.updateSort(ids);
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public List<TreeJson> getMenuInfo2TreeJson() {
        return menuInfoDao.getMenuInfo2TreeJson();
    }

    @Override
    public List<ListGroup> getMenuInfo2ListGroup(String keyword) {
        return menuInfoDao.getMenuInfo2ListGroup(null, keyword, null, false, false);
    }
}
