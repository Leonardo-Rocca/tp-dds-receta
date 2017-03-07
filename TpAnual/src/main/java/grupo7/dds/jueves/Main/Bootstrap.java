package grupo7.dds.jueves.Main;

import java.time.LocalDate;

import grupo7.dds.jueves.BuilderReceta;
import grupo7.dds.jueves.BuilderUsuario;
import grupo7.dds.jueves.Receta;
import grupo7.dds.jueves.Rutina;
import grupo7.dds.jueves.Usuario;

import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;


public class Bootstrap implements WithGlobalEntityManager, EntityManagerOps, TransactionalOps {

	private Receta nuez;
	private Receta pure;
	private Receta milanesa;
	private Usuario fede;
	private Usuario jess;
		
		
	public static void main(String[] args) {
		new Bootstrap().run();
		}

	public void run() {
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
		
		fede.agregarRecetaPropia(pure);
		fede.agregarRecetaPropia(nuez);
		//jess.agregarRecetaPropia(milanesa);
		fede.agregarRecetaPropia(milanesa);
		fede.marcarComoFavorita(milanesa);
		
		withTransaction(() -> {
		      persist(fede);
		      persist(jess);
		      persist(milanesa);
		      persist(pure);
		      persist(nuez);
		});
	}
	
}
