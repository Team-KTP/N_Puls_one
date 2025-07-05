package org.kwakmunsu.npulsone.domain.salary.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kwakmunsu.npulsone.domain.employee.entity.Employee;
import org.kwakmunsu.npulsone.domain.employee.entity.EmployeeFixture;

class SalaryTest {

    @DisplayName("월급을 준다")
    @Test
    void create() {
        Employee employee = EmployeeFixture.createEmployee(1L);
        Salary salary = Salary.create(employee, BigDecimal.valueOf(2_500_000L));

        assertThat(salary.getEmployee().getId()).isEqualTo(1L);
        assertThat(salary.getMoney()).isEqualTo(BigDecimal.valueOf(2_500_000L));
    }

    @DisplayName("월급이 변경된다")
    @Test
    void changeSalary() {
        Employee employee = EmployeeFixture.createEmployee(1L);
        Salary salary = Salary.create(employee, BigDecimal.valueOf(2_500_000L));

        salary.modifyMoney(BigDecimal.valueOf(3_000_000L));
        assertThat(salary.getMoney()).isEqualTo(BigDecimal.valueOf(3_000_000L));
    }

}