package clases.funcionDistribucion;

import clases.soporte.Intervalo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.rmi.MarshalException;
import java.util.Arrays;

public class Normal
{
    private double media;
    private double varianza;
    private double desviacion;
    private int muestra;
    private double[] normal;

    private double min;
    private double max;
    private final ObservableList<Intervalo> intervalosNormal = FXCollections.observableArrayList();

    public Normal()
    {

    }

    public Normal(double media, double desviacion)
    {
        this.media = media;
        this.desviacion = desviacion;
    }



    public double calcularMedia(double desviacion)
    {
        varianza =  Math.pow(desviacion,2);
        return varianza;
    }


    public double calcularVarianza()
    {
        varianza = 1;
        return varianza;
    }


    public double calcularDesviacionEstandar()
    {
        desviacion = Math.sqrt(varianza);
        return desviacion;
    }


    public void funcionDensidad() {

    }

    /**
     * No tiene funcion Acumulada
     */

    public void funcionAcumulada() {

    }


    /**
     * Calculo de la distribucion Normal mediante el metodo
     *                  Box Muller
     *
     * @param media es ingresado por parametros
     * @param desviacion es ingresado por parametros
     * @return retorna un vector Double con los numeros aleatoria con Distribucion
     */
    public double[] box_Muller(double media, double desviacion, int muestra)
    {
        this.media = media;
        this.desviacion = desviacion;

        System.out.println("PRIMERA PARTE \n genera lo random");

        if (muestra % 2 != 0)
        {
            muestra += 1;
        }


        double[] numeros = new double[muestra];
        for (int i = 0; i < muestra; i++)
        {
            numeros[i] = Math.random();
            //System.out.println(String.valueOf(numeros[i]).replace('.',','));
        }

        return generadorBox_Muller(media, desviacion, numeros);
    }

    public double[] box_Muller(double media, double desviacion,double[] numeros)
    {
        this.media = media;
        this.desviacion = desviacion;
        return generadorBox_Muller(media, desviacion, numeros);
    }


    private double[] generadorBox_Muller(double media, double desviacion, double[] numeros)
    {
        this.muestra = numeros.length;
        this.media = media;
        this.desviacion = desviacion;

        normal = new double[muestra];

        double numMenor;
        double numMayor;
        double num1;
        double num2;

        this.min = normal[0] = ((Math.sqrt(-2 * Math.log(numeros[0])) * Math.cos(2 * Math.PI * numeros[1])) * desviacion + media);
        this.max = min;

        System.out.println("Segunda PARTE \n hace la cosa esta");

        for (int i = 0; i < muestra; i+=2) {
            num1 = ((Math.sqrt(-2 * Math.log(numeros[i])) * Math.cos(2 * Math.PI * numeros[i + 1])) * desviacion + media);
            num2 = ((Math.sqrt(-2 * Math.log(numeros[i])) * Math.sin(2 * Math.PI * numeros[i + 1])) * desviacion + media);

            normal[i] = num1;
            normal[i + 1] = num2;

            numMenor = Math.min(num1, num2);
            numMayor = Math.max(num1, num2);

            if (numMenor < min)
                min = numMenor;
            else {
                if (numMayor > max)
                    max = numMayor + 0.0001;
            }
        }
        return normal;
    }


    /**
     * Calculo de la distribucion Normal mediante el metodo
     *                    ConvolucionController
     *
     * @param media es un float recibido por parametros
     * @param desviacion es un float recibido por parametros
     * @param muestra un numero int multiplo de 12
     * @return una serie de numeros normalizados mediante convolucion
     */
    public double[] convolucion(double media, double desviacion, int muestra)
    {
        double[] numeros = new double[muestra * 12];
        this.media = media;
        this.desviacion = desviacion;

        System.out.println("PRIMERA PARTE \n genera lo random\n");

        for (int i = 0; i < muestra*12; i++)
        {
            numeros[i] = Math.random();
        }
        return generaradorConvulcion( media, desviacion, numeros);
    }

    public double[] convolucion(double media, double desviacion, double[] numeros)
    {
        this.media = media;
        this.desviacion = desviacion;
        return generaradorConvulcion( media, desviacion, numeros);
    }

    private double[] generaradorConvulcion(double media, double desviacion, double[] numeros)
    {
        this.muestra = numeros.length/12;

        this.normal = new double[muestra];

        double sum = 0;
        int cont = 1;
        double num;
        this.min = 100000;
        this.max = -100000;

        System.out.println("\nSegunda PARTE \n hace la cosa esta\n");

        int pos = 0;
        for (double numero : numeros)
        {
            if (cont == 12)
            {
                num = (sum - 6) * desviacion + media;
                normal[pos] = num;
                System.out.println("\nnumero "+ pos + " : "+ num);
                sum = 0;
                cont = 0;
                pos++;

                if (num < min)
                    min = num;
                else {
                    if (num > max)
                        max = num + 0.0001;
                }

                System.out.println("\nmin: " + min + "\tmax:" + max);
            }
            sum += numero;
            cont++;
        }
        return normal;
    }



    private void calcularF_Observada()
    {

        for (double n: normal)
        {
            for (Intervalo i: intervalosNormal)
            {
//                System.out.println("Inf: " + i.getInferior()+
//                        " Sup: " + i.getSuperior()+
//                        " N°: " + n);
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
            intervalosNormal.add(new Intervalo(i,(float) Math.round(desde * 10000) / 10000, (float) Math.round((desde + ancho) * 10000) / 10000));
            desde += ancho;
            //System.out.println(intervalosNormal.get(i));
        }


    }

    public void probabilidad(int tam)
    {
        System.out.println("\n------Se calcula la FE------\n");
        double p ;
        for (Intervalo ie: intervalosNormal)
        {
            p = (Math.pow(Math.E,(-0.5*(Math.pow((((ie.getInferior()+ ie.getSuperior())/2)-media),2))))/(desviacion * Math.sqrt(2*Math.PI)))*(ie.getSuperior()-ie.getInferior());

            //System.out.println(p * tam);
            ie.setF_Esp((float)Math.round((p * tam)*10000)/10000);
            System.out.println(ie);
        }

    }


    public ObservableList<Intervalo> crearTablaChi()
    {
        System.out.println("\nAca se crea la table de Chi agrupando filas si la FE en menor a 5\n");

        ObservableList<Intervalo> tablaChi = FXCollections.observableArrayList();
        Intervalo aux = null;

        boolean ban = false;

        for (Intervalo ie: intervalosNormal)
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
                    //System.out.println("Original: " + ie);
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
//
//            int num = last.getNumIt();
//            float sup = aux.getSuperior();
//            int fO = last.getF_Obs() + aux.getF_Obs();
//            float fE = last.getF_Esp() + aux.getF_Esp();


        }

        return tablaChi;
    }

    public ObservableList<Intervalo> calcularChi(int cantIntervalos)
    {
        calcIntervalos(cantIntervalos);
        calcularF_Observada();
        probabilidad(muestra);

        float sum = 0;
        int fO;
        float fE;

        ObservableList<Intervalo> tablaChi = crearTablaChi();
        System.out.println("\n");
        for (Intervalo chi: tablaChi)
        {
            fO = chi.getF_Obs();
            fE = chi.getF_Esp();
            float v = (float) (Math.pow((fO - fE), 2) / fE);
            System.out.println(chi);
            System.out.println("calc: " + sum + "+"+ v + "= " + ((sum + v)) );
            sum += v;
            chi.setChi((float)Math.round(sum * 1000) / 1000);
        }
        return tablaChi;
    }



    public ObservableList<Intervalo> getIntervalosNormal() {
        return intervalosNormal;
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
