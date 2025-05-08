import {Component, OnInit} from '@angular/core';
import {NgForOf, NgIf} from '@angular/common';
import {BorrowedBookResponse} from '../../../../services/models/borrowed-book-response';
import {PageResponseBorrowedBookResponse} from '../../../../services/models/page-response-borrowed-book-response';
import {BookService} from '../../../../services/services/book.service';
import {FeedbackRequest} from '../../../../services/models/feedback-request';
import {FormsModule} from '@angular/forms';
import {RatingComponent} from '../../components/rating/rating.component';
import {FeedbackService} from '../../../../services/services/feedback.service';

@Component({
  selector: 'app-borrow-book-list',
  imports: [
    NgForOf,
    NgIf,
    FormsModule,
    RatingComponent
  ],
  templateUrl: './borrow-book-list.component.html',
  styleUrl: './borrow-book-list.component.scss'
})
export class BorrowBookListComponent implements OnInit {

  borrowedBooks : PageResponseBorrowedBookResponse = {};
  page = 0;
  size = 5;
  selectedBook : BorrowedBookResponse | undefined = undefined;
  feedBackRequest : FeedbackRequest = {bookId: 0, comment: "", note : 0};

  constructor(
    private bookService : BookService,
    private feedBackService : FeedbackService
  ) {
  }

  ngOnInit(): void {
    this.findAllBorrowedBooks();
  }

  returnBorrowedBook(book : BorrowedBookResponse)
  {
    this.selectedBook = book;
    this.feedBackRequest.bookId = book.id as number;
  }

  private findAllBorrowedBooks()
  {
    this.bookService.findAllBorrowedBooks({
      page : this.page,
      size : this.size
    }).subscribe({
      next : (result) => {
        this.borrowedBooks = result;
      }
    });
  }

  gotToFirstPage()
  {
    this.page = 0;
    this.findAllBorrowedBooks();
  }

  gotToPreviousPage()
  {
    this.page--;
    this.findAllBorrowedBooks();
  }

  gotToPage(page: number)
  {
    this.page = page;
    this.findAllBorrowedBooks();
  }

  gotToNextPage()
  {
    this.page++;
    this.findAllBorrowedBooks();
  }

  gotToLastPage()
  {
    this.page = this.borrowedBooks.totalPages as number -1;
    this.findAllBorrowedBooks();
  }

  get isLastPage() : boolean
  {
    return this.page == this.borrowedBooks.totalPages as number - 1;
  }

  returnBook(withFeedBack: boolean)
  {
    this.bookService.returnBorrowedBook({
      'book-id' : this.selectedBook?.id as number
    }).subscribe({
      next : () => {
        if (withFeedBack)
        {
          this.giveFeedBack();
        }
        this.selectedBook = undefined;
        this.findAllBorrowedBooks();
      }
    })
  }

  private giveFeedBack()
  {
    this.feedBackService.saveFeedback({
      body : this.feedBackRequest
    }).subscribe({
      next: () => {
        // Do nothing
      }
    })
  }
}
