package ligadorps;

/**
 *
 * @author João Pedro Bretanha
 */
public class ControllerUso {
    String simbolo;
    char sinal;
    int ocorrencia;
    ControllerDefinicao definicao;

    public ControllerUso(String simbolo, char sinal, int ocorrencia, ControllerDefinicao definicao) {
        this.simbolo = simbolo;
        this.sinal = sinal;
        this.ocorrencia = ocorrencia;
        this.definicao = definicao;
    }

    public ControllerDefinicao getDefinicao() {
        return definicao;
    }

    public void setDefinicao(ControllerDefinicao definicao) {
        this.definicao = definicao;
    }
    
    

    public int getOcorrencia() {
        return ocorrencia;
    }

    public void setOcorrencia(int ocorrencia) {
        this.ocorrencia = ocorrencia;
    }

    
    
    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public char getSinal() {
        return sinal;
    }

    public void setSinal(char sinal) {
        this.sinal = sinal;
    }
    
}
