import servicios.Servicios;
import servicios.Solucion;
import entidades.Paquete;

import java.util.List;
public class Main {
    public static void main(String[] args) {
        
        Servicios gestor= new Servicios("src/csv/Camiones.csv", "src/csv/Paquetes.csv");
        
        // SERVICIO 1
        Paquete p = gestor.servicio1("P001");
        if(p != null){
            System.out.println("Id del paquete: " + p.getId_paquete() + " | Codigo del Paquete: " + p.getCodigo_paquete() +
            "\nPeso del Paquete: " + p.getPeso_kg() + " | Contiene alimento: "
            + p.contiene_alimentos() + " | Nivel de Urgencia: " + p.getNivel_urgencia());
        }else{
            System.out.println("El paquete no existe");
        }
        
        // SERVICIO 2
        List<Paquete> paquetesConComida = gestor.servicio2(true);
        System.out.println("Se encontraron " + paquetesConComida.size() + " paquetes con comida.");
        for(Paquete pc : paquetesConComida){
             System.out.println("Cód: " + pc.getCodigo_paquete() + " | Peso: " + pc.getPeso_kg()  );
        }
        
        //SERVICIO 3
        List<Paquete> nivelUrgencia = gestor.servicio3( 75,105);
         System.out.println("Se encontraron " + nivelUrgencia.size() + " paquetes en ese rango de urgencia.");
        for(Paquete pu : nivelUrgencia){
             System.out.println("Cód: " + pu.getCodigo_paquete() + " | Peso: " + pu.getPeso_kg()  );
        }

        System.out.println("   RESOLUCIÓN CON ALGORITMO GREEDY");
        
        Solucion solucionGreedy = gestor.asignarPaquetesGreedy();
        
        if (solucionGreedy != null) {
            System.out.println(solucionGreedy.toString());
        } else {
            System.out.println("Greedy no pudo generar ninguna solución.");
        }

        System.out.println(" RESOLUCIÓN CON ALGORITMO BACKTRACKING");
        
        Solucion solucionBack = gestor.asignarPaquetesBack();
        
        if (solucionBack != null) {
            System.out.println(solucionBack.toString());
        } else {
            System.out.println("Backtracking no pudo encontrar ninguna solución.");
        }
    }
}