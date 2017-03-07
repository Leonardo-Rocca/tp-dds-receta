package grupo7.dds.jueves.CondicionesPreexistentes;

import grupo7.dds.jueves.Receta;
import grupo7.dds.jueves.Usuario;

import java.util.ArrayList;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("V")
public class Vegano extends CondicionPreexistente
{

	@Override
	public boolean subsanaCondicionPreexistente(Usuario usuario)
	{
		return (usuario.leGustaLaFruta());
	}

	public boolean esValidaPara(Usuario usuario){
		ArrayList<String> preferenciasInvalidas = new ArrayList<String>();
		preferenciasInvalidas.add("pollo");
		preferenciasInvalidas.add("carne");
		preferenciasInvalidas.add("chivito");
		preferenciasInvalidas.add("chori");
		return (!(usuario.contieneAlgunaPreferenciaInvalida(preferenciasInvalidas)));
	}
	
	public boolean esInadecuadoParaReceta(Receta receta){
		ArrayList<String> preferenciasInvalidas = new ArrayList<String>();
		preferenciasInvalidas.add("pollo");
		preferenciasInvalidas.add("carne");
		preferenciasInvalidas.add("chivito");
		preferenciasInvalidas.add("chori");
		return (receta.getIngredientes().stream().anyMatch(ingrediente -> preferenciasInvalidas.contains(ingrediente.getNombre())));
	}
}
