package com.jmrh.app.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.jmrh.app.models.dao.GanaderiaDAO;
import com.jmrh.app.models.entities.Ganaderia;


@Service
public class GanaderiaServiceImpl implements GanaderiaService {

	private final GanaderiaDAO ganaderiaDAO;
	
	public GanaderiaServiceImpl(GanaderiaDAO ganaderiaDAO) {
		this.ganaderiaDAO = ganaderiaDAO;
	}

	@Transactional(readOnly=true)
	@Override
	public List<Ganaderia> findAll() {
		return (List<Ganaderia>) ganaderiaDAO.findAll();
	}
	
	@Transactional(readOnly=true)
	@Override
	public Page<Ganaderia> findAll(Pageable pageable) {
		return ganaderiaDAO.findAll(pageable);
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
	
	@Transactional(readOnly=true)
	@Override
	public Long count() {
		return ganaderiaDAO.count();
	}


	

	
}
