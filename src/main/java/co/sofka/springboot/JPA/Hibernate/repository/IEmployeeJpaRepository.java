package co.sofka.springboot.JPA.Hibernate.repository;

import co.sofka.springboot.JPA.Hibernate.model.Employee;
import co.sofka.springboot.JPA.Hibernate.model.Role;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmployeeJpaRepository extends JpaRepository<Employee, Long> {
    //select fileds from employee where employeeid='[param]'
    Employee findByEmployeeid(String employeeid);
    List<Employee> findByLastName(String lastName);
    List<Employee> findByRole(Role role);
}
