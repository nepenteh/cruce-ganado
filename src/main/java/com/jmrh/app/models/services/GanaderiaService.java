package com.jmrh.app.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.jmrh.app.models.dao.GanaderiaDAOInterface;
import com.jmrh.app.models.entities.Ganaderia;


@Service
public class GanaderiaService implements GanaderiaServiceInterface {

	@Autowired
	private GanaderiaDAOInterface ganaderiaDAO;

	@Transactional(readOnly=true)
	@Override
	public List<Ganaderia> findAll() {
		return (List<Ganaderia>) ganaderiaDAO.findAll();
	}

	@Transactional(readOnly=true)
	@Override
	public Ganaderia findOne(Long idGan) {
		return ganaderiaDAO.findById(idGan).orElse(null);
	}

	@Transactional
	@Override
	public void save(Ganaderia ganaderia) {
		ganaderiaDAO.save(ganaderia);
	}

	@Transactional
	@Override
	public void remove(Long idGan) {
		ganaderiaDAO.deleteById(idGan);
	}
	

	
}
