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
    Tabela tabelaSegmentos;
    TabelaInstrucaoMaquina tabelaInstrucaoMaquina ;
    TabelaPseudoInstrucoes tabelaPseudoInstrucoes ;
    TabelaDeUsosEDef tabelaUsosDef;
    int contador;
    
    public Montador(String s){
        this.Entrada = s;
        this.saida="";
        this.tabelaSimbolos = new Tabela();
        this.tabelaSegmentos = new Tabela();
        this.tabelaInstrucaoMaquina = new TabelaInstrucaoMaquina();
        this.tabelaPseudoInstrucoes = new TabelaPseudoInstrucoes();    
        this.tabelaUsosDef = new TabelaDeUsosEDef();
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
                    if(this.tabelaPseudoInstrucoes.isInstrucaoMontagem(token)){ //Se for instrução de montagem
                        switch (token) {
                            case "DW":
                                bytes+=2;
                                tokensDados+=2;
                                if(st1.hasMoreElements()) {
                                    token = st1.nextToken();
                                    
                                }   break;
                            case "EQU":
                                token = st1.nextToken();
                                this.tabelaSimbolos.putEQU(token1,Integer.parseInt(token));
                                break;
                            case "SEGMENT":
                                this.tabelaSegmentos.putLabel(this.tabelaSimbolos.removeUltimoElemento(),1, bytes);
                                break;
                            case "ENDS":
                                this.tabelaSimbolos.removeUltimoElemento();
                                break;
                            case "ASSUME":
                                token = st1.nextToken();
                                break;
                            case "EXTRN":
                                StringTokenizer st2 = new StringTokenizer(st1.nextToken(),":");
                                String s = st2.nextToken();
                                if(st2.nextToken().equals("NEAR")) this.tabelaSimbolos.putLabelExtern(s, 1);
                                else this.tabelaSimbolos.putLabelExtern(s, 0);
                                break;
                            case "hlt":
                                this.contador++;
                                bytes++;  
                                break;
                        }

                    }else if(this.tabelaInstrucaoMaquina.isInstrução(token)==false && this.tabelaInstrucaoMaquina.isOperador(token)==false){
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
                            if(this.tabelaInstrucaoMaquina.isOperador(operador)){
                                operador=st2.nextToken();
                                if(operador.contains("[SI]")) bytes++;
                                st3 = new StringTokenizer(operador,"[SI]");
                                operador = st3.nextToken();


                                if(this.tabelaSimbolos.isNumero(operador)) this.tabelaSimbolos.putLabel(operador, 1,Integer.parseInt(operador),0);
                                else this.tabelaSimbolos.putLabel(operador, 0, 0);
                            }
                            else this.tabelaSimbolos.putLabel(operador, 0, 0);
                        }
                    }else if(this.tabelaInstrucaoMaquina.isInstrução(token)) {      //se for instrução     
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
           
     this.tabelaUsosDef.setBoollean(this.contador);
    }

    private void segundoPasso(){

    String token="",token1;
    int bytes = -1;
            StringTokenizer st = new StringTokenizer(this.Entrada,"\n");
            StringTokenizer st1;
            for(int j=0;st.hasMoreElements()==true;j++){
                st1 = new StringTokenizer((String) st.nextElement());
                for(int i=0;st1.hasMoreElements()==true;i++){
                    token1 = token;
                    token = st1.nextToken();
                    if(this.tabelaPseudoInstrucoes.isInstrucaoMontagem(token)){ //Se for instrução de montagem
                        switch (token) {
                            case "DW":
                                if(st1.hasMoreElements()){
                                    token = st1.nextToken();
                                    this.saida +=token+"\n";
                                }else this.saida += "?\n";
                                bytes+=2;
                                break;
                            case "EQU":
                                break;
                            case "EXTRN":
                                token = st1.nextToken();
                                break;
                            case "hlt":
                                this.saida +="F4\n";
                                this.tabelaUsosDef.putBoolean(false);
                                break;
                        }
                    }else if(this.tabelaInstrucaoMaquina.isInstrução(token)) {      //se for instrução     
                       bytes+=2;
                       token1 = token ;
                       token = st1.nextToken();

                       if(this.tabelaInstrucaoMaquina.isOperador(token)==false){
                        String operador="";
                        StringTokenizer st2 = new StringTokenizer(token,",");
                        if(st2.countTokens()==1){           //se st2 conter apenas um elemento
                            this.saida+=tabelaInstrucaoMaquina.getHexdecimal(token1);
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
                                    this.saida+=tabelaInstrucaoMaquina.getHexdecimalEspecial(token1,14);


                                }else{

                                    if(token1.equals("mov")){
                                    if(this.tabelaSegmentos.contLabel(operador)) this.saida+=tabelaInstrucaoMaquina.getHexdecimal(token1);
                                    else this.saida+=tabelaInstrucaoMaquina.getHexdecimalEspecial(token1,13);
                                    }else this.saida+=tabelaInstrucaoMaquina.getHexdecimal(token1);
                                }
                                
                            }else {
                                if(operador.contains("[SI]")){
                                    st3 = new StringTokenizer(operador,"[SI]");
                                    operador = st3.nextToken();
                                    this.saida+=tabelaInstrucaoMaquina.getHexdecimalEspecial(token1,16);

                                }else{
                                    this.saida+=tabelaInstrucaoMaquina.getHexdecimalEspecial(token1,15);
                                }

                            }
                        }
                        if(this.tabelaSimbolos.definidoLabel(operador)==false||this.tabelaSimbolos.haselementoExtern(operador) ) this.tabelaUsosDef.putUso(operador,'+', bytes);
                            this.saida+=" "+this.tabelaSimbolos.getAddressLabel(operador);
                        if(this.tabelaSimbolos.realocavelLabel(operador)) this.tabelaUsosDef.putBoolean(true);
                        else this.tabelaUsosDef.putBoolean(false);
                        }else{
                           this.saida+=tabelaInstrucaoMaquina.getHexdecimal(token1,token);
                           this.tabelaUsosDef.putBoolean(false);
                       }
                       this.saida+="\n";
                       
                    }
                }
            }

        for(int i=0;i<this.tabelaSimbolos.size();i++)
           if(this.tabelaSimbolos.definidoLabel(this.tabelaSimbolos.getElemento(i))&&this.tabelaSimbolos.isNumero(this.tabelaSimbolos.getElemento(i))==false)
               this.tabelaUsosDef.putDef(this.tabelaSimbolos.getElemento(i),this.tabelaSimbolos.getAddressLabel(this.tabelaSimbolos.getElemento(i)),this.tabelaSimbolos.realocavelLabel(this.tabelaSimbolos.getElemento(i))==true ? 'r':'a');
    }

    public String ImpprimirTabelas(){
        return this.tabelaSimbolos.toString()+"\n"+this.tabelaSegmentos.toString()+"\n"+this.tabelaUsosDef.imprimir();
    }

    public void ImprimeTabUso(JTextArea area){
        this.tabelaUsosDef.imprimeTabUso(area);
    }

    public void ImprimeTabDef(JTextArea area){
        this.tabelaUsosDef.imprimeTabDef(area);
    }

    public ArrayList<ControllerUso> getControllerUso(){
        return this.tabelaUsosDef.getUso();
    }

    public ArrayList<ControllerDefinicao> getControllerDefinicao(){
        return this.tabelaUsosDef.getDef();
    }
    public boolean[] getRelocabilidade(){
        return this.tabelaUsosDef.getBoolean();
    }

    public String getSAida(){
        return this.saida;
    }
    public void montar(){
        this.primeiroPasso();
        this.segundoPasso();
    }
}
