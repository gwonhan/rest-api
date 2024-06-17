package com.ohgiraffers.restapi.section01.response;

import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.print.attribute.standard.Media;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Controller

@RestController
@RequestMapping("/response")
/*Controller + ResponseBody*/
public class ResponseTestController {

    /*문자열 응답 test*/
    @GetMapping("/hello")
    public String helloWorld(){
        System.out.println("hello world");

        return "hello world!";
    }

    /*기본 자료형 test*/
    @GetMapping("/random")

    public int getRandomNumber(){

        return (int) ((Math.random()*10)+1);
    }


    /* Object 타입 응답*/
    @GetMapping("/message")
    public Message getMessage (){

        return new Message(200,"응답 해라");

    }
    /*List 타입 응답*/

    @GetMapping("/list")
    public List<String> getList(){

        return List.of(new String("햄버거, 피자, 닭가슴살"));
    }

    @GetMapping("/map")
    public Map<Integer,String> getMap(){

        Map<Integer, String> messageMap =new HashMap<>();

      messageMap.put(200,"정상응답");
        messageMap.put(404,"페이지 찾을 수 없음");
        messageMap.put(500," 서버 내부 오류-> 개발자 오류 ");

        return messageMap;
    }

    /* image response
    *  produces 설정을 해주지 않으면 이미지가 텍스트 형태로 전송된다.
    *  produces 는 response header 의 context- type 에 대한 설정이다.
    * */

    @GetMapping(value = "/image", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage() throws IOException {

        return getClass().getResourceAsStream("/images/dd.jpg").readAllBytes();
    }
    /*ResponseEntity 를 이용하 응답*/
    /*타입을 지정할 수 있음 , 값을 꺼내서 쓰려고 해서 쓰는거임*/
    @GetMapping("/entity")
    public ResponseEntity<Message> getEntity(){
        return ResponseEntity.ok(new Message(200,"정상응답?"));
    }

}
