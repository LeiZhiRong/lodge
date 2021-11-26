package com.shags.lodge.business.dao;

import com.shags.lodge.business.dao.basic.BusinessBaseDAO;
import com.shags.lodge.business.entity.AssetsInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;


/**
 * @author yglei
 * @classname AssetsInfoDao
 * @description 楼栋信息持久接口实现类
 * @date 2021-07-26 17:30
 */
@Repository("assetsInfoDao")
public class AssetsInfoDao extends BusinessBaseDAO<AssetsInfo,String> implements IAssetsInfoDao{


    @Override
    public AssetsInfo queryAssetsInfoByCardNumber(String cardNumber, String notInId) {
        StringBuilder jpql = new StringBuilder();
        Map<String, Object> alias = new HashMap<>();
        jpql.append(" from AssetsInfo a where a.cardNumber =:cardNumber ");
        alias.put("cardNumber",cardNumber);
        if(StringUtils.isNotEmpty(notInId)){
            jpql.append(" and a.id !=:notInId ");
            alias.put("notInId",notInId);
        }
        return (AssetsInfo) super.queryObjectByAlias(jpql.toString(),alias);
    }

    @Override
    public int getMaxCardNumber() {
        Object obj=super.queryObject("select max(a.customCardNumber) from AssetsInfo a where a.cardNumber like?0", "l%");
        if (obj == null)
            return 1;
        return (int) obj;
    }

}
