package edu.school21.foodapplicationgenerator.entity;

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
    private int course;
}
