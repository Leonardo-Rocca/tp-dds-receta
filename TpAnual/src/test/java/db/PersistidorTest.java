package db;

import java.time.LocalDate;

import grupo7.dds.jueves.BuilderReceta;
import grupo7.dds.jueves.BuilderUsuario;
import grupo7.dds.jueves.Grupo;
import grupo7.dds.jueves.Persistidor;
import grupo7.dds.jueves.Receta;
import grupo7.dds.jueves.RepoUsuarios;
import grupo7.dds.jueves.Rutina;
import grupo7.dds.jueves.Usuario;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

public class PersistidorTest extends AbstractPersistenceTest implements WithGlobalEntityManager {
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
		
	
		
		pablo.setSexo("masculino");
		pablo.agregarPreferenciaAlimenticia("carne");
		
		repoUsuarios = RepoUsuarios.getRepoUsuarios();
	}
	
	@Test
	public void sePersistenGrupos(){
		Grupo cursoPdep = new Grupo("Curso martes mañana 2014");
		cursoPdep.agregarMiembro(eric);
		cursoPdep.agregarMiembro(leo);
		cursoPdep.agregarMiembro(fedeC);
		cursoPdep.persistir();
		Assert.assertEquals(cursoPdep.obtenerGrupo(cursoPdep.getGrupoId()),cursoPdep);
	}
}