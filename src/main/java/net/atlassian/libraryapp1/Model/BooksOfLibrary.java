package net.atlassian.libraryapp1.Model;

public class BooksOfLibrary {

    private static String libraryName;

    public static String getLibraryName() {
        return libraryName;
    }

    public static void setLibraryName(String libraryName) {
        BooksOfLibrary.libraryName = libraryName;
    }
}
