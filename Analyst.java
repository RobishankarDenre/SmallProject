package com.denre.employees;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Analyst {
    private String lastName;
    private String firstName;
    private LocalDate dob;
    private final String role = "Analyst";
    private int numbersOfProject = 0;
    private final String peopleRegex = "(?<lnm>\\w+),\\s(?<fnm>\\w+),\\s(?<dob>\\d{1,2}/\\d{1,2}/\\d{4}),\\s*(?<role>\\w+)(?:,\\s*\\{(?<details>.*)\\})?\\n";
    private final Pattern peoplePat = Pattern.compile(peopleRegex);
    private final String analystRegex = "\\w+\\s?=\\s?(?<proCount>\\w+)";
    private final Pattern analystPat = Pattern.compile(analystRegex);

    private final NumberFormat moneyFormat = NumberFormat.getCurrencyInstance(new Locale("ENG", "IN"));
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyy");


    public Analyst(String analystText) {
        Matcher peopleMat = peoplePat.matcher(analystText);
        if (peopleMat.find()) {
            this.lastName = peopleMat.group("lnm");
            this.firstName = peopleMat.group("fnm");
            this.dob = LocalDate.from(dateTimeFormatter.parse(peopleMat.group("dob")));
            Matcher anaMat = analystPat.matcher(peopleMat.group("details"));
            if (anaMat.find()) {
                this.numbersOfProject = Integer.parseInt(anaMat.group("proCount"));
            }
        }
    }

    public double getSalary() {
        return 2200 + 1 * Math.pow(numbersOfProject, numbersOfProject - 2);
//        2200 + 1  * Math.pow(numberOfProject, numberOfProject - 2)
    }

    @Override
    public String toString() {
        String salary = moneyFormat.format(getSalary());
        return String.format("%s, %s, %s, %s", lastName, firstName, role, salary);
    }
}
