package com.evandro.testes_unitario.testando_endereco;

import com.evandro.testes_unitario.models.Endereco;
import com.evandro.testes_unitario.repositories.EnderecoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class EnderecoRepositoryTest {

    /*
        Essa configuração garante que os testes sejam executados em um ambiente isolado,
        utilizando um banco de dados em memória (H2),
        e valida as operações básicas de CRUD (Create, Read, Update, Delete) para a entidade Endereco.
    */
    @Autowired
    private EnderecoRepository repository;

    @Test
    public void testSalvarEndereco(){
        Endereco endereco = new Endereco();

        endereco.setRua("Rua A");
        endereco.setNumero(123);
        endereco.setComplemento("Apto 1");
        endereco.setCidade("Osasco");
        endereco.setEstado("SP");
        endereco.setCep("06053020");

        Endereco salvarEndereco =  repository.save(endereco);

        assertThat(salvarEndereco.getId()).isNotNull();
        assertThat(salvarEndereco.getRua()).isNotNull().isEqualTo("Rua A");
        assertThat(salvarEndereco.getNumero()).isNotNull();
        assertThat(salvarEndereco.getComplemento()).isNotNull().isEqualTo("Apto 1");
        assertThat(salvarEndereco.getCidade()).isNotNull().isEqualTo("Osasco");
        assertThat(salvarEndereco.getEstado()).isNotNull().isEqualTo("SP");
        assertThat(salvarEndereco.getCep()).isNotNull().isEqualTo("06053020");

    }
    @Test
   public void testeLocalizarEndereco(){
        Endereco endereco = new Endereco();


        endereco.setRua("Rua A");
        endereco.setNumero(123);
        endereco.setComplemento("Apto 1");
        endereco.setCidade("Osasco");
        endereco.setEstado("SP");
        endereco.setCep("06053020");

        Endereco salvarEndereco =  repository.save(endereco);

        Optional<Endereco> encontrarEndereco = repository.findById(salvarEndereco.getId());
        assertThat(salvarEndereco.getId()).isNotNull();
        assertThat(salvarEndereco.getRua()).isNotNull().isEqualTo("Rua A");
        assertThat(salvarEndereco.getNumero()).isNotNull();
        assertThat(salvarEndereco.getComplemento()).isNotNull().isEqualTo("Apto 1");
        assertThat(salvarEndereco.getCidade()).isNotNull().isEqualTo("Osasco");
        assertThat(salvarEndereco.getEstado()).isNotNull().isEqualTo("SP");
        assertThat(salvarEndereco.getCep()).isNotNull().isEqualTo("06053020");
    }
    @Test
    public void testeDeleteEndereco(){
        Endereco endereco = new Endereco();

        endereco.setRua("Rua A");
        endereco.setNumero(123);
        endereco.setComplemento("Apto 1");
        endereco.setCidade("Osasco");
        endereco.setEstado("SP");
        endereco.setCep("06053020");

        Endereco salvarEndereco =  repository.save(endereco);

        Long id = salvarEndereco.getId();
        repository.deleteById(id);
        Optional<Endereco> encontrarEndereco = repository.findById(id);

        assertThat(encontrarEndereco).isNotPresent();
    }
}
