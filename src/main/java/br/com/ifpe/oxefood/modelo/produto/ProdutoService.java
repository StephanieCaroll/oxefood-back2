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
    private CategoriaProdutoService categoriaProdutoService;

    @Transactional
    public Produto save(Produto produto, Long idCategoria) {
        if (produto.getValorUnitario() == null || produto.getValorUnitario() < 10) {
            throw new ProdutoException(ProdutoException.MSG_VALOR_MINIMO_PRODUTO);
        }

        if (idCategoria != null) {
            CategoriaProduto categoria = categoriaProdutoService.obterPorID(idCategoria);
            if (categoria == null) {

                throw new EntityNotFoundException("Categoria com ID " + idCategoria + " não encontrada.");
            }
            produto.setCategoria(categoria);
        } else {

            produto.setCategoria(null);
        }

        produto.setHabilitado(Boolean.TRUE);
        produto.setVersao(1L);
        produto.setDataCriacao(LocalDate.now());
        produto.setDataUltimaModificacao(LocalDate.now());

        return repository.save(produto);
    }

    public List<Produto> listarTodos() {
        return repository.findAll();
    }

    public Produto obterPorID(Long id) {

        return repository.findById(id).orElse(null);
    }

    @Transactional
    public void update(Long id, Produto produtoAlterado, Long idCategoria) {
        Produto produto = repository.findById(id).orElse(null);

        if (produto == null) {
            throw new EntityNotFoundException("Produto com ID " + id + " não encontrado para atualização.");
        }

        if (produtoAlterado.getValorUnitario() == null || produtoAlterado.getValorUnitario() < 10) {
            throw new ProdutoException(ProdutoException.MSG_VALOR_MINIMO_PRODUTO);
        }

        if (idCategoria != null) {
            CategoriaProduto categoria = categoriaProdutoService.obterPorID(idCategoria);
            if (categoria == null) {
                throw new EntityNotFoundException(
                        "Categoria com ID " + idCategoria + " não encontrada para atualização do produto.");
            }
            produto.setCategoria(categoria);
        } else {

            produto.setCategoria(null);
        }

        produto.setCodigo(produtoAlterado.getCodigo());
        produto.setTitulo(produtoAlterado.getTitulo());
        produto.setDescricao(produtoAlterado.getDescricao());
        produto.setValorUnitario(produtoAlterado.getValorUnitario());
        produto.setTempoEntregaMinimo(produtoAlterado.getTempoEntregaMinimo());
        produto.setTempoEntregaMaximo(produtoAlterado.getTempoEntregaMaximo());

        produto.setVersao(produto.getVersao() + 1);
        produto.setDataUltimaModificacao(LocalDate.now());

        repository.save(produto);
    }

    @Transactional
    public void delete(Long id) {
        Produto produto = repository.findById(id).orElse(null);

        if (produto == null) {
            throw new EntityNotFoundException("Produto com ID " + id + " não encontrado para exclusão.");
        }

        produto.setHabilitado(Boolean.FALSE);
        produto.setVersao(produto.getVersao() + 1);
        produto.setDataUltimaModificacao(LocalDate.now());
        repository.save(produto);
    }

    public List<Produto> filtrar(String codigo, String titulo, Long idCategoria) {

        List<Produto> listaProdutos = repository.findAll();

        if ((codigo != null && !"".equals(codigo)) &&
                (titulo == null || "".equals(titulo)) &&
                (idCategoria == null)) {
            listaProdutos = repository.consultarPorCodigo(codigo);

        } else if ((codigo == null || "".equals(codigo)) &&
                (titulo != null && !"".equals(titulo)) &&
                (idCategoria == null)) {
            listaProdutos = repository.findByTituloContainingIgnoreCaseOrderByTituloAsc(titulo);

        } else if ((codigo == null || "".equals(codigo)) &&
                (titulo == null || "".equals(titulo)) &&
                (idCategoria != null)) {
            listaProdutos = repository.consultarPorCategoria(idCategoria);
            
        } else if ((codigo == null || "".equals(codigo)) &&
                (titulo != null && !"".equals(titulo)) &&
                (idCategoria != null)) {
            listaProdutos = repository.consultarPorTituloECategoria(titulo, idCategoria);
        }

        return listaProdutos;
    }

}