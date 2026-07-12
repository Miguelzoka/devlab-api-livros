import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { Livro } from '../../models/livro';
import { LivroService } from '../../services/livro.service';

@Component({
  selector: 'app-livros',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './livros.html',
  styleUrl: './livros.css'
})
export class Livros implements OnInit {

  private livroService = inject(LivroService);

  livros: Livro[] = [];

  livro: Livro = {
    titulo: '',
    autor: '',
    isbn: ''
  };

  ngOnInit(): void {
    this.listar();
  }

  listar(): void {
    this.livroService.listar().subscribe(livros => {
      this.livros = livros;
    });
  }

  salvar(): void {
    this.livroService.salvar(this.livro).subscribe(() => {

      this.livro = {
        titulo: '',
        autor: '',
        isbn: ''
      };

      this.listar();
    });
  }

  excluir(id: number): void {
    this.livroService.excluir(id).subscribe(() => {
      this.listar();
    });
  }

}
