package com.shgs.lodge.service;

import com.shgs.lodge.auth.AuthMethod;
import com.shgs.lodge.dto.ManagePointDto;
import com.shgs.lodge.primary.dao.IManagePointDao;
import com.shgs.lodge.primary.entity.ManagePoint;
import com.shgs.lodge.util.Message;
import com.shgs.lodge.util.Pager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("managePointService")
public class ManagePointService implements IManagePointService {
    @AuthMethod
    private IManagePointDao managePointDao;

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public ManagePoint queryManagePointByID(String id) {
        return (ManagePoint) managePointDao.queryObject("from ManagePoint m where m.id =?0 )", id);
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message addManagePoint(ManagePoint managePoint) {
        Message msg = new Message(0, "新增失败");
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
    public Pager<ManagePointDto> findManagePointDto(String bookSet, String keyword) {
        StringBuffer jpql = new StringBuffer();
        Map<String, Object> alias = new HashMap<>();
        Pager<ManagePointDto> managePointDtoPager = new Pager<>();
        jpql.append(" from ManagePoint m where m.bookSet =:bookSet  ");
        alias.put("bookSet", bookSet);
        if (StringUtils.isNotEmpty(keyword)) {
            jpql.append(" and ( m.bh like:keyword  or m.name like:keyword ");
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
        StringBuffer jpql = new StringBuffer();
        Map<String, Object> alias = new HashMap<>();
        jpql.append(" from ManagePoint m where m.ztbz=1 and m.bookSet =:bookSet  ");
        alias.put("bookSet", bookSet);
        if (StringUtils.isNotEmpty(keyword)) {
            jpql.append(" and ( m.bh =:keyword  or m.name =:keyword ");
            alias.put("keyword", keyword);
        }
        jpql.append(" order by m.rVTime asc ");
        return (ManagePoint) managePointDao.queryObjectByAlias(jpql.toString(), alias);
    }
}
