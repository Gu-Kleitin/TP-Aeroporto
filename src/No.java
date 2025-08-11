public class No {
    // O avião que este nó armazena
    private Aviao aviao;

    // Referência para o próximo nó da lista
    private No proximo;

    public No(Aviao aviao) {
        this.aviao = aviao;
        this.proximo = null; // Inicialmente, este é o último nó
    }

    // Getters e setters
    public Aviao getAviao() {
        return aviao;
    }

    public No getProximo() {
        return proximo;
    }

    public void setProximo(No proximo) {
        this.proximo = proximo;
    }
}