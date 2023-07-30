package com.polarbookshop.catalogservice;

import com.polarbookshop.catalogservice.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CatalogServiceApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void whenGetRequestWithIdThenBookReturned() {
        String isbn = "1231231230";
        Book book = createNewBook(isbn);

        Book expectedBook = webTestClient
                .post()
                .uri("/books")
                .bodyValue(book)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class).value(b -> assertThat(book).isNotNull())
                .returnResult().getResponseBody();

        webTestClient
                .get()
                .uri("/books/" + isbn)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Book.class).value(actualBook -> {
                    assertThat(actualBook).isNotNull();
                    assertThat(actualBook.isbn()).isEqualTo(expectedBook.isbn());
                });
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void whenPostRequestThenBookCreated() {
        Book expectedBook = createNewBook("1231231231");

        webTestClient
                .post()
                .uri("/books")
                .bodyValue(expectedBook)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class).value(actualBook -> {
                    assertThat(actualBook).isNotNull();
                    assertThat(actualBook.isbn()).isEqualTo(expectedBook.isbn());
                });
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void whenPutRequestThenBookUpdated() {
        String isbn = "1231231232";
        Book bookToCreate = createNewBook(isbn);
        Book createdBook = webTestClient
                .post()
                .uri("/books")
                .bodyValue(bookToCreate)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class).value(book -> assertThat(book).isNotNull())
                .returnResult().getResponseBody();
        assert createdBook != null;
        Book bookToUpdate = Book.builder().isbn(createdBook.isbn()).title(createdBook.title()).author(createdBook.author()).price(7.95).build();

        webTestClient
                .put()
                .uri("/books/" + isbn)
                .bodyValue(bookToUpdate)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Book.class).value(actualBook -> {
                    assertThat(actualBook).isNotNull();
                    assertThat(actualBook.price()).isEqualTo(bookToUpdate.price());
                });
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void whenDeleteRequestTheBookDeleted() {
        var bookIsbn = "1231231233";
        Book bookToCreate = createNewBook(bookIsbn);
        webTestClient
                .post()
                .uri("/books")
                .bodyValue(bookToCreate)
                .exchange()
                .expectStatus().isCreated();

        webTestClient
                .delete()
                .uri("/books/" + bookIsbn)
                .exchange()
                .expectStatus().isNoContent();

        webTestClient
                .get()
                .uri("/books/" + bookIsbn)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(String.class).value(errormessage -> assertThat(errormessage).isEqualTo("The book with ISBN " + bookIsbn + " was not found."));
    }

    private static Book createNewBook(String isbn) {
        return Book.builder().isbn(isbn).title("Title").author("Author").price(9.90).build();
    }
}
