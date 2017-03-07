package grupo7.dds.jueves.CondicionesPreexistentes;

import grupo7.dds.jueves.Receta;
import grupo7.dds.jueves.Usuario;

import java.util.ArrayList;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("H")
public class Hipertenso extends CondicionPreexistente
{

	@Override
	public boolean subsanaCondicionPreexistente(Usuario usuario)
	{
		return (usuario.tieneRutinaIntensiva());
	}

	public boolean esValidaPara(Usuario usuario){
		return usuario.tieneAlMenosUnaPreferencia();
	}
	
	public boolean esInadecuadoParaReceta(Receta receta){
		ArrayList<String> preferenciasInvalidas = new ArrayList<String>();
		preferenciasInvalidas.add("sal");
		preferenciasInvalidas.add("caldo");
		return (receta.getCondimentos().stream().anyMatch(condimento -> preferenciasInvalidas.contains(condimento.getNombre())));
	}
}
