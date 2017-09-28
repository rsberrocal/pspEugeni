package conceptos;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Empleado[] emp = new Empleado[3];
        Random rnd = new Random();
        for(int j=0;j<emp.length;j++){
            emp[j]=new Empleado(("Empleado constructor1 "+j), rnd.nextInt(100)*(j+1));
        }
        for(int i=0;i<emp.length;i++){
            if(emp[i].getNombre().contains("2")){
                emp[i].addSueldo(150);
            }
            System.out.println("Nombre:"+emp[i].getNombre());
            System.out.println("Sueldo:"+emp[i].getSueldo());
        }
    }
}
