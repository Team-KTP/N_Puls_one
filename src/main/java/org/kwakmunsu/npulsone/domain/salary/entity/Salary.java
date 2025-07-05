package org.kwakmunsu.npulsone.domain.salary.entity;

import static java.util.Objects.requireNonNull;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.kwakmunsu.npulsone.domain.employee.entity.Employee;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(nullable = false)
    private BigDecimal money;

    @Column(name = "salary_at")
    private LocalDate salaryAt;

    public static Salary create(Employee employee, BigDecimal money) {
        Salary salary = new Salary();

        salary.employee = requireNonNull(employee);
        salary.money = requireNonNull(money);
        salary.salaryAt = LocalDate.now();

        return salary;
    }

    public void modifyMoney(BigDecimal money) {
        this.money = money;
    }

}