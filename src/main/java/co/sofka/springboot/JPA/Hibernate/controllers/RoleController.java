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

import co.sofka.springboot.JPA.Hibernate.model.Role;
import co.sofka.springboot.JPA.Hibernate.repository.IRoleJpaRepository;

@CrossOrigin("http://localhost:8080")
@RestController
@RequestMapping("/api")
public class RoleController {
  
  @Autowired
  IRoleJpaRepository roleRepository;


  // ========== CREATE ========== //
  
  /**
  * Metodo para crear un nuevo objeto Role, utilizando el modelo del body del request.
  * @param project
  * @return Un response exitoroso con el nuevo objeto creado, o un response fallido.
  */
  @PostMapping("/roles")
  public ResponseEntity<Role> createRole(@RequestBody Role newRole){
    try {
      Role _rol = roleRepository.save(new Role(newRole.getName()));
      return new ResponseEntity<>(_rol, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
    }
  }
  
  
  // ========== READ ========== //
  
  /**
  * Metodo para obtener todos los objetos Role
  * @return Un response exitoroso con la lista de todos roles si no esta vacio, o un response vacio. 
  */
  @GetMapping("/roles")
  public ResponseEntity<List<Role>> getAllRoles(){
    try {
      List<Role> roles = new ArrayList<Role>();
      
      roleRepository.findAll().forEach(roles::add);
      
      if(roles.isEmpty())
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      
      return new ResponseEntity<>(roles, HttpStatus.OK);
    }catch (Exception e){
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  
  /**
  * Metodo para obtener un objeto Role mediante su id
  * @param id
  * @return Un response exitoroso con el rol, o un response vacio por no haber encontrado el rol con el id.
  */
  @GetMapping("/roles/{id}")
  public ResponseEntity<Role> getRoleById(@PathVariable(value = "id") Long id) {
    Optional<Role> roleData = roleRepository.findById(id);
    
    if (roleData.isPresent())
      return new ResponseEntity<>(roleData.get(), HttpStatus.OK);
      
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
  
  /**
  * Metodo para obtener un objeto Role mediante su nombre
  * @return Un response exitoroso con el rol, o un response vacio por no haber encontrado el rol con su nombre.
  */
  @GetMapping(value = "/roles", params = "name")
  public ResponseEntity<List<Role>> getRoleByName(@RequestParam(value = "name") String name) {
    List<Role> roleData = roleRepository.findByName(name);
    
    try {
      if (roleData.isEmpty()) 
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      
      return new ResponseEntity<>(roleData, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }
  }


  // ========== UPDATE ========== //
  
  /**
  * Metodo para modificar un objeto Role mediante su id
  * @param id
  * @param project
  * @return Un response exitoso con el rol modificado, o un response vacio por no haber encontrado el rol con el id.
  */
  @PutMapping("/roles/{id}")
  public ResponseEntity<Role> updateRoleById(@PathVariable(value = "id") Long id, @RequestBody Role project){
    Optional<Role> roleData = roleRepository.findById(id);
    
    if(roleData.isPresent()){
      Role _rol = roleData.get();
      _rol.setName(project.getName());
      return new ResponseEntity<>(roleRepository.save(_rol), HttpStatus.OK);
    } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
  
  
  // ========== DELETE ========== //
  
  /**
  * Metodo para eliminar un Role mediante su id
  * @param id
  * @return Un response exitoso con un mensaje si se ha eliminado correctamente, o un response fallido.
  */
  @DeleteMapping("/roles/{id}")
  public ResponseEntity<String> deleteRoleById(@PathVariable(value = "id") Long id) {
    try {
      roleRepository.deleteById(id);
      return new ResponseEntity<>("Role DELETED!! ",HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }
  }
  
  /**
  * Metodo para eliminar todos los projectes
  * @return Un response exitoso con un mensaje si se han eliminado correctamente, o un response fallido.
  */
  @DeleteMapping("/roles")
  public ResponseEntity<HttpStatus> deleteAllRoles() {
    try {
      roleRepository.deleteAll();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }
  }
}