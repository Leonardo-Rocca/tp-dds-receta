
package grupo7.dds.jueves;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Ingredientes")
public class Ingrediente {
	
	@Id
	@GeneratedValue
	private long idIngrediente;
	
	private String nombre;
	
	private int cantidad;
	
	//Constructor
	public Ingrediente(String nombre,int cantidad){
		this.nombre = nombre;
		this.cantidad = cantidad;
	}
	
	//Getters
	public String getNombre(){
		return this.nombre;
	}
	
	public int getCantidad(){
		return this.cantidad;
	}
	
}


