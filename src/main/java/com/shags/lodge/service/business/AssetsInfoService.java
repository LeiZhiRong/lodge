package com.shags.lodge.service.business;

import com.shags.lodge.business.dao.IAssetsInfoDao;
import com.shags.lodge.business.entity.AssetsInfo;
import com.shags.lodge.dto.User;
import com.shags.lodge.dto.business.AssetsInfoDto;
import com.shags.lodge.util.CmsUtils;
import com.shags.lodge.util.Message;
import com.shags.lodge.util.Pager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yglei
 * @classname AssetsInfoService
 * @description 楼栋信息业务接口实现类
 * @date 2021-07-26 17:35
 */

@Service("assetsInfoService")
public class AssetsInfoService implements IAssetsInfoService {

    private IAssetsInfoDao assetsInfoDao;

    private IAssetsTypeService assetsTypeService;

    @Autowired
    public void setAssetsInfoDao(IAssetsInfoDao assetsInfoDao) {
        this.assetsInfoDao = assetsInfoDao;
    }

    @Autowired
    public void setAssetsTypeService(IAssetsTypeService assetsTypeService) {
        this.assetsTypeService = assetsTypeService;
    }

    @Override
    @Transactional(value = "businessTransactionManager")
    public Message addAssetsInfo(AssetsInfo assetsInfo) {
        Message msg = new Message(0, "添加失败");
        if (StringUtils.isNotEmpty(assetsInfo.getCardNumber())) {
            AssetsInfo temp = assetsInfoDao.queryAssetsInfoByCardNumber(assetsInfo.getCardNumber(), null);
            if (temp != null) {
                msg.setMessage("卡片编号【" + assetsInfo.getCardNumber() + "】违反唯一约定，卡片编号已在使用中。");
                return msg;
            }
        } else {
            int number = assetsInfoDao.getMaxCardNumber() + 1;
            assetsInfo.setCustomCardNumber(number);
            assetsInfo.setCardNumber("l" + CmsUtils.int2Formatter(number, 9));
        }
        if (assetsInfoDao.add(assetsInfo) != null) {
            msg.setCode(1);
            msg.setMessage("添加成功");
        }
        return msg;
    }

    @Override
    @Transactional(value = "businessTransactionManager")
    public void batchSave(List<AssetsInfo> list) {
        assetsInfoDao.batchSave(list);
    }

    @Override
    @Transactional(value = "businessTransactionManager")
    public Message updateAssetsInfo(AssetsInfo assetsInfo) {
        Message msg = new Message(0, "修改失败");
        if (StringUtils.isNotEmpty(assetsInfo.getCardNumber())) {
            AssetsInfo temp = assetsInfoDao.queryAssetsInfoByCardNumber(assetsInfo.getCardNumber(), assetsInfo.getId());
            if (temp != null) {
                msg.setMessage("卡片编号【" + assetsInfo.getCardNumber() + "】违反唯一约定，卡片编号已在使用中。");
                return msg;
            }
        } else {
            int number = assetsInfoDao.getMaxCardNumber() + 1;
            assetsInfo.setCustomCardNumber(number);
            assetsInfo.setCardNumber("l" + CmsUtils.int2Formatter(number, 9));
        }
        if (assetsInfoDao.update(assetsInfo)) {
            msg.setCode(1);
            msg.setMessage("修改成功");
        }
        return msg;
    }

    @Override
    @Transactional(value = "businessTransactionManager")
    public Message deleteAssetsInfo(String id) {
        Message msg = new Message(0, "删除失败");
        //后期调整需检测房屋信息，若在使用中，禁止删除.
        if (assetsInfoDao.delete(id)) {
            msg.setCode(1);
            msg.setMessage("删除成功");
        }
        return msg;
    }

    @Override
    @Transactional(value = "businessTransactionManager", readOnly = true)
    public AssetsInfo queryAssetsInfo(String id) {
        return assetsInfoDao.load(id);
    }

    @Override
    @Transactional(value = "businessTransactionManager", readOnly = true)
    public Pager<AssetsInfoDto> listAssetsInfoDto(String codeType, String codeValue, String corp_Name, String dept_ID, String managePoint_Name, String sitDown_Name, String assetsType_Name, User user) {
        Pager<AssetsInfoDto> list = new Pager<AssetsInfoDto>();
        StringBuilder jpql = new StringBuilder();
        Map<String, Object> alias = new HashMap<>();
        jpql.append(" from AssetsInfo a where a.bookSet =:bookSet and a.deptBh in(:deptBh) and a.managePointId in(:managePointId) ");
        alias.put("bookSet", user.getBookSet());
        alias.put("deptBh", CmsUtils.string2Array(user.getManageDept(), ";"));
        alias.put("managePointId", CmsUtils.string2Array(user.getBalanceDept(), ","));
        //公司查询
        if (StringUtils.isNotEmpty(corp_Name)) {
            jpql.append(" and a.companyName in(:corp_Name) ");
            alias.put("corp_Name", CmsUtils.string2Array(corp_Name, ";"));
        }
        //部门查询
        if (StringUtils.isNotEmpty(dept_ID)) {
            jpql.append(" and a.deptName in(:dept_ID) ");
            alias.put("dept_ID", CmsUtils.string2Array(dept_ID, ";"));
        }
        //管理处
        if (StringUtils.isNotEmpty(managePoint_Name)) {
            jpql.append(" and a.managePointName in(:managePoint_Name) ");
            alias.put("managePoint_Name", CmsUtils.string2Array(managePoint_Name, ";"));
        }
        //坐落位置
        if (StringUtils.isNotEmpty(sitDown_Name)) {
            jpql.append(" and a.sitDownName in(:sitDown_Name) ");
            alias.put("sitDown_Name", CmsUtils.string2Array(sitDown_Name, ";"));
        }
        //资产类型
        if (StringUtils.isNotEmpty(assetsType_Name)) {
            jpql.append(" and a.assetsTypeName in(:assetsType_Name) ");
            alias.put("assetsType_Name", CmsUtils.string2Array(assetsType_Name, ";"));
        }
        if ("all".equals(codeType)) {
            jpql.append(" and ( a.cardNumber like:cardNumber or  a.assetName like:assetName or a.nowName like:nowName or a.housingNumber like:housingNumber or a.communityNumber like:communityNumber or a.propertyCertificateNo like:propertyCertificateNo or a.landAssetNo like:landAssetNo or a.landCertificateNo like:landCertificateNo )  ");
            alias.put("cardNumber", "%" + codeValue + "%");
            alias.put("assetName", "%" + codeValue + "%");
            alias.put("nowName", "%" + codeValue + "%");
            alias.put("housingNumber", "%" + codeValue + "%");
            alias.put("communityNumber", "%" + codeValue + "%");
            alias.put("propertyCertificateNo", "%" + codeValue + "%");
            alias.put("landAssetNo", "%" + codeValue + "%");
            alias.put("landCertificateNo", "%" + codeValue + "%");

        } else if(StringUtils.isNotEmpty(codeType)) {
            String filed = codeType.replace("_", "");
            jpql.append(" and a." + filed + " like:value ");
            alias.put("value", "%" + codeValue + "%");
        }

        Pager<AssetsInfo> pager = assetsInfoDao.find(jpql.toString(), alias);
        if (pager != null) {
            list.setPageNumber(pager.getPageNumber());
            list.setPageOffset(pager.getPageOffset());
            list.setPageSize(pager.getPageSize());
            list.setTotal(pager.getTotal());
            list.setRows(new AssetsInfoDto().listAssetsInfoDto(pager.getRows()));
        }

        return list;
    }


}
