package entidades;

import java.util.ArrayList;

public class Camion {
    private int id_camion;
    private String patente;
    private boolean esta_refrigerado;
    private int capacidad_kg;
    private ArrayList<Paquete> paquetesCargados;
    private int pesoOcupado = 0;
    
    public Camion(int id_camion,String patente, boolean esta_refrigerado, int capacidad_kg){
        this.id_camion = id_camion;
        this.patente = patente;
        this.capacidad_kg = capacidad_kg;
        this.esta_refrigerado = esta_refrigerado;
        this.paquetesCargados = new ArrayList<>();
    }

    //getters
    public int getId_camion() {return id_camion;}
    public String getPatente() {return patente;}
    public int getCapacidad_kg() {return capacidad_kg;}
    public boolean esta_refrigerado(){return esta_refrigerado;}
    public ArrayList<Paquete> getPaquetesCargados(){
        return new ArrayList<>(paquetesCargados);
    }
    public int getPesoOcupado() {
        return pesoOcupado;
    }

    //setters
    public void setId_camion(int id_camion) {this.id_camion = id_camion;}
    public void setPatente(String patente) {this.patente = patente;}
    public void setCapacidad_kg(int capacidad_kg) {this.capacidad_kg = capacidad_kg;}
    public void setEsta_refrigerado(boolean esta_refrigerado){this.esta_refrigerado = esta_refrigerado;}
    public void setPesoOcupado(int pesoOcupado) {
        this.pesoOcupado = pesoOcupado;
    }
    public void agregarPaquete(Paquete p){
        paquetesCargados.add(p);
        this.pesoOcupado += p.getPeso_kg();
    }
    public void borrarPaquete(Paquete p){
        if(paquetesCargados.contains(p)){
            paquetesCargados.remove(p);
            this.pesoOcupado -= p.getPeso_kg();
        }
    }
    public void vaciarCamion() {
        this.paquetesCargados.clear();
        this.pesoOcupado = 0;
    }
}