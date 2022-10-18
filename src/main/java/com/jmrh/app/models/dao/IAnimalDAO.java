package com.jmrh.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.jmrh.app.models.entities.Animal;

public interface IAnimalDAO extends CrudRepository<Animal, Long> {

}
