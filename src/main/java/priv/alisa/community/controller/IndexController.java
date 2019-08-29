package priv.alisa.community.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import priv.alisa.community.dto.QuestionDTO;
import priv.alisa.community.mapper.QuestionMapper;
import priv.alisa.community.mapper.UserMapper;
import priv.alisa.community.model.Question;
import priv.alisa.community.model.User;
import priv.alisa.community.service.QuestionService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model){
        Cookie[] cookies =request.getCookies();
        if (cookies!=null){
            for (Cookie cookie:cookies){
                if ("token".equals(cookie.getName())){
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }
        List<QuestionDTO> questionList = questionService.queryAllQuestion();
        model.addAttribute("questionList",questionList);
        return "index";
    }
}
