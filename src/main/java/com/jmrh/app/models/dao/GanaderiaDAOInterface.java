package com.jmrh.app.models.dao;

import java.util.List;

import com.jmrh.app.models.entities.Ganaderia;

public interface GanaderiaDAOInterface {

	public List<Ganaderia> findAll();
	public Ganaderia findOne(Long idGan);
	public void save(Ganaderia ganaderia);
	public void remove(Long idGan);
}
