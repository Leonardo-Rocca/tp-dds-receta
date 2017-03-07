package grupo7.dds.jueves;

import grupo7.dds.jueves.CondicionesPreexistentes.CondicionPreexistente;
import grupo7.dds.jueves.Excepciones.CondicionInvalidaParaUsuarioException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

public class BuilderUsuario
{
private String nombre;
private String sexo;
private LocalDate fechaDeNacimiento;
private int peso;
private double altura;
private ArrayList<String> preferenciasAlimenticias = new ArrayList<String>();
private ArrayList<String> comidasQueLeDisgustan = new ArrayList<String>();
private List<CondicionPreexistente> condicionesPreexistentes = new ArrayList<CondicionPreexistente>();
private Rutina rutina;

private Usuario usuario;
	
	public BuilderUsuario(String nombre) {
		this.nombre = nombre;
	}

	public BuilderUsuario sexo(String sexo) {
		this.sexo = sexo;
		return this;
	}

	public BuilderUsuario fechaDeNacimiento(LocalDate fechaDeNacimiento) {
		this.fechaDeNacimiento = fechaDeNacimiento;
		return this;
	}

	public BuilderUsuario rutina(Rutina rutina) {
		this.rutina = rutina;
		return this;
	}
	
	public BuilderUsuario peso(int peso)
	{
		this.peso = peso;
		return this;
	}

	public BuilderUsuario altura(Double altura)
	{
		this.altura = altura;
		return this;
	}
		
	public BuilderUsuario condicionesPreexistentes(List<CondicionPreexistente> condicionesPreexistentes) {
		this.condicionesPreexistentes = condicionesPreexistentes;
		return this;
	}
	
	// Agregar a las listas
	
	public BuilderUsuario agregarCondicionPreexistente(CondicionPreexistente condicion){
		
			this.condicionesPreexistentes.add(condicion);
		
		return this;
				}
						
	public BuilderUsuario agregarPreferenciaAlimenticia(String alimento) {
			this.preferenciasAlimenticias.add(alimento);
			return this;
		  }
				
	public BuilderUsuario agregarComidaQueLeDisgusta(String comida){
			this.comidasQueLeDisgustan.add(comida);
			return this;
		 }

	public BuilderUsuario validar(){
		if(this.condicionesPreexistentes.stream().allMatch(condicion->condicion.esValidaPara(usuario)) )
			{			}
	   else{
       throw new CondicionInvalidaParaUsuarioException("Las condiciones no son válidas para este usuario");
		}
		return this;
	}
	
	public Usuario build(){
		usuario = new Usuario(nombre,peso,altura,fechaDeNacimiento,rutina);
		usuario.setSexo(sexo);
		usuario.setCondicionesPreexistentes(condicionesPreexistentes);
		preferenciasAlimenticias.forEach(preferencia->usuario.agregarPreferenciaAlimenticia(preferencia));
		comidasQueLeDisgustan.forEach(comida->usuario.agregarComidaQueLeDisgusta(comida));
		this.validar();
		return usuario;
		
	}
}
