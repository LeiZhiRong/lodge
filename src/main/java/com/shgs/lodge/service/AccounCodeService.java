package com.shgs.lodge.service;

import com.shgs.lodge.primary.dao.IAccounCodeDao;
import com.shgs.lodge.primary.entity.AccounCode;
import com.shgs.lodge.util.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 系统编号管理业务接口实现类
 * @author  雷智荣
 */

@Service("accounCodeService")
public class AccounCodeService implements IAccounCodeService{

    @Autowired
    private IAccounCodeDao accounCodeDao;

    @Override
    public AccounCode queryAccounCodeByID(String id) {
        return accounCodeDao.queryAccounCodeByID(id);
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message addAccounCode(AccounCode accounCode) {
        Message msg=new Message(0,"添加失败");
        if(accounCodeDao.listAccounCode(accounCode.getBookSet(),accounCode.getCodeType().getId()).size()>0){
            msg.setMessage("编号类型已在使用中，添加失败");
            return msg;
        }

        if(accounCodeDao.add(accounCode)!=null){
            msg.setCode(1);
            msg.setMessage("添加成功");
        }
        return msg;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message updateAccounCode(AccounCode accounCode) {
        Message msg=new Message(0,"更新失败");
        if(accounCodeDao.update(accounCode)){
            msg.setCode(1);
            msg.setMessage("更新成功");
        }
        return msg;
    }

    @Override
    public List<AccounCode> listAccounCode(String bookSet) {
        return accounCodeDao.listAccounCode(bookSet);
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message deleteAccounCodeByID(String id) {
        Message msg=new Message(0,"删除失败");
        if(accounCodeDao.deleteAccounCodeByID(id)){
            msg.setCode(1);
            msg.setMessage("删除成功");
        }
        return msg;
    }

    @Override
    public AccounCode queryAccounCode(String bookSet, String codeType) {
        return accounCodeDao.queryAccounCode(bookSet, codeType);
    }

}
