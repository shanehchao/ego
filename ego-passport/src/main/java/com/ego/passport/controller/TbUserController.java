package com.ego.passport.controller;

import com.ego.commons.pojo.EgoResult;
import com.ego.passport.service.TbUserService;
import com.ego.pojo.TbUser;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: aelchao aelchao207@gmail.com
 * @Date: 2018/9/3 下午6:45
 */
@Controller
public class TbUserController {

    @Resource
    private TbUserService tbUserService;

    @RequestMapping("/user/showLogin")
    public String showLogin(Model model, @RequestHeader("Referer") String referer) {
        if (referer == null) {
            model.addAttribute("redirect", "");
        } else {
            model.addAttribute("redirect", referer);
        }
        return "/WEB-INF/jsp/login.jsp";
    }

    // 登录
    @RequestMapping("/user/login")
    @ResponseBody
    public EgoResult login(TbUser tbUser, HttpServletRequest request, HttpServletResponse response) {
        return tbUserService.login(tbUser, request, response);
    }

    // 查看是否已经登录，如果已经登录查询登录信息
    @RequestMapping("/user/token/{token}")
    @ResponseBody
    public Object checkLogin(@PathVariable String token, String callback) {
        // 通过token查询用户信息
        TbUser user = tbUserService.selectByToken(token);
        if (user != null) {
            EgoResult er = new EgoResult();
            er.setStatus(200);
            er.setMsg("OK");
            er.setData(user);
            if (StringUtils.isEmpty(callback)) {
                return er;
            } else {
                MappingJacksonValue mjv = new MappingJacksonValue(er);
                mjv.setJsonpFunction(callback);
                return mjv;
            }
        }
        return null;
    }

    // 退出
    @RequestMapping("/user/logout/{token}")
    @ResponseBody
    public Object logout(@PathVariable String token, String callback, HttpServletRequest request, HttpServletResponse response) {
        EgoResult er = tbUserService.logout(token, request, response);

        if (StringUtils.isEmpty(callback)) {
            return er;
        } else {
            MappingJacksonValue mjv = new MappingJacksonValue(er);
            mjv.setJsonpFunction(callback);
            return mjv;
        }
    }

}
