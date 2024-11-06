/**
 * The ConsoleRepository class implements the `Repository` interface, storing data about books, copies, and readers.
 * It manages all the library’s in-memory data storage and retrieval.
 *
 * - **Information Expert**: As it contains information on books, copies, and readers, it is best suited to manage data operations for these entities.
 * - **Low Coupling**: This class interacts with `Book`, `Copy`, and `Reader` objects without depending on the UI or business logic, promoting separation of concerns.
 * - **High Cohesion**: Focuses solely on managing data, making it cohesive and easy to modify independently of business logic.
 */

package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

/**
 * The type Console repository.
 */
public class ConsoleRepository implements Repository
{
    final private HashMap<String, Book> bookList;
    final private HashMap<String, ArrayList<Copy>> copyList;
    final private HashMap<Integer, Reader> readerList;

    private int bookCount;

    /**
     * Instantiates a new Console repository.
     */
    public ConsoleRepository()
    {
        bookList = new HashMap<>();
        copyList = new HashMap<>();
        readerList = new HashMap<>();

        bookCount = 0;
    }


    @Override
    public String addBook(String title)
    {
        bookList.put(title, new Book(title));
        copyList.put(title, new ArrayList<>());
        return "Book added";
    }

    @Override
    public Optional<Book> getBook(String title)
    {
        return Optional.ofNullable(bookList.get(title));
    }

    @Override
    public String addCopies(String title, int count)
    {
        Optional<Book> book = getBook(title);
        if (book.isPresent())
        {
            ArrayList<Copy> copies = copyList.get(title);
            for (int i = 0; i < count; i++)
            {
                copies.add(new Copy(bookCount));
                bookCount++;
            }
            return "Copies successfully added";
        }
        else
        {
            return "Book does not exist!";
        }
    }

    @Override
    public Optional<Copy> getFreeCopy(String title)
    {
        Optional<Book> book = getBook(title);

        if (book.isPresent())
        {
            ArrayList<Copy> copies = copyList.get(title);
            for (Copy copy : copies)
            {
                if (!copy.isLent())
                {
                    return Optional.of(copy);
                }
            }
        }

        return Optional.empty();
    }

    @Override
    public String addReader(int id)
    {
        readerList.put(id, new Reader(id));
        return "Reader added";
    }

    @Override
    public Optional<Reader> getReader(int id)
    {
        return Optional.ofNullable(readerList.get(id));
    }

    @Override
    public String displayBooks()
    {
        StringBuilder list = new StringBuilder();
        ArrayList<String> books = new ArrayList<>(bookList.keySet());

        for (String book : books)
        {
            list.append(book);
            list.append(" : ");
            list.append(copyList.get(book).size());
            list.append("\n");
        }

        return list.toString();
    }

    @Override
    public String displayReaders()
    {

        StringBuilder list = new StringBuilder();
        ArrayList<Reader> readers = new ArrayList<>(readerList.values());

        for (Reader reader : readers)
        {
            list.append(reader.getID());
            list.append(" : ");
            ArrayList<Copy> lentBooks = reader.getLentBooks();
            for (Copy copy : lentBooks)
            {
                list.append(copy.getId());
                list.append(", ");
            }
            list.append("\n");
        }

        return list.toString();
    }
}
