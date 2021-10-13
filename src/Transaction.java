import java.util.UUID;

public class Transaction {
    private final UUID ID_TRANSACTION;
    private final UUID ID_SENDER;
    private final UUID ID_PAYEE;
    private int amount;
    private int delay;

    public int getAmount() {
        return amount;
    }

    public int getDelay() {
        return delay;
    }

    public void setAmount(int amount) {
        if(amount>=0) {
            this.amount = amount;
        }else{
            System.out.println("Сумма перевода не может быть отрицательным!");
        }
    }

    public void setDelay(int delay) {
        if(delay>=0) {
            this.delay = delay;
        }else{
            System.out.println("Задержка не может быть меньше нуля!");
        }
    }

    public UUID getID_SENDER() {
        return ID_SENDER;
    }

    public UUID getID_PAYEE() {
        return ID_PAYEE;
    }

    public UUID getID_TRANSACTION() {
        return ID_TRANSACTION;
    }

    protected Transaction(UUID fromCustomer, UUID toCustomer,int amount, int delay){
        this.ID_TRANSACTION=UUID.randomUUID();
        this.ID_SENDER=fromCustomer;
        this.ID_PAYEE=toCustomer;
        this.amount=amount;
        this.delay=delay;
    }
    protected Transaction(UUID id,UUID fromCustomer,UUID toCustomer, int amount,int delay){
        this.ID_TRANSACTION=id;
        this.ID_SENDER=fromCustomer;
        this.ID_PAYEE=toCustomer;
        this.amount=amount;
        this.delay=delay;
    }
}

