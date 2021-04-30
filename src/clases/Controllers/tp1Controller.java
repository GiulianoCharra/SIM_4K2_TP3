package clases.Controllers;

import clases.TP1.Chi_Cuadrado;
import clases.TP1.Generador;
import clases.TP1.Numero;
import clases.soporte.Intervalo;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class tp1Controller implements Initializable {
    public TextField tf_Semilla;
    public TextField tf_A_Multiplicativa;
    public TextField tf_C_Aditiva;
    public TextField tf_Modulo;
    public TextField tf_K_Valor;
    public TextField tf_G_Valor;
    public TextField tf_Muestra;

    public Button btGenerar;
    public Button btSiguiente;
    public Button btn_Calcular_Chi;

    public ToggleGroup tg_Metodo;
    public ToggleGroup tg_MetodoChi;
    public ToggleGroup tg_SubIntervalos;

    public Label lbl_c_Desc;
    public Label lbl_Semilla;
    public Label lbl_A_Multiplicativa;
    public Label lbl_C_aditiva;
    public Label lbl_X0_Desc;
    public Label lbl_a_Desc;
    public Label lbl_m_Desc;
    public Label lbl_k_g_Desc;

    public RadioButton rb_LinealMixto;
    public RadioButton rb_Multiplicativo;
    public RadioButton rb_5;
    public RadioButton rb_10;
    public RadioButton rb_15;
    public RadioButton rb_20;
    public RadioButton rb_Lineal_Chi;
    public RadioButton rb_Sistema;
    public RadioButton rb_Multiplicativo_Chi;

    public TableView<Numero> tv_NumerosGenerados;
    public TableColumn<Object, Object> tc_Iteraciones;
    public TableColumn<Object, Object>  tc_N_Generados;

    public TableView<Intervalo> tv_Test_Chi;
    public TableColumn tv_Intervalos;
    public TableColumn<Object, Object> tc_Nr_intervalo;
    public TableColumn<Object, Object> tc_menor;
    public TableColumn<Object, Object> tc_Max;
    public TableColumn<Object, Object> tc_F_Observada;
    public TableColumn<Object, Object> tc_F_Esperada;
    public TableColumn<Object, Object> tc_Chi;

    public BarChart<?, ?> bc_Resultados;

    public AnchorPane ap_Base_tp1;
    public AnchorPane ap_Chi_Cuadrado;
    public AnchorPane ap_Generador;

    private Generador generador;
    private ObservableList<Numero> numeros;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        tp1Controller sc = this;
        formatNodes(sc.ap_Base_tp1);

        tc_Iteraciones.setCellValueFactory(new PropertyValueFactory<>("iteracion"));
        tc_N_Generados.setCellValueFactory(new PropertyValueFactory<>("numRand"));

        tc_Nr_intervalo.setCellValueFactory(new PropertyValueFactory<>("numIt"));
        tc_menor.setCellValueFactory(new PropertyValueFactory<>("inferior"));
        tc_Max.setCellValueFactory(new PropertyValueFactory<>("superior"));
        tc_F_Observada.setCellValueFactory(new PropertyValueFactory<>("f_Obs"));
        tc_F_Esperada.setCellValueFactory(new PropertyValueFactory<>("f_Esp"));
        tc_Chi.setCellValueFactory(new PropertyValueFactory<>("chi"));
    }


    public void formatNodes(Node node) {
        String t = node.getTypeSelector();

        if (t.equals("AnchorPane") || t.equals("Pane")) {
            for (Node n : ((Pane) node).getChildren()) {
                formatNodes(n);
            }
        }
        if (t.equals("TextField")) {
            formatTextFiield((TextField) node);
        }

    }

    private boolean isEmpty()
    {
        if (tf_Semilla.getText().isEmpty() ||
            tf_A_Multiplicativa.getText().isEmpty() ||
            tf_C_Aditiva.getText().isEmpty() ||
            tf_Modulo.getText().isEmpty())
        {
            String msg ="Mi buen amigo me temo que usted se ah olvidado de ingresar un dato de vital impotancia, " +
                "\n" + "el cual es necesario para poder realizar esta actividad, " +
                "\n" + "seria tan amable de ingresar el dato faltante y asi poder continuar con mi trabajo" +
                "\n" + "Muchas Gracias\"" ;
            JOptionPane.showMessageDialog(null, msg, "Falta", JOptionPane.PLAIN_MESSAGE);
            return true;
        }
        return false;
    }

    private void formatTextFiield(TextField tf) {
        tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue observableValue, String o, String num) {
                if (!num.matches("\\d*")) {
                    tf.setText(num.replaceAll("[^\\d]", ""));
                }
            }

        });
    }

    private void formatTextCollumn(TableColumn tc) {
        tc.setCellFactory(c -> new TableCell<Object, Double>() {
            @Override
            protected void updateItem(Double num, boolean empty) {
                super.updateItem(num, empty);
                if (num == null || empty) {
                    setText(null);

                } else {
                    setText(String.format("%.4f", num));
                }
            }
        });
    }

    public void changeMetodo() {
        if (rb_Multiplicativo.isSelected()) {
            lbl_c_Desc.setText("c no se usa");
            tf_C_Aditiva.setText("0");
            tf_C_Aditiva.setEditable(false);
        } else {
            lbl_c_Desc.setText("c es una constante aditiva");
            tf_C_Aditiva.setEditable(true);
        }
    }


    public void calcular_K() {
        if (tf_A_Multiplicativa.getText().isEmpty())
            return;

        int a = Integer.parseInt(tf_A_Multiplicativa.getText());
        int k;

        if (rb_LinealMixto.isSelected()) {
            k = (a - 1) / 4;
        } else
            k = (a - 3) / 8;
        tf_K_Valor.setText(String.valueOf(k));
    }

    public void calcular_G() {
        if (tf_Modulo.getText().isEmpty())
            return;

        int m = Integer.parseInt(tf_Modulo.getText());
        int g = (int) (Math.log(m) / Math.log(2));

        tf_G_Valor.setText(String.valueOf(g));
    }

    public void calcular_A_multiplicativa() {
        if (tf_K_Valor.getText().isEmpty())
            return;

        int k = Integer.parseInt(tf_K_Valor.getText());
        int a;

        if (rb_LinealMixto.isSelected()) {
            a = 1 + 4 * k;
        } else
            a = 3 + 8 * k;

        tf_A_Multiplicativa.setText(String.valueOf(a));
    }

    public void calcular_Modulo() {
        if (tf_G_Valor.getText().isEmpty())
            return;

        int g = Integer.parseInt(tf_G_Valor.getText());
        int m = (int) Math.pow(2, g);

        tf_Modulo.setText(String.valueOf(m));
    }

    public void generarNumeros() {

        if (isEmpty())
            return;

        numeros = FXCollections.observableArrayList();

        int x0 = (int) Float.parseFloat(tf_Semilla.getText());
        int a = (int) Float.parseFloat(tf_A_Multiplicativa.getText());
        int c = (int) Float.parseFloat(tf_C_Aditiva.getText());
        int m = (int) Float.parseFloat(tf_Modulo.getText());

        float num;


        generador = new Generador(x0, a, c, m);

        if (rb_LinealMixto.isSelected()) {
            for (int i = 0; i < 20; i++) {
                num = generador.nextNumeroLineal();
                numeros.add(new Numero(i, num));
            }
        } else
            for (int i = 0; i < 20; i++) {
                num = generador.nextNumeroMultiplcativo();
                numeros.add(new Numero(i, num));
            }
        updatetable();
    }

    public void generarSiguiente() {
        float num = generador.nextNumeroLineal();
        numeros.add(new Numero(numeros.size(), num));
        updatetable();
    }


    public void updatetable() {
        tv_NumerosGenerados.setItems(numeros);
    }


    public void calcularChi() {

        if (tf_Muestra.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe cargar una muestra para generar los numeros", "Falta", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        //ObservableList<Float>  vecMuestra= FXCollections.observableArrayList();

        int muestra = Integer.parseInt(tf_Muestra.getText());
        int numInter = Integer.parseInt(((RadioButton) tg_SubIntervalos.getSelectedToggle()).getText());


        Chi_Cuadrado chi = new Chi_Cuadrado(muestra, numInter);
        float[] vecMuestra = new float[muestra];

        if (rb_Sistema.isSelected()) {
            for (int i = 0; i < muestra; i++) {
                vecMuestra[i] = (float) Math.round(Math.random() * 10000) / 10000;
            }
        } else if (generador != null) {
            generador.reiniciar();
            if (rb_Lineal_Chi.isSelected()) {
                for (int i = 0; i < muestra; i++) {
                    vecMuestra[i] = generador.nextNumeroLineal();
                }
            } else {
                if (rb_Multiplicativo_Chi.isSelected()) {
                    for (int i = 0; i < muestra; i++) {
                        vecMuestra[i] = generador.nextNumeroMultiplcativo();
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe cargar un Generador primero", "Falta", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        ObservableList<Intervalo> tablaChi =  chi.calcularChi(vecMuestra);

        tv_Test_Chi.setItems(tablaChi);

        XYChart.Series fObs = new XYChart.Series();
        XYChart.Series fEsp = new XYChart.Series();

        RadioButton metodo = (RadioButton) tg_MetodoChi.getSelectedToggle();

        fObs.setName(metodo.getText());
        fEsp.setName("Esperada");

        for (Intervalo i : chi.getIntervalos()) {
            fObs.getData().add(new XYChart.Data("" + i.getNumIt(), i.getF_Obs()));
            fEsp.getData().add(new XYChart.Data("" + i.getNumIt(), i.getF_Esp()));
        }

        bc_Resultados.getData().clear();
        bc_Resultados.getData().addAll(fObs, fEsp);
        //chi.mostrar();

    }
}
