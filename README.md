Trabalho Prático 1 - Simulador de Aeroporto
Descrição do Projeto
Este projeto é uma simulação de pousos e decolagens em um aeroporto com três pistas, implementado em Java. O objetivo principal é gerenciar o fluxo de aviões em filas de espera, respeitando as regras de prioridade e de uso de pistas.

A simulação avança em unidades de tempo discretas, gerando chegadas de novos aviões de forma aleatória e processando as operações de pouso e decolagem. A cada unidade de tempo, um relatório detalhado é impresso para monitorar o estado das filas e o desempenho geral do aeroporto.

Funcionalidades Principais

- Gerenciamento de Filas: Os aviões que chegam são adicionados ao final de filas de espera, que foram projetadas para tentar manter o mesmo tamanho, evitando que uma fila fique excessivamente longa. As filas não são reordenadas, seguindo a lógica de "primeiro a chegar, primeiro a ser atendido" (FIFO).

- Controle de Pistas: O simulador opera com 3 pistas. A pista 3 é usada para decolagens, exceto em casos de emergência.

- Lógica de Pouso de Emergência: Aviões de pouso têm uma reserva de combustível limitada. Se essa reserva chega a zero, o avião entra em estado de emergência e tem prioridade máxima para pousar, utilizando a Pista 3 ou outras pistas, se necessário.

- Geração Aleatória: A chegada de novos aviões para pouso e decolagem é simulada por meio de um gerador de números aleatórios, garantindo que a simulação seja dinâmica a cada execução.

- Relatório Detalhado: A cada unidade de tempo, o programa gera um relatório autoexplicativo que inclui:

  - O conteúdo de cada fila de pouso e decolagem.
  
  - O tempo médio de espera para pousos e decolagens.
  
  - O número de pousos de emergência.

Estruturas de Dados e Decisões de Design
Este projeto foi desenvolvido utilizando apenas as estruturas de dados implementadas manualmente, como parte dos requisitos da disciplina de Programação III. Não foram utilizadas classes prontas da biblioteca padrão do Java, como ArrayList ou LinkedList.

 - Classes Fila e No: Para implementar as filas de espera, foram criadas as classes Fila e No (nó), que representam uma lista encadeada. A classe 

 - Fila gerencia as operações de enfileirar e desenfileirar de forma eficiente, mantendo referências diretas para o início e o fim da fila.

 - Classe Aviao: A classe Aviao foi projetada para ser versátil, usando o atributo combustivel para diferenciar aviões de pouso (valor >= 0) e de decolagem (valor sentinela de -1). Isso evita a necessidade de herança de classes, simplificando a lógica geral.

 - IDs: Os IDs dos aviões são gerados em sequência, usando IDs ímpares para aviões de pouso e pares para aviões de decolagem, conforme a especificação do projeto.
