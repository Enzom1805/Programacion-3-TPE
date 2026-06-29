package servicios;

import entidades.Paquete;
import entidades.Camion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
public class Servicios {
    //paquetes
     private TreeMap<Integer, List<Paquete>> paquetesPorUrgencia;
    private HashMap<String, Paquete> paquetesPorCodigo;
    private List<Paquete> conAlimentos;
    private List<Paquete> sinAlimentos;
    private List<Paquete> paquetes;

    //camiones
    private Solucion mejorSolucionBack;
    private int mejorPesoNoAsignado;
    private int estadosGenerados;
    private ArrayList<Camion> camiones;
    private int candidatosConsiderados;
    
    public Servicios(String pathCamiones, String pathPaquetes) { 
        this.paquetesPorUrgencia = new TreeMap<>();
        this.paquetesPorCodigo = new HashMap<>();
        this.conAlimentos = new ArrayList<>();
        this.sinAlimentos = new ArrayList<>();
        this.paquetes = new ArrayList<>();

        this.camiones = new ArrayList<>();

        cargarCamiones(pathCamiones);
        cargarPaquetes(pathPaquetes);
    }

    private void cargarPaquetes(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String linea = br.readLine();

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");

                if(datos.length >= 5){
                int id_paquete = Integer.parseInt(datos[0].trim());
                String codigo_paquete = datos[1].trim();
                int peso_kg = Integer.parseInt(datos[2].trim());
                boolean contiene_alimentos = datos[3].trim().equals("1");
                int nivel_urgencia = Integer.parseInt(datos[4].trim());

                Paquete p = new Paquete(id_paquete, codigo_paquete, peso_kg, contiene_alimentos, nivel_urgencia);

                paquetes.add(p);
                // Servicio 1 (O(1))
                paquetesPorCodigo.put(codigo_paquete, p);

                // Para Servicio 2
                if (contiene_alimentos) {
                    conAlimentos.add(p);
                } else {
                    sinAlimentos.add(p);
                }

                if (!paquetesPorUrgencia.containsKey(nivel_urgencia)) {
                    paquetesPorUrgencia.put(nivel_urgencia, new ArrayList<>());
                }
                paquetesPorUrgencia.get(nivel_urgencia).add(p);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void cargarCamiones(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String linea = br.readLine();

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                if (datos.length >= 4) {
                int id_camion = Integer.parseInt(datos[0].trim());
                String patente = datos[1].trim();
                boolean esta_refrigerado = datos[2].trim().equals("1");
                int capacidad_kg = Integer.parseInt(datos[3].trim());

                Camion c = new Camion(id_camion, patente, esta_refrigerado, capacidad_kg);


                camiones.add(c);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    // Complejidad computacional servicio 1: O(1)
    public Paquete servicio1(String codigo_paquete) {
        return paquetesPorCodigo.get(codigo_paquete);
    }

    // Complejidad computacional servicio 2: O(1)
    public List<Paquete> servicio2(boolean contiene_alimentos) {
        if (contiene_alimentos) {
            return conAlimentos;
        } else {
            return sinAlimentos;
        }
    }   

    // Complejidad computacional servicio 3:
    public List<Paquete> servicio3(int minimaUrgencia, int maximaUrgencia) {
        List<Paquete> filtrados = new ArrayList<>();
        for (Paquete p : this.paquetes) {
            if (p.getNivel_urgencia() >= minimaUrgencia && p.getNivel_urgencia() <= maximaUrgencia) {
               filtrados.add(p);
            }
        }
        return filtrados;
    }

    /*<Explicación del algoritmo de Backtracking;
    - Poda: Si durante la recursión el peso no asignado actual iguala o supera nuestro
    "mejorPesoNoAsignado" histórico, cortamos esa rama inmediatamente (return) para ahorrar tiempo.
    - Proceso: Tomamos un paquete y probamos meterlo en cada camión válido. Avanzamos al siguiente
    paquete recursivamente y luego deshacemos el paso (lo sacamos del camión) para probar otra combinación.
    También probamos la rama donde el paquete NO se asigna a ningún camión.
    - Caso Base: Al terminar de evaluar todos los paquetes, si logramos un mejor resultado,
    hacemos una copia profunda (nuevaCopiaDeSolucion) para guardar la lista de
    los camiones ganadores antes de que la recursividad los vuelva a vaciar.
    >*/

    // COMPLEJIDAD COMPUTACIONAL (N = cantidad de paquetes, M = cantidad de camiones):
    // Complejidad computacional algoritmo backtracking: O((M + 1)^N) 
    public Solucion asignarPaquetesBack(){
        for(Camion c : camiones){
            c.vaciarCamion(); 
        }

        this.mejorSolucionBack = null;
        this.mejorPesoNoAsignado = Integer.MAX_VALUE;
        this.estadosGenerados = 0;

        List<Paquete> todosLosPaquetes = new ArrayList<>(paquetesPorCodigo.values());
        back(todosLosPaquetes, 0, 0);

        return mejorSolucionBack;
    }
    private void back(List<Paquete> todosLosPaquetes, int pesoNoAsignActual, int index){
        this.estadosGenerados++;

        if(pesoNoAsignActual >= mejorPesoNoAsignado) return;
        
        if(index == todosLosPaquetes.size()) {
            mejorPesoNoAsignado = pesoNoAsignActual;
            mejorSolucionBack = generarSolucion(pesoNoAsignActual,estadosGenerados);
            return;
        }

        Paquete p = todosLosPaquetes.get(index);

        for(Camion camionActual: camiones){
            if(esAsignable(camionActual,p)){
                camionActual.agregarPaquete(p);

                back(todosLosPaquetes, pesoNoAsignActual, index + 1);

                camionActual.borrarPaquete(p);
            }
        }

        back(todosLosPaquetes, pesoNoAsignActual + p.getPeso_kg(), index + 1);
    }
    private Solucion generarSolucion(int pesoNoAsignado, int estados) {
        List<Camion> copiaExactaCamiones = new ArrayList<>();

        for (Camion c : camiones) {

            Camion copiaCamion = new Camion(c.getId_camion(), c.getPatente(), c.esta_refrigerado(), c.getCapacidad_kg());

            for (Paquete p : c.getPaquetesCargados()) {
                copiaCamion.agregarPaquete(p);
            }

            copiaExactaCamiones.add(copiaCamion);
        }

        return new Solucion(copiaExactaCamiones, pesoNoAsignado, estados);
    }

    private boolean esAsignable(Camion camionActual, Paquete paqueteActual){
        if(!camionActual.esta_refrigerado() && paqueteActual.contiene_alimentos()) return false;

        if(camionActual.getPesoOcupado() + paqueteActual.getPeso_kg() > camionActual.getCapacidad_kg()) return false;

        return true;
    }


    /*<Explicación del algoritmo Greedy: 
    Función y justificación de selección:
    - Para los paquetes: Los ordenamos inicialmente de mayor a menor peso. Es más eficiente
     * ubicar primero los paquetes más grandes y pesados cuando los camiones están vacíos,
     * dejando los livianos para rellenar los huecos al final.
     * - Para los camiones: Recorremos los camiones y ubicamos el paquete en
     * el primero que tenga capacidad. Ademas le sumamos: si el paquete NO contiene
     * alimentos, evitamos meterlo en camiones refrigerados para guardar ese espacio.
     * Si luego de revisar todos los camiones el paquete no entró, se suma al peso no asignado.
     *
     *
     * Complejidad temporal: O(P log P + P * C)
     * Explicación: O(P log P) por el ordenamiento inicial de los paquetes, más O(P * C) porque, en el
     * peor caso, itero los 'C' camiones por cada uno de los 'P' paquetes en la lista.
    >*/
    public Solucion asignarPaquetesGreedy() {
        this.candidatosConsiderados = 0;

        for (Camion c : camiones) {
            c.vaciarCamion();
        }

        List<Paquete> candidatos = new ArrayList<>(paquetesPorCodigo.values());

        candidatos.sort((p1, p2) -> Integer.compare(p2.getPeso_kg(), p1.getPeso_kg()));

        int pesoNoAsignado = 0;

        for (Paquete p : candidatos) {
            boolean fueAsignado = false;
            for (Camion c : camiones) {
                this.candidatosConsiderados++;
                 if (!p.contiene_alimentos() && c.esta_refrigerado()) {
                    continue;
                }
                if (esAsignable(c, p)) {
                    c.agregarPaquete(p);
                    fueAsignado = true;
                    break;
                }
            }
            if (!fueAsignado) {
                pesoNoAsignado += p.getPeso_kg();
            }
        }

        return generarSolucion(pesoNoAsignado, candidatosConsiderados);
    }
}