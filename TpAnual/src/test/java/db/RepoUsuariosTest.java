package db;

import static org.junit.Assert.*;
import grupo7.dds.jueves.BuilderReceta;
import grupo7.dds.jueves.BuilderUsuario;
import grupo7.dds.jueves.ListadorDeRecetas;
import grupo7.dds.jueves.Receta;
import grupo7.dds.jueves.RepoUsuarios;
import grupo7.dds.jueves.Rutina;
import grupo7.dds.jueves.Usuario;
import grupo7.dds.jueves.CondicionesPreexistentes.Diabetico;
import grupo7.dds.jueves.Excepciones.NoSolicitoIncorporacionException;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

public class RepoUsuariosTest extends AbstractPersistenceTest implements WithGlobalEntityManager 
{
	private Usuario fede;
	private Usuario jess;
	private Usuario fedeC;
	private Usuario leo;
	private Usuario eric;
	private Usuario pablo;
	private Usuario pepe;
	private RepoUsuarios repoUsuarios;
	private Receta mezcladito;
	private Receta milanesa;
	private ListadorDeRecetas listadorRecetas;
		
	@Before
	public void setUp()
	{

		RepoUsuarios.getRepoUsuarios().limpiar();

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
		
		mezcladito = new BuilderReceta("recetaGrosa")
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
		
		pablo.setSexo("masculino");
		pablo.agregarPreferenciaAlimenticia("carne");
		
		repoUsuarios = RepoUsuarios.getRepoUsuarios();
	}
	
	@Test
	public void testSePersistenLosUsuariosEnElRepo(){
		repoUsuarios.solicitarIncorporacion(fedeC);
		repoUsuarios.aprobarSolicitud(fedeC);
		Assert.assertEquals(repoUsuarios.obtenerUsuario(fedeC.getUsuarioId()),fedeC);
	}
	
	@Test
	public void testSePersistenCondicionesLosUsuariosEnElRepo(){
		fede.setSexo("masculino");
		fede.agregarPreferenciaAlimenticia("manzana");
		fede.agregarCondicionPreexistente(new Diabetico());
		repoUsuarios.solicitarIncorporacion(fede);
		repoUsuarios.aprobarSolicitud(fede);
		Assert.assertEquals(repoUsuarios.obtenerUsuario(fede.getUsuarioId()).getCondicionesPreexistentes().get(0).getClass(),new Diabetico().getClass());

	}
	
	@Test
	public void testSePersistenLasRecetasFavoritasDeLosUsuarios(){
		repoUsuarios.solicitarIncorporacion(jess);
		repoUsuarios.aprobarSolicitud(jess);
		jess.marcarComoFavorita(milanesa);
		jess.marcarComoFavorita(mezcladito);
		Assert.assertEquals(repoUsuarios.obtenerUsuario(jess.getUsuarioId()).getRecetasFavoritas().size(),2);
		Assert.assertTrue(repoUsuarios.obtenerUsuario(jess.getUsuarioId()).getRecetasFavoritas().contains(mezcladito));
		Assert.assertTrue(repoUsuarios.obtenerUsuario(jess.getUsuarioId()).getRecetasFavoritas().contains(milanesa));
	}

	@Test
	public void testSePersistenLasAccionesDeLosUsuarios(){
		eric.habilitarMarcarConsultasComoFavoritos();
		repoUsuarios.solicitarIncorporacion(eric);
		repoUsuarios.aprobarSolicitud(eric);
	}
	
	@Ignore
	public void testSePersistenNombresDeLosUsuarios(){
		
		repoUsuarios.solicitarIncorporacion(leo);
		repoUsuarios.aprobarSolicitud(leo);
		leo.setNombre("leito");
		Assert.assertEquals(repoUsuarios.obtenerUsuario(leo.getUsuarioId()).getNombre(),"Leonardo Rocca");
	}
	
}
