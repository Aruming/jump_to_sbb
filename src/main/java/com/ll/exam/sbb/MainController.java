package com.ll.exam.sbb;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class MainController {
    private int increaseNo = -1;

    @RequestMapping("/sbb")
    // 아래 함수의 리턴값을 그대로 브라우저에 표시
    // 아래 함수의 리턴값을 문자열화 해서 브라우저 응답의 바디에 담는다.
    @ResponseBody
    public String index(){
        //서버에서 실행
        System.out.println("index");
        //브라우저에 보여짐
        return "안녕하세요ㅎㅎㅎㅎ";
    }

    @GetMapping
    @ResponseBody
    public String showPage1(){
        return """
                <form method="POST" action="/page2">
                    <input type="number" name="age" placeholder="나이" />
                    <input type="submit" value="page2로 POST 방식으로 이동" />
                </form>
                """;
    }

    @PostMapping("/page2")
    @ResponseBody
    public String showPage2Post(@RequestParam(defaultValue = "0") int age) {
        return """
                <h1>입력된 나이 : %d</h1>
                <h1>안녕하세요, POST 방식으로 오셨군요.</h1>
                """.formatted(age);
    }

    @GetMapping("/page2")
    @ResponseBody
    public String showPage2Get(@RequestParam(defaultValue = "0") int age) {
        return """
                <h1>입력된 나이 : %d</h1>
                <h1>안녕하세요, GET 방식으로 오셨군요.</h1>
                """.formatted(age);
    }

    @GetMapping("plus")
    @ResponseBody
    public String plus(@RequestParam(defaultValue = "0")int a, @RequestParam(defaultValue = "0")int b){
        return """
                <h1> %d + %d = %d </h1>                
                """.formatted(a, b, a+b);
    }

    @GetMapping("minus")
    @ResponseBody
    public String minus(@RequestParam(defaultValue = "0")int a, @RequestParam(defaultValue = "0")int b){
        return """
                <h1> %d - %d = %d </h1>                
                """.formatted(a, b, a-b);
    }

    @GetMapping("increase")
    @ResponseBody
    public int increase(){
        increaseNo++;
        return increaseNo;
    }

    @GetMapping("gugudan")
    @ResponseBody
    public String gugudan(@RequestParam(defaultValue = "0")int dan, @RequestParam(defaultValue = "0")int limit){
        return IntStream.rangeClosed(1, limit)
                .mapToObj(i -> "%d * %d = %d".formatted(dan, i, dan*i))
                .collect(Collectors.joining("<br>"));
    }

    @GetMapping("mbti")
    @ResponseBody
    public String mbti(@RequestParam(defaultValue = "") String name){
        String result = switch (name){
            case "홍길동", "이몽룡" -> "INFP";
            case "홍길순" -> "ENFP";
            case "임꺽정" -> "INFJ";
            case "본인" -> "ISTJ";
            default -> "모른다";
        };
        return result;
    }

    @GetMapping("saveSession/{name}/{value}")
    @ResponseBody
    public String saveSessionAge(@PathVariable String name, @PathVariable String value, HttpServletRequest req){
        HttpSession session = req.getSession();

        session.setAttribute(name, value);

        return "세션변수 %s의 값이 %s로 설정되었습니다".formatted(name, value);
    }

    @GetMapping("getSession/{name}")
    @ResponseBody
    public String getSessionAge(@PathVariable String name, HttpServletRequest req){
        HttpSession session = req.getSession();

        String value = (String) session.getAttribute(name);

        return "세션변수 %s의 값이 %s로 설정되었습니다".formatted(name, value);
    }

    private List<Article> articles = new ArrayList<>(
            Arrays.asList(
                    new Article("제목1", "내용1"),
                    new Article("제목2", "내용2")
            )
    );
    @GetMapping("/addArticle")
    @ResponseBody
    public String addArticle(String title, String body){
        Article article = new Article(title, body);

        articles.add(article);

        return "%d번 게시물이 생성되었습니다.".formatted(article.getId());
    }

    @GetMapping("/article/{id}")
    @ResponseBody
    public Article getArticle(@PathVariable int id){
        Article article = articles
                .stream()
                .filter(a -> a.getId() == id)
                .findFirst()
                .orElse(null);

        return article;
    }

    @GetMapping("/modifyArticle/{id}")
    @ResponseBody
    public String modifyArticle(@PathVariable int id, String title, String body){
        Article article = articles
                .stream()
                .filter(a -> a.getId() == id)
                .findFirst()
                .orElse(null);

        if(article==null) {
            return "%d번 게시물은 존재하지 않습니다.".formatted(id);
        }
        article.setTitle(title);
        article.setBody(body);

        return "%d번 게시물을 수정하였습니다.".formatted(article.getId());
    }
}

@AllArgsConstructor
@Getter
@Setter
class Article{
    private static int lastId = 0;
    private int id;
    private String title;
    private String body;

    public Article(String title, String body){
        this(++lastId, title, body);
    }
}
