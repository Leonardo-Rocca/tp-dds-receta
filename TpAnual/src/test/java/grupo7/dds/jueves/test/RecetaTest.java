package grupo7.dds.jueves.test;
import static org.junit.Assert.*;
import grupo7.dds.jueves.BuilderReceta;
import grupo7.dds.jueves.Receta;
import grupo7.dds.jueves.CondicionesPreexistentes.Diabetico;
import grupo7.dds.jueves.CondicionesPreexistentes.Hipertenso;
import grupo7.dds.jueves.CondicionesPreexistentes.Vegano;

import org.junit.Before;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

public class RecetaTest {
	private Receta nuez;
	private Receta pure;
	private Receta milanesa;
	
	@Before
	public void setUp(){
		nuez = new BuilderReceta("nuez moscada")
		.preparacion("mezcla todo")
		.dificultad("muy dificil")
		.temporada("verano")
		.ingrediente("nuez",2)
		.build();
		
		pure = new BuilderReceta("pure")
		.preparacion("mezcla todo")
		.dificultad("muy dificil")
		.temporada("verano")
		.ingrediente("papa",2)
		.subreceta(nuez)
		.build();
	
		milanesa = new BuilderReceta("milanesa con pure")
		.preparacion("mezcla todo")
		.dificultad("muy dificil")
		.temporada("verano")
		.ingrediente("carne",3)
		.subreceta(pure)
		.build();
	}
	
	@Test
	public void getIngredientesDeUnaRecetaConSubrecetas(){
		assertTrue(milanesa.getIngredientes().size() == 3);
	}
	
	@Test
	public void menosDeCienGramosDeAzucarNoSonInadecuadosParaNadie(){
		Receta azucar = new BuilderReceta("azucar")
			.preparacion("no hacer nada")
			.dificultad("muy facil")
			.temporada("todo el año")
			.condimento("azucar", 50)
			.build();
		assertEquals(azucar.getParaQueCondicionesPreexistentesEsInadecuado().size(),0);
	}
	
	@Test
	public void manzanaNoEsInadecuadoParaNadie(){
		Receta tarta = new BuilderReceta("tarta de manzana")
			.preparacion("mezclar manzanas")
			.dificultad("facil")
			.temporada("todo el año")
			.ingrediente("manzana", 4)
			.build();
		assertEquals(tarta.getParaQueCondicionesPreexistentesEsInadecuado().size(),0);
	}
	
	@Test
	public void recetaConChoriEsInadecuadaParaVegano(){
		Receta tarta = new BuilderReceta("choripan")
			.preparacion("mezcla todo")
			.dificultad("facil")
			.temporada("todo el año")
			.ingrediente("chori", 3)
			.ingrediente("pan",1)
			.build();
		assertEquals(tarta.getParaQueCondicionesPreexistentesEsInadecuado().get(0).getClass(),new Vegano().getClass());
	}
	
	@Test
	public void recetaConMas100GramosAzucarYCaldoEsInadecuadaParaDiabeticoEHipertenso(){
		Receta tarta = new BuilderReceta("receta extraña")
		.preparacion("mezcla todo")
		.dificultad("muy dificil")
		.temporada("todo el año")
		.ingrediente("pan", 5)
		.condimento("sal",30)
		.condimento("azucar",150)
		.build();
		assertEquals(tarta.getParaQueCondicionesPreexistentesEsInadecuado().get(0).getClass(),new Diabetico().getClass());
		assertEquals(tarta.getParaQueCondicionesPreexistentesEsInadecuado().get(1).getClass(),new Hipertenso().getClass());
	}

	
}
