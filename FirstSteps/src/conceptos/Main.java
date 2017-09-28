package conceptos;

import java.util.Random;

public class Main {

    public static BigBoss levelUp(Empleado emp,double incentivo){
        BigBoss nextBoss = new BigBoss(emp.getNombre(),emp.getSueldo(),emp.getSeccion(),incentivo);
        return nextBoss;
    }

    public static void main(String[] args) {
        Empleado[] emp = new Empleado[3];
        BigBoss finalBoss = new BigBoss();
        Random rnd = new Random();
        String nombre;
        for(int j=0;j<emp.length;j++){
            nombre = "Empleado "+j;
            if(j==0){
                nombre = "Richard";
            }
            emp[j]=new Empleado(nombre, 120);

        }
        finalBoss=levelUp(emp[0],1000);
        for(int i=0;i<emp.length;i++){
            if(emp[i].getNombre()==finalBoss.getNombre()){
                System.out.println("Nombre del jefe: "+finalBoss.getNombre());
                System.out.println("Sueldo: "+finalBoss.getSueldo());
            }else{
                System.out.println("Nombre:"+emp[i].getNombre());
                System.out.println("Sueldo:"+emp[i].getSueldo());
            }
        }
    }
}

