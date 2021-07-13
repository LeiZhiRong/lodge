package com.shgs.lodge.primary.dao;

import com.shgs.lodge.dto.ListGroup;
import com.shgs.lodge.dto.MenuInfoDto;
import com.shgs.lodge.dto.MenuShortcut;
import com.shgs.lodge.dto.TreeJson;
import com.shgs.lodge.primary.dao.basic.IBaseDAO;
import com.shgs.lodge.primary.entity.MenuInfo;
import com.shgs.lodge.util.Pager;

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

    List<TreeJson> listMenuInfoTreeByIDS(List<String> ids,boolean isAdmin);

    List<MenuShortcut> listMenuShortcutByIDS(List<String> ids, boolean isAdmin);

    int getCountMenuInfoByPid(String pid);

    Pager<MenuInfoDto> findMenuInfoDto(String pid, String value);

    void updateSort(String[] ids);

    List<TreeJson> getMenuInfo2TreeJson();

    int batchDelete(String ids);

    void executeIds(String id, String oldIds, String newIds);

    List<ListGroup> getMenuInfo2ListGroup(List<String> menuIds,String keyword,List<String> ids,boolean isNot,boolean isAdmin);
}