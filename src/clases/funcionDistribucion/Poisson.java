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
    private final ObservableList<Intervalo> intervalosPoisson = FXCollections.observableArrayList();


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
        for (int n: poisson)
        {
            intervalosPoisson.get(n-min).contar();
//            for (Intervalo i: intervalosPoisson)
//            {
//                System.out.println("Inf: " + i.getInferior()+
//                        " Sup: " + i.getSuperior()+
//                        " N°: " + n);
//                if (n == i.getNumIt())
//                {
//                    System.out.println("aceptado");
//                    i.contar();
//                    break;
//                }
//            }
        }
    }

    public void  calcIntervalos()
    {
        int dif = max - min;
        int pos = min;
        System.out.println("min: " + min + " max: " + max + " dif: " + dif);
        for (int i = 0; i <= dif; i++)
        {
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
        System.out.println("\n----Aca se calcula la FE------\n ");
        double p ;
        for (Intervalo ie: intervalosPoisson)
        {
            //System.out.println("\nintervalo: " + ie.getNumIt() + " lambda: " + lambda + " factorial: " + factorial(ie.getNumIt()));
            double fact = factorial(ie.getNumIt());
            p = (Math.pow(lambda,ie.getNumIt())* Math.pow(Math.E,-lambda))/fact;

            //System.out.println(p*tam);
            ie.setF_Esp(Math.round(p * tam));

            System.out.println("frecuencia esperada: " + ie);
        }
    }


    public ObservableList<Intervalo> crearTablaChi()
    {
        System.out.println("\n-----Aca creo la tabla con las filar don FE >=5-------\n");

        ObservableList<Intervalo> tablaChi = FXCollections.observableArrayList();
        Intervalo aux = new Intervalo();

        boolean ban = false;

        for (Intervalo ie: intervalosPoisson)
        {
            if (!ban)
            {
                if (ie.getF_Esp()>= 5)
                {
                    tablaChi.add(new Intervalo(ie));
                }
                else
                {
                    aux = new Intervalo(ie);
                    ban = true;
                    System.out.println("\nAsigno  el auxiliar: " + aux);

                    System.out.println("Intervalo origen: " + ie);
                }
            }
            else
            {
                //System.out.println("\nAntes de actualizar: " + intervalosNormal.get(i));
                float sup = ie.getSuperior();
                int fO = ie.getF_Obs();
                float fE = ie.getF_Esp();
                //System.out.println(aux.getF_Esp() + " " + fE);
                aux.setSuperior(sup);
                aux.setF_Obs(aux.getF_Obs() + fO);
                aux.setF_Esp(aux.getF_Esp() + fE);
                System.out.println("Actualize el auxiliar: " + aux);

                if (aux.getF_Esp()>= 5)
                {
                    tablaChi.add(aux);
                    aux = null;
                    ban = false;
                    //System.out.println("Limpio el Auxiliar: " + aux);
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
//        int j=0;
//        for (Intervalo e: tablaChi
//        ) {
//            System.out.println("Fila Chi Final " + j +": "+e);
//            j++;
//        }

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
            //System.out.println("fObse:" +fO+ " fEsp: " + fE+" nuevo: " + sum + "+"+ v + "= " + ((sum + v)) );
            sum += v;
            chi.setChi((float)Math.round(sum * 1000) / 1000);
        }
        return tablaChi;
    }

    public ObservableList<Intervalo> getIntervalosPoisson() {
        return intervalosPoisson;
    }
}
