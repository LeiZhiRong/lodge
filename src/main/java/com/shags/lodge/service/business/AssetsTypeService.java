package com.shags.lodge.service.business;

import com.shags.lodge.business.dao.IAssetsTypeDao;
import com.shags.lodge.business.entity.AssetsType;
import com.shags.lodge.dto.TreeJson;
import com.shags.lodge.dto.business.AssetsTypeListDto;
import com.shags.lodge.dto.business.AssetsTypePage;
import com.shags.lodge.util.CmsUtils;
import com.shags.lodge.util.Message;
import com.shags.lodge.util.Pager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yglei
 * @date 2021-07-1917:03
 */
@Service("assetsTypeService")
public class AssetsTypeService implements IAssetsTypeService {


    private IAssetsTypeDao assetsTypeDao;

    @Autowired
    public void setAssetsTypeDao(IAssetsTypeDao assetsTypeDao) {
        this.assetsTypeDao = assetsTypeDao;
    }


    @Override
    @Transactional(value = "businessTransactionManager")
    public Message addAssetsType(AssetsType assetsType, String pid) {
        Message msg = new Message(0, "新增失败");
        //检测分类名称是否唯一
        boolean check = assetsTypeDao.checkAssetsType(assetsType.getBookSet(), assetsType.getBh(), assetsType.getName(), null);
        if (check) {
            msg.setMessage("资产分类编号或资产分类名称存在重名，请检测后重试！");
            return msg;
        }
        int orders = assetsTypeDao.getMaxOrderByParent(assetsType.getBookSet(), pid);
        String ids = null;
        AssetsType pc = null;
        String bh = "";
        if (StringUtils.isNotEmpty(pid)) {
            pc = assetsTypeDao.load(pid);
            if (pc == null) {
                return new Message(0, "上级分类信息不正确");
            } else {
                ids = pc.getIds();
                assetsType.setParent(pc);
                bh = pc.getBh();
            }
        }
        assetsType.setOrders(orders + 1);
        if (StringUtils.isEmpty(assetsType.getBh())) {
            assetsType.setBh(bh + assetsType.getOrders());
        }
        AssetsType mast = assetsTypeDao.add(assetsType);
        if (mast != null) {
            mast.setIds(ids != null && !ids.isEmpty() ? mast.getId() + "," + ids : mast.getId());
            assetsTypeDao.update(mast);
            //检测上级目录标识
            if (pc != null && "F".equals(pc.getContents())) {
                pc.setContents("T");
                assetsTypeDao.update(pc);
            }
            msg.setData(pid);
            msg.setCode(1);
            msg.setMessage("资产分类添加成功");
        }
        return msg;
    }

    @Override
    @Transactional(value = "businessTransactionManager")
    public Message updateAssetsType(AssetsType assetsType, String pid) {
        Message msg = new Message(0, "更新失败");
        //检测分类名称是否唯一
        boolean check = assetsTypeDao.checkAssetsType(assetsType.getBookSet(), assetsType.getBh(), assetsType.getName(), assetsType.getId());
        if (check) {
            msg.setMessage("资产分类编号或资产分类名称存在重名，请检测后重试！");
            return msg;
        }
        //复制原分类上级分类
        AssetsType oldParent = assetsType.getParent();
        AssetsType parent = null;
        //如果上级分类不等同于原上级分类时获取新上级分类和排序号
        if (StringUtils.isNotEmpty(pid)) {
            if (oldParent == null || !pid.equals(oldParent.getId())) {
                parent = assetsTypeDao.load(pid);
                assetsType.setParent(parent);
                assetsType.setOrders(assetsTypeDao.getMaxOrderByParent(assetsType.getBookSet(), pid) + 1);
                assetsType.setBh(parent.getBh() + assetsType.getOrders());
            }
        }
        //更新分类信息，上级分类处理
        if (assetsTypeDao.update(assetsType)) {
            msg.setCode(1);
            msg.setMessage("更新成功");
            if (parent != null && "F".equals(parent.getContents())) {
                parent.setContents("T");
                assetsTypeDao.update(parent);
            }
            if (pid != null && !pid.isEmpty()) {
                if (oldParent != null && parent != null && !oldParent.getId().equals(parent.getId())) {
                    assetsTypeDao.executeIds(assetsType.getId(), oldParent.getIds(), parent.getIds());
                }
            }
            if (oldParent != null && !oldParent.getId().equals(pid)) {
                if (assetsTypeDao.getCountByPid(oldParent.getId()) == 0) {
                    oldParent.setContents("F");
                    assetsTypeDao.update(oldParent);
                    msg.setData(pid);
                }
            }
        }
        return msg;
    }

