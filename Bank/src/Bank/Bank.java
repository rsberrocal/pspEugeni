package Bank;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bank {

    private double[] cuentas1;
    private double[] cuentas2;
    private int maxCounts = 100;

    private final ReentrantLock lock = new ReentrantLock();
    private Condition moneyAvailable;

    public Bank() {
        this.cuentas1 = new double[maxCounts];
        this.cuentas2 = new double[maxCounts];
        for (int i = 0; i < maxCounts; i++) {
            this.cuentas1[i] = 2000.00;
            this.cuentas2[i] = 2000.00;
        }
        this.moneyAvailable = lock.newCondition();
    }

    public double getTotal() {
        double total1 = 0;
        double total2 = 0;
        for (int i = 0; i < maxCounts; i++) {
            total1 += this.cuentas1[i];
            total2 += this.cuentas2[i];
        }
        return total1 + total2;
    }

    //Cuenta Origen1 -> Cuenta Destino2
    //Cuenta Origen2 -> Cuenta Destino1

    public void transferencia(int cuentaOrigen1, int cuentaDestino1, int cuentaOrigen2, int cuentaDestino2, double cantidadATransferir) throws InterruptedException {
        System.out.println("Nueva Transferencia");
        if (this.cuentas1[cuentaOrigen1] < cantidadATransferir || this.cuentas2[cuentaOrigen2] < cantidadATransferir) {
            System.out.println("Sin dinero");
            return;
        }
        /*this.lock.lock();
        try {
            while (this.cuentas1[cuentaOrigen1] < cantidadATransferir || this.cuentas2[cuentaOrigen2] < cantidadATransferir) {
                this.moneyAvailable.await();
                System.out.println("Hilo a la espera");
            }*/
                //Se resta a la cuentas
            this.cuentas1[cuentaOrigen1] -= cantidadATransferir;
            this.cuentas2[cuentaDestino2] += cantidadATransferir;
            //Se suma a las cuentas
            this.cuentas2[cuentaOrigen2] -= cantidadATransferir;
            this.cuentas1[cuentaDestino1] += cantidadATransferir;

            System.out.println("La cuenta " + cuentaOrigen1 + " del banco1 ha dado " + String.format("%10.2f", cantidadATransferir) + "€ a la cuenta " + cuentaDestino2);
            System.out.println("La cuenta " + cuentaOrigen2 + " del banco2 ha dado " + String.format("%10.2f", cantidadATransferir) + "€ a la cuenta " + cuentaDestino1);
            System.out.println("Transferencia completada");
            System.out.println("Hilo actual: " + Thread.currentThread());
            //this.moneyAvailable.signalAll();

        /*} finally {
            this.lock.unlock();
        }*/

        System.out.println("Saldo total :" + String.format("%10.2f", this.getTotal()) + "\n");
    }

}
