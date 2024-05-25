## Projeto de Gestão de Endereços com dois ambientes, um de teste com h2 database e outro com postgresql para produção, itilizando migration com postgres e flyway.

Este projeto é uma aplicação Spring Boot para gerenciar endereços. Ele oferece uma API REST para realizar operações CRUD (Criar, Ler, Atualizar, Deletar) em endereços armazenados em um banco de dados.
### Utilizando dois embientes, o h2 database para testes e postgresql para produção com migration e Flyway.

## Estrutura do Projeto

- **Entidade**: `Endereco`
- **Repositório**: `EnderecoRepository`
- **Serviço**: `EnderecoService`
- **Controlador**: `EnderecoController`
- **Banco de Dados**: Configuração para PostgreSQL e H2 (para testes)

## Entidade Endereco

A classe `Endereco` representa um endereço com os seguintes campos:

```java
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
```
## Serviços

A classe `EnderecoService` fornece métodos para armazenar, localizar, listar, atualizar e deletar endereços:

```java
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

```
## Controladores

A classe `EnderecoController` expõe a API REST para interação com a entidade Endereco:

```java
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
```
## Teste de Repositório do Endereço com Spring Boot

Esta classe de teste utiliza as anotações @DataJpaTest e @ActiveProfiles("test") para garantir que os testes sejam executados em um ambiente isolado, utilizando um banco de dados em memória (H2). O objetivo é validar as operações básicas de CRUD (Create, Read, Update, Delete) para a entidade Endereco.
## Configuração da Classe de Teste

A configuração @DataJpaTest é utilizada para testar componentes JPA. Ela configura apenas os componentes relacionados ao JPA, como os repositórios, e utiliza um banco de dados em memória por padrão. A anotação @ActiveProfiles("test") ativa o perfil test, que pode ser configurado para usar um banco de dados H2, garantindo que os testes sejam isolados do ambiente de produção.
``` java
@DataJpaTest
@ActiveProfiles("test")
public class EnderecoRepositoryTest {
    // Configuração e testes aqui
}
```

## Injeção de Dependência
``` java
@Autowired
private EnderecoRepository repository;
```
A injeção do repositório EnderecoRepository é feita através da anotação @Autowired, permitindo o uso dos métodos do repositório dentro dos testes.

## Teste de Salvamento de Endereço
O método testSalvarEndereco cria uma nova instância de Endereco, define seus atributos e a salva no banco de dados. Em seguida, verifica se o endereço foi salvo corretamente através de asserções.
``` java
@Test
public void testSalvarEndereco() {
    Endereco endereco = new Endereco();
    endereco.setRua("Rua A");
    endereco.setNumero(123);
    endereco.setComplemento("Apto 1");
    endereco.setCidade("Osasco");
    endereco.setEstado("SP");
    endereco.setCep("06053020");

    Endereco salvarEndereco = repository.save(endereco);

    assertThat(salvarEndereco.getId()).isNotNull();
    assertThat(salvarEndereco.getRua()).isEqualTo("Rua A");
    assertThat(salvarEndereco.getNumero()).isEqualTo(123);
    assertThat(salvarEndereco.getComplemento()).isEqualTo("Apto 1");
    assertThat(salvarEndereco.getCidade()).isEqualTo("Osasco");
    assertThat(salvarEndereco.getEstado()).isEqualTo("SP");
    assertThat(salvarEndereco.getCep()).isEqualTo("06053020");
}

```
## Teste de Localização de Endereço

O método testeLocalizarEndereco verifica a capacidade de localizar um endereço salvo no banco de dados. O endereço é salvo e, em seguida, buscado pelo seu ID. Asserções são usadas para garantir que o endereço encontrado corresponde ao salvo.
```java
@Test
public void testeLocalizarEndereco() {
    Endereco endereco = new Endereco();
    endereco.setRua("Rua A");
    endereco.setNumero(123);
    endereco.setComplemento("Apto 1");
    endereco.setCidade("Osasco");
    endereco.setEstado("SP");
    endereco.setCep("06053020");

    Endereco salvarEndereco = repository.save(endereco);

    Optional<Endereco> encontrarEndereco = repository.findById(salvarEndereco.getId());

    assertThat(encontrarEndereco).isPresent();
    assertThat(encontrarEndereco.get().getRua()).isEqualTo("Rua A");
}
```
## Teste de Deleção de Endereço

O método testeDeleteEndereco verifica a capacidade de deletar um endereço do banco de dados. Um endereço é salvo, deletado pelo seu ID e, em seguida, verificado se não está mais presente no banco de dados.
```java
@Test
public void testeDeleteEndereco() {
    Endereco endereco = new Endereco();
    endereco.setRua("Rua A");
    endereco.setNumero(123);
    endereco.setComplemento("Apto 1");
    endereco.setCidade("Osasco");
    endereco.setEstado("SP");
    endereco.setCep("06053020");

    Endereco salvarEndereco = repository.save(endereco);

    Long id = salvarEndereco.getId();
    repository.deleteById(id);
    Optional<Endereco> encontrarEndereco = repository.findById(id);

    assertThat(encontrarEndereco).isNotPresent();
}

```
## Conclusão

Essa configuração garante que os testes de repositório sejam executados em um ambiente controlado e isolado, validando as operações básicas de CRUD para a entidade Endereco. Utilizando um banco de dados em memória, os testes são rápidos e não afetam os dados de produção.
## Endpoints 
    GET /endereco/todos: Retorna todos os endereços.
    POST /endereco/salvar: Salva um novo endereço.
    GET /endereco/{id}: Retorna o endereço com o ID especificado.
    PATCH /endereco/atualizar/{id}: Atualiza o endereço com o ID especificado.
    DELETE /endereco/deletar/{id}: Deleta o endereço com o ID especificado.

Configuração do Banco de Dados
PostgreSQL

As seguintes propriedades devem ser configuradas no arquivo application.properties para conectar ao PostgreSQL:
spring.application.name=testes_unitario

## Configuração do banco de dados PostgreSQL
- **spring.datasource.url=jdbc:postgresql://localhost:5432/formulario**
- **spring.datasource.username=**
- **spring.datasource.password=**
- **spring.datasource.driver-class-name=org.postgresql.Driver**

## Configuração do Hibernate
- **spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect**
- **spring.jpa.hibernate.ddl-auto=none**

## Configuração do Flyway
- **flyway.schemas=teste**
- **flyway.default-schema=teste**
- **spring.flyway.enabled=true**
- **spring.flyway.baseline-on-migrate=true**
- **spring.flyway.locations=classpath:db/migration**
- **spring.flyway.validate-migration-naming=true**

## H2 (Para Testes)

Para configurar o H2, adicione as seguintes propriedades ao seu application.properties:

## H2 Database
- **spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE**
- **spring.datasource.driverClassName=org.h2.Driver**
- **spring.datasource.username=sa**
- **spring.datasource.password=**
- **spring.jpa.database-platform=org.hibernate.dialect.H2Dialect**

## H2 Console
- **spring.h2.console.enabled=true**
- **spring.jpa.hibernate.ddl-auto=update**
- **spring.h2.console.path=/h2-console**

## Executando o Projeto

    Clone o repositório.
    Configure as propriedades do banco de dados no application.properties ou application.yml.
    Execute o comando mvn spring-boot:run para iniciar a aplicação.

## Acessando o H2 Console

Quando usando o H2, você pode acessar o console H2 na URL: http://localhost:sua-porta/h2-console. Use as configurações padrão (jdbc:h2:mem:testdb, sa, sem senha) para conectar.
Testes

O projeto inclui testes unitários e de integração para garantir a funcionalidade da aplicação.
