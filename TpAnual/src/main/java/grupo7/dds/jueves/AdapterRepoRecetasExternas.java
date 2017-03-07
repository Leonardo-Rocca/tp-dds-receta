package grupo7.dds.jueves;

import queComemos.entrega3.dominio.Receta;

import java.util.ArrayList;
import java.util.List;

import queComemos.entrega3.repositorio.BusquedaRecetas;
import queComemos.entrega3.repositorio.RepoRecetas;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class AdapterRepoRecetasExternas {
	private RepoRecetas repositorioExterno;
	
	public AdapterRepoRecetasExternas(){
		RepoRecetas repositorioExterno = new RepoRecetas();
	    this.repositorioExterno = repositorioExterno;
	}
	
	public List<grupo7.dds.jueves.Receta> obtenerRecetas(BusquedaRecetas busquedaRecetas){
		String recetasExternas = repositorioExterno.getRecetas(busquedaRecetas);
		List<queComemos.entrega3.dominio.Receta> mockRecetas = new ArrayList<queComemos.entrega3.dominio.Receta>();
		mockRecetas = new Gson().fromJson(recetasExternas,new TypeToken<ArrayList<queComemos.entrega3.dominio.Receta>>(){}.getType());
		return (this.adaptarRecetas(mockRecetas));
	}
	
	public List<grupo7.dds.jueves.Receta> adaptarRecetas(List<Receta> mockReceta) {
		List<grupo7.dds.jueves.Receta> recetas = new ArrayList<grupo7.dds.jueves.Receta>();
		mockReceta.forEach(receta -> recetas.add(new BuilderReceta(receta.getNombre()).dificultad(receta.getDificultadReceta().toString()).build()));
		return recetas;
	}
}
