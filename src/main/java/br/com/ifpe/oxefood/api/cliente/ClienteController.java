package br.com.ifpe.oxefood.api.cliente;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifpe.oxefood.modelo.acesso.UsuarioService;
import br.com.ifpe.oxefood.modelo.cliente.Cliente;
import br.com.ifpe.oxefood.modelo.cliente.ClienteService;
import br.com.ifpe.oxefood.modelo.cliente.EnderecoCliente;
import br.com.ifpe.oxefood.modelo.cliente.EnderecoClienteRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController // Faz a classe ser um controller
@RequestMapping("/api/cliente")
@CrossOrigin // Utilizada para o controller receber requisições do React
public class ClienteController {

    @Autowired // Instanciar no cliente service
    private ClienteService clienteService;

    @Autowired // Adicione a injeção do EnderecoClienteRepository
    private EnderecoClienteRepository enderecoClienteRepository;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping // Especificar que essa função vai receber requisições do tipo Post
    public ResponseEntity<Cliente> save(@RequestBody @Valid ClienteRequest clienterequest, HttpServletRequest request ) {
        Cliente cliente = clienteService.save(request.build(), usuarioService.obterUsuarioLogado(request));
        return new ResponseEntity<Cliente>(cliente, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Cliente> listarTodos() {
        return clienteService.listarTodos();
    }

    @GetMapping("/{clienteId}/endereco")
    public ResponseEntity<List<EnderecoCliente>> listarEnderecosDoCliente(@PathVariable Long clienteId) {
        Cliente cliente = clienteService.obterPorID(clienteId);

        if (cliente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Retorna erro 404 se o cliente não for
                                                                        // encontrado
        }
        // Filtra apenas endereços habilitados, se aplicável, ou retorne todos
        // Exemplo:
        // cliente.getEnderecos().stream().filter(EnderecoCliente::getHabilitado).collect(Collectors.toList());
        return ResponseEntity.ok(cliente.getEnderecos()); // Retorna os endereços do cliente
    }

    // NOVO ENDPOINT: Busca um endereço específico pelo ID do endereço
    @GetMapping("/endereco/{id}")
    public ResponseEntity<EnderecoCliente> obterEnderecoPorID(@PathVariable Long id) {
        Optional<EnderecoCliente> enderecoOptional = enderecoClienteRepository.findById(id);
        if (enderecoOptional.isPresent()) {
            return new ResponseEntity<>(enderecoOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public Cliente obterPorID(@PathVariable Long id) {
        return clienteService.obterPorID(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> update(@PathVariable("id") Long id, @RequestBody ClienteRequest clienterequest, HttpServletRequest request) { 
        clienteService.update(id, request.build(),usuarioService.obterUsuarioLogado(request)); 
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}") // passar o id do cliente que eu quero remover
    public ResponseEntity<Void> delete(@PathVariable Long id) { // repassar o id para a função delete
        clienteService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/endereco/{clienteId}")
    public ResponseEntity<EnderecoCliente> adicionarEnderecoCliente(@PathVariable("clienteId") Long clienteId,
            @RequestBody @Valid EnderecoClienteRequest request) {
        EnderecoCliente endereco = clienteService.adicionarEnderecoCliente(clienteId, request.build());
        return new ResponseEntity<EnderecoCliente>(endereco, HttpStatus.CREATED);
    }

    @PutMapping("/endereco/{enderecoId}")
    public ResponseEntity<EnderecoCliente> atualizarEnderecoCliente(@PathVariable("enderecoId") Long enderecoId,
            @RequestBody EnderecoClienteRequest request) {
        EnderecoCliente endereco = clienteService.atualizarEnderecoCliente(enderecoId, request.build());
        return new ResponseEntity<EnderecoCliente>(endereco, HttpStatus.OK);
    }

    @DeleteMapping("/endereco/{enderecoId}")
    public ResponseEntity<Void> removerEnderecoCliente(@PathVariable("enderecoId") Long enderecoId) {
        clienteService.removerEnderecoCliente(enderecoId);
        return ResponseEntity.noContent().build();
    }
}