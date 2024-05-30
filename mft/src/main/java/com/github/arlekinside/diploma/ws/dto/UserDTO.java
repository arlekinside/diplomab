package com.github.arlekinside.diploma.ws.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private String username;
    @ToString.Exclude
    private String password;

}
