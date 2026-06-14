import java.util.ArrayList;
import java.util.HashMap;
public class Servicios {
    private Solucion mejorSolucionBack;
    private int mejorPesoNoAsignado;
    private int estadosGenerados;
    private ArrayList<Camion> camiones;
    private ArrayList<Paquete> paquetes;
    private HashMap<String, Paquete> mapaPaquete;
    public Servicios(ArrayList<Camion> camiones, ArrayList<Paquete> paquetes) {
        this.camiones = camiones;
        this.paquetes = paquetes;
        
        this.mapaPaquete = new HashMap<>();
        for(Paquete p : this.paquetes){
            mapaPaquete.put(p.getCodigo_paquete(), p);
        }
    }

    public Paquete servicio1(String cod) {
        return this.mapaPaquete.get(cod.trim());
    }

    public ArrayList<Paquete> servicio2(boolean buscaAlimento) {
        ArrayList<Paquete> filtrados = new ArrayList<>();
        
        for(Paquete p : this.paquetes) {
            if(p.contiene_alimentos() == buscaAlimento) {
                filtrados.add(p);
            }
        }
        return filtrados;  
    }

    public ArrayList<Paquete> servicio3(int minimaUrgencia, int maximaUrgencia) {
        ArrayList<Paquete> filtrados = new ArrayList<>();
        for (Paquete p : this.paquetes) {
            if (p.getNivel_urgencia() >= minimaUrgencia && p.getNivel_urgencia() <= maximaUrgencia) {
               filtrados.add(p);
            }
        }
        return filtrados;
    }
     /*Se desea establecer una asignación de todos los paquetes a los camiones
    disponibles con el objetivo de minimizar el peso total de los paquetes que no
    pudieron ser asignados a ningún camión.
    Se sabe que existen ciertas restricciones para asignar un paquete a un camión:
    •
    Primero, ningún camión podrá superar su capacidad máxima de carga. La
    capacidad de cada camión está definida en el archivo de entrada.
    •
    Segundo, los paquetes que contienen alimentos sólo podrán ser asignados
    a camiones refrigerados*/
    public Solucion asignarPaquetesBack(){
        this.me
        back(asignados, mejorAsignacion, 0, 0, 0);
    }
    private void back(ArrayList<Paquete> asignados, ArrayList<Paquete> mejorAsignacion, int pesoActual, int mejorPeso, int index){

        if(index == paquetes.size()){
            if(pesoActual > mejorPeso){
                mejorPeso = pesoActual;
            }
        }




        for(int i = 0; i )
    }
}
