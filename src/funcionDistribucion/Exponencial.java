package funcionDistribucion;

public class Exponencial extends VariablesAleatorias
{
    private float lambda;
    private float media;
    private float varianza;
    private float[] numeros;
    private int muestra;

    public Exponencial(float[] numeros, float lambda)
    {
        this.lambda = lambda;
        this.numeros = numeros;
        this.muestra = numeros.length;;
    }

    @Override
    public float calcularMedia()
    {
        media = 1/lambda;
        return media;
    }

    @Override
    public float calcularVarianza()
    {
        varianza = (float) (1/Math.pow(lambda,2));
        return varianza;
    }

    @Override
    public void funcionDensidad() {

    }

    @Override
    public void funcionAcumulada() {

    }


    public float[] generadorExponencial()
    {
        float[] exponensial = new float[muestra];
        for (int i = 0; i < muestra; i++)
        {
                exponensial[i] = (float) (-media*Math.log(1-numeros[i]));
        }
        return exponensial;
    }
}
