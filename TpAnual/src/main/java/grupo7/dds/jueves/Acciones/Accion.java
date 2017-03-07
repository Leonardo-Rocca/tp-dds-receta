package grupo7.dds.jueves.Acciones;

import grupo7.dds.jueves.Receta;
import grupo7.dds.jueves.Usuario;
import grupo7.dds.jueves.Filtros.Filtro;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;


@Entity
@Table(name = "Acciones")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="tipoAccion")
public abstract class Accion {
	
	@Id
	@GeneratedValue
	private long idAccion;
	
	private String name;
	public abstract void ejecutar();
	public abstract Accion setValores(Usuario user, Filtro criterio, List<Receta> recetasConsultadas);
	
}
