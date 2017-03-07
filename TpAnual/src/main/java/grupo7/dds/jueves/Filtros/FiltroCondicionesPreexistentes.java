package grupo7.dds.jueves.Filtros;

import grupo7.dds.jueves.Receta;
import grupo7.dds.jueves.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FiltroCondicionesPreexistentes extends TipoDeFiltro {

	public FiltroCondicionesPreexistentes(Filtro filtro){
		super(filtro);
	}
	
	@Override
	public List<Receta> filtrar(List<Receta> recetas,Usuario usuario){
		List<Receta> recetasFiltradas = new ArrayList<Receta>();
		recetasFiltradas = recetas.stream()
				.filter(receta -> usuario.esAdecuadoPara(receta)).collect(Collectors.toCollection(ArrayList::new));
		return (this.getFiltro().filtrar(recetasFiltradas,usuario));
	}
}
