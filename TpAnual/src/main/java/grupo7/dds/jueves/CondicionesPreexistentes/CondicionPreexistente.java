package grupo7.dds.jueves.CondicionesPreexistentes;

import grupo7.dds.jueves.Receta;
import grupo7.dds.jueves.Usuario;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "Condiciones_preexistentes")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="tipoCondicion")
public abstract class CondicionPreexistente {

	@Id
	@GeneratedValue
	private Long id;

	public abstract boolean subsanaCondicionPreexistente(Usuario usuario);
	public abstract boolean esValidaPara(Usuario usuario);
	public abstract boolean esInadecuadoParaReceta(Receta receta);
	
	public String getNombre(){
		return this.getClass().toString().substring(49);
	}
}
