package com.evandro.testes_unitario.controllers;

import com.evandro.testes_unitario.models.Endereco;
import com.evandro.testes_unitario.services.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/endereco")
public class EnderecoControler {
    @Autowired
    private EnderecoService service;
    @GetMapping("/todos")
    public ResponseEntity<List<Endereco>> findAllEnderecos() throws FileNotFoundException {
        return new ResponseEntity<>(service.listarEnderecos(), HttpStatus.OK);
    }
    @PostMapping("/salvar")
    public ResponseEntity<String> salvarEndereco(@RequestBody Endereco endereco){
        return new ResponseEntity<>(service.armazenarEndereco(endereco),HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Endereco> findEnderecoPorId(@PathVariable("id") long id){
        return new ResponseEntity<>(service.localizarEndereco(id),HttpStatus.OK);
    }
    @PatchMapping("/atualizar/{id}")
    public ResponseEntity<String> atualizar(@RequestBody Endereco endereco,@PathVariable("id") long id){
        return new ResponseEntity<>(service.atualizarEndereco(endereco,id),HttpStatus.CREATED);
    }
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletar(@PathVariable("id") long id) {
        return new ResponseEntity<>(service.deletarEndereco(id),HttpStatus.OK);
    }
}
