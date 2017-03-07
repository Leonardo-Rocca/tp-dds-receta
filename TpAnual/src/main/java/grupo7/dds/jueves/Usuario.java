package grupo7.dds.jueves;

import grupo7.dds.jueves.Acciones.Accion;
import grupo7.dds.jueves.Acciones.EnviarMail;
import grupo7.dds.jueves.Acciones.Loguear;
import grupo7.dds.jueves.Acciones.MarcarFavoritos;
import grupo7.dds.jueves.CondicionesPreexistentes.CondicionPreexistente;
import grupo7.dds.jueves.Excepciones.CondicionInvalidaParaUsuarioException;
import grupo7.dds.jueves.Excepciones.FechaInvalidaException;
import grupo7.dds.jueves.Excepciones.NombreInvalidoException;
import grupo7.dds.jueves.Filtros.Filtro;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "Usuarios")
public class Usuario implements Sugerible
{
	@Id
	@GeneratedValue
	private long idUsuario;
	
	private String nombre;
	private String sexo;
	private LocalDate fechaDeNacimiento;
	private int peso;
	private double altura;
	private ArrayList<String> preferenciasAlimenticias = new ArrayList<String>();
	private ArrayList<String> comidasQueLeDisgustan = new ArrayList<String>();
	
	@OneToMany(cascade = {CascadeType.ALL})
	private List<CondicionPreexistente> condicionesPreexistentes = new ArrayList<CondicionPreexistente>();
	
	@Enumerated
	private Rutina rutina;
	
	@ManyToOne(cascade = {CascadeType.ALL})
	private ListadorDeRecetas listador;

	@ManyToOne(cascade = {CascadeType.ALL})
	private RepoConsultas repoConsultas;
	
	@ManyToMany
	private List<Receta> recetasFavoritas = new ArrayList<Receta>();
	           
	@ManyToMany
	private List<Grupo> gruposALosQuePertenece = new ArrayList<Grupo>();
	
	@OneToMany(cascade = {CascadeType.ALL})
	private List<Accion> acciones = new ArrayList<Accion>();
	
	@ManyToOne(cascade = {CascadeType.ALL})
	private EjecutadorDeAcciones ejecutador;
	
	//Setters
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public void setFechaDeNacimiento(LocalDate fechaDeNacimiento) {
		this.fechaDeNacimiento = fechaDeNacimiento;
	}

	public void setRutina(Rutina rutina) {
		this.rutina = rutina;
	}
	
	public void setPeso(int peso)
	{
		this.peso = peso;
	}

	public void setAltura(Double altura)
	{
		this.altura = altura;
	}
	
	public void setListadorDeRecetas(ListadorDeRecetas listador) {
		this.listador = listador;
	}
	
	public void setCondicionesPreexistentes(List<CondicionPreexistente> condicionesPreexistentes3) {
		this.condicionesPreexistentes = condicionesPreexistentes3;
	}
	
	public void setRepoConsultas(RepoConsultas repoConsultas){
		this.repoConsultas = repoConsultas;
	}
	
	public void setEjecutadorDeAcciones(EjecutadorDeAcciones ejecutador){
		this.ejecutador = ejecutador;
	}
	
	//Getters
	
	public List<Receta> getRecetasPropias(){
		return this.getListador().recetasPropiasDe(this);
	}
	
	public String getNombre() {
		return nombre;
	}

	public String getSexo() {
		return sexo;
	}

	public LocalDate getFechaDeNacimiento() {
		return fechaDeNacimiento;
	}
	
	public Rutina getRutina() {
		return rutina;
	}

	public int getPeso() {
		return peso;
	}

	public double getAltura() {
		return altura;
	}

	public ArrayList<String> getComidasQueLeDisgustan() {
		return comidasQueLeDisgustan;
	}

	public List<CondicionPreexistente> getCondicionesPreexistentes() {
		return condicionesPreexistentes;
	}

	
	public ArrayList<String> getPreferenciasAlimenticias() {
			
		return this.preferenciasAlimenticias;
	}
	
