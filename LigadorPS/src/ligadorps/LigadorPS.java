/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ligadorps;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 *
 * @author João Pedro Bretanha
 */
public class LigadorPS {

    private boolean[] relocabilidade_1, relocabilidade_2;
    private ArrayList<ControllerUso> TabUso1, TabUso2;
    private ArrayList<ControllerDefinicao> TabDef1, TabDef2;
    private ArrayList<ControllerDefinicao> TSG;
    private File mont1;
    private File mont2;
    private int nl_dados1, nl_dados2, nl_inst1, nl_inst2;
    
    public File getMont1() {
        return mont1;
    }

    public void setMont1(File mont1) {
        this.mont1 = mont1;
    }

    public File getMont2() {
        return mont2;
    }

    public void setMont2(File mont2) {
        this.mont2 = mont2;
    }

    public LigadorPS(boolean[] relocabilidade_1, boolean[] relocabilidade_2, ArrayList<ControllerUso> TabUso1, ArrayList<ControllerUso> TabUso2, ArrayList<ControllerDefinicao> TabDef1, ArrayList<ControllerDefinicao> TabDef2) {
        this.relocabilidade_1 = relocabilidade_1;
        this.relocabilidade_2 = relocabilidade_2;
        this.TabDef1 = TabDef1;
        this.TabDef2 = TabDef2;
        this.TabUso1 = TabUso1;
        this.TabUso2 = TabUso2;
    }

