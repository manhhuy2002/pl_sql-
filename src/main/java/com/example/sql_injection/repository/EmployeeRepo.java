package com.example.sql_injection.repository;

import com.example.sql_injection.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee,Long> {
    @Procedure(name = "get_credentials", outputParameterName = "v_result")
    void getCredentials(@Param("p_username") String username, @Param("p_password") String password);

}
