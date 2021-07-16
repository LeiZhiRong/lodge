package com.shags.lodge.primary.dao;

import com.shags.lodge.dto.ListGroup;
import com.shags.lodge.dto.MenuInfoDto;
import com.shags.lodge.dto.MenuShortcut;
import com.shags.lodge.dto.TreeJson;
import com.shags.lodge.primary.dao.basic.IBaseDAO;
import com.shags.lodge.primary.entity.MenuInfo;
import com.shags.lodge.util.Pager;

import java.util.List;

public interface IMenuInfoDao extends IBaseDAO<MenuInfo> {
    int getMaxOrderByParent(String pid);

    MenuInfo addMenuInfo(MenuInfo menuInfo);

    boolean updateMenuInfo(MenuInfo menuInfo);

    boolean deleteMenuInfo(String id);

    MenuInfo queryMenuInfo(String id);

    List<MenuInfo> listByParent(String pid);

    List<MenuInfo> listMenuInfoByIds(String ids);

    Pager<MenuInfo> findMenuInfo(String pid);

    List<TreeJson> listMenuInfoTreeByIDS(List<String> ids, boolean isAdmin);

    List<MenuShortcut> listMenuShortcutByIDS(List<String> ids, boolean isAdmin);

    int getCountMenuInfoByPid(String pid);

    Pager<MenuInfoDto> findMenuInfoDto(String pid, String value);

    void updateSort(String[] ids);

    List<TreeJson> getMenuInfo2TreeJson();

    int batchDelete(String ids);

    void executeIds(String id, String oldIds, String newIds);

    List<ListGroup> getMenuInfo2ListGroup(List<String> menuIds, String keyword, List<String> ids, boolean isNot, boolean isAdmin);
}