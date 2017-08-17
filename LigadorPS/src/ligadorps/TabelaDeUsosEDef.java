package ligadorps;

import java.util.ArrayList;
import javax.swing.JTextArea;

/**
 *
 * @author João Pedro Bretanha
 */
public class TabelaDeUsosEDef {
    ArrayList<ControllerUso> TabelaDeUso ;
    ArrayList<ControllerDefinicao> TabelaDefinicao ;
    boolean l[] ;
    int contador;
    
    public TabelaDeUsosEDef(){
        this.TabelaDeUso = new ArrayList<>();
        this.TabelaDefinicao = new ArrayList<>();
        this.contador=0;
        
    }
    public void putDef(String simbolo,int val, char reloc){
        ControllerDefinicao Def = new ControllerDefinicao(simbolo, val,reloc);
        this.TabelaDefinicao.add(Def);
        
    }
    public  void putUso(String simbolo, char sinal, int ocor){
        ControllerUso Uso = new ControllerUso(simbolo, sinal, ocor ,null);
        this.TabelaDeUso.add(Uso);
    }
    
    public ArrayList<ControllerUso> getUso(){
   
        return this.TabelaDeUso;
        
    } 
    
    public ArrayList<ControllerDefinicao> getDef(){
        return this.TabelaDefinicao;
        
    }
    public String imprimir(){
        String s="TabelaDefinicao\n";
         ControllerUso infoUso;
         ControllerDefinicao ControllerDefinicao;
        for(int i=0;i<this.TabelaDefinicao.size();i++){
          ControllerDefinicao = this.TabelaDefinicao.get(i);
           s+= ControllerDefinicao.getSimbolo()+" "+ControllerDefinicao.getValor()+" "+ControllerDefinicao.getReloc()+"\n";
        }
         s+="\nTabelaDeUso\n";
        for(int i=0;i<this.TabelaDeUso.size();i++){
          infoUso = this.TabelaDeUso.get(i);
           s+= infoUso.getSimbolo()+" "+infoUso.getOcorrencia()+"\n";
        }
        
         s+="\nrelocavel\n";
        for(int i=0;i<this.l.length;i++){
           s+= this.l[i]+"\n";
        }
        return s;
        
    }
    
    public void imprimeTabUso(JTextArea area){
        ControllerUso infoUso;
        area.append("Simbolo - Ocorrência  -    Sinal\n");
        for(int i=0;i<this.TabelaDeUso.size();i++){
          infoUso = this.TabelaDeUso.get(i);
          area.append(""+infoUso.getSimbolo()+"            "+infoUso.getOcorrencia()+"              "+infoUso.getSinal()+"\n");
        }
    }
    
    public void imprimeTabDef(JTextArea area){
        ControllerDefinicao infoDef;
        area.append("Simbolo - Valor    -    Reloc\n");
        for(int i=0;i<this.TabelaDefinicao.size();i++){
          infoDef = this.TabelaDefinicao.get(i);
          area.append(""+infoDef.getSimbolo()+"            "+infoDef.getValor()+"              "+infoDef.getReloc()+"\n");
        }
    }
    
    public void setBoollean(int i){
        this.l = new boolean[i];
    }
    public void putBoolean(boolean b){
        this.l[this.contador]=b;
        this.contador++;
    }
    public boolean[] getBoolean(){
        return this.l;
    } 
}
