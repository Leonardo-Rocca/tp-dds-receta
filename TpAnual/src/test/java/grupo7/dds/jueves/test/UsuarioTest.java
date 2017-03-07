package grupo7.dds.jueves.test;

import static org.junit.Assert.*;
import grupo7.dds.jueves.BuilderReceta;
import grupo7.dds.jueves.BuilderUsuario;
import grupo7.dds.jueves.Grupo;
import grupo7.dds.jueves.ListadorDeRecetas;
import grupo7.dds.jueves.Receta;
import grupo7.dds.jueves.RepoUsuarios;
import grupo7.dds.jueves.Rutina;
import grupo7.dds.jueves.Usuario;
import grupo7.dds.jueves.CondicionesPreexistentes.Celiaco;
import grupo7.dds.jueves.CondicionesPreexistentes.CondicionPreexistente;
import grupo7.dds.jueves.CondicionesPreexistentes.Diabetico;
import grupo7.dds.jueves.CondicionesPreexistentes.Hipertenso;
import grupo7.dds.jueves.CondicionesPreexistentes.Vegano;
import grupo7.dds.jueves.Excepciones.CondicionInvalidaParaUsuarioException;
import grupo7.dds.jueves.Excepciones.FechaInvalidaException;
import grupo7.dds.jueves.Excepciones.NoSolicitoIncorporacionException;
import grupo7.dds.jueves.Excepciones.NombreInvalidoException;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.Before;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

public class UsuarioTest {

	private Usuario fede;
	private Usuario jess;
	private Usuario fedeC;
	private Usuario leo;
	private Usuario eric;
	private Usuario pablo;
	private Usuario pepe;
	private RepoUsuarios repoUsuarios;
	private Receta receta;
	private Receta milanesa;
	
	@Before
	public void setUp()
	{
		//fede = new Usuario("Federico Martinez",60,1.70,LocalDate.of(1994,12,28),Rutina.INTENSIVA);
		//jess = new Usuario("Jessica Saavedra",51,1.58,LocalDate.of(1994,03,02),Rutina.NADA);
		//fedeC = new Usuario("Federico Catala",90,1.96,LocalDate.of(1995,03,16),Rutina.MEDIANO);
		//leo = new Usuario("Leonardo Rocca",60,1.71,LocalDate.of(1994,6,12),Rutina.INTENSIVA);
		//eric = new Usuario("Eric Kalinowski",60,1.70,LocalDate.of(1995,05,11),Rutina.MEDIANO);
		
		pablo = new Usuario("Pablo",65,1.76,LocalDate.of(1980,3,9),Rutina.ACTIVASINEJERCICIO);
		pepe = new Usuario("Juan",75,1.82,LocalDate.of(1985,6,7),Rutina.MEDIANO);
		
		fede = new BuilderUsuario("Federico Martinez")
		         .peso(60)
		         .altura(1.70)
		         .fechaDeNacimiento(LocalDate.of(1994,12,28))
		         .rutina(Rutina.INTENSIVA)
		         .agregarPreferenciaAlimenticia("fruta")
		        .build();
	
		jess = new BuilderUsuario("Jessica Saavedra")
        		.peso(51)
        		.altura(1.58)
        		.fechaDeNacimiento(LocalDate.of(1994,03,02))
        		.rutina(Rutina.NADA)
        		.build();
		
		
		leo = new BuilderUsuario("Leonardo Rocca")
				.peso(60)
				.altura(1.71)
				.fechaDeNacimiento(LocalDate.of(1994,6,12))
				.rutina(Rutina.INTENSIVA)
				.build();
		
		fedeC = new BuilderUsuario("Federico Catala")
				.peso(90)
				.altura(1.96)
				.fechaDeNacimiento(LocalDate.of(1995,03,16))
				.rutina(Rutina.MEDIANO)
				.build();
		
		eric = new BuilderUsuario("Eric Kalinowski")
				.peso(60)
				.altura(1.70)
				.fechaDeNacimiento(LocalDate.of(1995,05,11))
				.rutina(Rutina.MEDIANO)
				.build();
		
	
		
		pablo.setSexo("masculino");
		pablo.agregarPreferenciaAlimenticia("carne");
		
		repoUsuarios = new RepoUsuarios();
		repoUsuarios.add(fedeC);
		repoUsuarios.add(fede);
		repoUsuarios.add(jess);
		repoUsuarios.add(leo);
		repoUsuarios.add(eric);
		
		receta = new BuilderReceta("recetaGrosa")
			.preparacion("mezcla todo")
			.dificultad("dificil")
			.temporada("vernao")
			.ingrediente("papa",3)
			.build();
		
		milanesa = new BuilderReceta("milanesa a caballo")
		.preparacion("hacer milanesa y agregar dos huevos fritos")
		.dificultad("facil")
		.temporada("todo el año")
		.ingrediente("carne",1)
		.condimento("sal",20)
		.build();
		
	}
	
