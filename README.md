# pos-fiap-tech-challenge
Projeto desenvolvido ao longo do curso "Arquitetura e Desenvolvimento em Java" de pós graduação da FIAP.

# Objetivo
API para gerenciamento de restaurantes. Containerização do projeto via Docker e Docker Compose. Persistência de dados via MySQL.

# Documentação interativa
Swagger disponível através do link: http://localhost:8080/tech-challenge/swagger-ui/index.html#/user-controller-v-1/home

# Collection do Postman para testes
Disponível através do arquivo: tech-challenge.postman_collection.json

# Execução
- clonar repositório do projeto
- criar um diretório de nome "secrets" no root do projeto
- criar dentro dessa pasta secrets os arquivos:
    - db_name.txt -> incluir no arquivo o nome desejado para o banco de dados
    - db_password.txt -> incluir no arquivo a senha desejada para o banco de dados
    - db_root_password.txt -> incluir no arquivo a senha desejada para o usuário root do banco de dados
    - db_url.txt -> incluir no arquivo a URL "jdbc:mysql://mysql:3306/XXX?createDatabaseIfNotExist=true" onde XXX é o nome escolhido no arquivo db_name.txt
    - db_user.txt -> incluir no arquivo o usuário desejado para o banco de dados
- criar dentro da pasta src/main/resources/keys os arquivos:
  - app.key -> incluir no arquivo a chave privada para gerenciamento de tokens JWT
  - app.pub -> incluir no arquivo a chave pública para gerenciamento de tokens JWT
- acessar o diretório do projeto clonado via CMD
- executar o comando “docker compose up -d” via CMD

Obs.: Para executar a aplicação via IDE é necessário descomentar as propriedades spring.datasource.url, spring.datasource.username e spring.datasource.password no arquivo application.properties.

Ao finalizar o passo a passo a aplicação estará disponível na porta 8080 do localhost (http://localhost:8080/tech-challenge) e seu banco de dados estará disponível na porta 3306 do localhost.
