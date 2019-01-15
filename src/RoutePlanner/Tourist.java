package RoutePlanner;


import java.util.*;

import static RoutePlanner.TouristFactory.NUMERO_PREFERENCIAS;
import static RoutePlanner.ProvinceFactory.NUMERO_PROVINCIAS;
import static RoutePlanner.Threshold.*;

public class Tourist {
    private static int userID = 0;

    private int id;
    private boolean activo;
    private boolean[] preferencias;
    private int ubicacion;
    private int ubicacionanterior;
    private double satisfaccion;
    private Logging logging;
    //private boolean[] provSatisfaccion;
    private double[] provSatisfaccion;
    private int[] estadias;
    private int tipoAgente;

    Tourist(boolean[] preferencias, int ubicacion, int tipoAgente) {

        this.activo = true;
        this.preferencias = preferencias;
        this.tipoAgente = tipoAgente;

        //this.ubicacion = ProvinceFactory.MADRID;

        //System.out.println("ubi:" + ubicacion + " ubix:" + this.ubicacion);

        this.id = ++userID;
        this.logging = new Logging();

        provSatisfaccion = new double[NUMERO_PROVINCIAS];
        estadias = new int[NUMERO_PROVINCIAS];

        //this.ubicacion = obtenerAzarMayorSatisfaccion();


        for (int i = 0; i < NUMERO_PROVINCIAS; ++i) {
            double prefSatis = calcularPreferencias(i);
            if (prefSatis > UMBRALESSATISFACCIONES[tipoAgente][2]-0.1) {
                this.ubicacion = i;
                break;
            }
        }

            //System.out.println("UMBRALES: " + UMBRALESSATISFACCIONES[tipoAgente][0] + "," + UMBRALESSATISFACCIONES[tipoAgente][1] + "," +  UMBRALESSATISFACCIONES[tipoAgente][2]);

        for (int i = 0; i < NUMERO_PROVINCIAS; ++i) {
            double prefSatis = calcularPreferencias(i);

            provSatisfaccion[i] = prefSatis < UMBRALESSATISFACCIONES[tipoAgente][0]? UMBRALESSATISFACCIONES[tipoAgente][0]:
                                  prefSatis < UMBRALESSATISFACCIONES[tipoAgente][1]? UMBRALESSATISFACCIONES[tipoAgente][1]:
                                          prefSatis <= UMBRALESSATISFACCIONES[tipoAgente][2]? UMBRALESSATISFACCIONES[tipoAgente][2]: -1000;


            //System.out.println("INNER   Satis:" + prefSatis + "   prov:" + provSatisfaccion[i] + " ciudad:" + i + " Agente:" + tipoAgente);

            estadias[i] = 0;
        }



        this.satisfaccion = calcularPreferencias(this.ubicacion);

        //System.out.println("ubi:" + ubicacion + "   ubix:" + this.ubicacion);

        //filtrarPorProbabilidadVisita();

        //System.out.println("tipo:"+ tipoAgente + "    factibles:" + contarProvinciasFactibles());
    }

    @Override
    public int hashCode() { return id; }



    private void filtrarPorProbabilidadVisita() {
      for(int i = 0; i < NUMERO_PROVINCIAS; ++i) {
          provSatisfaccion[i] = Math.random() <= ProvinceFactory.getProbabilidadVisita(i, tipoAgente)? provSatisfaccion[i]: -1*provSatisfaccion[i];
      }
    }


    private void filtroProvPorHuida(int futuraUbicacion) {
        if (ubicacion !=  futuraUbicacion) {
            provSatisfaccion[ubicacion] = -1;
        }
    }

    private int contarProvinciasFactibles() {
        int numero = 0;

        for (int i = 0; i < NUMERO_PROVINCIAS; ++i) {
            if (provSatisfaccion[i] != -1) {
                ++numero;
            }
        }
        return numero;
    }


    private double calcularDistancia(int origen, int destino) {
        return ProvinceFactory.getDistanciaEstadia(origen,destino);
    }

    private void filtrar() {
        //filtroProvPorPresupuesto();
        //filtroProvPorEstadia();
    }