	// Tests IMC 
	
	@Test
	public void calcularIMCdeEric()
	{
		Double eric_IMC = eric.calcularIMC();
		assertTrue((eric_IMC < 20.8) && (eric_IMC > 20.7));
	}

	@Test
	public void calcularIMCJess()	{
		Double jess_IMC = jess.calcularIMC();
		assertTrue((jess_IMC < 20.5) && (jess_IMC > 20.4));
	}
		
	@Test
	public void calcularIMCdeFedeC()
	{
		Double fedeC_IMC = fedeC.calcularIMC();
		assertTrue((fedeC_IMC < 23.5) && (fedeC_IMC > 23.4));
	}
	
	@Test
	public void calcularIMCFede()
	{
		Double fedeM_IMC;
		fedeM_IMC = fede.calcularIMC();
		assertTrue( (fedeM_IMC < 20.8) && (fedeM_IMC > 20.7));
	}
	
	@Test
	public void calcularIMCdeLeo()
	{
		Double leo_IMC;
		leo_IMC = leo.calcularIMC();
		assertTrue( (leo_IMC < 20.6) && (leo_IMC > 20.5));
	} 
	
	@Test
	public void imcEnRangoSaludableDeFede()	{
		assertTrue(fede.imcEnRangoSaludable());
	}
	
	// Tests subsanar condiciones
	
	@Test
	public void fedeCSigueUnaRutinaSaludableSubsanandoElVeganismo() {
		fede.agregarCondicionPreexistente(new Vegano());
		assertTrue(fede.sigueUnaRutinaSaludable());
	}
	
	@Test
	public void fedeSigueUnaRutinaSaludableSubsanandoHipertenso() {
		fede.agregarCondicionPreexistente(new Hipertenso());
		assertTrue(fede.sigueUnaRutinaSaludable());
	}
	
	@Test
	public void usuarioConRutinaActivaSinEjercicioYPesoMenorA70SubsanaCondicionDeDiabetico(){
		pablo.agregarCondicionPreexistente(new Diabetico());
		assertTrue(pablo.sigueUnaRutinaSaludable());
	}
	
	@Test
	public void fedeCSigueUnaRutinaSaludableSubsanandoCeliaco() {
		fedeC.agregarCondicionPreexistente(new Celiaco());
		assertTrue(fedeC.sigueUnaRutinaSaludable());
	}
	
	// Tests validaciones
	
	@Test(expected = NombreInvalidoException.class)
	  public void nombreCortoEsInvalido() {
	    new Usuario("ana",60,1.70,LocalDate.of(1994,6,12),Rutina.LEVE);
	  }
	
	@Test(expected = FechaInvalidaException.class)
	  public void fechaPosteriorALaActualEsInvalida() {
	    new Usuario("pedro",78,1.86,LocalDate.of(2070,5,12),Rutina.NADA);
	  }
	
	@Test(expected = CondicionInvalidaParaUsuarioException.class)
	public void usuarioHipertensoSinPreferenciasEsInvalido() {
	    eric.agregarCondicionPreexistente(new Hipertenso());
	}
	
