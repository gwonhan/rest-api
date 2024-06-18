package com.ohgiraffers.restapi.section05.swagger;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*@Tag : API 들의 그룹을 짓기 위한 어노테이션*/
@Tag(name = "Spring Boot Swagger 연동(USER 기능)")
@RestController
@RequestMapping("/swagger")
public class SwaggerTestController {

    private List<UserDTO> users;

    public SwaggerTestController() {
        users = new ArrayList<>();

        users.add(new UserDTO(1,"user01,","pass01", "김치맨", LocalDate.now()));
        users.add(new UserDTO(2,"user02,","pass02", "사리맨", LocalDate.now()));
        users.add(new UserDTO(3,"user03,","pass03", "양념맨", LocalDate.now()));


    }

    /*@Operation
    *  해당하는 api 의 정보를 제공하는 어노테이션
    *  속성
    *  summary : 해당 api 의 간단한 요약을 제공한다.
    *  description : 해당 api 의 상제한 성명을 제공한다.*/

    @Operation(summary = "전체 회원조회", description = "우리 사이트의 전체 회원 목록 조회")
    @GetMapping("/users")

    public ResponseEntity<ResponseMessage> findAllUsers(){

        HttpHeaders headers =new HttpHeaders();
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));

        Map<String, Object> responseMap =new HashMap<>();
        responseMap.put("users", users);

        ResponseMessage responseMessage = new ResponseMessage(200,"조회성공",responseMap);

        return new ResponseEntity<>(responseMessage,headers, HttpStatus.OK);
    }




    @Operation(summary = "회원번호로 회원조회", description = "회원번호를 통해 해당되는 회원을 조회한다."
                , parameters = {@Parameter(name = "userNo", description = "사용자 화면에서 넘어오는 유저의 pk")})
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
    @Operation(summary = "신규회원등록")
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


    @Operation(summary = "회원정보 수정")
    @PutMapping("/users/{userNo}")
    public ResponseEntity<?> modifyUser(@PathVariable int userNo,@RequestBody UserDTO modifyInfo){

        //userNo 값으로 1 행을 식별 하는 것을 foundUser 에 담아주는 것
        UserDTO foundUser = users.stream().filter(user->user.getNo()== userNo).collect(Collectors.toList()).get(0);
        foundUser.setId(modifyInfo.getId());
        foundUser.setPwd(modifyInfo.getPwd());
        foundUser.setName(modifyInfo.getName());

        return ResponseEntity.created(URI.create("/entity/users/"+ userNo)).build();

    }


    @Operation(summary = "회원정보 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "204",description = "회원정보 삭제 성공"),
            @ApiResponse(responseCode = "400",description = "잘못 입력된 파라미터 오류")
    })
    @DeleteMapping("/users/{userNo}")

    public ResponseEntity<?> removerUser(@PathVariable int userNo){
        UserDTO foundUser = users.stream().filter(user->user.getNo()== userNo).collect(Collectors.toList()).get(0);
        users.remove(foundUser);

        return ResponseEntity.noContent().build();
    }


}
