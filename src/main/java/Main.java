import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Employee> employees = new ArrayList<>();

        System.out.print("Введіть кількість працівників: ");
        int n = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < n; i++) {
            System.out.println("\nПрацівник " + (i + 1));

            System.out.print("Введіть id: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Введіть ім'я: ");
            String name = scanner.nextLine();

            System.out.print("Введіть зарплату: ");
            double salary = scanner.nextDouble();
            scanner.nextLine();

            Employee employee = new Employee(id, name, salary);
            employees.add(employee);
        }

        System.out.println("\nІнформація про всіх працівників:");
        for (Employee employee : employees) {
            System.out.println(employee.toString());
        }

        scanner.close();
    }
}