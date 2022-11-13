package com.jmrh.app.controllers.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jmrh.app.models.entities.Animal;

@Service
public class ArbolAnimales implements IArbolAnimales {

	public List<Animal> generarArbol(Animal animal) {

		List<Animal> arbol = new ArrayList<>();

		arbol.add(animal);

		List<Animal> generacion = new ArrayList<>();
		generacion.add(animal);

		// tres generaciones de ancestros
		for (int i = 0; i < 3; i++) {
			generacion = obtenerPadresYMadres(generacion);
			arbol.addAll(generacion);
		}

		return arbol;

	}

	private List<Animal> obtenerPadresYMadres(List<Animal> generacion) {

		List<Animal> padresymadres = new ArrayList<>();

		generacion.forEach(a -> {
			if (a != null) {
				padresymadres.add(a.getPadreA());
				padresymadres.add(a.getMadreA());
			} else {
				padresymadres.add(null);
				padresymadres.add(null);
			}
		});

		return padresymadres;
	}

}
