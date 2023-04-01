package com.denre.employees;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static final NumberFormat moneyFormatting = NumberFormat.getCurrencyInstance(new Locale("HIN", "IN"));

    public static void main(String[] args) {
        String frnd = """
                Smith, Stive, 05/08/1995, Programmer, {locpd = 2000,yoe = 5,iq = 140}
                Smith1, Stive, 09/12/1989, Programmer, {locpd = 1200,yoe = 7,iq = 150}
                Smith2, Stive, 23/08/1993, Programmer, {locpd = 2200,yoe = 3,iq = 120}
                Smith3, Stive, 01/05/1998, Programmer, {locpd = 2400,yoe = 2,iq = 144}
                Sharma, Rohit, 11/11/1996, Manager, {orgSize = 200,dr = 10}
                Sharma1, Rohit, 18/03/1998, Manager, {orgSize = 250,dr = 5}
                Sharma2, Rohit, 12/11/1988, Manager, {orgSize = 400,dr = 9}
                Sharma3, Rohit, 01/10/1999, Manager, {orgSize = 900,dr = 7}
                Koli, Virat, 06/12/1991, Analyst, {ProjectCount = 7}
                Koli1, Virat, 28/09/1998, Analyst, {ProjectCount = 8}
                Koli2, Virat, 30/01/1990, Analyst, {ProjectCount = 9}
                Koli3, Virat, 25/07/1999, Analyst, {ProjectCount = 6}
                Allen, Finn, 29/11/1987, CEO, {avgStockPrice = 450}
                """;
        String peopleRegex = "(?<lnm>\\w+),\\s(?<fnm>\\w+),\\s(?<dob>\\d{1,2}/\\d{1,2}/\\d{4}),\\s*(?<role>\\w+)(?:,\\s*\\{(?<details>.*)\\})?\\n";
        Pattern peoplePat = Pattern.compile(peopleRegex);
        Matcher peopleMat = peoplePat.matcher(frnd);

        Double totalSalaries = 0d;
        while (peopleMat.find()) {
            totalSalaries += switch (peopleMat.group("role")) {
                case "Programmer" -> {
                    Programmer programmer = new Programmer(peopleMat.group());

                    System.out.println(programmer.toString());
                    yield programmer.getSalary();
                }
                case "Manager" -> {
                    Manager manager = new Manager(peopleMat.group());
                    System.out.println(manager.toString());
                    yield manager.getSalary();
                }

                case "Analyst" -> {
                    Analyst analyst = new Analyst(peopleMat.group());
                    System.out.println(analyst.toString());
                    yield analyst.getSalary();
                }
                case "CEO" -> {
                    CEO ceo = new CEO(peopleMat.group());
                    System.out.println(ceo.toString());
                    yield ceo.getSalary();
                }
                default -> 0;
            };
        }
        System.out.printf("The Total salaries %s%n", moneyFormatting.format(totalSalaries));
    }
}