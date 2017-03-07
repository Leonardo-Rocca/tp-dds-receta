package grupo7.dds.jueves.Procesamientos;

import grupo7.dds.jueves.Receta;
import grupo7.dds.jueves.Usuario;
import grupo7.dds.jueves.Filtros.Filtro;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProcesamientoConsiderarSoloResultadosPares implements Filtro {

	public List<Receta> filtrar(List<Receta> recetas,Usuario usuario){
		return (recetas.stream().filter(receta -> (recetas.indexOf(receta) % 2) == 0).collect(Collectors.toCollection(ArrayList::new)));
	}
	
}
