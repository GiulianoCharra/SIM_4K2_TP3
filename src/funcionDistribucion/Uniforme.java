package funcionDistribucion;

public class Uniforme extends VariablesAleatorias
{
    private int a;
    private int b;
    private float media;
    private float varianza;
    private float[] numeros;
    private int muestra;

    public Uniforme(float[] numeros, int a, int b)
    {
        this.a = a;
        this.b = b;
        this.numeros = numeros;
        this.muestra = numeros.length;
    }

    @Override
    public float calcularMedia()
    {
        media = (float) (b+a)/2;
        return media;
    }


    @Override
    public float calcularVarianza()
    {
        varianza = (float) (Math.pow((b-a),2)/12);
        return varianza;
    }

    @Override
    public void funcionDensidad()
    {

    }

    @Override
    public void funcionAcumulada()
    {

    }

    public float[] generadorUnitario()
    {
        float[] uniforme = new float[muestra];

        for (int i = 0; i < muestra; i++)
        {
            uniforme[i] = a + numeros[i]*(b-a);
        }
        return uniforme;
    }

}
