package model.entities;

public record GameNotification(String message, boolean isRequestingInput, boolean isGameEnded) {
}
