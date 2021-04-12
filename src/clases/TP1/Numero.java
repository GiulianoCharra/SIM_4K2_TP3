package clases.TP1;

public class Numero {
    private int iteracion;
    private float numRand;

    public Numero()
    {
    }

    public Numero(int iteracion, float numRand)
    {
        this.iteracion = iteracion;
        this.numRand = numRand;
    }

    public int getIteracion()
    {
        return iteracion;
    }

    public void setIteracion(int iteracion) {
        this.iteracion = iteracion;
    }

    public float getNumRand() {
        return numRand;
    }

    public void setNumRand(float numRand) {
        this.numRand = numRand;
    }

}
