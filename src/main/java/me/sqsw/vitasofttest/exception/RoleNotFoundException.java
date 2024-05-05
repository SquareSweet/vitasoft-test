package me.sqsw.vitasofttest.exception;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(String name) {
        super(String.format("Role '%s' not found", name));
    }
}
