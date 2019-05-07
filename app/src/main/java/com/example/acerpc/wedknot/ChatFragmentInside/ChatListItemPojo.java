package com.example.acerpc.wedknot.ChatFragmentInside;

public class ChatListItemPojo {
    private String chatimage;
    private String chatname;
            String chatEmail;

    public ChatListItemPojo(String chatimage, String chatname, String chatEmail) {
        this.chatimage = chatimage;
        this.chatname = chatname;
        this.chatEmail = chatEmail;
    }

    public String getChatimage() {
        return chatimage;
    }

    public void setChatimage(String chatimage) {
        this.chatimage = chatimage;
    }

    public String getChatname() {
        return chatname;
    }

    public void setChatname(String chatname) {
        this.chatname = chatname;
    }

    public String getChatEmail() {
        return chatEmail;
    }

    public void setChatEmail(String chatEmail) {
        this.chatEmail = chatEmail;
    }
}
