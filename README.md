# Capital Gain Challenge

## Decisões Técnicas e Arquiteturais

A solução foi projetada para ser simples e eficiente, utilizando uma abordagem baseada em memória para gerenciar o estado das operações. As principais decisões incluem:

- **Arquitetura Diplomata**: Inspirada na arquitetura do Nubank, a solução separa claramente as responsabilidades entre:
  - **Controllers**: Funções não puras responsáveis por interações externas, como entrada e saída de dados.
  - **Logic**: Funções puras que contêm as regras de negócio, garantindo maior testabilidade e previsibilidade.
- **Processamento em Memória**: Todas as operações são processadas em memória como solicitado, utilizando os atomos do Clojure.

## Justificativa para o Uso de Frameworks ou Bibliotecas

Nenhum framework ou biblioteca externa foi utilizada, exceto as bibliotecas padrão da linguagem escolhida. Isso foi feito para manter a simplicidade e evitar dependências desnecessárias, alinhando-se às expectativas do desafio.

## Instruções para Compilar e Executar o Projeto

1. Certifique-se de ter o ambiente de execução do Clojure (Leineghen)
2. Navegue até o diretório do projeto:
   ```bash
   cd /capital-gain-challenge
   ```
3. Execute o programa utilizando o comando:
   ```bash
   lein run
   ```

## Instruções para Executar os Testes

1. Certifique-se de que o ambiente de testes está configurado.
2. Navegue até o diretório do projeto:
   ```bash
   cd /capital-gain-challenge
   ```
3. Execute os testes com o comando:
   ```bash
   lein test
   ```

## Notas Adicionais

- **Casos de Teste**: O programa foi validado com todos os casos de teste fornecidos no enunciado, garantindo cobertura completa.
- **Erros de Entrada**: O programa assume que a entrada está sempre no formato correto, conforme especificado no desafio.
