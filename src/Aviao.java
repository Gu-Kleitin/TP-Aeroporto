public class Aviao {
    private int id;
    private int tempoEspera;
    private int combustivel; // Unidades de tempo restantes para pouso de emergência

    // Construtor para aviões decolando
    // ID é par. A reserva de combustível não é um fator, então podemos
    // usar um valor padrão que indica "não aplicável".
    public Aviao(int id) {
        this.id = id;
        this.tempoEspera = 0;
        this.combustivel = -1; // -1 indica que não há contagem de combustível
    }

    // Construtor para aviões pousando
    // ID é ímpar. O avião recebe uma reserva de combustível (unidades de tempo).
    public Aviao(int id, int combustivel) {
        this.id = id;
        this.tempoEspera = 0;
        this.combustivel = combustivel;
    }

    // Getters para acessar as informações do avião
    public int getId() {
        return id;
    }

    public int getTempoEspera() {
        return tempoEspera;
    }

    public int getCombustivel() {
        return combustivel;
    }

    // Métodos para atualizar o estado do avião

    // Incrementa o tempo de espera do avião na fila
    public void incrementarTempoEspera() {
        this.tempoEspera++;
    }

    // Decrementa a reserva de combustível, aplicável apenas a aviões de pouso
    public void decrementarCombustivel() {
        // Apenas decrementa se a reserva for maior que zero
        if (this.combustivel > 0) {
            this.combustivel--;
        }
    }

    // Verifica se o avião está em estado de emergência (sem combustível)
    public boolean precisaPousarEmergencia() {
        return this.combustivel == 0;
    }

    @Override
    public String toString() {
        if (this.combustivel == -1) {
            // Representação para aviões de decolagem
            return "Avião [ID: " + id + ", Tempo de Espera: " + tempoEspera + "]";
        } else {
            // Representação para aviões de pouso
            return "Avião [ID: " + id + ", Tempo de Espera: " + tempoEspera + ", Combustível: " + combustivel + "]";
        }
    }
}

/* Justificativa das escolhas de design:
Dois construtores: Isso torna o código mais claro e seguro, pois um avião de decolagem não precisa de uma reserva de combustível, e a forma como ele é criado reflete isso.
Atributo combustivel: Utiliza um valor sentinela (-1) para indicar que a contagem de combustível não se aplica a aviões de decolagem. Isso evita a necessidade de uma subclasse e simplifica a lógica geral.
Métodos de atualização: incrementarTempoEspera() e decrementarCombustivel() encapsulam a lógica de atualização do estado do avião, que será chamada a cada unidade de tempo.
Método precisaPousarEmergencia(): Este método é crucial para o simulador, pois ele permite verificar facilmente quais aviões precisam de prioridade de pouso, conforme as regras da Pista 3.
Método toString(): A sobrescrita do método toString() facilita a impressão do estado da fila e de cada avião, tornando a saída do programa mais "auto-explicativa e fácil de entender", como solicitado.
   */