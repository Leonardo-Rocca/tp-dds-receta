package grupo7.dds.jueves;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CascadeType;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

@Entity
@Table(name = "Grupos")
public class Grupo implements Sugerible
{
	@Id
	@GeneratedValue
	private long idGrupo;
	
	private String nombre;
	
	private ArrayList<String> preferenciasAlimenticias = new ArrayList<String>();
	
	@ManyToMany
	private List<Usuario> miembros = new ArrayList<Usuario>();
	
	//Setters
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void setPreferenciasAlimenticias(ArrayList<String> preferenciasAlimenticias) {
		this.preferenciasAlimenticias = preferenciasAlimenticias;
	}

	public void setMiembros(List<Usuario> miembros) {
		this.miembros = miembros;
	}
	
	//Getters
	
	public long getGrupoId(){
		return idGrupo;
	}
	
	
	public String getNombre() {
		return nombre;
	}
	
	public ArrayList<String> getPreferenciasAlimenticias() {
		return preferenciasAlimenticias;
	}
	
	public List<Usuario> getMiembros() {
		return miembros;
	}
	
	//---
	
	public Grupo(String nombre){
		this.nombre = nombre;
	}
	
	public void agregarMiembro(Usuario usuario){
		this.getMiembros().add(usuario);
		usuario.fueAgregadoAGrupo(this);
	}
	
	public void agregarPreferenciaAlimenticia(String preferencia){
		this.preferenciasAlimenticias.add(preferencia);
	}
	
	public boolean leGusta(Receta receta){
		 return(receta.getIngredientes().stream().anyMatch(ingrediente -> this.getPreferenciasAlimenticias().contains(ingrediente.getNombre()))
				 || (this.getPreferenciasAlimenticias().stream().anyMatch(preferencia -> receta.getNombre().contains(preferencia))));
	}
	
	public boolean esAdecuadoPara(Receta receta){
		return (this.getMiembros().stream().allMatch(miembro -> miembro.esAdecuadoPara(receta)));
	}
	
	public void recibirSugerencia(Receta receta){
		//Averiguar como implementar
	}
	
	public List<Receta> recetasDelGrupo(){
		List<Receta> recetasDelGrupo = new ArrayList<Receta>();
		this.getMiembros().forEach(miembro -> recetasDelGrupo.addAll(miembro.getRecetasPropias()));
		return recetasDelGrupo;
	}
	
	public void persistir(){
		Persistidor.getPersistidor().persistir(this);
	}
	
	public Grupo obtenerGrupo(Long grupoId){
		return (Grupo) Persistidor.getPersistidor().obtener(grupoId, this);
	}
	
}