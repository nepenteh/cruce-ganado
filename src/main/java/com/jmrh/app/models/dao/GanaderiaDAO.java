package com.jmrh.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.jmrh.app.models.entities.Ganaderia;

public interface GanaderiaDAO extends PagingAndSortingRepository<Ganaderia, Long> {
	
	@Query("select g from Ganaderia g where g.nombreGan like %?1%")
	public List<Ganaderia> findByNombre(String cadena);
	
}
