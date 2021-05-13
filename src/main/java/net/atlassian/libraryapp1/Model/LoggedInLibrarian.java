package net.atlassian.libraryapp1.Model;

public class LoggedInLibrarian {
    public static String username;
    public static String getUsername()
    {
        return username;
    }
    public static void setUsername(String username)
    {
        LoggedInLibrarian.username= username;
    }
}
