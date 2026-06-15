    import java.io.BufferedReader;
    import java.io.FileReader;
    import java.io.IOException;
    import java.util.ArrayList;
    public class LectorDeArchivos {
    
        
    
    public ArrayList<Camion> obtenerCamiones(String ruta) {
        ArrayList<Camion> listaCamiones = new ArrayList<>();
        String linea;
        String separador = ";";

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            
            // 1. Leemos la primera línea (la cantidad) para sacarla del medio
            br.readLine(); 
            
            // 2. Ahora sí, iteramos sobre el resto de las líneas con datos
            while ((linea = br.readLine()) != null) {
                String[] campos = linea.split(separador);
            if (campos.length >= 4) {    
                // 3. Parseamos los Strings a los tipos de datos correctos
                String id_camion = campos[0];
                String patente = campos[1];
                boolean esta_refrigerado = campos[2].equals("1"); // Si es "1" da true, sino false
                int capacidad = Integer.parseInt(campos[3]);
                
                // 4. Creamos el objeto Camion
                Camion c = new Camion(id_camion, patente, capacidad, esta_refrigerado, 0.0);
                
                // 5. Lo guardamos en la lista
                listaCamiones.add(c);
            }
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // 6. Devolvemos la lista llena
        return listaCamiones;
    }
    
    public ArrayList<Paquete> obtenerPaquetes(String ruta) {
    ArrayList<Paquete> listaPaquetes = new ArrayList<>();
    String linea;
    String separador = ";";

    try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
        
        // Al igual que con camiones, salteamos la primera línea que indica la cantidad
        br.readLine(); 
        
        while ((linea = br.readLine()) != null) {
            String[] campos = linea.split(separador);
            if (campos.length >= 5) {
            // Parseamos las 5 columnas del CSV de paquetes
            String id_paquete = campos[0];
            String codigo = campos[1];
            int peso = Integer.parseInt(campos[2]);
            boolean contiene_alimentos = campos[3].equals("1"); // "1" es true, "0" es false
            int nivel_urgencia = Integer.parseInt(campos[4]);
            
            // Instanciamos y agregamos a la lista
            Paquete p = new Paquete(id_paquete, codigo, peso, contiene_alimentos, nivel_urgencia);
            listaPaquetes.add(p);
        }
    }
    } catch (IOException e) {
        e.printStackTrace();
    }
    
    return listaPaquetes;
    }
}
