package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagerTest {

    Manager man;
    ConsoleRepository repo;

    @BeforeEach
    void setUp()
    {
        man = new Manager();
        repo = new ConsoleRepository();
    }

    @Test
    void addBook()
    {
        String message = man.addBook("test");
        assertEquals(message, "Book added");
    }

    @Test
    void addCopies()
    {
        man.addBook("test");
        String message = man.addCopies("test", 10);
        assertEquals(message, "Copies successfully added");
    }

    @Test
    void addReader()
    {
        String message = man.addReader(1);
        assertEquals(message, "Reader added");
    }

    @Test
    void lendBook()
    {
        man.addReader(1);
        man.addBook("test");
        man.addCopies("test", 10);
        String message = man.lendBook(1, "test");
        assertEquals(message, "Book lent successfully");
    }
}