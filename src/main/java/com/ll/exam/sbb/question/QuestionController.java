package com.ll.exam.sbb.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class QuestionController
{
    @RequestMapping("/question/list")
    public String list() {
        return "question_list";
    }
}
