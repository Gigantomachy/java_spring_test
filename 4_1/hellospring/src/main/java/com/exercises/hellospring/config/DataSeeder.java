package com.exercises.hellospring.config;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.exercises.hellospring.model.Book;
import com.exercises.hellospring.model.Author;
import com.exercises.hellospring.repository.BookRepository;
import com.exercises.hellospring.repository.AuthorRepository;

@Component
public class DataSeeder implements CommandLineRunner {

    BookRepository bookRepository;
    AuthorRepository authorRepository;

    public DataSeeder(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Author orwell = new Author();
        orwell.setFirstName("George");
        orwell.setLastName("Orwell");
        orwell.setBiography("English novelist and essayist, known for his criticism of totalitarianism.");
        orwell.setBirthDate(LocalDate.of(1903, 6, 25));
        authorRepository.save(orwell);

        Author fitzgerald = new Author();
        fitzgerald.setFirstName("F. Scott");
        fitzgerald.setLastName("Fitzgerald");
        fitzgerald.setBiography("American novelist widely regarded as one of the greatest writers of the 20th century.");
        fitzgerald.setBirthDate(LocalDate.of(1896, 9, 24));
        authorRepository.save(fitzgerald);

        Author tolkien = new Author();
        tolkien.setFirstName("J.R.R.");
        tolkien.setLastName("Tolkien");
        tolkien.setBiography("English author and philologist, best known for The Lord of the Rings.");
        tolkien.setBirthDate(LocalDate.of(1892, 1, 3));
        authorRepository.save(tolkien);

        Author herbert = new Author();
        herbert.setFirstName("Frank");
        herbert.setLastName("Herbert");
        herbert.setBiography("American science fiction author best known for the Dune series.");
        herbert.setBirthDate(LocalDate.of(1920, 10, 8));
        authorRepository.save(herbert);

        Author adams = new Author();
        adams.setFirstName("Douglas");
        adams.setLastName("Adams");
        adams.setBiography("English author and humorist, best known for The Hitchhiker's Guide to the Galaxy.");
        adams.setBirthDate(LocalDate.of(1952, 3, 11));
        authorRepository.save(adams);

        bookRepository.save(new Book(null, "1984", orwell, 1949, "978-0451524935"));
        bookRepository.save(new Book(null,"Animal Farm", orwell, 1945, "978-0452284241"));
        bookRepository.save(new Book(null,"The Great Gatsby", fitzgerald, 1925, "978-0743273565"));
        bookRepository.save(new Book(null,"The Lord of the Rings", tolkien, 1954, "978-0618640157"));
        bookRepository.save(new Book(null,"The Two Towers", tolkien, 1954, "978-0618346257"));
        bookRepository.save(new Book(null,"Dune", herbert, 1965, "978-0441013593"));
        bookRepository.save(new Book(null,"Dune Messiah", herbert, 1969, "978-0441172696"));
        bookRepository.save(new Book(null,"The Hitchhiker's Guide to the Galaxy", adams, 1979, "978-0345391803"));
        bookRepository.save(new Book(null,"The Restaurant at the End of the Universe", adams, 1980, "978-0345391810"));
        bookRepository.save(new Book(null,"So Long, and Thanks for All the Fish", adams, 1984, "978-0345391827"));
    }
}