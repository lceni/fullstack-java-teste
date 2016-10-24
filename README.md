# lceni@fullstack-java-teste

Aplicação single-web-page e RESTful webservice que utiliza as seguintes tecnologias:

**Cliente:**
* Angular-1.5.8
* Bootstrap-3.3.7
* JQuery-3.1.1

**Servidor:**
* Maven
* Jetty
* Jersey (com MOXy e JAXB)
* Rich Domain Model & Persistent Domain Objects
* JUnit
* MongoDB

### Para facilitar a execução em uma máquina de desenvolvimento ou testes, é necessário:

* Node.js
* Maven
* Git
* Bower (npm install bower -g)
* http-server (npm install http-server -g)

### Instruções para execução:

Faça uma cópia local deste repositório, em seguida, execute os passos abaixo para cada um dos projetos, a partir do diretório fullstack-java-teste:

**Cliente:**

     cd cliente
     bower install
     http-server -a localhost -p 8000 -c-1 -o

**Servidor:**

     cd servidor
     mvn exec:java -Dexec.mainClass="br.com.contabilizei.lceni.servidor.App"
