package com.shags.lodge.primary.dao;

import com.shags.lodge.primary.dao.basic.LodgeBaseDAO;
import com.shags.lodge.primary.entity.AccounPeriod;
import org.springframework.stereotype.Repository;

/**
 * 会计期间持久层接口实现类
 * @author 雷智荣
 */
@Repository("accounPeriodDao")
public class AccounPeriodDao extends LodgeBaseDAO<AccounPeriod,String> implements IAccounPeriodDao {

}
