package com.ll.exam.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
}
