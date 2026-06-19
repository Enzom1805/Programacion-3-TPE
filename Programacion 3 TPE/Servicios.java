import java.util.ArrayList;
import java.util.HashMap;
public class Servicios {
    //paquetes
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

    // Complejidad computacional servicio 1:
    public Paquete servicio1(String cod) { 
        return this.mapaPaquete.get(cod.trim());
    }

    // Complejidad computacional servicio 2:
    public ArrayList<Paquete> servicio2(boolean buscaAlimento) {
        ArrayList<Paquete> filtrados = new ArrayList<>();
        
        for(Paquete p : this.paquetes) {
            if(p.contiene_alimentos() == buscaAlimento) {
                filtrados.add(p);
            }
        }
        return filtrados;  
    }

    // Complejidad computacional servicio 3:
    public ArrayList<Paquete> servicio3(int minimaUrgencia, int maximaUrgencia) {
        ArrayList<Paquete> filtrados = new ArrayList<>();
        for (Paquete p : this.paquetes) {
            if (p.getNivel_urgencia() >= minimaUrgencia && p.getNivel_urgencia() <= maximaUrgencia) {
               filtrados.add(p);
            }
        }
        return filtrados;
    }

    /*<Explicación del algoritmo de Backtracking;
    Lo primero que arrancamos haciendo fue vaciar la lista de camiones asignados porque nos clonaba paquetes anteriores, 
    con ese for, solucionamos el error.
    Incializamos la mejor solucion como nula, el mejor peso no asignado le dimos el valor más alto posible y estado generados
    tomó el valor de 0, llamamos al método back y le mandamos por parámetros dos variables, un double que va a ir sumando
    el peso no asignado, y el index.
    Ni bien comienza el método, aumentamos los estados generados, y hacemos la condición de poda, si el peso no asignado es mayor
    o igual al mejor peso no asignado, corto la rama, porque no me interesa seguir cuando se que el peso no asignado que tengo 
    actualmente es peor que el mejor peso.
    Una vez que llego al final de mi arreglo de paquetes disponibles, si el peso no asignado es menor al mejor peso, mi atributo de clase,
    toma el valor de peso no asignado actual, y la mejor solucion back guarda una réplica exacta de la distribución actual mediante una copia profunda de los camiones, 
    rompiendo la referencia en memoria para que no se borren los paquetes cuando el algoritmo vuelva hacia atrás.
    Una vez hecha la poda y caso base, tomo un paquete de los disponibles, y recorro los camiones, ahora tengo que ver si se puede asignar,
    teniendo el camion de la posicion i, y el paquete previamente obtenido, me fijo si;
    - el paquete contiene alimentos y el camión no está refrigerado, si es así, no es asignable.
    - la capacidad restante del camión, sumada el peso del paquete seleccionado es mayor a la capacidad total del camión, si es así, no es asignable.
    - si no devolví false, es que mi paquete no contiene alimentos, o que la suma no sobrepasa la capacidad, o ambas.
    Una vez que chequeo que cumplo los requisitos necesarios, le asigno el paquete al camión, llamo recursivamente al método back,
    con los parámetros de peso no asignado actual (sin modificar), y mi index + 1 para que siga avanzando sobre el arreglo de paquetes,
    luego de esto, saco el pquete del camión para seguir probando todas las opciones posibles.
    Por último, llamo al método recursivo nuevamente, pero cambiando los parametros, a peso no asignado actual, 
    le sumo el peso de paquete no asignado y también mando el index + 1, para seguir recorriendo los paquetes disponibles.
    >*/

    // COMPLEJIDAD COMPUTACIONAL (N = cantidad de paquetes, M = cantidad de camiones):
    // Complejidad computacional algoritmo backtracking: O((M + 1)^N) 
    public Solucion asignarPaquetesBack(){
        for(Camion c : camiones){
            c.vaciarCamion(); 
        }

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
            return;
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

        back(pesoNoAsignActual + p.getPeso_kg(), index + 1);

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


    /*<Explicación del algoritmo Greedy: 
    Función y justificación de selección:
    - Para los paquetes: Elegimos siempre el paquete más pesado disponible. 
    Es más eficiente ubicar primero los elementos más pesados
    cuando los camiones están vacíos, dejando los paquetes livianos para rellenar 
    los huecos más chicos al final.
    - Para los camiones: Elegimos el camión con mayor capacidad restante libre (worst fit).
    Al usar el camión con más espacio, evitamos saturar un contenedor 
    rápidamente y nos aseguramos de dejar huecos grandes disponibles para los 
    siguientes paquetes pesados.
    Teniendo en cuenta el paquete previamente obtenido, evaluamos si el camión puede llevarlo, y las condiciones para seleccionarlo son; 
    - si el paquete contiene alimentos pero el camión no está refrigerado, entonces no puede llevar el paquete seleccionado.
    - si el peso que quiero subir a mi camión es mayor a la capacidad, entonces no puede llevar el paquete seleccionado.
    y una vez que evaluo que la función esAsignable se cumple, voy buscando en mi arreglo de camiones, cual es el que tiene mayor
    capacidad restante, comparando las diferencias entre capacidad y peso ocupado.
    Una vez obtenido el camion (chequeo que no sea null) le agrego el paquete obtenido al principio, en caso de que no se haya
    encontrado un camion que cumpla con los requisitos necesarios, entonces modifico el valor no asignado en mi clase Solucion,
    porque significa que no pude colocar el paquete en ningún camión.
    Cuando termine mi bucle while, agrego los camiones que si pudieron ser asignados, setteo la métrica de la solución
    mandandole por parámetro la cantidad de estados generados y por último devuelvo la solución. 
    >*/

    // COMPLEJIDAD COMPUTACIONAL (N = cantidad de paquetes, M = cantidad de camiones): 
    // Complejidad computacional algoritmo greedy: O(N^2 + N * M)
    public Solucion asignarPaquetesGreedy(){
        for(Camion c : camiones){
            c.vaciarCamion();
        }
        Solucion S = new Solucion(0.0,0);
        ArrayList<Paquete> paquetesDisp = new ArrayList<>(paquetes);

        while(!paquetesDisp.isEmpty()){
            this.estadosGenerados++;
            
            Paquete x = seleccionarPaquete(paquetesDisp);
            paquetesDisp.remove(x);

            Camion camionElegido = buscarCamionFactible(x);

            if(camionElegido != null){
                camionElegido.agregarPaquete(x);
            }
            else{
                S.sumarPesoNoAsignado(x.getPeso_kg());
            }
        }
        S.setCamionesAsignados(camiones);
        S.setMetrica(estadosGenerados);
        return S;
    }
    private Paquete seleccionarPaquete(ArrayList<Paquete> paquetesDisp){
        Paquete mayor = null;
        for(Paquete p: paquetesDisp){
            if(mayor == null || p.getPeso_kg() > mayor.getPeso_kg()){
                mayor = p;
            }
        }

        return mayor;
    }
    private Camion buscarCamionFactible(Paquete p){
        Camion ideal = null;

        for(Camion c : camiones){
            if(esAsignable(c, p)){
                if(ideal == null || (c.getCapacidad_kg() - c.getPesoOcupado()) > (ideal.getCapacidad_kg() - ideal.getPesoOcupado())){
                    ideal = c;
                }
            }
        }

        return ideal;
    }
}
