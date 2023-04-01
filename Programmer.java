package com.denre.employees;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Programmer {
    private String lastName;
    private String firstName;
    private final String role = "Programmer";
    private LocalDate dob;

    private int linesOfCode = 0;
    private int yearsOfExp = 0;
    private int iq = 0;

    private final String peopleRegex = "(?<lnm>\\w+),\\s(?<fnm>\\w+),\\s(?<dob>\\d{1,2}/\\d{1,2}/\\d{4}),\\s*(?<role>\\w+)(?:,\\s*\\{(?<details>.*)\\})?\\n";
    private final Pattern peoplePat = Pattern.compile(peopleRegex);

    private final String proRegex = "\\w+\\s?=\\s?(?<locpd>\\w+),\\w+\\s?=\\s?(?<yoe>\\w+),\\w+\\s?=\\s?(?<iq>\\w+)";
    private final Pattern proPat = Pattern.compile(proRegex);
    private final NumberFormat moneyFormat = NumberFormat.getCurrencyInstance(new Locale("ENG","IN"));

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Programmer(String programmerText) {
        Matcher peopleMat = peoplePat.matcher(programmerText);
        if (peopleMat.find()) {
            this.lastName = peopleMat.group("lnm");
            this.firstName = peopleMat.group("fnm");
            this.dob = LocalDate.from(dateTimeFormatter.parse(peopleMat.group("dob")));

            Matcher proMat = proPat.matcher(peopleMat.group("details"));
            if (proMat.find()) {
                this.linesOfCode = Integer.parseInt(proMat.group("locpd"));
                this.yearsOfExp = Integer.parseInt(proMat.group("yoe"));
                this.iq = Integer.parseInt(proMat.group("iq"));
            }
        }
    }

    public int getSalary() {
//        salary = 3000 + locpd * iq * yoe;
        return 3000 + linesOfCode * yearsOfExp * iq;
    }

    @Override
    public String toString() {
        String salary = moneyFormat.format(getSalary());
        return String.format("%s, %s, %s, %s",lastName,firstName,role, salary);
    }
}
