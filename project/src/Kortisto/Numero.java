
package Kortisto;

public class Numero {
    
    private int ID;
    private int numero;
    private int vuosi;
    private String viivakoodi;
    
    public Numero(int ID, int numero, int vuosi) {
        this.ID = ID;
        this.numero = numero;
        this.vuosi = vuosi;
    }
    
    public int getNumero() {
        return numero;
    }
    
    public int getVuosi() {
        return vuosi;
    }
}
