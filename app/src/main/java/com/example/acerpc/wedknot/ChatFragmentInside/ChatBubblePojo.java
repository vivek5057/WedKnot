package com.example.acerpc.wedknot.ChatFragmentInside;

public class ChatBubblePojo {
    String currentUserMessage;
    String messageEmail;

    public ChatBubblePojo() {
    }

    public ChatBubblePojo(String currentUserMessage, String messageEmail) {
        this.currentUserMessage = currentUserMessage;
        this.messageEmail = messageEmail;
    }

    public String getCurrentUserMessage() {
        return currentUserMessage;
    }

    public void setCurrentUserMessage(String currentUserMessage) {
        this.currentUserMessage = currentUserMessage;
    }

    public String getMessageEmail() {
        return messageEmail;
    }

    public void setMessageEmail(String messageEmail) {
        this.messageEmail = messageEmail;
    }
}