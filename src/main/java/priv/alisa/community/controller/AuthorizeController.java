package priv.alisa.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import priv.alisa.community.entity.Accesstoken;
import priv.alisa.community.entity.GithubUser;
import priv.alisa.community.provider.GithubProvider;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state) {
        Accesstoken accesstoken = new Accesstoken();
        accesstoken.setClient_id(clientId);
        accesstoken.setClient_secret(clientSecret);
        accesstoken.setCode(code);
        accesstoken.setRedirect_uri(redirectUri);
        accesstoken.setState(state);
        String accessToken = githubProvider.getAccesstoken(accesstoken);
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user.getName());
        return "index";
    }
}
