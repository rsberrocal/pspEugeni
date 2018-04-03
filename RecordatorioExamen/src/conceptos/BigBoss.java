package conceptos;

public class BigBoss extends Empleado {

    private double incentivo;

    public BigBoss() {

    }

    public BigBoss(String nombre, double sueldo, String seccion, double incentivo) {
        super(nombre, sueldo, seccion);
        this.incentivo = incentivo;
    }

    @Override
    public double getSueldo() {
        return super.getSueldo() + this.incentivo;
    }

    public double getIncentivo() {
        return incentivo;
    }

    public void setIncentivo(double incentivo) {
        this.incentivo = incentivo;
    }
}