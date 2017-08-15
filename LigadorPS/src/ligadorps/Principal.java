/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ligadorps;

import java.util.ArrayList;

/**
 *
 * @author João Pedro Bretanha
 */
public class Principal {
    
    
    public static void main(String[] args){
        boolean[] relocabilidade_2 = {false,true,false,true,false,true,false,false, true};
        boolean[] relocabilidade_1 ={false,true,false,true,false,true,false,true,false,true,true,true};
        
        ArrayList<ControllerUso> TabUso2 = criaTabUso1();
        ArrayList<ControllerUso> TabUso1 = criaTabUso2();
        
        ArrayList<ControllerDefinicao> TabDef2 = criaTabDef1();
        ArrayList<ControllerDefinicao> TabDef1 = criaTabDef2();
        
       
        /*
        Ligador ligador = new Ligador(relocabilidade_1, relocabilidade_2, TabUso1, TabUso2, TabDef1, TabDef2);
        //ligador.executa();
        Frame fr = new Frame(ligador);
        fr.setVisible(true);*/
        FrameMontador fm = new FrameMontador();
        fm.setVisible(true);
        
    
    }
    
    
    private static ArrayList<ControllerUso> criaTabUso1() {
        ArrayList<ControllerUso> tab1 = new ArrayList<ControllerUso>();
        ControllerUso aux1 = new ControllerUso("Nove", '+', 5, null);
        tab1.add(aux1);
        
        aux1 = new ControllerUso("Um", '+', 7, null);
        tab1.add(aux1);
        
        aux1 = new ControllerUso("Oito", '+', 9, null);
        tab1.add(aux1);
        
        aux1 = new ControllerUso("Um", '+', 11, null);
        tab1.add(aux1);
        
        aux1 = new ControllerUso("Sete", '+', 13, null);
        tab1.add(aux1);
   
        aux1 = new ControllerUso("Um", '+', 15, null);
        tab1.add(aux1);
        
        aux1 = new ControllerUso("Seis", '+', 19, null);
        tab1.add(aux1);
        
        return tab1;
    }
    
    private static ArrayList<ControllerUso> criaTabUso2(){
        ArrayList<ControllerUso> tab2 = new ArrayList<ControllerUso>();
        ControllerUso aux1 = new ControllerUso("Inicio", '+', 42, null);
        tab2.add(aux1);
        
        aux1 = new ControllerUso("Teste", '+', 40, null);
        tab2.add(aux1);
        
        return tab2;
    }
    
    private static ArrayList<ControllerDefinicao> criaTabDef1(){
        ArrayList<ControllerDefinicao> tab1 = new ArrayList<ControllerDefinicao>();
        ControllerDefinicao temp = new ControllerDefinicao("Teste", 0, 'r');
        tab1.add(temp);
        
        temp = new ControllerDefinicao("Inicio",4, 'r');
        tab1.add(temp);
        return tab1;
    }

    private static ArrayList<ControllerDefinicao> criaTabDef2() {
        ArrayList<ControllerDefinicao> tab2 = new ArrayList<ControllerDefinicao>();
        
        ControllerDefinicao aux = new ControllerDefinicao("Um", 1, 'a');
        tab2.add(aux);
        
        aux = new ControllerDefinicao("Dois", 0, 'r');
        tab2.add(aux);
        
        aux = new ControllerDefinicao("Tres", 2, 'r');
        tab2.add(aux);
        
        aux = new ControllerDefinicao("Quatro", 4, 'r');
        tab2.add(aux);
        
        aux = new ControllerDefinicao("Cinco", 6, 'r');
        tab2.add(aux);
        
        aux = new ControllerDefinicao("Seis", 8, 'r');
        tab2.add(aux);
        
        aux = new ControllerDefinicao("Sete", 10, 'r');
        tab2.add(aux);
        
        aux = new ControllerDefinicao("Oito", 12, 'r');
        tab2.add(aux);
        
        aux = new ControllerDefinicao("Nove", 14, 'r');
        tab2.add(aux);
        
        aux = new ControllerDefinicao("Zero", 16, 'r');
        tab2.add(aux);
        
        return tab2;
    }
    
}
