package com.example.demo.resolver;
import com.example.demo.model.Book;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.ArrayList;
@Component
public class BookResolver {
    private static List<Book> books = new ArrayList<>();
    static {
        books.add(new Book("1","1984","George Orwell"));
        books.add(new Book("2","Brave New World","Aldous Huxley"));
        books.add(new Book("3","Fahrenheit 451","Ray Bradbury"));
    }
    public List<Book> getBooks() { return books; }
    public Book getBookById(String id) {
        return books.stream().filter(b->b.getId().equals(id)).findFirst().orElse(null);
    }
}