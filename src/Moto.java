public class Moto extends Veiculo {
    private int cilindradas;

    public Moto(int id, String marca, String modelo, int cilindradas) {
        super(id, marca, modelo);
        this.cilindradas = cilindradas;
    }

    public int getCilindradas() {
        return cilindradas;
    }

    public void setCilindradas(int cilindradas) {
        this.cilindradas = cilindradas;
    }

    @Override
    public String toString() {
        return super.toString() + ", Cilindradas: " + cilindradas;
    }
}
