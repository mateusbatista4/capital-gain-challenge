
# Capital Gain Challenge

## Decisões Técnicas e Arquiteturais

A solução foi projetada para ser simples e eficiente, utilizando uma abordagem baseada em memória para gerenciar o estado das operações. As principais decisões incluem:

- **Processamento em Memória**: Todas as operações são processadas em memória para evitar dependências externas, garantindo que o programa seja independente e fácil de executar.
- **Cálculo Incremental**: O cálculo do preço médio ponderado e do imposto é realizado de forma incremental, garantindo precisão e eficiência.
- **Entrada e Saída Padrão**: O programa utiliza `stdin` para entrada e `stdout` para saída, permitindo fácil integração com redirecionamento de arquivos e pipelines de terminal.

## Justificativa para o Uso de Frameworks ou Bibliotecas

Nenhum framework ou biblioteca externa foi utilizada, exceto as bibliotecas padrão da linguagem escolhida. Isso foi feito para manter a simplicidade e evitar dependências desnecessárias, alinhando-se às expectativas do desafio.

## Instruções para Compilar e Executar o Projeto

1. Certifique-se de ter o ambiente de execução da linguagem configurado (por exemplo, Python, Node.js, etc.).
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

- **Arredondamento**: Todos os cálculos utilizam arredondamento para duas casas decimais, conforme especificado no desafio.
- **Casos de Teste**: O programa foi validado com todos os casos de teste fornecidos no enunciado, garantindo cobertura completa.
- **Extensibilidade**: A solução foi projetada para ser facilmente extensível, permitindo a adição de novas regras ou funcionalidades no futuro.
- **Erros de Entrada**: O programa assume que a entrada está sempre no formato correto, conforme especificado no desafio.
