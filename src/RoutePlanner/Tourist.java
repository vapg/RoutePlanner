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
    private double[] provSatisfaccion;
    private int[] estadias;
    private int tipoAgente;

    Tourist(boolean[] preferencias, int tipoAgente) {

        this.activo = true;
        this.preferencias = preferencias;
        this.tipoAgente = tipoAgente;
        this.id = ++userID;
        this.logging = new Logging();

        provSatisfaccion = new double[NUMERO_PROVINCIAS];
        estadias = new int[NUMERO_PROVINCIAS];

        for (int i = 0; i < NUMERO_PROVINCIAS; ++i) {
            double prefSatis = calcularPreferencias(i);
            if (prefSatis > UMBRALESSATISFACCIONES[tipoAgente][2]-0.1) {
                this.ubicacion = i;
                break;
            }
        }

        for (int i = 0; i < NUMERO_PROVINCIAS; ++i) {
            double prefSatis = calcularPreferencias(i);

            provSatisfaccion[i] = prefSatis < UMBRALESSATISFACCIONES[tipoAgente][0]? UMBRALESSATISFACCIONES[tipoAgente][0]:
                                  prefSatis < UMBRALESSATISFACCIONES[tipoAgente][1]? UMBRALESSATISFACCIONES[tipoAgente][1]:
                                          prefSatis <= UMBRALESSATISFACCIONES[tipoAgente][2]? UMBRALESSATISFACCIONES[tipoAgente][2]: -1000;
            estadias[i] = 0;
        }
        this.satisfaccion = calcularPreferencias(this.ubicacion);
    }

    @Override
    public int hashCode() { return id; }

    private void filtrarPorProbabilidadVisita() {
      for(int i = 0; i < NUMERO_PROVINCIAS; ++i) {
          provSatisfaccion[i] = Math.random() <= ProvinceFactory.getProbabilidadVisita(i, tipoAgente)? provSatisfaccion[i]: -1*provSatisfaccion[i];
      }
    }

    private double calcularDistancia(int origen, int destino) {
        return ProvinceFactory.getDistanciaEstadia(origen,destino);
    }

    private void debeSalir() {
        for (int i = 0; i < NUMERO_PROVINCIAS; ++i) {
            if (provSatisfaccion[i] >= 0) {
                this.activo = true;
                return;
            }
        }
        this.activo = false;
    }

    private double calcularPreferencias(int codigoProvincia) {
        double sumaPreferencias = 0.0;

        double[] provAtractivos = ProvinceFactory.getPreferencias(codigoProvincia);

        for (int i = 0; i < NUMERO_PREFERENCIAS; ++i) {
                sumaPreferencias += preferencias[i]? provAtractivos[i] : 0;
        }
        return sumaPreferencias;
    }


    private int obtenerProximaProvincia() {
        double sMax = 0;
        ArrayList<Integer> provinciasMax = new ArrayList<Integer>();
        Random random = new Random();
        int provinciaSeleccionada; //never

        do {
            habilitarTodasProbabilidadVisita();
            filtrarPorProbabilidadVisita();
            provSatisfaccion[ubicacion] = -1;

            for (int i = 0; i < NUMERO_PROVINCIAS; ++i) {
                sMax = sMax < provSatisfaccion[i] ? provSatisfaccion[i] : sMax;
            }

            for (int i = 0; i < NUMERO_PROVINCIAS; ++i) {
                if (sMax == provSatisfaccion[i]) {
                    provinciasMax.add(i);
                }
            }
        } while (provinciasMax.size() < 1);

        if (provinciasMax.size() > 1) {
            double costomin = Double.MAX_VALUE;
            int provinciamin = -1;

            for (int i = 0; i < provinciasMax.size(); ++i) {
                double costTemp = calcularDistancia(ubicacion, provinciasMax.get(i));
                if (costomin > costTemp) {
                    costomin = costTemp;
                    provinciamin = provinciasMax.get(i);
                }
            }
            provinciaSeleccionada = provinciamin;

        } else {
            provinciaSeleccionada = provinciasMax.get(0);
        }
        return provinciaSeleccionada;
    }

    private void habilitarTodasProbabilidadVisita() {
        for (int i = 0; i < NUMERO_PROVINCIAS; ++i) {
            if (provSatisfaccion[i] < 0 && provSatisfaccion[i] != -1) {
                provSatisfaccion[i] = provSatisfaccion[i]*-1;
            }
        }
    }

    void proximoPaso() {
        debeSalir();

        if (!this.activo) {
            return;
        }

        int futuraUbicacion = obtenerProximaProvincia();
        debeSalir();

        if (!this.activo) {
            return;
        }

        ubicacionanterior = ubicacion;
        ubicacion = futuraUbicacion;
        satisfaccion = calcularPreferencias(ubicacion);
    }


    void registrarBitacora(int dia) {
        double distancia = calcularDistancia(ubicacionanterior,ubicacion);

        logging.agregar(hashCode(), dia, provSatisfaccion[ubicacion], satisfaccion, preferencias, ubicacion, activo, distancia);
    }

    Logging obtenerBitacora() {
        return logging;
    }

    boolean estaActivo() {
        return activo;
    }
}
