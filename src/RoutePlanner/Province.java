package RoutePlanner;

public class Province {


    private String nombre;
    private double[] preferencias;

    Province(int codProvincia, double[] preferencias){
        this.preferencias = preferencias;
        this.nombre = ProvinceFactory.getNombre(codProvincia);
    }

    private String getNombre() { return this.nombre; }
    double[] getPreferencias() { return this.preferencias; }

    @Override
    public String toString() {
        return getNombre();
    }

}
