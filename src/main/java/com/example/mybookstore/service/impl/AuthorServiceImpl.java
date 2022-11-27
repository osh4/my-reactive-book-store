package com.example.mybookstore.service.impl;

import com.example.mybookstore.converter.impl.AuthorConverter;
import com.example.mybookstore.converter.impl.AuthorReverseConverter;
import com.example.mybookstore.data.AuthorData;
import com.example.mybookstore.exception.EntityNotFoundException;
import com.example.mybookstore.repository.UserRepository;
import com.example.mybookstore.service.AuthorService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Component
public class AuthorServiceImpl implements AuthorService {

    //    public static final String AUTHOR_HAS_BOOKS_MSG = "The author has books, please delete all books before deleting the author";
    public static final String NO_AUTHOR_MSG = "There is no such an author";
    private final AuthorReverseConverter authorReverseConverter;
    private final AuthorConverter authorConverter;
    private final UserRepository userRepository;


    public AuthorServiceImpl(AuthorReverseConverter authorReverseConverter, AuthorConverter authorConverter,
                             UserRepository userRepository) {
        this.authorReverseConverter = authorReverseConverter;
        this.authorConverter = authorConverter;
        this.userRepository = userRepository;
    }

    @Override
    public Flux<AuthorData> getAllAuthors() {
        return authorConverter.convertAll(userRepository.findAll());
    }

    @Override
    public Mono<String> addAuthor(AuthorData authorData) {
        return userRepository.findById(authorData.getEmail())
                .flatMap(x -> Mono.error(new DuplicateKeyException("The author already exists")))
                .switchIfEmpty(authorReverseConverter.convert(authorData).flatMap(userRepository::save))
                .flatMap(x -> Mono.just("Author successfully added"));
    }

    //    @Override
//    public void removeAuthor(AuthorData authorData, boolean shouldRemoveBooks) {
//        userRepository.findById(authorData.getEmail())
//                .handle((author, sink) -> {
//                    if (author == null) {
//                        sink.error(new EntityNotFoundException(NO_AUTHOR_MSG));
//                    } else if (CollectionUtils.isNotEmpty(author.getBooks()) && !shouldRemoveBooks) {
//                        sink.error(new EntityExistsException(AUTHOR_HAS_BOOKS_MSG));
//                    } else {
//                        if (CollectionUtils.isNotEmpty(author.get())) {
//                            Mono.fromCallable(() -> transactionalOperator
//                                            .execute(status -> bookRepository.deleteAll(author.getBooks())))
//                                    .subscribeOn(jdbcScheduler);
//                        }
//                        Mono.fromCallable(() -> transactionalOperator.execute(status -> userRepository.delete(author)))
//                                .subscribeOn(jdbcScheduler);
//                    }
//                });
//    }
    @Override
    public Mono<String> removeAuthor(AuthorData authorData, boolean shouldRemoveBooks) {
        return userRepository.findById(authorData.getEmail())
                .flatMap(userRepository::delete)
                .flatMap(x -> Mono.just("Author successfully removed"))
                .switchIfEmpty(Mono.error(new EntityNotFoundException(NO_AUTHOR_MSG)));
    }
}
