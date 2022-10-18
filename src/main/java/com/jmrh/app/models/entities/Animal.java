package com.jmrh.app.models.entities;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size; 

@Entity
@Table(name="animales")
public class Animal implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_a")
	Long idA;
	
	@NotEmpty
	@Size(max=50)
	@Column(name = "nombre_a")
	String nombreA;
	
	@NotEmpty
	@Size(max=1)
	@Column(name = "sexo_a")
	String sexoA;
	
	@Column(name = "puntuacion_a")
	Long puntuacionA;
	
	
	public Animal() {}

	
	public Animal(@NotEmpty @Size(max = 50) String nombreA, @NotEmpty @Size(max = 1) String sexoA, Long puntuacionA) {
		super();
		this.nombreA = nombreA;
		this.sexoA = sexoA;
		this.puntuacionA = puntuacionA;
	}


	public Long getIdA() {
		return idA;
	}


	public void setIdA(Long idA) {
		this.idA = idA;
	}


	public String getNombreA() {
		return nombreA;
	}


	public void setNombreA(String nombreA) {
		this.nombreA = nombreA;
	}


	public String getSexoA() {
		return sexoA;
	}


	public void setSexoA(String sexoA) {
		this.sexoA = sexoA;
	}


	public Long getPuntuacionA() {
		return puntuacionA;
	}


	public void setPuntuacionA(Long puntuacionA) {
		this.puntuacionA = puntuacionA;
	}
	
	
		
	private static final long serialVersionUID = 1L;
	
}