    @Override
    @Transactional(value = "businessTransactionManager")
    public Message deleteAssetsTypeById(String id) {
        Message msg = new Message(0, "资产分类信息删除失败");
        //检测参数是否为空
        if (id == null || id.isEmpty()) {
            msg.setMessage("参数错误");
            return msg;
        }
        //检测是否有下级分类
        int o = assetsTypeDao.getCountByPid(id);
        if (o > 0) {
            msg.setMessage("该资产分类已被引用" + o + "次，请先删除所属小分类后重试");
            return msg;
        }
        AssetsType assetsType = assetsTypeDao.load(id);
        //执行删除
        if (assetsTypeDao.delete(id)) {
            if (assetsType.getParent() != null) {
                AssetsType oldParent = assetsType.getParent();
                if (assetsTypeDao.getCountByPid(oldParent.getId()) == 0) {
                    oldParent.setContents("F");
                    assetsTypeDao.update(oldParent);
                    msg.setData(oldParent.getParent() != null ? oldParent.getParent().getId() : "all");
                }
            }
            msg.setCode(1);
            msg.setMessage("资产分类信息删除成功");
        }
        return msg;
    }

    @Override
    @Transactional(value = "businessTransactionManager", readOnly = true)
    public AssetsType queryAssetsTypeById(String id) {
        return assetsTypeDao.load(id);
    }

    @Override
    @Transactional(value = "businessTransactionManager", readOnly = true)
    public AssetsType queryAssetsTypeByBookSetAndName(String bookSet, String name) {
        return (AssetsType) assetsTypeDao.queryObject("from AssetsType a where a.bookSet =?0 and a.name =?1", new Object[]{bookSet, name});
    }

    @Override
    @Transactional(value = "businessTransactionManager", readOnly = true)
    public Pager<AssetsTypePage> findAssetsTypeDto(String bookSet, String pid, String value) {
        pid = "all".equals(pid) ? null : pid;
        Pager<AssetsTypePage> assetsTypePagePager = new Pager<>();
        StringBuilder jpql;
        jpql = new StringBuilder();
        Map<String, Object> alias = new HashMap<>();
        jpql.append("from AssetsType a where a.bookSet =:bookSet ");
        alias.put("bookSet", bookSet);
        if (StringUtils.isNotEmpty(pid)) {
            jpql.append("and a.parent.id =:pid ");
            alias.put("pid", pid);
        } else if (value == null || value.isEmpty()) {
            jpql.append("and a.parent is null ");
        }
        if (StringUtils.isNotEmpty(value)) {
            jpql.append(" and ( a.name like:value or a.bh like:value ) and a.contents =:contents ");
            alias.put("value", "%" + value + "%");
            alias.put("contents","F");
        }
        Pager<AssetsType> assetsTypePager = assetsTypeDao.find(jpql.toString(), alias);
        if (assetsTypePager != null) {
            assetsTypePagePager.setTotal(assetsTypePager.getTotal());
            assetsTypePagePager.setPageNumber(assetsTypePager.getPageNumber());
            assetsTypePagePager.setPageOffset(assetsTypePager.getPageOffset());
            assetsTypePagePager.setPageSize(assetsTypePager.getPageSize());
            assetsTypePagePager.setRows(new AssetsTypePage().listAssetsTypePage(assetsTypePager.getRows()));
        }
        return assetsTypePagePager;
    }


