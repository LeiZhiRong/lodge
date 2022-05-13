package com.shags.lodge.business.dao;

import com.shags.lodge.business.dao.basic.BusinessBaseDAO;
import com.shags.lodge.business.entity.OperationLogDetail;
import org.springframework.stereotype.Repository;

/**
 * @author yglei
 * @classname OperationLogDetailDao
 * @description 操作日志详细持久层接口类
 * @date 2022-01-13 13:53
 */
@Repository("operationLogDetailDao")
public class OperationLogDetailDao extends BusinessBaseDAO<OperationLogDetail, String> implements IOperationLogDetailDao {
}
