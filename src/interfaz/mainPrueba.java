package interfaz;


import clases.Chi_Cuadrado;
import funcionDistribucion.Exponencial;
import funcionDistribucion.Normal;
import funcionDistribucion.Poisson;
import funcionDistribucion.Uniforme;

import java.util.Arrays;

public class mainPrueba {

    public static void main(String[] args)
    {

//        Chi_Cuadrado chi = new Chi_Cuadrado(10,100,"Sistema");
//        chi.calcularChi();
//        chi.mostrar();
        double[] vec;

        //vec= Uniforme.generar(5,8,100);
        //vec= Exponencial.generar(5,100);
        //vec= Normal.box_Muller(-2,0.2,50);
        vec= Normal.convolucion(-2,0.2,100);
//        vec = new double[100];
//        for (int i = 0; i < 100; i++) {
//            vec[i] = Poisson.generar(10);
//        }

        StringBuilder str = new StringBuilder();
        for (double n: vec)
        {
            str.append(String.valueOf(n).replace('.',',')).append("\n");
        }
        System.out.println(str.toString());
    }
}
