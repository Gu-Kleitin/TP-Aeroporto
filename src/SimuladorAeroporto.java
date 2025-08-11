import java.util.Random;

public class SimuladorAeroporto {

    // Armazena a saída de texto de cada evento
    private static StringBuilder eventosUnidadeTempo;

    // Array de 4 filas de pouso.
    private static Fila[] filasPouso = new Fila[4];

    // Array de 3 filas de decolagem.
    private static Fila[] filasDecolagem = new Fila[3];

    // Gerador de números aleatórios para simular as chegadas.
    private static Random random = new Random();

    // Contadores para os IDs de aviões.
    // IDs ímpares para pouso e pares para decolagem, sucessivamente.
    private static int proximoIdPouso = 1;
    private static int proximoIdDecolagem = 2;

    // Variáveis para calcular as médias e o número de pousos de emergência.
    private static int totalPousos = 0;
    private static long totalTempoEsperaPouso = 0;
    private static int totalDecolagens = 0;
    private static long totalTempoEsperaDecolagem = 0;
    private static int pousosEmergencia = 0;

    public static void main(String[] args) {
        // Inicialização das filas
        for (int i = 0; i < 4; i++) {
            filasPouso[i] = new Fila();
        }
        for (int i = 0; i < 3; i++) {
            filasDecolagem[i] = new Fila();
        }

        int unidadeTempo = 0;

        // Loop principal da simulação.
        while (unidadeTempo < 10) {

            System.out.println("--- Unidade de Tempo: " + unidadeTempo + " ---");
            eventosUnidadeTempo = new StringBuilder();
            //Simulando a chegada de novos aviões.
            gerarChegadas();

            //Atualizando o estado dos aviões nas filas.
            atualizarFilas();
            //System.out.print(eventosUnidadeTempo.toString());

            //Gerenciando as operações nas pistas.
            gerenciarPistas();

            // Imprimindo o relatório periodicamente.
            imprimirRelatorio();

            // Atualiza o tempo
            unidadeTempo++;
        }
    }

    // Método para gerar chegadas de aviões.
    private static void gerarChegadas() {
        int numChegadasPouso = random.nextInt(4);
        int numChegadasDecolagem = random.nextInt(4);

        // Lógica para aviões de pouso
        for (int i = 0; i < numChegadasPouso; i++) {
            int combustivel = random.nextInt(20) + 1;
            Aviao novoAviao = new Aviao(proximoIdPouso, combustivel);
            proximoIdPouso += 2; // IDs ímpares

            // Adicionar à fila de pouso menor
            int filaMenor = encontrarFilaMenor(filasPouso);
            filasPouso[filaMenor].enfileirar(novoAviao);
        }

        // Lógica para aviões de decolagem
        for (int i = 0; i < numChegadasDecolagem; i++) {
            Aviao novoAviao = new Aviao(proximoIdDecolagem);
            proximoIdDecolagem += 2; // IDs pares

            // Adicionar à fila de decolagem menor
            int filaMenor = encontrarFilaMenor(filasDecolagem);
            filasDecolagem[filaMenor].enfileirar(novoAviao);
        }
    }

    // Método auxiliar para encontrar a fila com o menor número de aviões.
    private static int encontrarFilaMenor(Fila[] filas) {
        int menorTamanho = Integer.MAX_VALUE;
        int indiceMenor = -1;
        for (int i = 0; i < filas.length; i++) {
            if (filas[i].tamanho() < menorTamanho) {
                menorTamanho = filas[i].tamanho();
                indiceMenor = i;
            }
        }
        return indiceMenor;
    }

    // Método para atualizar o estado dos aviões nas filas.
    private static void atualizarFilas() {
        // Atualiza as filas de pouso
        for (Fila fila : filasPouso) {
            fila.atualizarAviões();
        }

        // Atualiza as filas de decolagem
        for (Fila fila : filasDecolagem) {
            fila.atualizarAviões();
        }
    }

    // Método para encontrar a fila mais longa entre as filas de pouso para alocação normal.
    // O "inicio" e "fim" são os índices do array de filas.
    private static int encontrarFilaMaisLonga(Fila[] filas, int inicio, int fim) {
        int maiorTamanho = -1;
        int indiceMaior = -1;
        for (int i = inicio; i < fim; i++) {
            // Ignorar filas vazias ou que já foram processadas
            if (filas[i].tamanho() > maiorTamanho && !filas[i].estaVazia()) {
                maiorTamanho = filas[i].tamanho();
                indiceMaior = i;
            }
        }
        return indiceMaior;
    }

    // Método para processar um pouso.
    private static void pousarAviao(Aviao aviao, int pista) {
        if (aviao == null) {
            return; // Isso evita um nullpointer
        }
        totalPousos++;
        totalTempoEsperaPouso += aviao.getTempoEspera();
        eventosUnidadeTempo.append("Pouso na Pista ").append(pista).append(" de ").append(aviao).append("\n");
    }

    // Método para processar uma decolagem.
    private static void decolarAviao(Aviao aviao, int pista) {
        if (aviao == null) {
            return; // Evita NullPointerException se a fila estiver vazia.
        }
        totalDecolagens++;
        totalTempoEsperaDecolagem += aviao.getTempoEspera();
        eventosUnidadeTempo.append("Decolagem na Pista ").append(pista).append(" de ").append(aviao).append("\n");    }

