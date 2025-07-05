package org.kwakmunsu.npulsone.domain.employee.entity;

import org.springframework.test.util.ReflectionTestUtils;

/*
 * 테스트 코드에서 반복적으로 사용되는 도메인의 관련 데이터를 모아둔 클래스
 * */
public class EmployeeFixture {

    public static EmployeeJoinRequest createEmployeeJoinRequest() {
        return new EmployeeJoinRequest("kwak", Position.INTERN, Department.BACKEND);
    }

    public static Employee createEmployee() {
        return Employee.join(createEmployeeJoinRequest());
    }

    public static Employee createEmployee(Long id) {
        Employee employee = Employee.join(createEmployeeJoinRequest());
        ReflectionTestUtils.setField(employee, "id", id);

        return employee;
    }

}