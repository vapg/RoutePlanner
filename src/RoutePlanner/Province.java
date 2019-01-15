package RoutePlanner;

public class Province {

    private int codigo;
    private String nombre;
    private double[] preferencias;

    Province(int codProvincia, double[] preferencias){
        this.codigo = codProvincia;
        this.preferencias = preferencias;
        this.nombre = ProvinceFactory.getNombre(codProvincia);
    }

    public int getCodigo() {
        return codigo;
    }
    private String getNombre() { return this.nombre; }
    double[] getPreferencias() { return this.preferencias; }

    @Override
    public String toString() {
        return getNombre();
    }

}
