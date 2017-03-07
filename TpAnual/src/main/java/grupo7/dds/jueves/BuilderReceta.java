package grupo7.dds.jueves;

import grupo7.dds.jueves.CondicionesPreexistentes.Celiaco;
import grupo7.dds.jueves.CondicionesPreexistentes.CondicionPreexistente;
import grupo7.dds.jueves.CondicionesPreexistentes.Diabetico;
import grupo7.dds.jueves.CondicionesPreexistentes.Hipertenso;
import grupo7.dds.jueves.CondicionesPreexistentes.Vegano;

import java.util.ArrayList;
import java.util.List;

public class BuilderReceta{
	
	private Receta receta;
	
	public BuilderReceta(String nombre){
		receta = new Receta();
		receta.setNombre(nombre);
	}
	
	public BuilderReceta preparacion(String preparacion){
		receta.setPreparacion(preparacion);
		return this;
	}
	
	public BuilderReceta dificultad(String dificultad){
		receta.setDificultad(dificultad);
		return this;
	}

	public BuilderReceta temporada(String temporada){
		receta.setTemporada(temporada);
		return this;
	}
	
	public BuilderReceta autor(Usuario autor){
		receta.setAutor(autor);
		return this;
	}
	
	public Receta build(){
		receta.validar();
		List<CondicionPreexistente> condicionesPreexistentes = new ArrayList<CondicionPreexistente>();
		condicionesPreexistentes.add(new Celiaco());
		condicionesPreexistentes.add(new Diabetico());
		condicionesPreexistentes.add(new Hipertenso());
		condicionesPreexistentes.add(new Vegano());
		
		receta.setCondicionesPreexistentes(condicionesPreexistentes);
		
		ArrayList<String> ingredientesCaros = new ArrayList<String>();
		ingredientesCaros.add("lechon");
		ingredientesCaros.add("lomo");
		ingredientesCaros.add("salmon");
		ingredientesCaros.add("alcaparras");
		
		receta.setIngredientesCaros(ingredientesCaros);
		Persistidor.getPersistidor().persistir(receta);
		return receta;
		}
	
	public BuilderReceta ingrediente(String nombre,int cantidad){
		receta.agregarIngrediente(new Ingrediente(nombre,cantidad));
		return this;
	}
	
	public BuilderReceta condimento(String nombre, int cantidad){
	receta.agregarCondimento(new Condimento(nombre,cantidad));
	return this;
	}
	
	public BuilderReceta setIngredientes(List<Ingrediente> ingredientes){
		receta.setIngredientes(ingredientes);
		return this;
	}
	
	public BuilderReceta setCondimentos(List<Condimento> condimentos){
		receta.setCondimentos(condimentos);
		return this;
	}
	
	public BuilderReceta subreceta(Receta subreceta){
	receta.agregarSubReceta(subreceta);
	return this;
	}

}
