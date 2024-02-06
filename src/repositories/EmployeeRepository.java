package repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import entities.Employee;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static entities.Employee.getFormattedSalary;

public class EmployeeRepository {
    private final ObjectMapper mapper;
    private ArrayList<Employee> employees;

    public EmployeeRepository() {
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());

        try {
            loadData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadData() throws IOException {
        try (InputStream inputStream = EmployeeRepository.class.getClassLoader().getResourceAsStream("employees.json")) {
            if (inputStream == null) {
                throw new IOException("File employees.json not found on the classpath");
            }

            byte[] jsonData = inputStream.readAllBytes();
            employees = new ArrayList<>(Arrays.asList(mapper.readValue(jsonData, Employee[].class)));
        }
    }

    public ArrayList<Employee> getAllEmployees(Optional<Integer> initialMonth, Optional<Integer> endMonth, Optional<String> sortBy) {
        Stream<Employee> employeeStream = employees.stream();

        if (initialMonth.isPresent() && endMonth.isPresent()) {
            int initialMonthValue = initialMonth.get();
            int endMonthValue = endMonth.get();

            employeeStream = employeeStream.filter(employee -> {
                int birthMonth = employee.getBirthDay().getMonthValue();
                return birthMonth >= initialMonthValue && birthMonth <= endMonthValue;
            });
        }

        if (sortBy.isPresent()) {
            String sortByValue = sortBy.get();

            switch (sortByValue) {
                case "age":
                    employeeStream = employeeStream.sorted(Comparator.comparingInt(Employee::getAge));
                    break;
                case "name":
                    employeeStream = employeeStream.sorted(Comparator.comparing(Employee::getName));
                    break;
                default:
                    break;
            }
        }

        return employeeStream.collect(Collectors.toCollection(ArrayList::new));
    }

    public void removeEmployeeByName(String name) {
        Iterator<Employee> iterator = employees.iterator();
        while (iterator.hasNext()) {
            Employee employee = iterator.next();
            if (employee.getName().equals(name)) {
                iterator.remove();
                break;
            }
        }
    }

    public void increaseEmployeesSalary(int percentage) {
        for (Employee employee : employees) {
            employee.increaseSalary(percentage);
        }
    }

    public String getEmployeesSalary() {
        BigDecimal counter = new BigDecimal(0);
        for (Employee employee : employees) {
            counter = counter.add(employee.getSalary());
        }
        return getFormattedSalary(counter);
    }

    public Map<String, List<Employee>> getEmployeesByRole() {
        return employees.stream()
            .collect(Collectors.groupingBy(Employee::getRole));
    }
}
