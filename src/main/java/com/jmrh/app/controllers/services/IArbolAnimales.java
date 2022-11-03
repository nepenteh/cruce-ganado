package com.jmrh.app.controllers.services;

import java.util.List;

import com.jmrh.app.models.entities.Animal;

public interface IArbolAnimales {
	
	public List<Animal> generarArbol(Animal animal);
	
}
