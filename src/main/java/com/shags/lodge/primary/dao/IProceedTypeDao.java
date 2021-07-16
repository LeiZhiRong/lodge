package com.shags.lodge.primary.dao;

import com.shags.lodge.dto.ProceedTypeListDto;
import com.shags.lodge.primary.dao.basic.IBaseDAO;
import com.shags.lodge.primary.entity.ProceedType;

import java.util.List;

public interface IProceedTypeDao extends IBaseDAO<ProceedType> {

    boolean deleteProceedTypeById(String id);

    int getMaxOrderByParent(String pid);

    void executeIds(String id, String oldIds, String newIds);

    int getCountProceedTypeByPid(String pid);

    List<ProceedTypeListDto> listProceedTypeListDto(String pid, String value);

}
