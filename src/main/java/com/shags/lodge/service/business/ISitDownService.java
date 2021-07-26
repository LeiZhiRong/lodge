package com.shags.lodge.service.business;

import com.shags.lodge.business.entity.SitDown;
import com.shags.lodge.util.Message;
import com.shags.lodge.util.Pager;

/**
 * @author yglei
 * @classname ISitDownService
 * @description 资产管理-坐落位置Service接口类
 * @date 2021-07-23 9:59
 */
public interface ISitDownService {

    /**  
     * @description: 新增坐落位置
     * @param: [SitDown]
     * @author: ygLei
     * @return: {@link Message} 
     * @date: 2021-07-21 16:28
     */
    Message addSitDown(SitDown sitDown);

    /**
     * @description: 编辑坐落位置
     * @param: [SitDown]
     * @author: ygLei
     * @return: {@link Message}
     * @date: 2021-07-21 16:54
     */
    Message updateSitDown(SitDown sitDown);

    /**
     * @description: 删除坐落位置
     * @param: [id]
     * @author: ygLei
     * @return: {@link Message}
     * @date: 2021-07-21 16:53
     */
    Message deleteSitDown(String id);

   /**
    * @description: 获取分页数据
    * @param: [bookSet, point_id, keyWord]
    * @author: ygLei
    * @return: {@link Pager<SitDown>}
    * @date: 2021-07-23 10:20
    */
    Pager<SitDown> findSitDown(String bookSet, String point_id, String keyWord);

    /**
     * @description: 按ID查询对象
     * @param: [id]
     * @author: ygLei
     * @return: {@link SitDown}
     * @date: 2021-07-21 17:08
     */
    SitDown querySitDown(String id);


}
