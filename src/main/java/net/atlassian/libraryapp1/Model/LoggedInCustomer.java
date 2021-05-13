package net.atlassian.libraryapp1.Model;

public class LoggedInCustomer {

    private static String username;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        LoggedInCustomer.username = username;
    }
}
