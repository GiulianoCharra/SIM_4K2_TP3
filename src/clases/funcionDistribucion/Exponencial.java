package clases.funcionDistribucion;

import clases.soporte.Intervalo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Exponencial
{
    private float lambda;
    private float media;
    private float varianza;
    private double[] exponencial;
    private int muestra;
    private double min;
    private double max;
    private final ObservableList<Intervalo> intervalosEXP = FXCollections.observableArrayList();

    public Exponencial() {
    }

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


    public double[] generar(float lambda, double[] numeros)
    {
        return generadorExponencial(lambda, numeros);
    }
    public double[] generar(float lambda, int muestra)
    {
        double[] numeros = new double[muestra];

        System.out.println("PRIMERA PARTE \n genera lo random\n"); // Control de dasd

        for (int i = 0; i < muestra; i++)
        {
            numeros[i] = Math.random();
            //System.out.println(String.valueOf(numeros[i]).replace('.',','));
        }
        return generadorExponencial(lambda, numeros);
    }
    public double[] generar(double[] numeros)
    {
        return generadorExponencial(lambda, numeros);
    }
    public double[] generar(int muestra)
    {
        double[] numeros = new double[muestra];

        System.out.println("PRIMERA PARTE \n genera lo random\n"); // Control de dasd

        for (int i = 0; i < muestra; i++)
        {
            numeros[i] = Math.random();
            //System.out.println(String.valueOf(numeros[i]).replace('.',','));
        }
        return generadorExponencial(lambda, numeros);
    }


    private double[] generadorExponencial(float lambda, double[] numeros)
    {

        this.lambda = lambda;
        int muestra = numeros.length;

        System.out.println("Segunda PARTE \n hace la cosa esta");

        this.exponencial = new double[muestra];
        double num = - 1/lambda * Math.log( 1 - numeros[0]);

        this.min = num;
        this.max = min;

        for (int i = 0; i < muestra; i++)
        {
            num = (float) (- 1/lambda * Math.log( 1 - numeros[i]));
            //System.out.println(i +":  "+ num);
            exponencial[i] = num;

            if (num < min)
                min = num;
            else
            if (num > max)
                max = num + 0.0001;
        }
        System.out.println("\nmin: "+ min + " MAx: " + max);

        return exponencial;
    }


    private void calcularF_Observada()
    {
        for (double n: exponencial)
        {
            for (Intervalo i: intervalosEXP)
            {
                /*System.out.println("Inf: " + i.getInferior()+
                                    " Sup: " + i.getSuperior()+
                                    " N°: " + n);*/
                if (n <= i.getSuperior())
                {
                    //System.out.println("aceptado");
                    i.contar();
                    break;
                }
            }
        }
    }

    public void  calcIntervalos(int cantIntervalos)
    {

        double dif = max - min;
        double ancho = dif/cantIntervalos;
        System.out.println("ancho: " + ancho);
        double desde = min;

        for (int i = 0; i < cantIntervalos; i++)
        {
            intervalosEXP.add(new Intervalo(i,(float) Math.round(desde * 10000) / 10000, (float) Math.round((desde + ancho) * 10000) / 10000));
            desde += ancho;
            System.out.println(intervalosEXP.get(i));
        }


    }

    public void probabilidadAcumulada(int tam)
    {
        double Pac ;
        for (Intervalo ie: intervalosEXP)
        {
            Pac = 1-Math.pow(Math.E,(-lambda*ie.getSuperior()))-(1-Math.pow(Math.E,(-lambda*ie.getInferior())))                                                                                                ;
            ie.setF_Esp((float)Math.round((Pac * tam)*10000)/10000);
            System.out.println("\n frecuencia esperada: " + ie);
        }

    }


    public ObservableList<Intervalo> crearTablaChi()
    {
        ObservableList<Intervalo> tablaChi = FXCollections.observableArrayList();
        Intervalo aux = new Intervalo();


        boolean ban = false;

        for (Intervalo ie: intervalosEXP)
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
                    //System.out.println("\nAsigno  el auxiliar: " + aux);
                }
            }
            else
            {
                //System.out.println("\nAntes de actualizar: " + intervalosNormal.get(i));
                float sup = ie.getSuperior();
                int fO = ie.getF_Obs();
                float fE = ie.getF_Esp();
                //System.out.println("Asigno de variablesr: " + intervalosNormal.get(i));
                aux.setSuperior(sup);
                aux.setF_Obs(aux.getF_Obs() + fO);
                aux.setF_Esp(aux.getF_Esp() + fE);
                //System.out.println("despues de actualizar: " + intervalosNormal.get(i));
                //System.out.println("Actualizo el auxiliar: " + aux);

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

        for (Intervalo e: tablaChi
             ) {
            System.out.println("\n"+e);

        }

        return tablaChi;
    }

    public ObservableList<Intervalo> calcularChi(int cantIntervalos, double[] vecE)
    {
        calcIntervalos(cantIntervalos);
        calcularF_Observada();
        probabilidadAcumulada(vecE.length);

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



    public ObservableList<Intervalo> getIntervalosEXP() {
        return intervalosEXP;
    }


    /*public static class intervaloExp
    {
        private int numIt;
        private float inferior;
        private float superior;
        private int f_Obs;
        private float f_Esp;
        private float chi;

        public intervaloExp() {
        }

        public intervaloExp(int num, float inferior, float superior)
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

        public float getF_Esp() {
            return f_Esp;
        }

        public void setF_Esp(float f_Esp) {
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

