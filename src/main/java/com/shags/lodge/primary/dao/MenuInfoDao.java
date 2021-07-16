package com.shags.lodge.primary.dao;

import com.shags.lodge.dto.ListGroup;
import com.shags.lodge.dto.MenuInfoDto;
import com.shags.lodge.dto.MenuShortcut;
import com.shags.lodge.dto.TreeJson;
import com.shags.lodge.primary.dao.basic.LodgeBaseDAO;
import com.shags.lodge.primary.entity.MenuInfo;
import com.shags.lodge.util.CmsUtils;
import com.shags.lodge.util.Pager;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository("menuInfoDao")
public class MenuInfoDao extends LodgeBaseDAO<MenuInfo,String> implements IMenuInfoDao {

    @Override
    public int getMaxOrderByParent(String pid) {
        Object obj;
        if (pid != null && !pid.isEmpty()) {
            obj = super.queryObject("select max(m.orders) from MenuInfo m where m.parent.id=?0", pid);
        } else {
            obj = super.queryObject("select max(m.orders) from MenuInfo m where m.parent is null");
        }
        if (obj == null)
            return 0;
        return (Integer) obj;
    }

    @Override
    public MenuInfo addMenuInfo(MenuInfo menuInfo) {
        return super.add(menuInfo);
    }

    @Override
    public boolean updateMenuInfo(MenuInfo menuInfo) {
        return super.update(menuInfo);
    }

    @Override
    public boolean deleteMenuInfo(String id) {
        Object o = super.executeByJpql("delete from MenuInfo m where m.id =?0", id);
        return o != null;
    }

    @Override
    public MenuInfo queryMenuInfo(String id) {
        return (MenuInfo) super.queryObject("from MenuInfo m where m.id =?0", id);
    }

    @Override
    public List<MenuInfo> listByParent(String pid) {
        return super.list("from MenuInfo m where m.parent.id =?0 order by m.orders asc ", pid);
    }

    @Override
    public List<MenuInfo> listMenuInfoByIds(String ids) {
        StringBuilder jpql = new StringBuilder();
        Map<String, Object> alias = new HashMap<>();
        jpql.append("from MenuInfo m where 1=1");
        if (ids != null && !ids.isEmpty()) {
            List<String> array = CmsUtils.string2Array(ids, ",");
            jpql.append(" and m.id in(:ids)");
            alias.put("ids", array);
        }
        return super.listByAlias(jpql.toString(), alias);
    }

    @Override
    public Pager<MenuInfo> findMenuInfo(String pid) {
        StringBuilder jpql = new StringBuilder();
        Map<String, Object> alias = new HashMap<>();
        if (pid != null && !pid.isEmpty()) {
            jpql.append("from MenuInfo m where m.parent.id =:pid ");
            alias.put("pid", pid);
        } else {
            jpql.append("from MenuInfo m where m.parent is null");
            alias = null;
        }
        return super.find(jpql.toString(), alias);
    }

    @Override
    public List<TreeJson> listMenuInfoTreeByIDS(List<String> ids, boolean isAdmin) {
        if (ids == null || ids.isEmpty()) {
            return null;
        }
        List<TreeJson> treeJsonList = new ArrayList<>();
        StringBuilder jpql = new StringBuilder();
        Map<String, Object> alias = new HashMap<>();
        jpql.append("select id as id, name as text,icons as iconCls,pid as pid,pathUrl as arg from menu_info  where 1=1 ");
        if (!isAdmin) {
            jpql.append(" and status =1  and id in(:ids) ");
            alias.put("ids", ids);
        }
        jpql.append(" order by pid asc,orders asc ");
        List<Map> menuInfos = super.listToMapByAliasSql(jpql.toString(), alias);
        if (menuInfos != null && menuInfos.size() > 0) {
            for (Map menuInfo : menuInfos) {
                String pid = (String) menuInfo.get("pid");
                TreeJson treeJson = new TreeJson();
                if (pid == null || pid.isEmpty())
                    treeJson.setIconCls((String) menuInfo.get("iconCls"));
                treeJson.setId((String) menuInfo.get("id"));
                treeJson.setPid((String) menuInfo.get("pid"));
                treeJson.setText((String) menuInfo.get("text"));
                treeJson.setArg(menuInfo.get("arg"));
                treeJsonList.add(treeJson);
            }
        }
        return TreeJson.getfatherNode(treeJsonList);
    }

    @Override
    public List<MenuShortcut> listMenuShortcutByIDS(List<String> ids, boolean isAdmin) {
        if (ids == null || ids.isEmpty()) {
            return null;
        }
        List<MenuShortcut> list = new ArrayList<>();
        StringBuilder jpql = new StringBuilder();
        Map<String, Object> alias = new HashMap<>();
        jpql.append("select id as id, name as text,icons as iconCls,pid as pid,pathUrl as url from menu_info  where  hompPage='T' and status =1 ");
        if (!isAdmin) {
            jpql.append(" and modelGrant ='T' ");
            jpql.append(" and id in(:ids) ");
            alias.put("ids", ids);
        }
        jpql.append(" order by pid asc,orders asc ");
        List<Map> menuInfos = super.listToMapByAliasSql(jpql.toString(), alias);
        List<String> _ids = new ArrayList<>();
        if (menuInfos != null && menuInfos.size() > 0) {
            for (Map menuInfo : menuInfos) {
                MenuShortcut temp = new MenuShortcut();
                String pid = (String) menuInfo.get("pid");
                String id = (String) menuInfo.get("id");
                if (pid == null || pid.isEmpty()) {
                    if (!_ids.contains(id)) {
                        temp.setId((String) menuInfo.get("id"));
                        temp.setText((String) menuInfo.get("text"));
                        list.add(temp);
                        _ids.add(id);
                    }
                } else {
                    temp.setText((String) menuInfo.get("text"));
                    temp.setIconCls((String) menuInfo.get("iconCls"));
                    temp.setText((String) menuInfo.get("text"));
                    temp.setUrl(menuInfo.get("url"));
                    temp.setId((String) menuInfo.get("id"));
                    if (_ids.contains(pid)) {
                        for (MenuShortcut mast : list) {
                            if (mast.getId().equals(pid)) {
                                List<MenuShortcut> menus = mast.getList();
                                menus.add(temp);
                                break;
                            }
                        }
                    }

                }


            }
        }
        List<MenuShortcut> menuShortcutList = new ArrayList<>();
        for (MenuShortcut m : list) {
            if (m.getList() != null && m.getList().size() > 0)
                menuShortcutList.add(m);
        }
        return menuShortcutList;
    }

