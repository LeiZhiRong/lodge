package com.shags.lodge.service.business;

import com.shags.lodge.business.entity.SitDown;
import com.shags.lodge.dto.TreeJson;
import com.shags.lodge.dto.business.SitDownInfoListDto;
import com.shags.lodge.util.Message;
import com.shags.lodge.util.Pager;

import java.util.List;

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

    /**
     * @description: 客户端获取座落位置树
     * @param: [keyword, bookSet]
     * @author: ygLei
     * @return: {@link List< TreeJson>}
     * @date: 2021-11-26 16:57
     */
    List<TreeJson> getClientSitDownToTreeJson(String keyword, String bookSet);

    /**
     * @description: 获取坐落位置列表
     * @param: [bookSet, keyword, ztBz]
     * @author: ygLei
     * @return: {@link List< SitDownInfoListDto>}
     * @date: 2022-01-10 15:08
     */
    List<SitDownInfoListDto> listSitDownInfoDto(String bookSet, String keyword, String ztBz);


}