    private void debeSalir() {
        for (int i = 0; i < NUMERO_PROVINCIAS; ++i) {
            if (provSatisfaccion[i] >= 0) {
                this.activo = true;
                return;
            }
        }

        //System.out.println("ERRRROROROROROROOROROOROROOR");
        this.activo = false;
    }

    int obtenerAzarMayorSatisfaccion() {
        Random random = new Random();

        ArrayList<Integer> lista = obtenerListaMayorSatisfaccion();
        int size = lista.size();

        return lista.get(random.nextInt(size));
    }

    ArrayList<Integer> obtenerListaMayorSatisfaccion() {
        ArrayList<Integer> provinciasMax = new ArrayList<Integer>();
        double sMax = 0;

        for (int i = 0; i < NUMERO_PROVINCIAS; ++i) {
            sMax = sMax < provSatisfaccion[i] ? provSatisfaccion[i] : sMax;
        }

        for (int i = 0; i < NUMERO_PROVINCIAS; ++i) {
            if (sMax == provSatisfaccion[i]) {
                provinciasMax.add(i);
            }
        }

        return provinciasMax;
    }

    private double calcularPreferencias(int codigoProvincia) {
        double sumaPreferencias = 0.0;

        double[] provAtractivos = ProvinceFactory.getPreferencias(codigoProvincia);

        for (int i = 0; i < NUMERO_PREFERENCIAS; ++i) {
                sumaPreferencias += preferencias[i]? provAtractivos[i] : 0;
        }

        return sumaPreferencias;

        //todo: min[0,1]
        //return sumaPreferencias == 1? 2.0/3.0: sumaPreferencias; //min [0, 1]
    }


    private int obtenerProximaProvincia() {
        double sMax = 0;
        ArrayList<Integer> provinciasMax = new ArrayList<Integer>();
        Random random = new Random();
        int provinciaSeleccionada; //never

        do {
            habilitarTodasProbabilidadVisita();
            filtrarPorProbabilidadVisita();

            //validacion
            provSatisfaccion[ubicacion] = -1; //todo: validacion


            //estadia
            // provSatisfaccion[ubicacion] = random.nextDouble() >
            //        1.0 * obtenerEstadia(ubicacion)/ProvinciaFactory.getMaxEstadia(ubicacion)? provSatisfaccion[ubicacion] : -1;

            //50%
            //provSatisfaccion[ubicacion] = random.nextDouble() >= 0.5? -1.0 : provSatisfaccion [ubicacion]; 50%


            for (int i = 0; i < NUMERO_PROVINCIAS; ++i) {
                sMax = sMax < provSatisfaccion[i] ? provSatisfaccion[i] : sMax;
            }

            for (int i = 0; i < NUMERO_PROVINCIAS; ++i) {
                if (sMax == provSatisfaccion[i]) {
                    provinciasMax.add(i);
                }
            }


            if (provinciasMax.size() == 0) {
                //System.out.println("NO!!!!!!!!! :" + sMax);
                //return -1;
            }
        } while (provinciasMax.size() < 1);

        if (provinciasMax.size() > 1) {
            //provinciaSeleccionada = provinciasMax.get(random.nextInt(provinciasMax.size()));

            double costomin = Double.MAX_VALUE;
            int provinciamin = -1;

            for (int i = 0; i < provinciasMax.size(); ++i) {
                //double costTemp = calcularCostoTransporteEstadia(ubicacion, i);
                double costTemp = calcularDistancia(ubicacion, provinciasMax.get(i));
                if (costomin > costTemp) {
                    costomin = costTemp;
                    provinciamin = provinciasMax.get(i);
                }
            }
            provinciaSeleccionada = provinciamin;

        } else {
            //System.out.println(" Numero de provincias max: " + provinciasMax.size());
            provinciaSeleccionada = provinciasMax.get(0);
        }

        //System.out.println(" Numero de provincias max: " + provinciasMax.size() + "     Seleccionada:" + ProvinciaFactory.getNombre(provinciaSeleccionada) + "           Umbral:" + sMax);
        return provinciaSeleccionada;
    }

    private void habilitarTodasProbabilidadVisita() {
        for (int i = 0; i < NUMERO_PROVINCIAS; ++i) {
            if (provSatisfaccion[i] < 0 && provSatisfaccion[i] != -1) {
                provSatisfaccion[i] = provSatisfaccion[i]*-1;
            }
        }
    }




