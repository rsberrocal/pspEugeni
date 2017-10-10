package conceptos;

import java.util.*;

public class Main {

    public static BigBoss levelUp(Empleado emp,double incentivo){
        BigBoss nextBoss = new BigBoss(emp.getNombre(),emp.getSueldo(),emp.getSeccion(),incentivo);
        return nextBoss;
    }

    public static void main(String[] args) {
        List<Empleado> emp = new ArrayList<Empleado>();
        Empleado test = new Empleado("Gregorio",361.12,"Administracion",2017,5,14);
        BigBoss finalBoss = new BigBoss();
        Random rnd = new Random();
        String nombre;

        for(int j=0;j<3;j++){
            nombre = "Empleado "+j;
            if(j==0){
                nombre = "Richard";
            }
            emp.add(new Empleado(nombre, 120));

        }
        finalBoss=levelUp(emp.get(0),1000);
        for(int i=0;i<emp.size();i++){
            if(emp.get(i).getNombre()==finalBoss.getNombre()){
                System.out.println("Nombre del jefe: "+finalBoss.getNombre());
                System.out.println("Sueldo: "+finalBoss.getSueldo());
            }else{
                System.out.println("Nombre:"+emp.get(i).getNombre());
                System.out.println("Sueldo:"+emp.get(i).getSueldo());
            }
        }
        System.out.println("Nombre: "+test.getNombre());
        System.out.println("Sueldo: "+test.getSueldo());
        System.out.println("Fecha de contrato: "+test.fechaToString());
        //Array method sort
        Collections.sort(emp, new Comparator<Empleado>() {
            @Override
            public int compare(Empleado empleado, Empleado t1) {
                return new Integer((int) empleado.getSueldo()).compareTo((int)t1.getSueldo());
            }
        });
        for (int i = 0; i < emp.size() ; i++) {
            System.out.println("Nombre:"+emp.get(i).getNombre());
        }
    }
}

