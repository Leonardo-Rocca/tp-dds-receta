package grupo7.dds.jueves.Controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class LoginController {
	
	public ModelAndView loguear(Request request, Response response) {
	    return new ModelAndView(null, "login.hbs");
	}
} 
