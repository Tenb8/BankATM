import java.util.Comparator;

public class TransactionIdComparator implements Comparator<Transaction> {
    @Override
    public int compare(Transaction a, Transaction b) {
        return a.getID_TRANSACTION().compareTo(b.getID_TRANSACTION());
    }
}
