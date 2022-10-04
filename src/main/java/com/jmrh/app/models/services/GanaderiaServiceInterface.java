package com.jmrh.app.models.services;

import java.util.List;

import com.jmrh.app.models.entities.Ganaderia;

public interface GanaderiaServiceInterface {
	
	public List<Ganaderia> findAll();
	public Ganaderia findOne(Long idGan);
	public void save(Ganaderia ganaderia);
	public void remove(Long idGan);
	
}
