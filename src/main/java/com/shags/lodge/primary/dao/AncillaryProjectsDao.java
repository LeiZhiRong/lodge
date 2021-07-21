package com.shags.lodge.primary.dao;

import com.shags.lodge.dto.AncillaryProjectsDto;
import com.shags.lodge.dto.AncillaryProjectsListDto;
import com.shags.lodge.dto.TreeJson;
import com.shags.lodge.primary.dao.basic.LodgeBaseDAO;
import com.shags.lodge.primary.entity.AncillaryProjects;
import com.shags.lodge.util.CmsUtils;
import com.shags.lodge.util.Pager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("ancillaryProjectsDao")
public class AncillaryProjectsDao extends LodgeBaseDAO<AncillaryProjects,String> implements IAncillaryProjectsDao {

    @Override
    public int getMaxOrderByParent(String pid) {
        Object obj;
        if (pid != null && !pid.isEmpty()) {
            obj = super.queryObject("select max(m.orders) from AncillaryProjects m where m.parent.id=?0", pid);
        } else {
            obj = super.queryObject("select max(m.orders) from AncillaryProjects m where m.parent is null");
        }
        if (obj == null)
            return 0;
        return (Integer) obj;
    }

    @Override
    public AncillaryProjects queryAncillaryProjects(String id) {
        return (AncillaryProjects) super.queryObject("from AncillaryProjects m where m.id =?0",id);
    }


    @Override
    public int getCountAncillaryProjectsByPid(String pid) {
        Object obj = super.queryObject("select count(m.id) from AncillaryProjects m where m.parent.id =?0", pid);
        if (obj == null)
            return 0;
        return ((Number) obj).intValue();
    }

    @Override
    public Pager<AncillaryProjectsDto> findAncillaryProjectsDto(String pid, String value, String t_id) {
        pid = "all".equals(pid) ? null : pid;
        Pager<AncillaryProjectsDto> list = new Pager<>();
        StringBuilder jpql = new StringBuilder();
        Map<String, Object> alias = new HashMap<>();
        jpql.append("from AncillaryProjects m where 1=1 ");
        if (pid != null && !pid.isEmpty()) {
            jpql.append("and m.parent.id =:pid ");
            alias.put("pid", pid);
        } else if (value == null || value.isEmpty()) {
            jpql.append("and m.parent is null ");
        }
        if(StringUtils.isNotEmpty(t_id)){
            jpql.append(" and  m.projectType.id =:t_id ");
            alias.put("t_id", t_id);
        }
        if (value != null && !value.isEmpty()) {
            jpql.append(" and  m.projectsName like:value ");
            alias.put("value", "%" + value + "%");
        }
        Pager<AncillaryProjects> mast = super.find(jpql.toString(), alias);
        if (mast != null) {
            list.setTotal(mast.getTotal());
            list.setPageNumber(mast.getPageNumber());
            list.setPageOffset(mast.getPageOffset());
            list.setPageSize(mast.getPageSize());
            list.setRows(new AncillaryProjectsDto().listAncillaryProjectsDto(mast.getRows()));
        }
        return list;
    }

    @Override
    public List<AncillaryProjectsListDto> listAncillaryProjectsListDto(String pid, String value, String t_id) {
       List<AncillaryProjectsListDto> list = new ArrayList<>();
        pid = "all".equals(pid) ? null : pid;
        StringBuilder jpql = new StringBuilder();
        Map<String, Object> alias = new HashMap<>();
        jpql.append("from AncillaryProjects m where m.ztbz =:ztbz ");
        alias.put("ztbz","T");
        if (pid != null && !pid.isEmpty()) {
            jpql.append("and m.parent.id =:pid ");
            alias.put("pid", pid);
        } else if (value == null || value.isEmpty()) {
            jpql.append("and m.parent is null ");
        }
        if(StringUtils.isNotEmpty(t_id)){
            jpql.append(" and m.projectType.parameCode =:t_id ");
            alias.put("t_id",t_id);
        }
        if (value != null && !value.isEmpty()) {
            jpql.append(" and m.projectsName like:value  ");
            alias.put("value", "%" + value + "%");
        }
        List<AncillaryProjects> mast = super.listByAlias(jpql.toString(), alias);
        if (mast != null) {
            list=new AncillaryProjectsListDto().listAncillaryProjectsListDto(mast);
        }
        return list;
    }

    @Override
    public List<AncillaryProjects> listAncillaryProjects(String pid, String value) {
        pid = "all".equals(pid) ? null : pid;
          StringBuilder jpql = new StringBuilder();
        Map<String, Object> alias = new HashMap<>();
        jpql.append("from AncillaryProjects m where 1=1 ");
        if (pid != null && !pid.isEmpty()) {
            jpql.append("and m.parent.id =:pid ");
            alias.put("pid", pid);
        } else if (value == null || value.isEmpty()) {
            jpql.append("and m.parent is null ");
        }
        if (value != null && !value.isEmpty()) {
            jpql.append(" and m.projectsName like:value  ");
            alias.put("value", "%" + value + "%");
        }
        return super.listByAlias(jpql.toString(), alias);
    }

    @Override
    public void updateSort(String[] ids) {
        int index = 1;
        String hql = "update AncillaryProjects m set m.orders=?0 where m.id=?1";
        for (String id : ids) {
            super.executeByJpql(hql, new Object[]{index++, id});
        }
    }

    @Override
    public int batchDelete(String ids) {
        if (ids != null && !ids.isEmpty()) {
            List<String> list = CmsUtils.string2Array(ids, ",");
            Map<String, Object> alias = new HashMap<>();
            alias.put("ids", list);
            return (int) super.executeByAliasJpql("delete from AncillaryProjects where id in(:ids)", alias);
        }
        return 0;
    }

    @Override
    public void executeIds(String id, String oldIds, String newIds) {
        List<AncillaryProjects> list = super.list("from AncillaryProjects m  where m.ids like ?0", "%" + id + "%");
        List<AncillaryProjects> mast = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (AncillaryProjects temp : list) {
                temp.setIds(temp.getIds().replace(oldIds, newIds));
                mast.add(temp);
            }
        }
        if (mast.size() > 0)
            super.batchUpdate(mast);
    }



    @Override
    public List<TreeJson> getAncillaryProjects2TreeJson(String keyword, String t_id) {
        StringBuilder jpql = new StringBuilder();
        Map<String, Object> alias = new HashMap<>();
        jpql.append("select m.id as id,m.projectsName as text,m.pid as pid,m.contents as contents from ancillary_projects m  where m.t_id =:t_id  ");
        alias.put("t_id",t_id);
        jpql.append(" order by m.pid asc,m.orders asc ");
        List<TreeJson> cts = new ArrayList<>();
        List<Map> dts = super.listToMapByAliasSql(jpql.toString(),alias);
        if (dts.size() > 0) {
            List<String> list = CmsUtils.string2Array(keyword, ";");
            for (Map map : dts) {
                TreeJson temp = new TreeJson();
                temp.setId((String) map.get("id"));
                temp.setText((String) map.get("text"));
                temp.setPid((String) map.get("pid"));
                temp.setArg1(map.get("contents"));
                cts.add(temp);
            }
        }
        return TreeJson.getfatherNode(cts);
    }
}
