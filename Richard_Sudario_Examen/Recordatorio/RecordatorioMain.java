package conceptos;

import java.util.*;

public class RecordatorioMain {

    public static BigBoss levelUp(Empleado emp,double incentivo){
        BigBoss nextBoss = new BigBoss(emp.getNombre(),emp.getSueldo(),emp.getSeccion(),incentivo);
        return nextBoss;
    }

    public static void main(String[] args) {
        List<Empleado> emp = new ArrayList<Empleado>();
        //Polimorfismo:Una clase padre puede cambiar a cualquiera de sus clases hijas
        Empleado finalBoss = new BigBoss();
        //Encapsulacion:Se encapsulan diferentes variables en un solo objeto
        emp.add(new Empleado("Richard", 400));
        emp.add(new Empleado("Alex", 400));
        emp.add(new Empleado("Tarzan", 400));
        emp.add(new Empleado("David", 400));
        finalBoss=levelUp(emp.get(0),1000);
        //Herencia:Si una clase hereda de otra esta puede usar los metodos de la clase padre junto con sus variables
        finalBoss.fechaToString();
        //Enlace dinamico:Se escoje que metodo usar de los dos que hay en las clases padre e hijo en tiempo de ejecución
        //Sobrecarga de metodos:Se pueden volver a escribir metodos de la clase padre en la clase hijo para añadir funciones nuevas
        finalBoss.getSueldo();
        //Para ordenar
        Collections.sort(emp, new Comparator<Empleado>() {
            @Override
            public int compare(Empleado empleado, Empleado t1) {
                int num;
                num = new String(empleado.getNombre()).compareTo((String)t1.getNombre());
                return num;
            }
        });
        //Para imprimir
        for(int i=0;i<emp.size();i++){
            if(emp.get(i).getNombre()==finalBoss.getNombre()){
                System.out.println("Nombre del jefe: "+finalBoss.getNombre());
            }else{
                System.out.println("Nombre:"+emp.get(i).getNombre());
            }
        }
    }
}

