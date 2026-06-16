package br.com.agenda.controller;

import br.com.agenda.exception.CampoVazioException;
import br.com.agenda.exception.ContatoNaoEncontradoException;
import br.com.agenda.model.Contato;
import br.com.agenda.service.AgendaTelefonica;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/contatos")
@CrossOrigin(origins = "*")
public class ContatoController {
    private final AgendaTelefonica agendaTelefonica;

    public ContatoController(AgendaTelefonica agendaTelefonica) {
        this.agendaTelefonica = agendaTelefonica;
    }

    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(agendaTelefonica.listarContatos());
    }

    @GetMapping("/buscar")
    public ResponseEntity<?> buscar(@RequestParam String nome) {
        return ResponseEntity.ok(agendaTelefonica.buscarContato(nome));
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Contato contato) {
        agendaTelefonica.adicionarContato(contato);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "mensagem", "Contato cadastrado com sucesso.",
                "contato", contato
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Integer id, @RequestBody Contato contato) {
        agendaTelefonica.atualizarContato(id, contato);
        return ResponseEntity.ok(Map.of("mensagem", "Contato atualizado com sucesso."));
    }

    @DeleteMapping("/{nome}")
    public ResponseEntity<?> remover(@PathVariable String nome) {
        agendaTelefonica.removerContato(nome);
        return ResponseEntity.ok(Map.of("mensagem", "Contato removido com sucesso."));
    }

    @ExceptionHandler({CampoVazioException.class, ContatoNaoEncontradoException.class})
    public ResponseEntity<?> tratarErroSimples(RuntimeException e) {
        return ResponseEntity.badRequest().body(Map.of("mensagem", e.getMessage()));
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<?> tratarErroBanco() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("mensagem", "Erro ao acessar o banco de dados."));
    }
}
