package com.example.converter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @GetMapping("/")
    public String homePage(Model model) {
        return "home";
    }

    @GetMapping("/calculator")
    public String calculatorPage(Model model) {
        return "calculator";
    }

    @PostMapping("/calculate")
    public String calculate(
            @RequestParam double num1,
            @RequestParam String operation,
            @RequestParam double num2,
            Model model) {

        double result = 0;
        switch (operation) {
            case "add":
                result = num1 + num2;
                break;
            case "subtract":
                result = num1 - num2;
                break;
            case "multiply":
                result = num1 * num2;
                break;
            case "divide":
                if (num2 != 0) {
                    result = num1 / num2;
                } else {
                    model.addAttribute("error", "Деление на ноль невозможно");
                    return "calculator";
                }
                break;
        }
        model.addAttribute("result", result);
        return "result";
    }

    @GetMapping("/currency-converter")
    public String currencyConverterPage() {
        return "currency-converter";
    }

    @PostMapping("/convert")
    public String convert(
            @RequestParam String fromCurrency,
            @RequestParam String toCurrency,
            @RequestParam double amount,
            Model model) {

        double conversionRate = 1.0;

        if (fromCurrency.equals("RUB") && toCurrency.equals("GBP")) {
            conversionRate = 0.0085;
        } else if (fromCurrency.equals("RUB") && toCurrency.equals("CNY")) {
            conversionRate = 0.074;
        } else if (fromCurrency.equals("GBP") && toCurrency.equals("RUB")) {
            conversionRate = 117.0;
        } else if (fromCurrency.equals("GBP") && toCurrency.equals("CNY")) {
            conversionRate = 8.74;
        } else if (fromCurrency.equals("CNY") && toCurrency.equals("RUB")) {
            conversionRate = 13.5;
        } else if (fromCurrency.equals("CNY") && toCurrency.equals("GBP")) {
            conversionRate = 0.11;
        }

        double convertedAmount = amount * conversionRate;

        model.addAttribute("amount", amount);
        model.addAttribute("fromCurrency", fromCurrency);
        model.addAttribute("toCurrency", toCurrency);
        model.addAttribute("convertedAmount", convertedAmount);

        return "conversion-result";
    }
}
