import java.awt.geom.*;

import javax.swing.*;

import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class Main {

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        JFrame marco=new MarcoRebote();

        marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        marco.setVisible(true);

    }

}




//Movimiento de la pelota-----------------------------------------------------------------------------------------

class Pelota{

    // Mueve la pelota invirtiendo posición si choca con límites

    public void mueve_pelota(Rectangle2D limites){

        x+=dx;

        y+=dy;

        if(x<limites.getMinX()){

            x=limites.getMinX();

            dx=-dx;
        }

        if(x + TAMX>=limites.getMaxX()){

            x=limites.getMaxX() - TAMX;

            dx=-dx;
        }

        if(y<limites.getMinY()){

            y=limites.getMinY();

            dy=-dy;
        }

        if(y + TAMY>=limites.getMaxY()){

            y=limites.getMaxY()-TAMY;

            dy=-dy;

        }

    }

    //Forma de la pelota en su posición inicial

    public Ellipse2D getShape(){

        return new Ellipse2D.Double(x,y,TAMX,TAMY);

    }

    private static final int TAMX=15;

    private static final int TAMY=15;

    private double x=0;

    private double y=0;

    private double dx=1;

    private double dy=1;


}

// Lámina que dibuja las pelotas----------------------------------------------------------------------


class LaminaPelota extends JPanel{

    //Añadimos pelota a la lámina

    public void add(Pelota b){

        pelotas.add(b);
    }

    public void paintComponent(Graphics g){

        super.paintComponent(g);

        Graphics2D g2=(Graphics2D)g;

        for(Pelota b: pelotas){

            g2.fill(b.getShape());
        }

    }

    private ArrayList<Pelota> pelotas=new ArrayList<Pelota>();
}

//Clase runnable para tener mas pelotas
class PelotaHilos implements Runnable{
    private Pelota pelota;
    private Component component;

    public PelotaHilos(Pelota p,Component c){
        this.pelota=p;
        this.component=c;

    }

    public void run(){
        //Bucle donde se mueve la pelota
        //for (int i=1; i<=3000; i++){
        //while(!Thread.interrupted()){
        while(!Thread.currentThread().isInterrupted()){

            this.pelota.mueve_pelota(this.component.getBounds());

            this.component.paint(this.component.getGraphics());

            try{
                //Para que se mueva la pelota de forma más lenta para ver la pelota
                Thread.sleep(4);
            }catch (Exception e){
                Thread.currentThread().interrupt();
            }

        }
    }
}


//Marco con lámina y botones------------------------------------------------------------------------------

class MarcoRebote extends JFrame{

    public MarcoRebote(){

        setBounds(600,300,800,350);

        setTitle ("Rebotes");

        lamina=new LaminaPelota();

        add(lamina, BorderLayout.CENTER);

        JPanel laminaBotones=new JPanel();

        ponerBoton(laminaBotones, "Dale 1!", new ActionListener(){

            public void actionPerformed(ActionEvent evento){

                comienza_el_juego(evento);
            }

        });

        ponerBoton(laminaBotones, "Dale 2!", new ActionListener(){

            public void actionPerformed(ActionEvent evento){

                comienza_el_juego(evento);
            }

        });

        ponerBoton(laminaBotones, "Dale 3!", new ActionListener(){

            public void actionPerformed(ActionEvent evento){

                comienza_el_juego(evento);
            }

        });


        ponerBoton(laminaBotones, "Salir", new ActionListener(){

            public void actionPerformed(ActionEvent evento){

                System.exit(0);

            }

        });

        //Nuevo boton para detener
        ponerBoton(laminaBotones, "Detener 1", new ActionListener(){

            public void actionPerformed(ActionEvent evento){

                stop(evento);

            }

        });

        ponerBoton(laminaBotones, "Detener 2", new ActionListener(){

            public void actionPerformed(ActionEvent evento){

                stop(evento);

            }

        });

        ponerBoton(laminaBotones, "Detener 3", new ActionListener(){

            public void actionPerformed(ActionEvent evento){

                stop(evento);

            }

        });

        add(laminaBotones, BorderLayout.SOUTH);

    }


    //Ponemos botones

    public void ponerBoton(Container c, String titulo, ActionListener oyente){

        JButton boton=new JButton(titulo);

        c.add(boton);

        boton.addActionListener(oyente);

    }


    //Añade pelota y la bota 1000 veces

    public void comienza_el_juego (ActionEvent e){
        //Creamos un boton para comparar
        JButton jb = (JButton)e.getSource();

        Pelota pelota=new Pelota();

        lamina.add(pelota);

        Runnable r = new PelotaHilos(pelota,lamina);
        //Hacemos la comparacion con el texto que tiene el boton
        if(jb.getText().equals("Dale 1!")){
            this.hiloPelota1 = new Thread(r);
            this.hiloPelota1.start();
        }else if(jb.getText().equals("Dale 2!")){
            this.hiloPelota2 = new Thread(r);
            this.hiloPelota2.start();
        }else if(jb.getText().equals("Dale 3!")){
            this.hiloPelota3 = new Thread(r);
            this.hiloPelota3.start();
        }

    }
    //Metodo para detener el hilo
    public void stop(ActionEvent e){
        //Mas de lo mismo con el otro
        JButton jb = (JButton)e.getSource();

        if(jb.getText().equals("Detener 1")){
            this.hiloPelota1.interrupt();
        }else if(jb.getText().equals("Detener 2")){
            this.hiloPelota2.interrupt();
        }else if(jb.getText().equals("Detener 3")){
            this.hiloPelota3.interrupt();
        }
    }

    /*Per què no es pot interrompre un fil ja bloquejat?
    *Porque al estar bloqueado dicho hilo no se puede
    * utilizar más hasta que se vuelva a reanudar
    * */


    Thread hiloPelota1,hiloPelota2,hiloPelota3;

    private LaminaPelota lamina;


}