package com.shgs.lodge.primary.dao;

import com.shgs.lodge.primary.dao.basic.BaseDAO;
import com.shgs.lodge.primary.entity.AccounCode;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("accounCodeDao")
public class AccounCodeDao extends BaseDAO<AccounCode,String> implements IAccounCodeDao {

    @Override
    public AccounCode queryAccounCodeByID(String id) {
        return (AccounCode) super.queryObject("from AccounCode a where a.id =?0", id);
    }

    @Override
    public List<AccounCode> listAccounCode(String bookSet) {
        if(bookSet!=null&& !bookSet.isEmpty()) {
            return super.list("from AccounCode a where a.bookSet =?0 order by a.bookSet asc", bookSet);
        }else{
            return super.list("from AccounCode a  order by a.bookSet asc");
        }
    }

    @Override
    public boolean deleteAccounCodeByID(String id) {
        Object o = super.executeByJpql("delete from AccounCode a where a.id =?0", id);
        if (o != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<AccounCode> listAccounCode(String bookSet, String codeType) {
        return super.list("from AccounCode a where a.bookSet =?0 and a.codeType.id =?1",new Object[]{bookSet,codeType});
    }

    @Override
    public AccounCode queryAccounCode(String bookSet, String codeType) {
        return (AccounCode) super.queryObject("from AccounCode a where a.bookSet =?0 and a.codeType.parameCode =?1",new Object[]{bookSet,codeType});
    }
}
