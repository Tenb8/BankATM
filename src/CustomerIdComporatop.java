import java.util.Comparator;

public class CustomerIdComporatop implements Comparator<Customer> {
    @Override
    public int compare(Customer a, Customer b) {

        return a.getID_CUSTOMER().compareTo(b.getID_CUSTOMER());
    }
}