	@Test(expected = CondicionInvalidaParaUsuarioException.class)
	public void usuarioDiabeticoSinSexoEsInvalido() {
	    jess.agregarCondicionPreexistente(new Diabetico());
	}
	
	@Test(expected = CondicionInvalidaParaUsuarioException.class)
	public void usuarioVeganoConPreferenciaCarneEsInvalido() {
	    pablo.agregarCondicionPreexistente(new Vegano());
	}
	
	// Otros tests
	
	@Test
	public void usuarioConRutinaActivaSinEjercicioNoTieneRutinaIntensiva(){
		assertFalse(pablo.tieneRutinaIntensiva());
	}
	
	@Test
	public void asignarUnaRecetaGeneralAUnUsuario() {
		eric.getListador().agregarReceta(receta);
		assertTrue(eric.tieneEnRecetaGeneral(receta));
	}
	
	@Test
	public void saberSiUnUsuarioPuedeVerUnaRecetaGeneral() {
		eric.getListador().agregarReceta(receta);
		assertTrue(eric.puedeVer(receta));
	}
	
	@Test
	public void unUsuarioModificaExitosamenteElNombreYPreparacionDeUnaRecetaPropia(){
		pepe.agregarRecetaPropia(milanesa);
		pepe.modificarReceta(milanesa,"milanesa napolitana",milanesa.getIngredientes(),milanesa.getCondimentos(),"hacer milanesa y agregar salsa y queso",milanesa.getDificultad(),milanesa.getTemporada());
		assertTrue(pepe.getRecetasPropias().stream()
				.anyMatch(recetaPropia -> (recetaPropia.getNombre()=="milanesa a caballo")&&(recetaPropia.getPreparacion()=="hacer milanesa y agregar dos huevos fritos")));
		assertTrue(pepe.getRecetasPropias().stream().anyMatch(recetaPropia -> (recetaPropia.getNombre()=="milanesa napolitana")&&(recetaPropia.getPreparacion()=="hacer milanesa y agregar salsa y queso")));
	}
	
	@Test
	public void unUsuarioModificaExitosamenteElNombreYPreparacionDeUnaRecetaGeneral(){
		jess.getListador().agregarReceta(milanesa);
		jess.modificarReceta(milanesa,"milanesa napolitana",milanesa.getIngredientes(),milanesa.getCondimentos(),"hacer milanesa y agregar salsa y queso",milanesa.getDificultad(),milanesa.getTemporada());
		assertTrue(jess.getListador().getRecetasGenerales().stream()
				.anyMatch(recetaGeneral -> (recetaGeneral.getNombre()=="milanesa a caballo")&&(recetaGeneral.getPreparacion()=="hacer milanesa y agregar dos huevos fritos")));
		assertTrue(jess.getRecetasPropias().stream()
				.anyMatch(recetaPropia -> (recetaPropia.getNombre()=="milanesa napolitana")&&(recetaPropia.getPreparacion()=="hacer milanesa y agregar salsa y queso")));
	}
	
	@Test
	public void usuarioTieneAccesoARecetasPrivadasPublicasYDeSusGrupos(){
		Grupo cursoPdep = new Grupo("Curso martes mañana 2014");
		cursoPdep.agregarMiembro(jess);
		cursoPdep.agregarMiembro(fede);
		cursoPdep.agregarMiembro(leo);
		cursoPdep.agregarMiembro(fedeC);
		Receta paella = new BuilderReceta("Paella")
			.preparacion("Mezclar mariscos")
			.dificultad("Medio")
			.temporada("Invierno")
			.build();
		
		Receta pizza = new BuilderReceta("pizza 4 quesos")
		.preparacion("Agregar más salsa a prepizza")
		.dificultad("Muy fácil")
		.temporada("Todo el año")
		.build();
		
		Receta torta = new BuilderReceta("Torta de chocolate")
		.preparacion("Hacer bizcochuelo, bañar en chocolate y agregar adornos azucarados")
		.dificultad("Fácil")
		.temporada("Cumpleaños")
		.build();
	
		jess.agregarRecetaPropia(torta);
		fedeC.agregarRecetaPropia(paella);
		ListadorDeRecetas.getListador().agregarReceta(pizza);
		assertTrue(jess.recetasALasQueTieneAcceso().contains(torta));
		assertTrue(jess.recetasALasQueTieneAcceso().contains(paella));
		assertTrue(jess.recetasALasQueTieneAcceso().contains(pizza));
	}
	
