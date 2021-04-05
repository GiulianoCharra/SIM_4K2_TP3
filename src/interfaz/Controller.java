package interfaz;

import clases.Chi_Cuadrado;
import clases.Generador;
import clases.Numero;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable
{
    public TextField tf_Semilla;
    public TextField tf_A_Multiplicativa;
    public TextField tf_C_Aditiva;
    public TextField tf_Modulo;
    public TextField tf_K_Valor;
    public TextField tf_G_Valor;
    public Button btGenerar;
    public Button btSiguiente;
    public Button btnSalir;
    public ToggleGroup tg_Metodo;
    public Label lbl_c_Desc;
    public Label lbl_Semilla;
    public Label lbl_A_Multiplicativa;
    public Label lbl_C_aditiva;
    public Label lbl_X0_Desc;
    public Label lbl_a_Desc;
    public Label lbl_m_Desc;
    public Label lbl_k_g_Desc;
    public ToggleGroup tg_SubIntervalos;
    public AnchorPane ap_Base;
    public RadioButton rb_LinealMixto;
    public RadioButton rb_Multiplicativo;
    public RadioButton rb_5;
    public RadioButton rb_10;
    public RadioButton rb_15;
    public RadioButton rb_20;
    public RadioButton rb_Lineal_Chi;
    public RadioButton rb_Sistema;
    public RadioButton rb_Multiplicativo_Chi;
    public ToggleGroup tg_MetodoChi;
    public TextField tf_Muestra;
    public TableView<Chi_Cuadrado.Intervalo> tv_Test_Chi;
    public TableColumn tv_Intervalos;
    public TableColumn<Chi_Cuadrado, Integer> tc_Nr_intervalo;
    public TableColumn<Chi_Cuadrado, Float> tc_menor;
    public TableColumn<Chi_Cuadrado, Float> tc_Max;
    public TableColumn<Chi_Cuadrado, Integer> tc_F_Observada;
    public TableColumn<Chi_Cuadrado, Integer> tc_F_Esperada;
    public TableColumn<Chi_Cuadrado, Float> tc_Chi;
    public Button btn_Calcular_Chi;
    public TableColumn<Numero,Integer> tc_Iteraciones;
    public TableColumn<Numero,Float> tc_N_Generados;
    public TableView<Numero> tv_NumerosGenerados;
    public BarChart<?,?> bc_Resultados;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

    private int x0;
    private int a;
    private int c;
    private int m;
    private int k;
    private int g;
    private boolean metodo = true;//true = lineal -- false = Multiplicativo
    private Chi_Cuadrado chi;
    private Generador generador;
    private ObservableList<Numero> numeros;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        tc_Iteraciones.setCellValueFactory(new PropertyValueFactory<>("iteracion"));
        tc_N_Generados.setCellValueFactory(new PropertyValueFactory<>("numRand"));

        tc_Nr_intervalo.setCellValueFactory(new PropertyValueFactory<>("numIt"));
        tc_menor.setCellValueFactory(new PropertyValueFactory<>("inferior"));
        tc_Max.setCellValueFactory(new PropertyValueFactory<>("superior"));
        tc_F_Observada.setCellValueFactory(new PropertyValueFactory<>("f_Obs"));
        tc_F_Esperada.setCellValueFactory(new PropertyValueFactory<>("f_Esp"));
        tc_Chi.setCellValueFactory(new PropertyValueFactory<>("chi"));


//        bc_Resultados = new BarChart<>(x,y);
//
//        x.setLabel("Intervalo");
//        y.setLabel("Cantidad");

    }


    public void changeMetodo()
    {
        if (rb_Multiplicativo.isSelected())
        {
            lbl_c_Desc.setText("c vale '0', no se usa");
            tf_C_Aditiva.setText("0");
            tf_C_Aditiva.setEditable(false);
            metodo = false;
       }
        else
        {
            lbl_c_Desc.setText("c es una constante aditiva");
            tf_C_Aditiva.setEditable(true);
            metodo = true;
        }
    }


    public void calcular_K()
    {
        a = Integer.parseInt(tf_A_Multiplicativa.getText());
        if (rb_LinealMixto.isPressed())
            {
                k = (a-1)/4;
            }
        else
            k = (a-3)/8;
        tf_K_Valor.setText(String.valueOf(k));
    }

    public void calcular_G()
    {
        m = Integer.parseInt(tf_Modulo.getText());
        g = (int)(Math.log(m)/Math.log(2));
        tf_G_Valor.setText(String.valueOf(g));
    }

    public void calcular_A_multiplicativa()
    {
        k = Integer.parseInt(tf_K_Valor.getText());
        if (metodo)
        {
            a = 1+4*k;
        }
        else
            a = 3+8*k;
        tf_A_Multiplicativa.setText(String.valueOf(a));
    }

    public void calcular_Modulo()
    {
        g = Integer.parseInt(tf_G_Valor.getText());
        m = (int) Math.pow(2,g);
        tf_Modulo.setText(String.valueOf(m));
    }

    public void generarNumeros()
    {
        numeros = FXCollections.observableArrayList();

        x0 = Integer.parseInt(tf_Semilla.getText());
        c = Integer.parseInt(tf_C_Aditiva.getText());
        float num;

        generador = new Generador(x0, a, c, m);
        if (rb_LinealMixto.isSelected())
        {
            for (int i = 0; i < 20; i++)
            {
                num = generador.nextNumeroLineal();
                numeros.add(new Numero(i,num));
            }
        }
        else
            for (int i = 0; i < 20; i++)
            {
                 num = generador.nextNumeroMultiplcativo();
                 numeros.add(new Numero(i,num));
            }
        updatetable();
    }

    public void generarSiguiente()
    {
        float num = generador.nextNumeroLineal();
        numeros.add(new Numero(numeros.size(),num));
        updatetable();
    }


    public void updatetable()
    {
        tv_NumerosGenerados.setItems(numeros);
    }

    public void close()
    {
        Stage stage = (Stage)btnSalir.getScene().getWindow();
        stage.close();
    }

    public void calcularChi()
    {

        //ObservableList<Float>  vecMuestra= FXCollections.observableArrayList();

        int muestra = Integer.parseInt( tf_Muestra.getText());
        int numInter= Integer.parseInt(((RadioButton) tg_SubIntervalos.getSelectedToggle()).getText());

        chi = new Chi_Cuadrado(muestra, numInter);

        float[] vecMuestra = new float[muestra];



        if (rb_Sistema.isSelected())
        {
            for (int i = 0; i < muestra; i++)
            {
                vecMuestra[i] =  (float)Math.round(Math.random()*10000)/10000;
                //vecMuestra.add((float)Math.round(Math.random()*10000)/10000);
            }
        }
        else
            if (generador!= null)
            {
                generador.reiniciar();
                if (rb_Lineal_Chi.isSelected())
                {
                    for (int i = 0; i < muestra; i++)
                    {
                        vecMuestra[i] = generador.nextNumeroLineal();
                        //vecMuestra.add(generador.nextNumeroLineal());
                    }
                } else
                    {
                        if (rb_Multiplicativo_Chi.isSelected()) {
                            for (int i = 0; i < muestra; i++)
                            {
                                vecMuestra[i] = generador.nextNumeroMultiplcativo();
                                //vecMuestra.add(generador.nextNumeroMultiplcativo());
                            }
                        }
                    }
            }

        chi.calcularChi(vecMuestra);
        tv_Test_Chi.setItems(chi.getIntervalos());

        bc_Resultados.getData().clear();

        //XYChart.Series[] fObs = new  XYChart.Series[((ObservableList)chi.getIntervalos()).size()];
        XYChart.Series fObs = new XYChart.Series();
        XYChart.Series fEsp = new XYChart.Series();

        RadioButton metodo = (RadioButton) tg_MetodoChi.getSelectedToggle();

        fObs.setName(metodo.getText());
        fEsp.setName("Esperada");

        for (Chi_Cuadrado.Intervalo i :chi.getIntervalos())
        {
            fObs.getData().add(new XYChart.Data(""+i.getNumIt(), i.getF_Obs()));
            fEsp.getData().add(new XYChart.Data(""+i.getNumIt(), i.getF_Esp()));
        }

        bc_Resultados.getData().addAll(fObs,fEsp);
        //chi.mostrar();

    }
}
