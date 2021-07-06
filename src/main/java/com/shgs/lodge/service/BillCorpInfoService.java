package com.shgs.lodge.service;

import com.shgs.lodge.dto.CorpInfoListDto;
import com.shgs.lodge.primary.dao.IBankAccountDao;
import com.shgs.lodge.primary.dao.IBillCorpInfoDao;
import com.shgs.lodge.primary.dao.ICustomParameDao;
import com.shgs.lodge.primary.entity.BillCorpInfo;
import com.shgs.lodge.util.Message;
import com.shgs.lodge.util.Pager;
import com.shgs.lodge.util.SelectJson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("billCorpInfoService")
public class BillCorpInfoService implements IBillCorpInfoService {

    @Autowired
    private IBillCorpInfoDao billCorpInfoDao;

    @Autowired
    private ICustomParameDao customParameDao;

    @Autowired
    private IBankAccountDao bankAccountDao;

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message saveBillCorpInfo(BillCorpInfo billCorpInfo) {
        Message msg = new Message(0, "保存失败");
        if (billCorpInfoDao.queryBillCorpInfoByCorpBM(billCorpInfo.getCorpBM()) != null) {
            msg.setMessage("客户编号已在使用中，禁止重复添加");
            return msg;
        }
        BillCorpInfo temp = billCorpInfoDao.add(billCorpInfo);
        if (temp != null) {
            msg.setMessage("保存成功");
            msg.setCode(1);
            msg.setData(temp.getId());
        }
        return msg;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message updateBillCorpInfo(BillCorpInfo billCorpInfo) {
        Message msg = new Message(0, "保存失败");
        if (billCorpInfoDao.ChickBillCorpOtherByCorpBM(billCorpInfo.getCorpBM(), billCorpInfo.getId())) {
            msg.setMessage("编号已在使用中，保存失败");
            return msg;
        }
        if (billCorpInfoDao.update(billCorpInfo)) {
            msg.setMessage("保存成功");
            msg.setCode(1);
        }
        return msg;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message deleteBillCorpInfo(String id) {
        Message msg = new Message(0, "删除失败");
        if (billCorpInfoDao.deleteBillCorpInfo(id)) {
            bankAccountDao.deleteBankAccountByCorpId(id);
            msg.setMessage("删除成功");
            msg.setCode(1);
        }
        return msg;
    }

    @Override
    public Pager<BillCorpInfo> listBillCorpInfo(String corpType, String keyword, String ztbz) {
        return billCorpInfoDao.listBillCorpInfo(corpType, keyword, ztbz);
    }

    @Override
    public Pager<BillCorpInfo> listBillCorpInfo(String keyword) {
        return billCorpInfoDao.listBillCorpInfo(null, keyword, null);
    }

    @Override
    public BillCorpInfo queryBillCorpInfoById(String id) {
        return billCorpInfoDao.queryBillCorpInfoById(id);
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message batchDeleteCorp(String ids) {
        Message msg = new Message(0, "删除失败");
        if (billCorpInfoDao.batchDeleteCorp(ids) > 0) {
            bankAccountDao.batchDeleteCorpBank(ids);
            msg.setCode(1);
            msg.setMessage("删除成功");
        }
        return msg;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public boolean batchSaveBillCorpInfo(List<BillCorpInfo> list) {
        return billCorpInfoDao.batchSaveBillCorpInfo(list);
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public boolean batchUpdateBillCorpInfo(List<BillCorpInfo> list) {
        return billCorpInfoDao.batchUpdateBillCorpInfo(list);
    }

    @Override
    public BillCorpInfo queryBillCorpInfoByCorpBM(String corpBM) {
        return billCorpInfoDao.queryBillCorpInfoByCorpBM(corpBM);
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public List<BillCorpInfo> listBillCorpInfoByKeyWord(String corpType,String keyword) {
        return billCorpInfoDao.listBillCorpInfo(corpType,keyword);
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public List<CorpInfoListDto> listCorpInfoListDto(String keyword, String pid) {
        if (StringUtils.isNotEmpty(pid)) {
            return billCorpInfoDao.listCorpInfoListDto(keyword, pid);
        } else {
            List<BillCorpInfo> billCorpInfoList = new ArrayList<BillCorpInfo>();
            List<SelectJson> temp = customParameDao.listCustomParameByCode("CORPTYPE");
            for (SelectJson mast : temp) {
                billCorpInfoList.add(new BillCorpInfo(mast.getValues(), null, mast.getValues(), mast.getName()));
            }
            return new CorpInfoListDto().listCorpInfoListDto(billCorpInfoList, "T");
        }
    }

}