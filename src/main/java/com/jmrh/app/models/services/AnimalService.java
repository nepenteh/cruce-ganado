package com.jmrh.app.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jmrh.app.models.dao.IAnimalDAO;
import com.jmrh.app.models.entities.Animal;
import com.jmrh.app.models.entities.Ganaderia;

@Service
public class AnimalService implements IAnimalService {

	@Autowired
	private IAnimalDAO animalDAO;
	
	@Transactional(readOnly=true)
	@Override
	public List<Animal> findAll() {
		return (List<Animal>) animalDAO.findAll();
	}

	@Transactional(readOnly=true)
	@Override
	public Animal findOne(Long idA) {
		return animalDAO.findById(idA).orElse(null);
	}

	@Transactional
	@Override
	public void save(Animal animal) {
		animalDAO.save(animal);
	}

	@Transactional
	@Override
	public void remove(Long idA) {
		animalDAO.deleteById(idA);
	}

	@Transactional(readOnly=true)
	@Override
	public Long count() {
		return animalDAO.count();
	}

}
