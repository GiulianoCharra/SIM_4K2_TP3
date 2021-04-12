package clases.funcionDistribucion;

public class Poisson
{
    private float lamda;
    private float media;
    private float varianza;

    public Poisson(float lamda)
    {
        this.lamda = lamda;
    }


    public float calcularMedia()
    {
        media = lamda;
        return media;
    }


    public float calcularVarianza()
    {
        varianza = lamda;
        return varianza;
    }


    public void funcionDensidad() {

    }


    public void funcionAcumulada() {

    }

    public static double generar(double lamba)
    {
        return generadorPoisson(lamba);
    }

    private static double generadorPoisson(double lamda)
    {
        double P = 1;
        int X = -1;
        double A = Math.pow(Math.E,-lamda);
        double U;
        do {
            U = Math.random();
            P *= U;
            X ++;
        }while (P >= A);

        return X;
    }

}
