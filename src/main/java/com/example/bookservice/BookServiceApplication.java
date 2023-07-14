package com.example.bookservice;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

@SpringBootApplication
public class BookServiceApplication {

	public static void main(String[] args) {
		Properties properties = System.getProperties();
		for (String key : properties.stringPropertyNames()) {
			String value = properties.getProperty(key);
			System.out.println(key + " = " + value);
		}
		SpringApplication.run(BookServiceApplication.class, args);
	}

	@Bean
	ApplicationRunner runner(final BookRepository bookRepository) {
		return new ApplicationRunner() {
			@Override
			public void run(ApplicationArguments args) throws Exception {
				bookRepository.findAll().forEach(System.out::println);
			}
		};
	}
}

@RestController()
class BookController {
	private final BookRepository bookRepository;

	BookController(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@GetMapping("/all")
	Iterable<Book> allBooks() {
		return this.bookRepository.findAll();
	}

	@GetMapping("/byId/{id}")
	Book byId(@PathVariable Integer id) {
		System.out.println("Fetching book by id [" + id + "]");
		Optional<Book> optionalBook = this.bookRepository.findById(id);
		if (optionalBook.isPresent() ) {
			return optionalBook.get();
		}
		// throws exception
		Assert.state(false, String.format("book with id %s is not found", id));
		return null;
	}

	@DeleteMapping("/book/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void deleteBookById(@PathVariable Integer id) {
		System.out.println("Fetching book by id [" + id + "]");
		Optional<Book> optionalBook = this.bookRepository.findById(id);
		if (optionalBook.isEmpty()) {
			// throws exception
			Assert.state(false, String.format("book with id %s is not found", id));
		} else {
			this.bookRepository.delete(optionalBook.get());
		}
	}

}
interface BookRepository extends CrudRepository<Book, Integer> {}

record Book(@Id Integer id, String title) { }

@ControllerAdvice
class ErrorHandlingControllerAdvice {

	@ExceptionHandler
	public ProblemDetail handleException(IllegalStateException iae, HttpServletRequest request) {
		String requestURI = request.getRequestURI();
		System.out.println(requestURI);
		return ProblemDetail
				.forStatusAndDetail(HttpStatus.BAD_REQUEST, iae.getMessage());
	}

}