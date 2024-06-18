package com.ohgiraffers.restapi.section03.valid;


import com.ohgiraffers.restapi.section02.responseentity.ResponseMessage;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(("/valid"))
public class ValidTestController {

    private List<UserDTO> users;

    public ValidTestController(){
        users =new ArrayList<>();

        users.add(new UserDTO(1, "user01", "pass01","랄랄이", LocalDate.now()));
        users.add(new UserDTO(2, "user02", "pass02","김영이", LocalDate.now()));
        users.add(new UserDTO(3, "user03", "pass03","이김차", LocalDate.now()));



    }

    @GetMapping("/users/{userNo}")
    public ResponseEntity<ResponseMessage> findUserByNo(@PathVariable int userNo) throws UserNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));

        List<UserDTO> foundUserList = users.stream().filter(user->user.getNo()==userNo).collect(Collectors.toList());

        UserDTO foundUser = null;
        if (foundUserList.size()>0){
            foundUser = foundUserList.get(0);
        }else {
            throw new UserNotFoundException("회원 정보 찾을 수 없음");
        }
        Map<String, Object> responseMap =new HashMap<>();
        responseMap.put("user", foundUser);

        return ResponseEntity.ok().headers(headers).body(new ResponseMessage(200,"조회성공",responseMap));


    }
    @PostMapping("/users")
    public ResponseEntity<?> registUser(@Valid @RequestBody UserDTO newUser){

        System.out.println("user 들어오고 있니?" + newUser);

        return ResponseEntity.created(URI.create("/valid/users/"+newUser.getNo())).build();

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)

    public ResponseEntity<ErrorResponse>methodException(MethodArgumentNotValidException e){

        String code="";
        String description="";
        String detail = "";

        if (e.getBindingResult().hasErrors()){
            detail = e.getBindingResult().getFieldError().getDefaultMessage(); // e.getMessage() 동일한 의미
            System.out.println("detail = " + detail);

            //NotNull, Size, NotBlank .,..
            String binResultCode = e.getBindingResult().getFieldError().getCode();
            switch (binResultCode){
                case "NotBlank":
                    code = "ERROR_CODE_0002";
                    description = "필수 값이 누락되었습니다";
                    break;
                case "Size" :
                    code = "ERROR_CODE_0003";
                    description="글자 수를 확인해주세요";
                    break;
            }
        }
        return new ResponseEntity<>(new ErrorResponse(code,description,detail), HttpStatus.BAD_REQUEST);

    }




}
