package com.shgs.lodge.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.shgs.lodge.auth.AuthClass;
import com.shgs.lodge.auth.AuthMethod;
import com.shgs.lodge.dto.User;
import com.shgs.lodge.primary.entity.TableHeader;
import com.shgs.lodge.service.TableHeaderService;
import com.shgs.lodge.util.CmsUtils;
import com.shgs.lodge.util.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/table/")
@Scope("prototype")
@AuthClass("login")

/**
 * 列表
 */
public class TableInfoController {

    @Autowired
    private TableHeaderService tableHeaderService;

    /**
     * 列设置页跳转
     * @param action
     * @param model
     * @return
     * @throws JsonProcessingException
     */
    @AuthMethod
    @GetMapping("getColumns")
    public ModelAndView getColumns(String action, Model model) throws JsonProcessingException {
        model.addAttribute("action", action);
        ModelAndView view = new ModelAndView("columns/getColumns");
        return view;
    }

    /**
     * 获取列设置信息
     *
     * @param action
     * @return
     */
    @AuthMethod
    @RequestMapping("getColumnsInfo")
    public List<TableHeader> getColumnsInfo(String action, HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<TableHeader> tableHeaders=tableHeaderService.listTableHeader(user.getId(), action);
        return tableHeaders;
    }

    @AuthMethod
    @RequestMapping("resetTableHeader")
    public Message resetTableHeader(String action, HttpSession session){
        User user = (User) session.getAttribute("user");
        return  tableHeaderService.deleteTableHeader(user.getId(),action);
    }

    /**
     * 更新列排序
     *
     * @param ids
     * @return
     */
    @AuthMethod
    @RequestMapping("upordersHeader")
    public Message upordersHeader(String ids) {
        try {
            tableHeaderService.updateSort(ids);
            return new Message(1, "更新成功");
        } catch (Exception e) {
            return new Message(0, e.getMessage());
        }
    }

    /**
     * 更新列排序
     *
     * @param rows
     * @param session
     * @return
     */
    @AuthMethod
    @PostMapping(value = "saveTableHeader")
    public Message saveTableHeader(String rows, HttpSession session) {
        if (rows != null && !rows.isEmpty()) {
            rows = CmsUtils.decryptBASE64(rows);
            JSONArray jsonArray = JSONObject.parseArray(rows);
            List<TableHeader> dtos = JSONArray.parseArray(jsonArray.toString(), TableHeader.class);
            if (dtos.size() > 0) {
                return tableHeaderService.batchUpdateTableHeader(dtos);
            }
        }
        return new Message(0, "更新失败");
    }
}
