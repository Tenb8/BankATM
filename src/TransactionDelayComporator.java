import java.util.Comparator;

public class TransactionDelayComporator implements Comparator<Transaction> {
    @Override
    public int compare(Transaction a, Transaction b) {
        if(a.getDelay()>b.getDelay())
        return 1;
        else if(a.getDelay()<b.getDelay())
            return -1;
        else
            return 0;
    }
}
