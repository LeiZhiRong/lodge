package com.shags.lodge.controller.lodge;

import com.shags.lodge.auth.AuthClass;
import com.shags.lodge.auth.AuthMethod;
import com.shags.lodge.dto.HeaderColumns;
import com.shags.lodge.dto.ManagePointDto;
import com.shags.lodge.dto.User;
import com.shags.lodge.util.CmsUtils;
import com.shags.lodge.util.Message;
import com.shags.lodge.util.Pager;
import com.shags.lodge.util.SystemContext;
import com.shags.lodge.primary.entity.ManagePoint;
import com.shags.lodge.service.primary.IManagePointService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

/**
 * 管理处客户端接口层
 *
 * @author 雷智荣
 */

@RestController
@RequestMapping("/managepoint/")
@Scope("prototype")
@AuthClass("login")
public class ManagePointController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private IManagePointService managePointService;

    @Autowired
    public void setManagePointService(IManagePointService managePointService) {
        this.managePointService = managePointService;
    }

    /**
     * 首页
     *
     * @param model
     * @return
     */
    @AuthMethod(role = "ROLE_MANAGEPOINT")
    @RequestMapping("index")
    public ModelAndView index(Model model) {
        List<HeaderColumns> columns = CmsUtils.getHeaderColumns("com.shags.lodge.dto.ManagePointDto");
        model.addAttribute("columns", columns);
        return new ModelAndView("point/index");
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
    @AuthMethod(role = "ROLE_MANAGEPOINT")
    @RequestMapping("list")
    public Pager<ManagePointDto> findManagePoint(String order, String sort, int page, int rows, String value, HttpSession session) {
        SystemContext.setPageSize(rows);
        SystemContext.setPageNumber(page);
        SystemContext.setOrder(order);
        SystemContext.setSort(sort);
        User user = (User) session.getAttribute("user");
        return managePointService.findManagePointDto(user.getBookSet(), value);
    }

    /**
     * 添加
     *
     * @param id    关键字
     * @param model
     * @return
     */
    @AuthMethod(role = "ROLE_MANAGEPOINT")
    @GetMapping("dialog")
    public ModelAndView dialog(String id, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        ManagePoint managePoint;
        if (StringUtils.isNotEmpty(id)) {
            managePoint = managePointService.queryManagePointByID(id);
        } else {
            managePoint = new ManagePoint();
            managePoint.setBookSet(user.getBookSet());
            managePoint.setZtbz("T");
        }
        model.addAttribute("managePoint", managePoint);
        return new ModelAndView("point/dialog");
    }

    /**
     * 保存（更新）
     *
     * @param dto
     * @param br
     * @return
     */
    @AuthMethod(role = "ROLE_MANAGEPOINT")
    @PostMapping("save")
    public Message save(@Validated ManagePointDto dto, BindingResult br) {
        if (br.hasErrors()) {
            return new Message(0, Objects.requireNonNull(br.getFieldError()).getDefaultMessage());
        }
        try {
            ManagePoint info = new ManagePointDto().getManagePoint(dto);
            if (StringUtils.isEmpty(dto.getId())) {
                //添加
                info.setrVTime(CmsUtils.getTimeMillis());
                return managePointService.addManagePoint(info);
            } else {
                return managePointService.updateManagePoint(info);
            }
        } catch (Exception e) {
            return new Message(0, e.getMessage());
        }

    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @AuthMethod(role = "ROLE_MANAGEPOINT")
    @PostMapping(value = "delete")
    public Message delete(String id) {
        if (StringUtils.isNotEmpty(id)) {
            return managePointService.deleteManagePointByID(id);
        } else {
            return new Message(0, "id参数不能为空");
        }

    }

    @AuthMethod(role = "ROLE_MANAGEPOINT")
    @GetMapping(value = "test")
    public void test() {
        System.out.println(CmsUtils.isNumeric("中"));


    }

}
