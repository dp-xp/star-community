package priv.alisa.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import priv.alisa.community.mapper.QuestionMapper;
import priv.alisa.community.mapper.UserMapper;
import priv.alisa.community.model.Question;
import priv.alisa.community.model.User;

import javax.servlet.http.Cookie;

@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            @CookieValue("token") String token,
            Model model
            ){
        System.out.println("doPublish");
        User user = userMapper.findByToken(token);
        if (user!=null){
            System.out.println("user is not null");
            Question question = new Question();
            question.setTitle(title);
            question.setDescription(description);
            question.setTitle(tag);
            question.setCreator(user.getId());
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.create(question);
            model.addAttribute("message","提交成功");
            return "publish";
        }else {
            model.addAttribute("message","用户未登录");
            return "redirect:/";
        }
    }
}
