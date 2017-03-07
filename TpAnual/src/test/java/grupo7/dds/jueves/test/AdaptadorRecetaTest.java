package grupo7.dds.jueves.test;

import static org.junit.Assert.*;
import grupo7.dds.jueves.AdapterRepoRecetasExternas;
import grupo7.dds.jueves.ListadorDeRecetas;
import grupo7.dds.jueves.Receta;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import queComemos.entrega3.dominio.Dificultad;
import queComemos.entrega3.repositorio.BusquedaRecetas;

public class AdaptadorRecetaTest {
	
	@Test
	public void TestQueVerificaObtenerRecetaExterna (){
		AdapterRepoRecetasExternas adaptador = new AdapterRepoRecetasExternas();
		BusquedaRecetas busquedaRecetas = new BusquedaRecetas();
		busquedaRecetas.setNombre("salmon a la plancha");
		busquedaRecetas.setDificultad(Dificultad.FACIL);
		busquedaRecetas.agregarPalabraClave("salmon");
		busquedaRecetas.agregarPalabraClave("limon");
		List<Receta> receta = adaptador.obtenerRecetas(busquedaRecetas);
		assertEquals(receta.get(0).getNombre(),busquedaRecetas.getNombre());
	}
	
	@Test
	public void ElListadorDeRecetasContieneALasRecetasExternas(){
		AdapterRepoRecetasExternas adaptador = new AdapterRepoRecetasExternas();
		ListadorDeRecetas listador = new ListadorDeRecetas();
		assertEquals(listador.listarTodas().size(), adaptador.obtenerRecetas(new BusquedaRecetas()).size());
	}
}
