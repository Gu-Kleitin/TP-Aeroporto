public class Fila {
    // O primeiro nó da fila (onde os aviões serão removidos)
    private No inicio;

    // O último nó da fila (onde os aviões serão adicionados)
    private No fim;

    // Construtor
    public Fila() {
        this.inicio = null;
        this.fim = null;
    }

    // Método para adicionar um avião no final da fila (enqueue)
    public void enfileirar(Aviao aviao) {
        No novoNo = new No(aviao);
        if (estaVazia()) {
            this.inicio = novoNo;
            this.fim = novoNo;
        } else {
            this.fim.setProximo(novoNo);
            this.fim = novoNo;
        }
    }

    // Método para remover um avião do início da fila (dequeue)
    public Aviao desenfileirar() {
        if (estaVazia()) {
            return null; // A fila está vazia
        }
        Aviao aviaoRemovido = this.inicio.getAviao();
        this.inicio = this.inicio.getProximo();
        if (this.inicio == null) {
            this.fim = null; // A fila ficou vazia
        }
        return aviaoRemovido;
    }

    public void atualizarAviões() {
        No atual = this.inicio;
        while (atual != null) {
            Aviao aviao = atual.getAviao();
            // Aviões de pouso têm combustível >= 0, aviões de decolagem têm -1.
            if (aviao.getCombustivel() != -1) {
                aviao.decrementarCombustivel();
            }
            aviao.incrementarTempoEspera();
            atual = atual.getProximo();
        }
    }

    // Método para espiar o primeiro avião da fila sem removê-lo
    public Aviao primeiro() {
        if (estaVazia()) {
            return null;
        }
        return this.inicio.getAviao();
    }

    // Verifica se a fila está vazia
    public boolean estaVazia() {
        return this.inicio == null;
    }

    // Retorna o tamanho da fila
    public int tamanho() {
        int contador = 0;
        No atual = this.inicio;
        while (atual != null) {
            contador++;
            atual = atual.getProximo();
        }
        return contador;
    }

    // Retorna o conteúdo da fila como uma string para fácil impressão
    @Override
    public String toString() {
        if (estaVazia()) {
            return "Fila vazia.";
        }
        StringBuilder sb = new StringBuilder();
        No atual = this.inicio;
        while (atual != null) {
            sb.append(atual.getAviao().toString());
            if (atual.getProximo() != null) {
                sb.append(" -> ");
            }
            atual = atual.getProximo();
        }
        return sb.toString();
    }
}

/*
* Justificativa das escolhas de design:

Encapsulamento: As classes Fila e No são projetadas para serem bem organizadas e com responsabilidades claras. A classe Fila gerencia a lógica de como os aviões entram e saem, e a classe No simplesmente armazena um avião e a ligação para o próximo na linha. Isso separa bem as tarefas e torna o código mais fácil de entender e de manter.

inicio e fim: Manter referências diretas para o começo (inicio) e o final (fim) da fila é uma estratégia inteligente. Isso significa que, para adicionar um novo avião ou para remover o próximo avião, não precisamos percorrer a fila inteira. Podemos ir diretamente para o ponto de interesse.

Eficiência nas Operações: Graças às referências inicio e fim, as operações de adicionar um avião e remover um avião são muito rápidas. Elas levam praticamente o mesmo tempo, independentemente se a fila tem poucos ou muitos aviões.

toString(): A forma como a classe Fila se apresenta quando convertida para texto (toString()) foi pensada para ser fácil de ler. Isso é útil para atender ao requisito do projeto de ter uma saída "auto-explicativa e fácil de entender".

Método primeiro(): Este método é importante porque nos permite olhar para o avião que está na frente da fila — por exemplo, para verificar se ele precisa de um pouso de emergência — sem removê-lo. Isso é crucial para a lógica de prioridade do simulador.
*  */