package entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.Period;

public class Person {
    @JsonProperty("name")
    private String name;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthDay;

    public Person() { }

    public Person(String name, LocalDate birthDay) {
        this.name = name;
        this.birthDay = birthDay;
    }

    public String getName() {
        return this.name;
    }

    public LocalDate getBirthDay() {
        return this.birthDay;
    }

    public int getAge() {
        LocalDate currentDate = LocalDate.now();
        return Period.between(this.birthDay, currentDate).getYears();
    }
}
