package com.jmrh.app.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jmrh.app.models.dao.IAnimalDAO;
import com.jmrh.app.models.dao.IGanaderiaDAO;
import com.jmrh.app.models.entities.Animal;
import com.jmrh.app.models.entities.Ganaderia;

@Service
public class AnimalService implements IAnimalService {

	@Autowired
	private IAnimalDAO animalDAO;
	
	@Autowired
	private IGanaderiaDAO ganaderiaDAO;
	
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
	
	@Transactional(readOnly=true)
	@Override
	public List<Ganaderia> findByNombre(String cadena) {
		return ganaderiaDAO.findByNombre(cadena);
	}

	@Transactional(readOnly=true)
	@Override
	public List<Animal> findHembraByNombre(String cadena) {
		return animalDAO.findHembraByNombre(cadena);
	}

	@Transactional(readOnly=true)
	@Override
	public List<Animal> findMachoByNombre(String cadena) {
		return animalDAO.findMachoByNombre(cadena);
	}

	@Transactional
	@Override
	public void quitarPadres(Long idA) {
		animalDAO.quitarPadres(idA);
	}
	
	@Transactional
	@Override
	public void quitarMadres(Long idA) {
		animalDAO.quitarMadres(idA);
	}
	
	@Transactional
	@Override
	public void quitarGanaderia(Long idGan) {
		animalDAO.quitarGanaderia(idGan);
	}

}