    /*
    private int obtenerProximaProvincia() {
        double sMax = satisfacciones[ubicacion]; //satisfaccion
        ArrayList<Integer> provinciasMax = new ArrayList<Integer>();
        ArrayList<Pair<Integer, Double>> provinciasCostoMax = new ArrayList<Pair<Integer, Double>>();

        for (int i = 0; i < NUMERO_PROVINCIAS; ++i) {
            sMax = sMax < satisfacciones[i] ? satisfacciones[i] : sMax;
        }

        for (int i = 0; i < NUMERO_PROVINCIAS; ++i) {
            if (sMax == satisfacciones[i]) {
                provinciasMax.add(i);
            }
        }


        if (provinciasMax.size() > 1) {

            for (int i = 0; i < provinciasMax.size(); ++i) {
                Pair<Integer, Double> p = new Pair<Integer, Double>(provinciasMax.get(i),
                        ProvinciaFactory.getCostoEstadia(provinciasMax.get(i)) + ProvinciaFactory.getCostoTransporte(ubicacion, provinciasMax.get(i)));

                provinciasCostoMax.add(p);
            }

            // Sorting
            Collections.sort(provinciasCostoMax, new Comparator<Pair<Integer, Double>>() {
                @Override
                public int compare(Pair<Integer, Double> p1, Pair<Integer, Double> p2) {
                    return (int) (p1.getValue() - p2.getValue());
                }
            });


            int inicioFiltro = ubicacion == provinciasCostoMax.get(0).getKey()? 1: 0;

            double limiteDiferencia = (provinciasCostoMax.get(provinciasCostoMax.size() - 1).getValue() -
                    provinciasCostoMax.get(inicioFiltro).getValue())/3.0 + provinciasCostoMax.get(inicioFiltro).getValue();

            if (provinciasCostoMax.size() > 1) {
                provinciasCostoMax.removeIf(p -> p.getValue() > limiteDiferencia);
            }

            System.out.println("============================:" + provinciasCostoMax.size());
            for (int i = 0; i < provinciasCostoMax.size(); ++i) {
                System.out.println("COSTO " + i + ", " + ProvinciaFactory.getNombre( provinciasCostoMax.get(i).getKey()) +" :" + provinciasCostoMax.get(i).getValue());
            }
            System.out.println("=============================");

            Random r = new Random();
            int indice = r.nextInt((int)(Math.floor(provinciasCostoMax.size())));
            int provinciaSeleccionada = provinciasCostoMax.get(indice).getKey();

            System.out.println("Provincia Seleccionada: " + ProvinciaFactory.getNombre(provinciaSeleccionada));
            System.out.println("=============================");

            return provinciasCostoMax.get(indice).getKey();
        } else {
            System.out.println("prov:" + ProvinciaFactory.getNombre(provinciasMax.get(0)));
            return provinciasMax.get(0);
        }
    }


        /*
        System.out.println("COMENZANDO MINIMOS    XXXXDXXXXXXXXXXXX");

        if (provinciasMax.size()>1) {
            double costomin = 1000.0; //todo: reparar
            for (int i = 0; i < provinciasMax.size(); ++i) {
                if (costomin > ProvinciaFactory.getCostoEstadia(provinciasMax.get(i)) + ProvinciaFactory.getCostoTransporte(ubicacion, provinciasMax.get(i))) {
                    costomin = ProvinciaFactory.getCostoEstadia(provinciasMax.get(i)) + ProvinciaFactory.getCostoTransporte(ubicacion, provinciasMax.get(i));
                    System.out.println();
                    System.out.println(costomin+ " " + ProvinciaFactory.getNombre(provinciasMax.get(i)) +  " " +  satisfacciones[provinciasMax.get(i)]);
                }
            }

            System.out.println("FIN MINIMOS    XXXXDXXXXXXXXXXXX");


            ArrayList<Integer> provinciasMin = new ArrayList<Integer>();
            for (int i = 0; i < provinciasMax.size(); ++i) {
                if (costomin == ProvinciaFactory.getCostoEstadia(provinciasMax.get(i)) + ProvinciaFactory.getCostoTransporte(ubicacion, provinciasMax.get(i))) {
                    provinciasMin.add(provinciasMax.get(i));
                }
            }

          */


