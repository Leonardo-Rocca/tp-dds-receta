package grupo7.dds.jueves.Filtros;

import grupo7.dds.jueves.Receta;
import grupo7.dds.jueves.Usuario;

import java.util.ArrayList;

public abstract class TipoDeFiltro implements Filtro{

	private Filtro filtro;
	
	public TipoDeFiltro(Filtro filtro){
		this.filtro = filtro;
	}
	
	public ArrayList<Receta> filtrar(ArrayList<Receta> recetas,Usuario usuario){
		return recetas;
	}
	
	public Filtro getFiltro(){
		return filtro;
	}
	

}
