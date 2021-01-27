package com.benz.batch.api.dao;

import com.benz.batch.api.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO extends JpaRepository<Employee,Integer> {
}
