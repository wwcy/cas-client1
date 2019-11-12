package com.vesystem.controller;

/*import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.validation.Assertion;*/
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import com.vesystem.entity.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @auth wcy on 2019/10/10.
 */
@Controller
public class LoginController {

    @Autowired
    private RestTemplate restTemplate;

    private String authUrl =
            "http://192.168.50.156:8080/cas/oauth2.0/authorize?response_type=code&client_id=%s&redirect_uri=%s";

    private String tokenUrl =
      "http://192.168.50.156:8080/cas/oauth2.0/accessToken";

    private String profileUrl =
            "http://192.168.50.156:8080/cas/oauth2.0/profile?access_token=%s";

    private String clientId = "123";

    private String clientSecret = "123456";

    private String redirectUri = "http://192.168.50.156:9090/index";

    private boolean isLogin = false;

    private Map<String,String> parameter = new HashMap<>();


    @GetMapping("/login")
    public String login(HttpServletRequest request){
        if(isLogin){
            return "forward:/index";
        }
        return "login";
    }

    @GetMapping("/login1")
    public String login1(HttpServletRequest request){
        String url = String.format(authUrl,clientId,redirectUri);
        return "redirect:"+url;
    }

    @RequestMapping("/index")
    public ModelAndView index(String code) throws Exception{
        if(code == null){
            throw new NullPointerException();
        }
        if(parameter.size() != 0){

        }
        Map resultMap = new HashMap();

        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("grant_type","authorization_code");
        map.add("client_id",clientId);
        map.add("client_secret",clientSecret);
        map.add("redirect_uri",redirectUri);
        map.add("code",code);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity< MultiValueMap<String,String>> entity = new HttpEntity<>(map,headers);
        ResponseEntity<TokenProfile> responseEntity = restTemplate.postForEntity(tokenUrl,entity,TokenProfile.class);
        if(responseEntity.getStatusCodeValue() != HttpStatus.OK.value()){
            throw new RuntimeException();
        }
        TokenProfile t = responseEntity.getBody();

        resultMap.put("tokenProfile",t);

        String url = String.format(profileUrl,t.getAccess_token());
        HttpEntity< Map<String,String>> entity1 = new HttpEntity<>(headers);
        ResponseEntity<UserProfile>  responseEntity1 = restTemplate.exchange(url,HttpMethod.GET,entity1,UserProfile.class);
        UserProfile userProfile = responseEntity1.getBody();
        resultMap.put("userProfile",userProfile);
        isLogin = true;
        return new ModelAndView("index",resultMap);
    }

    @PostMapping("/post-login")
    public String loginPost(String username,String password){
        System.out.println(username+":"+password);
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
        //退出登录后，跳转到退成成功的页面
        return "redirect:http://192.168.50.156:8080/cas/logout?service=http://192.168.50.156:9090/login";
    }
    }
