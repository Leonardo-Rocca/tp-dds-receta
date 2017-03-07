package grupo7.dds.jueves;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Consultas")
public class Consulta
{
	@Id
	@GeneratedValue
	private long idConsulta;
	
	@ManyToOne
	private Usuario autor;
	
	@ManyToMany
	private List<Receta> recetas = new ArrayList<Receta>();
	
	private LocalDateTime fecha;
	
	public LocalDateTime getFecha()
	{
		return fecha;
	}

	public void setFecha(LocalDateTime fecha)
	{
		this.fecha = fecha;
	}

	public Usuario getAutor()
	{
		return autor;
	}

	public void setAutor(Usuario autor)
	{
		this.autor = autor;
	}

	public List<Receta> getRecetas()
	{
		return recetas;
	}
	
	public long getConsultaId(){
		return idConsulta;
	}

	public void setRecetas(List<Receta> recetasConsultadas)
	{
		this.recetas = recetasConsultadas;
	}
	
	public Consulta(Usuario autor, List<Receta> recetasConsultadas)
	{
		this.setAutor(autor);
		this.setRecetas(recetasConsultadas);
		this.setFecha(LocalDateTime.now());
	}

	public int tieneReceta(Receta receta)
	{
		if(this.getRecetas().contains(receta)){
			return 1;
		}
		return 0;
	}

}
