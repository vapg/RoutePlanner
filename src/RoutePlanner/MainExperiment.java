package RoutePlanner;

public class MainExperiment {

    static final int NUMERO_AGENTES = 18000;  //18000
    private static final int PERIODOS = 9;
    private static final int NUMERO_SIMULACIONES = 10; //todo 800
    public static int SIM_ACTUAL = 0;

    public static void main(String[] args) {
        ProvinceFactory.inicializar();

        Simulation s = new Simulation();

        for (int i = 1; i <= NUMERO_SIMULACIONES; ++i) {
            SIM_ACTUAL = i;
            if (i%5 == 0) {System.out.println("Simulacion: " + i);}
            s.reinit(NUMERO_AGENTES, PERIODOS, "SIMULACION" + "_" + NUMERO_SIMULACIONES, true);
            s.ejecutar();
            s.generarExcel();
        }

        //s.generarExcel();
    }

}
