package grupo7.dds.jueves.Main;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.SparkBase.port;
import static spark.SparkBase.staticFileLocation;
import spark.template.handlebars.HandlebarsTemplateEngine;
import grupo7.dds.jueves.Controllers.ConsultaRecetaController;
import grupo7.dds.jueves.Controllers.HomeController;
import grupo7.dds.jueves.Controllers.LoginController;
import grupo7.dds.jueves.Controllers.RecetasController;
import grupo7.dds.jueves.Controllers.UsuarioController;

public class Routes {

  public static void main(String[] args) {
    System.out.println("Iniciando servidor");

    HomeController home = new HomeController();
    HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();
    LoginController login = new LoginController();
    ConsultaRecetaController consulta = new ConsultaRecetaController();
    RecetasController recetas = new RecetasController();
    UsuarioController usuario = new UsuarioController();

    port(8080);
    
    staticFileLocation("/public");

    get("/", home::mostrar, engine);
    get("/index.html", (request, response) -> {
      response.redirect("/");
      return null;
    });
    get("/login", login::loguear, engine);
    get("/consultaReceta",consulta::consultar,engine);
    get("/receta/:id", recetas::mostrar, engine);
    get("/recetas",recetas::listarRecetas,engine);
    get("/perfil",usuario::detallarUsuario,engine);
    post("/recetas", recetas::crear);
    get("/recetas/new", recetas::nuevo, engine);
   

  }

}