    @Override
    @Transactional(value = "businessTransactionManager", readOnly = true)
    public List<TreeJson> listTreeJson(String bookSet) {
        List<TreeJson> cts = new ArrayList<>();
        String sql = "select m.id as id,m.bh as bh,m.name as text,m.pid as pid,m.contents as contents from assets_type  m  where  m.bookSet =:bookSet  order by m.pid asc,m.orders asc";
        Map<String, Object> alias = new HashMap<>();
        alias.put("bookSet", bookSet);
        List<Map> dts = assetsTypeDao.listToMapByAliasSql(sql, alias);
        if (dts != null && dts.size() > 0) {
            dts.forEach(map -> {
                TreeJson temp = new TreeJson();
                temp.setId((String) map.get("id"));
                temp.setText(map.get("text") + (StringUtils.isNotEmpty((CharSequence) map.get("bh")) ? "【" + map.get("bh") + "】" : ""));
                temp.setPid((String) map.get("pid"));
                temp.setArg(map.get("contents"));
                cts.add(temp);
            });
        }
        return TreeJson.getfatherNode(cts);
    }

    @Override
    @Transactional(value = "businessTransactionManager", readOnly = true)
    public List<TreeJson> getClientTreeJson(String keyword,String bookSet,String ztBz) {
        List<TreeJson> cts = new ArrayList<>();
        String sql = "select m.id as id,m.bh as bh,m.name as text,m.pid as pid,m.contents as contents from assets_type  m  where  m.bookSet =:bookSet  and m.ztBz =:ztBz  order by m.pid asc,m.orders asc";
        Map<String, Object> alias = new HashMap<>();
        alias.put("bookSet", bookSet);
        alias.put("ztBz", ztBz);
        List<Map> dts = assetsTypeDao.listToMapByAliasSql(sql, alias);
        if (dts != null && dts.size() > 0) {
            List<String> list = CmsUtils.string2Array(keyword, ";");
            dts.forEach(map -> {
                TreeJson temp = new TreeJson();
                temp.setId((String) map.get("id"));
                temp.setText((String) map.get("text"));
                temp.setPid((String) map.get("pid"));
                temp.setArg(map.get("contents"));
                if (list.contains(map.get("text"))) {
                    temp.setChecked(true);
                }
                cts.add(temp);
            });
        }
        return TreeJson.getfatherNode(cts);
    }

    @Override
    @Transactional(value = "businessTransactionManager", readOnly = true)
    public List<AssetsTypeListDto> listAssetsTypeListDto(String bookSet, String pid, String value, String ztBz) {
        pid = "all".equals(pid) ? null : pid;
        List<AssetsTypeListDto> arrayList = new ArrayList<>();
        StringBuilder jpql = new StringBuilder();
        Map<String, Object> alias = new HashMap<>();
        jpql.append("from AssetsType m where m.bookSet =:bookSet ");
        alias.put("bookSet",bookSet);
        if(StringUtils.isNotEmpty(ztBz)) {
            jpql.append(" and m.ztBz =:ztBz ");
            alias.put("ztBz", ztBz);
        }
        if (pid != null && !pid.isEmpty()) {
            jpql.append("and m.parent.id =:pid ");
            alias.put("pid", pid);
        } else if (value == null || value.isEmpty()) {
            jpql.append("and m.parent is null ");
        }

        if (value != null && !value.isEmpty()) {
            jpql.append(" and ( m.name  like:value or m.bh like:value ) ");
            alias.put("value", value + "%");
        }
        jpql.append(" order by m.bh ");
        List<AssetsType> dptInfoPager = assetsTypeDao.listByAlias(jpql.toString(), alias);
        if (dptInfoPager != null && dptInfoPager.size() > 0) {
            arrayList = new AssetsTypeListDto().listAssetsTypeListDto(dptInfoPager);
        }
        return arrayList;
    }

    @Override
    @Transactional(value = "businessTransactionManager")
    public void updateSort(String[] ids) {
        int index = 1;
        String hql = "update AssetsType m set m.orders=?0 where m.id=?1";
        for (String id : ids) {
            assetsTypeDao.executeByJpql(hql, new Object[]{index++, id});
        }
    }
}
