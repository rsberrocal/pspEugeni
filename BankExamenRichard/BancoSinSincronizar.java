package Bank;

import java.text.DecimalFormat;

public class BancoSinSincronizar {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        //Para formatar
        DecimalFormat df = new DecimalFormat("#,00");
        Bank b = new Bank();

        /* 2.1 */

        /*for (int i = 0; i < 10; i++) {

            int deLaCuenta1 = (int) (100 * Math.random());
            int paraLaCuenta1 = (int) (100 * Math.random());
            int deLaCuenta2 = (int) (100 * Math.random());
            int paraLaCuenta2 = (int) (100 * Math.random());
            double cantidad = (2000 * Math.random());
            cantidad = Double.parseDouble(df.format(cantidad));
            b.transferencia(deLaCuenta1, paraLaCuenta1, deLaCuenta2, paraLaCuenta2, cantidad);
        }*/

        /* 2.2 2.3 2.4 */

        for (int i = 0; i < 100; i++) {
            int deLaCuenta1 = (int) (100 * Math.random());
            int deLaCuenta2 = (int) (100 * Math.random());
            EjecucionTransferencia r = new EjecucionTransferencia(b,deLaCuenta1,deLaCuenta2 , 1800);

            Thread t = new Thread(r);

            t.start();
        }
    }
}