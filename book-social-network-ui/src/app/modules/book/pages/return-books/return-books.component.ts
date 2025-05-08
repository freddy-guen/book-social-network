import {Component, OnInit} from '@angular/core';
import {NgForOf, NgIf} from "@angular/common";
import {PageResponseBorrowedBookResponse} from '../../../../services/models/page-response-borrowed-book-response';
import {BorrowedBookResponse} from '../../../../services/models/borrowed-book-response';
import {FeedbackRequest} from '../../../../services/models/feedback-request';
import {BookService} from '../../../../services/services/book.service';
import {FeedbackService} from '../../../../services/services/feedback.service';

@Component({
  selector: 'app-return-books',
    imports: [
        NgForOf,
        NgIf
    ],
  templateUrl: './return-books.component.html',
  styleUrl: './return-books.component.scss'
})
export class ReturnBooksComponent implements OnInit {

  returnedBooks : PageResponseBorrowedBookResponse = {};
  page = 0;
  size = 5;
  message = '';
  level = 'succes';

  constructor(
    private bookService : BookService
  ) {
  }

  ngOnInit(): void {
    this.findAllReturnedBooks();
  }

  private findAllReturnedBooks()
  {
    this.bookService.findAllReturnedBooks({
      page : this.page,
      size : this.size
    }).subscribe({
      next : (result) => {
        this.returnedBooks = result;
      }
    });
  }

  gotToFirstPage()
  {
    this.page = 0;
    this.findAllReturnedBooks();
  }

  gotToPreviousPage()
  {
    this.page--;
    this.findAllReturnedBooks();
  }

  gotToPage(page: number)
  {
    this.page = page;
    this.findAllReturnedBooks();
  }

  gotToNextPage()
  {
    this.page++;
    this.findAllReturnedBooks();
  }

  gotToLastPage()
  {
    this.page = this.returnedBooks.totalPages as number -1;
    this.findAllReturnedBooks();
  }

  get isLastPage() : boolean
  {
    return this.page == this.returnedBooks.totalPages as number - 1;
  }

  approveBookReturn(book: BorrowedBookResponse)
  {
    if (!book.returned)
    {
      this.level = 'error';
      this.message = 'Le livre n\'est pas encore rendu';
      return;
    }
    this.bookService.approveReturnBorrowedBook({
      'book-id' : book.id as number
    }).subscribe({
      next : () => {
        this.level = 'success';
        this.message = 'Le livre rendu est approuvé';
        this.findAllReturnedBooks();
      }
    });
  }
}
