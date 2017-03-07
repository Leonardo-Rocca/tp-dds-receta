package grupo7.dds.jueves.Controllers;

import grupo7.dds.jueves.ListadorDeRecetas;
import grupo7.dds.jueves.Persistidor;
import grupo7.dds.jueves.Receta;
import grupo7.dds.jueves.RepoConsultas;
import grupo7.dds.jueves.RepoUsuarios;
import grupo7.dds.jueves.Usuario;
import grupo7.dds.jueves.BuilderReceta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class RecetasController implements WithGlobalEntityManager, TransactionalOps {

	
	 public ModelAndView listarRecetas(Request request, Response response) {
		 	Usuario usuarioLogueado = RepoUsuarios.getRepoUsuarios().get();
		 	List<Receta> recetas = new ArrayList<>();
		 	List<Receta> recetasTodas = ListadorDeRecetas.getListador().listarTodas();
		 	List<Receta> recetasConsultadas = RepoConsultas.getRepoConsultas().obtenerRecetasDeUsuario(usuarioLogueado);
		 	
		 	if (usuarioLogueado.getRecetasFavoritas().isEmpty()){
		 		if(recetasConsultadas.isEmpty()){
		 			recetas = RepoConsultas.getRepoConsultas().obtenerPrimerasRecetasMasConsultadas(recetasTodas,10);
		 		}
		 		else{
		 			recetas = recetasConsultadas;
		 		}
		 	}
		 	else{
		 		recetas = usuarioLogueado.getRecetasFavoritas();
		 	}
		    
		  

		    HashMap<String, Object> viewModel = new HashMap<>();
		    viewModel.put("recetas", recetas);
		    
		    return new ModelAndView(viewModel, "recetas.hbs");
		  }
	 
	 public ModelAndView mostrar(Request request, Response response) {
		    long id = Long.parseLong(request.params(":id"));

		    Receta receta = ListadorDeRecetas.getListador().obtenerReceta(id);

		    return new ModelAndView(receta, "receta.hbs");
		  }
	 
	 public Void crear(Request request, Response response) {
		 
		 Usuario autor = RepoUsuarios.getRepoUsuarios().get();
		 
		    String nombre = request.queryParams("nombre");
		    String temporada = request.queryParams("temporada");
		    String dificultad = request.queryParams("dificultad");
		    //Ingredientes
		    //Codimentos
		    //Preparacion
		    //Calorias
		    		    
		    Receta receta = new BuilderReceta(nombre).temporada(temporada).
		    		dificultad(dificultad).build();
		    
		    autor.agregarRecetaPropia(receta);
		    
		    withTransaction(() -> {
		       Persistidor.getPersistidor().persistir(autor);
		    });

		    response.redirect("/recetas");
		    return null;
		  }
	 
	 public ModelAndView nuevo(Request request, Response response){
		
		 return new ModelAndView(null, "receta-nueva.hbs");
	 }
}
