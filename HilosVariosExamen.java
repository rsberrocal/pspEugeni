package Main;

public class Main {

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        HilosVarios hilo1=new HilosVarios();
        HilosVarios hilo2=new HilosVarios();
        HilosVarios2 hilo3 = new HilosVarios2(hilo1,hilo2);
        hilo1.start();
        //Sincronitzar: volem que hilo2 no comenci fins que no acabi hilo1
        //metode join()
        try {
            hilo1.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        hilo2.start();

        try {
            hilo2.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        hilo3.start();
        try{
            hilo3.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        System.out.println("Terminadas las tareas");
    }
}

class HilosVarios extends Thread{

    public void run() {

        for (int i=0;i<15;i++) {

            System.out.println("Ejecutando hilo" + getName());

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}

class HilosVarios2 extends Thread{
    Thread h1;
    Thread h2;
    HilosVarios2(Thread h1,Thread h2){
        this.h1=h1;
        this.h2=h2;
    }

    public void run(){
        for (int i=0;i<15;i++) {

            System.out.println("Ejecutando hilo" + getName());

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}