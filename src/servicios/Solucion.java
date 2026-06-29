package servicios;

import entidades.Camion;
import entidades.Paquete;
import java.util.ArrayList;
import java.util.List;

public class Solucion {
    private List<Camion> camionesAsignados;
    private int pesoNoAsignado;
    private int metrica;

    public Solucion(List<Camion> camiones, int pesoNoAsignado, int metrica) {
        this.camionesAsignados = camiones;
        this.pesoNoAsignado = pesoNoAsignado;
        this.metrica = metrica;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Solucion obtenida:\n");

        for(Camion c : camionesAsignados){
            sb.append(" - Camión").append(c.getPatente()).append(" lleva: ");
            if(c.getPaquetesCargados().isEmpty()){
                sb.append("vacio");
            }
            else{
                for(Paquete p : c.getPaquetesCargados()){
                    sb.append("[").append(p.getCodigo_paquete()).append("]");
                }
            }
            sb.append("\n");
        }
        sb.append("peso no asignado: ").append(pesoNoAsignado).append(" kg. \n");
        sb.append("métrica para analizar el costo de la solución: ").append(metrica).append("\n");

        return sb.toString();
    }
    public void setCamionesAsignados(ArrayList<Camion> camionesAsignados) {
        this.camionesAsignados = camionesAsignados;
    }
    public void sumarPesoNoAsignado(double pesoPaquete){
        this.pesoNoAsignado += pesoPaquete;
    }
    public void setMetrica(int metrica) {
        this.metrica = metrica;
    }
}
