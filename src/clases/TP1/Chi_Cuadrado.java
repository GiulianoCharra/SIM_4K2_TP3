package clases.TP1;

import clases.soporte.Intervalo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Chi_Cuadrado
{
    private final int cantIntervalos;
    private final int muestra;

    private final ObservableList<Intervalo> intervalos;

    public Chi_Cuadrado(int muestra, int cantIntervalos)
    {
        this.cantIntervalos = cantIntervalos;
        this.muestra = muestra;
        this.intervalos = FXCollections.observableArrayList();
    }


    public void calcIntervalos()
    {
       float tam = (float) 1 /cantIntervalos;
       float inf = 0;
       float sup = tam;
        for (int i = 1; i <= cantIntervalos; i++)
        {
            intervalos.add(new Intervalo(i, inf,(float)Math.round(sup*100)/100));
            inf = (float) Math.round((sup + 0.0001)*10000)/10000;
            sup += tam;
        }
    }



    private void f_Esperada()
    {
        int fE = muestra/cantIntervalos;
        for (Intervalo i: intervalos)
        {
            i.setF_Esp(fE);
        }
    }

    private void f_Observada(float[] numeros)
    {
        for (Float n: numeros)
        {
            for (Intervalo i: intervalos)
            {
                if (n>= i.getInferior() && n<= i.getSuperior())
                {
                    i.contar();
                    break;
                }
            }
        }
    }

    public ObservableList<Intervalo> crearTablaChi()
    {
        System.out.println("\n----Aca se crea la table de Chi agrupando filas si la FE en menor a 5------\n");

        ObservableList<Intervalo> tablaChi = FXCollections.observableArrayList();
        Intervalo aux = null;

        boolean ban = false;

        for (Intervalo ie: intervalos)
        {
            System.out.println(ie);
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
                }
            }
            else
            {
                float sup = ie.getSuperior();
                int fO = ie.getF_Obs();
                float fE = ie.getF_Esp();
                aux.setSuperior(sup);
                aux.setF_Obs(aux.getF_Obs() + fO);
                aux.setF_Esp(aux.getF_Esp() + fE);

                if (aux.getF_Esp()>= 5)
                {
                    tablaChi.add(aux);
                    aux = null;
                    ban = false;
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

    public ObservableList<Intervalo> calcularChi(float[] numeros)
    {
        calcIntervalos();
        f_Esperada();
        f_Observada(numeros);

        float sum = 0;
        int fO;
        float fE;

        ObservableList<Intervalo> tablaChi = crearTablaChi();

        System.out.println("\n-----SE crea la tabla Final de Chi-------\n");
        for (Intervalo n: tablaChi)
        {
            fO = n.getF_Obs();
            fE = n.getF_Esp();
            float v = (float) (Math.pow((fO - fE), 2) / fE);
            System.out.println(n);
            //System.out.println("fObse:" +fO+ " fEsp: " + fE+" nuevo: " + sum + "+"+ v + "= " + ((sum+v)) );
            sum += v;
            n.setChi((float)Math.round(sum*1000)/1000);
        }
        return tablaChi;
    }

    public void mostrar()
    {
        for (Intervalo i: intervalos)
        {
            System.out.println(i.toString());
        }
    }

    public ObservableList<Intervalo> getIntervalos()
    {
        return intervalos;
    }

}
