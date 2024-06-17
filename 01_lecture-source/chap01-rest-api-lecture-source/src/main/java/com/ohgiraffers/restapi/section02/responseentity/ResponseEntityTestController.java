package com.ohgiraffers.restapi.section02.responseentity;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/entity")
public class ResponseEntityTestController {

    /*필기.
    *  ResponseEntity 란?
    *  결과 데이터와 http 상태 코드를 직접 제어할 수 있는 클래스 이다.
    *  HttpStatus, HttpHeaders, HttpBody 를 포함하고 있다. */

    private List<UserDTO> users;
    public ResponseEntityTestController(){
        users =new ArrayList<>();

        users.add(new UserDTO(1,"user01","pass01", "너구리", LocalDate.now()));
        users.add(new UserDTO(2,"user02","pass02", "김윤재", LocalDate.now()));
        users.add(new UserDTO(3,"user03","pass03", "정박은", LocalDate.now()));

    }

    @GetMapping("/users")

    public ResponseEntity<ResponseMessage> findAllUsers(){

        HttpHeaders headers =new HttpHeaders();
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));

       Map<String, Object> responseMap =new HashMap<>();
       responseMap.put("users", users);
       
       ResponseMessage responseMessage = new ResponseMessage(200,"조회성공",responseMap);
       
       return new ResponseEntity<>(responseMessage,headers, HttpStatus.OK);
    }


    @GetMapping("/users/{userNo}")
    public ResponseEntity<ResponseMessage>findUserByNo(@PathVariable int userNo){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));

        UserDTO foundUser = users.stream().filter(user -> user.getNo() == userNo).collect(Collectors.toList()).get(0);

        Map<String,Object> responseMap =new HashMap<>();
        responseMap.put("user", foundUser);


        /*메소드 체이닝*/

        return ResponseEntity.ok().headers(headers).body(new ResponseMessage(200,"succsess",responseMap));



    }
    /*와일드 카드 모르는 시점에는 저렇게 설정을 할 수 있다 하지만 권장되진 않는다*/
    @PostMapping("/users")
    public ResponseEntity<?> registUser(@RequestBody UserDTO newUser){

        System.out.println("newUser 잘 들어오니?"+ newUser);

        int lastUserNo = users.get(users.size() -1).getNo();

        newUser.setNo(lastUserNo +1);

        users.add(newUser);

        return ResponseEntity
                // 201 상태코드 -> 등록 관련(생성)
                .created(URI.create("/entity/users/" + users.get(users.size()-1).getNo())) // List 의 마지막 녀석 빼오기
                .build();

    }

    /*수정*/
    @PutMapping("/users/{userNo}")
    public ResponseEntity<?> modifyUser(@PathVariable int userNo,@RequestBody UserDTO modifyInfo){

        //userNo 값으로 1 행을 식별 하는 것을 foundUser 에 담아주는 것
        UserDTO foundUser = users.stream().filter(user->user.getNo()== userNo).collect(Collectors.toList()).get(0);
        foundUser.setId(modifyInfo.getId());
        foundUser.setPwd(modifyInfo.getPwd());
        foundUser.setName(modifyInfo.getName());

        return ResponseEntity.created(URI.create("/entity/users/"+ userNo)).build();

    }

    /*삭제*/

    @DeleteMapping("/users/{userNo}")

    public ResponseEntity<?> removerUser(@PathVariable int userNo){
        UserDTO foundUser = users.stream().filter(user->user.getNo()== userNo).collect(Collectors.toList()).get(0);
        users.remove(foundUser);

        return ResponseEntity.noContent().build();
    }
}
