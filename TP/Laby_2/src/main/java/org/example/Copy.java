/**
 * The Copy class represents a copy of a book in the library system. It tracks its own unique ID and whether it is currently lent out.
 *
 * - **Information Expert**: As a copy of a book, it knows its current lent status, which makes it the best choice to change or check that state.
 * - **Low Coupling**: This class has minimal dependencies, as it only tracks its own state and does not need other classes to manage its functionality.
 */

package org.example;

/**
 * The type Copy.
 */
public class Copy
{
    final private int id;
    private boolean lent;

    /**
     * Instantiates a new Copy.
     *
     * @param id the id
     */
    public Copy(int id)
    {
        this.id = id;
        lent = false;
    }

    /**
     * Is lent boolean.
     *
     * @return the boolean
     */
    boolean isLent()
    {
        return lent;
    }

    /**
     * Change state.
     *
     * @param state the state
     */
    void changeState(boolean state)
    {
        lent = state;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    int getId()
    {
        return id;
    }
}
