package grupo7.dds.jueves.test;

import static org.junit.Assert.*;
import grupo7.dds.jueves.BuilderReceta;
import grupo7.dds.jueves.Consulta;
import grupo7.dds.jueves.EjecutadorDeAcciones;
import grupo7.dds.jueves.ListadorDeRecetas;
import grupo7.dds.jueves.Receta;
import grupo7.dds.jueves.RepoConsultas;
import grupo7.dds.jueves.Rutina;
import grupo7.dds.jueves.Usuario;
import grupo7.dds.jueves.Acciones.Accion;
import grupo7.dds.jueves.Acciones.EnviarMail;
import grupo7.dds.jueves.CondicionesPreexistentes.Diabetico;
import grupo7.dds.jueves.Filtros.Filtro;
import grupo7.dds.jueves.Filtros.FiltroCaro;
import grupo7.dds.jueves.Filtros.FiltroCondicionesPreexistentes;
import grupo7.dds.jueves.Filtros.FiltroExcesoDeCalorias;
import grupo7.dds.jueves.Filtros.FiltroGusto;
import grupo7.dds.jueves.Procesamientos.NoProcesamiento;
import grupo7.dds.jueves.Procesamientos.ProcesamientoConsiderarSoloResultadosPares;
import grupo7.dds.jueves.Procesamientos.ProcesamientoOrdenamientoPorCalorias;
import grupo7.dds.jueves.Procesamientos.ProcesamientoOrdenarAlfabeticamente;
import grupo7.dds.jueves.Procesamientos.ProcesamientoTomarPrimerosDiez;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito.*;

import static org.mockito.Mockito.verify;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

public class ConsultasTest extends AbstractPersistenceTest implements WithGlobalEntityManager{

	private Filtro filtro;
	private Filtro filtroProcesamiento;
	private Filtro filtroResultadosPares;
	private Filtro filtroOrdenAlfabetico;
	private Filtro filtroPorCalorias;
	private Usuario maria;
	private Usuario roberto;
	private Receta pizza;
	private Receta paella;
	private Receta sushi;
	private Receta torta;
	private Receta milanesas;
	private Receta pure;
	private Filtro filtroCaro;
	private RepoConsultas repoConsultas;
	@Mock
	private EnviarMail mockMail;
	
	@Before
	public void setUp(){
		
		repoConsultas = new RepoConsultas();
		ListadorDeRecetas.getListador().limpiar();
		
		//Filtro con todos los filtros y sin procesamiento posterior
		filtro = new FiltroExcesoDeCalorias(new FiltroCondicionesPreexistentes(new FiltroGusto(new FiltroCaro(new NoProcesamiento()))));
		filtroCaro = new FiltroCaro(new NoProcesamiento());
		
		//Cero filtros y post procesamiento
		filtroProcesamiento = new ProcesamientoTomarPrimerosDiez();
		filtroResultadosPares = new ProcesamientoConsiderarSoloResultadosPares();
		filtroOrdenAlfabetico = new ProcesamientoOrdenarAlfabeticamente();
		filtroPorCalorias = new ProcesamientoOrdenamientoPorCalorias();
		
		
		maria = new Usuario("Maria",150,1.62,LocalDate.of(1996,4,11),Rutina.INTENSIVA);
		maria.setSexo("femenino");
		maria.agregarPreferenciaAlimenticia("manzana");
		maria.agregarComidaQueLeDisgusta("mariscos");
		maria.agregarCondicionPreexistente(new Diabetico());
		
		roberto = new Usuario("Roberto",68,1.72,LocalDate.of(1962,11,21),Rutina.LEVE);
		
		pizza = new BuilderReceta("pizza 4 quesos")
			.preparacion("Agregar más salsa a prepizza")
			.dificultad("Muy fácil")
			.temporada("Todo el año")
			.build();
		
		paella = new BuilderReceta("paella")
			.preparacion("Mezclar mariscos")
			.dificultad("Medio")
			.temporada("Invierno")
			.ingrediente("mariscos",20)
			.build();
		
		sushi = new BuilderReceta("sushi")
			.preparacion("cortar salmon")
			.dificultad("dificil")
			.temporada("verano")
			.ingrediente("salmon",500)
			.build();
		
		torta = new BuilderReceta("torta de chocolate")
			.preparacion("Hacer bizcochuelo, bañar en chocolate y agregar adornos azucarados")
			.dificultad("Fácil")
			.temporada("Cumpleaños")
			.ingrediente("chocolate", 200)
			.ingrediente("bizcochuelo",1)
			.condimento("azucar", 150)
			.build();
				
		pure = new BuilderReceta("pure")
			.preparacion("mezcla todo")
			.dificultad("muy dificil")
			.temporada("verano")
			.ingrediente("papa",2)
			.build();
	
		milanesas = new BuilderReceta("milanesa con pure")
			.preparacion("mezcla todo")
			.dificultad("muy dificil")
			.temporada("verano")
			.ingrediente("carne",3)
			.subreceta(pure)
			.build();
	
		maria.getListador().agregarReceta(pizza);
		maria.getListador().agregarReceta(torta);
		
	}
	
	@Test
	public void noSeFiltraRecetaConMasDe500CaloriasParaUsuarioSinSobrepeso(){
		Filtro filtroCalorias = new FiltroExcesoDeCalorias(new NoProcesamiento());
		roberto.agregarRecetaPropia(milanesas);
		assertTrue(milanesas.cantidadCaloriasTotales() > 500);
		assertTrue(roberto.consultarRecetas(filtroCalorias).contains(milanesas));
	}
	
