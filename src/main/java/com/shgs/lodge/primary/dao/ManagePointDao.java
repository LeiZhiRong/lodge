package com.shgs.lodge.primary.dao;

import com.shgs.lodge.primary.dao.basic.BaseDAO;
import com.shgs.lodge.primary.entity.ManagePoint;
import org.springframework.stereotype.Repository;

@Repository("managePointDao")
public class ManagePointDao extends BaseDAO<ManagePoint, String> implements IManagePointDao {
}