	public List<Grupo> getGruposALosQuePertenece(){
		return this.gruposALosQuePertenece;
	}
	
	public ListadorDeRecetas getListador(){
		return this.listador;
	}
	
	public List<Receta> getRecetasFavoritas(){
		return this.recetasFavoritas;
	}
	
	public RepoConsultas getRepoConsultas(){
		return repoConsultas;
	}
	
	public EjecutadorDeAcciones getEjecutadorDeAcciones(){
		return ejecutador;
	}
	
	public List<Accion> getAcciones(){
		return acciones;
	}
	
	public long getUsuarioId(){
		return idUsuario;
	}

		
	// Agregar a las listas
		
		public void agregarCondicionPreexistente(CondicionPreexistente condicion){
			if(condicion.esValidaPara(this)){
				this.condicionesPreexistentes.add(condicion);
			}
			else{
				throw new CondicionInvalidaParaUsuarioException("La condición no es válida para este usuario");
			}
		}
				
		public void agregarPreferenciaAlimenticia(String alimento) {
			this.preferenciasAlimenticias.add(alimento);
		}
		
		public void agregarComidaQueLeDisgusta(String comida){
			this.comidasQueLeDisgustan.add(comida);
		}
		
		public void agregarRecetaPropia(Receta receta){
			receta.setAutor(this);
			this.getListador().agregarReceta(receta);
		}
		
	// Constructor	
				
	public Usuario(String nombre,int peso,double altura,LocalDate fechaDeNacimiento,Rutina rutina) {
		this.setNombre(nombre);
		this.setPeso(peso);
		this.setAltura(altura);
		this.setFechaDeNacimiento(fechaDeNacimiento);
		this.setRutina(rutina);
		this.esValido();
		this.setListadorDeRecetas(ListadorDeRecetas.getListador());
		this.setRepoConsultas(RepoConsultas.getRepoConsultas());
		this.setEjecutadorDeAcciones(EjecutadorDeAcciones.getEjecutador());
		this.getAcciones().add(new Loguear());
	}
	
	public Usuario(String nombre) {
		this.setNombre(nombre);
	}
	
	public Usuario(String nombre,List<CondicionPreexistente> condicionesPreexistentes) {
		this.setNombre(nombre);
		this.setCondicionesPreexistentes(condicionesPreexistentes);;
	}
	
	//Validaciones
	
	public void esValido(){
		this.validarNombre();
		this.validarFechaDeNacimiento();
	}
	
	public void validarFechaDeNacimiento()
	{
		if (this.fechaDeNacimiento.compareTo(LocalDate.now()) > 0){
			throw new FechaInvalidaException("La fecha ingresada es posterior al día de hoy");
		}
	}

	public void validarNombre() {
		if(this.nombre.length() < 4 ){
			throw new NombreInvalidoException("El nombre de usuario tiene menos de 4 caracteres"); }
	}
	
	//Otros tests

	public Double calcularIMC()	{
		return (this.getPeso() / (this.getAltura() * this.getAltura()));
	}
		
	public boolean imcEnRangoSaludable(){
		Double imc = this.calcularIMC();
		return (imc > 18 && imc < 30);
		}
		
	public boolean subsanaCondicionesPreexistentes()	{
		return (this.getCondicionesPreexistentes().stream().allMatch(condicion -> condicion.subsanaCondicionPreexistente(this)));
	}
	
	public boolean sigueUnaRutinaSaludable (){
		return (this.imcEnRangoSaludable() && this.subsanaCondicionesPreexistentes());
	}
	
	public boolean leGustaLaFruta(){
		return this.getPreferenciasAlimenticias().contains("fruta");
	}
	
	public boolean tieneRutinaIntensiva() {
		return (this.getRutina().name().toString() == "INTENSIVA");
	}
	
	public boolean tieneRutinaActiva() {
		return (this.getRutina().getTipoRutina() == "Activa");
	}

	public boolean pesaMenosDe70Kg(){
		return (this.getPeso() < 70);
	}
	
