package com.shgs.lodge.service;

import com.shgs.lodge.dto.ListGroup;
import com.shgs.lodge.dto.MenuInfoDto;
import com.shgs.lodge.dto.MenuShortcut;
import com.shgs.lodge.dto.TreeJson;
import com.shgs.lodge.primary.entity.MenuInfo;
import com.shgs.lodge.util.Message;
import com.shgs.lodge.util.Pager;

import java.util.List;

public interface IMenuService {

    /**
     * 添加
     * @param menuInfo
     * @return Message
     */
    Message addMenuInfo(MenuInfo menuInfo,String pid);

    /**
     * 更新菜单
     * @param menuInfo
     * @return Message
     */
    Message updateMenuInfo(MenuInfo menuInfo,String pid);

    /**
     * 按ID删除菜单
     * @param id
     * @return Message
     */
    Message deleteMenuInfo(String id);

    /**
     * 按ID查询菜单
     * @param id
     * @return meunInfo
     */
    MenuInfo queryMenuInfo(String id);

    /**
     * 根据PID获取下级菜单
     * @param pid
     * @return list
     */
    List<MenuInfo> listByParent(String pid);

    /**
     * 分页查询
     * @param pid
     * @return Pager
     */
    Pager<MenuInfo> findMenuInfo(String pid);

    /**
     * 根据IDS查询目录树
     * @param ids
     * @return
     */
    List<TreeJson> listMenuInfoTreeByIDS(List<String> ids,boolean isAdmin);

    /**
     * 获取首页快捷导航
     * @param ids
     * @param isAdmin
     * @return
     */
    List<MenuShortcut> listMenuShortcutByIDS(List<String> ids, boolean isAdmin);

    /**
     * 根据PID查询下级菜单
     * @param pid
     * @return
     */
    Pager<MenuInfoDto> findMenuInfoDto(String pid,String value);

    /**
     * 更新排序号
     * @param ids
     */
    void updateSort(String[] ids);

    /**
     * 正序获取目录数
     * @return
     */
    List<TreeJson> getMenuInfo2TreeJson();

    /**
     * 获取LIST分组数据
     * @param keyword
     * @return
     */
    List<ListGroup> getMenuInfo2ListGroup(String keyword);
}
