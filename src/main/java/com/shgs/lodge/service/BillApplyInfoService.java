package com.shgs.lodge.service;

import com.shgs.lodge.Vo.BillConfirmInfoVo;
import com.shgs.lodge.dto.BillApplyHistoryInfoDto;
import com.shgs.lodge.dto.BillApplyInfoDto;
import com.shgs.lodge.dto.BillConfirmInfoDto;
import com.shgs.lodge.dto.ReveExpeItemListDto;
import com.shgs.lodge.primary.dao.IAccounCodeDao;
import com.shgs.lodge.primary.dao.IBillApplyInfoDao;
import com.shgs.lodge.primary.dao.IReveExpeItemDao;
import com.shgs.lodge.primary.dao.IVoucherInfoDao;
import com.shgs.lodge.primary.entity.AccounCode;
import com.shgs.lodge.primary.entity.BillApplyInfo;
import com.shgs.lodge.primary.entity.VoucherInfo;
import com.shgs.lodge.util.CmsUtils;
import com.shgs.lodge.util.Message;
import com.shgs.lodge.util.Pager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 开票申请业务接口实现类
 *
 * @author 雷智荣
 */
@Service("billApplyInfoService")
public class BillApplyInfoService implements IBillApplyInfoService {


    private IBillApplyInfoDao billApplyInfoDao;


    private IAccounCodeDao accounCodeDao;


    private IReveExpeItemDao reveExpeItemDao;


    private IVoucherInfoDao voucherInfoDao;

    @Autowired
    public void setBillApplyInfoDao(IBillApplyInfoDao billApplyInfoDao) {
        this.billApplyInfoDao = billApplyInfoDao;
    }

    @Autowired
    public void setAccounCodeDao(IAccounCodeDao accounCodeDao) {
        this.accounCodeDao = accounCodeDao;
    }

    @Autowired
    public void setReveExpeItemDao(IReveExpeItemDao reveExpeItemDao) {
        this.reveExpeItemDao = reveExpeItemDao;
    }

