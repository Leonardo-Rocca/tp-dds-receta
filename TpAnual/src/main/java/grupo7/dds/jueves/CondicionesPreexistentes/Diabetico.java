package grupo7.dds.jueves.CondicionesPreexistentes;

import grupo7.dds.jueves.Receta;
import grupo7.dds.jueves.Usuario;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("D")
public class Diabetico extends CondicionPreexistente
{

	@Override
	public boolean subsanaCondicionPreexistente(Usuario usuario)	{
		return (usuario.tieneRutinaActiva() && usuario.pesaMenosDe70Kg());
	}

	public boolean esValidaPara(Usuario usuario){
		return (usuario.tieneAlMenosUnaPreferencia() && usuario.getSexo() != null);
	}
	
	public boolean esInadecuadoParaReceta(Receta receta){
		return (receta.getCondimentos().stream().anyMatch(condimento -> condimento.getNombre() == "azucar" && condimento.getCantidad() > 100));
	}
}