	public boolean tieneAlMenosUnaPreferencia(){
		return (this.getPreferenciasAlimenticias().size() > 0);
	}
	
	public boolean tieneEnRecetaPropia(Receta receta){
		return getRecetasPropias().contains(receta);
	}
	
	public boolean tieneEnRecetaGeneral(Receta receta){
		return (this.getListador().getRecetasGenerales().contains(receta));
	}
	
	public boolean puedeVer(Receta receta){
		return (this.recetasALasQueTieneAcceso().contains(receta));
	}
	
	public boolean puedeModificarUnaReceta(Receta receta){
		return puedeVer(receta);
	}
	
	public boolean contieneAlgunaPreferenciaInvalida(ArrayList<String> preferenciasInvalidas){
		preferenciasInvalidas.retainAll(this.getPreferenciasAlimenticias());
		return (preferenciasInvalidas.size() > 0);
	}
	
 
	public void modificarReceta(Receta receta, String nombre, List<Ingrediente> ingredientes, List<Condimento> condimentos, String preparacion, String dificultad, String temporada) {
		if(this.puedeModificarUnaReceta(receta)) {
			Receta recetaModificada = new BuilderReceta(nombre)
			                          .preparacion(preparacion).dificultad(dificultad)
			                          .temporada(temporada).setIngredientes(ingredientes)
			                          .setCondimentos(condimentos).build();
			this.agregarRecetaPropia(recetaModificada);
		}
		
	}
	
	public boolean leGusta(Receta receta){
		return (receta.getIngredientes().stream().noneMatch(ingrediente -> this.getComidasQueLeDisgustan().contains(ingrediente.getNombre())));
	}
	
	public boolean esAdecuadoPara(Receta receta){
		return (this.getCondicionesPreexistentes().stream().noneMatch(condicion -> condicion.esInadecuadoParaReceta(receta)));
	}
	
	public void recibirSugerencia(Receta receta){
		//Averiguar como implementar
	}
	
	public void marcarComoFavorita(Receta receta){
		if(!this.esFavorita(receta))
			this.recetasFavoritas.add(receta);
	}
	
	public void desmarcarFavorita(Receta receta){
		if(this.esFavorita(receta))
			this.recetasFavoritas.remove(receta);
	}
	
	public boolean esFavorita(Receta receta){
		return(this.getRecetasFavoritas().contains(receta));
	}
	
	public void fueAgregadoAGrupo(Grupo grupo){
		this.gruposALosQuePertenece.add(grupo);
	}
	
	public List<Receta> recetasCompartidasPorSusGrupos(){
		List<Receta> recetasCompartidas = new ArrayList<Receta>();
		this.getGruposALosQuePertenece().forEach(grupo -> recetasCompartidas.addAll(grupo.recetasDelGrupo()));
		return recetasCompartidas;
	}
	
	public List<Receta> recetasALasQueTieneAcceso(){
		return (this.getListador().recetasQuePuedeVer(this));
	}
	
	public List<Receta> consultarRecetas(Filtro filtro){
		List<Receta> recetasConsultadas = new ArrayList<Receta>();
		recetasConsultadas = filtro.filtrar(this.recetasALasQueTieneAcceso(),this);
		repoConsultas.generarConsulta(this, recetasConsultadas);
		this.getEjecutadorDeAcciones().agregarAcciones(this.getAcciones(),this,filtro,recetasConsultadas);
		return recetasConsultadas;
	}
	
	public boolean tieneSobrepeso(){
		return (this.calcularIMC() > 25);
	}
	
	public boolean subsanaUnaListaDeCondicionesPreexistentes(List<CondicionPreexistente> list){
		return (list.stream().allMatch(condicion -> condicion.subsanaCondicionPreexistente(this)));
	}	
	
	public void habilitarMarcarConsultasComoFavoritos(){
		this.getAcciones().add(new MarcarFavoritos());
	}
	
	public void suscribirAMailsPorConsultas(){
		this.getAcciones().add(new EnviarMail());
	}

	
}
