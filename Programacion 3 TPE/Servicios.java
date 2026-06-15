import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
public class Servicios {
    //paquetes
    /*private TreeMap<Integer, ArrayList<Paquete>> paquetesPorUrgencia;
    private HashMap<String, Paquete> paquetesPorCodigo;*/
    private ArrayList<Paquete> paquetes;
    private HashMap<String, Paquete> mapaPaquete;

    //camiones
    private Solucion mejorSolucionBack;
    private double mejorPesoNoAsignado;
    private int estadosGenerados;
    private ArrayList<Camion> camiones;
    
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

    //explicacion BACKTRACKING
    public Solucion asignarPaquetesBack(){
        this.mejorSolucionBack = null;
        this.mejorPesoNoAsignado = Double.MAX_VALUE;
        this.estadosGenerados = 0;
        back(0.0, 0);

        return mejorSolucionBack;
    }
    private void back(double pesoNoAsignActual, int index){
        this.estadosGenerados++;

        if(pesoNoAsignActual >= this.mejorPesoNoAsignado) return;
        
        if(index == paquetes.size()){
            if(pesoNoAsignActual < this.mejorPesoNoAsignado){
                mejorPesoNoAsignado = pesoNoAsignActual;
                mejorSolucionBack = generarSolucion(pesoNoAsignActual,estadosGenerados);
            }
        }

        Paquete p = paquetes.get(index);

        for(int i = 0; i < camiones.size(); i++){
            Camion camionActual = camiones.get(i);
            if(esAsignable(camionActual,p)){
                camionActual.agregarPaquete(p);

                back(pesoNoAsignActual, index + 1);

                camionActual.borrarPaquete(p);
            }
        }

        back(pesoNoAsignActual + p.getPeso_kg(), index);

    }
    private Solucion generarSolucion(double pesoNoAsignActual, int metrica){
        Solucion S = new Solucion(pesoNoAsignActual, metrica);
        ArrayList<Camion> copiaCamion = new ArrayList<>();

        for(Camion c : camiones){
            Camion copia = new Camion(c.getId_camion(),c.getPatente(),c.getCapacidad_kg(),c.esta_refrigerado(),c.getPesoOcupado());
            for(Paquete p : c.getPaquetesCargados()){
                copia.agregarPaquete(p);
            }
            copiaCamion.add(copia);
        }

        S.setCamionesAsignados(copiaCamion);
        
        return S;
    }
    private boolean esAsignable(Camion camionActual, Paquete paqueteActual){
        if(!camionActual.esta_refrigerado() && paqueteActual.contiene_alimentos()) return false;

        if(camionActual.getPesoOcupado() + paqueteActual.getPeso_kg() > camionActual.getCapacidad_kg()) return false;

        return true;
    }

    //explicacion GREDDYYYY
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
    public void asignarPaquetesGreedy(){

    }
}
