package com.arka99.SpringBatchStarter.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@XmlRootElement(name = "student")
public class StudentXML {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
