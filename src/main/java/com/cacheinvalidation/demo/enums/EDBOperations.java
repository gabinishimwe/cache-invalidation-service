package com.cacheinvalidation.demo.enums;

public enum EDBOperations {
    CREATE("c"),
    UPDATE("u"),
    DELETE("d"),
    TRUNCATE("t");

    private String operation;

    EDBOperations(String operation) {
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }
}
