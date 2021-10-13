import java.util.UUID;

public class Customer  {
    private final UUID ID_CUSTOMER;
    private String name;
    private int balance;


    public String getName() {
        return name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        if(balance>=0) {
            this.balance = balance;
        }else{
            System.out.println("Баланс клиента не может быть отрицательным!");
        }
    }

    public void setName(String name) {
            this.name = name;
    }
    public UUID getID_CUSTOMER(){
        return ID_CUSTOMER;
    }

    protected Customer(String name,int balance){
        this.ID_CUSTOMER=UUID.randomUUID();
        this.name=name;
        this.balance=balance;
    }
    protected Customer(String name,int balance, UUID id){
        this.ID_CUSTOMER=id;
        this.balance=balance;
        this.name=name;
    }
}

