package com.shgs.lodge.service;

import com.shgs.lodge.dto.AncillaryProjectsDto;
import com.shgs.lodge.dto.AncillaryProjectsListDto;
import com.shgs.lodge.dto.TreeJson;
import com.shgs.lodge.primary.dao.IAncillaryProjectsDao;
import com.shgs.lodge.primary.dao.ICustomParameDao;
import com.shgs.lodge.primary.entity.AncillaryProjects;
import com.shgs.lodge.primary.entity.CustomParame;
import com.shgs.lodge.util.Message;
import com.shgs.lodge.util.Pager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("ancillaryProjectsService")
public class AncillaryProjectsService implements IAncillaryProjectsService {

    @Autowired
    private IAncillaryProjectsDao ancillaryProjectsDao;

    @Autowired
    private ICustomParameDao customParameDao;

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message addAncillaryProjects(AncillaryProjects ancillaryProjects, String pid,String t_id) {
        Message msg = new Message(0, "添加失败");
        Integer orders = ancillaryProjectsDao.getMaxOrderByParent(pid);
        String ids = null;
        AncillaryProjects pc = null;
        if (pid != null && !pid.isEmpty()) {
            pc = ancillaryProjectsDao.queryAncillaryProjects(pid);
            if (pc == null) {
                return new Message(0, "上级项目信息不正确");
            }
            ids = pc.getIds();
            ancillaryProjects.setParent(pc);
        }
        if(StringUtils.isNotEmpty(t_id)){
            CustomParame customParame=customParameDao.queryCustomParame(t_id);
            if(customParame!=null)
                ancillaryProjects.setProjectType(customParame);
        }
        ancillaryProjects.setOrders(orders + 1);
        AncillaryProjects mast = ancillaryProjectsDao.add(ancillaryProjects);
        if (mast != null) {
            if (ids != null && !ids.isEmpty()) {
                mast.setIds(mast.getId() + "," + ids);
            } else {
                mast.setIds(mast.getId());
            }
            ancillaryProjectsDao.update(mast);
            //检测上级目录标识
            if (pc != null && "F".equals(pc.getContents())) {
                pc.setContents("T");
                ancillaryProjectsDao.update(pc);
            }
            msg.setData(pid);
            msg.setCode(1);
            msg.setMessage("添加成功");
        }
        return msg;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message updateAncillaryProjects(AncillaryProjects ancillaryProjects, String pid,String t_id) {
        Message msg = new Message(0, "更新失败");
        //复制原科目上级科目
        AncillaryProjects oldparent = ancillaryProjects.getParent();
        AncillaryProjects parent = null;
        //如果上级项目不等同于原上级项目时获取新上级项目和排序号
        if (pid != null && !pid.isEmpty()) {
            if (oldparent == null || !pid.equals(oldparent.getId())) {
                parent = ancillaryProjectsDao.queryAncillaryProjects(pid);
                ancillaryProjects.setParent(parent);
                ancillaryProjects.setOrders(ancillaryProjectsDao.getMaxOrderByParent(pid) + 1);
            }
        }
        if(StringUtils.isNotEmpty(t_id)){
            CustomParame customParame=customParameDao.queryCustomParame(t_id);
            if(customParame!=null)
                ancillaryProjects.setProjectType(customParame);
        }
        //更新项目信息，上级项目处理
        if (ancillaryProjectsDao.update(ancillaryProjects)) {
            msg.setCode(1);
            msg.setMessage("更新成功");
            if (parent != null && "F".equals(parent.getContents())) {
                parent.setContents("T");
                ancillaryProjectsDao.update(parent);
            }
            if (pid != null && !pid.isEmpty()) {
                if (oldparent != null && parent != null && oldparent.getId() != parent.getId()) {
                    ancillaryProjectsDao.executeIds(ancillaryProjects.getId(), oldparent.getIds(), parent.getIds());
                }
            }
            if (oldparent != null && oldparent.getId() != pid) {
                if (ancillaryProjectsDao.getCountAncillaryProjectsByPid(oldparent.getId()) == 0) {
                    oldparent.setContents("F");
                    ancillaryProjectsDao.update(oldparent);
                    msg.setData(pid);
                }
            }
        }
        return msg;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public Message deleteAncillaryProjects(String id) {
        Message msg = new Message(0, "删除失败");
        //检测参数是否为空
        if (id == null || id.isEmpty()) {
            msg.setMessage("参数错误");
            return msg;
        }
        //检测是否有下级项目
        int o = ancillaryProjectsDao.getCountAncillaryProjectsByPid(id);
        if (o > 0) {
            msg.setMessage("该项目已被引用" + o + "次，禁止删除");
            return msg;
        }
        AncillaryProjects ancillaryProjects = ancillaryProjectsDao.queryAncillaryProjects(id);
        //执行删除
        if (ancillaryProjectsDao.delete(id)) {
            if (ancillaryProjects.getParent() != null) {
                AncillaryProjects oldparent = ancillaryProjects.getParent();
                if (ancillaryProjectsDao.getCountAncillaryProjectsByPid(oldparent.getId()) == 0) {
                    oldparent.setContents("F");
                    ancillaryProjectsDao.update(oldparent);
                    msg.setData(oldparent.getParent() != null ? oldparent.getParent().getId() : "all");
                }
            }
            msg.setCode(1);
            msg.setMessage("删除成功");
        }
        return msg;
    }

    @Override
    public AncillaryProjects queryAncillaryProjects(String id) {

        return ancillaryProjectsDao.queryAncillaryProjects(id);
    }

    @Override
    public List<AncillaryProjects> listByParent(String pid, String value) {

        return ancillaryProjectsDao.listAncillaryProjects(pid, value);
    }

    @Override
    public Pager<AncillaryProjectsDto> findAncillaryProjectsDto(String pid, String value,String t_id) {

        return ancillaryProjectsDao.findAncillaryProjectsDto(pid, value,t_id);
    }

    @Override
    public List<AncillaryProjectsListDto> listAncillaryProjectsDto(String pid, String value,String t_id) {

        return ancillaryProjectsDao.listAncillaryProjectsListDto(pid, value,t_id);
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public void updateSort(String[] ids) {
        ancillaryProjectsDao.updateSort(ids);

    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public List<TreeJson> getAncillaryProjects2TreeJson(String keyword,String t_id) {
        return ancillaryProjectsDao.getAncillaryProjects2TreeJson(keyword,t_id);
    }
}
