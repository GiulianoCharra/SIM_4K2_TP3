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

    public static int[] generar(double lamba, int muestra)
    {   int[] vec = new int[muestra];
        for (int i = 0; i < muestra; i++) {
            vec[i] = generadorPoisson(lamba);
        }
        return vec;
    }

    private static int generadorPoisson(double lamda)
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

    /*public static class Intervalo
    {
        private int numIt;
        private float inferior;
        private float superior;
        private int f_Obs;
        private int f_Esp;
        private float chi;

        public Intervalo(int num, float inferior, float superior)
        {
            this.numIt = num;
            this.inferior = inferior;
            this.superior = superior;
            this.f_Obs = 0;
            this.f_Esp = 0;
        }

        public int getNumIt() {
            return numIt;
        }

        public void setNumIt(int numIt) {
            this.numIt = numIt;
        }

        public float getInferior() {
            return inferior;
        }

        public void setInferior(float inferior) {
            this.inferior = inferior;
        }

        public float getSuperior() {
            return superior;
        }

        public void setSuperior(float superior) {
            this.superior = superior;
        }

        public int getF_Obs() {
            return f_Obs;
        }

        public void setF_Obs(int f_Obs) {
            this.f_Obs = f_Obs;
        }

        public int getF_Esp() {
            return f_Esp;
        }

        public void setF_Esp(int f_Esp) {
            this.f_Esp = f_Esp;
        }

        public float getChi() {
            return chi;
        }

        public void setChi(float chi) {
            this.chi = chi;
        }

        public void contar()
        {
            f_Obs++;
        }

        @Override
        public String toString() {
            return "intervaloExp{" +
                    "num=" + numIt +
                    ", inferior=" + inferior +
                    ", superior=" + superior +
                    ", f_Obs=" + f_Obs +
                    ", f_Esp=" + f_Esp +
                    ", chi=" + chi +
                    '}';
        }
    }*/
}
