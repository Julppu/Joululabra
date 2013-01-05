
package Kortisto;

public class Numero {
    
    private int ID;
    private int numero;
    private int vuosi;
    
    public Numero(int ID, int vuosi, int numero) {
        this.ID = ID;
        this.numero = numero;
        this.vuosi = vuosi;
    }
    
    public void setNumero(int uusiNumero) {
        this.numero = uusiNumero;
    }
    
    public int getNumero() {
        return numero;
    }
    
    public void setVuosi(int uusiVuosi) {
        this.vuosi = uusiVuosi;
    }
    
    public int getVuosi() {
        return vuosi;
    }
}
