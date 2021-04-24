package clases.TP1;

public class Numero<E>{
    private int iteracion;
    private E numRand;

    public Numero()
    {
    }

    public Numero(int iteracion, E numRand)
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

    public E getNumRand() {
        return numRand;
    }

    public void setNumRand(E numRand) {
        this.numRand = numRand;
    }

}
