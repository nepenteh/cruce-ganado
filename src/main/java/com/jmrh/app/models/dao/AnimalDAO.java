package com.jmrh.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.jmrh.app.models.entities.Animal;

public interface AnimalDAO extends PagingAndSortingRepository<Animal, Long> {

	@Query("select a from Animal a where a.sexoA='H' and a.nombreA like %?1%")
	public List<Animal> findHembraByNombre(String string);

	@Query("select a from Animal a where a.sexoA='M' and a.nombreA like %?1%")
	public List<Animal> findMachoByNombre(String string);
	
	@Modifying
	@Query("update Animal a set a.padreA=null where a.padreA.idA=?1")
	public void quitarPadres(Long idA);
	
	@Modifying
	@Query("update Animal a set a.madreA=null where a.madreA.idA=?1")
	public void quitarMadres(Long idA);
	
	@Modifying
	@Query("update Animal a set a.ganaderiaA=null where a.ganaderiaA.idGan=?1")
	public void deleteGanaderia(Long idGan);
	
	@Query("select a from Animal a where a.ganaderiaA.idGan=?1")
	public List<Animal> animalWithGanaderia(Long idGan);
}
