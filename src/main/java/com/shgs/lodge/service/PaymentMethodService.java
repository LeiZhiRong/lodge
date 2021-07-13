package com.shgs.lodge.service;

import com.shgs.lodge.dto.PaymentMethodDto;
import com.shgs.lodge.dto.PaymentMethodListDto;
import com.shgs.lodge.dto.TreeJson;
import com.shgs.lodge.primary.dao.IPaymentMethodDao;
import com.shgs.lodge.primary.entity.PaymentMethod;
import com.shgs.lodge.util.Message;
import com.shgs.lodge.util.Pager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("paymentMethodService")
public class PaymentMethodService implements IPaymentMethodService {


    private IPaymentMethodDao paymentMethodDao;

    @Autowired
    public void setPaymentMethodDao(IPaymentMethodDao paymentMethodDao) {
        this.paymentMethodDao = paymentMethodDao;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message addPaymentMethod(PaymentMethod paymentMethod, String pid) {
        Message msg = new Message(0, "添加失败");
        //检测编号是否唯一
        if (StringUtils.isNotEmpty(paymentMethod.getPaymentBh())) {
            Integer o = paymentMethodDao.countProjectsBh(paymentMethod.getId(), pid, paymentMethod.getPaymentBh());
            if (o > 0) {
                msg.setMessage("编号:【" + paymentMethod.getPaymentBh() + "】已在使用中，请检测后重试！");
                return msg;
            }
        }
        int orders = paymentMethodDao.getMaxOrderByParent(pid);
        String ids = null;
        PaymentMethod pc = null;
        if (pid != null && !pid.isEmpty()) {
            pc = paymentMethodDao.queryPaymentMethod(pid);
            if (pc == null) {
                return new Message(0, "上级信息不正确");
            }
            ids = pc.getIds();
            paymentMethod.setParent(pc);
        }
        paymentMethod.setOrders(orders + 1);
        PaymentMethod mast = paymentMethodDao.add(paymentMethod);
        if (mast != null) {
            if (ids != null && !ids.isEmpty()) {
                mast.setIds(mast.getId() + "," + ids);
            } else {
                mast.setIds(mast.getId());
            }
            paymentMethodDao.update(mast);
            //检测上级目录标识
            if (pc != null && "F".equals(pc.getContents())) {
                pc.setContents("T");
                paymentMethodDao.update(pc);
            }
            msg.setData(pid);
            msg.setCode(1);
            msg.setMessage("添加成功");
        }
        return msg;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message updatePaymentMethod(PaymentMethod paymentMethod, String pid) {
        Message msg = new Message(0, "更新失败");
        //检测科目编号是否唯一
        if (StringUtils.isNotEmpty(paymentMethod.getPaymentBh())) {
            Integer o = paymentMethodDao.countProjectsBh(paymentMethod.getId(), pid, paymentMethod.getPaymentBh());
            if (o > 0) {
                msg.setMessage("编号:【" + paymentMethod.getPaymentBh() + "】已在使用中，请检测后重试！");
                return msg;
            }
        }
        //复制原科目上级科目
        PaymentMethod oldparent = paymentMethod.getParent();
        PaymentMethod parent = null;
        //如果上级项目不等同于原上级项目时获取新上级项目和排序号
        if (pid != null && !pid.isEmpty()) {
            if (oldparent == null || !pid.equals(oldparent.getId())) {
                parent = paymentMethodDao.queryPaymentMethod(pid);
                paymentMethod.setParent(parent);
                paymentMethod.setOrders(paymentMethodDao.getMaxOrderByParent(pid) + 1);
            }
        }
        //更新项目信息，上级项目处理
        if (paymentMethodDao.update(paymentMethod)) {
            msg.setCode(1);
            msg.setMessage("更新成功");
            if (parent != null && "F".equals(parent.getContents())) {
                parent.setContents("T");
                paymentMethodDao.update(parent);
            }
            if (pid != null && !pid.isEmpty()) {
                if (oldparent != null && parent != null && !oldparent.getId().equals(parent.getId())) {
                    paymentMethodDao.executeIds(paymentMethod.getId(), oldparent.getIds(), parent.getIds());
                }
            }
            if (oldparent != null && !oldparent.getId().equals(pid)) {
                if (paymentMethodDao.getCountPaymentMethodByPid(oldparent.getId()) == 0) {
                    oldparent.setContents("F");
                    paymentMethodDao.update(oldparent);
                    msg.setData(pid);
                }
            }
        }
        return msg;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message deletePaymentMethod(String id) {
        Message msg = new Message(0, "删除失败");
        //检测参数是否为空
        if (id == null || id.isEmpty()) {
            msg.setMessage("参数错误");
            return msg;
        }
        //检测是否有下级项目
        int o = paymentMethodDao.getCountPaymentMethodByPid(id);
        if (o > 0) {
            msg.setMessage("该项目已被引用" + o + "次，禁止删除");
            return msg;
        }
        PaymentMethod paymentMethod = paymentMethodDao.queryPaymentMethod(id);
        //执行删除
        if (paymentMethodDao.deletePaymentMethod(id)) {
            if (paymentMethod.getParent() != null) {
                PaymentMethod oldparent = paymentMethod.getParent();
                if (paymentMethodDao.getCountPaymentMethodByPid(oldparent.getId()) == 0) {
                    oldparent.setContents("F");
                    paymentMethodDao.update(oldparent);
                    msg.setData(oldparent.getParent() != null ? oldparent.getParent().getId() : "all");
                }
            }
            msg.setCode(1);
            msg.setMessage("删除成功");
        }
        return msg;
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public PaymentMethod queryPaymentMethod(String id) {
        return paymentMethodDao.queryPaymentMethod(id);
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public List<PaymentMethod> listByParent(String pid, String value) {
        return paymentMethodDao.listPaymentMethod(pid, value);
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public Pager<PaymentMethodDto> findPaymentMethodDto(String pid, String value) {
        return paymentMethodDao.findPaymentMethodDto(pid, value);
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public List<PaymentMethodListDto> listPaymentMethodDto(String pid, String value) {
        return paymentMethodDao.listPaymentMethodListDto(pid, value);
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public void updateSort(String[] ids) {
        paymentMethodDao.updateSort(ids);
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public PaymentMethod queryByPaymentBh(String paymentBh) {
        return paymentMethodDao.queryByPaymentBh(paymentBh);
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public List<TreeJson> getPaymentMethod2TreeJson(String keyword) {
        return paymentMethodDao.getPaymentMethod2TreeJson(keyword);
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public List<TreeJson> listPaymentMethod() {
        return paymentMethodDao.listPaymentMethod();
    }
}
