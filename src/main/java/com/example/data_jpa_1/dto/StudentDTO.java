package com.example.data_jpa_1.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class StudentDTO {

    private Long id;
    private String username;
    private String departmentName;
}
