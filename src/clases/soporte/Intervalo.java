package clases.soporte;

public class Intervalo
{
    private int numIt;
    private float inferior;
    private float superior;
    private int f_Obs;
    private float f_Esp;
    private float chi;

    public Intervalo()
    {
    }

    public Intervalo(int numIt)
    {
        this.numIt = numIt;
    }

    public Intervalo(Intervalo copy)
    {
        this.numIt = copy.getNumIt();
        this.inferior = copy.getInferior();
        this.superior = copy.getSuperior();
        this.f_Obs = copy.getF_Obs();
        this.f_Esp = copy.getF_Esp();
        this.chi = copy.getChi();
    }

    public Intervalo(int num, float inferior, float superior)
    {
        this.numIt = num;
        this.inferior = inferior;
        this.superior = superior;
        this.f_Obs = 0;
        this.f_Esp = 0;
    }

    public Intervalo(int numIt, float inferior, float superior, float f_Esp)
    {
        this.numIt = numIt;
        this.inferior = inferior;
        this.superior = superior;
        this.f_Esp = f_Esp;
    }

    public Intervalo(int numIt, float inferior, float superior, int f_Obs, float f_Esp, float chi) {
        this.numIt = numIt;
        this.inferior = inferior;
        this.superior = superior;
        this.f_Obs = f_Obs;
        this.f_Esp = f_Esp;
        this.chi = chi;
    }

    public int getNumIt() {
        return numIt;
    }

    public void setNumIt(int numIt) {
        this.numIt = numIt;
    }

    public float getInferior() {
        return inferior;
    }

    public void setInferior(float inferior) {
        this.inferior = inferior;
    }

    public float getSuperior() {
        return superior;
    }

    public void setSuperior(float superior) {
        this.superior = superior;
    }

    public int getF_Obs() {
        return f_Obs;
    }

    public void setF_Obs(int f_Obs) {
        this.f_Obs = f_Obs;
    }

    public float getF_Esp() {
        return f_Esp;
    }

    public void setF_Esp(float f_Esp) {
        this.f_Esp = f_Esp;
    }

    public float getChi() {
        return chi;
    }

    public void setChi(float chi) {
        this.chi = chi;
    }

    public void contar()
    {
        f_Obs++;
    }

    @Override
    public String toString() {
        return "intervaloExp{" +
                "num=" + numIt +
                ", inferior=" + inferior +
                ", superior=" + superior +
                ", f_Obs=" + f_Obs +
                ", f_Esp=" + f_Esp +
                ", chi=" + chi +
                '}';
    }
}
