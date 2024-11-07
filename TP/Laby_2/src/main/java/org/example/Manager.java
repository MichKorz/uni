/**
 * The Manager class orchestrates actions between the UI and the `Repository`.
 * It serves as a central point for executing library operations.
 *
 * - **Controller**: Acts as an intermediary between the UI and the data handling layer (`Repository`),
 * coordinating actions and ensuring operations are performed correctly.
 * - **High Cohesion**: Contains operations related to library management (e.g., lending books),
 * making it focused on a single purpose.
 * - **Low Coupling**: Delegates data-related tasks to the `Repository`,
 * minimizing dependencies on specific data structures and promoting flexibility.
 */

package org.example;


import java.util.Optional;

/**
 * The type Manager.
 */
public class Manager
{
    /**
     * The Repo.
     */
    Repository repo;

    /**
     * Instantiates a new Manager.
     */
    public Manager()
    {
        repo = new ConsoleRepository();
    }


    /**
     * Add book string.
     *
     * @param title the title
     * @return the string
     */
    protected String addBook(String title)
    {
        return repo.addBook(title);
    }

    /**
     * Add copies string.
     *
     * @param title  the title
     * @param copies the copies
     * @return the string
     */
    protected String addCopies(String title, int copies)
    {
        return repo.addCopies(title, copies);
    }

    /**
     * Add reader string.
     *
     * @param id the id
     * @return the string
     */
    protected String addReader(int id)
    {
        return repo.addReader(id);
    }

    /**
     * Lend book string.
     *
     * @param readerID the reader id
     * @param title    the title
     * @return the string
     */
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

    /**
     * Display books string.
     *
     * @return the string
     */
    protected String displayBooks()
    {
        return repo.displayBooks();
    }

    /**
     * Display readers string.
     *
     * @return the string
     */
    protected String displayReaders()
    {
        return repo.displayReaders();
    }
}
