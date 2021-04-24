package clases;

import clases.funcionDistribucion.Exponencial;
import clases.funcionDistribucion.Normal;
import clases.funcionDistribucion.Poisson;
import clases.funcionDistribucion.Uniforme;

public class mainPrueba {

    public static void main(String[] args)
    {
/*

        Chi_Cuadrado chi = new Chi_Cuadrado(10,100,"Sistema");
        chi.calcularChi();
        chi.mostrar();
*/
        int[] vec = null;
//
//        vec= Uniforme.generar(5,8,100);
//      vec= Exponencial.generar(5,100);
//        vec= Normal.box_Muller(-2,0.2,50);
//        vec= Normal.convolucion(-2,0.2,100);
        Poisson poisson = new Poisson();

        System.out.println(poisson.factorial(19));

        vec = poisson.generar(15,50);

        StringBuilder str = new StringBuilder();
        for (int n: vec)
        {
            str.append(String.valueOf(n).replace('.',',')).append("\n");
        }
        System.out.println(str.toString());

        poisson.calcularChi();
    }
}
