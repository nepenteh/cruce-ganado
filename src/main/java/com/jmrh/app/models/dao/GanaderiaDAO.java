package com.jmrh.app.models.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jmrh.app.models.entities.Ganaderia;

@Repository
public class GanaderiaDAO implements GanaderiaDAOInterface {

	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<Ganaderia> findAll() {
		return em.createQuery("from Ganaderia").getResultList();
	}
	
	@Transactional(readOnly = true)
	@Override
	public Ganaderia findOne(Long idGan) {
		return em.find(Ganaderia.class, idGan);
	}

	@Transactional
	@Override
	public void save(Ganaderia ganaderia) {
		if(ganaderia.getIdGan() != null && ganaderia.getIdGan() > 0)
			em.merge(ganaderia);
		else
			em.persist(ganaderia);
	}
	
	
	@Transactional
	@Override
	public void remove(Long idGan) {
		em.remove(findOne(idGan));
	}
	
	
			
}
