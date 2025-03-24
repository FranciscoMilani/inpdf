# InPDF – Extrator de Dados de PDFs

Uma ferramenta para Windows que lê e interpreta documentos PDF a partir de uma configuração personalizada, extraindo os dados para arquivos formatados em JSON.
<p style="text-align: center;">
  <img src="https://github.com/user-attachments/assets/1233771c-2a48-47f7-bc9e-d9a76e36f4d1" width="700" />
</p>

## Sobre

O InPDF (Interpretador de arquivos Portable Document Format) foi desenvolvido para solucionar problemas de empresas na região da Serra Gaúcha, automatizando a extração de informações de boletos bancários e declarações de imposto de renda (DIRPF 2022) para o formato JSON. O sistema possibilita que o usuário configure e persista os pontos de extração dos dados (por exemplo, indicando as linhas onde as informações se encontram), garantindo flexibilidade para diferentes modelos de documentos.

O projeto passou por um rigoroso processo de planejamento e modelagem de software. Foram levantados os requisitos (negócio, funcionais e não-funcionais), casos de uso e suas realizações no modelo de Craig Larman, "_sitemap_" e _wireframes_ visuais de interface
de usuário. Além disso foi esboçada a arquitetura do software em alto nível e feita a diagramação de classes UML. A solução foi construída em Java (JDK 17) com a utilização de bibliotecas como PDFBox (para manipulação de PDFs), Gson (para serialização/deserialização de JSON), Apache Commons IO (para gerenciamento de arquivos), Swing (para a interface gráfica), Log4j (para logs) e Maven (para gerenciamento de dependências).

Este projeto foi desenvolvido por [alunos](#autores) da disciplina de Projeto Temático I (2022/4), da [Universidade de Caxias do Sul (UCS)](https://www.ucs.br/site) .

## Começando

### Dependências

- **Java JDK 17** – Versão LTS da Oracle Corporation
- **PDFBox** – Biblioteca para manipulação de arquivos PDF ([PDFBox Website](https://pdfbox.apache.org/))
- **Gson** – Biblioteca para serialização/deserialização de objetos JSON ([Gson GitHub](https://github.com/google/gson))
- **Apache Commons IO** – Utilitário para operações de arquivos ([Commons IO on Maven](https://mvnrepository.com/artifact/commons-io/commonsio))
- **Swing** – API para construção de interfaces gráficas no Java
- **Log4j** – Biblioteca para geração de logs ([Log4j Website](https://logging.apache.org/log4j/2.x/))
- **Maven** – Para gerenciamento de dependências e build do projeto ([Maven Website](https://maven.apache.org/))

### Instalando

1. **Download do Código-Fonte**  
   Clone ou baixe o repositório do projeto.

2. **Configuração do Ambiente**  
   Certifique-se de ter o Java JDK 17 e Maven instalados.

4. **Build do Projeto**  
   No diretório do projeto, execute:
```
mvn clean install
```

### Executando o Programa

1. Navegue até o diretório onde os arquivos executáveis foram gerados.
2. Execute o arquivo principal (por exemplo, `InPDF.exe` para Windows ou via comando:
```
java -jar InPDF.jar
```
3. Configure o programa conforme a necessidade:
- **Tela Principal:** Escolha entre acessar o configurador de documentos ou as opções do programa.
- **Configurador do Programa:** Defina os diretórios de entrada, saída, arquivos processados e rejeitados, além de configurar o intervalo de verificação.
- **Configurador de Documentos:** O InPDF oferece duas telas de configuração, por onde os dados de extração serão salvos para serem reaplicados com facilidade:
  - **Configurador de Boletos:** Nesta tela, o usuário pode selecionar um PDF de boleto bancário e configurar manualmente as posições dos campos (através de números de linha) que serão extraídos. Essa configuração simplifica a extração dos dados, reduzindo ajustes futuros.
  - **Configurador de Declaração de Imposto de Renda:** Para documentos de imposto de renda, a interface é adaptada para lidar com a complexidade dos dados distribuídos em múltiplas páginas. Além da seleção das linhas correspondentes, a interface permite a paginação do conteúdo e a configuração de seções repetitivas.

## Demonstração de Telas

### Tela Principal

A tela inicial permite ao usuário:
- Iniciar ou interromper o serviço de escaneamento de diretórios.
- Acessar os diretórios configurados e visualizar logs de erros e avisos ocorridos durante o processo de extração de dados.

### Configurador do Programa

Nesta tela, o usuário define:
- Diretórios para entrada, saída, arquivos processados e rejeitados.
- Intervalo de execução do serviço observador de diretórios.
- Opções para resetar os caminhos para os padrões definidos.

### Configurador de Documentos

Para cada tipo de documento (boletos bancários e declarações de imposto de renda):
- Uma área de texto exibe o conteúdo do PDF, numerado por linhas, facilitando a configuração.
- O usuário seleciona e configura os campos que deverão ser extraídos, podendo limpar e salvar as configurações individualmente.
- No caso das declarações, foi incluída uma funcionalidade de paginação para navegar pelo conteúdo extraído.

## Funcionalidades

- **Extração Configurável Persistente:** Permite ao usuário indicar manualmente as linhas de onde os dados devem ser extraídos e salvar as configurações realizadas.
- **Suporte a Vários Documentos:** Compatível com boletos bancários de vários bancos (Banco do Brasil, Banrisul, Bradesco, Itaú, Santander, Sicredi) e seções comuns da declaração de imposto de renda de pessoa física (DIRPF) de 2022.
- **Processamento Paralelo:** A aplicação utiliza threads para escanear diretórios sem interromper a interação com a interface.
- **Interface Amigável:** Desenvolvida com a API Swing, a interface prioriza a usabilidade.

## Autores

- **Francisco F. Milani** – [ffmilani@ucs.br](mailto:ffmilani@ucs.br)
- **Luiz Henrique Zaballa** – [lhzzaballa@ucs.br](mailto:lhzzaballa@ucs.br)
- **Raul T. Pillotti** – [rtpillotti@ucs.br](mailto:rtpillotti@ucs.br)

## Referências

- [PDFBox](https://pdfbox.apache.org/)
- [Gson](https://github.com/google/gson)
- [Apache Commons IO](https://mvnrepository.com/artifact/commons-io/commonsio)
- [Log4j](https://logging.apache.org/log4j/2.x/)
- [Maven](https://maven.apache.org/)
- Referências metodológicas e documentação técnica adotada durante o desenvolvimento do projeto.

<br/>

## Artigo Técnico de Desenvolvimento

Ao longo do desenvolvimento do projeto, a equipe documentou todas as etapas de Engenharia de Software que culminaram nos resultados e versão final entregue. Para isto, foi utilizado o template LaTeX da Sociedade Brasileira de Computação (SBC).
<br/>
<br/>
O download da versão final do documento, em .PDF, pode ser feito através do link abaixo:

<table>
  <tr>
    <td><img src="https://upload.wikimedia.org/wikipedia/commons/8/87/PDF_file_icon.svg" width="30" /></td>
    <td><a href="https://github.com/user-attachments/files/19430647/Projeto.I.-.InPDF.-.Grupo.10.pdf" target="_blank"><strong>Artigo.pdf</strong></a></td>
  </tr>
</table>
