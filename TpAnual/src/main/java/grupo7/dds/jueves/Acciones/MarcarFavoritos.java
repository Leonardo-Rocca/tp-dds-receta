package grupo7.dds.jueves.Acciones;

import grupo7.dds.jueves.Receta;
import grupo7.dds.jueves.Usuario;
import grupo7.dds.jueves.Filtros.Filtro;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("M")
public class MarcarFavoritos extends Accion {
	
	@ManyToOne
	private Usuario usuario;
	
	@Transient //@OneToMany
	private List<Receta> resultadoBusqueda = new ArrayList<Receta>();
	
	public MarcarFavoritos setValores(Usuario usuario, Filtro criterio, List<Receta> resultado){
		this.setUsuario(usuario);
		this.setResultadoBusqueda(resultado);
		return this;
	}
	
	public void ejecutar(){
		this.getResultadoBusqueda().forEach(receta -> this.getUsuario().marcarComoFavorita(receta));
	}

	//Setters

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public void setResultadoBusqueda(List<Receta> resultadoBusqueda) {
		this.resultadoBusqueda = resultadoBusqueda;
	}
	
	//Getters
	
	public Usuario getUsuario() {
		return usuario;
	}

	public List<Receta> getResultadoBusqueda() {
		return resultadoBusqueda;
	}

}
