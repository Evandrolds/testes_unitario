package com.evandro.testes_unitario.repositories;

import com.evandro.testes_unitario.models.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco,Long> {
}
