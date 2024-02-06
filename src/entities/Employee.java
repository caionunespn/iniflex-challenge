package entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Employee extends Person {
    @JsonProperty("salary")
    private BigDecimal salary;
    @JsonProperty("role")
    private String role;

    Employee() { }

    public Employee(String name, LocalDate birthDay, BigDecimal salary, String role) {
        super(name, birthDay);
        this.salary = salary;
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }

    public BigDecimal getSalary() {
        return this.salary;
    }

    public String getFormattedBirthDay() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return this.getBirthDay().format((formatter));
    }

    public int getMinSalaryCount() {
        return this.salary.divide(BigDecimal.valueOf(1212), 0, RoundingMode.DOWN).intValue();
    }

    public static String getFormattedSalary(BigDecimal salary) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');

        DecimalFormat decimalFormat = new DecimalFormat("#,###.##", symbols);

        return decimalFormat.format(salary);
    }

    public void increaseSalary(int percentage) {
        BigDecimal decimalPercentage = new BigDecimal(percentage).divide(new BigDecimal(100));
        BigDecimal increaseAmount = this.salary.multiply(decimalPercentage);
        this.salary = this.salary.add(increaseAmount);
    }

    @Override
    public String toString() {
        return String.format(
                "Nome do Funcionário: %s\n" +
                        "Data de Nascimento: %s\n" +
                        "Salário: %s\n" +
                        "Cargo: %s\n",
                this.getName(), this.getFormattedBirthDay(), getFormattedSalary(this.salary), role
        );
    }
}
