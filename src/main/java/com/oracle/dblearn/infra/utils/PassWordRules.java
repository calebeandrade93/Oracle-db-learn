package com.oracle.dblearn.infra.utils;

import lombok.Getter;

import java.util.function.Predicate;

@Getter
public class PassWordRules {

    Predicate<String> rule;
    String message;

    public PassWordRules(Predicate<String> rule, String message) {
        this.rule = rule;
        this.message = message;
    }

}
