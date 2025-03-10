package com.example.noticeboardexample.controller.request;

import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public record SignUpRequest(
    @NotEmpty
    @Length(min = 4, max = 10)
    String username,

    @NotEmpty
    @Length(min = 8, max = 15)
    String password
) {

}
