import {Component, OnInit} from '@angular/core';
import {BookCardComponent} from "../../components/book-card/book-card.component";
import {NgForOf, NgIf} from "@angular/common";
import {PageResponseBookResponse} from '../../../../services/models/page-response-book-response';
import {BookService} from '../../../../services/services/book.service';
import {Router, RouterLink} from '@angular/router';
import {BookResponse} from '../../../../services/models/book-response';

@Component({
  selector: 'app-my-books',
  imports: [
    BookCardComponent,
    NgForOf,
    RouterLink
  ],
  templateUrl: './my-books.component.html',
  styleUrl: './my-books.component.scss'
})
export class MyBooksComponent implements OnInit {

  bookResponse: PageResponseBookResponse = {};
  page: number = 0;
  size: number = 2;

  constructor(
    private bookService: BookService,
    private router: Router
  ) {
  }

  ngOnInit(): void
  {
    this.findAllBooks();
  }

  private findAllBooks()
  {
    this.bookService.findAllBooksByOwner({
      size: this.size,
      page: this.page
    }).subscribe({
      next: (books) => {
        this.bookResponse = books;
      }
    });
  }

  gotToFirstPage()
  {
    this.page = 0;
    this.findAllBooks();
  }

  gotToPreviousPage()
  {
    this.page--;
    this.findAllBooks();
  }

  gotToPage(page: number)
  {
    this.page = page;
    this.findAllBooks();
  }

  gotToNextPage()
  {
    this.page++;
    this.findAllBooks();
  }

  gotToLastPage()
  {
    this.page = this.bookResponse.totalPages as number -1;
    this.findAllBooks();
  }

  get isLastPage() : boolean
  {
    return this.page == this.bookResponse.totalPages as number - 1;
  }

  archiveBook(book: BookResponse)
  {
    this.bookService.updateArchivedStatus({
      'book-id' : book.id as number
    }).subscribe({
      next : () => {
        book.archived = !book.archived;
      }
    });
  }

  shareBook(book: BookResponse)
  {
    this.bookService.updateShareableStatus({
      'book-id': book.id as number
    }).subscribe({
      next : () => {
        book.shareable = !book.shareable
      }
    });
  }

  editBook(book: BookResponse)
  {
    this.router.navigate(['books', 'manage', book.id]);
  }
}
