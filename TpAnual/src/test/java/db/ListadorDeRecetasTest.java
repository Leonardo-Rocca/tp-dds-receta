package db;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import grupo7.dds.jueves.BuilderReceta;
import grupo7.dds.jueves.BuilderUsuario;
import grupo7.dds.jueves.Condimento;
import grupo7.dds.jueves.ListadorDeRecetas;
import grupo7.dds.jueves.Receta;
import grupo7.dds.jueves.Rutina;
import grupo7.dds.jueves.Usuario;

public class ListadorDeRecetasTest extends AbstractPersistenceTest implements WithGlobalEntityManager {
	
	 private Receta mezcladito;
	 private Receta milanesa;
	 private Usuario fede;
	 private ListadorDeRecetas listadorRecetas;
	 
	@Before
	public void setUp(){ 
		
	ListadorDeRecetas.getListador().limpiar();
		
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
     
      fede = new BuilderUsuario("Federico Martinez")
      .peso(60)
      .altura(1.70)
      .fechaDeNacimiento(LocalDate.of(1994,12,28))
      .rutina(Rutina.INTENSIVA)
      .agregarPreferenciaAlimenticia("fruta")
      .build();
      
      listadorRecetas = ListadorDeRecetas.getListador();
      
	}
	
	@Test
	public void seAgreganRecetasAlRepoDesdeUnUsuario(){
		fede.agregarRecetaPropia(milanesa);
		Assert.assertEquals(listadorRecetas.obtenerReceta(milanesa.getIdReceta()),milanesa);
	}
	
	@Test
	public void sePersisteListaDeIngredientes(){
		fede.agregarRecetaPropia(milanesa);
		Assert.assertEquals(listadorRecetas.obtenerReceta(milanesa.getIdReceta()).getIngredientes().get(0).getNombre(),"carne");
	}

	@Test
	public void sePersisteListaDeCondimentos(){
		Condimento bebida = new Condimento("VODKA",100);
		mezcladito.agregarCondimento(bebida);
		Condimento tang = new Condimento("jugo tang",20);
		mezcladito.agregarCondimento(tang);
		fede.agregarRecetaPropia(mezcladito);
		
		Assert.assertTrue(listadorRecetas.obtenerReceta(mezcladito.getIdReceta()).getCondimentos().contains(bebida));
		Assert.assertTrue(listadorRecetas.obtenerReceta(mezcladito.getIdReceta()).getCondimentos().contains(tang));
	}
	@Test
	public void sePersisteListaStringIngredientesCaros(){
		fede.agregarRecetaPropia(milanesa);
		Assert.assertEquals(listadorRecetas.obtenerReceta(milanesa.getIdReceta()).getIngredientesCaros().get(0),"lechon");
	}

	@Test
	public void sePersisteAutor(){
		fede.agregarRecetaPropia(milanesa);
		Assert.assertEquals(listadorRecetas.obtenerReceta(milanesa.getIdReceta()).getAutor(),fede);
	}

	@Test
	public void sePersisteDificultadPreparacionYTemporada(){
		fede.agregarRecetaPropia(mezcladito);
		Assert.assertEquals(listadorRecetas.obtenerReceta(mezcladito.getIdReceta()).getDificultad(),mezcladito.getDificultad());
		Assert.assertEquals(listadorRecetas.obtenerReceta(mezcladito.getIdReceta()).getPreparacion(),mezcladito.getPreparacion());
		Assert.assertEquals(listadorRecetas.obtenerReceta(mezcladito.getIdReceta()).getTemporada(),mezcladito.getTemporada());
	}

	@Test
	public void sePersistenSubrecetas(){
		milanesa.agregarSubReceta(mezcladito);
		fede.agregarRecetaPropia(milanesa);
		Assert.assertEquals(listadorRecetas.obtenerReceta(milanesa.getIdReceta()).getSubRecetas().get(0),mezcladito);
	}
	
	@Test
	public void seAgreganRecetasAlRepoDesdeElSistema(){
		listadorRecetas.agregarReceta(mezcladito);
		Assert.assertEquals(listadorRecetas.obtenerReceta(mezcladito.getIdReceta()),mezcladito);
	}

}
