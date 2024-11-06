/**
 * The Repository interface defines methods for managing books, copies, and readers in the system.
 * It serves as an abstraction layer for data handling.
 *
 * - **Controller**: Provides an abstraction for managing data-related operations across the system, centralizing book and reader operations.
 * - **High Cohesion**: This interface defines a clear set of related responsibilities, keeping all data access methods grouped in one place.
 */

package org.example;

import javax.print.attribute.standard.MediaSize;
import java.util.Optional;

/**
 * The interface Repository.
 */
public interface Repository
{
    /**
     * Add book string.
     *
     * @param title the title
     * @return the string
     */
    String addBook(String title);

    /**
     * Gets book.
     *
     * @param title the title
     * @return the book
     */
    Optional<Book> getBook(String title);

    /**
     * Add copies string.
     *
     * @param title the title
     * @param count the count
     * @return the string
     */
    String addCopies(String title, int count);

    /**
     * Gets free copy.
     *
     * @param title the title
     * @return the free copy
     */
    Optional<Copy> getFreeCopy(String title);

    /**
     * Add reader string.
     *
     * @param id the id
     * @return the string
     */
    String addReader(int id);

    /**
     * Gets reader.
     *
     * @param id the id
     * @return the reader
     */
    Optional<Reader> getReader(int id);

    /**
     * Display books string.
     *
     * @return the string
     */
    String displayBooks();

    /**
     * Display readers string.
     *
     * @return the string
     */
    String displayReaders();
}
