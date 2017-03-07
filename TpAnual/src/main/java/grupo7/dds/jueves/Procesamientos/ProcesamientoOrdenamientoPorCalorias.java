package grupo7.dds.jueves.Procesamientos;
import grupo7.dds.jueves.Receta;
import grupo7.dds.jueves.Usuario;
import grupo7.dds.jueves.Filtros.Filtro;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProcesamientoOrdenamientoPorCalorias implements Filtro{

	public Comparator<Receta> getCriterio(){
		Comparator<Receta> calorias = (receta1, receta2) -> Integer.compare(receta1.getCalorias(), receta2.getCalorias());
		return calorias;
	}
		
	public List<Receta> filtrar(List<Receta> recetas, Usuario usuario){
		return recetas.stream().sorted(getCriterio()).collect(Collectors.toCollection(ArrayList::new));
	}
		
}
