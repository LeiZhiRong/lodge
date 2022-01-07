package com.shags.lodge.primary.dao;

import com.shags.lodge.dto.DeptInfoDto;
import com.shags.lodge.dto.DeptInfoListDto;
import com.shags.lodge.dto.TreeJson;
import com.shags.lodge.primary.dao.basic.LodgeBaseDAO;
import com.shags.lodge.primary.entity.DeptInfo;
import com.shags.lodge.util.CmsUtils;
import com.shags.lodge.util.Pager;
import com.shags.lodge.util.SelectJson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("deptInfoDao")
public class DeptInfoDao extends LodgeBaseDAO<DeptInfo, String> implements IDeptInfoDao {

    @Override
    public int getMaxOrderByParent(String pid) {
        Object obj;
        if (pid != null && !pid.isEmpty()) {
            obj = super.queryObject("select max(m.orders) from DeptInfo m where m.parent.id=?0", pid);
        } else {
            obj = super.queryObject("select max(m.orders) from DeptInfo m where m.parent is null");
        }
        if (obj == null)
            return 0;
        return (Integer) obj;
    }

    @Override
    public DeptInfo addDeptInfo(DeptInfo deptInfo) {
        return super.add(deptInfo);
    }

    @Override
    public boolean updateDeptInfo(DeptInfo deptInfo) {
        return super.update(deptInfo);
    }

    @Override
    public boolean deleteDeptInfo(String id) {
        Object o = super.executeByJpql("delete from DeptInfo m where m.id =?0", id);
        return o != null;
    }

    @Override
    public DeptInfo queryDeptInfo(String id) {
        return (DeptInfo) super.queryObject("from DeptInfo m where m.id =?0", id);
    }

    @Override
    public DeptInfo queryByDeptDeptIdORName(String value) {
        return (DeptInfo) super.queryObject(" from DeptInfo d where d.deptID =?0 or d.deptName like?1 ", new Object[]{value, "%" + value + "%"});
    }

    @Override
    public List<DeptInfo> listByParent(String pid) {
        if (StringUtils.isNotEmpty(pid)) {
            return super.list("from DeptInfo m where m.parent.id =?0 order by m.orders asc ", pid);
        } else {
            return super.list("from DeptInfo m order by m.orders asc ");
        }
    }

    @Override
    public Pager<DeptInfo> findDeptInfo(String pid) {
        StringBuilder jpql = new StringBuilder();
        Map<String, Object> alias = new HashMap<>();
        if (pid != null && !pid.isEmpty()) {
            jpql.append("from DeptInfo m where m.parent.id =:pid ");
            alias.put("pid", pid);
        } else {
            jpql.append("from DeptInfo m where m.parent is null");
            alias = null;
        }
        return super.find(jpql.toString(), alias);
    }


    @Override
    public int getCountDeptInfoByPid(String pid) {
        Object obj = super.queryObject("select count(m.id) from DeptInfo m where m.parent.id =?0", pid);
        if (obj == null)
            return 0;
        return ((Number) obj).intValue();
    }

    @Override
    public Pager<DeptInfoDto> findDeptInfoDto(String pid, String value) {
        pid = "all".equals(pid) ? null : pid;
        Pager<DeptInfoDto> deptInfoPager = new Pager<>();
        StringBuilder jpql;
        jpql = new StringBuilder();
        Map<String, Object> alias = new HashMap<>();
        jpql.append("from DeptInfo m where 1=1 ");
        if (pid != null && !pid.isEmpty()) {
            jpql.append("and m.parent.id =:pid ");
            alias.put("pid", pid);
        } else if (value == null || value.isEmpty()) {
            jpql.append("and m.parent is null ");
        }
        if (value != null && !value.isEmpty()) {
            jpql.append(" and ( m.deptName like:value or m.deptID like:value ) ");
            alias.put("value", "%" + value + "%");
        }
        Pager<DeptInfo> dptInfoPager = super.find(jpql.toString(), alias);
        if (dptInfoPager != null) {
            deptInfoPager.setTotal(dptInfoPager.getTotal());
            deptInfoPager.setPageNumber(dptInfoPager.getPageNumber());
            deptInfoPager.setPageOffset(dptInfoPager.getPageOffset());
            deptInfoPager.setPageSize(dptInfoPager.getPageSize());
            deptInfoPager.setRows(new DeptInfoDto().listDeptInfoDto(dptInfoPager.getRows()));
        }
        return deptInfoPager;
    }

    @Override
    public void updateSort(String[] ids) {
        int index = 1;
        String hql = "update DeptInfo m set m.orders=?0 where m.id=?1";
        for (String id : ids) {
            super.executeByJpql(hql, new Object[]{index++, id});
        }
    }

