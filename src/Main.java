import entities.Employee;
import repositories.EmployeeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        EmployeeRepository employeeRepository = new EmployeeRepository();

        try {
            ArrayList<Employee> employees = employeeRepository.getAllEmployees(Optional.empty(), Optional.empty(), Optional.empty());

            System.out.println("------ LISTA INICIAL ------\n");
            employees.forEach(employee -> System.out.println(employee.toString()));
            System.out.println("---------------------------");

            employeeRepository.removeEmployeeByName("João");
            employees = employeeRepository.getAllEmployees(Optional.empty(), Optional.empty(), Optional.empty());
            employeeRepository.increaseEmployeesSalary(10);

            System.out.println("------ DEPOIS DE AUMENTAR O SALÁRIO ------\n");
            employees.forEach(employee -> System.out.println(employee.toString()));
            System.out.println("---------------------------");

            Map<String, List<Employee>> employeesByRole = employeeRepository.getEmployeesByRole();

            employeesByRole.forEach((role, employeesInRole) -> {
                System.out.println("Funcionários na função '" + role + "':");
                employeesInRole.forEach(employee -> System.out.println("  " + employee.getName()));
                System.out.println("---------------------------");
            });

            ArrayList<Employee> filteredByBirthMonthEmployees = employeeRepository.getAllEmployees(Optional.of(10), Optional.of(12), Optional.empty());

            System.out.println("\n----- FUNCIONÁRIOS QUE FAZEM ANIVERSÁRIO DE OUTUBRO A NOVEMBRO -----");
            filteredByBirthMonthEmployees.forEach(employee -> System.out.println(employee.getName()));
            System.out.println("---------------------------\n");

            ArrayList<Employee> sortedByAge = employeeRepository.getAllEmployees(Optional.empty(), Optional.empty(), Optional.of("age"));

            if (!sortedByAge.isEmpty()) {
                Employee oldestEmployee = sortedByAge.get(sortedByAge.size() - 1);
                System.out.println("\n----- FUNCIONÁRIO MAIS VELHO -----");
                System.out.println(oldestEmployee.getName());
                System.out.println(oldestEmployee.getAge());
                System.out.println("---------------------------\n");
            }

            ArrayList<Employee> sortedByName = employeeRepository.getAllEmployees(Optional.empty(), Optional.empty(), Optional.of("name"));
            System.out.println("------ FUNCIONÁRIOS EM ORDEM ALFABETICA ------\n");
            sortedByName.forEach(employee -> System.out.println(employee.toString()));
            System.out.println("---------------------------\n");

            System.out.println("---------------------------");
            String totalSalaries = employeeRepository.getEmployeesSalary();
            System.out.printf("Soma do salário de todos funcionários: %s\n", totalSalaries);
            System.out.println("---------------------------\n");

            System.out.println("------ FAIXA SALARIAL DE CADA FUNCIONÁRIO ------");
            employees.forEach(employee -> {
                int salaryCount = employee.getMinSalaryCount();
                String salaryLabel = "salário";
                if (salaryCount > 1) {
                    salaryLabel += "s";
                }
                System.out.printf("%s ganha %d %s.\n", employee.getName(), salaryCount, salaryLabel);
            });
            System.out.println("---------------------------\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
