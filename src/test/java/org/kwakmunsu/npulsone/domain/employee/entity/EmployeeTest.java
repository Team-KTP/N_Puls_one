package org.kwakmunsu.npulsone.domain.employee.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EmployeeTest {

    Employee employee;

    @BeforeEach
    void setUp() {
        employee = Employee.join("kwak", Position.INTERN, BigDecimal.valueOf(2500000L), Department.BACKEND);
    }


    @DisplayName("새로운 직원이 입사한다")
    @Test
    void join() {
        assertThat(employee.getName()).isEqualTo("kwak");
    }

    @DisplayName("직원의 이름이 변경 된다.")
    @Test
    void updateName() {
        assertThat(employee.getName()).isEqualTo("kwak");

        employee.updateName("updateName");

        assertThat(employee.getName()).isEqualTo("updateName");
    }

    @DisplayName("직원의 부서 이동이 일어난다")
    @Test
    void changeDepartment() {
        assertThat(employee.getDepartment()).isEqualTo(Department.BACKEND);

        employee.changeDepartment(Department.FRONTEND);

        assertThat(employee.getDepartment()).isEqualTo(Department.FRONTEND);
    }

    @DisplayName("직원의 직급이 변경된다")
    @Test
    void changePosition() {
        assertThat(employee.getPosition()).isEqualTo(Position.INTERN);

        employee.changePosition(Position.ASSISTANT);

        assertThat(employee.getPosition()).isEqualTo(Position.ASSISTANT);
    }

    @DisplayName("월급이 변경된다")
    @Test
    void changeSalary() {
        assertThat(employee.getSalary()).isEqualTo(BigDecimal.valueOf(2500000L));

        employee.changeSalary(BigDecimal.valueOf(3_000_000L));

        assertThat(employee.getSalary()).isEqualTo(BigDecimal.valueOf(3_000_000L));
    }

}