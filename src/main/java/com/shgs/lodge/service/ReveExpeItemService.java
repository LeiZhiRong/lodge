package com.shgs.lodge.service;

import com.shgs.lodge.dto.ProceedTypeListDto;
import com.shgs.lodge.dto.ReveExpeItemDto;
import com.shgs.lodge.dto.ReveExpeItemListDto;
import com.shgs.lodge.dto.TreeJson;
import com.shgs.lodge.primary.dao.*;
import com.shgs.lodge.primary.entity.*;
import com.shgs.lodge.util.CmsUtils;
import com.shgs.lodge.util.Message;
import com.shgs.lodge.util.SelectJson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 收支项目业务接口实现类
 *
 * @author 雷智荣
 */
@Service("reveExpeItemService")
public class ReveExpeItemService implements IReveExpeItemService {


    private IProceedTypeDao proceedTypeDao;


    private IReveExpeItemDao reveExpeItemDao;


    private ICashBankDao cashBankDao;


    private IBillCorpInfoDao billCorpInfoDao;


    private IDeptInfoDao deptInfoDao;


    @Autowired
    public void setProceedTypeDao(IProceedTypeDao proceedTypeDao) {
        this.proceedTypeDao = proceedTypeDao;
    }

    @Autowired
    public void setReveExpeItemDao(IReveExpeItemDao reveExpeItemDao) {
        this.reveExpeItemDao = reveExpeItemDao;
    }

    @Autowired
    public void setCashBankDao(ICashBankDao cashBankDao) {
        this.cashBankDao = cashBankDao;
    }

    @Autowired
    public void setBillCorpInfoDao(IBillCorpInfoDao billCorpInfoDao) {
        this.billCorpInfoDao = billCorpInfoDao;
    }

