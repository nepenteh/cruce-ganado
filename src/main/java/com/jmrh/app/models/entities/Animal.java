package com.jmrh.app.models.entities;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat; 

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
	
	@Column(name = "fecha_a")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Past
	@Temporal(TemporalType.DATE)
	private Date fechaA;
	
	@Column(name = "foto_a")
	private String fotoA;
	
	@ManyToOne
	@JoinColumn(name="ganaderia_id")
	private Ganaderia ganaderiaA;
	
	@ManyToOne
	@JoinColumn(name="padre_id")
	private Animal padreA;
	
	@ManyToOne
	@JoinColumn(name="madre_id")
	private Animal madreA;
	
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
	
	
		
	public Date getFechaA() {
		return fechaA;
	}


	public void setFechaA(Date fechaA) {
		this.fechaA = fechaA;
	}


	public String getFotoA() {
		return fotoA;
	}


	public void setFotoA(String fotoA) {
		this.fotoA = fotoA;
	}


	public void setGanaderiaA(Ganaderia ganaderia) {
		this.ganaderiaA = ganaderia;
	}
	
	public Ganaderia getGanaderiaA() {
		return ganaderiaA;
	}
	
	
	


	public Animal getPadreA() {
		return padreA;
	}


	public void setPadreA(Animal padreA) {
		this.padreA = padreA;
	}


	public Animal getMadreA() {
		return madreA;
	}


	public void setMadreA(Animal madreA) {
		this.madreA = madreA;
	}





	private static final long serialVersionUID = 1L;
	
}