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

        emp.add(new Empleado("Richard", 400));
        emp.add(new Empleado("Alex", 400));
        emp.add(new Empleado("Tarzan", 400));
        emp.add(new Empleado("David", 400));
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
                int num;
                num = new String(empleado.getNombre()).compareTo((String)t1.getNombre());
                return num;
            }
        });
        for (int i = 0; i < emp.size() ; i++) {
            System.out.println("Nombre:"+emp.get(i).getNombre());
        }
    }
}

