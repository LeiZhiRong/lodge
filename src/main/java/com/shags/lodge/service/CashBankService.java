package com.shags.lodge.service;

import com.shags.lodge.dto.CashBankDto;
import com.shags.lodge.dto.CashBankListDto;
import com.shags.lodge.dto.TreeJson;
import com.shags.lodge.util.Message;
import com.shags.lodge.util.Pager;
import com.shags.lodge.primary.dao.ICashBankDao;
import com.shags.lodge.primary.entity.CashBank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 科目管理业务接口实现类
 *
 * @author 雷智荣
 */

@Service("cashBankService")
public class CashBankService implements ICashBankService {


    private ICashBankDao cashBankDao;

    @Autowired
    public void setCashBankDao(ICashBankDao cashBankDao) {
        this.cashBankDao = cashBankDao;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message addCashBank(CashBank cashBank, String pid) {
        Message msg = new Message(0, "科目添加失败");
        //检测科目编号是否唯一
        Integer o = cashBankDao.countKmBH(cashBank.getId(), pid, cashBank.getKmBH());
        if (o > 0) {
            msg.setMessage("科目编号:【" + cashBank.getKmBH() + "】已在使用中，请检测后重试！");
            return msg;
        }
        int orders = cashBankDao.getMaxOrderByParent(pid);
        String ids = null;
        CashBank pc = null;
        if (pid != null && !pid.isEmpty()) {
            pc = cashBankDao.queryCashBank(pid);
            if (pc == null) {
                return new Message(0, "上级科目信息不正确");
            }
            ids = pc.getIds();
            cashBank.setParent(pc);
        }
        cashBank.setOrders(orders + 1);
        CashBank mast = cashBankDao.add(cashBank);
        if (mast != null) {
            if (ids != null && !ids.isEmpty()) {
                mast.setIds(mast.getId() + "," + ids);
            } else {
                mast.setIds(mast.getId());
            }
            cashBankDao.update(mast);
            //检测上级目录标识
            if (pc != null && "F".equals(pc.getContents())) {
                pc.setContents("T");
                cashBankDao.update(pc);
            }
            msg.setData(pid);
            msg.setCode(1);
            msg.setMessage("科目添加成功");
        }
        return msg;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message updateCashBank(CashBank cashBank, String pid) {
        Message msg = new Message(0, "科目信息更新失败");
        //检测科目编号是否唯一
        Integer o = cashBankDao.countKmBH(cashBank.getId(), pid, cashBank.getKmBH());
        if (o > 0) {
            msg.setMessage("科目编号:【" + cashBank.getKmBH() + "】已在使用中，请检测后重试！");
            return msg;
        }
        //复制原科目上级科目
        CashBank oldparent = cashBank.getParent();
        CashBank parent = null;
        //如果上级科目不等同于原上级科目时获取新上级科目和排序号
        if (pid != null && !pid.isEmpty()) {
            if (oldparent == null || !pid.equals(oldparent.getId())) {
                parent = cashBankDao.queryCashBank(pid);
                cashBank.setParent(parent);
                cashBank.setOrders(cashBankDao.getMaxOrderByParent(pid)+1);
            }
        }
        //更新科目信息，上级科目处理
        if (cashBankDao.update(cashBank)) {
            msg.setCode(1);
            msg.setMessage("更新成功");
            if (parent != null && "F".equals(parent.getContents())) {
                parent.setContents("T");
                cashBankDao.update(parent);
            }
            if (pid != null && !pid.isEmpty()) {
                if (oldparent != null && parent != null && !oldparent.getId().equals(parent.getId())) {
                    cashBankDao.executeIds(cashBank.getId(), oldparent.getIds(), parent.getIds());
                }
            }
            if (oldparent != null && !oldparent.getId().equals(pid)) {
                if (cashBankDao.getCountCashBankByPid(oldparent.getId()) == 0) {
                    oldparent.setContents("F");
                    cashBankDao.update(oldparent);
                    msg.setData(pid);
                }
            }
        }
        return msg;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message deleteCashBank(String id) {
        Message msg = new Message(0, "科目信息删除失败");
        //检测参数是否为空
        if (id == null || id.isEmpty()) {
            msg.setMessage("参数错误");
            return msg;
        }
        //检测是否有下级科目
        int o = cashBankDao.getCountCashBankByPid(id);
        if (o > 0) {
            msg.setMessage("该科目已被引用" + o + "次，禁止删除");
            return msg;
        }
        CashBank cashBank = cashBankDao.queryCashBank(id);
        //执行删除
        if (cashBankDao.deleteCashBank(id)) {
            if (cashBank.getParent() != null) {
                CashBank oldparent = cashBank.getParent();
                if (cashBankDao.getCountCashBankByPid(oldparent.getId()) == 0) {
                    oldparent.setContents("F");
                    cashBankDao.update(oldparent);
                    msg.setData(oldparent.getParent() != null ? oldparent.getParent().getId() : "all");
                }
            }
            msg.setCode(1);
            msg.setMessage("部门信息删除成功");
        }
        return msg;
    }

    @Override
    public CashBank queryCashBank(String id) {
        return cashBankDao.queryCashBank(id);
    }

    @Override
    public List<CashBank> listByParent(String pid, String value) {
        return cashBankDao.listCashBank(pid, value);
    }

    @Override
    public Pager<CashBankDto> findCashBankDto(String pid, String value) {
        return cashBankDao.findCashBankDto(pid, value);
    }

    @Override
    public List<CashBankListDto> listCashBankDto(String pid, String value) {
        return cashBankDao.listCashBankDto(pid, value);
    }


    @Override
    @Transactional(value = "primaryTransactionManager")
    public void updateSort(String[] ids) {
        cashBankDao.updateSort(ids);
    }

    @Override
    public CashBank queryByKmBH(String kmBH) {
        return cashBankDao.queryByKmBH(kmBH);
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public List<TreeJson> getCashBank2TreeJson(String keyword) {
        return cashBankDao.getCashBank2TreeJson(keyword);
    }
}
