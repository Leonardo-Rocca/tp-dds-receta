package grupo7.dds.jueves.Controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ConsultaRecetaController {

	public ModelAndView consultar(Request request, Response response) {
	    return new ModelAndView(null, "consultaRecetas.hbs");
	}
	
}
