package clases.funcionDistribucion;

import clases.soporte.Intervalo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Uniforme
{
    private float media;
    private float varianza;
    private float desviacion;
    private float a;
    private float b;

    private double[] uniforme;
    private int muestra;
    private double min;
    private double max;
    private final ObservableList<Intervalo> intervalosUniforme = FXCollections.observableArrayList();


    public Uniforme() {

    }

    public Uniforme(float a, float b, double[] numeros)
    {
        this.a = a;
        this.b = b;
        this.uniforme = numeros;
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
        return generadorUnitario(this.a, this.b,this.uniforme);
    }

    public double[] generar(float a, float b, double[]numeros)
    {
        this.muestra = numeros.length;
        this.uniforme = numeros;
        return generadorUnitario(a, b, numeros);
    }

    public double[] generar(float a, float b, int muestra)
    {
        this.uniforme = new double[muestra];
        this.muestra = muestra;

        System.out.println("PRIMERA PARTE \n genera lo random\n");

        for (int i = 0; i < muestra; i++)
        {
            uniforme[i] = Math.random();
            //System.out.println(String.valueOf(uniforme[i]).replace('.',','));
        }
        return generadorUnitario(a,b, uniforme);
    }

    private double[] generadorUnitario(float a, float b, double[] numeros)
    {
        int muestra = numeros.length;
        this.uniforme = new double[muestra];

        double num = a + numeros[0]*(b-a);
        this.min = num;
        this.max = min;

        System.out.println("\nSegunda PARTE \nhace el coso de unitario\n");
        for (int i = 0; i < muestra; i++)
        {
            num = a + numeros[i]*(b-a);
            uniforme[i] = num;

            if (num < min)
                min = num;
            else
            if (num > max)
                max = num + 0.0001;
        }
        return uniforme;
    }

    private void calcularF_Observada()
    {
        for (double n: uniforme)
        {
            for (Intervalo i: intervalosUniforme)
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
        float fE = frecuenciaEsperada(cantIntervalos);

        for (int i = 0; i < cantIntervalos; i++)
        {
            intervalosUniforme.add(new Intervalo(i,(float) Math.round(desde * 10000) / 10000, (float) Math.round((desde + ancho) * 10000) / 10000,fE));
            desde += ancho;
            System.out.println(intervalosUniforme.get(i));
        }


    }

    public float frecuenciaEsperada(int cantIntervalos)
    {
       return (float) muestra/cantIntervalos;
    }


    public ObservableList<Intervalo> crearTablaChi()
    {
        ObservableList<Intervalo> tablaChi = FXCollections.observableArrayList();
        Intervalo aux = new Intervalo();

        boolean ban = false;

        for (Intervalo ie: intervalosUniforme)
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
                //System.out.println("despues de actualizar: " + intervalosNormal.get(i));
                System.out.println("Actualizo el auxiliar: " + aux);

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
        return tablaChi;
    }

    public ObservableList<Intervalo> calcularChi(int cantIntervalos)
    {
        calcIntervalos(cantIntervalos);
        calcularF_Observada();

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

    public ObservableList<Intervalo> getIntervalosUni() {
        return intervalosUniforme;
    }
}
