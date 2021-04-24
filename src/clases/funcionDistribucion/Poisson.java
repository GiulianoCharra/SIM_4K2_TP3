package clases.funcionDistribucion;

import clases.soporte.Intervalo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Arrays;

public class Poisson
{
    private float lambda;
    private float media;
    private float varianza;
    private int muestra;
    private int[] poisson;

    private int min;
    private int max;
    private ObservableList<Intervalo> intervalosPoisson = FXCollections.observableArrayList();


    public Poisson() {
    }

    public Poisson(float lamda)
    {
        this.lambda = lamda;
    }


    public float calcularMedia()
    {
        media = lambda;
        return media;
    }


    public float calcularVarianza()
    {
        varianza = lambda;
        return varianza;
    }


    public void funcionDensidad() {

    }


    public void funcionAcumulada() {

    }

    public int[] generar(float lambda, int muestra)
    {
        this.muestra = muestra;
        this.lambda = lambda;
        poisson = new int[muestra];
        int num = generadorPoisson(lambda);
        this.min = num;
        this.max = num;

        for (int i = 0; i < muestra; i++)
        {
            num = generadorPoisson(lambda);
            poisson[i] = num;

            if (num < min)
                min = num;
            else{
                if (num > max)
                    max = num;
            }
            System.out.println(num);
        }
        return poisson;
    }

    private int generadorPoisson(double lambda)
    {
        double P = 1;
        int X = -1;
        double A = Math.pow(Math.E,-lambda);
        double U;
        do {
            U = Math.random();
            P *= U;
            X ++;
        }while (P >= A);

        return X;
    }

    private void calcularF_Observada()
    {
        System.out.println("\n" + (Arrays.toString(poisson)) + "\n");
        for (double n: poisson)
        {
            for (Intervalo i: intervalosPoisson)
            {
                System.out.println("Inf: " + i.getInferior()+
                        " Sup: " + i.getSuperior()+
                        " N°: " + n);
                if (n == i.getNumIt())
                {
                    System.out.println("aceptado");
                    i.contar();
                    break;
                }
            }
        }
    }

    public void  calcIntervalos()
    {
        int dif = max - min;
        int pos = min;
        System.out.println("min: " + min + " max: " + max + " dif: " + dif);
        for (int i = 0; i <= dif; i++) {
            intervalosPoisson.add(new Intervalo(pos,pos,pos));
            pos++;
        }

    }


//    public void  calcIntervalos()
//    {
//        boolean existe = false;
//        for (int n: poisson)
//        {
//
//            //System.out.println(intervalosPoisson.get(0));
//            if (intervalosPoisson.isEmpty())
//            {
//                intervalosPoisson.add(new Intervalo(n,n,n));
//                intervalosPoisson.get(0).contar();
//                continue;
//            }
//            for (Intervalo i: intervalosPoisson)
//            {
//                if (i.getNumIt() == n)
//                {
//                    existe = true;
//                    i.contar();
//                    break;
//                }
//                existe = false;
//            }
//            if (!existe)
//            {
//                intervalosPoisson.add(new Intervalo(n,n,n));
//                intervalosPoisson.get(intervalosPoisson.size()-1).contar();
//            }
//        }
//    }

    public double factorial(int n) {
        if (n==0)
            return 1;
        if (n <= 2) {
            return n;
        }
        return n * factorial(n - 1);
    }

    public void probabilidad(int tam)
    {
        double p ;
        for (Intervalo ie: intervalosPoisson)
        {
            System.out.println("\nintervalo: " + ie.getNumIt() + " lambda: " + lambda + " factorial: " + factorial(ie.getNumIt()));
            double fact = factorial(ie.getNumIt());
            p = (Math.pow(lambda,ie.getNumIt())* Math.pow(Math.E,-lambda))/fact;

            System.out.println(p*tam);
            ie.setF_Esp(Math.round(p * tam));

            System.out.println("frecuencia esperada: " + ie);
        }
    }


    public ObservableList<Intervalo> crearTablaChi()
    {
        ObservableList<Intervalo> tablaChi = FXCollections.observableArrayList();
        Intervalo aux = new Intervalo();


        boolean ban = false;

        for (Intervalo ie: intervalosPoisson)
        {
            if (ie.getF_Esp()>= 5)
            {
                tablaChi.add(ie);
            }
            else
            {
                if (!ban)
                {
                    aux = new Intervalo();
                    aux = ie;
                    ban = true;

                    System.out.println("\n SOy el auxiliar: " + aux);
                }
                else
                {
                    aux.setSuperior(ie.getSuperior());
                    aux.setF_Obs(aux.getF_Obs() + ie.getF_Obs());
                    aux.setF_Esp(aux.getF_Esp() + ie.getF_Esp());

                    System.out.println("\n SOy el auxiliar: " + aux);

                    if (aux.getF_Esp()>= 5)
                    {
                        tablaChi.add(aux);
                        aux = null;
                        ban = false;
                    }
                }
            }
        }

        if (aux != null)
        {
            Intervalo last = tablaChi.get(tablaChi.size() - 1);
            last.setSuperior(aux.getSuperior());
            last.setF_Obs(last.getF_Obs() + aux.getF_Obs());
            last.setF_Esp(last.getF_Esp() + aux.getF_Esp());
        }

        for (Intervalo e: tablaChi
        ) {
            System.out.println("\n"+e);

        }

        return tablaChi;
    }

    public ObservableList<Intervalo> calcularChi()
    {
        calcIntervalos();
        calcularF_Observada();
        probabilidad(muestra);

        float sum = 0;
        int fO;
        float fE;

        ObservableList<Intervalo> tablaChi = crearTablaChi();

        for (Intervalo chi: tablaChi)
        {
            fO = chi.getF_Obs();
            fE = chi.getF_Esp();
            float v = (float) (Math.pow((fO - fE), 2) / fE);
            System.out.println("fObse:" +fO+ " fEsp: " + fE+" nuevo: " + sum + "+"+ v + "= " + ((sum + v)) );
            sum += v;
            chi.setChi((float)Math.round(sum * 1000) / 1000);
        }
        return tablaChi;
    }

    public ObservableList<Intervalo> getIntervalosPoisson() {
        return intervalosPoisson;
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
