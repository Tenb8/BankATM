import javax.sound.midi.Soundbank;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.*;

public class Bank {
    private HashSet<Customer> customerList;
    private HashSet<Transaction> transactionList;
    private TreeSet<Transaction> transactionTreeSet;
    private TreeSet<Customer> customerTreeSet;
    private final static String path = new File("").getAbsolutePath();
    private final static File CUSTOMERS = new File(path, "customers.txt");
    private final static File TRANSACTIONS = new File(path, "transaction.txt");

    protected Bank() {
        this.customerList = new HashSet<>();
        this.transactionList = new HashSet<>();

    }

    public void changeBalance(UUID id, int amount) {
        for (Customer cast : customerList) {
            if (cast.getID_CUSTOMER().equals(id)) {
             cast  .setBalance( amount+cast.getBalance());
                System.out.println("New Balance:" + (double)(cast.getBalance())  + " Name: " + cast.getName());
            }
        }
    }

    public void changeName(String name) {
        for (Customer customer : customerList) {
            customer.setName(name);
            System.out.println("Change: id= " + customer.getID_CUSTOMER() +
                    " new name= " + customer.getName() +
                    " balance= " + customer.getBalance());
        }
    }

    public String addCustomer(Customer customer) {
        customerList.add(customer);
        return "New Customer: id=" + customer.getID_CUSTOMER() +
                " name=" + customer.getName() +
                " balance=" + customer.getBalance();
    }

    public String removeCustomer(UUID id) {
        for (Customer cast : customerList) {
            if (cast.getID_CUSTOMER().equals(id)) {
                customerList.remove(cast);
                return "Delete customer: " + cast.getName() + " id=" + cast.getID_CUSTOMER();
            }
        }
        return "Клиента с таким данными не существует!";
    }

    public ArrayList<Customer> getByName(String name) {
        ArrayList<Customer> customerArrayList = new ArrayList<>();
        customerArrayList.sort(new CustomerBalanceComporator().thenComparing(new CustomerIdComporatop()));
        for (Customer cast : customerList) {
            if (cast.getName().equalsIgnoreCase(name)) {
                customerArrayList.add(cast);
                // return  "Name=" + cast.getName() + " id=" + cast.getID_CUSTOMER() + " balance=" + cast.getBalance();
            }
        }
       // System.out.println("Клиент с таким именем не существует!");
        return customerArrayList;
    }

    public String getById(UUID id) {
        for (Customer cast : customerList) {
            if (cast.getID_CUSTOMER().equals(id)) {
                return "Name=" + cast.getName() + " id=" + cast.getID_CUSTOMER() + " balance=" + cast.getBalance();
            }
        }
        return "Клиент с таким ID не существует";
    }

    public void fullList() {
        for (Customer cast : customerList) {
            System.out.println("Name= " + cast.getName() +
                    " id=" + cast.getID_CUSTOMER() +
                    " balance=" + cast.getBalance());
        }
    }

    public boolean addTransaction(Transaction transaction) throws CustomerNotFoundedException {
        boolean founded1 = false;
        boolean founded2 = false;
        boolean tB = false;
        for (Customer customer : customerList) {
            if (transaction.getID_PAYEE().equals(customer.getID_CUSTOMER())) {
                founded2 = true;
            }
        }
        for (Customer customer : customerList) {
            if (transaction.getID_SENDER().equals(customer.getID_CUSTOMER())) {
                founded1 = true;
                if (customer.getBalance() >= transaction.getAmount()) {
                    tB = true;
                }
            }
        }
        if(transaction.getID_PAYEE().equals(transaction.getID_SENDER())) {
            System.out.println("Нельзя добавить транзакцию");
            return false;
        }
         if (founded1 && founded2 && tB) {
            transactionList.add(transaction);
//            System.out.println(transactionList.size());
            System.out.println("New Transaction: id=" + transaction.getID_TRANSACTION() +
                    " from=" + transaction.getID_SENDER() +
                    " to=" + transaction.getID_PAYEE() +
                    " value=" + transaction.getAmount() +
                    " delay=" + transaction.getDelay());
        }
        if (!(founded1 && founded2)) {
            throw new CustomerNotFoundedException("CustomerNotFounded");
        }
        return founded1 && founded2 && tB;//CHANGER LE RETURN
    }

    public TreeSet<Customer> listByName() {
        Comparator<Customer> customerComparator = new CustomerNameComporator()
                .thenComparing(new CustomerBalanceComporator())
                .thenComparing(new CustomerIdComporatop());
        customerTreeSet = new TreeSet(customerComparator);
        customerTreeSet.addAll(customerList);
        //return "Customer: id " +customer.getID_CUSTOMER()+" name= "+customer.getName()+" balance= "+customer.getBalance();
        return customerTreeSet;
    }

