package com.shgs.lodge.primary.dao;

import com.shgs.lodge.primary.dao.basic.IBaseDAO;
import com.shgs.lodge.primary.entity.ManagePoint;

import java.util.List;
import java.util.Map;

public interface IManagePointDao extends IBaseDAO<ManagePoint> {

    List<Map> listNotInManagePoint(String bookSet,String name);

}
