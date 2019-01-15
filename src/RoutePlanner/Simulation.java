package RoutePlanner;

import java.util.ArrayList;
import java.util.HashMap;

class Simulation {

    private static int simulacionID = 0;

    private int id = 0;
    private ArrayList<Tourist> tourists;
    private int periodos;
    private HashMap<String, String> configuracion;
    private Logging loggingTotal;
    private String descripcion;

    Simulation() {
        loggingTotal = new Logging();
        configuracion = new HashMap<>();
    }

    void reinit(int numeroAgentes, int periodos, String descripcion, boolean cleanBitacora) {

        this.id = ++simulacionID;

        if (cleanBitacora) {
            this.loggingTotal = new Logging();
            this.configuracion = new HashMap<>();
        }
            this.descripcion = descripcion;
            configuracion.put("Periodos", "" + periodos);
            configuracion.put("NumeroAgentes", "" + numeroAgentes);
            configuracion.put("Descripcion", descripcion);


        this.periodos = periodos;
        this.tourists = TouristFactory.crearTuristas();
    }

    void ejecutar() {
        registrarRuta(0);

        for (int dia = 1; dia <= periodos; ++dia) {
            proximoPaso();
            registrarRuta(dia);
        }
        generarBitocoraTotal();
    }

    @Override
    public int hashCode() {
        return id;
    }

    void generarExcel() {
        DataSaver.writeXLS(loggingTotal, configuracion, descripcion);
    }

    private void generarBitocoraTotal() {
        for (Tourist tourist : tourists) {
            loggingTotal.agregar(tourist.obtenerBitacora());
        }
    }

    private void registrarRuta(int dia) {
        for (Tourist tourista : tourists) {
            tourista.registrarBitacora(dia);
        }
    }

    private void proximoPaso() {


            for (Tourist tourist : tourists) {
            if (tourist.estaActivo()) {
                tourist.proximoPaso();
            }
        }
    }
}
