package com.vesystem.controller;

import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @auth wcy on 2019/10/10.
 */
@Controller
public class LoginController {

    @RequestMapping("/index")
    public String index( HttpServletRequest request){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Assertion assertion = (Assertion) request.getSession().getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION);

        System.out.println("ValidFromDate"+simpleDateFormat.format(assertion.getValidFromDate()));
      //  System.out.println("alidUntilDate"+simpleDateFormat.format(assertion.getValidUntilDate()));
        System.out.println("AuthenticationDate"+simpleDateFormat.format(assertion.getAuthenticationDate()));

        System.out.println("---------------------------------------------------------------------------");
        AttributePrincipal principal = assertion.getPrincipal();
        Map<String, Object> map = principal.getAttributes();
        map.forEach((k,v)->{
            System.out.println(k+":"+v);
        });

        return "index";
    }

    /**
     * 跳转到指定页面,如果配置了service参数，该地址不起作用
     * @param session
     * @return
     */
    @RequestMapping("/logout")
    public String loginOut(HttpSession session) {
        session.invalidate();
        //退出登录后，跳转到退成成功的页面，不走默认页面
        return "redirect:http://localhost:8080/cas/logout";
    }
    }
