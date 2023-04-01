package com.denre.employees;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CEO {
    private String lastName;
    private String firstName;
    private LocalDate dob;
    private final String role = "CEO";
    private double avgStockPrice = 0.0;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final NumberFormat moneyFormat = NumberFormat.getCurrencyInstance(new Locale("ENG", "IN"));
    private final String peopleRegex = "(?<lnm>\\w+),\\s(?<fnm>\\w+),\\s(?<dob>\\d{1,2}/\\d{1,2}/\\d{4}),\\s*(?<role>\\w+)(?:,\\s*\\{(?<details>.*)\\})?\\n";
    private final Pattern peoplePat = Pattern.compile(peopleRegex);
    private final String ceoRegex = "\\w+\\s?=\\s?(?<avgStkPrice>\\w+)";
    private final Pattern ceoPat = Pattern.compile(ceoRegex);

    public CEO(String ceoText) {
        Matcher peopleMat = peoplePat.matcher(ceoText);
        if (peopleMat.find()) {
            this.lastName = peopleMat.group("lnm");
            this.firstName = peopleMat.group("fnm");
            this.dob = LocalDate.from(dateTimeFormatter.parse(peopleMat.group("dob")));
            Matcher ceoMat = ceoPat.matcher(peopleMat.group("details"));
            if (ceoMat.find()) {
                this.avgStockPrice = Double.parseDouble(ceoMat.group("avgStkPrice"));
            }
        }
    }

    public Double getSalary() {
//        salary = 5500 * avgStockPrice;
        return 5500 * avgStockPrice;
    }

    @Override
    public String toString() {
        String salary = moneyFormat.format(getSalary());
        return String.format("%s, %s, %s, %s", lastName, firstName, role, salary);
    }
}
