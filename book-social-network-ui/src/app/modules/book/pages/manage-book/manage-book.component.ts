import {Component, OnInit} from '@angular/core';
import {NgForOf, NgIf} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {BookRequest} from '../../../../services/models/book-request';
import {ActivatedRoute, Router, RouterLink} from '@angular/router';
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
export class ManageBookComponent implements OnInit {

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
    private router : Router,
    private activatedRoute : ActivatedRoute
  ) {
  }

  ngOnInit(): void {
    const bookId = this.activatedRoute.snapshot.params['bookId'];

    if (bookId)
    {
      this.bookService.findBookById({
        'book-id' : bookId
      }).subscribe({
        next : (book) => {
          this.bookRequest = {
            id : book.id,
            title : book.title as string,
            authorName : book.authorName as string,
            isbn : book.isbn as string,
            synopsis : book.synopsis as string,
            shareable : book.shareable
          };
          if (book.cover)
          {
            this.selectedPicture = 'data:image/jpeg;base64, ' + book.cover;
          }
        }
      })
    }
  }

  /**
   * Cette méthode fait que si l'on sélectionne un fichier image, l'image est automatique affichée après la sélection
   * @param event
   */
  onFileSelected(event: any)
  {
    this.selectedBookCover = event.target.files[0];

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
        // si la couverture est présente on la met à jour
        if (this.selectedBookCover) {
          this.bookService.uploadBookCoverPicture({
            'book-id': bookId,
            body: {
              file: this.selectedBookCover
            }
          }).subscribe({
            next: () => {
              this.router.navigate(['/books/my-books']);
            }
          })
        }

        // On redirige vers la page de livres de l'utilisateur
        this.router.navigate(['/books/my-books']);
      },
      error : (err) => {
        this.errorMessage = err.error.validationErrors;
      }
    });
  }
}
