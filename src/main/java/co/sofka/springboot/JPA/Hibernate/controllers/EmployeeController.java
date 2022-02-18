package co.sofka.springboot.JPA.Hibernate.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.sofka.springboot.JPA.Hibernate.model.Employee;
import co.sofka.springboot.JPA.Hibernate.model.Role;
import co.sofka.springboot.JPA.Hibernate.repository.IEmployeeJpaRepository;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class EmployeeController {
  
  @Autowired
  IEmployeeJpaRepository employeeRepository;
  
  // ========== CREATE ========== //
  
  /**
  * Metodo para crear un nuevo objeto Employee, utilizando el modelo del body del request.
  * @param employee
  * @return Un response exitoroso con el nuevo objeto creado, o un response fallido.
  */
  @PostMapping("/employees")
  public ResponseEntity<Employee> createEmployee(@RequestBody Employee newEmployee){
    try {
      Employee _employee = employeeRepository.save(new Employee(newEmployee.getFirstName(), newEmployee.getLastName(), newEmployee.getEmployeeid(), newEmployee.getRole()));
      return new ResponseEntity<>(_employee, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
    }
  }
  
  // ========== READ ========== //
  
  /**
  * Metodo para obtener todos los objetos Employee
  * @return Un response exitoroso con la lista de todos empleados si no esta vacio, o un response vacio. 
  */
  @GetMapping("/employees")
  public ResponseEntity<List<Employee>> getAllEmployees(){
    try {
      List<Employee> employees = new ArrayList<Employee>();
      
      employeeRepository.findAll().forEach(employees::add);
      
      if(employees.isEmpty())
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      
      
      return new ResponseEntity<>(employees, HttpStatus.OK);
    }catch (Exception e){
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  
  /**
  * Metodo para obtener un objeto Employee mediante su id
  * @param id
  * @return Un response exitoroso con el empleado, o un response vacio por no haber encontrado el empleado con el id.
  */
  @GetMapping("/employees/{id}")
  public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") Long id) {
    Optional<Employee> employeeData = employeeRepository.findById(id);
    
    if (employeeData.isPresent()) {
      return new ResponseEntity<>(employeeData.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  // ========== UPDATE ========== //
  
  /**
  * Metodo para modificar un objeto employee mediante su id
  * @param id
  * @param employee
  * @return Un response exitoso con el empleado modificado, o un response vacio por no haber encontrado el empleado con el id.
  */
  @PutMapping("/employees/{id}")
  public ResponseEntity<Employee> updateEmployeeById(@PathVariable(value = "id") Long id, @RequestBody Employee employee){
    Optional<Employee> employeeData = employeeRepository.findById(id);
    
    if(employeeData.isPresent()){
      Employee _employee = employeeData.get();
      _employee.setFirstName(employee.getFirstName());
      _employee.setLastName(employee.getLastName());
      _employee.setEmployeeid(employee.getEmployeeid());
      _employee.setRole(employee.getRole());
      return new ResponseEntity<>(employeeRepository.save(_employee), HttpStatus.OK);
    } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
  
  
  // ========== DELETE ========== //
  
  /**
  * Metodo para eliminar un employee mediante su id
  * @param id
  * @return Un response exitoso con un mensaje si se ha eliminado correctamente, o un response fallido.
  */
  @DeleteMapping("/employees/{id}")
  public ResponseEntity<String> deleteEmployeeById(@PathVariable(value = "id") Long id) {
    try {
      employeeRepository.deleteById(id);
      return new ResponseEntity<>("Employee DELETED!! ",HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }
  }
  
  /**
  * Metodo para eliminar todos los employeees
  * @param title
  * @return Un response exitoso con un mensaje si se han eliminado correctamente, o un response fallido.
  */
  @DeleteMapping("/employees")
  public ResponseEntity<HttpStatus> deleteAllEmployees() {
    try {
      employeeRepository.deleteAll();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }
  }
}
