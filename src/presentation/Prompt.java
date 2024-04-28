package presentation;

public record Prompt(String message, PromptType type){
    public enum PromptType {
        SELECT_TOY, MAKE_BET

    }
}

