package Bank;

public class EjecucionTransferencia implements Runnable {
    private Bank bank;
    private int sourceAccount1;
    private int sourceAccount2;
    private int maxMoneyToTransfer;


    EjecucionTransferencia(Bank b, int sA1, int sA2, int money) {
        this.bank = b;
        this.sourceAccount1 = sA1;
        this.sourceAccount2 = sA2;
        this.maxMoneyToTransfer = money;
    }


    @Override
    public void run() {
        while (true) {
            int destinationAccount1 = (int) (100 * Math.random());
            int destinationAccount2 = (int) (100 * Math.random());
            double money = this.maxMoneyToTransfer * Math.random();
            try {
                this.bank.transferencia(this.sourceAccount1, this.sourceAccount2, destinationAccount1, destinationAccount2, money);
                Thread.sleep(700);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}