    // Método para gerenciar pousos e decolagens nas pistas.
    private static void gerenciarPistas() {
        boolean[] pistasOcupadas = new boolean[3]; // Pista 1, 2, 3

        // ----- FASE 1: Pousos de Emergência -----
        for (int i = 0; i < filasPouso.length; i++) {
            if (!filasPouso[i].estaVazia() && filasPouso[i].primeiro().precisaPousarEmergencia()) {
                if (!pistasOcupadas[2]) { // Prioridade 1: Pista 3
                    Aviao aviao = filasPouso[i].desenfileirar();
                    pousarAviao(aviao, 3);
                    pousosEmergencia++;
                    pistasOcupadas[2] = true;
                } else if (!pistasOcupadas[0]) { // Prioridade 2: Pista 1
                    Aviao aviao = filasPouso[i].desenfileirar();
                    pousarAviao(aviao, 1);
                    pousosEmergencia++;
                    pistasOcupadas[0] = true;
                } else if (!pistasOcupadas[1]) { // Prioridade 3: Pista 2
                    Aviao aviao = filasPouso[i].desenfileirar();
                    pousarAviao(aviao, 2);
                    pousosEmergencia++;
                    pistasOcupadas[1] = true;
                }
            }
        }

        // ----- FASE 2: Pousos Normais -----
        if (!pistasOcupadas[0]) {
            int filaMaisLonga = encontrarFilaMaisLonga(filasPouso, 0, 4);
            if (filaMaisLonga != -1) {
                Aviao aviao = filasPouso[filaMaisLonga].desenfileirar();
                pousarAviao(aviao, 1);
                pistasOcupadas[0] = true;
            }
        }
        if (!pistasOcupadas[1]) {
            int filaMaisLonga = encontrarFilaMaisLonga(filasPouso, 0, 4);
            if (filaMaisLonga != -1) {
                Aviao aviao = filasPouso[filaMaisLonga].desenfileirar();
                pousarAviao(aviao, 2);
            }
        }

        // ----- FASE 3: Decolagens -----
        if (!pistasOcupadas[2]) { // Prioridade: Pista 3
            if (!filasDecolagem[2].estaVazia()) {
                Aviao aviao = filasDecolagem[2].desenfileirar();
                decolarAviao(aviao, 3);
                pistasOcupadas[2] = true;
            }
        }
        if (!pistasOcupadas[0]) {
            if (!filasDecolagem[0].estaVazia()) {
                Aviao aviao = filasDecolagem[0].desenfileirar();
                decolarAviao(aviao, 1);
                pistasOcupadas[0] = true;
            }
        }
        if (!pistasOcupadas[1]) {
            if (!filasDecolagem[1].estaVazia()) {
                Aviao aviao = filasDecolagem[1].desenfileirar();
                decolarAviao(aviao, 2);
                pistasOcupadas[1] = true;
            }
        }
    }

    // Método para imprimir o relatório completo.
    private static void imprimirRelatorio() {
        System.out.println("\n--- RELATÓRIO DO AEROPORTO ---");

        // Imprime os eventos da unidade de tempo atual
        if (eventosUnidadeTempo.length() > 0) {
            System.out.println("Eventos desta unidade de tempo:");
            System.out.print(eventosUnidadeTempo.toString());
        } else {
            System.out.println("Nenhum evento de pouso ou decolagem nesta unidade de tempo.");
        }

        // Deixa claro que o estado das filas é após os eventos.
        System.out.println("Estado das Filas após eventos desta unidade de tempo:");

        // a) Conteúdo de cada fila.
        System.out.println("Conteúdo das Filas de Pouso:");
        for (int i = 0; i < 4; i++) {
            System.out.println("  Fila de Pouso " + (i + 1) + ": " + filasPouso[i].toString());
        }
        System.out.println("Conteúdo das Filas de Decolagem:");
        for (int i = 0; i < 3; i++) {
            System.out.println("  Fila de Decolagem " + (i + 1) + ": " + filasDecolagem[i].toString());
        }

        // b) Tempo médio de espera para decolagem.
        if (totalDecolagens > 0) {
            double tempoMedioDecolagem = (double) totalTempoEsperaDecolagem / totalDecolagens;
            System.out.printf("Tempo médio de espera para decolagem: %.2f unidades de tempo\n", tempoMedioDecolagem);
        } else {
            System.out.println("Tempo médio de espera para decolagem: N/A (nenhuma decolagem ainda)");
        }

        // c) Tempo médio de espera para aterrissagem.
        if (totalPousos > 0) {
            double tempoMedioPouso = (double) totalTempoEsperaPouso / totalPousos;
            System.out.printf("Tempo médio de espera para aterrissagem: %.2f unidades de tempo\n", tempoMedioPouso);
        } else {
            System.out.println("Tempo médio de espera para aterrissagem: N/A (nenhum pouso ainda)");
        }

        // d) Número de aviões que aterrissam sem reserva de combustível.
        System.out.println("Número de pousos de emergência: " + pousosEmergencia);
        System.out.println("-------------------------------------\n");
    }
}