    public TreeSet<Customer> listByBalance() {
        Comparator<Customer> customerComparator = new CustomerBalanceComporator()
                .thenComparing(new CustomerNameComporator())
                .thenComparing(new CustomerIdComporatop());
        customerTreeSet = new TreeSet(customerComparator);
        customerTreeSet.addAll(customerList);
        return customerTreeSet;
    }

    public TreeSet<Transaction> printTransaction() {
        Comparator<Transaction> transactionComparator = new TransactionDelayComporator()
                .thenComparing(new TransactionIdComparator());
        transactionTreeSet = new TreeSet(transactionComparator);
        transactionTreeSet.addAll(transactionList);
        return transactionTreeSet;

    }
    public void saveCustomers() {
        if (CUSTOMERS.exists()) {
            PrintWriter printer = null;
            try {
                printer = new PrintWriter(new FileWriter(CUSTOMERS), true);
                for (Customer customer : customerList) {
                    printer.println("Name: " + customer.getName() +
                            " Balance: " + customer.getBalance() +
                            " id: " + customer.getID_CUSTOMER());
                }
                System.out.println(CUSTOMERS.getParent());
                System.out.println("Файл сохранен!");
                printer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            try {
                CUSTOMERS.createNewFile();
                saveCustomers();
            } catch (IOException ex) {
                System.out.println("Невозможно создать файл");
            }
        }

    }

    public void readCustomers() {
        if (CUSTOMERS.exists()) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(CUSTOMERS));
                String line;
                while ((line = reader.readLine()) != null) {
                    String masLines[] = line.replaceAll("Name:", "").
                            replaceAll("Balance:", "").
                            replaceAll("id:", "").
                            split("  ");
                    Customer customer = new Customer(masLines[0], Integer.valueOf(masLines[1]), UUID.fromString(masLines[2]));
                    customerList.add(customer);
                    System.out.println(line);
                }
                System.out.println("Добавлены все клиенты из файла!");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            System.out.println("Файл не существует!");
        }
    }

    public Queue<Transaction> runTransaction() {
        Comparator<Transaction> transactionComparator = new TransactionDelayComporator();
        Queue<Transaction> transactionPriorityQueue = new PriorityQueue<>(transactionComparator);
        transactionPriorityQueue.addAll(transactionList);
        Queue<Transaction> resultQueue = new PriorityQueue<>(transactionPriorityQueue);
        Iterator<Transaction> transactionIterator = transactionPriorityQueue.iterator();
        while (transactionIterator.hasNext()) {
            Transaction run = transactionPriorityQueue.poll();
            for (Customer customer : customerList) {
                if (customer.getID_CUSTOMER().equals(run.getID_SENDER())) {
                    if(customer.getBalance()>=0)
                            customer.setBalance(customer.getBalance() - run.getAmount());
                }
                if (customer.getID_CUSTOMER().equals(run.getID_PAYEE()))
                            customer.setBalance(customer.getBalance() + run.getAmount());
                }
            }
            return resultQueue;
    }

    public void saveTransaction() {
        if (TRANSACTIONS.exists()) {
            PrintWriter printTransaction = null;
            try {
                    printTransaction = new PrintWriter(new FileWriter(TRANSACTIONS), true);
                for (Transaction transaction : printTransaction()) {
                    printTransaction.println("Transaction: id= " + transaction.getID_TRANSACTION() +
                            " from= " + transaction.getID_SENDER() +
                            " to= " + transaction.getID_PAYEE() +
                            " value= " + transaction.getAmount() +
                            " delay= " + transaction.getDelay());
                }
                System.out.println(TRANSACTIONS.getParent());
                System.out.println("Файл успешно сохранен");
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            try {
                TRANSACTIONS.createNewFile();
                saveTransaction();
            } catch (IOException ex) {
                System.out.println("Невозможно создать файл");
            }
        }
    }

    public void readTransactions() {
        if (TRANSACTIONS.exists()) {
            BufferedReader bufferedTransaction = null;
            try {
                bufferedTransaction = new BufferedReader(new FileReader(TRANSACTIONS));
                String line;
                while ((line = bufferedTransaction.readLine()) != null) {
                    String masLines[] = line.replaceAll("Transaction: ", "").
                            replaceAll("id=", "").
                            replaceAll("from=", "").
                            replaceAll("to=", "").
                            replaceAll("value=", "").
                            replaceAll("delay=", "").
                            split(" ");
                    Transaction transaction = new Transaction(UUID.fromString(masLines[0]),
                            UUID.fromString(masLines[1]),
                            UUID.fromString(masLines[2]),
                            Integer.valueOf(masLines[3]),
                            Integer.valueOf(masLines[4]));
                    transactionList.add(transaction);
                }
                System.out.println("Файл успешно загружен!");
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }else {
            System.out.println("Файл не существует!");
        }
    }
}
