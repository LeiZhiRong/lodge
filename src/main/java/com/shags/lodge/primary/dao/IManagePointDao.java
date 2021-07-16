package com.shags.lodge.primary.dao;

import com.shags.lodge.primary.dao.basic.IBaseDAO;
import com.shags.lodge.primary.entity.ManagePoint;

import java.util.List;
import java.util.Map;

public interface IManagePointDao extends IBaseDAO<ManagePoint> {

    List<Map> listNotInManagePoint(String bookSet,String name);

}
