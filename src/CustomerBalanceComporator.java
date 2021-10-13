import java.util.Comparator;

public class CustomerBalanceComporator implements Comparator<Customer> {
    @Override
    public int compare(Customer a, Customer b) {
        if(a.getBalance()>b.getBalance()) {
            return 1;
        }else if(a.getBalance()<b.getBalance()){
            return -1;
        }else{
            return 0;
        }
    }
}
