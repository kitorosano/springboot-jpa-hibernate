package co.sofka.springboot.JPA.Hibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.sofka.springboot.JPA.Hibernate.model.Project;

@Repository
public interface IProjectJpaRepository extends JpaRepository<Project, Long> {
  
}

