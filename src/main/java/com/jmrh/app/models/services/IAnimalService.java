package com.jmrh.app.models.services;

import java.util.List;

import com.jmrh.app.models.entities.Animal;

public interface IAnimalService {

	public List<Animal> findAll();
	public Animal findOne(Long idA);
	public void save(Animal animal);
	public void remove(Long idA);
	public Long count();
	
}
