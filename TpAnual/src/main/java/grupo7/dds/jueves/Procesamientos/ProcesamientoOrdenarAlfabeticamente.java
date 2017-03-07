package grupo7.dds.jueves.Procesamientos;
import grupo7.dds.jueves.Receta;
import grupo7.dds.jueves.Usuario;
import grupo7.dds.jueves.Filtros.Filtro;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProcesamientoOrdenarAlfabeticamente implements Filtro {

	public Comparator<Receta> getCriterio(){
		Comparator<Receta> alfabeticamente = (receta1, receta2) -> receta1.getNombre().compareTo(receta2.getNombre());
		return alfabeticamente;
	}
		
	public List<Receta> filtrar(List<Receta> recetas,Usuario usuario){
		return recetas.stream().sorted(getCriterio()).collect(Collectors.toCollection(ArrayList::new));
	}
	
}
