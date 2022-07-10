package edu.school21.financialassistanceapplicationgenerator.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Person {
    private String name;
    private String lastName;
    private int age;
}
