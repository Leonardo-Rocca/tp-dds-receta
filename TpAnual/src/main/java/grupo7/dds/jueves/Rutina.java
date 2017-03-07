package grupo7.dds.jueves;

public enum Rutina {
	LEVE("Sedentaria"),NADA("Sedentaria"),MEDIANO("Sedentaria"),INTENSIVA("Activa"),ACTIVASINEJERCICIO("Activa");
 private String tipoRutina;
 
 private Rutina(String descripcion){
	 this.tipoRutina = descripcion;
 }
 
 public String getTipoRutina(){
	 return this.tipoRutina;
 }
 
}