    @Override
    public int getCountMenuInfoByPid(String pid) {
        Object obj = super.queryObject("select count(m.id) from MenuInfo m where m.parent.id =?0", pid);
        if (obj == null)
            return 0;
        return ((Number) obj).intValue();
    }

    @Override
    public Pager<MenuInfoDto> findMenuInfoDto(String pid, String value) {
        pid = "all".equals(pid) ? null : pid;
        Pager<MenuInfoDto> menuInfoDtoPager = new Pager<>();
        StringBuilder jpql = new StringBuilder();
        Map<String, Object> alias = new HashMap<>();
        jpql.append("from MenuInfo m where 1=1 ");
        if (pid != null && !pid.isEmpty()) {
            jpql.append("and  m.parent.id =:pid ");
            alias.put("pid", pid);
        } else if (value == null || value.isEmpty()) {
            jpql.append("and m.parent is null");
        }
        if (value != null && !value.isEmpty()) {
            jpql.append(" and m.name like:name  ");
            alias.put("name", "%" + value + "%");
        }
        Pager<MenuInfo> menuInfoPager = super.find(jpql.toString(), alias);
        if (menuInfoPager != null) {
            menuInfoDtoPager.setTotal(menuInfoPager.getTotal());
            menuInfoDtoPager.setPageNumber(menuInfoPager.getPageNumber());
            menuInfoDtoPager.setPageOffset(menuInfoPager.getPageOffset());
            menuInfoDtoPager.setPageSize(menuInfoPager.getPageSize());
            menuInfoDtoPager.setRows(new MenuInfoDto().listMenuInfoDto(menuInfoPager.getRows()));
        }
        return menuInfoDtoPager;
    }

    @Override
    public void updateSort(String[] ids) {
        int index = 1;
        String hql = "update MenuInfo m set m.orders=?0 where m.id=?1";
        for (String id : ids) {
            super.executeByJpql(hql, new Object[]{index++, id});
        }
    }

    @Override
    public List<TreeJson> getMenuInfo2TreeJson() {
        List<TreeJson> cts = new ArrayList<>();
        List<Map> dts = super.listToMapBySql("select m.id as id,m.name as text,m.pid as pid from menu_info m ");
        if (dts != null && dts.size() > 0) {
            for (Map map : dts) {
                cts.add(new TreeJson((String) map.get("id"), (String) map.get("text"), (String) map.get("pid")));
            }
        }
        return TreeJson.getfatherNode(cts);
    }

    @Override
    public int batchDelete(String ids) {
        if (ids != null && !ids.isEmpty()) {
            List<String> list = CmsUtils.string2Array(ids, ",");
            Map<String, Object> alias = new HashMap<>();
            alias.put("ids", list);
            return (int) super.executeByAliasJpql("delete from MenuInfo where id in(:ids)", alias);
        }
        return 0;
    }

    @Override
    public void executeIds(String id, String oldIds, String newIds) {
        List<MenuInfo> list = super.list("from MenuInfo m  where m.ids like ?0", "%" + id + "%");
        List<MenuInfo> mast = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (MenuInfo temp : list) {
                temp.setIds(temp.getIds().replace(oldIds, newIds));
                mast.add(temp);
            }
        }
        if (mast.size() > 0)
            super.batchUpdate(mast);
    }

    @Override
    public List<ListGroup> getMenuInfo2ListGroup(List<String> menuIds, String keyword, List<String> ids, boolean isNot, boolean isAdmin) {
        StringBuilder jpql = new StringBuilder();
        Map<String, Object> alias = new HashMap<>();
        jpql.append("from MenuInfo m where 1=1 ");
        if (keyword != null && !keyword.isEmpty()) {
            jpql.append(" and ( m.name like:keyword or m.parent.name like:keyword )");
            alias.put("keyword", "%" + keyword + "%");
        }
        if (ids != null && !ids.isEmpty()) {
            jpql.append(isNot ? " and m.id not in(:ids) " : " and m.id in(:ids) ");
            alias.put("ids", ids);
        }
        if (!isAdmin) {
            if (menuIds != null && menuIds.size() > 0) {
                jpql.append(" and m.id in(:menuIds) ");
                alias.put("menuIds", menuIds);
            }
            jpql.append(" and m.modelGrant ='T' ");
        }
        jpql.append(" order by m.parent.orders asc,m.orders asc ");
        List<MenuInfo> list = super.listByAlias(jpql.toString(), alias);
        List<ListGroup> groups = new ArrayList<>();
        if (list.size() > 0) {
            for (MenuInfo menuInfo : list) {
                if (menuInfo.getParent() != null)
                    groups.add(new ListGroup(menuInfo.getId(), menuInfo.getName(), menuInfo.getParent().getName()));
            }
        }
        return groups;
    }
}