    @Override
    public List<TreeJson> getMenuInfo2TreeJson(String keyword) {
        List<TreeJson> cts = new ArrayList<>();
        List<Map> dts = super.listToMapBySql("select m.id as id,m.deptID as deptID,m.deptName as text,m.pid as pid,m.contents as contents from dept_info m  order by m.pid asc,m.orders asc");
        if (dts.size() > 0) {
            List<String> list = CmsUtils.string2Array(keyword, ";");
            for (Map map : dts) {
                TreeJson temp = new TreeJson();
                temp.setId((String) map.get("id"));
                temp.setText(map.get("deptID") + " " + map.get("text"));
                temp.setPid((String) map.get("pid"));
                temp.setArg(map.get("deptID"));
                if (list.contains(map.get("deptID"))) {
                    temp.setChecked(true);
                }
                temp.setArg1(map.get("contents"));
                cts.add(temp);
            }
        }
        return TreeJson.getfatherNode(cts);
    }

    @Override
    public List<TreeJson> getClientTreeJson(String keyword, Integer status, String userDeptID) {
        List<TreeJson> cts = new ArrayList<>();
        StringBuilder jpql = new StringBuilder();
        Map<String, Object> alias = new HashMap<>();
        jpql.append("select m.id as id,m.deptID as deptID,m.deptName as text,m.pid as pid,m.contents as contents from dept_info m where m.status =:status ");
        alias.put("status", status);
        if (StringUtils.isNotEmpty(userDeptID)) {
            List<String> deptIds = CmsUtils.string2Array(userDeptID, ";");
            jpql.append(" and m.deptID in(:deptID) ");
            alias.put("deptID", deptIds);
        }
        jpql.append(" order by m.pid asc,m.orders asc ");
        List<Map> dts = super.listToMapByAliasSql(jpql.toString(), alias);
        if (dts.size() > 0) {
            List<String> list = CmsUtils.string2Array(keyword, ";");
            for (Map map : dts) {
                TreeJson temp = new TreeJson();
                temp.setId((String) map.get("id"));
                if ("F".equals(map.get("contents"))) {
                    temp.setText(map.get("deptID") + " " + map.get("text"));
                }else{
                    temp.setText((String) map.get("text"));
                }
                temp.setPid((String) map.get("pid"));
                temp.setArg(map.get("deptID"));
                if (list.contains(map.get("deptID"))) {
                    temp.setChecked(true);
                }
                temp.setArg1(map.get("contents"));
                cts.add(temp);
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
            return (int) super.executeByAliasJpql("delete from DeptInfo where id in(:ids)", alias);
        }
        return 0;
    }

    @Override
    public void executeIds(String id, String oldIds, String newIds) {
        List<DeptInfo> list = super.list("from DeptInfo m  where m.ids like ?0", "%" + id + "%");
        List<DeptInfo> mast = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (DeptInfo temp : list) {
                temp.setIds(temp.getIds().replace(oldIds, newIds));
                mast.add(temp);
            }
        }
        if (mast.size() > 0)
            super.batchUpdate(mast);

    }

    @Override
    public Integer countDeptID(String id, String pid, String deptId) {
        StringBuilder jpql = new StringBuilder();
        Map<String, Object> alias = new HashMap<>();
        jpql.append(" select count(d) from DeptInfo d where d.deptID =:deptID ");
        alias.put("deptID", deptId);
        if (StringUtils.isNotEmpty(pid)) {
            jpql.append(" and d.parent.id =:pid ");
            alias.put("pid", pid);
        } else {
            jpql.append(" and d.parent is null ");
        }
        if (id != null && !id.isEmpty()) {
            jpql.append(" and d.id !=:id ");
            alias.put("id", id);
        }
        Object o = super.queryObjectByAlias(jpql.toString(), alias);
        if (o == null)
            return 0;
        return ((Number) o).intValue();
    }

    @Override
    public List<DeptInfoDto> listDeptInfoDto(String pid, String value) {
        pid = "all".equals(pid) ? null : pid;
        List<DeptInfoDto> deptInfoDtos = new ArrayList<>();
        StringBuilder jpql = new StringBuilder();
        Map<String, Object> alias = new HashMap<>();
        jpql.append("from DeptInfo m where 1=1 ");
        if (pid != null && !pid.isEmpty()) {
            jpql.append("and m.parent.id =:pid ");
            alias.put("pid", pid);
        } else if (value == null || value.isEmpty()) {
            jpql.append("and m.parent is null ");
        }
        if (value != null && !value.isEmpty()) {
            jpql.append(" and ( m.deptName like:value or m.deptID like:value ) ");
            alias.put("value", "%" + value + "%");
        }
        List<DeptInfo> dptInfoPager = super.listByAlias(jpql.toString(), alias);
        if (dptInfoPager != null && dptInfoPager.size() > 0) {
            deptInfoDtos = new DeptInfoDto().listDeptInfoDto(dptInfoPager);
        }
        return deptInfoDtos;
    }

