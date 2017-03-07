package grupo7.dds.jueves.Acciones;

import grupo7.dds.jueves.Receta;
import grupo7.dds.jueves.Usuario;
import grupo7.dds.jueves.Filtros.Filtro;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.apache.commons.logging.*;

@Entity
@DiscriminatorValue("L")
public class Loguear extends Accion {
	
	@Transient //@OneToMany
	private List<Receta> resultadoBusqueda = new ArrayList<Receta>();
	
	@Transient
	private Log log = LogFactory.getLog(Loguear.class);
	
	public Loguear setValores(Usuario usuario, Filtro criterio, List<Receta> resultado){
		this.setResultadoBusqueda(resultado);
		return this;
	}
	
	public void ejecutar(){
		if (this.getResultadoBusqueda().size() > 100){
			this.getResultadoBusqueda().forEach(receta -> log.info("Nombre receta: " + receta.getNombre()));
		}
	}

	//Getter y Setter
	
	public List<Receta> getResultadoBusqueda() {
		return resultadoBusqueda;
	}

	public void setResultadoBusqueda(List<Receta> resultado) {
		this.resultadoBusqueda = resultado;
	}
	
}
