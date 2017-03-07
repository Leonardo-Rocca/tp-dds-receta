package grupo7.dds.jueves;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Condimentos")
public class Condimento {
	
	@Id
	@GeneratedValue
	private long idCondimento;
	
	private String nombre;
	
	private int cantidad;

	//Constructor
	
	public Condimento(String nombre, int cantidad){
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