    @Override
    public List<DeptInfoListDto> listDeptInfoListDto(String pid, String value,boolean bh) {
        pid = "all".equals(pid) ? null : pid;
        List<DeptInfoListDto> deptInfoDtos = new ArrayList<>();
        StringBuilder jpql = new StringBuilder();
        Map<String, Object> alias = new HashMap<>();
        jpql.append("from DeptInfo m where m.status =1 ");
        if (pid != null && !pid.isEmpty()) {
            jpql.append("and m.parent.id =:pid ");
            alias.put("pid", pid);
        } else if (value == null || value.isEmpty()) {
            jpql.append("and m.parent is null ");
        }
        if (value != null && !value.isEmpty()) {
            jpql.append(" and ( m.deptName like:value or m.deptID like:value ) ");
            alias.put("value", "%" + value + "%");
        }
        jpql.append(" order by m.deptID ");
        List<DeptInfo> dptInfoPager = super.listByAlias(jpql.toString(), alias);
        if (dptInfoPager != null && dptInfoPager.size() > 0) {
            deptInfoDtos = new DeptInfoListDto().listDeptInfoListDto(dptInfoPager,bh);
        }
        return deptInfoDtos;
    }

    @Override
    public List<DeptInfoListDto> listDeptInfoListDto(String pid, String value, boolean bh, String userDeptID) {
        pid = "all".equals(pid) ? null : pid;
        List<DeptInfoListDto> deptInfoDtos = new ArrayList<>();
        StringBuilder jpql = new StringBuilder();
        Map<String, Object> alias = new HashMap<>();
        jpql.append("from DeptInfo m where m.status =1 ");
        if (pid != null && !pid.isEmpty()) {
            jpql.append("and m.parent.id =:pid ");
            alias.put("pid", pid);
        } else if (value == null || value.isEmpty()) {
            jpql.append("and m.parent is null ");
        }
        if (value != null && !value.isEmpty()) {
            jpql.append(" and ( m.deptName like:value or m.deptID like:value ) ");
            alias.put("value", "%" + value + "%");
        }
        if (StringUtils.isNotEmpty(userDeptID)) {
            List<String> deptIds = CmsUtils.string2Array(userDeptID, ";");
            jpql.append(" and m.deptID in(:deptID) ");
            alias.put("deptID", deptIds);
        }
        jpql.append(" order by m.deptID ");
        List<DeptInfo> dptInfoPager = super.listByAlias(jpql.toString(), alias);
        if (dptInfoPager != null && dptInfoPager.size() > 0) {
            deptInfoDtos = new DeptInfoListDto().listDeptInfoListDto(dptInfoPager,bh);
        }
        return deptInfoDtos;
    }

    @Override
    public DeptInfo queryByDeptID(String deptID) {
        return (DeptInfo) super.queryObject("from DeptInfo m where m.deptID =?0", deptID);
    }

    @Override
    public List<Map> listNotInDeptName(String deptIDs) {
        if (deptIDs == null || deptIDs.isEmpty())
            return null;
        StringBuilder jpql = new StringBuilder();
        Map<String, Object> alias = new HashMap<>();
        List<String> list = CmsUtils.string2Array(deptIDs, ";");
        jpql.append("SELECT deptID FROM ( ");
        int i = list.size() - 1;
        int h = 0;
        for (String str : list) {
            if (i == h) {
                jpql.append(" select '").append(list.get(h)).append("' as deptID ");
            } else {
                jpql.append(" select '").append(list.get(h)).append("' as deptID UNION ");
            }
            h++;
        }
        jpql.append(" ) a WHERE deptID NOT IN ( SELECT deptID from dept_info WHERE deptID IN (:ids) )");
        alias.put("ids", list);
        return super.listToMapByAliasSql(jpql.toString(), alias);
    }

    @Override
    public List<SelectJson> listUserByDeptIDS(String deptIDS) {
        List<SelectJson> select = new ArrayList<>();
        if (deptIDS != null && !deptIDS.isEmpty()) {
            List<String> array = CmsUtils.string2Array(deptIDS, ";");
            Map<String, Object> alias = new HashMap<>();
            alias.put("contents", "F");
            alias.put("ids", array);
            List<DeptInfo> list = super.listByAlias("from DeptInfo d where d.status =1 and d.contents =:contents and  d.deptID in(:ids) order by d.orders asc ", alias);
            if (list != null && list.size() > 0) {
                for (DeptInfo mast : list) {
                    select.add(new SelectJson(mast.getId(), mast.getDeptID() + " " + mast.getDeptName(), mast.getDeptID()));
                }
            }
        }
        return select;
    }

    @Override
    public List<TreeJson> listUserSetllDept(String deptIDS) {
        List<TreeJson> jsonList = new ArrayList<>();
        jsonList.add(new TreeJson("all", "all 所有部门", null));
        if (deptIDS != null && !deptIDS.isEmpty()) {
            List<String> array = CmsUtils.string2Array(deptIDS, ";");
            Map<String, Object> alias = new HashMap<>();
            alias.put("contents", "F");
            alias.put("ids", array);
            List<DeptInfo> list = super.listByAlias("from DeptInfo d where d.status =1 and d.contents =:contents and  d.deptID in(:ids) order by d.orders asc ", alias);
            if (list != null && list.size() > 0) {
                for (DeptInfo mast : list) {
                    jsonList.add(new TreeJson(mast.getDeptID(), mast.getDeptID() + " " + mast.getDeptName(), "all"));
                }
            }
        }
        return TreeJson.getfatherNode(jsonList);
    }

}
