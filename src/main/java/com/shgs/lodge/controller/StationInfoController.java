package com.shgs.lodge.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shgs.lodge.auth.AuthClass;
import com.shgs.lodge.auth.AuthMethod;
import com.shgs.lodge.dto.HeaderColumns;
import com.shgs.lodge.primary.entity.StationInfo;
import com.shgs.lodge.service.IStationInfoService;
import com.shgs.lodge.service.IUserInfoService;
import com.shgs.lodge.util.CmsUtils;
import com.shgs.lodge.util.Message;
import com.shgs.lodge.util.Pager;
import com.shgs.lodge.util.SystemContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;

/**
 * 职务信息示图接口层
 *
 * @author 雷智荣
 */
@RestController
@RequestMapping("/station/")
@Scope("prototype")
@AuthClass("login")
public class StationInfoController {


    private IStationInfoService stationInfoService;


    private IUserInfoService userInfoService;

    @Autowired
    public void setStationInfoService(IStationInfoService stationInfoService) {
        this.stationInfoService = stationInfoService;
    }


    @Autowired
    public void setUserInfoService(IUserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @AuthMethod(role = "ROLE_STATION")
    @RequestMapping("index")
    public ModelAndView index(Model model) throws JsonProcessingException {
        List<HeaderColumns> columns = CmsUtils.getHeaderColumns("com.shgs.lodge.primary.entity.StationInfo");
        model.addAttribute("columns", columns);
        return new ModelAndView("station/index");
    }


    /**
     * 获取分页数据
     *
     * @param order
     * @param sort
     * @param page
     * @param rows
     * @param value
     * @return
     */
    @AuthMethod(role = "ROLE_STATION")
    @RequestMapping("list")
    public Pager<StationInfo> listStationInfo(String order, String sort, int page, int rows, String value) {
        SystemContext.setPageSize(rows);
        SystemContext.setPageNumber(page);
        SystemContext.setOrder(order);
        SystemContext.setSort(sort);
        return stationInfoService.findStationInfoDto(value, null);
    }

    /**
     * 添加页面dialog
     *
     * @param id    关键字
     * @param model
     * @return
     * @throws JsonProcessingException
     */
    @AuthMethod(role = "ROLE_STATION")
    @GetMapping("dialog")
    public ModelAndView dialog(String id, Model model) throws JsonProcessingException {
        StationInfo dto = new StationInfo();
        if (id != null && !id.isEmpty()) {
            StationInfo stationInfo = stationInfoService.queryStationInfoByID(id);
            if (stationInfo != null)
                dto = stationInfo;
        } else {
            dto.setZtbz("T");
        }
        model.addAttribute("stationInfo", dto);
        return new ModelAndView("station/dialog");
    }


    /**
     * 保存（更新）
     *
     * @param info
     * @param br
     * @return
     */
    @AuthMethod(role = "ROLE_STATION")
    @PostMapping("save")
    public Message save(@Validated StationInfo info, BindingResult br) {
        if (br.hasErrors()) {
            return new Message(0, Objects.requireNonNull(br.getFieldError()).getDefaultMessage());
        }
        try {
            String id = info.getId();
            if (id == null || id.isEmpty()) {
                //添加
                info.setId(null);
                info.setrVTime(CmsUtils.getTimeMillis());
                return stationInfoService.addStationInfo(info);
            } else {
                //更新
                StationInfo mast = stationInfoService.queryStationInfoByID(id);
                String station = mast.getStationName();
                mast.setStationName(info.getStationName());
                mast.setZtbz(info.getZtbz());
                Message msg = stationInfoService.updateStationInfo(mast);
                if (msg.getCode() == 1) {
                    if (!info.getStationName().equals(station)) {
                        userInfoService.updateUserStation(station, info.getStationName());
                    }
                }
                return msg;
            }
        } catch (Exception e) {
            return new Message(0, e.getMessage());
        }

    }

    /**
     * 按ID删除
     *
     * @param id
     * @return
     */
    @AuthMethod(role = "ROLE_STATION")
    @PostMapping(value = "delete")
    public Message delete(String id) {
        return stationInfoService.deleteStationInfo(id);
    }

}
