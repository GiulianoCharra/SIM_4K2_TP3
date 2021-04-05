package funcionDistribucion;

public class Normal extends VariablesAleatorias
{
    private float media;
    private float varianza;
    private  float desviacion;
    private float[][] rnd;
    private float[] rnd1;
    private float[] rnd2;
    private float[] N1;
    private float[] N2;
    private int muestra;

    public Normal(float[] rnd1, float[] rnd2, float media, float varianza)
    {
        this.media = media;
        this.varianza = varianza;
        this.rnd1 = rnd1;
        this.rnd2 = rnd2;
        this.N1 = new float[muestra];
        this.N2 = new float[muestra];
        this.muestra = rnd2.length;
    }

    @Override
    public float calcularMedia()
    {
        return 0;
    }

    @Override
    public float calcularVarianza() {
        return 0;
    }

    public float calcularDesviacionEstandar()
    {
        desviacion = (float) Math.sqrt(varianza);
        return desviacion;
    }

    @Override
    public void funcionDensidad() {

    }

    /**
     * No tiene funcion Acumulada
     */
    @Override
    public void funcionAcumulada() {

    }

    public void generadorBox_Muller()
    {
        for (int i = 0; i < muestra; i++)
        {
            N1[i] = (float) ((Math.sqrt(-2 * Math.log(rnd1[i])) * Math.cos(2 * Math.PI * rnd2[i])) * desviacion + media);
            N2[i] = (float) ((Math.sqrt(-2 * Math.log(rnd1[i])) * Math.sin(2 * Math.PI * rnd2[i])) * desviacion + media);
        }
    }


    public float[] generadorConvolucion()
    {
        float[] convolucion = new float[muestra];
        rnd = new float[muestra][12];

        float sum = 0;

        for (int i = 0; i < rnd.length; i++)
        {
            for (int j = 0; j < rnd[i].length; j++)
            {
                rnd[i][j] = (float) Math.round(Math.random()*10000)/10000;
                sum += rnd[i][j];
            }
            convolucion[i] = (sum - 6) * desviacion + media;
            sum = 0;
        }
        return convolucion;
    }
}
