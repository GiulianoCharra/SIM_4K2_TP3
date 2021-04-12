package funcionDistribucion;

public class Normal
{
    private double media;
    private double varianza;
    private double desviacion;
    private double[] normal;

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
    public static double[] box_Muller(double media, double desviacion, int muestra)
    {

        System.out.println("PRIMERA PARTE \n genera lo random");
        double[] numeros = new double[muestra*2];
        for (int i = 0; i < muestra*2; i++)
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


    private static double[] generadorBox_Muller(double media, double desviacion, double[] numeros)
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
     *                    Convolucion
     *
     * @param media es un float recibido por parametros
     * @param desviacion es un float recibido por parametros
     * @param muestra un numero int multiplo de 12
     * @return una serie de numeros normalizados mediante convolucion
     */
    public static double[] convolucion(double media, double desviacion, int muestra)
    {
        double[] numeros = new double[muestra*12];

        System.out.println("PRIMERA PARTE \n genera lo random\n");

        for (int i = 0; i < muestra*12; i++)
        {
            numeros[i] = Math.random();
        }
        return generaradorConvulcion( media, desviacion, numeros);
    }

    public static double[] convolucion(double media, double desviacion, double[] numeros)
    {
        return generaradorConvulcion( media, desviacion, numeros);
    }

    private static double[] generaradorConvulcion(double media, double desviacion, double[] numeros)
    {
        int tam = numeros.length/12;

        double[] convolucion = new double[tam];

        double sum = 0;
        int cont = 1;

        System.out.println("\nSegunda PARTE \n hace la cosa esta\n");

        int pos = 0;
        for (double numero : numeros)
        {
            if (cont == 12) {
                convolucion[pos] = (sum - 6) * desviacion + media;
                sum = 0;
                cont = 0;
                pos++;
            }
            sum += numero;
            cont++;
        }
        return convolucion;
    }
}
