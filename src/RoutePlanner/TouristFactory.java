package RoutePlanner;

import java.util.ArrayList;

import static RoutePlanner.MainExperiment.*;

class TouristFactory {

    private static final double[][] PREFERENCES_COMBINATION = new double[][]{
            {1, 0, 1, 1, 0, 1, 0},
            {1, 1, 1, 0, 0, 0, 1},
            {1, 1, 0, 1, 1, 0, 0},};

    static final int NUMERO_PREFERENCIAS = PREFERENCES_COMBINATION.length;

    //grupos de turistas
    static final int GCV = 0;
    static final int CV = 1;
    static final int GC = 2;
    static final int GV = 3;
    static final int V = 4;
    static final int G = 5;
    static final int C = 6;
    static final int NUMERO_TIPOS_TURISTAS = 7;


    static ArrayList<Tourist> crearTuristas() {
        ArrayList<Tourist> tourists = new ArrayList<Tourist>();

        boolean[][] preferencias = new boolean[NUMERO_AGENTES][NUMERO_PREFERENCIAS];

        int contadorPreferencias = 0;


        for (int i = 0; i < NUMERO_AGENTES; ++i) {

            if (contadorPreferencias > 6) {
                contadorPreferencias = 0;
            }
            
            preferencias[i][0] = PREFERENCES_COMBINATION[0][contadorPreferencias] == 1;
            preferencias[i][1] = PREFERENCES_COMBINATION[1][contadorPreferencias] == 1;
            preferencias[i][2] = PREFERENCES_COMBINATION[2][contadorPreferencias] == 1;

            int tipoAgente = contadorPreferencias;

            tourists.add(new Tourist(preferencias[i], tipoAgente));
            ++contadorPreferencias;
        }

        return tourists;
    }

}
