package com.shgs.lodge.service;

import com.shgs.lodge.primary.dao.IAccounCodeDao;
import com.shgs.lodge.primary.dao.IBillAccounInfoDao;
import com.shgs.lodge.primary.entity.AccounCode;
import com.shgs.lodge.primary.entity.BillAccounInfo;
import com.shgs.lodge.util.CmsUtils;
import com.shgs.lodge.util.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 团购挂单-账务信息业务接口实现类
 *
 * @author 雷智荣
 */
@Service("billAccounInfoService")
public class BillAccounInfoService implements IBillAccounInfoService {
    @Autowired
    private IBillAccounInfoDao billAccounInfoDao;

    @Autowired
    private IAccounCodeDao accounCodeDao;

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message addBillAccounInfo(BillAccounInfo billAccounInfo) {
        Message msg = new Message(0, "添加失败");
        int orderNum = billAccounInfoDao.getMaxOrders() + 1;
        billAccounInfo.setOrders(orderNum);
        AccounCode accounCode = accounCodeDao.queryAccounCode(billAccounInfo.getBookSet(), "TRANS");
        String applyBH = CmsUtils.getAccounCode(accounCode, billAccounInfo.getDeptInfo().getDeptID(), billAccounInfo.getBuyerCorp().getCorpBM(), orderNum);
        billAccounInfo.setTransactionNumber(applyBH);
        if (billAccounInfoDao.add(billAccounInfo) != null) {
            msg.setCode(1);
            msg.setMessage("添加成功");
        }
        return msg;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message updateAccounInfo(BillAccounInfo billAccounInfo) {
        Message msg = new Message(0, "更新失败");
        if (billAccounInfoDao.update(billAccounInfo)) {
            msg.setCode(1);
            msg.setMessage("更新成功");
        }
        return msg;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message deleteBillAccounInfoById(String id) {
        Message msg = new Message(0, "删除失败");
        if (billAccounInfoDao.deleteBillAccounInfoByID(id)) {
            msg.setCode(1);
            msg.setMessage("删除成功");
        }
        return msg;
    }

    @Override
    public BillAccounInfo queryBillAccounInfoByID(String id) {
        return billAccounInfoDao.queryBillAccounInfoByID(id);
    }

    @Override
    public BillAccounInfo queryMaxBillAccounInfo(String saleCorpID, String buyerCorpID, String ztbz) {
        return billAccounInfoDao.queryMaxBillAccounInfo(saleCorpID, buyerCorpID, ztbz);
    }

    @Override
    public Integer getMaxOrders() {
        return billAccounInfoDao.getMaxOrders();
    }

}
