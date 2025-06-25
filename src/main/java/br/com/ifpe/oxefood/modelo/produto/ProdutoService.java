package br.com.ifpe.oxefood.modelo.produto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; 

import br.com.ifpe.oxefood.modelo.categoriaproduto.CategoriaProduto;
import br.com.ifpe.oxefood.modelo.categoriaproduto.CategoriaProdutoService;
import br.com.ifpe.oxefood.util.exception.ProdutoException;
import jakarta.persistence.EntityNotFoundException; 

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private CategoriaProdutoService categoriaProdutoService; // Injeta o serviço de CategoriaProduto

    @Transactional
    public Produto save(Produto produto, Long idCategoria) { // Agora recebe o idCategoria
        if (produto.getValorUnitario() == null || produto.getValorUnitario() < 10) {
            throw new ProdutoException(ProdutoException.MSG_VALOR_MINIMO_PRODUTO);
        }

        // *** BUSCA E ASSOCIA A CATEGORIA ***
        if (idCategoria != null) {
            CategoriaProduto categoria = categoriaProdutoService.obterPorID(idCategoria);
            if (categoria == null) {
                // Opcional: Lançar exceção se a categoria não for encontrada
                throw new EntityNotFoundException("Categoria com ID " + idCategoria + " não encontrada.");
            }
            produto.setCategoria(categoria);
        } else {
            // Se idCategoria for null, garanta que a categoria no produto também seja null
            produto.setCategoria(null);
        }

        produto.setHabilitado(Boolean.TRUE);
        produto.setVersao(1L); // Define a versão inicial
        produto.setDataCriacao(LocalDate.now()); // Define a data de criação
        produto.setDataUltimaModificacao(LocalDate.now()); // Define a data de modificação inicial

        return repository.save(produto);
    }

    public List<Produto> listarTodos() {
        return repository.findAll();
    }

    public Produto obterPorID(Long id) {
        // Use orElse(null) para não lançar exceção se não encontrar, retornando null
        return repository.findById(id).orElse(null);
    }

    @Transactional
    public void update(Long id, Produto produtoAlterado, Long idCategoria) { // Agora recebe o idCategoria
        Produto produto = repository.findById(id).orElse(null);

        if (produto == null) {
            throw new EntityNotFoundException("Produto com ID " + id + " não encontrado para atualização.");
        }

        if (produtoAlterado.getValorUnitario() == null || produtoAlterado.getValorUnitario() < 10) {
            throw new ProdutoException(ProdutoException.MSG_VALOR_MINIMO_PRODUTO);
        }

        // *** BUSCA E ASSOCIA A CATEGORIA NA ATUALIZAÇÃO ***
        if (idCategoria != null) {
            CategoriaProduto categoria = categoriaProdutoService.obterPorID(idCategoria);
            if (categoria == null) {
                throw new EntityNotFoundException("Categoria com ID " + idCategoria + " não encontrada para atualização do produto.");
            }
            produto.setCategoria(categoria);
        } else {
            // Se idCategoria for null, desassocia a categoria existente
            produto.setCategoria(null);
        }

        produto.setCodigo(produtoAlterado.getCodigo());
        produto.setTitulo(produtoAlterado.getTitulo());
        produto.setDescricao(produtoAlterado.getDescricao());
        produto.setValorUnitario(produtoAlterado.getValorUnitario());
        produto.setTempoEntregaMinimo(produtoAlterado.getTempoEntregaMinimo());
        produto.setTempoEntregaMaximo(produtoAlterado.getTempoEntregaMaximo());

        produto.setVersao(produto.getVersao() + 1); // Incrementa a versão
        produto.setDataUltimaModificacao(LocalDate.now()); // Atualiza a data de modificação

        repository.save(produto);
    }

    @Transactional
    public void delete(Long id) {
        Produto produto = repository.findById(id).orElse(null);

        if (produto == null) {
            throw new EntityNotFoundException("Produto com ID " + id + " não encontrado para exclusão.");
        }

        produto.setHabilitado(Boolean.FALSE);
        produto.setVersao(produto.getVersao() + 1); // Incrementa a versão
        produto.setDataUltimaModificacao(LocalDate.now()); // Atualiza a data de modificação
        repository.save(produto);
    }
}