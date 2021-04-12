package funcionDistribucion;

public class Exponencial
{
    private float lambda;
    private float media;
    private float varianza;
    private double[] numeros;
    private int muestra;

    public Exponencial(float lambda)
    {
        this.lambda = lambda;
    }


    public float calcularMedia()
    {
        media = 1/lambda;
        return media;
    }


    public float calcularVarianza(float lambda)
    {
        varianza = (float) (1/Math.pow(lambda,2));
        return varianza;
    }


    public void funcionDensidad() {

    }


    public void funcionAcumulada() {

    }


    public double[] generar(float media, double[] numeros)
    {
        return generadorExponencial(media, numeros);
    }

    public static double[] generar(float media, int muestra)
    {
        double[] numeros = new double[muestra];

        System.out.println("PRIMERA PARTE \n genera lo random\n"); // Control de dasd

        for (int i = 0; i < muestra; i++)
        {
            numeros[i] = Math.random();
            System.out.println(String.valueOf(numeros[i]).replace('.',','));
        }
        return generadorExponencial(media, numeros);
    }
    public double[] generar()
    {
        return generadorExponencial(this.media, this.numeros);
    }

    private static double[] generadorExponencial(float media, double[] numeros)
    {
        int muestra = numeros.length;

        System.out.println("Segunda PARTE \n hace la cosa esta");

        double[] exponensial = new double[muestra];
        for (int i = 0; i < muestra; i++)
        {
                exponensial[i] = (float) (- media * Math.log( 1 - numeros[i]));
        }
        return exponensial;
    }
}
