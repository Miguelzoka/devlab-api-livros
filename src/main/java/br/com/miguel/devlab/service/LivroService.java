package br.com.miguel.devlab.service;

import br.com.miguel.devlab.entity.Livro;
import br.com.miguel.devlab.repository.LivroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivroService {

    private final LivroRepository repository;

    public LivroService(LivroRepository repository) {
        this.repository = repository;
    }

    public Livro salvar(Livro livro) {

        if (repository.existsByIsbn(livro.getIsbn())) {
            throw new RuntimeException("ISBN já cadastrado.");
        }

        return repository.save(livro);
    }

    public List<Livro> listar() {
        return repository.findAll();
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}