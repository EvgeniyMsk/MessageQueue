package edu.school21.studentsdataproducer.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Person {
    private String name;
    private String lastName;
    private int age;
}
