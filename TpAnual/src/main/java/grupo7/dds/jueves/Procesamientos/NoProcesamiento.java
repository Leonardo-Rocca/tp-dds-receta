package grupo7.dds.jueves.Procesamientos;

import grupo7.dds.jueves.Receta;
import grupo7.dds.jueves.Usuario;
import grupo7.dds.jueves.Filtros.Filtro;

import java.util.ArrayList;
import java.util.List;

public class NoProcesamiento implements Filtro{

	public List<Receta> filtrar(List<Receta> recetas,Usuario usuario){
		return recetas;
	}
	
}
