package com.semion.web.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by heshuanxu on 2016/10/27.
 */
@Controller
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    //@Resource(name = "jedisUtil")
    private JedisUtil jedisUtil;

    @RequestMapping("/index.action")
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("index");
        return view;
    }

    @RequestMapping("/login.action")
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ModelAndView view = new ModelAndView("index");

        String token = null;
        // 获取cookie中的 token 获取redis session信息
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equalsIgnoreCase(UserUtil.TOKEN_NAME_KEY)) {
                    token = cookies[i].getValue();
                }
                continue;
            }
        }
        if (!StringUtils.isEmpty(token)) {
            String id = jedisUtil.hget(token, "id");
            String nick = jedisUtil.hget(token, "nick");
            System.out.println(id + "====" + nick);
            // 已经登陆 session 存在
            if (!StringUtils.isEmpty(id) && !StringUtils.isEmpty(nick)) {
                request.getSession().setAttribute("userid", id);
                request.getSession().setAttribute("nick", nick);
                return view;
            }
        }
        String id = request.getParameter("id");
        String nick = request.getParameter("nick");

        if (!StringUtils.isEmpty(nick) && nick.equalsIgnoreCase("admin")) {//登陆成功
            String token2 = UUID.randomUUID().toString();
            Cookie cookie = new Cookie(UserUtil.TOKEN_NAME_KEY, token2);
            cookie.setDomain("localhost");
            cookie.setMaxAge(3600);
            response.addCookie(cookie);
            // 用户信息 推到redis缓存
            // key :token  value:loginUser;
            jedisUtil.hset(token2, "id", id);
            jedisUtil.hset(token2, "nick", nick);
            request.getSession().setAttribute("userid", id);
            request.getSession().setAttribute("nick", nick);

        } else {// 登陆失败
            response.sendRedirect("index.html");
        }
        return view;
    }

    public JedisUtil getJedisUtil() {
        return jedisUtil;
    }

    public void setJedisUtil(JedisUtil jedisUtil) {
        this.jedisUtil = jedisUtil;
    }
}
