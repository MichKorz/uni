package org.example;


import java.util.Optional;

public class Manager
{
    Repository repo;

    public Manager()
    {
        repo = new ConsoleRepository();
    }


    protected String addBook(String title)
    {
        return repo.addBook(title);
    }

    protected String addCopies(String title, int copies)
    {
        return repo.addCopies(title, copies);
    }

    protected String addReader(int id)
    {
        return repo.addReader(id);
    }

    protected String lendBook(int readerID, String title)
    {
        Optional<Book> book = repo.getBook(title);
        if (book.isEmpty()) return "Book does NOT exist!";

        Optional<Copy> copy = repo.getFreeCopy(title);
        if (copy.isEmpty()) return "There are no more free copies!";

        Optional<Reader> reader = repo.getReader(readerID);
        if (reader.isEmpty()) return "Reader does NOT exist!";

        reader.get().lendBook(copy.get());
        copy.get().changeState(true);
        return "Book lent successfully";
    }

    protected String displayBooks()
    {
        return repo.displayBooks();
    }

    protected String displayReaders()
    {
        return repo.displayReaders();
    }
}
