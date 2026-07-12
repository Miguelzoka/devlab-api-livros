import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Livro } from '../models/livro';

@Injectable({
  providedIn: 'root'
})
export class LivroService {

  private http = inject(HttpClient);

  private readonly API = 'http://localhost:8080/livros';

  listar(): Observable<Livro[]> {
    return this.http.get<Livro[]>(this.API);
  }

  salvar(livro: Livro): Observable<Livro> {
    return this.http.post<Livro>(this.API, livro);
  }

  excluir(id: number): Observable<void> {
    return this.http.delete<void>(`${this.API}/${id}`);
  }
}
