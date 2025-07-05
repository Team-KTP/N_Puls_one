package org.kwakmunsu.npulsone.domain.employee.entity;

import static java.util.Objects.requireNonNull;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Position position;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Department department;

    @Column(name = "hire_at")
    private LocalDateTime hireAt;

    public static Employee join(EmployeeJoinRequest request) {
        Employee employee = new Employee();

        employee.name = requireNonNull(request.name());
        employee.position = requireNonNull(request.position());
        employee.department = requireNonNull(request.department());

        employee.hireAt = LocalDateTime.now();

        return employee;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void changeDepartment(Department department) {
        this.department = department;
    }

    public void changePosition(Position position) {
        this.position = position;
    }

}