
public class Paquete {
    private String id_paquete;
    private String codigo_paquete;
    private double peso_kg;
    private boolean contiene_alimentos;
    private int nivel_urgencia;
    
    public Paquete(String id_paquete, String codigo_paquete, double peso_kg, boolean contiene_alimentos, int nivel_urgencia){
        this.id_paquete = id_paquete;
        this.codigo_paquete = codigo_paquete;
        this.peso_kg = peso_kg;
        this.contiene_alimentos = contiene_alimentos;
        this.nivel_urgencia = nivel_urgencia;
        
    }

    //getters
    public String getId_paquete() {return id_paquete;}
    public String getCodigo_paquete() {return codigo_paquete;}
    public double getPeso_kg() {return peso_kg;}
    public int getNivel_urgencia() {return nivel_urgencia;}
    public boolean contiene_alimentos(){return contiene_alimentos;}

    //setters
    public void setId_paquete(String id_paquete) {this.id_paquete = id_paquete;}
    public void setCodigo_paquete(String codigo_paquete) {this.codigo_paquete = codigo_paquete;}
    public void setPeso_kg(double peso_kg) {this.peso_kg = peso_kg;}
    public void setNivel_urgencia(int nivel_urgencia) {this.nivel_urgencia = nivel_urgencia;}
    public void setContiene_alimentos(boolean contiene_alimentos){this.contiene_alimentos = contiene_alimentos;}
}   