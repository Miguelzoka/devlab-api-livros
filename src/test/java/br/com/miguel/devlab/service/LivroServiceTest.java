package br.com.miguel.devlab.service;

import br.com.miguel.devlab.entity.Livro;
import br.com.miguel.devlab.repository.LivroRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LivroServiceTest {

    @Mock
    private LivroRepository repository;

    @InjectMocks
    private LivroService service;

    @Test
    @DisplayName("Deve salvar um livro com sucesso")
    void deveSalvarLivro() {

        Livro livro = new Livro();
        livro.setTitulo("Misery");
        livro.setAutor("Stephen King");
        livro.setIsbn("9788581052144");

        Livro livroSalvo = new Livro();
        livroSalvo.setId(1L);
        livroSalvo.setTitulo("Misery");
        livroSalvo.setAutor("Stephen King");
        livroSalvo.setIsbn("9788581052144");

        when(repository.existsByIsbn(livro.getIsbn()))
                .thenReturn(false);

        when(repository.save(livro))
                .thenReturn(livroSalvo);

        Livro resultado = service.salvar(livro);

        assertEquals(1L, resultado.getId());
        assertEquals("Misery", resultado.getTitulo());

        verify(repository).existsByIsbn(livro.getIsbn());
        verify(repository).save(livro);
    }

    @Test
    @DisplayName("Deve listar todos os livros")
    void deveListarTodosOsLivros() {

        Livro livro1 = new Livro();
        livro1.setId(1L);
        livro1.setTitulo("Clean Code");
        livro1.setAutor("Robert Martin");
        livro1.setIsbn("123456");

        Livro livro2 = new Livro();
        livro2.setId(2L);
        livro2.setTitulo("Effective Java");
        livro2.setAutor("Joshua Bloch");
        livro2.setIsbn("654321");

        List<Livro> livros = List.of(livro1, livro2);

        when(repository.findAll()).thenReturn(livros);

        List<Livro> resultado = service.listar();

        assertEquals(2, resultado.size());
        assertEquals("Clean Code", resultado.get(0).getTitulo());

        verify(repository).findAll();
    }

    @Test
    @DisplayName("Deve excluir um livro pelo id")
    void deveExcluirLivro() {

        Long id = 1L;

        service.excluir(id);

        verify(repository).deleteById(id);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o ISBN já estiver cadastrado")
    void deveLancarExcecaoQuandoIsbnJaExistir() {

        Livro livro = new Livro();
        livro.setTitulo("Clean Code");
        livro.setAutor("Robert Martin");
        livro.setIsbn("123456");

        when(repository.existsByIsbn(livro.getIsbn()))
                .thenReturn(true);

        RuntimeException excecao = assertThrows(
                RuntimeException.class,
                () -> service.salvar(livro)
        );

        assertEquals("ISBN já cadastrado.", excecao.getMessage());

        verify(repository).existsByIsbn(livro.getIsbn());
        verify(repository, never()).save(livro);
    }
}