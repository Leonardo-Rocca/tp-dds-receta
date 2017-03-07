package grupo7.dds.jueves.Controllers;

import grupo7.dds.jueves.RepoUsuarios;
import grupo7.dds.jueves.Usuario;

import java.util.HashMap;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class UsuarioController {
	
	public ModelAndView detallarUsuario(Request request, Response response) {
		
		RepoUsuarios repoUsuarios = new RepoUsuarios();
		Usuario usuario = repoUsuarios.get();
		
		 HashMap<String, Object> viewModel = new HashMap<>();
		    viewModel.put("usuario", usuario);
		    viewModel.put("imc", usuario.calcularIMC().toString().substring(0, 5));
			
		return new ModelAndView(viewModel, "usuario.hbs");
	}
}
