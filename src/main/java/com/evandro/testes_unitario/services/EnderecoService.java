package com.evandro.testes_unitario.services;

import com.evandro.testes_unitario.models.Endereco;
import com.evandro.testes_unitario.repositories.EnderecoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {
    @Autowired
    private EnderecoRepository repository;

    public String armazenarEndereco(Endereco endereco) {
        repository.save(endereco);
        return "Endereço armazenado com sucesso";
    }

    public Endereco localizarEndereco(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Não foi possível encontrar endereço!"));
    }

    public List<Endereco> listarEnderecos() throws FileNotFoundException {
        List<Endereco> enderecos = repository.findAll();
        if (enderecos.isEmpty())
            throw new FileNotFoundException("Lista de endereço vazia");

        return enderecos;
    }
    public String atualizarEndereco(Endereco endereco, long id){
        Endereco ende = repository.findById(id).get();
        BeanUtils.copyProperties(endereco, ende);
        repository.save(ende);
        return "Endereço atualizado com sucesso!";
    }
    public String deletarEndereco(Long id) {
        repository.deleteById(id);
        return "Cadastro deletado com sucesso!";
    }
}
