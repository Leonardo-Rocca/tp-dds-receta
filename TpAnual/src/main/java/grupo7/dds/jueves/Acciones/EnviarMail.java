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
@DiscriminatorValue("E")
public class EnviarMail extends Accion {
	
	@ManyToOne
	private Usuario usuario;
	
	@Transient //@OneToOne
	private Filtro criterioBusqueda;
	
	@Transient //@OneToMany
	private List<Receta> resultadoBusqueda = new ArrayList<Receta>();
	
	public EnviarMail setValores(Usuario usuario, Filtro criterio, List<Receta> resultado){
		this.setUsuario(usuario);
		this.setCriterioBusqueda(criterio);
		this.setResultadoBusqueda(resultado);
		return this;
	}
	
	public void ejecutar(){
		//Enviar mail con criterio y resultados al usuario correspondiente
	}
	
	//Setters
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public void setCriterioBusqueda(Filtro criterioBusqueda) {
		this.criterioBusqueda = criterioBusqueda;
	}
	
	public void setResultadoBusqueda(List<Receta> resultadoBusqueda) {
		this.resultadoBusqueda = resultadoBusqueda;
	}
	
	//Getters

	public Usuario getUsuario() {
		return usuario;
	}

	public Filtro getCriterioBusqueda() {
		return criterioBusqueda;
	}
	
	public List<Receta> getResultadoBusqueda() {
		return resultadoBusqueda;
	}
	
}
