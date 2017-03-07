package grupo7.dds.jueves.Filtros;

import grupo7.dds.jueves.Receta;
import grupo7.dds.jueves.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FiltroExcesoDeCalorias extends TipoDeFiltro {

	public FiltroExcesoDeCalorias(Filtro filtro){
		super(filtro);
	}
	
	@Override
	public List<Receta> filtrar(List<Receta> recetas,Usuario usuario){
		if (usuario.tieneSobrepeso()){
			List<Receta> recetasFiltradas = new ArrayList<Receta>();
			recetasFiltradas = recetas.stream()
					.filter(receta -> receta.cantidadCaloriasTotales() <= 500).collect(Collectors.toCollection(ArrayList::new));
			return (this.getFiltro().filtrar(recetasFiltradas,usuario));
		}
		return (this.getFiltro().filtrar(recetas,usuario));		
	}
	
}