    @Autowired
    public void setDeptInfoDao(IDeptInfoDao deptInfoDao) {
        this.deptInfoDao = deptInfoDao;
    }



    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message addProceedType(ProceedType proceedType, String pid) {
        Message msg = new Message(0, "添加失败");
        ProceedType temp = (ProceedType) proceedTypeDao.queryObject("from ProceedType p where p.proceedBh =?0", proceedType.getProceedBh());
        if (temp != null) {
            msg.setMessage("项目编号【" + proceedType.getProceedBh() + "】已在使用中...，添加失败!");
            return msg;
        }
        int orders = proceedTypeDao.getMaxOrderByParent(pid);
        String ids = null;
        ProceedType pc = null;
        if (pid != null && !pid.isEmpty()) {
            pc = this.queryProceedTypeById(pid);
            if (pc == null) {
                return new Message(0, "上级项目信息不正确");
            }
            ids = pc.getIds();
            proceedType.setParent(pc);
        }
        proceedType.setOrders(orders + 1);
        ProceedType mast = proceedTypeDao.add(proceedType);
        if (mast != null) {
            if (ids != null && !ids.isEmpty()) {
                mast.setIds(mast.getId() + "," + ids);
            } else {
                mast.setIds(mast.getId());
            }
            proceedTypeDao.update(mast);
            //检测上级目录标识
            if (pc != null && "F".equals(pc.getContents())) {
                pc.setContents("T");
                proceedTypeDao.update(pc);
            }
            msg.setData(pid);
            msg.setCode(1);
            msg.setMessage("部门添加成功");
        }
        return msg;
    }


    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message updateProceedType(ProceedType proceedType, String pid) {
        Message msg = new Message(0, "保存失败");
        ProceedType temp = (ProceedType) proceedTypeDao.queryObject("from ProceedType p where p.proceedBh =?0 and p.id !=?1", new Object[]{proceedType.getProceedBh(), proceedType.getId()});
        if (temp != null) {
            msg.setMessage("项目编号【" + proceedType.getProceedBh() + "】已在使用中...，保存失败!");
            return msg;
        }
        //复制原项目上级项目
        ProceedType oldparent = proceedType.getParent();
        ProceedType parent = null;
        //如果上级项目不等同于原上级项目时获取新上级项目和排序号
        if (pid != null && !pid.isEmpty()) {
            if (oldparent == null || !pid.equals(oldparent.getId())) {
                parent = this.queryProceedTypeById(pid);
                proceedType.setParent(parent);
                proceedType.setOrders(proceedTypeDao.getMaxOrderByParent(pid) + 1);
            }
        }
        //更新项目信息，上级项目处理
        if (proceedTypeDao.update(proceedType)) {
            msg.setCode(1);
            msg.setMessage("更新成功");
            if (parent != null && "F".equals(parent.getContents())) {
                parent.setContents("T");
                proceedTypeDao.update(parent);
            }
            if (pid != null && !pid.isEmpty()) {
                if (oldparent != null && parent != null && !oldparent.getId().equals(parent.getId())) {
                    proceedTypeDao.executeIds(proceedType.getId(), oldparent.getIds(), parent.getIds());
                }
            }
            if (oldparent != null && !oldparent.getId().equals(pid)) {
                if (proceedTypeDao.getCountProceedTypeByPid(oldparent.getId()) == 0) {
                    oldparent.setContents("F");
                    proceedTypeDao.update(oldparent);
                    msg.setData(pid);
                }
            }
        }
        return msg;
    }


    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message deleteProceedType(String id) {
        Message msg = new Message(0, "删除失败");
        //检测是否有下级项目
        int o = proceedTypeDao.getCountProceedTypeByPid(id);
        if (o > 0) {
            msg.setMessage("该项目已被引用" + o + "次，禁止删除");
            return msg;
        }
        ReveExpeItem reveExpeItem = (ReveExpeItem) reveExpeItemDao.queryObject("from ReveExpeItem r where r.proceedType.id =?0", id);
        if (reveExpeItem != null) {
            msg.setMessage("该项目已在使用中，禁止删除");
            return msg;
        }
        if (proceedTypeDao.deleteProceedTypeById(id)) {
            msg.setCode(1);
            msg.setMessage("保存成功");
        }
        return msg;
    }


    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public ProceedType queryProceedTypeById(String id) {
        return (ProceedType) proceedTypeDao.queryObject("from ProceedType p where p.id =?0 ", id);
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public ProceedType queryProceedTypeByNameAndProceedBh(String keyword) {
        return (ProceedType) proceedTypeDao.queryObject("from ProceedType p where p.proceedBh =?0 or p.name =?1 ", new Object[]{keyword, keyword});
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public List<SelectJson> listProceedType() {
        List<SelectJson> jsonList = new ArrayList<>();
        List<ProceedType> list = proceedTypeDao.list("from ProceedType ");
        if (list != null && list.size() > 0) {
            for (ProceedType mast : list) {
                jsonList.add(new SelectJson(mast.getId(), mast.getName()));
            }
        }
        return jsonList;
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public List<TreeJson> listProceedTypeToTreeJson(String keyword) {
        List<TreeJson> cts = new ArrayList<>();
        List<Map> dts = proceedTypeDao.listToMapBySql("select m.id as id,m.name as text,m.proceedBh as proceedBh,m.pid as pid,m.contents as contents from proceed_type m  order by m.pid asc,m.orders asc");
        if (dts.size() > 0) {
            List<String> list = CmsUtils.string2Array(keyword, ";");
            for (Map map : dts) {
                TreeJson temp = new TreeJson();
                temp.setId((String) map.get("id"));
                temp.setText(map.get("text") + "【" + map.get("proceedBh") + "】");
                temp.setPid((String) map.get("pid"));
                temp.setArg(map.get("deptID"));
                if (list.contains(map.get("deptID")))
                    temp.setChecked(true);
                temp.setArg1(map.get("contents"));
                cts.add(temp);
            }
        }
        return TreeJson.getfatherNode(cts);
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message addReveExpeItem(ReveExpeItem reveExpeItem, String proceedTypeId, String cashBankId, String saleCorpId, String deptId) {
        Message msg = new Message(0, "增加失败");
        //费项
        if (StringUtils.isNotEmpty(proceedTypeId)) {
            ProceedType proceedType = this.queryProceedTypeById(proceedTypeId);
            reveExpeItem.setProceedType(proceedType);
        }
        //科目
        if (StringUtils.isNotEmpty(cashBankId)) {
            CashBank cashBank = cashBankDao.queryCashBank(cashBankId);
            reveExpeItem.setCashBank(cashBank);
        }
        //客商绑定
        if (StringUtils.isNotEmpty(saleCorpId)) {
            BillCorpInfo billCorpInfo = billCorpInfoDao.queryBillCorpInfoById(saleCorpId);
            reveExpeItem.setSaleCorp(billCorpInfo);
        } else {
            reveExpeItem.setSaleCorp(null);
        }
        //部门绑定
        if (StringUtils.isNotEmpty(deptId)) {
            DeptInfo deptInfo = deptInfoDao.queryDeptInfo(deptId);
            reveExpeItem.setDeptInfo(deptInfo);
        } else {
            reveExpeItem.setDeptInfo(null);
        }
        if (reveExpeItemDao.add(reveExpeItem) != null) {
            msg.setCode(1);
            msg.setMessage("增加成功");
        }
        return msg;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message updateReveExpeItem(ReveExpeItem reveExpeItem, String proceedTypeId, String cashBankId, String saleCorpId, String deptId) {
        Message msg = new Message(0, "保存失败");
        //费项
        if (StringUtils.isNotEmpty(proceedTypeId)) {
            ProceedType proceedType = this.queryProceedTypeById(proceedTypeId);
            reveExpeItem.setProceedType(proceedType);
        }
        //科目
        if (StringUtils.isNotEmpty(cashBankId)) {
            CashBank cashBank = cashBankDao.queryCashBank(cashBankId);
            reveExpeItem.setCashBank(cashBank);
        }
        //客商绑定
        if (StringUtils.isNotEmpty(saleCorpId)) {
            BillCorpInfo billCorpInfo = billCorpInfoDao.queryBillCorpInfoById(saleCorpId);
            reveExpeItem.setSaleCorp(billCorpInfo);
        } else {
            reveExpeItem.setSaleCorp(null);
        }
        //部门绑定
        if (StringUtils.isNotEmpty(deptId)) {
            DeptInfo deptInfo = deptInfoDao.queryDeptInfo(deptId);
            reveExpeItem.setDeptInfo(deptInfo);
        } else {
            reveExpeItem.setDeptInfo(null);
        }
        if (reveExpeItemDao.update(reveExpeItem)) {
            msg.setCode(1);
            msg.setMessage("更新成功");
        }
        return msg;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message deleteReveExpeItem(String id) {
        Object o = reveExpeItemDao.executeByJpql("delete from ReveExpeItem m where m.id =?0", id);
        if (o != null) {
            return new Message(1, "删除成功");
        }
        return new Message(1, "删除失败");
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public List<ReveExpeItemDto> listReveExpeItemDto(String proceedTypeId) {
        List<ReveExpeItemDto> dto = new ArrayList<>();
        StringBuilder jpql = new StringBuilder();
        Map<String, Object> alias = new HashMap<>();
        jpql.append(" from ReveExpeItem r where 1=1 ");
        if (StringUtils.isNotEmpty(proceedTypeId) && !"all".equals(proceedTypeId)) {
            jpql.append("and r.proceedType.id =:proceedTypeId  ");
            alias.put("proceedTypeId", proceedTypeId);
        }
        List<ReveExpeItem> list = reveExpeItemDao.listByAlias(jpql.toString(), alias);
        if (list != null && list.size() > 0) {
            dto = new ReveExpeItemDto().listReveExpeItemDto(list);
        }
        return dto;
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public ReveExpeItemDto queryReveExpeItemDtoById(String id) {
        ReveExpeItemDto dto = new ReveExpeItemDto();
        ReveExpeItem reveExpeItem = this.queryReveExpeItemById(id);
        if (reveExpeItem != null) {
            dto = new ReveExpeItemDto(reveExpeItem);
            dto.setHandle(null);
            dto.setFormatZtbz(null);
            dto.setCashBankBhAndName(null);

        }
        return dto;
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public ReveExpeItemDto queryReveExpeItemDtoByReBh(String reBh) {
        ReveExpeItemDto dto = new ReveExpeItemDto();
        ReveExpeItem reveExpeItem = this.queryReveExpeItemByReBh(reBh);
        if (reveExpeItem != null) {
            dto = new ReveExpeItemDto(reveExpeItem);
            dto.setHandle(null);
            dto.setFormatZtbz(null);
            dto.setCashBankBhAndName(null);
        }
        return dto;
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public ReveExpeItem queryReveExpeItemById(String id) {
        return (ReveExpeItem) reveExpeItemDao.queryObject("from ReveExpeItem m where m.id =?0", id);
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public ReveExpeItem queryReveExpeItemByReBh(String reBh) {
        return (ReveExpeItem) reveExpeItemDao.queryObject(" from ReveExpeItem m where m.reBh =?0", reBh);
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public List<ProceedTypeListDto> listProceedTypeListDto(String pid, String value) {
        return proceedTypeDao.listProceedTypeListDto(pid, value);
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public List<ReveExpeItemListDto> listReveExpeItemListDto(String proceedId, String auditStatus, String onAccount, String paymentMethod) {
        return reveExpeItemDao.listReveExpeItemListDto(proceedId, auditStatus, onAccount, paymentMethod);
    }


}
