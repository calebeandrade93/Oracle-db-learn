package com.oracle.dblearn.infra.utils;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class PasswordChecker {

    private String password;
    @Getter
    private List<String> message = new ArrayList<>();

    public PasswordChecker(String password) {
        this.password = password;
    }

    public boolean isValid() {
        List<PassWordRules> rules = List.of(
        new PassWordRules(pass -> pass.length() >= 8, "Password must be at least 8 characters long"),
        new PassWordRules(pass -> pass.matches(".*[A-Z].*"), "Password must contain at least one uppercase letter"),
        new PassWordRules(pass -> pass.matches(".*[a-z].*"), "Password must contain at least one lowercase letter"),
        new PassWordRules(pass -> pass.matches(".*\\d.*"), "Password must contain at least one digit"),
        new PassWordRules(pass -> pass.matches(".*[!@#$%^&*()].*"), "Password must contain at least one special character (!@#$%^&*())"),
        new PassWordRules(pass -> !pass.contains(" "), "Password must not contain spaces")
        );
        for (PassWordRules rule : rules) {
            if (!rule.getRule().test(password)) {
                this.message.add(rule.getMessage());
            }
        }
        return this.message.isEmpty();
    }

}

