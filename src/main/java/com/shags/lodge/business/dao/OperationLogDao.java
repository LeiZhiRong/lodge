package com.shags.lodge.business.dao;

import com.shags.lodge.business.dao.basic.BusinessBaseDAO;
import com.shags.lodge.business.entity.OperationLog;
import org.springframework.stereotype.Repository;

/**
 * @author yglei
 * @classname OperationLogDao
 * @description 操作日志持久层实现类
 * @date 2022-01-13 13:39
 */
@Repository("operationLogDao")
public class OperationLogDao  extends BusinessBaseDAO<OperationLog, String> implements  IOperationLogDao {

}
