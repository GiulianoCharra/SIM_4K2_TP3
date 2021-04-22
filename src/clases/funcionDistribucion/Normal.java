package clases.funcionDistribucion;

import clases.soporte.Intervalo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.rmi.MarshalException;

public class Normal
{
    private double media;
    private double varianza;
    private double desviacion;
    private int muestra;
    private double[] normal;

    private double min;
    private double max;
    private ObservableList<Intervalo> intervalosNormal = FXCollections.observableArrayList();

    public Normal() {

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

        System.out.println("PRIMERA PARTE \n genera lo random");

        System.out.println("Primera muestra: " + muestra);
        if (muestra % 2 == 1)
        {   this.muestra = muestra;
            muestra += 1;
        }
        System.out.println("Nueva muestra: " + muestra);

        double[] numeros = new double[muestra];
        for (int i = 0; i < muestra; i++)
        {
            numeros[i] = Math.random();
            System.out.println(String.valueOf(numeros[i]).replace('.',','));
        }

        return generadorBox_Muller(media, desviacion, numeros);
    }

    public double[] box_Muller(double media, double desviacion,double[] numeros)
    {
        return generadorBox_Muller(media, desviacion, numeros);
    }


    private double[] generadorBox_Muller(double media, double desviacion, double[] numeros)
    {
        int tam = numeros.length;
        double[ ]normal = new double[tam];
        System.out.println("Segunda PARTE \n hace la cosa esta");
        for (int i = 0; i < tam; i+=2)
        {
            normal[i] = ((Math.sqrt(-2 * Math.log(numeros[i])) * Math.cos(2 * Math.PI * numeros[i+1])) * desviacion + media);
            normal[i+1] = ((Math.sqrt(-2 * Math.log(numeros[i])) * Math.sin(2 * Math.PI * numeros[i+1])) * desviacion + media);
        }
        System.out.println(tam);
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
        int tam = numeros.length/12;

        this.normal = new double[tam];

        double sum = 0;
        int cont = 1;

        System.out.println("\nSegunda PARTE \n hace la cosa esta\n");

        int pos = 0;
        for (double numero : numeros)
        {
            if (cont == 12)
            {
                normal[pos] = (sum - 6) * desviacion + media;
                sum = 0;
                cont = 0;
                pos++;
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
                System.out.println("Inf: " + i.getInferior()+
                        " Sup: " + i.getSuperior()+
                        " N°: " + n);
                if (n <= i.getSuperior())
                {
                    System.out.println("aceptado");
                    i.contar();
                    break;
                }
            }
        }
    }

    public void  calcIntervalos(int cantIntervalos, double[] vecE)
    {

        double dif = max - min;
        double ancho = dif/cantIntervalos;
        System.out.println("ancho: " + ancho);
        double desde = min;

        for (int i = 0; i < cantIntervalos; i++)
        {
            intervalosNormal.add(new Intervalo(i,(float) Math.round(desde * 10000) / 10000, (float) Math.round((desde + ancho) * 10000) / 10000));
            desde += ancho;
            System.out.println(intervalosNormal.get(i));
        }


    }

    public void probabilidad(int tam)
    {
        double p ;
        for (Intervalo ie: intervalosNormal)
        {

            p = (Math.pow(Math.E,(-0.5*(Math.pow((((ie.getInferior()+ ie.getSuperior())/2)-media),2))))/(desviacion * Math.sqrt(2*Math.PI)))*(ie.getSuperior()-ie.getInferior());                                                                                                ;
            ie.setF_Esp((float)Math.round((p * tam)*10000)/10000);
            System.out.println("\n frecuencia esperada: " + ie);
        }

    }


    public ObservableList<Intervalo> crearTablaChi()
    {
        ObservableList<Intervalo> tablaChi = FXCollections.observableArrayList();
        Intervalo aux = new Intervalo();


        boolean ban = false;

        for (Intervalo ie: intervalosNormal)
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

    public ObservableList<Intervalo> calcularChi(int cantIntervalos, double[] vecE)
    {
        calcIntervalos(cantIntervalos, vecE);
        calcularF_Observada();
        probabilidad(vecE.length);

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
