package com.oracle.dblearn.service.utils;

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
        new PassWordRules(pass -> pass.length() >= 8, "A senha deve ter pelo menos 8 caracteres"),
        new PassWordRules(pass -> pass.matches(".*[A-Z].*"), "A senha deve conter pelo menos uma letra maiúscula"),
        new PassWordRules(pass -> pass.matches(".*[a-z].*"), "A senha deve conter pelo menos uma letra minúscula"),
        new PassWordRules(pass -> pass.matches(".*\\d.*"), "A senha deve conter pelo menos um número"),
        new PassWordRules(pass -> pass.matches(".*[!@#$%^&*()].*"), "A senha deve conter pelo menos um caractere especial (!@#$%^&*())"),
        new PassWordRules(pass -> !pass.contains(" "), "A senha não deve conter espaços em branco")
        );
        for (PassWordRules rule : rules) {
            if (!rule.getRule().test(password)) {
                this.message.add(rule.getMessage());
            }
        }
        return this.message.isEmpty();
    }

}

