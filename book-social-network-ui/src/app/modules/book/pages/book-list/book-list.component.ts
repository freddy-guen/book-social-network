import {Component, OnInit} from '@angular/core';
import {BookService} from '../../../../services/services/book.service';
import {Router} from '@angular/router';
import {PageResponseBookResponse} from '../../../../services/models/page-response-book-response';
import {NgForOf} from '@angular/common';
import {BookCardComponent} from '../../components/book-card/book-card.component';

@Component({
  selector: 'app-book-list',
  imports: [
    NgForOf,
    BookCardComponent
  ],
  templateUrl: './book-list.component.html',
  styleUrl: './book-list.component.scss'
})
export class BookListComponent implements OnInit{

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
    this.bookService.findAllBooks({
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
}