    @Autowired
    public void setVoucherInfoDao(IVoucherInfoDao voucherInfoDao) {
        this.voucherInfoDao = voucherInfoDao;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message saveBillApplyInfo(BillApplyInfo billApplyInfo) {
        Message msg = new Message(0, "保存失败");
        int orderNum = billApplyInfoDao.getMaxOrders() + 1;
        billApplyInfo.setOrders(orderNum);
        AccounCode accounCode = accounCodeDao.queryAccounCode(billApplyInfo.getBookSet(), "TG");
        String applyBH = CmsUtils.getAccounCode(accounCode, billApplyInfo.getApplyDept().getDeptID(), billApplyInfo.getBuyerCorp().getCorpBM(), orderNum);
        billApplyInfo.setApplyBH(applyBH);
        if (billApplyInfoDao.add(billApplyInfo) != null) {
            //获取入账规则
            List<ReveExpeItemListDto> cts = reveExpeItemDao.listReveExpeItemListDto(billApplyInfo.getProceedId(), billApplyInfo.getZtbz(), billApplyInfo.getOnAccount(), billApplyInfo.getPaymentMethod());
            if (cts != null && cts.size() > 0) {
                List<VoucherInfo> dts = new ReveExpeItemListDto().listVoucherInfo(cts, billApplyInfo);
                voucherInfoDao.batchSave(dts);
            }

            msg.setMessage("保存成功");
            msg.setCode(1);
        }
        return msg;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message updateBillApplyInfo(BillApplyInfo billApplyInfo, String proccedID) {
        Message msg = new Message(0, "更新失败");
        if (billApplyInfoDao.update(billApplyInfo)) {
            //删除凭证记录
            if (StringUtils.isEmpty(proccedID)) {
                proccedID = billApplyInfo.getProceedId();
            }
            voucherInfoDao.deleteVoucherInfo(billApplyInfo.getId(), proccedID, billApplyInfo.getZtbz(), billApplyInfo.getOnAccount(), billApplyInfo.getPaymentMethod());
            //获取入账规则
            List<ReveExpeItemListDto> cts = reveExpeItemDao.listReveExpeItemListDto(billApplyInfo.getProceedId(), billApplyInfo.getZtbz(), billApplyInfo.getOnAccount(), billApplyInfo.getPaymentMethod());
            if (cts != null && cts.size() > 0) {
                List<VoucherInfo> dts = new ReveExpeItemListDto().listVoucherInfo(cts, billApplyInfo);
                //添加凭证记录
                voucherInfoDao.batchSave(dts);
            }
            msg.setMessage("更新成功");
            msg.setCode(1);
        }
        return msg;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message deleteBillApplyInfo(String id) {
        Message msg = new Message(0, "删除失败");
        if (billApplyInfoDao.deleteBillApplyInfo(id)) {
            voucherInfoDao.batchDeleteVoucherInfo(id);
            msg.setMessage("删除成功");
            msg.setCode(1);
        }
        return msg;
    }

    @Override
    public BillApplyInfo queryBillApplyInfoById(String id) {
        return billApplyInfoDao.queryBillApplyInfoById(id);
    }

    @Override
    public Pager<BillApplyInfoDto> listBillApplyInfoDto(String field, String value, String ztbz, String bookSet, String userBh) {
        List<String> _ztbz = new ArrayList<>();
        if (ztbz != null && !ztbz.isEmpty()) {
            _ztbz = CmsUtils.string2Array(ztbz, ",");
        }
        Pager<BillApplyInfoDto> dto = new Pager<>();
        Pager<BillApplyInfo> list = billApplyInfoDao.listBillApplyInfo(field, value, null, null, _ztbz, bookSet, userBh);
        if (list != null) {
            dto.setRows(new BillApplyInfoDto().listBillApplyInfoDto(list.getRows()));
            dto.setPageSize(list.getPageSize());
            dto.setPageOffset(list.getPageOffset());
            dto.setTotal(list.getTotal());
            dto.setPageNumber(list.getPageNumber());
        }
        return dto;
    }

    @Override
    public Pager<BillApplyHistoryInfoDto> listHistoryInfoDto(String field, String value, String starDate, String endDate, List<String> ztbz, String bookSet, String userBh) {
        Pager<BillApplyHistoryInfoDto> dto = new Pager<>();
        Pager<BillApplyInfo> list = billApplyInfoDao.listBillApplyInfo(field, value, starDate, endDate, ztbz, bookSet, userBh);
        if (list != null) {
            dto.setRows(new BillApplyHistoryInfoDto().listHistoryInfoDto(list.getRows()));
            dto.setPageSize(list.getPageSize());
            dto.setPageOffset(list.getPageOffset());
            dto.setTotal(list.getTotal());
            dto.setPageNumber(list.getPageNumber());
        }
        return dto;
    }

    @Override
    public Pager<BillConfirmInfoDto> listBillConfirmInfoDto(String field, String value, String starDate, String endDate, String ztbz, String bookSet, String applyDeptBH, String saleCorpID, String buyerCorpID) {
        Pager<BillConfirmInfoDto> dto = new Pager<>();
        Pager<BillApplyInfo> list = billApplyInfoDao.listBillApplyInfo(field, value, starDate, endDate, ztbz, bookSet, applyDeptBH, saleCorpID, buyerCorpID);
        if (list != null) {
            dto.setRows(new BillConfirmInfoDto().listBillConfirmInfoDto(list.getRows()));
            dto.setPageSize(list.getPageSize());
            dto.setPageOffset(list.getPageOffset());
            dto.setTotal(list.getTotal());
            dto.setPageNumber(list.getPageNumber());
        }
        return dto;
    }

    @Override
    public List<BillConfirmInfoVo> listBillConfirmInfoVo(String field, String value, String starDate, String endDate, String ztbz, String bookSet, String applyDeptBH, String saleCorpID, String buyerCorpID) {
        List<BillConfirmInfoVo> dto = new ArrayList<>();
        List<BillApplyInfo> list = billApplyInfoDao.listToBillApplyInfo(field, value, starDate, endDate, ztbz, bookSet, applyDeptBH, saleCorpID, buyerCorpID);
        if (list != null && list.size() > 0) {
            dto = new BillConfirmInfoVo().listBillConfirmInfoVo(list);
        }
        return dto;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message batchDeleteApply(String ids) {
        Message msg = new Message(0, "删除失败");
        if (billApplyInfoDao.batchDeleteApply(ids) > 0) {
            voucherInfoDao.batchDeleteVoucherInfo(ids);
            msg.setCode(1);
            msg.setMessage("删除成功");
        }
        return msg;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public boolean batchSaveBillApplyInfo(List<BillApplyInfo> list) {
        return billApplyInfoDao.batchSaveBillApplyInfo(list);
    }


    @Override
    @Transactional(value = "primaryTransactionManager")
    public boolean batchUpdateBillApplyInfo(List<BillApplyInfo> list) {
        return billApplyInfoDao.batchUpdateBillApplyInfo(list);
    }

    @Override
    public String getOrderCode(String bookSet, String codeType, String deptBh, String corpBh) {
        int orderNum = billApplyInfoDao.getMaxOrders();
        AccounCode accounCode = accounCodeDao.queryAccounCode(bookSet, codeType);
        return CmsUtils.getAccounCode(accounCode, deptBh, corpBh, orderNum);
    }

    @Override
    public List<BillApplyInfo> exportBillApplyInfo(String field, String value, String starDate, String endDate, String ztbz, String bookSet, String userBh) {
        return billApplyInfoDao.exportBillApplyInfo(field, value, starDate, endDate, ztbz, bookSet, userBh);
    }

    @Override
    public Integer getMaxOrders() {
        return billApplyInfoDao.getMaxOrders();
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message batchExamine(String ids, String ztbz) {
        Message msg = new Message(0, "审核失败");
        if (billApplyInfoDao.batchExamine(ids, ztbz) > 0) {
            msg.setCode(1);
            msg.setMessage("审核成功");
        }
        return msg;
    }


}
