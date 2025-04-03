package com.nitish.quicktasks.controller;

import com.nitish.quicktasks.models.Connection;
import com.nitish.quicktasks.models.Edge;
import com.nitish.quicktasks.models.Employee;
import com.nitish.quicktasks.models.PageInfo;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

import static com.nitish.quicktasks.utils.CommonUtils.getSubList;

@Controller
public class EmployeeController {
    @QueryMapping
    public Employee employeeById(@Argument String id) {
        return Employee.getById(id);
    }

    @SchemaMapping
    public List<Employee> subordinates(Employee e) {
        return Employee.getParent(e.employeeId());
    }

    @QueryMapping
    public static Connection<Employee> getAll(@Argument int first, @Argument Integer pageSize) {
        var totalRecord = Employee.getAllEmp().size();
        pageSize = pageSize != null ? Math.min(pageSize, totalRecord) : 10;

        int offSet = (first - 1) * pageSize;

        var employees = getSubList(Employee.getAllEmp(), offSet, pageSize);

        List<Edge<Employee>> edgeList = employees.stream()
                .map(employee -> new Edge<>(String.valueOf(employee.employeeId()), employee))
                .collect(Collectors.toList());

        String startCursor = edgeList.isEmpty() ? null : edgeList.get(0).cursor();
        String endCursor = edgeList.isEmpty() ? null : edgeList.get(edgeList.size() - 1).cursor();

        PageInfo pageInfo = new PageInfo(startCursor, endCursor, totalRecord, offSet, edgeList.size());
        return new Connection<>(pageInfo, edgeList);
    }

}
