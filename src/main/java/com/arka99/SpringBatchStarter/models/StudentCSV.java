package com.arka99.SpringBatchStarter.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StudentCSV {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
