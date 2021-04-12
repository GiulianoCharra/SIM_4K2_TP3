package clases.funcionDistribucion;

public class Uniforme
{
    private int a;
    private int b;
    private float media;
    private float varianza;
    private double[] numeros;
    private int muestra;

    public Uniforme(int a, int b, double[] numeros)
    {
        this.a = a;
        this.b = b;
        this.numeros = numeros;
        this.muestra = numeros.length;
    }

    public float calcularMedia(int a, int b)
    {
        media = (float) (b+a)/2;
        return media;
    }


    public float calcularVarianza(int a, int b)
    {
        varianza = (float) (Math.pow((b-a),2)/12);
        return varianza;
    }

    public void funcionDensidad()
    {

    }

    public void funcionAcumulada()
    {

    }



    public double[] generar()
    {
        return generadorUnitario(this.a, this.b,this.numeros);
    }

    public double[] generar(int a, int b, double[]numeros)
    {
        return generadorUnitario(a, b, numeros);
    }

    public static double[] generar(int a, int b, int muestra)
    {
        double[] numeros = new double[muestra];

        System.out.println("PRIMERA PARTE \n genera lo random\n");

        for (int i = 0; i < muestra; i++)
        {
            numeros[i] = Math.random();
            System.out.println(String.valueOf(numeros[i]).replace('.',','));
        }
        return generadorUnitario(a,b,numeros);
    }

    private static double[] generadorUnitario(int a, int b, double[] numeros)
    {
        int muestra = numeros.length;
        double[] uniforme = new double[muestra];

        System.out.println("\nSegunda PARTE \nhace el coso de unitario\n");
        for (int i = 0; i < muestra; i++)
        {
            uniforme[i] = a + numeros[i]*(b-a);
        }
        return uniforme;
    }

}
