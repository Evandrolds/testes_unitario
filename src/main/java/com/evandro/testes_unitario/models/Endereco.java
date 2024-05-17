package com.evandro.testes_unitario.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "endereco")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String rua;

    @Column(nullable = false)
    private Integer numero;

    @Column(nullable = true, length = 30)
    private String complemento;

    @Column(nullable = false, length = 50)
    private String cidade;

    @Column(nullable = false, length = 2)
    private String estado;

    @Column(nullable = false, length = 8)
    private String cep;
}