            //Collections.shuffle(provinciasMin);
            //Integer[] arrayProvinciaMin = provinciasMin.toArray(new Integer[provinciasMin.size()]);

/*
        Arrays.sort(arrayProvinciaMax, new Comparator<Integer>() {
            public int compare(Integer ubicacion1, Integer ubicacion2) {
                double c1 = ProvinciaFactory.getCostoEstadia(ubicacion1) + ProvinciaFactory.getCostoTransporte(ubicacion, ubicacion1);
                double c2 = ProvinciaFactory.getCostoEstadia(ubicacion2) + ProvinciaFactory.getCostoTransporte(ubicacion, ubicacion2);

                return (int) (c1 - c2);
            }
        });
        System.out.print("Imprimiendo orden de provincias (" + id +  "): ");
        for (int i = 0; i < arrayProvinciaMax.length; i++) {
            System.out.print(getNombre(arrayProvinciaMax[i]) + ", ");
        }
        System.out.println();*/
              /*
            System.out.print("Imprimiendo orden de provincias (" + id +  "): ");
            for (int i = 0; i < provinciasMax.size(); i++) {
                System.out.print(getNombre(provinciasMax.get(i)) + ", ");
            }
            System.out.println();

            return provinciasMin.get(0);
        }
        else {
            return provinciasMax.get(0);
        }
    }           */

            /*
        int numProv = provinciasMax.size();

        if (numProv > 1) {
            double[] costos = new double [numProv];
            int min = provinciasMax.get(0);
            for (int i = 0; i < numProv; ++i) {
                costos [i] = ProvinciaFactory.getCostoTransporte(ubicacion, i) + ProvinciaFactory.getCostoEstadia(i);
                if (i > 0 && costos [i] < costos [i-1]) {
                    min = provinciasMax.get(i);
                }
            }
        }

        else {
            sMax = provinciasMax.get(0);
        }

       // Random random = new Random();
       // return  sMax == satisfaccion && provSatisfaccion[ubicacion]?  //todo: parche!!!
       //         ubicacion :
       //         provinciasMax.size() > 0 ? provinciasMax.get(random.nextInt(provinciasMax.size())) : ubicacion;
       */


    void proximoPaso() {

        //System.out.println(toString());
        //registrarEstadia();

        //filtrar();
        debeSalir();

        if (!this.activo) {
            return;
        }

        int futuraUbicacion = obtenerProximaProvincia();

        debeSalir();

        if (!this.activo) {
            return;
        }

        //filtroProvPorHuida(futuraUbicacion);

        //pagarCostoTransporte(ubicacion, futuraUbicacion);

        ubicacionanterior = ubicacion;
        ubicacion = futuraUbicacion;
        satisfaccion = calcularPreferencias(ubicacion);
    }


    private int obtenerEstadia(int codigo) {
        return estadias[codigo];
    }

    private double obtenerProvFactibles(int i) {
        return provSatisfaccion[i];
    }
    private double obtenerSatisfacciones (int i) {
        return provSatisfaccion[i];
    }


    void registrarBitacora(int dia) {
        //double costo = ProvinciaFactory.getCostoEstadia(ubicacion) + ProvinciaFactory.getCostoTransporte(ubicacionanterior, ubicacion);
        double distancia = calcularDistancia(ubicacionanterior,ubicacion);

        logging.agregar(hashCode(), dia, provSatisfaccion[ubicacion], satisfaccion, preferencias, ubicacion, activo, distancia);
        //bitacora.agregar(hashCode(), dia, presupuesto, satisfaccion, preferencias, ubicacion, activo, costo);
    }

    Logging obtenerBitacora() {
        return logging;
    }

    boolean estaActivo() {
        return activo;
    }

   /* public String toString() {
       String text = "";
       text += id + " ";

       text += getNombre(ubicacion) + " ";
       text += obtenerEstadia(ubicacion)+ " ";
       text += ProvinciaFactory.getMaxEstadia(ubicacion)+ " ";
       //text += estaActivo()+ " ";
       /*for (int i = 0; i < NUMERO_PROVINCIAS; ++i) {
           text += getNombre(i) + " ";
           text += obtenerProvFactibles(i)+ " ";
           text += obtenerSatisfacciones(i)+ " ";
       }
       return text;
    }*/
}
