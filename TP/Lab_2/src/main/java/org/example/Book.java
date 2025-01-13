/**
 * The Book class is responsible for holding the title of a book,
 * encapsulating this as its only data.
 *
 * - **Information Expert**: The class knows the title of the book,
 * so it is the best place to store and manage that information.
 * - **Low Coupling**: It does not interact with other classes and has a single responsibility,
 * keeping it simple and focused.
 */

package org.example;


/**
 * The type Book.
 */
public class Book
{
    /**
     * The Title.
     */
    String title;

    /**
     * Instantiates a new Book.
     *
     * @param title the title
     */
    public Book(String title)
    {
        this.title = title;
    }
}
