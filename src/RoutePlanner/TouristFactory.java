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

   //     int[] ubicaciones = new int[NUMERO_AGENTES];
        boolean[][] preferencias = new boolean[NUMERO_AGENTES][NUMERO_PREFERENCIAS];

        int contadorUbicacion = 0;
        int contadorPreferencias = 0;
/*
        for (int i = 0; i < NUMERO_AGENTES; ++i) {

            double avanceAgentes = 1.0 * i / NUMERO_AGENTES;

            if (avanceAgentes >= UBICACIONES_INICIALES[1][contadorUbicacion]) {
                ++contadorUbicacion;
            }

            if (avanceAgentes >= PROBABILIDADES_PREFERENCIAS[3][contadorPreferencias]) {
                ++contadorPreferencias;
            }

            ubicaciones[i] = (int) UBICACIONES_INICIALES[0][contadorUbicacion];

            preferencias[i][0] = PROBABILIDADES_PREFERENCIAS[0][contadorPreferencias] == 1;
            preferencias[i][1] = PROBABILIDADES_PREFERENCIAS[1][contadorPreferencias] == 1;
            preferencias[i][2] = PROBABILIDADES_PREFERENCIAS[2][contadorPreferencias] == 1;
        }

        //ArrayList<Integer> ubicacionesList = new ArrayList<Integer>(Arrays.asList(ubicaciones));
/*
        /*
        List<Integer> list = new ArrayList<Integer>();
        Collections.addAll(list, Arrays.stream(ints).boxed().toArray(Integer[]::new));
        */
/*
        List<Integer> ubicacionesList =  Arrays.stream(ubicaciones).boxed().collect(Collectors.toList());
        Collections.shuffle(ubicacionesList);

        for (int i = 0; i < NUMERO_AGENTES; ++i) {
            int ub = ubicacionesList.get(i);
            turistas.add(new Turista(getPresupuesto(), preferencias[i], ub));
        }


  */    int ubicacion = 0;

        for (int i = 0; i < NUMERO_AGENTES; ++i) {
            //contadorPreferencias = i;
            //double avanceAgentes = 1.0 * i / NUMERO_AGENTES;

            //if (avanceAgentes >= 0.5) {
            //    ubicacion = 9;
            //}



            if (contadorPreferencias > 6) {
                contadorPreferencias = 0;
                //contadorUbicacion = 0;
            }


            //ubicacion = UBICACIONES_INICIALES[contadorUbicacion];

            preferencias[i][0] = PROBABILIDADES_PREFERENCIAS[0][contadorPreferencias] == 1;
            preferencias[i][1] = PROBABILIDADES_PREFERENCIAS[1][contadorPreferencias] == 1;
            preferencias[i][2] = PROBABILIDADES_PREFERENCIAS[2][contadorPreferencias] == 1;

            int tipoAgente = contadorPreferencias;  //contador es el tipo

            tourists.add(new Tourist(preferencias[i], -1, tipoAgente));
            ++contadorPreferencias;
            ++contadorUbicacion;
        }

        return tourists;
    }

}
