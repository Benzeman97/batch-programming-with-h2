package com.benz.batch.api.batch;

import com.benz.batch.api.dao.UserDAO;
import com.benz.batch.api.model.Employee;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DBWriter implements ItemWriter<Employee> {

    private UserDAO userDAO;

    public DBWriter(UserDAO userDAO)
    {
        this.userDAO=userDAO;
    }

    @Override
    public void write(List<? extends Employee> emps) throws Exception {
        System.out.printf("Employee is saved %s",emps);
         userDAO.saveAll(emps);
    }
}
