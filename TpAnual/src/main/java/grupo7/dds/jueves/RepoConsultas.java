package grupo7.dds.jueves;

import grupo7.dds.jueves.CondicionesPreexistentes.Vegano;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "Repositorio_de_consultas")
public class RepoConsultas
{
	@Id
	private long idRepoConsultas;
	
	@Transient
	private static RepoConsultas repoConsultas = new RepoConsultas();
	
	@OneToMany
	private List<Consulta> consultas = new ArrayList<Consulta>();
	
	@Transient
	private ListadorDeRecetas listador;
	
	public RepoConsultas(){
		this.setListador(ListadorDeRecetas.getListador());
	}
	
	public static RepoConsultas getRepoConsultas()
	{
		return repoConsultas;
	}
	
	public static void setRepoConsultas(RepoConsultas repoConsultas)
	{
		RepoConsultas.repoConsultas = repoConsultas;
	}
	
	public List<Consulta> getConsultas()
	{
		return consultas;
	}
	
	public void setConsultas(ArrayList<Consulta> consultas)
	{
		this.consultas = consultas;
	}
	
	public ListadorDeRecetas getListador()
	{
		return listador;
	}

	public void setListador(ListadorDeRecetas listador)
	{
		this.listador = listador;
	}
	
	public void generarConsulta(Usuario autor, List<Receta> recetasConsultadas)
	{
		Consulta consulta = new Consulta(autor, recetasConsultadas);
		this.getConsultas().add(consulta);
		Persistidor.getPersistidor().persistir(consulta);
	}
	
	public List<Consulta> obtenerConsultasDeUsuario(Usuario usuario){
		return this.getConsultas().stream().filter(consulta -> consulta.getAutor()==usuario).collect(Collectors.toCollection(ArrayList::new));
	}
	public List<Receta> obtenerRecetasDeUsuario(Usuario usuario){
		List<Receta> recetasDeUsuario = new ArrayList<>();
		this.obtenerConsultasDeUsuario(usuario).forEach(consulta -> recetasDeUsuario.addAll(consulta.getRecetas()));
		return recetasDeUsuario;
	}
	
	public ArrayList<Integer> obtenerCantidadConsultas(List<Receta> list){
		return list.stream().map(receta -> receta.cantidadConsultas(this)).collect(Collectors.toCollection(ArrayList::new));
	}
	
	public Receta obtenerRecetaConMasConsultas(ArrayList<Integer> cantidadConsultas){
		Integer maximo = Collections.max(cantidadConsultas);
		return listador.listarTodas().stream().filter(receta -> receta.cantidadConsultas(this) == maximo).collect(Collectors.toCollection(ArrayList::new)).get(0);
	}
	
	public Comparator<Receta> getCriterioCantidadConsultas(){
		Comparator<Receta> cantidadConsultas = (receta1, receta2) -> Integer.compare(receta1.cantidadConsultas(this), receta2.cantidadConsultas(this));
		return cantidadConsultas;
	}
	
	public List<Receta> obtenerPrimerasRecetasMasConsultadas(List<Receta> recetas,int cantidad){
		return (recetas.stream().limit(cantidad).collect(Collectors.toCollection(ArrayList::new)));
	}
	
	public List<Receta> obtenerRecetasOrdenadasPorCantidadDeConsultas(){
		return listador.listarTodas().stream().sorted(getCriterioCantidadConsultas()).collect(Collectors.toCollection(ArrayList::new));
	}
	
	private ArrayList<Integer> obtenerCantidadConsultasPorSexo(List<Receta> list, String sexo)
	{
		return list.stream().map(receta -> receta.cantidadConsultasPorSexo(this, sexo)).collect(Collectors.toCollection(ArrayList::new));
	}
	
	public int cantidadDeRecetasConsultadasPorHora(LocalDateTime fecha){
		return (this.getConsultas().stream().filter(consulta -> (consulta.getFecha().getHour() == fecha.getHour() && consulta.getFecha().getDayOfYear() == fecha.getDayOfYear()))).collect(Collectors.toCollection(ArrayList::new)).size();
	}
	
	public Receta recetaMasConsultada(){
		ArrayList<Integer> cantidadConsultas = new ArrayList<Integer>();
		cantidadConsultas = this.obtenerCantidadConsultas(listador.listarTodas());
		return this.obtenerRecetaConMasConsultas(cantidadConsultas);
	}
	
	public Receta recetaMasConsultadaPorSexo(String sexo){
		ArrayList<Integer> cantidadConsultas = new ArrayList<Integer>();
		cantidadConsultas = this.obtenerCantidadConsultasPorSexo(listador.listarTodas(),sexo);
		return this.obtenerRecetaConMasConsultas(cantidadConsultas);
	}

	public List<Consulta> getConsultasPorSexo(String sexo)
	{
		return this.getConsultas().stream().filter(consulta -> consulta.getAutor().getSexo() == sexo).collect(Collectors.toCollection(ArrayList::new));
	}
	
	public int cantidadDeVeganosQueConsultaronRecetasDificiles(){
		List<Consulta> consultas = new ArrayList<Consulta>();
		consultas = this.filtrarConsultasConRecetasDificiles(this.filtrarConsultasPorVegano(this.getConsultas()));
		Set<Usuario> autores = (Set<Usuario>) new ArrayList<Usuario>();
		autores.addAll(consultas.stream().map(consulta -> consulta.getAutor()).collect(Collectors.toCollection(ArrayList::new)));
		return autores.size();
	}

	private List<Consulta> filtrarConsultasConRecetasDificiles(List<Consulta> consultasPorVegano)
	{
		return consultasPorVegano.stream().filter(consulta -> consulta.getRecetas().stream().anyMatch(receta -> receta.getDificultad() == "dificil")).collect(Collectors.toCollection(ArrayList::new));
	}

	private List<Consulta> filtrarConsultasPorVegano(List<Consulta> consultas)
	{
		return this.getConsultas().stream().filter(consulta -> consulta.getAutor().getCondicionesPreexistentes().contains(new Vegano())).collect(Collectors.toCollection(ArrayList::new));
	}

	public Consulta obtenerConsulta(Long consultaId){
		return (Consulta) Persistidor.getPersistidor().obtener(consultaId, new Consulta(new Usuario("nombre"),new ArrayList<Receta>()));
	}
	
}
