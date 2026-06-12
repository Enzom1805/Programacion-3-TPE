import java.util.ArrayList;
import java.util.HashMap;
public class Servicios {
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
}
