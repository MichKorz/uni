/**
 * The Reader class is responsible for representing a reader in the library system.
 * It encapsulates the ID and manages the books that are lent to the reader.
 *
 * - **Information Expert**: The class is responsible for handling information about the reader's lent books, so it knows which books have been borrowed.
 * - **Low Coupling**: The class only interacts with `Copy` objects and manages a collection of them without requiring information about the entire library.
 */

package org.example;

import java.util.ArrayList;

/**
 * The type Reader.
 */
public class Reader
{
    final private int id;
    final private ArrayList<Copy> lentBooks;

    /**
     * Instantiates a new Reader.
     *
     * @param id the id
     */
    public Reader(int id)
    {
        this.id = id;
        lentBooks = new ArrayList<>();
    }

    /**
     * Lend book.
     *
     * @param copy the copy
     */
    void lendBook(Copy copy)
    {
        lentBooks.add(copy);
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    int getID()
    {
        return id;
    }

    /**
     * Gets lent books.
     *
     * @return the lent books
     */
    ArrayList<Copy> getLentBooks()
    {
        return lentBooks;
    }
}
