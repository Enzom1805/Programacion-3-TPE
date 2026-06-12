import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        
       
        LectorDeArchivos lector = new LectorDeArchivos();
        ArrayList<Camion> misCamiones = lector.obtenerCamiones("Camiones.csv");
        ArrayList<Paquete> misPaquetes = lector.obtenerPaquetes("Paquetes.csv");
        
       
        Servicios gestor = new Servicios(misCamiones, misPaquetes);
        
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
        ArrayList<Paquete> paquetesConComida = gestor.servicio2(true);
        System.out.println("Se encontraron " + paquetesConComida.size() + " paquetes con comida.");
        for(Paquete pc : paquetesConComida){
             System.out.println("Cód: " + pc.getCodigo_paquete() + " | Peso: " + pc.getPeso_kg()  );
        }
        
        //SERVICIO 3
        ArrayList<Paquete> nivelUrgencia = gestor.servicio3( 75,105);
         System.out.println("Se encontraron " + nivelUrgencia.size() + " paquetes en ese rango de urgencia.");
        for(Paquete pu : paquetesConComida){
             System.out.println("Cód: " + pu.getCodigo_paquete() + " | Peso: " + pu.getPeso_kg()  );
        }
    }
}