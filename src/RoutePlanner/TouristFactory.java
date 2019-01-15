package RoutePlanner;

import java.util.ArrayList;

import static RoutePlanner.MainExperiment.*;

class TouristFactory {

    static final double[][] PROBABILIDADES_PREFERENCIAS = new double[][]{
            {1, 0, 1, 1, 0, 1, 0},
            {1, 1, 1, 0, 0, 0, 1},
            {1, 1, 0, 1, 1, 0, 0},
            {0.133, 0.1738, 0.3654, 0.9128, 0.9131, 0.9168, 0.9835, 1}};

    public static final int NUMERO_PREFERENCIAS = PROBABILIDADES_PREFERENCIAS.length - 1;

    //grupos de turistas
    public static final int GCV = 0;
    public static final int CV = 1;
    public static final int GC = 2;
    public static final int GV = 3;
    public static final int V = 4;
    public static final int G = 5;
    public static final int C = 6;
    public static final int NUMERO_TIPOS_TURISTAS = 7;


    static ArrayList<Tourist> crearTuristas() {
        ArrayList<Tourist> tourists = new ArrayList<Tourist>();

        boolean[][] preferencias = new boolean[NUMERO_AGENTES][NUMERO_PREFERENCIAS];

        int contadorPreferencias = 0;


        for (int i = 0; i < NUMERO_AGENTES; ++i) {

            if (contadorPreferencias > 6) {
                contadorPreferencias = 0;
            }

            preferencias[i][0] = PROBABILIDADES_PREFERENCIAS[0][contadorPreferencias] == 1;
            preferencias[i][1] = PROBABILIDADES_PREFERENCIAS[1][contadorPreferencias] == 1;
            preferencias[i][2] = PROBABILIDADES_PREFERENCIAS[2][contadorPreferencias] == 1;

            int tipoAgente = contadorPreferencias;

            tourists.add(new Tourist(preferencias[i], -1, tipoAgente));
            ++contadorPreferencias;
        }

        return tourists;
    }

}
