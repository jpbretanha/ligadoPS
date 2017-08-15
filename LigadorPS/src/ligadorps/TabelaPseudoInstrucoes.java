package ligadorps;

import java.util.ArrayList;

/**
 *
 * @author João Pedro Bretanha
 */
public class TabelaPseudoInstrucoes {
    ArrayList<String> tabela;
    String operadores;
    
    public TabelaPseudoInstrucoes(){
        this.tabela = new ArrayList();
        this.tabela.add("DW");
        this.tabela.add("EXTRN");
        this.tabela.add("EQU"); 
        this.tabela.add("END"); 
        this.tabela.add("hlt");
        this.tabela.add("SEGMENT");
        this.tabela.add("ENDS");
        this.tabela.add("ASSUME");
        this.tabela.add("OFFSET");
    }
    public boolean isInstrucaoMontagem(String s){
        return this.tabela.contains(s);
    }    
}
