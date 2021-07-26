package com.shags.lodge.service.business;

import com.shags.lodge.business.dao.ISitDownDao;
import com.shags.lodge.business.entity.SitDown;
import com.shags.lodge.util.Message;
import com.shags.lodge.util.Pager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yglei
 * @classname SitDownService
 * @description 资产管理-坐落位置Service接口实现类
 * @date 2021-07-23 9:59
 */
@Service("sitDownService")
public class SitDownService implements ISitDownService {

    private ISitDownDao sitDownDao;

    @Autowired
    public void setSitDownDao(ISitDownDao sitDownDao) {
        this.sitDownDao = sitDownDao;
    }

   
   

    @Override
    @Transactional(value = "businessTransactionManager")
    public Message addSitDown(SitDown sitDown) {
        Message msg=new Message(0,"添加失败");
        if(sitDownDao.add(sitDown)!=null){
            msg.setCode(1);
            msg.setMessage("添加成功");
        }
        return msg;
    }

    @Override
    @Transactional(value = "businessTransactionManager")
    public Message updateSitDown(SitDown sitDown) {
        Message msg=new Message(0,"保存失败");
        if(sitDownDao.update(sitDown)){
            msg.setMessage("保存成功");
            msg.setCode(1);
        }
        return msg;
    }

    @Override
    @Transactional(value = "businessTransactionManager")
    public Message deleteSitDown(String id) {
        Message msg=new Message(0,"删除失败");
        if(sitDownDao.delete(id)){
            msg.setMessage("删除成功");
            msg.setCode(1);
        }
        return msg;
    }

    @Override
    @Transactional(value = "businessTransactionManager",readOnly = true)
    public Pager<SitDown> findSitDown(String bookSet, String point_id, String keyWord) {
        StringBuilder jpql=new StringBuilder();
        Map<String,Object> alias= new HashMap<>();
        jpql.append(" from SitDown l where l.bookSet =:bookSet ");
        alias.put("bookSet",bookSet);
        if(StringUtils.isNotEmpty(point_id)){
            jpql.append(" and l.managerPoint =:managerPoint ");
            alias.put("managerPoint",point_id);
        }
        if(StringUtils.isNotEmpty(keyWord)){
            jpql.append(" and l.name like:keyword ");
            alias.put("keyword",keyWord+"%");
        }
        return sitDownDao.find(jpql.toString(),alias);
    }

    @Override
    @Transactional(value = "businessTransactionManager",readOnly = true)
    public SitDown querySitDown(String id) {
        return sitDownDao.load(id);
    }


}
