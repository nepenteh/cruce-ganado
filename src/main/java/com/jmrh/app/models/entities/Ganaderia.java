package com.jmrh.app.models.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ganaderias")
public class Ganaderia implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long idGan;
	public String codigoGan;
	public String nombreGan;

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

	@Override
	public int hashCode() {
		return Objects.hash(codigoGan, idGan, nombreGan);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ganaderia other = (Ganaderia) obj;
		return Objects.equals(codigoGan, other.codigoGan) && Objects.equals(idGan, other.idGan)
				&& Objects.equals(nombreGan, other.nombreGan);
	}

	@Override
	public String toString() {
		return "Ganaderia [idGan=" + idGan + ", codigoGan=" + codigoGan + ", nombreGan=" + nombreGan + "]";
	}

	

}
