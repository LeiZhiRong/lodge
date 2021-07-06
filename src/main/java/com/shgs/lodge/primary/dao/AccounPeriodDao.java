package com.shgs.lodge.primary.dao;

import com.shgs.lodge.primary.dao.basic.BaseDAO;
import com.shgs.lodge.primary.entity.AccounPeriod;
import org.springframework.stereotype.Repository;

/**
 * 会计期间持久层接口实现类
 * @author 雷智荣
 */
@Repository("accounPeriodDao")
public class AccounPeriodDao extends BaseDAO<AccounPeriod,String> implements IAccounPeriodDao {

}
