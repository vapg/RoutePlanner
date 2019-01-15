package RoutePlanner;

public class Main {

    //todo configuration
    static final int NUMERO_AGENTES = 7;  //18000
    static final int PERIODOS = 21;
    
    
    public static void main(String[] args) {
        ProvinceFactory.inicializar();

        Simulation s = new Simulation(NUMERO_AGENTES, PERIODOS, "SIMULACION");
        s.ejecutar();
        s.generarExcel();
    }
}
