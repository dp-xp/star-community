package priv.alisa.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import priv.alisa.community.dto.Accesstoken;
import priv.alisa.community.dto.GithubUser;
import priv.alisa.community.mapper.UserMapper;
import priv.alisa.community.model.User;
import priv.alisa.community.provider.GithubProvider;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    @Autowired
    private UserMapper userMapper;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request) {
        Accesstoken accesstoken = new Accesstoken();
        accesstoken.setClient_id(clientId);
        accesstoken.setClient_secret(clientSecret);
        accesstoken.setCode(code);
        accesstoken.setRedirect_uri(redirectUri);
        accesstoken.setState(state);
        String accessToken = githubProvider.getAccesstoken(accesstoken);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if (githubUser!=null){
            //登录成功，写cookie和session
            User user = new User();
            user.setToken(UUID.randomUUID().toString());
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
            request.getSession().setAttribute("user",githubUser);
            return "redirect:/";
        }else {
            //登录失败重新登录
            return "redirect:/";
        }
    }
}
