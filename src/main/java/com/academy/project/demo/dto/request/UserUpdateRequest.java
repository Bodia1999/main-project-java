package com.academy.project.demo.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserUpdateRequest {
    //    @NotBlank
    private Long id;

    //    @NotBlank
//    @Size(min = 4, max = 40)
    private String name;

    //    @NotBlank
//    @Size(min = 4, max = 100)
    private String surname;

    //    @NotBlank
//    @Size(max = 40)
    private String phoneNumber;
    @Email
    private String email;

}
