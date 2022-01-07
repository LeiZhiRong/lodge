package com.shags.lodge.service.primary;

import com.shags.lodge.dto.CorpInfoListDto;
import com.shags.lodge.dto.TreeJson;
import com.shags.lodge.util.Message;
import com.shags.lodge.util.Pager;
import com.shags.lodge.util.SelectJson;
import com.shags.lodge.primary.dao.IBankAccountDao;
import com.shags.lodge.primary.dao.IBillCorpInfoDao;
import com.shags.lodge.primary.dao.ICustomParameDao;
import com.shags.lodge.primary.entity.BillCorpInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("billCorpInfoService")
public class BillCorpInfoService implements IBillCorpInfoService {


    private IBillCorpInfoDao billCorpInfoDao;


    private ICustomParameDao customParameDao;


    private IBankAccountDao bankAccountDao;

    @Autowired
    public void setBillCorpInfoDao(IBillCorpInfoDao billCorpInfoDao) {
        this.billCorpInfoDao = billCorpInfoDao;
    }

    @Autowired
    public void setCustomParameDao(ICustomParameDao customParameDao) {
        this.customParameDao = customParameDao;
    }

    @Autowired
    public void setBankAccountDao(IBankAccountDao bankAccountDao) {
        this.bankAccountDao = bankAccountDao;
    }

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
    public List<CorpInfoListDto> listCorpInfoListDto(String keyword, String corpType, Integer status) {
        return billCorpInfoDao.listCorpInfoListDto(keyword, corpType, status);
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public List<CorpInfoListDto> listCorpInfoListDto(String keyword, String pid) {
        if (StringUtils.isNotEmpty(pid)) {
            return billCorpInfoDao.listCorpInfoListDto(keyword, pid);
        } else {
            List<BillCorpInfo> billCorpInfoList = new ArrayList<>();
            List<SelectJson> temp = customParameDao.listCustomParameByCode("CORPTYPE");
            for (SelectJson mast : temp) {
                billCorpInfoList.add(new BillCorpInfo(mast.getValues(), null, mast.getValues(), mast.getName()));
            }
            return new CorpInfoListDto().listCorpInfoListDto(billCorpInfoList, "T");
        }
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public List<TreeJson> listCorpInfoToTreeJson(String keyword, String corpType, Integer status) {
        return billCorpInfoDao.listCorpInfoToTreeJson(keyword, corpType, status);
    }


}
