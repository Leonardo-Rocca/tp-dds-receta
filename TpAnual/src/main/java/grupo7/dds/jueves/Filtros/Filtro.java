package grupo7.dds.jueves.Filtros;

import grupo7.dds.jueves.Receta;
import grupo7.dds.jueves.Usuario;

import java.util.ArrayList;
import java.util.List;

public interface Filtro {

	public List<Receta> filtrar(List<Receta> list,Usuario usuario);
	
}