    public void constroiTSG() { 
        int tam_mont1=0;
        try {
            Scanner sc = new Scanner(new FileReader(mont1)).useDelimiter("\\||\\n");
            if(sc != null && sc.hasNext() ){
                tam_mont1 = Integer.parseInt(sc.next());
                
                sc.close();
            }
            TSG = new ArrayList<ControllerDefinicao>();
            for (ControllerDefinicao infdef : TabDef1) {    //pro primeiro segmento é só copiar as definiçoes
                TSG.add(infdef);
            }
            
            for(int i=0; i<TabDef2.size();i++){
                ControllerDefinicao temp = TabDef2.get(i);
                if(temp.getReloc() == 'r'){        //pro seg 2, se for um simbolo relocavel, desloca
                    temp.setValor(temp.getValor()+tam_mont1+1);  //mais um pq substituira hlt (1 byte) do fim do arqv por um jmp (2bytes)
                }
                TSG.add(temp);
            }
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LigadorPS.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    }

    public void imprimeTSG(JTextArea AreaTSG) {
        for(int i=0; i<TSG.size(); i++){
            ControllerDefinicao tmp = TSG.get(i);
            String linha = i+": Simbolo "+tmp.getSimbolo()+" - Valor: "+tmp.getValor()+" - Reloc: "+tmp.getReloc()+"\n";
            AreaTSG.append(linha);
        }
    }
    
    public void escreveArquivo(String nome, String conteudo){
        StringBuilder stb = new StringBuilder();
        //System.out.print("Diretorio: "+mont1.getParent());
        stb.append(mont1.getParent()+"/").append(nome);
        try {  
            FileWriter fw = new FileWriter( new File(stb.toString()), true );
            fw.write(conteudo);
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(LigadorPS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //associar a cada uso uma definiçao, pra ficar mais facil de buscar numa tabela só
    public void ajusta_TabsUso() {
        int index = 0;
        while( TabUso1!=null && index < TabUso1.size() ){
            ControllerUso info_uso = TabUso1.get(index);
            String simb = info_uso.getSimbolo();
            ControllerDefinicao info_def = buscaPorSimbTSG(simb);
            if( info_def != null){
                info_uso.setDefinicao(info_def);
            }
            index++;
        }
        
        index = 0;
        
        while( TabUso2!=null && index < TabUso2.size() ){
            ControllerUso info_uso = TabUso2.get(index);
            String simb = info_uso.getSimbolo();
            ControllerDefinicao info_def = buscaPorSimbTSG(simb);
            if( info_def != null){
                info_uso.setDefinicao(info_def);
            }
            index++;
        }
        
        //teste pra verificar se alguma tabuso ficou sem def
        for(int i=0; i<TabUso1.size(); i++){
            if(TabUso1.get(i).getDefinicao()== null ){
                System.out.print("\nDeu erro no seg 1\n");
            }
        }
         for(int i=0; i<TabUso2.size(); i++){
            if(TabUso2.get(i).getDefinicao() == null ){
                System.out.print("\nDeu erro no seg 2\n");
            }
        }
    }
    
    public ControllerDefinicao buscaPorSimbTSG(String simb) {
        for(int i=0; i < TSG.size(); i++){
            if(TSG.get(i).getSimbolo().equalsIgnoreCase(simb)){
                return TSG.get(i);
            }
        }
        return null;
    }

    public ControllerUso buscaPorOcorrTabUso(ArrayList<ControllerUso> tab, int valor){
        for(int i=0; i<tab.size();i++){
            if(valor == tab.get(i).getOcorrencia()){
                //System.out.print("achou\n");
                return tab.get(i);
            }
        }
        return null;
    }
    
    public String buscaPorValorTabDef(ArrayList<ControllerDefinicao> tab, int valor){
        for(int i=0; i<tab.size();i++){
            //System.out.printf("%d == %d ?\n", valor, tab.get(i).getValor());
            if(valor == tab.get(i).getValor()){
               // System.out.print("achou\n");
                return tab.get(i).getSimbolo();
            }
        }
        return null;
    }
    
    //com as tabelas de uso e tsg prontas, lê o arqvo montado e vai escrevendo o que nao precisa alterar (codigos de maquina
    // e valores absolutos definidos internamente e endereços de dados do seg1 (que nao foram deslocados)) e alterando o que é necessario
    public void imprime_seg2(JTextArea AreaLig) {
        
        try {
            Scanner sc = new Scanner(new FileReader(mont2)).useDelimiter("\\||\\n");
            
            int qtde_elem_validos = Integer.parseInt(sc.next());
            //System.out.printf("elem v %d\n", qtde_elem_validos);
            int num_linhas_dados = (Integer.parseInt(sc.next()))/2;
            //System.out.printf("n lin dados %d\n", num_linhas_dados);
            int num_linhas_inst = Integer.parseInt(sc.next());
            //System.out.printf("n lin inst %d\n", num_linhas_inst);
            
            //copia a parte de dados, se for indefinido, por default, preenche com zero, senao, preenche com o valor especificado no arqvo
            for(int i=0; i < num_linhas_dados; i++){ 
                String aux = sc.next();
                
                if(aux.equalsIgnoreCase("?")){
                    escreveArquivo("lig.txt","0000\n");
                    AreaLig.append("0000\n");
                }
                else{
                    escreveArquivo("lig.txt", aux+"\n");
                    String t = aux+"\n";
                    AreaLig.append(t);
                }
            }
            StringTokenizer st;
            ArrayList<String> guarda_tks_2 = new ArrayList<String>();  //array list que vai guardar cada token da parte de instrucoes
            while(sc.hasNext()){
                StringBuilder linha = new StringBuilder();
                linha.append(sc.next()).append("\n");         //gambiarra pra recolocar a \n que o sc tira, por causa do usedelimiter
                st = new StringTokenizer(linha.toString()," ");    //unico delimitador pro tokenizer é o espaço em branco
                //System.out.printf("ctokens %d\n",st.countTokens());
                while(st.hasMoreTokens()){  //separa os tokens da linha
                     guarda_tks_2.add(st.nextToken());
                 }
            }
            int pos_arqv=0; //pos_arqv é referente a cada byte (0,1,2,3,...) do arquivo
            int pos_reloc=0;  //pos_reloc é referente a cada linha do arquivo
            String t1;
            while(!guarda_tks_2.isEmpty()){
                   // System.out.printf("gtks size %d\n", guarda_tks_2.size());
                    String temp = guarda_tks_2.remove(0);
                    if(temp.endsWith("\n")){         //se o primeiro token ja termina com \n, significa que é uma instruçao sem valor de mem ou cte
                        escreveArquivo("lig.txt", temp+"");
                        t1 = temp+"";
                        AreaLig.append(t1);
                        // se for hlt, pop AX, push AX, popf ou pushf ou ret, tem um byte só, senao, tem dois.
                        if(temp.equalsIgnoreCase("F4") || temp.equalsIgnoreCase("58") || temp.equalsIgnoreCase("50") || temp.equalsIgnoreCase("9D") || temp.equalsIgnoreCase("9C") || temp.equalsIgnoreCase("C3")){
                            pos_arqv++;
                        }
                        else{
                            pos_arqv = pos_arqv+2;
                        }
                    }
                    else{ //se nao, significa que apos esse token tem outro que faz parte dessa instruçao
                        escreveArquivo("lig.txt", temp+" ");
                        t1 = temp+" ";
                        AreaLig.append(t1);
                        if(temp.equalsIgnoreCase("8B84") || temp.equalsIgnoreCase("8984")){  //sao as duas unicas instrucoes de 3 bytes, 8B84 mem e 8984 mem
                            pos_arqv = pos_arqv + 2;
                        }
                        else{
                            pos_arqv++;
                        }
                        if(!guarda_tks_2.isEmpty()){  //vai pegar o proximo token, que ainda faz parte da instrucao, MAS se for um F4 (hlt) ai vai ta vazio, por isso o teste
                            if(pos_reloc < relocabilidade_2.length && relocabilidade_2[pos_reloc]){         //se é algo relocavel
                                ControllerUso iu = buscaPorOcorrTabUso(TabUso2,pos_arqv+(num_linhas_dados*2));  //procura na tabela de uso, a ocorrencia é pos_arqv + deslocamento por causa da parte de dados do segmento
                                if(iu!=null){  //se achou infouso, ou seja, aqui nesse lugar é pra completar com algo que foi definido externamente ao seg corrente
                                    temp = guarda_tks_2.remove(0);  //remove o token
                                    temp = temp.replaceAll("(\\r|\\n)", "");  //substitui \n por espaço em branco
                                    int valor_escrito;
                                    int valor_arqvo = Integer.parseInt(temp);  //pega o valor que ta no arquivo (possivelmente 0)
                                    char c = iu.getSinal();
                                    switch(c){
                                        case '+': valor_escrito = valor_arqvo + iu.getDefinicao().getValor();  //define valor final a ser escrito com o valor q tinha no arqvo mais o valor q ta na infouso
                                                escreveArquivo("lig.txt", valor_escrito+"\n");
                                                t1 = valor_escrito+"\n";
                                                AreaLig.append(t1);
                                                //System.out.print(valor_escrito+"\n");
                                                break;
                                        case '-': valor_escrito = valor_arqvo - iu.getDefinicao().getValor();
                                                escreveArquivo("lig.txt", valor_escrito+"\n");
                                                t1 = valor_escrito+"\n";
                                                AreaLig.append(t1);
                                                break;
                                    }
                                }
                                //é relocavel mas nao ta na tabuso, entao ta na tabdef e no proprio arquivo com valor desatualizado ainda
                                else{
                                    int tam_mont1 = 0;
                                    Scanner sc1 = new Scanner(new FileReader(mont1)).useDelimiter("\\||\\n");
                                    if(sc1 != null && sc1.hasNext() ){
                                        tam_mont1 = Integer.parseInt(sc1.next());  //pega a qtde de bytes do prim seg
                                        sc1.close();
                                    }
                                    tam_mont1 = tam_mont1 + 1;   //acrescenta 2 por causa do jump no final do seg1, ao inves do hlt
                                    temp = guarda_tks_2.remove(0);
                                    temp = temp.replaceAll("(\\r|\\n)", "");
                                    int valor_arqvo = Integer.parseInt(temp);
                                    int valor_escrito = valor_arqvo + tam_mont1;  // valor arqvo ja contem o deslocamento dentro do proprio arqvo (feito pelo montador)
                                    escreveArquivo("lig.txt",valor_escrito+"\n");
                                    t1 = valor_escrito+"\n";
                                    AreaLig.append(t1);
                                }
                            }
                            else{      //se eh absoluto verifica se é da tab_uso, se nao for é só escrever
                                ControllerUso iu = buscaPorOcorrTabUso(TabUso2,pos_arqv+(num_linhas_dados*2));
                                if(iu!=null){
                                    temp = guarda_tks_2.remove(0);
                                    temp = temp.replaceAll("(\\r|\\n)", "");
                                    int valor_escrito;
                                    int valor_arqvo = Integer.parseInt(temp);
                                    char c = iu.getSinal();
                                    switch(c){
                                        case '+': valor_escrito = valor_arqvo + iu.getDefinicao().getValor();
                                                  escreveArquivo("lig.txt", valor_escrito+"\n");
                                              //System.out.print(valor_escrito+"\n");
                                                  t1 = valor_escrito+"\n";
                                                  AreaLig.append(t1);
                                                  break;
                                        case '-': valor_escrito = valor_arqvo - iu.getDefinicao().getValor();
                                                  escreveArquivo("lig.txt", valor_escrito+"\n");
                                                  t1 = valor_escrito+"\n";
                                                  AreaLig.append(t1);
                                                  break;
                                    }
                                }
                                else{
                                    temp = guarda_tks_2.remove(0);
                                    escreveArquivo("lig.txt", temp);
                                    AreaLig.append(temp);
                                }
                            //System.out.print(temp);
                            }
                            pos_reloc++;
                            pos_arqv++;
                        /*System.out.printf("gtks size %d\n", guarda_tks_2.size());
                        System.out.print("\n--------------------------\n");*/
                        }
                    } 
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LigadorPS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void imprime_seg1(JTextArea AreaLig) {
        try {
            Scanner sc = new Scanner(new FileReader(mont1)).useDelimiter("\\||\\n");
            
            int qtde_elem_validos = Integer.parseInt(sc.next());
            //System.out.printf("elem v %d\n", qtde_elem_validos);
            int num_linhas_dados = nl_dados1 = (Integer.parseInt(sc.next()))/2;
            //System.out.printf("n lin dados %d\n", num_linhas_dados);
            sc.next();
            for(int cont = 0; cont < num_linhas_dados; cont++) sc.next();
            
            int num_linhas_inst = 0;
            while(sc.hasNext()){
                num_linhas_inst++;
                sc.next();
            }
            nl_inst1 = num_linhas_inst;
            sc.close();
            sc = new Scanner(new FileReader(mont1)).useDelimiter("\\||\\n");
            sc.next();sc.next();sc.next();  //pular dados que ja foram usados
            
            //System.out.printf("num linhas inst %d\n", num_linhas_inst);
            
            Scanner scaux = new Scanner(new FileReader(mont2)).useDelimiter("\\||\\n");
            scaux.next();
            nl_dados2 = (Integer.parseInt(scaux.next()))/2;
            nl_inst2 = 0; 
            scaux.next();
            for(int cont = 0; cont < nl_dados2; cont++) scaux.next();
                while(scaux.hasNext()){
                nl_inst2++;
                scaux.next();
            }
            scaux.close();
            //nl_inst1++;
            escreveArquivo("lig.txt", nl_dados1+"\n");
            escreveArquivo("lig.txt", nl_inst1+"\n");
            escreveArquivo("lig.txt", nl_dados2+"\n");
            escreveArquivo("lig.txt", nl_inst2+"\n");
            
            for(int i=0; i < num_linhas_dados; i++){ 
                String aux = sc.next();
                
                if(aux.equalsIgnoreCase("?")){
                    escreveArquivo("lig.txt","0000\n");
                    AreaLig.append("0000\n");
                }
                else{
                    escreveArquivo("lig.txt", aux+"\n");
                    String t = aux+"\n";
                    AreaLig.append(t);
                }
            }
            
           
            StringTokenizer st;
            ArrayList<String> guarda_tks_1 = new ArrayList<String>();
            while(sc.hasNext()){
                StringBuilder linha = new StringBuilder();
                linha.append(sc.next()).append("\n");
                st = new StringTokenizer(linha.toString()," ");
                //System.out.printf("ctokens %d\n",st.countTokens());
                while(st.hasMoreTokens()){
                    String temp = st.nextToken();
                    if( !temp.equalsIgnoreCase("F4\n")){            //pra nao pegar o hlt do seg1
                     guarda_tks_1.add(temp);
                    } 
                 }
            }
            Scanner sc2 = new Scanner(new FileReader(mont2)).useDelimiter("\\||\\n");
            if(sc2 != null && sc2.hasNext() ){
                sc2.next();
                int tam_dados2 = Integer.parseInt(sc2.next());
                guarda_tks_1.add("EB");
                int desvio_dados = qtde_elem_validos + 1 + (tam_dados2);
                String end_jump = String.valueOf(desvio_dados);
                guarda_tks_1.add(end_jump+"\n");
                sc2.close();
            }
            int pos_arqv=0;
            int pos_reloc=0;
            String t1;
            while(!guarda_tks_1.isEmpty()){
                   // System.out.printf("gtks size %d\n", guarda_tks_2.size());
                    String temp = guarda_tks_1.remove(0);
                    if(temp.endsWith("\n")){
                        escreveArquivo("lig.txt", temp+"");
                        t1 = temp+"";
                        AreaLig.append(t1);
                        // se for hlt, pop AX, push AX, popf ou pushf, tem um byte só, senao, tem dois.
                        if(temp.equalsIgnoreCase("F4") || temp.equalsIgnoreCase("58") || temp.equalsIgnoreCase("50") || temp.equalsIgnoreCase("9D") || temp.equalsIgnoreCase("9C") || temp.equalsIgnoreCase("C3")){
                            pos_arqv++;
                        }
                        else{
                            pos_arqv = pos_arqv+2;
                        }
                    }
                    else{
                        escreveArquivo("lig.txt", temp+" ");
                        t1 = temp+" ";
                        AreaLig.append(t1);
                        if(temp.equalsIgnoreCase("8B84") || temp.equalsIgnoreCase("8984")){
                            pos_arqv = pos_arqv + 2;
                        }
                        else{
                            pos_arqv++;
                        }
                        if(!guarda_tks_1.isEmpty()){
                            if(pos_reloc < relocabilidade_1.length && relocabilidade_1[pos_reloc]){
                                ControllerUso iu = buscaPorOcorrTabUso(TabUso1,pos_arqv+(num_linhas_dados*2));
                                if(iu!=null){
                                    temp = guarda_tks_1.remove(0);
                                    temp = temp.replaceAll("(\\r|\\n)", "");
                                    int valor_escrito;
                                    int valor_arqvo = Integer.parseInt(temp);
                                    char c = iu.getSinal();
                                    switch(c){
                                        case '+': valor_escrito = valor_arqvo + iu.getDefinicao().getValor();
                                                  escreveArquivo("lig.txt", valor_escrito+"\n");
                                                  t1 = valor_escrito+"\n";
                                                  AreaLig.append(t1);
                                                  break;
                                        case '-': valor_escrito = valor_arqvo - iu.getDefinicao().getValor();
                                                  escreveArquivo("lig.txt", valor_escrito+"\n");
                                                  t1 = valor_escrito+"\n";
                                                  AreaLig.append(t1);
                                                  break;
                                    }
                                }
                                //é relocavel mas nao ta na tabuso, entao ta na tabdef e o valor do arquivo é o correto, pois o montador ja resolveu isso pro seg1
                                else{
                                    temp = guarda_tks_1.remove(0);
                                    escreveArquivo("lig.txt",temp);
                                    AreaLig.append(temp);
                                }
                            }
                            else{      //se eh absoluto testa pra ver se era coisa da tabuso ou da tabdef,
                                ControllerUso iu = buscaPorOcorrTabUso(TabUso1,pos_arqv+(num_linhas_dados*2));
                                if(iu!=null){
                                    temp = guarda_tks_1.remove(0);
                                    temp = temp.replaceAll("(\\r|\\n)", "");
                                    int valor_escrito;
                                    int valor_arqvo = Integer.parseInt(temp);
                                    char c = iu.getSinal();
                                    switch(c){
                                        case '+': valor_escrito = valor_arqvo + iu.getDefinicao().getValor();
                                                  escreveArquivo("lig.txt", valor_escrito+"\n");
                                                  t1 = valor_escrito+"\n";
                                                  AreaLig.append(t1);
                                                  break;
                                        case '-': valor_escrito = valor_arqvo - iu.getDefinicao().getValor();
                                                  escreveArquivo("lig.txt", valor_escrito+"\n");
                                                  t1 = valor_escrito+"\n";
                                                  AreaLig.append(t1);
                                                  break;
                                    }
                                }
                                else{  //ta na tabdef, só escreve (reescreve)
                                    temp = guarda_tks_1.remove(0);
                                    escreveArquivo("lig.txt", temp);
                                    AreaLig.append(temp);
                                }
                            }
                            pos_reloc++;
                            pos_arqv++;
                        }
                    }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LigadorPS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
