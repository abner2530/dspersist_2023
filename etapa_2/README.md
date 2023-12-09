exemplo-dao-spring-jpa-com_associacoes-ui-completa
==================================================

Exemplo de aplicação JPA com Spring Boot e Spring Data JPA.

A aplicação possui menus e sub-menus para cadastro de clientes, produtos, compras e itens de compras.

```mermaid
classDiagram
    Adocao "1" *-- "*" ItemAdocao
    Animal "1" -- "&nbsp;*" ItemAdocao
    Cliente "1" -- "*" Adocao

class Cliente {
    -id: Integer
    -cpf: String
    -nome: String
    -fone: String
}

class Adocao {
    -id: Integer
    -dataHora: LocalDateTime

}

class ItemAdocao {
    -id: Integer
    -adocao: adocao
    -animal: animal
}

class Animal {
    -id: Integer
    -nome: String
    -tipo: String
    -sexo: String
}
```