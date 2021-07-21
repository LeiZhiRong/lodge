package com.shags.lodge.service.business;

import com.shags.lodge.business.entity.LocationInfo;
import com.shags.lodge.util.Message;
import com.shags.lodge.util.Pager;

public interface ILocationInfoService {

    /**  
     * @description: 新增坐落位置
     * @param: [locationInfo]
     * @author: ygLei
     * @return: {@link Message} 
     * @date: 2021-07-21 16:28
     */
    Message addLocationInfo(LocationInfo locationInfo);

    /**
     * @description: 编辑坐落位置
     * @param: [locationInfo]
     * @author: ygLei
     * @return: {@link Message}
     * @date: 2021-07-21 16:54
     */
    Message updateLocationInfo(LocationInfo locationInfo);

    /**
     * @description: 删除坐落位置
     * @param: [id]
     * @author: ygLei
     * @return: {@link Message}
     * @date: 2021-07-21 16:53
     */
    Message deleteLocationInfo(String id);

    /**
     * @description: 获取分页数据
     * @param: [keyWord]
     * @author: ygLei
     * @return: {@link Pager<LocationInfo>}
     * @date: 2021-07-21 16:52
     */
    Pager<LocationInfo> findLocationInfo(String keyWord);

    /**
     * @description: 按ID查询对象
     * @param: [id]
     * @author: ygLei
     * @return: {@link LocationInfo}
     * @date: 2021-07-21 17:08
     */
    LocationInfo queryLocationInfo(String id);


}
