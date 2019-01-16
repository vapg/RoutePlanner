package RoutePlanner;

public class MainExperiment {

    static int NUMERO_AGENTES = 100;
    static int PERIODOS = 9;
    static int NUMERO_SIMULACIONES = 1;
    static String DESCRIPTION = "RESULTS_SIMULATION" + "_" + NUMERO_SIMULACIONES;
    public static int SIM_ACTUAL = 0;

    public static void main(String[] args) {
        
        if (args.length > 0) {
            NUMERO_AGENTES = Integer.parseInt(args[0]);
            PERIODOS = Integer.parseInt(args[1]);
            NUMERO_SIMULACIONES = Integer.parseInt(args[2]);
            DESCRIPTION = args[3];
            //System.out.println(NUMERO_AGENTES+ " "+ PERIODOS+ " " +NUMERO_SIMULACIONES+ " " + DESCRIPTION);
        }

        ProvinceFactory.inicializar();
        Simulation s = new Simulation();

        for (int i = 1; i <= NUMERO_SIMULACIONES; ++i) {
            SIM_ACTUAL = i;
            if (i%5 == 0) {System.out.println("Simulacion: " + i);}
            s.reinit(NUMERO_AGENTES, PERIODOS, DESCRIPTION, true);
            s.ejecutar();
            s.generarExcel();
        }
    }

}
