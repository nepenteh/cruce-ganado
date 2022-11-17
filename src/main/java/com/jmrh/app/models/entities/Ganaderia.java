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
@Table(name = "ganaderias")
public class Ganaderia implements Serializable {

	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_gan")
	private Long idGan;

	@NotEmpty
	@Size(max = 50)
	@Column(name = "codigo_gan")
	private String codigoGan;

	@NotEmpty
	@Size(max = 255)
	@Column(name = "nombre_gan")
	private String nombreGan;

	@Column(name = "hierro_gan")
	private String hierroGan;

	public Ganaderia() {
	}

	public Ganaderia(String codigoGan, String nombreGan) {
		this.codigoGan = codigoGan;
		this.nombreGan = nombreGan;
	}

	public Long getIdGan() {
		return idGan;
	}

	public void setIdGan(Long idGan) {
		this.idGan = idGan;
	}

	public String getCodigoGan() {
		return codigoGan;
	}

	public void setCodigoGan(String codigoGan) {
		this.codigoGan = codigoGan;
	}

	public String getNombreGan() {
		return nombreGan;
	}

	public void setNombreGan(String nombreGan) {
		this.nombreGan = nombreGan;
	}

	public String getHierroGan() {
		return hierroGan;
	}

	public void setHierroGan(String hierroGan) {
		this.hierroGan = hierroGan;
	}
	
	@Override
	public String toString() {
		return "Ganaderia [nombreGan=" + nombreGan + "]";
	}

	private static final long serialVersionUID = 1L;

}
