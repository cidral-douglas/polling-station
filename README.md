# Projeto Assembléia de Votação
Este projeto perimte que sejam criadas pautas para uma votação em assembléa entre os associados.

## Funcionalidades do Projeto
* Criar, editar, remover e recuperar um associado;
* Recuperar todos os associados cadastrados;
* Criar e recuperar uma pauta;
* Recuperar todas as Pautas cadastradas;
* Fazer um associado votar em uma pauta;
* Contar quantos votos tem em uma pauta;
* Recuperar o resultado da votação em uma pauta.

## Validações do Projeto
* Não podemos cadastrar associado com cpf inválido, nulo ou em branco;
* Não podemos cadastrar associado sem nome;
* Não podemos cadastras 2 associados com o mesmo cpf;
* Associado só pode votar 1 vez em uma mesma pauta. Após votado, voto não pode ser mudado;
* Associado só pode votar enquanto a pauta ainda está aberta;
* O resultado da votação não é mostrado parcialmente, dessa forma se a pauta ainda esta aberta, o resultado mostrado será "A votação ainda está aberta".

## Banco de dados
O banco de dados escolhido foi o PostgreSQL.

### Configuração
* Baixar localmente o postgreSQL pelo link: https://www.postgresql.org/download
* Configurar o banco de dados polling-station na porta 5432, usuário postgres e senha postgres.

### Diagramas
#### Associado
![image](https://user-images.githubusercontent.com/70900973/204954284-0e10238c-d1fc-41f3-92d0-f29f37bc5377.png)

#### Pauta
![image](https://user-images.githubusercontent.com/70900973/204954323-4e35943d-b06d-4b23-8c27-bac21d0de491.png)

#### Votacao
![image](https://user-images.githubusercontent.com/70900973/204954404-86ee0ec2-7d88-4974-88b2-840c19da767b.png)


## Configuração Kafka
* Baixar Kafka pelo link: https://kafka.apache.org/downloads
* Versão utilizada: 2.7.2 Binary Scala 2.13;
* Rodar zookeeper: `C:\kafka\kafka_2.13-2.7.2\bin\windows>zookeeper-server-start.bat ../../config/zookeeper.properties`;
* Rodar kafka : `C:\kafka\kafka_2.13-2.7.2\bin\windows>kafka-server-start.bat ../../config/server.properties`;
* Criar Topic: `C:\kafka\kafka_2.13-2.7.2\bin\windows>kafka-topics.bat --bootstrap-server localhost:9092 --topic pautas --create --partitions 1 `.

## Swagger
Todos os endpoints foram disponibilizados via Swagger no link: http://localhost:8080/swagger-ui.html

![image](https://user-images.githubusercontent.com/70900973/204953658-6b7a87fb-2ce2-4ea4-b926-52a9016d94b9.png)

## Arquitetura do Projeto
O projeto foi criado utilizando Java 11 com Spring Boot. A arquitetura escolhida foi uma mistura do uso de DDD, Clean Architecture, Ports and Adapters e Builder Pattern.

![image](https://user-images.githubusercontent.com/70900973/204956796-ef5b20bb-8249-4bde-b651-8931e2ae9189.png)
