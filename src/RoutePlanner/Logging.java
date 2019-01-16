package RoutePlanner;

import java.util.ArrayList;
import java.util.List;

class Logging {

    private List<List> records;


    Logging(){
        records = new ArrayList<List>();
    }

    void agregar(Logging bit) {
        for (int i = 0; i < bit.size(); ++i) {
            int touristaID = bit.obtenerTourista(i);
            int dia = bit.obtenerDia(i);
            double presupuesto = bit.obtenerPresupuesto(i);
            double satisfaccion = bit.obtenerSatisfaccion(i);
            int provincia = bit.obtenerProvince(i);
            boolean[] atractivos = bit.obtenerAtractivos(i);
            boolean activo = bit.obtenerActivo(i);
            double costo = bit.obtenerCosto(i);

            agregar(touristaID, dia, presupuesto, satisfaccion, atractivos, provincia, activo, costo);
        }
    }




    int obtenerTourista(int i) {
        return (Integer) (records.get(i).get(0));
    }

    int obtenerDia(int i) {
        return (Integer) (records.get(i).get(1));
    }

    double obtenerPresupuesto(int i) {
        return (Double) (records.get(i).get(2));
    }
    int obtenerProvince(int i) {
        return (Integer) (records.get(i).get(3));
    }

    double obtenerSatisfaccion(int i) {
        return (Double) (records.get(i).get(4));
    }

    boolean[] obtenerAtractivos(int i) {return (boolean[]) (records.get(i).get(5)); }

    boolean obtenerActivo(int i) { return (Boolean) (records.get(i).get(6)); }

    double obtenerCosto(int i) {
        return (Double) (records.get(i)).get(7);
    }

    int size() {
        return records.size();
    }

    void agregar(int touristaID, int dia, double presupuesto, double satisfaccion, boolean[] preferencias, int provincia, boolean activo, double costo) {
        ArrayList record = new ArrayList<>();

        record.add(touristaID);
        record.add(dia);
        record.add(presupuesto);
        record.add(provincia);
        record.add(satisfaccion);
        record.add(preferencias);
        record.add(activo);
        record.add(costo);

        records.add(record);
    }
}