	@Test
	public void usuarioEstaInteresadoEnTorta(){
		Receta torta = new BuilderReceta("Torta de chocolate")
		.preparacion("Hacer bizcochuelo, bañar en chocolate y agregar adornos azucarados")
		.dificultad("Fácil")
		.temporada("Cumpleaños")
		.ingrediente("chocolate", 200)
		.ingrediente("bizcochuelo",1)
		.ingrediente("azucar", 150)
		.build();

		jess.marcarComoFavorita(torta);
		assertTrue(jess.getRecetasFavoritas().contains(torta));
	}
	
	//Tests de RepoUsuarios
	
	@Test
	public void BuscarAFedericoCatalaConGet(){
		Usuario usuarioPrototipo = new Usuario("Federico Catala");
		assertEquals(repoUsuarios.get(usuarioPrototipo),fedeC);
	}
	
	@Test
	public void BuscarConListALosFedericos(){
		Usuario usuarioPrototipo = new Usuario("Federico");
		assertEquals(repoUsuarios.list(usuarioPrototipo).size(),2);
	}
	
	@Test
	public void BuscarConListALosFedericosQueSubsanenElVeganismo(){
		ArrayList<CondicionPreexistente> listaDeCondicionesPreexistentes = new ArrayList<CondicionPreexistente>();
		listaDeCondicionesPreexistentes.add(new Vegano());
		Usuario usuarioPrototipo = new Usuario("Federico",listaDeCondicionesPreexistentes);
		assertTrue(fedeC.getCondicionesPreexistentes().size() == 0);
		assertEquals(repoUsuarios.list(usuarioPrototipo).size(),1);
	}
	
	@Test
	public void BuscarConListALosFedericosQueSubsanenLaCondicionCeliaco(){
		ArrayList<CondicionPreexistente> listaDeCondicionesPreexistentes = new ArrayList<CondicionPreexistente>();
		listaDeCondicionesPreexistentes.add(new Celiaco());
		Usuario usuarioPrototipo = new Usuario("Federico",listaDeCondicionesPreexistentes);
		assertTrue(fedeC.getCondicionesPreexistentes().size() == 0);
		assertTrue(fede.getCondicionesPreexistentes().size() == 0);
		assertEquals(repoUsuarios.list(usuarioPrototipo).size(),2);
	}
	
	@Test
	public void usuarioSolicitaIncorporacion(){
		repoUsuarios.solicitarIncorporacion(jess);
		assertTrue(repoUsuarios.getSolicitudesPendientes().contains(jess));
	}
	
	@Test
	public void seAceptaIncorporacionDeUnUsuario(){
		repoUsuarios.solicitarIncorporacion(jess);
		repoUsuarios.aprobarSolicitud(jess);
		assertTrue(repoUsuarios.getUsuarios().contains(jess));
	}
	
	@Test(expected = NoSolicitoIncorporacionException.class)
	public void noSePuedeAprobarUsuarioQueNoSolicitoIncorporacion(){
		repoUsuarios.aprobarSolicitud(pablo);
	}
	
	@Test
	public void seRechazaAUnUsuario(){
		repoUsuarios.solicitarIncorporacion(pepe);
		repoUsuarios.rechazarSolicitud(pepe,"Nos cae mal (?)");
		assertFalse(repoUsuarios.getSolicitudesPendientes().contains(pepe));
		assertFalse(repoUsuarios.getUsuarios().contains(pepe));
	}
	
}
