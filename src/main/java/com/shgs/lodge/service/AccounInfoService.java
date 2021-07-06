package com.shgs.lodge.service;

import com.shgs.lodge.dto.AccounInfoDto;
import com.shgs.lodge.primary.dao.IAccounInfoDao;
import com.shgs.lodge.primary.entity.AccounInfo;
import com.shgs.lodge.util.Message;
import com.shgs.lodge.util.Pager;
import com.shgs.lodge.util.SelectJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 账套信息业务接口实现类
 *
 * @author 雷智荣
 */
@Service("accounInfoService")
public class AccounInfoService implements IAccounInfoService {
    @Autowired
    private IAccounInfoDao accounInfoDao;

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message addAccounInfo(AccounInfo accounInfo) {
        Message msg=new Message(0,"添加失败");
        if(accounInfoDao.queryAccounInfoByBookSet(accounInfo.getBookSet())!=null){
            msg.setMessage("账套编号已在使用中");
        }else {
            if(accounInfoDao.add(accounInfo)!=null){
                msg.setMessage("添加成功");
                msg.setCode(1);
            }
        }
        return msg;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message updateAccounInfo(AccounInfo accounInfo) {
        Message msg=new Message(0,"更新失败");
        if(accounInfoDao.queryAccounInfoByIDAndBookSet(accounInfo.getId(),accounInfo.getBookSet())!=null){
            msg.setMessage("账套编号已在使用中");
        }else {
            if(accounInfoDao.update(accounInfo)){
                msg.setMessage("保存成功");
                msg.setCode(1);
            }
        }
        return msg;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message deleteAccounInfo(String id) {
        Message msg=new Message(0,"删除失败");
        if(accounInfoDao.deleteAccounInfo(id)){
            msg.setMessage("删除成功");
            msg.setCode(1);
        }
        return msg;
    }

    @Override
    public AccounInfo queryAccounInfoByID(String id) {
        return accounInfoDao.queryAccounInfoByID(id);
    }

    @Override
    public AccounInfo queryAccounInfoByBookSet(String bookSet) {
        return accounInfoDao.queryAccounInfoByBookSet(bookSet);
    }


    @Override
    public List<SelectJson> listAccounInfoToSelectJson(String value, String ztbz) {
        return accounInfoDao.listAccounInfoToSelectJson(value, ztbz);
    }

    @Override
    public Pager<AccounInfoDto> findAccounInfoDto(String value, String ztbz) {
        return accounInfoDao.findAccounInfoDto(value, ztbz);
    }

}
