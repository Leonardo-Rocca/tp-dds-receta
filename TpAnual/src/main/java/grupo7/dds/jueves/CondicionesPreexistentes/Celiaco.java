package grupo7.dds.jueves.CondicionesPreexistentes;

import grupo7.dds.jueves.Receta;
import grupo7.dds.jueves.Usuario;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("C")
public class Celiaco extends CondicionPreexistente
{

	@Override
	public boolean subsanaCondicionPreexistente(Usuario usuario)
	{
		return true;
	}

	public boolean esValidaPara(Usuario usuario){
		return true;
	}
	
	public boolean esInadecuadoParaReceta(Receta receta){
		return false;
	}
}
