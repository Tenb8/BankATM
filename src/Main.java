import java.io.*;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        System.out.println("Добро пожаловать в ModelBank v1.0\n" +
                "Для того чтобы увидеть все команды, напишите help");
        Bank bank = new Bank();
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {

                String command = sc.nextLine();
                if (command.startsWith("addCustomer")) {
                        String[] cust = command.split(" ");
                        if (cust[1].matches("[a-zA-Z]+") && cust[2].matches("[0-9]+")) {
                            System.out.println(bank.addCustomer(new Customer(cust[1], Integer.valueOf(cust[2]))));
                        } else {
                            System.out.println("Введено некоректно имя или баланс клиента");
                        }
                } else if (command.startsWith("getName")) {
                        String[] cust = command.split(" ");
                        if (cust[1].matches("[a-zA-Z]+")) {
                            for (Customer customer : bank.getByName(cust[1]))
                                System.out.println("Name=" + customer.getName() +
                                        " id=" + customer.getID_CUSTOMER() +
                                        " balance=" + customer.getBalance());
                        } else {
                            System.out.println("Введено некоректно имя клиента");
                        }
                } else if (command.startsWith("getId")) {
                        String[] cust = command.split(" ");
                        System.out.println(bank.getById(UUID.fromString(cust[1])));

                } else if (command.startsWith("removeCustomer")) {
                        String[] cust = command.split(" ");
                        System.out.println(bank.removeCustomer(UUID.fromString(cust[1])));

                } else if (command.startsWith("changeBalance")) {
                    String[] cust = command.split(" ");
                    bank.changeBalance(UUID.fromString(cust[1]), Integer.valueOf(cust[2]));

                } else if (command.startsWith("printCustomersByBalance")) {
                    for (Customer customer : bank.listByBalance())
                        System.out.println("Name=" + customer.getName() +
                                " id=" + customer.getID_CUSTOMER() +
                                " balance=" + customer.getBalance());

                } else if (command.startsWith("printCustomersByName")) {
                    for (Customer customer : bank.listByName())
                        System.out.println("Name=" + customer.getName() +
                                " id=" + customer.getID_CUSTOMER() +
                                " balance=" + customer.getBalance());

                } else if (command.startsWith("fullList")) {
                    bank.fullList();

                } else if (command.startsWith("addTransaction")) {
                    try {
                        String[] cust = command.split(" ");
                        System.out.println(bank.addTransaction(new Transaction(UUID.fromString(cust[1]),
                                UUID.fromString(cust[2]),
                                Integer.valueOf(cust[3]),
                                Integer.valueOf(cust[4]))));
                    } catch (CustomerNotFoundedException ex) {
                        System.out.println(ex.getMessage());
                    }
                } else if (command.startsWith("changeName")) {
                    String[] cust = command.split(" ");
                    bank.changeName(cust[1]);
                } else if (command.startsWith("printTransaction")) {
                    for (Transaction transaction : bank.printTransaction())
                        System.out.println("Transaction: id=" + transaction.getID_TRANSACTION() +
                                " from=" + transaction.getID_SENDER() +
                                " to=" + transaction.getID_PAYEE() +
                                " value=" + transaction.getAmount() +
                                " delay=" + transaction.getDelay());
                } else if (command.startsWith("saveCustomers")) {
                    bank.saveCustomers();
                } else if (command.startsWith("readCustomers")) {
                    bank.readCustomers();
                } else if (command.startsWith("runTransaction")) {
                    for (Transaction transaction : bank.runTransaction()) {
                        System.out.println("Running Transaction: id= " + transaction.getID_TRANSACTION() +
                                " from=" + transaction.getID_SENDER() +
                                " to=" + transaction.getID_PAYEE() +
                                " value=" + transaction.getAmount() +
                                " delay=" + transaction.getDelay());
                    }
                }else if (command.startsWith("saveTransactions")) {
                    bank.saveTransaction();
                } else if (command.startsWith("readTransactions")) {
                    bank.readTransactions();
                } else if (command.startsWith("help")) {
                    //region Commands
                    System.out.println("Команды доступные для пользователя:\n" +
                            "1.addCustomer - команда для добавления клиента в систему.\n" +
                            "2.getName - команда получения списка данных о клиентах с одинаковыми именами.\n" +
                            "3.getId - команда для получения данных о клиенте по его UUID.\n" +
                            "4.removeCustomer - команда для удаления клинта из системы.\n" +
                            "5.changeBalance - команда для изменения баланса клиента.\n" +
                            "6.printCustomersByBalance - команда для получения списка клинтов отсортированного по балансу.\n" +
                            "7.printCustomersByName - команда для получения списка клинтов отсортированного по имени.\n" +
                            "8.fullList - команда для получения списка клиентов.\n" +
                            "9.addTransaction - команда для добавления транзакции между двумя клиентами.\n" +
                            "10.changeName - команда для изменения имени клиента.\n" +
                            "11.printTransaction - команда для получения списка всех транзакций отсортированного по задержке.\n" +
                            "12.saveCustomers - команда для выгрузки списка клиентов в файл.\n" +
                            "13.readCustomers - команда для загрузки списка клиентов из файла.\n" +
                            "14.runTransaction - команда для запуска исполнения транзакций.\n" +
                            "15.saveTransactions - команда для выгрузки транзакций в файл.\n" +
                            "16.readTransactions - команда для загрузки транзакций из файла.\n" +
                            "17.help - команда для помощи пользователям.\n" +
                            "18.exit - команда для выхода из системы.");

                    //endregion
                }
                else if(command.startsWith("exit")){
                    return;
                }
                else{
                    System.out.println("Unknown command");
                }
            }catch (ArrayIndexOutOfBoundsException ai) {
                System.out.println("Вы ввели не все данные");
            }catch (IllegalArgumentException ia){
                System.out.println("Введены некоректно данные клиента!");
            }
        }
    }
}
