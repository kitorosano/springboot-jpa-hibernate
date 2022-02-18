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

import co.sofka.springboot.JPA.Hibernate.model.Project;
import co.sofka.springboot.JPA.Hibernate.repository.IProjectJpaRepository;

@CrossOrigin("http://localhost:8080")
@RestController
@RequestMapping("/api")
public class ProjectController {
  
  @Autowired
  IProjectJpaRepository projectRepository;


  // ========== CREATE ========== //
  
  /**
  * Metodo para crear un nuevo objeto Project, utilizando el modelo del body del request.
  * @param project
  * @return Un response exitoroso con el nuevo objeto creado, o un response fallido.
  */
  @PostMapping("/projects")
  public ResponseEntity<Project> createProject(@RequestBody Project newProject){
    try {
      Project _project = projectRepository.save(new Project(newProject.getName()));
      return new ResponseEntity<>(_project, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
    }
  }
  
  
  // ========== READ ========== //
  
  /**
  * Metodo para obtener todos los objetos Project
  * @return Un response exitoroso con la lista de todos proyectos si no esta vacio, o un response vacio. 
  */
  @GetMapping("/projects")
  public ResponseEntity<List<Project>> getAllProjects(){
    try {
      List<Project> projects = new ArrayList<Project>();
      
      projectRepository.findAll().forEach(projects::add);
      
      if(projects.isEmpty())
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      
      
      return new ResponseEntity<>(projects, HttpStatus.OK);
    }catch (Exception e){
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  
  /**
  * Metodo para obtener un objeto Project mediante su id
  * @param id
  * @return Un response exitoroso con el proyecto, o un response vacio por no haber encontrado el proyecto con el id.
  */
  @GetMapping("/projects/{id}")
  public ResponseEntity<Project> getProjectById(@PathVariable(value = "id") Long id) {
    Optional<Project> projectData = projectRepository.findById(id);
    
    if (projectData.isPresent()) {
      return new ResponseEntity<>(projectData.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
  
  /**
  * Metodo para obtener un objeto Project mediante su nombre
  * @return Un response exitoroso con el proyecto, o un response vacio por no haber encontrado el proyecto con su nombre.
  */
  @GetMapping(value = "/projects", params = "name")
  public ResponseEntity<List<Project>> getProjectByName(@RequestParam(value = "name") String name) {
    List<Project> projectData = projectRepository.findByName(name);
    
    try {
      if (projectData.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      
      return new ResponseEntity<>(projectData, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }
  }


  // ========== UPDATE ========== //
  
  /**
  * Metodo para modificar un objeto Project mediante su id
  * @param id
  * @param project
  * @return Un response exitoso con el proyecto modificado, o un response vacio por no haber encontrado el proyecto con el id.
  */
  @PutMapping("/projects/{id}")
  public ResponseEntity<Project> updateProjectById(@PathVariable(value = "id") Long id, @RequestBody Project project){
    Optional<Project> projectData = projectRepository.findById(id);
    
    if(projectData.isPresent()){
      Project _project = projectData.get();
      _project.setName(project.getName());
      return new ResponseEntity<>(projectRepository.save(_project), HttpStatus.OK);
    } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
  
  
  // ========== DELETE ========== //
  
  /**
  * Metodo para eliminar un Project mediante su id
  * @param id
  * @return Un response exitoso con un mensaje si se ha eliminado correctamente, o un response fallido.
  */
  @DeleteMapping("/projects/{id}")
  public ResponseEntity<String> deleteProjectById(@PathVariable(value = "id") Long id) {
    try {
      projectRepository.deleteById(id);
      return new ResponseEntity<>("Project DELETED!! ",HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }
  }
  
  /**
  * Metodo para eliminar todos los projectes
  * @return Un response exitoso con un mensaje si se han eliminado correctamente, o un response fallido.
  */
  @DeleteMapping("/projects")
  public ResponseEntity<HttpStatus> deleteAllProjects() {
    try {
      projectRepository.deleteAll();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }
  }
}
