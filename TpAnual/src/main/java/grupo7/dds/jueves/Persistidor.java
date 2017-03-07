package grupo7.dds.jueves;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

public class Persistidor implements WithGlobalEntityManager {
	
	static Persistidor persistidor = new Persistidor();
	
	public static Persistidor getPersistidor(){
		return persistidor;
	}
	
	public Object obtener(Long id, Object objeto){
		return entityManager().find(objeto.getClass(), id); 
	}

	public void persistir(Object objeto)
	{
		entityManager().persist(objeto);
	}
	
}
