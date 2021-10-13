import java.util.Comparator;

public class CustomerNameComporator implements Comparator<Customer> {
    @Override
    public int compare(Customer a, Customer b) {

        return a.getName().compareTo(b.getName());
    }
}
