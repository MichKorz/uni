package org.example;

public class Copy
{
    final private int id;
    private boolean lent;

    public Copy(int id)
    {
        this.id = id;
        lent = false;
    }

    boolean isLent()
    {
        return lent;
    }

    void changeState(boolean state)
    {
        lent = state;
    }

    int getId()
    {
        return id;
    }
}
