package grupo7.dds.jueves;

import grupo7.dds.jueves.Acciones.Accion;
import grupo7.dds.jueves.Filtros.Filtro;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class EjecutadorDeAcciones {
	
	@Id
	private long idEjecutadorDeAcciones;
	
	private static EjecutadorDeAcciones ejecutador = new EjecutadorDeAcciones();
	
	@OneToMany
	private List<Accion> acciones = new ArrayList<Accion>();
	
	public static EjecutadorDeAcciones getEjecutador(){
		return ejecutador;
	}
	
	public void ejecutarAcciones(){
		this.getAcciones().forEach(accion -> accion.ejecutar());
		this.getAcciones().clear();
	}
	
	public void agregarAcciones(List<Accion> acciones,Usuario usuario, Filtro criterios,List<Receta> recetasConsultadas){
		this.getAcciones()
				.addAll(acciones.stream()
								.map(accion -> accion.setValores(usuario,criterios,recetasConsultadas))
								.collect(Collectors.toCollection(ArrayList::new)));
	}
	
	public List<Accion> getAcciones(){
		return this.acciones;
	}
	
}
