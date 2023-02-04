package com.jmrh.app.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jmrh.app.models.entities.Animal;
import com.jmrh.app.models.entities.Ganaderia;

public interface AnimalService {

	public List<Animal> findAll();
	public Page<Animal> findAll(Pageable pageable);
	public Animal findOne(Long idA);
	public void save(Animal animal);
	public void remove(Long idA);
	public Long count();
	public List<Ganaderia> findByNombre(String string);
	public List<Animal> findHembraByNombre(String string);
	public List<Animal> findMachoByNombre(String string);
	public void quitarPadres(Long idA);
	public void quitarMadres(Long idA);
	public void deleteGanaderia(Long idGan);
	public List<Animal> animalWithGanaderia(Long idGan);
	
}
