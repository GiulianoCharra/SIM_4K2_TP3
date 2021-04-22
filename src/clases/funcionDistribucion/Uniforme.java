package clases.funcionDistribucion;

public class Uniforme
{
    private float a;
    private float b;
    private float media;
    private float varianza;
    private double[] numeros;
    private int muestra;

    public Uniforme(float a, float b, double[] numeros)
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

    public double[] generar(float a, float b, double[]numeros)
    {
        return generadorUnitario(a, b, numeros);
    }

    public static double[] generar(float a, float b, int muestra)
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

    private static double[] generadorUnitario(float a, float b, double[] numeros)
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
