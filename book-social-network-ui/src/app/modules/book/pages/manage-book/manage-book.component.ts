import { Component } from '@angular/core';
import {NgForOf, NgIf} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {BookRequest} from '../../../../services/models/book-request';
import {Router, RouterLink} from '@angular/router';
import {BookService} from '../../../../services/services/book.service';

@Component({
  selector: 'app-manage-book',
  imports: [
    NgIf,
    NgForOf,
    FormsModule,
    RouterLink
  ],
  templateUrl: './manage-book.component.html',
  styleUrl: './manage-book.component.scss'
})
export class ManageBookComponent {

  bookRequest : BookRequest = {
    authorName: '',
    isbn: '',
    synopsis: '',
    title: ''
  };
  errorMessage : Array<string> = [];
  selectedBookCover : any;
  selectedPicture : string | undefined;

  constructor(
    private bookService : BookService,
    private router : Router
  ) {
  }

  /**
   * Cette méthode fait que si l'on sélectionne un fichier image, l'image est automatique affichée après la sélection
   * @param event
   */
  onFileSelected(event: any)
  {
    this.selectedBookCover = event.target.files[0];
    //console.log(this.selectedBookCover);

    if (this.selectedBookCover) //if selectedBookCover is not empty or not undefined
    {
      const reader : FileReader = new FileReader();
      reader.onload = () => {
        this.selectedPicture = reader.result as string;
      }
      reader.readAsDataURL(this.selectedBookCover);
    }
  }

  saveBook()
  {
    this.bookService.saveBook({
      body : this.bookRequest
    }).subscribe({
      next: (bookId : number) => {
        console.log('DEBUG');
        this.bookService.uploadBookCoverPicture({
          'book-id' : bookId,
          body : {
            file : this.selectedBookCover
          }
        }).subscribe({
          next : () => {
            this.router.navigate(['/books/my-books']);
          }
        })
      },
      error : (err) => {
        this.errorMessage = err.error.validationErrors;
      }
    });
  }
}
