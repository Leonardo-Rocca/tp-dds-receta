package grupo7.dds.jueves.test;

import static org.junit.Assert.*;
import grupo7.dds.jueves.BuilderReceta;
import grupo7.dds.jueves.Grupo;
import grupo7.dds.jueves.ListadorDeRecetas;
import grupo7.dds.jueves.Receta;
import grupo7.dds.jueves.Rutina;
import grupo7.dds.jueves.Usuario;
import grupo7.dds.jueves.CondicionesPreexistentes.Diabetico;
import grupo7.dds.jueves.CondicionesPreexistentes.Hipertenso;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

public class SugerenciasTest {

	private Usuario juan;
	private Usuario pedro;
	private Grupo amigosDelClub;
	private Receta paella;
	private Receta torta;
	private Receta pizza;
	private ListadorDeRecetas listador;
	
	@Before
	public void setUp(){
		
		ListadorDeRecetas.getListador().limpiar();
		
		juan = new Usuario("Juan",70,1.78,LocalDate.of(1980,05,26),Rutina.INTENSIVA);
		juan.agregarPreferenciaAlimenticia("nueces");
		juan.setSexo("masculino");
		
		pedro = new Usuario("Pedro",60,1.68,LocalDate.of(1980,10,04),Rutina.LEVE);
		pedro.agregarPreferenciaAlimenticia("tomate");
		
		amigosDelClub = new Grupo("Amigos del club");
		amigosDelClub.agregarMiembro(juan);
		amigosDelClub.agregarMiembro(pedro);
		amigosDelClub.agregarPreferenciaAlimenticia("chocolate");
		amigosDelClub.agregarPreferenciaAlimenticia("pizza");
		
		paella = new BuilderReceta("paella")
			.preparacion("Mezclar mariscos")
			.dificultad("Medio")
			.temporada("Invierno")
			.ingrediente("mariscos",20)
			.build();

		torta = new BuilderReceta("Torta de chocolate")
			.preparacion("Hacer bizcochuelo, bañar en chocolate y agregar adornos azucarados")
			.dificultad("Fácil")
			.temporada("Cumpleaños")
			.ingrediente("chocolate", 200)
			.ingrediente("bizcochuelo",1)
			.condimento("azucar", 150)
			.build();
		
		pizza = new BuilderReceta("pizza 4 quesos")
			.preparacion("Agregar más salsa a prepizza")
			.dificultad("Muy fácil")
			.temporada("Todo el año")
			.condimento("sal",50)
			.build();
		
		listador = ListadorDeRecetas.getListador();
	}
	
	@Test
	public void aUsuarioQueLeDisgustanLosMariscosNoLeGustaLaPaella() {
		juan.agregarComidaQueLeDisgusta("mariscos");
		assertFalse(juan.leGusta(paella));
	}

	@Test
	public void usuarioDiabeticoNoEsAdecuadoParaTorta() {
		juan.agregarCondicionPreexistente(new Diabetico());
		assertFalse(juan.esAdecuadoPara(torta));
	}
	
	@Test
	public void aGrupoConPreferenciaAlimenticiaPizzaLeGustaLaPizzaCuatroQuesos() {
		assertTrue(amigosDelClub.leGusta(pizza));
	}
	
	@Test
	public void grupoConUsuarioHipertensoNoEsAdecuadoParaPizzaCuatroQuesos(){
		pedro.agregarCondicionPreexistente(new Hipertenso());
		assertFalse(amigosDelClub.esAdecuadoPara(pizza));
	}
	
	@Test
	public void paellaEsRecomendableParaUnUsuario(){
		listador.sugerirSiEsPosible(paella,pedro);
		assertTrue(paella.esRecomendablePara(pedro));
	}
	
	@Test
	public void paellaNoEsRecomendableParaUnUsuarioAlQueNoLeGustanLosMariscos(){
		juan.agregarComidaQueLeDisgusta("mariscos");
		listador.sugerirSiEsPosible(paella,juan);
		assertFalse(paella.esRecomendablePara(juan));
	}
	
	@Test
	public void tortaEsRecomendableParaUnGrupo(){
		listador.sugerirSiEsPosible(torta,amigosDelClub);
		assertTrue(torta.esRecomendablePara(amigosDelClub));
	}
	
	@Test
	public void tortaNoEsRecomendableParaGrupoConUnMiembroDiabetico(){
		juan.agregarCondicionPreexistente(new Diabetico());
		listador.sugerirSiEsPosible(torta,amigosDelClub);
		assertFalse(torta.esRecomendablePara(amigosDelClub));
	}
}