	@Test
	public void noSeAplicanFiltrosPeroSiProcesamientoDeTomarPrimerosDiez(){
		maria.agregarRecetaPropia(milanesas);
		maria.agregarRecetaPropia(milanesas);
		maria.agregarRecetaPropia(milanesas);
		maria.agregarRecetaPropia(paella);
		maria.agregarRecetaPropia(paella);
		maria.agregarRecetaPropia(paella);
		maria.agregarRecetaPropia(torta);
		maria.agregarRecetaPropia(torta);
		maria.agregarRecetaPropia(torta);
		maria.agregarRecetaPropia(pizza);
		maria.agregarRecetaPropia(pizza);
		maria.agregarRecetaPropia(pizza);
		maria.agregarRecetaPropia(sushi);
		maria.agregarRecetaPropia(sushi);
		maria.agregarRecetaPropia(sushi);
		assertEquals(maria.consultarRecetas(filtroProcesamiento).size(),10);
	}
	
	@Test
	public void noSeAplicanFiltrosPeroSiProcesamientoDeConsiderarResultadosPares(){
		roberto.agregarRecetaPropia(paella);
		roberto.agregarRecetaPropia(sushi);
		roberto.agregarRecetaPropia(pure);
		roberto.agregarRecetaPropia(milanesas);
		assertEquals(roberto.consultarRecetas(filtroResultadosPares).size(),9);
	}
	
	@Test
	public void noSeAplicanFiltrosPeroSiProcesamientoDeOrdenarAlfabeticamente(){
		ListadorDeRecetas.getListador().limpiar();
		roberto.agregarRecetaPropia(torta);
		roberto.agregarRecetaPropia(milanesas);
		roberto.agregarRecetaPropia(sushi);
		roberto.agregarRecetaPropia(pizza);
		assertEquals(roberto.consultarRecetas(filtroOrdenAlfabetico).get(0).getNombre(),"canelones de ricota y verdura");
		assertEquals(roberto.consultarRecetas(filtroOrdenAlfabetico).get(1).getNombre(),"cassatta");
		assertEquals(roberto.consultarRecetas(filtroOrdenAlfabetico).get(2).getNombre(),"churrasco a la sal");
		assertEquals(roberto.consultarRecetas(filtroOrdenAlfabetico).get(3).getNombre(),"ensalada caesar");
	}
	
	@Test
	public void noSeAplicanFiltrosPeroSiProcesamientoDeOrdenarPorCalorias(){
		roberto.getListador().limpiar();
		roberto.agregarRecetaPropia(torta);
		roberto.agregarRecetaPropia(milanesas);
		assertEquals(roberto.consultarRecetas(filtroPorCalorias).get(0),torta);
		assertEquals(roberto.consultarRecetas(filtroPorCalorias).get(1),milanesas);
	}
	
	@Test
	public void todosLosFiltrosSeAplicanExitosamente(){
		assertEquals(maria.consultarRecetas(filtro).size(),13); //
	}

	@Test
	public void verRecetaMasConsultada(){
		roberto.agregarRecetaPropia(torta);
		roberto.agregarRecetaPropia(milanesas);
		roberto.agregarRecetaPropia(sushi);
		roberto.agregarRecetaPropia(pizza);
		roberto.consultarRecetas(filtroCaro);
		assertEquals(roberto.getRepoConsultas().recetaMasConsultada().getNombre(),"pizza 4 quesos");
		
		
	}
	
	@Test
	public void MarcarFavoritos(){
		roberto.habilitarMarcarConsultasComoFavoritos();
		roberto.agregarRecetaPropia(paella);
		roberto.consultarRecetas(filtroCaro);
		EjecutadorDeAcciones.getEjecutador().ejecutarAcciones();
		assertTrue(roberto.getRecetasFavoritas().contains(paella));
	}
	
	@Ignore
	public void EnviarMail(){
		roberto.suscribirAMailsPorConsultas();
		roberto.consultarRecetas(filtroCaro);
		Accion mail = EjecutadorDeAcciones.getEjecutador().getAcciones().get(1);
		//mockMail = mock(EnviarMail.class);
		//when(mail.ejecutar() ).thenReturn(1.0);
		verify(mail).ejecutar();
	}
	
	@Test
	public void lasConsultasSePersisten(){
		RepoConsultas.getRepoConsultas().getConsultas().clear();
		Filtro filtroCalorias = new FiltroExcesoDeCalorias(new NoProcesamiento());
		roberto.agregarRecetaPropia(milanesas);
		assertTrue(milanesas.cantidadCaloriasTotales() > 500);
		roberto.consultarRecetas(filtroCalorias);
		Consulta consulta = RepoConsultas.getRepoConsultas().getConsultas().get(0);
		long consultaId = consulta.getConsultaId();
		assertEquals(RepoConsultas.getRepoConsultas().obtenerConsulta(consultaId),consulta);
	}
	
	@Test
	public void recetasConsultadasPorUsuario(){
		RepoConsultas.getRepoConsultas().getConsultas().clear();
		Filtro filtroCalorias = new FiltroExcesoDeCalorias(new NoProcesamiento());
		roberto.agregarRecetaPropia(milanesas);
		roberto.consultarRecetas(filtroCalorias);
		List<Receta> recetas = RepoConsultas.getRepoConsultas().obtenerRecetasDeUsuario(roberto);
		assertTrue(recetas.contains(milanesas));
	}
	
}
