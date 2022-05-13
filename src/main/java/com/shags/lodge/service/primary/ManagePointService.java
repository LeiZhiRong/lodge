package com.shags.lodge.service.primary;

import com.shags.lodge.dto.ManagePointDto;
import com.shags.lodge.dto.TreeJson;
import com.shags.lodge.dto.User;
import com.shags.lodge.dto.business.ManagePointListDto;
import com.shags.lodge.primary.dao.IManagePointDao;
import com.shags.lodge.primary.entity.ManagePoint;
import com.shags.lodge.util.CmsUtils;
import com.shags.lodge.util.Message;
import com.shags.lodge.util.Pager;
import com.shags.lodge.util.SelectJson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("managePointService")
public class ManagePointService implements IManagePointService {

    private IManagePointDao managePointDao;

    @Autowired
    public void setManagePointDao(IManagePointDao managePointDao) {
        this.managePointDao = managePointDao;
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public ManagePoint queryManagePointByID(String id) {
        return (ManagePoint) managePointDao.queryObject("from ManagePoint m where m.id =?0 ", id);
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message addManagePoint(ManagePoint managePoint) {
        Message msg = new Message(0, "新增失败");
        ManagePoint m = (ManagePoint) managePointDao.queryObject("from ManagePoint m where ( m.bh =?0 or m.name =?1 ) and  m.bookSet =?2 ", new Object[]{managePoint.getBh(), managePoint.getName(), managePoint.getBookSet()});
        if (m != null) {
            msg.setMessage("编号或名称已经存在，请修改后重试！");
            return msg;
        }
        if (managePointDao.add(managePoint) != null) {
            msg.setCode(1);
            msg.setMessage("新增成功");
        }
        return msg;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message updateManagePoint(ManagePoint managePoint) {
        Message msg = new Message(0, "修改失败");
        ManagePoint m = (ManagePoint) managePointDao.queryObject("from ManagePoint m where ( m.bh =?0 or m.name =?1 ) and  m.bookSet =?2 and m.id !=?3 ", new Object[]{managePoint.getBh(), managePoint.getName(), managePoint.getBookSet(), managePoint.getId()});
        if (m != null) {
            msg.setMessage("编号或名称已经存在，请修改后重试！");
            return msg;
        }
        if (managePointDao.update(managePoint)) {
            msg.setCode(1);
            msg.setMessage("修改成功");
        }
        return msg;
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public List<ManagePoint> listManagePoint(String bookSet) {
        return managePointDao.list("from ManagePoint m where m.bookSet =?0  order by m.rVTime asc ", bookSet);
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public List<SelectJson> listManagePointToSelectJson(String bookSet) {
        List<ManagePoint> list = managePointDao.list("from ManagePoint m where m.bookSet =?0 and m.ztbz =?1  order by m.rVTime asc ", new Object[]{bookSet, "T"});
        List<SelectJson> jsonList = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (ManagePoint managePoint : list) {
                jsonList.add(new SelectJson(managePoint.getId(), managePoint.getName()));
            }
        }
        return jsonList;
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public List<SelectJson> getClientManagePointByUser(User user) {
        List<SelectJson> jsonList = new ArrayList<>();
        if (user.isAdmin()) {
            return this.listManagePointToSelectJson(user.getBookSet());
        } else {
            StringBuilder jpql = new StringBuilder();
            Map<String, Object> alias = new HashMap<>();
            jpql.append(" from ManagePoint m where m.bookSet =:bookSet and m.ztbz =:ztbz ");
            alias.put("bookSet", user.getBookSet());
            alias.put("ztbz", "T");
            if (StringUtils.isNotEmpty(user.getManageDept())) {
                List<String> setId = CmsUtils.string2Array(user.getBalanceDept(), ",");
                jpql.append(" and m.name in(:setId) ");
                alias.put("setId", setId);
            }
            List<ManagePoint> list = managePointDao.listByAlias(jpql.toString(), alias);
            if (list != null && list.size() > 0) {
                for (ManagePoint managePoint : list) {
                    jsonList.add(new SelectJson(managePoint.getId(), managePoint.getName()));
                }
            }
            return jsonList;
        }
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public Pager<ManagePointDto> findManagePointDto(String bookSet, String keyword) {
        StringBuilder jpql = new StringBuilder();
        Map<String, Object> alias = new HashMap<>();
        Pager<ManagePointDto> managePointDtoPager = new Pager<>();
        jpql.append(" from ManagePoint m where m.bookSet =:bookSet  ");
        alias.put("bookSet", bookSet);
        if (StringUtils.isNotEmpty(keyword)) {
            jpql.append(" and ( m.bh like:keyword  or m.name like:keyword ) ");
            alias.put("keyword", "%" + keyword + "%");
        }
        Pager<ManagePoint> pager = managePointDao.find(jpql.toString(), alias);
        if (pager != null) {
            managePointDtoPager.setPageNumber(pager.getPageNumber());
            managePointDtoPager.setPageSize(pager.getPageSize());
            managePointDtoPager.setPageOffset(pager.getPageOffset());
            managePointDtoPager.setTotal(pager.getTotal());
            managePointDtoPager.setRows(new ManagePointDto().listManagePointDto(pager.getRows()));
        }
        return managePointDtoPager;
    }


    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message deleteManagePointByID(String id) {
        Message msg = new Message(0, "删除失败");
        if (managePointDao.delete(id)) {
            msg.setCode(1);
            msg.setMessage("删除成功");
        }
        return msg;
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public ManagePoint queryManagePoint(String bookSet, String keyword) {
        StringBuilder jpql = new StringBuilder();
        Map<String, Object> alias = new HashMap<>();
        jpql.append(" from ManagePoint m where m.ztbz=:ztbz and m.bookSet =:bookSet  ");
        alias.put("bookSet", bookSet);
        alias.put("ztbz", "T");
        if (StringUtils.isNotEmpty(keyword)) {
            jpql.append(" and ( m.bh =:keyword  or m.name =:keyword ");
            alias.put("keyword", keyword);
        }
        jpql.append(" order by m.rVTime asc ");
        return (ManagePoint) managePointDao.queryObjectByAlias(jpql.toString(), alias);
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public String listNotInManagePoint(String bookSet, String name) {
        List<Map> list = managePointDao.listNotInManagePoint(bookSet, name);
        if (list != null && list.size() > 0) {
            List<String> strings = new ArrayList<>();
            for (Map map : list) {
                strings.add((String) map.get("name"));
            }
            return String.join(",", strings);
        } else {
            return null;
        }
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public List<ManagePointListDto> listManagePointListDto(String bookSet, String keyword, String ztbz, String userBalanceDept) {
        List<ManagePointListDto> dto = new ArrayList<>();
        StringBuilder jpql = new StringBuilder();
        Map<String, Object> alias = new HashMap<>();
        jpql.append(" from ManagePoint m where m.bookSet =:bookSet  ");
        alias.put("bookSet", bookSet);
        if (StringUtils.isNotEmpty(ztbz)) {
            jpql.append(" and m.ztbz =:ztbz ");
            alias.put("ztbz", ztbz);
        }
        if (StringUtils.isNotEmpty(keyword)) {
            jpql.append(" and ( m.bh like:keyword  or m.name like:keyword )");
            alias.put("keyword", keyword+"%");
        }
        if (StringUtils.isNotEmpty(userBalanceDept)) {
            List<String> dept = CmsUtils.string2Array(userBalanceDept, ",");
            jpql.append(" and m.id in(:dept) ");
            alias.put("dept", dept);
        }
        jpql.append(" order by m.rVTime asc ");
        List<ManagePoint> list = managePointDao.listByAlias(jpql.toString(), alias);
        if (list != null && list.size() > 0) {
            dto = new ManagePointListDto().listManagePointDto(list);
        }
        return dto;
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public List<TreeJson> getClientManagePointToTreeJson(String bookSet,String keyword,  String ztbz,String userManageDept) {
        List<TreeJson> cts = new ArrayList<>();
        StringBuilder jpql = new StringBuilder();
        Map<String, Object> alias = new HashMap<>();
        jpql.append(" from ManagePoint m where m.bookSet =:bookSet and m.ztbz =:ztbz  ");
        alias.put("bookSet", bookSet);
        alias.put("ztbz", ztbz);
        if (StringUtils.isNotEmpty(userManageDept)) {
            List<String> setId = CmsUtils.string2Array(userManageDept, ",");
            jpql.append(" and m.id in(:setId) ");
            alias.put("setId", setId);
        }
        jpql.append(" order by m.name  asc ");
        List<ManagePoint> dts = managePointDao.listByAlias(jpql.toString(), alias);
        if (dts.size() > 0) {
            cts.add(0, new TreeJson("all", "<所有管理处>", null));
            List<String> list = CmsUtils.string2Array(keyword, ";");
            for (ManagePoint map : dts) {
                TreeJson temp = new TreeJson();
                temp.setId(map.getId());
                temp.setPid("all");
                temp.setText(map.getName());
                if (list.contains(map.getName())) {
                    temp.setChecked(true);
                }
                cts.add(temp);
            }
        }
        return TreeJson.getfatherNode(cts);
    }

}
