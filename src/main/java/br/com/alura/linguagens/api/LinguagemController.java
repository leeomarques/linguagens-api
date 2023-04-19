package br.com.alura.linguagens.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class LinguagemController {

    @Autowired
    private LinguagemRepository repository;

    @GetMapping("/linguagens")
    public List<Linguagem> obterLinguagens() {
        List<Linguagem> linguagens = repository.findByOrderByRanking();
        return linguagens;
    }

    @GetMapping("/linguagens/{id}") //Busca o que esta na URL
    public Linguagem obterLinguagemPorId(@PathVariable String id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/linguagens")
    public ResponseEntity<Linguagem> cadastrarLinguagem(@RequestBody Linguagem linguagem) {
        Linguagem linguagemSalva = repository.save(linguagem);
        return new ResponseEntity<>(linguagemSalva, HttpStatus.CREATED);
    }

    @PutMapping("/linguagens/{id}") //Busca o que esta no corpo @RequestBody
    public Linguagem atualizarLinguagem(@PathVariable String id, @RequestBody Linguagem linguagem) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        linguagem.setId(id);
        Linguagem linguagemSalva = repository.save(linguagem);
        return linguagemSalva;
    }

    @DeleteMapping("/linguagens/{id}")
    public void excluirLinguagem(@PathVariable String id) {
        repository.deleteById(id);
    }

}
