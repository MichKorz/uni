package org.example;

import javax.print.attribute.standard.MediaSize;
import java.util.Optional;

public interface Repository
{
    String addBook(String title);
    Optional<Book> getBook(String title);

    String addCopies(String title, int count);
    Optional<Copy> getFreeCopy(String title);

    String addReader(int id);
    Optional<Reader> getReader(int id);

    String displayBooks();

    String displayReaders();
}
