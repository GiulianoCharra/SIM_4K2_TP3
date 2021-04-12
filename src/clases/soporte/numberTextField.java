package clases.soporte;

import javafx.scene.control.TextField;

public class numberTextField extends TextField
{
    public numberTextField() {
        this.setPromptText("Ingrese solo numeros");
    }

    @Override
    public void replaceText(int i, int i1, String s) {
        if(s.matches("[0-9]") || s.isEmpty())
            super.replaceText(i,i1,s);
    }


    @Override
    public void replaceSelection(String s)
    {
        super.replaceSelection(s);
    }
}
