package br.com.miguel.devlab.controller;

import br.com.miguel.devlab.entity.Livro;
import br.com.miguel.devlab.service.LivroService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livros")
@CrossOrigin(origins = "http://localhost:4200")
public class LivroController {

    private final LivroService service;

    public LivroController(LivroService service) {
        this.service = service;
    }

    @PostMapping
    public Livro salvar(@RequestBody Livro livro) {
        return service.salvar(livro);
    }

    @GetMapping
    public List<Livro> listar() {
        return service.listar();
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}