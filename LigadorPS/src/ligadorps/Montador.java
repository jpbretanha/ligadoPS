package ligadorps;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.JTextArea;

/**
 *
 * @author João Pedro Bretanha
 */
public class Montador {
   String Entrada,saida;
    Tabela tabelaSimbolos;
    Tabela tabelaSegment;
    TabelaInstrucaoMaquina tim ;
    TabelaPseudoInstrucoes tpi ;
    TabelaDeUsosEDef UD;
    int contador;
    
    public Montador(String s){
        this.Entrada = s;
        this.saida="";
        this.tabelaSimbolos = new Tabela();
        this.tabelaSegment = new Tabela();
        this.tim = new TabelaInstrucaoMaquina();
        this.tpi = new TabelaPseudoInstrucoes();    
        this.UD = new TabelaDeUsosEDef();
        this.contador=0;
    }

    private void primeiroPasso(){
            String token="",token1;
            StringTokenizer st = new StringTokenizer(this.Entrada,"\n");
            StringTokenizer st1;
            int bytes = 0,tokensDados=0,tokensInstr=0;
            for(int j=0;st.hasMoreElements()==true;j++){//primeiro passo
                st1 = new StringTokenizer((String) st.nextElement());
                for(int i=0;st1.hasMoreElements()==true;i++){
                    token1 = token ;
                    token = st1.nextToken();
                    if(this.tpi.isInstrucaoMontagem(token)){//Etoken = st1.nextToken();ntre se for instrução de montagem
                        if(token.equals("DW")){
                            bytes+=2;
                            tokensDados+=2;
                            if(st1.hasMoreElements()) {
                                token = st1.nextToken();

                            }
                        }else if(token.equals("EQU")){
                            token = st1.nextToken();
                            this.tabelaSimbolos.putEQU(token1,Integer.parseInt(token));
                        }else if(token.equals("SEGMENT")){
                            this.tabelaSegment.putLabel(this.tabelaSimbolos.removerLastElemento(),1, bytes);
                        }else if(token.equals("ENDS")){
                            this.tabelaSimbolos.removerLastElemento();
                        }else if(token.equals("ASSUME")){
                            token = st1.nextToken();
                        }else if(token.equals("EXTRN")){
                            StringTokenizer st2 = new StringTokenizer(st1.nextToken(),":");
                            String s = st2.nextToken();
                            if(st2.nextToken().equals("NEAR")) this.tabelaSimbolos.putLabelExtern(s, 1);
                            else this.tabelaSimbolos.putLabelExtern(s, 0);

                        }else if(token.equals("hlt")){
                            this.contador++;
                            bytes++;  
                        }

                    }else if(this.tim.isInstrução(token)==false && this.tim.isOperador(token)==false){//se não for instrução ou operador sem labol = contem labol ou é um labol con
                        StringTokenizer st2 = new StringTokenizer(token,",");
                        if(st2.countTokens()==1){           //se st2 conter apenas um elemento

                            if(this.tabelaSimbolos.isNumero(token)) this.tabelaSimbolos.putLabel(token, 1,Integer.parseInt(token),0);
                            else{

                                if(i==0) this.tabelaSimbolos.putLabel(token, 1,bytes);
                                else this.tabelaSimbolos.putLabel(token, 0, 0);

                            }
                        }else{                              //se st2 conter dois elementos
                            String operador = st2.nextToken();
                            if(operador.contains("[SI]")) bytes++;
                            StringTokenizer st3 = new StringTokenizer(operador,"[SI]");
                            operador = st3.nextToken();
                            if(this.tim.isOperador(operador)){
                                operador=st2.nextToken();
                                if(operador.contains("[SI]")) bytes++;
                                st3 = new StringTokenizer(operador,"[SI]");
                                operador = st3.nextToken();


                                if(this.tabelaSimbolos.isNumero(operador)) this.tabelaSimbolos.putLabel(operador, 1,Integer.parseInt(operador),0);
                                else this.tabelaSimbolos.putLabel(operador, 0, 0);
                            }
                            else this.tabelaSimbolos.putLabel(operador, 0, 0);
                        }
                    }else if(this.tim.isInstrução(token)) {      //se for instrução     
                        this.contador++;
                       if(token.equals("ret")|| token.equals("pop") || token.equals("push")){
                           bytes++;

                           tokensInstr++;
                       }else {
                           bytes+=2;

                           tokensInstr+=2;
                       }
                    }
                }
            }
           this.saida+=bytes+"\n"+tokensDados+"\n"+tokensInstr+"\n";
     this.UD.setBoollean(this.contador);
    }

