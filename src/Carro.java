public class Carro extends Veiculo {
    private int quantidadePortas;

    public Carro(int id, String marca, String modelo, int quantidadePortas) {
        super(id, marca, modelo);
        this.quantidadePortas = quantidadePortas;
    }

    public int getQuantidadePortas() {
        return quantidadePortas;
    }

    public void setQuantidadePortas(int quantidadePortas) {
        this.quantidadePortas = quantidadePortas;
    }

    @Override
    public String toString() {
        return super.toString() + ", Quantidade de Portas: " + quantidadePortas;
    }
}
