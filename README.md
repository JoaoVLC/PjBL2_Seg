# Projeto Pratico: Servidor HTTPS Seguro

Disciplina: Seguranca da Informacao  
Tema: servidor web simples em Java usando HTTPS com certificado digital autoassinado.

## Arquivos do projeto

- `ServidorHTTPS.java`: codigo do servidor HTTPS em Java.
- `index.html`: pagina exibida ao acessar `https://localhost:8443`.
- `keystore.jks`: certificado digital autoassinado, gerado com `keytool`.
- `README.md`: instrucoes, respostas teoricas, lista de prints e roteiro do video.

## O que o projeto faz

Este projeto cria um servidor HTTPS simples usando Java. O servidor roda na porta `8443`, carrega o certificado salvo no arquivo `keystore.jks` e mostra a pagina `index.html` no navegador.

Ao acessar:

```bash
https://localhost:8443
```

o navegador deve abrir a pagina do projeto mostrando que a conexao HTTPS esta funcionando.

## Dados do certificado

O certificado usado no projeto deve ter estes dados:

- Alias: `meuservidor`
- Algoritmo: `RSA`
- Tamanho da chave: `2048`
- Validade: `365 dias`
- CN: `localhost`
- Senha: `senha123`
- Arquivo: `keystore.jks`

## Como gerar o keystore.jks

Entre na pasta do projeto:

```bash
cd meu-projeto
```

Verifique se o Java esta instalado:

```bash
java -version
```

Gere o certificado com o comando:

```bash
keytool -genkeypair -alias meuservidor -keyalg RSA -keysize 2048 -validity 365 -keystore keystore.jks -dname "CN=localhost, O=MinhaFaculdade, C=BR" -storepass senha123 -keypass senha123
```

Se o arquivo `keystore.jks` ja existir e voce quiser gerar de novo para tirar print, apague ou renomeie o arquivo antigo antes de rodar o comando.

Depois liste o certificado:

```bash
keytool -list -v -keystore keystore.jks -storepass senha123
```

Observacao para Windows: se aparecer que `keytool` nao foi reconhecido, use o caminho completo do JDK. Exemplo:

```bash
"C:\Program Files\Java\jdk-21\bin\keytool.exe" -genkeypair -alias meuservidor -keyalg RSA -keysize 2048 -validity 365 -keystore keystore.jks -dname "CN=localhost, O=MinhaFaculdade, C=BR" -storepass senha123 -keypass senha123
```

## Como compilar e executar

Na mesma pasta onde estao `ServidorHTTPS.java`, `index.html` e `keystore.jks`, compile:

```bash
javac ServidorHTTPS.java
```

Execute:

```bash
java ServidorHTTPS
```

Se tudo estiver certo, o terminal vai mostrar:

```text
Servidor HTTPS rodando!
Acesse: https://localhost:8443
```

Depois abra o navegador e acesse:

```bash
https://localhost:8443
```

Como o certificado e autoassinado, o navegador deve mostrar um aviso de seguranca. Isso e normal neste projeto, porque o certificado foi criado por nos e nao por uma Autoridade Certificadora reconhecida. Para testar em `localhost`, clique em avancado e aceite continuar.

## Correcoes feitas no codigo-base

O codigo do PDF tinha alguns caracteres estranhos e espacos especiais por causa da formatacao do arquivo. Neste projeto, estes valores foram corrigidos:

- `"index.html"` ficou sem espacos invisiveis.
- `"keystore.jks"` ficou sem espacos invisiveis.
- `"senha123"` ficou sem espacos invisiveis.
- `"Content-Type"` ficou escrito corretamente.
- A leitura dos arquivos usa UTF-8.
- O codigo fecha os arquivos e a resposta HTTP corretamente.

## Respostas teoricas

### O que significa CN=localhost?

`CN` significa `Common Name`, ou nome comum do certificado. Quando colocamos `CN=localhost`, estamos dizendo que o certificado foi feito para o endereco `localhost`, que e o proprio computador onde o servidor esta rodando.

### Por que a senha do certificado e importante?

A senha protege o `keystore.jks` e a chave privada do servidor. Se outra pessoa tiver acesso a chave privada sem protecao, ela poderia tentar se passar pelo servidor. Por isso a senha ajuda a manter o certificado seguro.

### Por que aparece aviso de seguranca no navegador?

O aviso aparece porque o certificado e autoassinado. Isso quer dizer que ele foi criado por nos mesmos, e nao por uma Autoridade Certificadora confiavel, como acontece em sites reais. Para o teste em `localhost`, esse aviso e esperado.

### Por que HTTPS e importante?

HTTPS e importante porque criptografa a comunicacao entre o navegador e o servidor. Assim, dados como senhas, informacoes pessoais e mensagens nao ficam expostos facilmente. Ele tambem ajuda a garantir que a comunicacao nao seja alterada no caminho.

## Lista de prints para tirar

1. Terminal mostrando a versao do Java com `java -version`.
2. Terminal mostrando o certificado sendo gerado com `keytool`.
3. Terminal mostrando o certificado listado com `keytool -list -v`.
4. Terminal mostrando o servidor rodando.
5. Aviso de seguranca do navegador.
6. Pagina funcionando em `https://localhost:8443`.
7. Informacoes do certificado no navegador.

## Roteiro curto para video de 3 a 5 minutos

### Parte 1 - Joao explica a geracao do certificado

Joao mostra o terminal, roda ou explica o comando `keytool` e fala que ele cria um certificado digital autoassinado. Ele destaca o alias `meuservidor`, o algoritmo RSA, a chave de 2048 bits, a validade de 365 dias e o `CN=localhost`.

### Parte 2 - Barbara explica a execucao do servidor

Barbara mostra os arquivos `ServidorHTTPS.java`, `index.html` e `keystore.jks`. Depois compila com `javac ServidorHTTPS.java` e executa com `java ServidorHTTPS`, mostrando no terminal que o servidor esta rodando na porta 8443.

### Parte 3 - Joao ou Barbara mostra o navegador

Um dos dois acessa `https://localhost:8443`, mostra o aviso de seguranca e explica que ele aparece porque o certificado e autoassinado. Depois aceita o risco em `localhost` e mostra a pagina HTTPS funcionando.

### Parte 4 - Os dois explicam o que aprenderam

Joao pode falar que aprendeu como gerar um certificado e como o Java carrega o `keystore.jks`. Barbara pode falar que aprendeu por que HTTPS protege a comunicacao e por que certificados sao importantes para sites seguros.

## Comandos finais pedidos

Verificar a versao do Java:

```bash
java -version
```

Gerar o certificado:

```bash
keytool -genkeypair -alias meuservidor -keyalg RSA -keysize 2048 -validity 365 -keystore keystore.jks -dname "CN=localhost, O=MinhaFaculdade, C=BR" -storepass senha123 -keypass senha123
```

Listar o certificado:

```bash
keytool -list -v -keystore keystore.jks -storepass senha123
```

Compilar e executar:

```bash
javac ServidorHTTPS.java
java ServidorHTTPS
```
