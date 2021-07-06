package com.shgs.lodge.service;

import com.shgs.lodge.dto.AccounPeriodDto;
import com.shgs.lodge.primary.dao.IAccounPeriodDao;
import com.shgs.lodge.primary.entity.AccounPeriod;
import com.shgs.lodge.util.BeanUtil;
import com.shgs.lodge.util.CmsUtils;
import com.shgs.lodge.util.Message;
import com.shgs.lodge.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会计期间业务层接口实现类
 *
 * @author 雷智荣
 */
@Service("accounPeriodService")
public class AccounPeriodService implements IAccounPeriodService {

    @Autowired
    private IAccounPeriodDao accounPeriodDao;

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = false)
    public Message addAccounPeriod(AccounPeriod accounPeriod) {
        Message msg = new Message(0, "新增失败");
        AccounPeriod mast = (AccounPeriod) accounPeriodDao.queryObject("from AccounPeriod a where a.month =?0 ", accounPeriod.getMonth());
        if (mast != null) {
            msg.setMessage("【" + accounPeriod.getMonth() + "】已在使用中，新增失败！");
        } else {
            if (accounPeriodDao.add(accounPeriod) != null) {
                msg.setMessage("新成成功");
                msg.setCode(1);
            }
        }
        return msg;
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = false)
    public Message updateAccounPeriod(AccounPeriod accounPeriod) {
        Message msg = new Message(0, "更新失败");
        AccounPeriod mast = (AccounPeriod) accounPeriodDao.queryObject("from AccounPeriod a where a.month =?0 and a.id !=?1 ", new Object[]{accounPeriod.getMonth(), accounPeriod.getId()});
        if (mast != null) {
            msg.setMessage("【" + accounPeriod.getMonth() + "】已在使用中，更新失败！");
        } else {
            if (accounPeriodDao.update(accounPeriod)) {
                msg.setMessage("更新成功");
                msg.setCode(1);
            }
        }
        return msg;
    }


    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = false)
    public Message deleteAccounPeriod(String id) {
        Message msg = new Message(0, "删除失败");
        if (accounPeriodDao.delete(id)) {
            msg.setCode(1);
            msg.setMessage("删除成功");
        }
        return msg;
    }


    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public Pager<AccounPeriodDto> findAccounPeriodDto() {
        Pager<AccounPeriodDto> pager = new Pager<AccounPeriodDto>();
        Pager<AccounPeriod> accounPeriodPager = accounPeriodDao.find(" from AccounPeriod ");
        if (accounPeriodPager != null) {
            pager.setPageNumber(accounPeriodPager.getPageNumber());
            pager.setTotal(accounPeriodPager.getTotal());
            pager.setPageOffset(accounPeriodPager.getPageOffset());
            pager.setPageSize(accounPeriodPager.getPageSize());
            pager.setRows(new AccounPeriodDto().listAccounPeriodDto(accounPeriodPager.getRows()));
        }
        return pager;
    }


    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public List<AccounPeriodDto> listAccounPeriodDto() {
        List<AccounPeriodDto> cts = new ArrayList<>();
        List<AccounPeriod> dts = accounPeriodDao.list(" from AccounPeriod a order by a.month asc");
        if (dts != null && dts.size() > 0)
            cts = new AccounPeriodDto().listAccounPeriodDto(dts);
        return cts;
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public AccounPeriod load(String id) {
        return accounPeriodDao.load(id);
    }


    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public String getMonth() {
        StringBuffer jpql = new StringBuffer();
        Map<String, Object> alias = new HashMap<>();
        String nowDate = CmsUtils.getNowDate();
        jpql.append("from AccounPeriod a where a.startTime <=:nowdate  and a.endTime >=:nowdate");
        alias.put("nowdate", BeanUtil.strToTimestampTime(nowDate));
        AccounPeriod accounPeriod = (AccounPeriod) accounPeriodDao.queryObjectByAlias(jpql.toString(), alias);
        if (accounPeriod != null) {
            return accounPeriod.getMonth();
        } else {
            return BeanUtil.getYearMonth(BeanUtil.strToTimestampTime(nowDate));
        }
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public AccounPeriod getNowMonth() {
        StringBuffer jpql = new StringBuffer();
        Map<String, Object> alias = new HashMap<>();
        String nowDate = CmsUtils.getNowDate();
        jpql.append("from AccounPeriod a where a.startTime <=:nowdate  and a.endTime >=:nowdate");
        alias.put("nowdate", BeanUtil.strToTimestampTime(nowDate));
        AccounPeriod accounPeriod = (AccounPeriod) accounPeriodDao.queryObjectByAlias(jpql.toString(), alias);
        if (accounPeriod != null)
            return accounPeriod;
        return null;
    }


}
