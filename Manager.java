package com.denre.employees;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Manager {
    private String lastName;
    private String firstName;
    private final String role = "Manager";
    private LocalDate dob;

    private int orgSize = 0;
    private int dr = 0;
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyy");
    private final NumberFormat moneyFormat = NumberFormat.getCurrencyInstance(new Locale("ENG", "IN"));

    private final String peopleRegex = "(?<lnm>\\w+),\\s(?<fnm>\\w+),\\s(?<dob>\\d{1,2}/\\d{1,2}/\\d{4}),\\s*(?<role>\\w+)(?:,\\s*\\{(?<details>.*)\\})?\\n";
    private final Pattern peoplePat = Pattern.compile(peopleRegex);

    private final String managerRegex = "\\w+\\s?=\\s?(?<orgSize>\\w+),\\w+\\s?=\\s?(?<dr>\\w+)";
    private final Pattern managerPat = Pattern.compile(managerRegex);

    public Manager(String managerText) {
        Matcher peopleMat = peoplePat.matcher(managerText);
        if (peopleMat.find()) {
            this.lastName = peopleMat.group("lnm");
            this.firstName = peopleMat.group("fnm");
            this.dob = LocalDate.from(dateTimeFormatter.parse(peopleMat.group("dob")));

            Matcher mgrMat = managerPat.matcher(peopleMat.group("details"));
            if (mgrMat.find()) {
                this.orgSize = Integer.parseInt(mgrMat.group("orgSize"));
                this.dr = Integer.parseInt(mgrMat.group("dr"));
            }
        }
    }

    public Double getSalary() {
        return 2500 + Math.pow(orgSize, 2) + dr;
    }

    @Override
    public String toString() {
        String salary = moneyFormat.format(getSalary());
        return String.format("%s, %s, %s, %s, %d", lastName, firstName, role, salary,dr);
    }
}
