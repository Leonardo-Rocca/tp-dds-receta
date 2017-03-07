package grupo7.dds.jueves;

import grupo7.dds.jueves.CondicionesPreexistentes.CondicionPreexistente;
import grupo7.dds.jueves.Excepciones.CantidadDeCaloriasInvalidasException;










import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "Recetas")
public class Receta{
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long idReceta;

	private String nombre;
	
	@OneToMany(cascade = {CascadeType.ALL})
	private List<Ingrediente> ingredientes = new ArrayList<Ingrediente>();
	
	@OneToMany(cascade = {CascadeType.ALL})
	private List<Condimento> condimentos = new ArrayList<Condimento>();
	
	private String preparacion;
	
	@OneToMany(cascade = {CascadeType.ALL})
	private List<CondicionPreexistente> condicionesPreexistentes = new ArrayList<CondicionPreexistente>();

	private int calorias;

	private String dificultad;

	private String temporada;

	// <---- Ver esto, bucle. 
	@ManyToMany
	private List<Receta> subRecetas = new ArrayList<Receta>();
	
	private ArrayList<String> ingredientesCaros = new ArrayList<String>();
	@ManyToOne
	private Usuario autor; 
			
	//Setters
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void setPreparacion(String preparacion) {
		this.preparacion = preparacion;
	}
	
	public void setDificultad(String dificultad) {
		this.dificultad = dificultad;
	}
	
	public void setTemporada(String temporada) {
		this.temporada = temporada;
	}
	
	public void setIngredientes(List<Ingrediente> ingredientes) { 	
		this.ingredientes = ingredientes; 	
	}
	
	public void setCondimentos(List<Condimento> condimentos) { 	
		this.condimentos = condimentos; 	
	}
	
	public void setCondicionesPreexistentes(List<CondicionPreexistente> condiciones){
		this.condicionesPreexistentes = condiciones;
	}
	
	public void setIngredientesCaros(ArrayList<String> ingredientes){
		this.ingredientesCaros = ingredientes;
	}
	
	public void setAutor(Usuario autor) {
		this.autor = autor;
	}
	
	//Para probar test
	public int getCalorias(int c) {
		return this.calorias = c;
	}
	
	//Getters
	
	public String getNombre(){
		return this.nombre;
	}
	
	public long getIdReceta(){
		return idReceta;
	}
		
	public List<Ingrediente> getIngredientes(){
		List<Ingrediente> ingredientes = new ArrayList<Ingrediente>();
		ingredientes.addAll(this.ingredientes);
		this.getSubRecetas().forEach(receta -> ingredientes.addAll(receta.getIngredientes()));
		return ingredientes;
	}
	
	public List<Condimento> getCondimentos(){
		return this.condimentos;
	}
		
	public String getPreparacion(){
		return this.preparacion;
	}

	public int getCalorias() {
		return this.calorias;
	}
	
	public String getDificultad(){
		return this.dificultad;
	}
	public String getTemporada(){
		return this.temporada;
	}
	
	public List<Receta> getSubRecetas() {
		return subRecetas;
	}
	
	public List<CondicionPreexistente> getCondicionesPreexistentes(){
		return this.condicionesPreexistentes;  //Acá debería devolver para que condiciones es inadecuada la receta
											  //adaptando el metodo que esta mas abajo llamado paraQueCondicionesPreexistentesEsInadecuado()
	} 
	
	public ArrayList<String> getIngredientesCaros(){
		return this.ingredientesCaros;
	}

	public Usuario getAutor() {
		return autor;
	}
	
	//Constructor
	
	public void validar(){
		tieneAlMenosUnIngrediente();
		totalDeCaloriasValidas();
	}
	
	public boolean tieneAlMenosUnIngrediente(){
		return (this.ingredientes.size() > 0);
	}
	
	public void totalDeCaloriasValidas(){
		if(this.cantidadCaloriasTotales() < 10 || this.cantidadCaloriasTotales() > 5000)
			throw new CantidadDeCaloriasInvalidasException("La receta no tiene una cantidad de calorías válida");
	}
	
	public void agregarIngrediente(Ingrediente ingrediente) {
		this.ingredientes.add(ingrediente);
	}
	
	public void agregarCondimento(Condimento condimento) {
		this.condimentos.add(condimento);
	}
	
	public void agregarSubReceta(Receta receta) {
		this.subRecetas.add(receta);
	}
	
	//VER COMO CALCULAR
	public int cantidadCalorias(){
		return 300;
	}
	
	public int cantidadCaloriasTotales(){
		return (this.cantidadCalorias() + this.getSubRecetas().stream().mapToInt(receta -> receta.cantidadCalorias()).sum());
	}
	
	public List<CondicionPreexistente> getParaQueCondicionesPreexistentesEsInadecuado(){
		List<CondicionPreexistente> condiciones = new ArrayList<CondicionPreexistente>();
		condiciones = this.getCondicionesPreexistentes().stream().filter(condicion -> condicion.esInadecuadoParaReceta(this)).collect(Collectors.toCollection(ArrayList::new));
		return condiciones;
	}
	
	public boolean esRecomendablePara(Sugerible sugerible){
		return (sugerible.leGusta(this) && sugerible.esAdecuadoPara(this));
	}

	public boolean esCara(){
		return (this.getIngredientes().stream().anyMatch(ingrediente -> this.getIngredientesCaros().contains(ingrediente.getNombre())));
	}

	public int cantidadConsultas(RepoConsultas repoConsultas){
		
		return contarConsultas(repoConsultas.getConsultas());
		
	}

	public int cantidadConsultasPorSexo(RepoConsultas repoConsultas, String sexo)
	{
		return contarConsultas(repoConsultas.getConsultasPorSexo(sexo));
	}

	public int contarConsultas(List<Consulta> consultas){
		return consultas.stream().map(consulta -> consulta.tieneReceta(this)).filter(numero -> numero == 1).collect(Collectors.toCollection(ArrayList::new)).size();
	}
	
}
