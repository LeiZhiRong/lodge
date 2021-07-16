package com.shags.lodge.service;

import com.shags.lodge.dto.TreeJson;
import com.shags.lodge.util.CmsUtils;
import com.shags.lodge.util.Message;
import com.shags.lodge.util.SelectJson;
import com.shags.lodge.primary.dao.ICustomParameDao;
import com.shags.lodge.primary.dao.ICustomTypeDao;
import com.shags.lodge.primary.entity.CustomParame;
import com.shags.lodge.primary.entity.CustomType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义参数业务层Service接口实现类
 *
 * @author 雷智荣
 */
@Service("customService")
public class CustomService implements ICustomService {

    private ICustomParameDao customParameDao;

    private ICustomTypeDao customTypeDao;

    @Autowired
    public void setCustomParameDao(ICustomParameDao customParameDao) {
        this.customParameDao = customParameDao;
    }

    @Autowired
    public void setCustomTypeDao(ICustomTypeDao customTypeDao) {
        this.customTypeDao = customTypeDao;
    }


    @Override
    public CustomType getCustomType(String id) {
        return customTypeDao.queryCustomType(id);
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message savaCustomType(CustomType customType, String pid) {
        Message msg = new Message(0, "保存失败");
        if (StringUtils.isNotEmpty(pid)) {
            CustomType parent = customTypeDao.queryCustomType(pid);
            if (parent != null)
                customType.setParent(parent);
        }
        if (customType.getOrders() == null) {
            int orders = customTypeDao.getMaxOrderByParent(pid);
            customType.setOrders(orders + 1);
        }
        if (customTypeDao.add(customType) != null) {
            msg.setCode(1);
            msg.setMessage("保存成功");
        }
        return msg;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message updateCustomType(CustomType customType, String pid) {
        Message msg = new Message(0, "保存失败");
        if (StringUtils.isNotEmpty(pid)) {
            CustomType parent = customTypeDao.queryCustomType(pid);
            if (parent != null)
                customType.setParent(parent);
        }
        if (customType.getOrders() == null) {
            int orders = customTypeDao.getMaxOrderByParent(pid);
            customType.setOrders(orders + 1);
        }
        if (customTypeDao.update(customType)) {
            msg.setCode(1);
            msg.setMessage("保存成功");
        }
        return msg;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message deleteCustomType(String id) {
        Message msg = new Message(0, "删除失败");
        List<CustomType> list = customTypeDao.list("from CustomType c where c.parent.id =?0", id);
        List<CustomType> temp = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (CustomType mast : list) {
                mast.setParent(null);
                temp.add(mast);
            }
            customTypeDao.batchUpdate(temp);
        }
        if (customTypeDao.deleteCusTomType(id)) {
            //删除自定义参数
            customParameDao.batchDeleCustomParame(id, null);
            msg.setCode(1);
            msg.setMessage("删除成功");
        }
        return msg;
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public List<CustomType> listCustomType(String keyword) {
        return customTypeDao.listCustomType(keyword);
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message savaCustomParame(CustomParame customParame) {
        Message msg = new Message(0, "保存失败");
        int orders = customParameDao.getMaxOrderByParent(customParame.getTypeId());
        customParame.setOrders(orders + 1);
        if (customParameDao.add(customParame) != null) {
            msg.setMessage("保存成功");
            msg.setCode(1);
        }
        return msg;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message updateCustomParame(CustomParame customParame) {
        Message msg = new Message(0, "保存失败");
        if (customParameDao.update(customParame)) {
            msg.setMessage("保存成功");
            msg.setCode(1);
        }
        return msg;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message deleteCustomParame(String id) {
        Message msg = new Message(0, "保存失败");
        if (customParameDao.deleteCustomParame(id)) {
            msg.setMessage("删除成功");
            msg.setCode(1);
        }
        return msg;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message batchDeleCustomParame(String type_id, String param_ids) {
        Message msg = new Message(0, "删除失败");
        if (type_id == null || type_id.isEmpty()) {
            msg.setMessage("字典类型参数为空");
        } else {
            List<String> ids = CmsUtils.string2Array(param_ids, ",");
            Integer o = customParameDao.batchDeleCustomParame(type_id, ids);
            if (o > 0) {
                msg.setCode(1);
                msg.setMessage("删除成功");
            }
        }
        return msg;
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public CustomParame queryCustomParame(String id) {
        return customParameDao.queryCustomParame(id);
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public List<CustomParame> listCustomParame(String type_id, String keyword) {
        return customParameDao.listCustomParame(type_id, keyword);
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public void updateCusTomParamSort(String[] ids) {
        customParameDao.updateSort(ids);
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public List<SelectJson> listCustomParame(String typeCode, String keyword, boolean status) {
        return customParameDao.listCustomParame(typeCode, keyword, status);
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public List<SelectJson> listCustomParameByCode(String typeCode) {
        return customParameDao.listCustomParameByCode(typeCode);
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public CustomParame queryCustomParame(String typeCode, String keyword) {
        return customParameDao.queryCustomParame(typeCode, keyword);
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public List<TreeJson> findTreeJson() {
        return customTypeDao.findTreeJson();
    }

    @Override
    public List<SelectJson> listSelectJson() {
        List<SelectJson> dts = new ArrayList<>();
        List<CustomType> list = customTypeDao.list("from CustomType c where c.parent is null order by c.orders asc ");
        if (list != null && list.size() > 0) {
            for (CustomType mast : list) {
                dts.add(new SelectJson(mast.getId(), mast.getTypeName()));
            }
        }
        return dts;
    }
}
