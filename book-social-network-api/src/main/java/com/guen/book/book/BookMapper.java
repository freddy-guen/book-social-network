package com.guen.book.book;

import org.springframework.stereotype.Service;

@Service
public class BookMapper
{

    public Book toBook(BookRequest request)
    {
        return Book.builder()
                .id(request.id())
                .title(request.title())
                .authorName(request.authorName())
                .synopsis(request.synopsis())
                .archived(false)
                .shareable(request.shareable())
                .build();
    }

    public BookResponse toBookResponse(Book book)
    {
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .authorName(book.getAuthorName())
                .isbn(book.getIsbn())
                .rate(book.getRate())
                .archived(book.isArchived())
                .shareable(book.isShareable())
                .ownerName(book.getOwner().fullName())
                // TODO : A implémenter plus tard
                //.cover()
                .build();
    }
}