    private void segundoPasso(){

    String token="",token1;
    int bytes = -1;
            StringTokenizer st = new StringTokenizer(this.Entrada,"\n");
            StringTokenizer st1;
            for(int j=0;st.hasMoreElements()==true;j++){
                st1 = new StringTokenizer((String) st.nextElement());
                for(int i=0;st1.hasMoreElements()==true;i++){
                    token1 = token ;
                    token = st1.nextToken();
                    if(this.tpi.isInstrucaoMontagem(token)){//Entre se for instrução de montagem
                        if(token.equals("DW")){
                            if(st1.hasMoreElements()){
                            token = st1.nextToken();
                            this.saida +=token+"\n";
                            }else this.saida += "?\n";
                            bytes+=2;
                        }else if(token.equals("EQU")){

                        }else if(token.equals("EXTRN")){
                            token = st1.nextToken();
                        }else if(token.equals("hlt")){
                            this.saida +="F4\n"; 
                            this.UD.putBoolean(false);
                        }
                    }else if(this.tim.isInstrução(token)) {      //se for instrução     
                       bytes+=2;
                       token1 = token ;
                       token = st1.nextToken();

                       if(this.tim.isOperador(token)==false){
                        String operador="";
                        StringTokenizer st2 = new StringTokenizer(token,",");
                        if(st2.countTokens()==1){           //se st2 conter apenas um elemento
                            this.saida+=tim.getHexdecimal(token1);
                            operador=st2.nextToken();
                        }else{                              //se st2 conter dois elementos
                            operador = st2.nextToken();
                            StringTokenizer st3;
                            if(operador.equals("AX")){
                                operador = st2.nextToken();
                                if(operador.contains("[SI]")){
                                    bytes++;
                                    st3 = new StringTokenizer(operador,"[SI]");
                                    operador = st3.nextToken();
                                    this.saida+=tim.getHexdecimalEspecila(token1,14);


                                }else{

                                    if(token1.equals("mov")){
                                    if(this.tabelaSegment.contLabol(operador)) this.saida+=tim.getHexdecimal(token1);
                                    else this.saida+=tim.getHexdecimalEspecila(token1,13);
                                    }else this.saida+=tim.getHexdecimal(token1);
                                }
                                //(this.tabelaSimbolos.realocavelLabol(operador) && this.tabelaSimbolos.definidoLabol(operador))
                            }else {
                                //operador = st2.nextToken();
                                if(operador.contains("[SI]")){
                                    st3 = new StringTokenizer(operador,"[SI]");
                                    operador = st3.nextToken();
                                    this.saida+=tim.getHexdecimalEspecila(token1,16);

                                }else{
                                    this.saida+=tim.getHexdecimalEspecila(token1,15);
                                }

                            }
                        }
                        if(this.tabelaSimbolos.definidoLabol(operador)==false||this.tabelaSimbolos.haselementoExtern(operador) ) this.UD.putUso(operador,'+', bytes);
                        this.saida+=" "+this.tabelaSimbolos.getAddressLabol(operador);
                        if(this.tabelaSimbolos.realocavelLabol(operador)) this.UD.putBoolean(true);
                        else this.UD.putBoolean(false);
                        }else{
                           this.saida+=tim.getHexdecimal(token1,token);
                           this.UD.putBoolean(false);
                       }
                       this.saida+="\n";
                    }
                }
            }

        for(int i=0;i<this.tabelaSimbolos.size();i++)
           if(this.tabelaSimbolos.definidoLabol(this.tabelaSimbolos.getElemento(i))&&this.tabelaSimbolos.isNumero(this.tabelaSimbolos.getElemento(i))==false)
               this.UD.putDef(this.tabelaSimbolos.getElemento(i),this.tabelaSimbolos.getAddressLabol(this.tabelaSimbolos.getElemento(i)),this.tabelaSimbolos.realocavelLabol(this.tabelaSimbolos.getElemento(i))==true ? 'r':'a');
    }

    public String ImpprimirTabelas(){
        return this.tabelaSimbolos.toString()+"\n"+this.tabelaSegment.toString()+"\n"+this.UD.imprimir();
        //return this.tabelaSimbolos.toString()+"\n"+"\n"+this.UD.imprimir();
    }

    public void ImprimeTabUso(JTextArea area){
        this.UD.imprimeTabUso(area);
    }

    public void ImprimeTabDef(JTextArea area){
        this.UD.imprimeTabDef(area);
    }

    public ArrayList<ControllerUso> getControllerUso(){
        return this.UD.getUso();
    }

    public ArrayList<ControllerDefinicao> getControllerDefinicao(){
        return this.UD.getDef();
    }
    public boolean[] getRelocabilidade(){
        return this.UD.getBoolean();
    }

    public String getSAida(){
        return this.saida;
    }
    public void montar(){
        this.primeiroPasso();;
        this.segundoPasso();
    }
}
