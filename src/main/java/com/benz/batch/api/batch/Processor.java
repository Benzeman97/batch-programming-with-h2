package com.benz.batch.api.batch;

import com.benz.batch.api.model.Employee;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class Processor implements ItemProcessor<Employee,Employee> {

    final private static Map<Integer,String> DEPT_NAMES
            =new HashMap<>();

    public Processor()
    {
        DEPT_NAMES.put(10,"Technology");
        DEPT_NAMES.put(20,"Operations");
        DEPT_NAMES.put(30,"Account");
    }

    @Override
    public Employee process(Employee employee) throws Exception {

        String deptCode = employee.getDept();
        String dept = DEPT_NAMES.get(Integer.valueOf(deptCode));
        employee.setDept(dept);
        employee.setTime(new Date());
        return employee;
    }
}
