package com.benz.batch.api.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
public class Employee {

    @Id
    private int id;
    private String name;
    private String dept;
    private double salary;
    private Date time;
}
