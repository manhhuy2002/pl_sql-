package com.example.sql_injection.service;

import com.example.sql_injection.model.Employee;
import com.example.sql_injection.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepo employeeRepo;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepo.findAll();
    }
    @Override
    public void saveEmployee(Employee employee) {
        this.employeeRepo.save(employee);
    }

    @Override
    public Employee getEmployeeById(long id) {
        Optional < Employee > optional = employeeRepo.findById(id);
        Employee employee = null;
        if (optional.isPresent()) {
            employee = optional.get();
        } else {
            throw new RuntimeException(" Employee not found for id :: " + id);
        }
        return employee;
    }
    @Override
    public void deleteEmployeeById(long id) {
        // Xóa bộ đệm JPA
        entityManager.getEntityManagerFactory().getCache().evictAll();

        // Xóa bản ghi từ cơ sở dữ liệu
        this.employeeRepo.deleteById(id);

        // Làm mới dữ liệu
        entityManager.refresh(Employee.class);
    }
}
