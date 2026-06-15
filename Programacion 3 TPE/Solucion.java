import java.util.ArrayList;

public class Solucion {
    private ArrayList<Camion> camionesAsignados;
    private double pesoNoAsignado;
    private int metrica;

    public Solucion(double peso,int metrica){ 
        this.camionesAsignados = new ArrayList<>();
        this.pesoNoAsignado = peso;
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
}
