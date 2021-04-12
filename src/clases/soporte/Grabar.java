package clases.soporte;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class Grabar
{

    private String arch = "resultados.dsv";

    public Grabar()
    {
    }

    public Grabar(String nom)
    {
        arch = nom + "NumerosAleatorios.xslx";
        System.out.println(arch);
    }

    public void write (String sl) throws NullPointerException
    {
        try
        {
            FileOutputStream fos = new FileOutputStream(arch);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeChars(sl);

            oos.flush();
            fos.close();
        }
        catch ( Exception e )
        {
            throw new NullPointerException("No se pudo grabar la lista...");
        }
    }
}
