package com.jmrh.app.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jmrh.app.models.entities.Ganaderia;

public interface IGanaderiaService {
	
	public List<Ganaderia> findAll();
	public Page<Ganaderia> findAll(Pageable pageable);
	public Ganaderia findOne(Long idGan);
	public void save(Ganaderia ganaderia);
	public void remove(Long idGan);
	public Long count();
	
}
