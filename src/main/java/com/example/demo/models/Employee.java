package com.example.demo.models;

import com.example.demo.staticdtata.StaticData;
import com.example.demo.utils.JsonParser;

import java.util.List;

public record Employee(String employeeId, String fullName, String department, String designation, String hireDate,
                       Integer annualSalary, String parent) {

    private static final List<Employee> EMPLOYEES = JsonParser.parseToList(StaticData.Employee, Employee.class);


    public static Employee getById(String id) {
        return EMPLOYEES.stream()
                .filter(book -> book.employeeId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public static List<Employee> getParent(String id) {
        return EMPLOYEES.stream().filter(employee -> employee.parent.equals(id))
                .toList();
    }

    public static List<Employee> getAllEmp() {
        return EMPLOYEES;
    }
}
