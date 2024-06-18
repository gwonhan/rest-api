package com.ohgiraffers.restapi.section03.valid;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class UserDTO {

    /*@Valid - 유효성 검증*/
    /*필기.
    *  @Null : null 만 허용한다.
    *  @NotNull : null 은 허용하지 않습니다. "", " " 는 허용한다.
    *  @NotEmpty : null 과 "" 허용하지 않는다. " " 는 허용한다.
    *  @NotBlank : null 과, "", " " 모두 허용하지 않는다
    *  @Email : 이메일 형식을 검사한다. 다만 "" 은 통과시킨다.
    *  @Pattern(regexp) : 정규식 검사시에 사용된다.
    *  ex) regexp"[a-z1-8]{6-10}", message = "비밀번호는 영어 숫자 포함해서 6~10 자리 이내로"
    *  @Size(min=, max=) : 길이를 제한할 떄 사용한다.
    *  @Max(value=) value 이하의 값을 받을때 사용한다.
    *  @Min(value=) value 이상의 값을 받을때 사용한다.
    *  @Positive : 값을 양수로 제한
    *  @PositiveOrZero :양수와 0 만 가능하다.
    *  @Future 현재보다 미래 날짜
    *  @Past 현재보다 과거 날짜
    *  @AssertTrue : true 여부 확인, null 은 체크하지 않음
    * */

    private int no;
    @NotNull(message = "아이디는 반드시 입력되어야 합니다.")
    @NotBlank(message = "공백 안됨")
    private String id;
    private String pwd;


    private String name;
    @NotNull(message = "이름은 반드시 입력되어야 합니다.")
    @Size(min = 2, message = "이름은 2자 이상")

    private LocalDate enrollDate;
    @Past

    public UserDTO() {
    }

    public UserDTO(int no, String id, String pwd, String name, LocalDate enrollDate) {
        this.no = no;
        this.id = id;
        this.pwd = pwd;
        this.name = name;
        this.enrollDate = enrollDate;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getEnrollDate() {
        return enrollDate;
    }

    public void setEnrollDate(LocalDate enrollDate) {
        this.enrollDate = enrollDate;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "no=" + no +
                ", id='" + id + '\'' +
                ", pwd='" + pwd + '\'' +
                ", name='" + name + '\'' +
                ", enrollDate=" + enrollDate +
                '}';
    }
}
