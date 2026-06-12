
public class Camion {
    private String id_camion;
    private String patente;
    private boolean esta_refrigerado;
    private int capacidad_kg;
    
    public Camion(String id_camion,String patente, int capacidad_kg, boolean esta_refrigerado){
        this.id_camion = id_camion;
        this.patente = patente;
        this.capacidad_kg = capacidad_kg;
        this.esta_refrigerado = esta_refrigerado;
        
    }

    //getters
    public String getId_camion() {return id_camion;}
    public String getPatente() {return patente;}
    public int getCapacidad_kg() {return capacidad_kg;}
    public boolean esta_refrigerado(){return esta_refrigerado;}

    //setters
    public void setId_camion(String id_camion) {this.id_camion = id_camion;}
    public void setPatente(String patente) {this.patente = patente;}
    public void setCapacidad_kg(int capacidad_kg) {this.capacidad_kg = capacidad_kg;}
    public void setEsta_refrigerado(boolean esta_refrigerado){this.esta_refrigerado = esta_refrigerado;}
   
}