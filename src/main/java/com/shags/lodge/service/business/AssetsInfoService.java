package com.shags.lodge.service.business;

import com.shags.lodge.business.dao.IAssetsInfoDao;
import com.shags.lodge.business.entity.AssetsInfo;
import com.shags.lodge.business.entity.AssetsType;
import com.shags.lodge.dto.business.AssetsInfoDto;
import com.shags.lodge.util.CmsUtils;
import com.shags.lodge.util.Message;
import com.shags.lodge.util.Pager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Message addAssetsInfo(AssetsInfo assetsInfo, String assetsTypeId) {
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
        if (StringUtils.isNotEmpty(assetsTypeId)) {
            AssetsType assetsType = assetsTypeService.queryAssetsTypeById(assetsTypeId);
            if (assetsType != null)
                assetsInfo.setAssetsType(assetsType);
        }
        if (assetsInfoDao.add(assetsInfo) != null) {
            msg.setCode(1);
            msg.setMessage("添加成功");
        }
        return msg;
    }

    @Override
    public void batchSave(List<AssetsInfo> list) {
        assetsInfoDao.batchSave(list);
    }

    @Override
    public Message updateAssetsInfo(AssetsInfo assetsInfo, String assetsTypeId) {
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
        if (StringUtils.isNotEmpty(assetsTypeId)) {
            AssetsType assetsType = assetsTypeService.queryAssetsTypeById(assetsTypeId);
            if (assetsType != null)
                assetsInfo.setAssetsType(assetsType);
        }
        if (assetsInfoDao.update(assetsInfo)) {
            msg.setCode(1);
            msg.setMessage("修改成功");
        }
        return msg;
    }

    @Override
    public Message deleteAssetsInfo(String id) {
        Message msg=new Message(0,"删除失败");
        //后期调整需检测房屋信息，若在使用中，禁止删除.
        if(assetsInfoDao.delete(id)){
            msg.setCode(1);
            msg.setMessage("删除成功");
        }
        return msg;
    }

    @Override
    public AssetsInfo queryAssetsInfo(String id) {
        return assetsInfoDao.load(id);
    }

    @Override
    public Pager<AssetsInfoDto> getAssetsInfoDto(AssetsInfoDto dto,boolean isAdmin) {

        return null;
    }


}
