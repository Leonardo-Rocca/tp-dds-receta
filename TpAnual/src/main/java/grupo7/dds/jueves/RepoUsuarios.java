package grupo7.dds.jueves;

import grupo7.dds.jueves.Excepciones.NoSolicitoIncorporacionException;
import grupo7.dds.jueves.Filtros.Filtro;
import grupo7.dds.jueves.Filtros.FiltroCaro;
import grupo7.dds.jueves.Filtros.FiltroExcesoDeCalorias;
import grupo7.dds.jueves.Procesamientos.NoProcesamiento;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

@Entity
@Table(name = "Repositorio_de_Usuarios")
public class RepoUsuarios implements WithGlobalEntityManager, TransactionalOps {
	
	@Id
	private long idRepoUsuarios;
	
	@OneToMany
	private List<Usuario> usuarios = new ArrayList<Usuario>();
	
	@OneToMany
	private List<Usuario> solicitudesPendientes = new ArrayList<Usuario>();
	
	@Transient
	private static RepoUsuarios repoUsuarios = new RepoUsuarios();
	
	public static RepoUsuarios getRepoUsuarios() {
		return repoUsuarios;
	}
		
	public List<Usuario> getUsuarios()
	{
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios)
	{
		this.usuarios = usuarios;
	}
	
	public void agregarASolicitudesPendientes(Usuario usuario){
		this.solicitudesPendientes.add(usuario);
	}
	
	public List<Usuario> getSolicitudesPendientes()
	{
		return solicitudesPendientes;
	}

	public void add(Usuario usuario){
		this.getUsuarios().add(usuario);
	}
	
	public void remove(Usuario usuario){
		this.getUsuarios().remove(usuario);
	}
	
	public void update(Usuario usuario){
		this.remove(get(usuario));
		this.add(usuario);
	}

	public Usuario get(Usuario usuarioPrototipo)	{
		return (this.getUsuarios().stream().filter(user -> user.getNombre() == usuarioPrototipo.getNombre()).collect(Collectors.toCollection(ArrayList::new)).get(0));
	}
	
	public List<Usuario> list(Usuario usuarioPrototipo){
		if(usuarioPrototipo.getCondicionesPreexistentes().size() == 0) {
			return (this.buscarUsuariosQueContenganMismoNombre(usuarioPrototipo));
		} else { 
			return (this.buscarUsuariosQueContenganMismoNombre(usuarioPrototipo).stream().filter(user -> user.subsanaUnaListaDeCondicionesPreexistentes(usuarioPrototipo.getCondicionesPreexistentes())).collect(Collectors.toCollection(ArrayList::new)));
		}
	}
	
	public List<Usuario> buscarUsuariosQueContenganMismoNombre(Usuario usuarioPrototipo){
		return (this.getUsuarios().stream().filter(user -> user.getNombre().contains(usuarioPrototipo.getNombre())).collect(Collectors.toCollection(ArrayList::new)));
	}
	
	public void solicitarIncorporacion(Usuario usuario){
		this.agregarASolicitudesPendientes(usuario);
	}
	
	public void aprobarSolicitud(Usuario usuario){
		this.validarQueEsteLaSolicitud(usuario);
		this.getSolicitudesPendientes().remove(usuario);
		this.getUsuarios().add(usuario);
		Persistidor.getPersistidor().persistir(usuario);
	}
	
	public void rechazarSolicitud(Usuario usuario, String motivo){
		this.validarQueEsteLaSolicitud(usuario);
		this.getSolicitudesPendientes().remove(usuario);
	}
	
	public void validarQueEsteLaSolicitud(Usuario usuario){
			if(!this.getSolicitudesPendientes().contains(usuario)){
				throw new NoSolicitoIncorporacionException("El usuario no se encuentra en solicitudes pendientes");
		}
	}
	
	public Usuario obtenerUsuario(Long usuarioId){
		return (Usuario) Persistidor.getPersistidor().obtener(usuarioId, new Usuario("nombre"));
	}
	
	public void limpiar(){
		this.usuarios.clear();
	}

	// Usuario de prueba harcodeado
	public Usuario get(){
		
		Usuario fedeC = new BuilderUsuario("Federico Catala")
		.peso(90)
		.altura(1.96)
		.sexo("masculino")
		.fechaDeNacimiento(LocalDate.of(1995,03,16))
		.rutina(Rutina.MEDIANO)
		.build();
		
		/*Receta nuez = new BuilderReceta("nuez moscada")
				.preparacion("mezcla todo")
				.dificultad("muy dificil")
				.temporada("verano")
				.ingrediente("nuez",2)
				.build();
		
		Receta pure = new BuilderReceta("pure")
				.preparacion("mezcla todo")
				.dificultad("muy dificil")
				.temporada("verano")
				.ingrediente("papa",2)
				.subreceta(nuez)
				.build();
		

		Receta sushi = new BuilderReceta("sushi")
				.preparacion("cortar salmon")
				.dificultad("dificil")
				.temporada("verano")
				.ingrediente("salmon",500)
				.build();
		
		Receta milanesa = new BuilderReceta("milanesa con pure")
				.preparacion("mezcla todo")
				.dificultad("muy dificil")
				.temporada("verano")
				.ingrediente("carne",3).condimento("sal", 5000)
				.subreceta(pure)
				.build();
		
		fedeC.agregarRecetaPropia(milanesa);
		fedeC.agregarPreferenciaAlimenticia("sal");
		fedeC.agregarPreferenciaAlimenticia("azucar");*/
		
		Filtro filtro = new FiltroExcesoDeCalorias(new NoProcesamiento());
		fedeC.consultarRecetas(filtro);
		
		//fedeC.marcarComoFavorita(pure);
		
		return fedeC;
	}

}
