package clases;

import clases.funcionDistribucion.Exponencial;
import clases.funcionDistribucion.Normal;
import clases.funcionDistribucion.Poisson;
import clases.funcionDistribucion.Uniforme;
import clases.soporte.Intervalo;

import java.util.ArrayList;
import java.util.List;

public class mainPrueba {

    public static void main(String[] args)
    {
/*

        Chi_Cuadrado chi = new Chi_Cuadrado(10,100,"Sistema");
        chi.calcularChi();
        chi.mostrar();
*/
        double[] vec = null;
        Uniforme uniforme = new Uniforme();
        vec= uniforme.generar(-10,8,100);

//      vec= Exponencial.generar(5,100);
//        vec= Normal.box_Muller(-2,0.2,50);
//        vec= Normal.convolucion(-2,0.2,100);
//        Poisson poisson = new Poisson();
//
//        System.out.println(poisson.factorial(19));
//
//        vec = poisson.generar(15,50);

        StringBuilder str = new StringBuilder();
        for (double n: vec)
        {
            str.append(String.valueOf(n).replace('.',',')).append("\n");
        }
        System.out.println(str.toString());

        //poisson.calcularChi();


        /*Intervalo aux = new Intervalo();
        List<Intervalo> vecFinal = new ArrayList<>();
        boolean ban = false;
        for (Intervalo in: ListaIntervalos)
        {
           if (!ban)
           {
               if (in.getValor() >= 5)
                   vecFinal.add(in);
               else
               {
                   aux = in;
                   ban = true;
               }
           }
           else
           {
               aux.setValor(aux.getValor() + in.getValor());

               if (aux.getValor() >= 5)
               {
                   vecFinal.add(aux);
                   ban = false;
               }
           }

        }*/
    }
}
