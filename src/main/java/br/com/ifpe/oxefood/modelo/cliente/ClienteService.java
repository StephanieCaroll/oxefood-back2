package br.com.ifpe.oxefood.modelo.cliente;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service //Faz a classe ser um serviço
public class ClienteService {

    @Autowired //Instanciar um objeto de forma que eu possar usar os metodos e atributos dessa classe
   private ClienteRepository repository;

   @Transactional //Se um falhar as outras seram desfeitas e não seram gravadas no banco 
   public Cliente save(Cliente cliente) { //Recebe o objeto cliente que ele vai salvar no banco

       cliente.setHabilitado(Boolean.TRUE);
       return repository.save(cliente);
       
       //Ou voce pode fazer
       //Cliente c = repository.save(cliente);
       //return c;
   }
   
   public List<Cliente> listarTodos() {
  
    return repository.findAll();
}

public Cliente obterPorID(Long id) {

    return repository.findById(id).get();
}

@Transactional
public void update(Long id, Cliente clienteAlterado) {

   Cliente cliente = repository.findById(id).get(); //Consultar no banco o cliente
   cliente.setNome(clienteAlterado.getNome());
   cliente.setDataNascimento(clienteAlterado.getDataNascimento());
   cliente.setCpf(clienteAlterado.getCpf());
   cliente.setFoneCelular(clienteAlterado.getFoneCelular());
   cliente.setFoneFixo(clienteAlterado.getFoneFixo());
     
   repository.save(cliente); //Função para cadastra e alterar objeto
}

@Transactional //Toda vez que for mexer no banco utilizar o transactional
public void delete(Long id) {

    Cliente cliente = repository.findById(id).get();
    cliente.setHabilitado(Boolean.FALSE);

    repository.save(cliente); //Na verdade está sendo alterado se realmente fosse deletado em vez de save seria o delete
}


}
