package com.jmrh.app.models.services;

import java.util.List;

import com.jmrh.app.models.entities.Animal;

public interface IArbolAnimalesService {
	
	public List<Animal> generarArbol(Animal animal);
	
}
