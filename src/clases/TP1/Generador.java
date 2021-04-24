package clases.TP1;

public class Generador
{
    private final int x0; // semilla
    private final int a; // contante multiplicativa
    private final int c;  //contante aditiva -- en el metodo Multiplicativo vale 0, no se usa
    private final int m;  // modulo
    private float xi; // ultima semilla

    public Generador(int x0, int a, int c, int m )
    {
        this.x0 = x0;
        this.a = a;
        this.c = c;
        this.m = m;
        this.xi = x0;
    }

    public float nextNumeroLineal()
    {
        float num = xi/m;
        xi = (( a * xi) + c) % m;

        return (float)(Math.round(num*10000))/10000;
    }

    public float nextNumeroMultiplcativo()
    {
        float num = xi/m;
        xi = (( a * xi)) % m;

        return (float)(Math.round(num*10000))/10000;
    }

    public void reiniciar()
    {
        xi = x0;
    }

}
