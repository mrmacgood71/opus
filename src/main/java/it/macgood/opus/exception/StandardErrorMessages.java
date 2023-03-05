package it.macgood.opus.exception;

public enum StandardErrorMessages {
    NOT_FOUND("User not found. Check your login or password"),
    ALREADY_EXISTS("This user already exists");

    private String text;

    StandardErrorMessages(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
