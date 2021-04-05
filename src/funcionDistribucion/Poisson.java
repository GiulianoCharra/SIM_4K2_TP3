package funcionDistribucion;

public class Poisson extends VariablesAleatorias
{
    private float lamda;
    private float media;
    private float varianza;

    public Poisson(float lamda)
    {
        this.lamda = lamda;
    }

    @Override
    public float calcularMedia() {
        return 0;
    }

    @Override
    public float calcularVarianza() {
        return 0;
    }

    @Override
    public void funcionDensidad() {

    }

    @Override
    public void funcionAcumulada() {

    }


    public double generadorPoisson()
    {

        double P = 1;
        int X = -1;
        double A = Math.pow(Math.E,-lamda);
        do {
            double U = Math.random();
            P *= U;
            X ++;
        }while (P >= A);

        return X;
    }
}
