package grupo7.dds.jueves;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import queComemos.entrega3.repositorio.BusquedaRecetas;

@Entity
public class ListadorDeRecetas{
	
	@Id
	private long idListadorDeRecetas;
	
	@Transient
	private static ListadorDeRecetas listador = new ListadorDeRecetas();
	
	@OneToMany
	private List<Receta> recetas = new ArrayList<Receta>();
	
	public static ListadorDeRecetas getListador(){
		return listador;
	}
	
	/*public ListadorDeRecetas(){
		AdapterRepoRecetasExternas adapterRepoRecetasExternas = new AdapterRepoRecetasExternas();
		 List<Receta> recetasExternas = new ArrayList<Receta>();
		 recetasExternas = adapterRepoRecetasExternas.obtenerRecetas(new BusquedaRecetas());
		 recetasExternas.forEach(receta -> this.agregarReceta(receta));
	}*/
	
	
	public List<Receta> getRecetasGenerales(){
			return this.recetas.stream()
					.filter(receta -> receta.getAutor() == null)
					.collect(Collectors.toCollection(ArrayList::new));
		}
	
	public void sugerirSiEsPosible(Receta receta,Sugerible sugerible){
		if (receta.esRecomendablePara(sugerible))
			sugerible.recibirSugerencia(receta);
	}
	
	public void agregarReceta(Receta receta){
		this.recetas.add(receta);
		//Persistidor.getPersistidor().persistir(receta);
	}
	
	public void quitarReceta(Receta receta){
		this.recetas.remove(receta);
	}
	
	public List<Receta> listarTodas(){
		 AdapterRepoRecetasExternas adapterRepoRecetasExternas = new AdapterRepoRecetasExternas();
		 List<Receta> recetasInternasYExternas = new ArrayList<Receta>();
		 recetasInternasYExternas.addAll(this.recetas);
		 recetasInternasYExternas.addAll(adapterRepoRecetasExternas.obtenerRecetas(new BusquedaRecetas()));
		 recetasInternasYExternas.forEach(receta -> Persistidor.getPersistidor().persistir(receta));
		 return recetasInternasYExternas;
	}

	public List<Receta> recetasQuePuedeVer(Usuario usuario) {
		return (this.listarTodas().stream()
				.filter(receta -> this.tieneAcceso(usuario,receta))
				.collect(Collectors.toCollection(ArrayList::new)));
	}

	private boolean tieneAcceso(Usuario usuario, Receta receta) {
		return (receta.getAutor()==usuario || receta.getAutor()==null || usuario.recetasCompartidasPorSusGrupos().contains(receta));
	}

	public List<Receta> recetasPropiasDe(Usuario usuario) {
		return (this.recetas.stream()
				.filter(receta -> receta.getAutor() == usuario)
				.collect(Collectors.toCollection(ArrayList::new)));
	}
	
	public void limpiar(){
		this.recetas.clear();
	}
	
	public Receta obtenerReceta(long recetaId){
		return (Receta) Persistidor.getPersistidor().obtener(recetaId, new Receta()); 
	}
	
}
