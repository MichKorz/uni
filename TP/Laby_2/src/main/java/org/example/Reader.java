package org.example;

import java.util.ArrayList;

public class Reader
{
    final private int id;
    final private ArrayList<Copy> lentBooks;

    public Reader(int id)
    {
        this.id = id;
        lentBooks = new ArrayList<>();
    }

    void lendBook(Copy copy)
    {
        lentBooks.add(copy);
    }

    int getID()
    {
        return id;
    }

    ArrayList<Copy> getLentBooks()
    {
        return lentBooks;
    }
}
