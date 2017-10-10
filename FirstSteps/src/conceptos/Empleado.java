package conceptos;

import java.util.Date;
import java.util.GregorianCalendar;

public class Empleado {
    //Clase para empleados
    private String dni;
    private String nombre;
    private String apellidos;
    private String seccion;
    private double sueldo;
    private Date fechaContrato;

    Empleado() {
        this.nombre = "Ana";
        this.sueldo = 20000;
        this.seccion = "Administracion";
    }

    Empleado(String nombre, double sueldo) {
        this.nombre = nombre;
        this.sueldo = sueldo;
        this.seccion = "Administración";
    }

    Empleado(String nombre, double sueldo, String seccion) {
        this.nombre = nombre;
        this.sueldo = sueldo;
        this.seccion = seccion;
    }

    Empleado(String nombre, double sueldo, String seccion, int ano, int mes, int dia) {
        this.nombre = nombre;
        this.sueldo = sueldo;
        this.seccion = seccion;
        if (mes > 0) {
            mes += -1;
        }
        GregorianCalendar gregorio = new GregorianCalendar(ano, mes, dia);
        this.fechaContrato = gregorio.getTime();
    }

    public String fechaToString() {
        return this.fechaContrato.toString();
    }

    public void addSueldo(double per) {
        this.sueldo += (this.sueldo * per) / 100;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

}
