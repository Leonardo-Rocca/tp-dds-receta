package grupo7.dds.jueves.Controllers;

import grupo7.dds.jueves.ListadorDeRecetas;
import grupo7.dds.jueves.Receta;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class HomeController {

  public ModelAndView mostrar(Request request, Response response) {
    return new ModelAndView(null, "home.hbs");
  }
  
  

}
