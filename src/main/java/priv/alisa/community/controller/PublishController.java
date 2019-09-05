package priv.alisa.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import priv.alisa.community.dto.QuestionDTO;
import priv.alisa.community.mapper.QuestionMapper;
import priv.alisa.community.model.Question;
import priv.alisa.community.model.User;
import priv.alisa.community.service.QuestionService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionMapper questionMapper;

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Integer id, Model model){
        QuestionDTO questionDTO = questionService.queryById(id);
        model.addAttribute("title",questionDTO.getTitle());
        model.addAttribute("description",questionDTO.getDescription());
        model.addAttribute("tag",questionDTO.getTag());
        model.addAttribute("questionId",questionDTO.getId());
        return "publish";
    }

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
            @RequestParam("questionId") Integer questionId,
            Model model,
            HttpServletRequest request
            ){
        System.out.println("doPublish");
        User user = (User) request.getSession().getAttribute("user");
        if (user!=null){
            model.addAttribute("title",title);
            model.addAttribute("description",description);
            model.addAttribute("tag",tag);
            System.out.println("user is not null");
            Question question = new Question();
            question.setTitle(title);
            question.setDescription(description);
            question.setTag(tag);
            question.setCreator(user.getId());
            question.setId(questionId);
            questionService.createOrUpdate(question);
            model.addAttribute("message","提交成功");
            return "publish";
        }else {
            model.addAttribute("message","用户未登录");
            return "redirect:/";
        }
    }
}
