package ligadorps;
import java.util.ArrayList;
/**
 *
 * @author João Pedro Bretanha
 */
public class TabelaInstrucaoMaquina {
    ArrayList<String> tabela;
    String operadores;
    private final ArrayList<String> operadoresOpCode[];
    private final ArrayList<String> hexdecimalOpcode[];
    
    //construtor
    public TabelaInstrucaoMaquina(){
        this.tabela = new ArrayList();
        this.operadoresOpCode = new ArrayList[19];
        
        for(int i=0;i<19;i++)
            this.operadoresOpCode[i] = new ArrayList<>();
        
        this.hexdecimalOpcode = new ArrayList[19];
        
        for(int i=0;i<19;i++)
            this.hexdecimalOpcode[i] = new ArrayList<>();

        this.tabela.add("add");//1
            this.operadoresOpCode[0].add("AX,DX");
            this.operadoresOpCode[0].add("AX,AX");
            this.hexdecimalOpcode[0].add("03C2");
            this.hexdecimalOpcode[0].add("03C0");
            this.hexdecimalOpcode[0].add("05");
        this.tabela.add("sub");//2
            this.operadoresOpCode[1].add("AX,DX");
            this.operadoresOpCode[1].add("AX,AX");
            this.hexdecimalOpcode[1].add("2BC2");
            this.hexdecimalOpcode[1].add("2BC0");
            this.hexdecimalOpcode[1].add("2D");
        this.tabela.add("mul");//3
            this.operadoresOpCode[2].add("SI");
            this.operadoresOpCode[2].add("AX");
            this.hexdecimalOpcode[2].add("F7E3");
            this.hexdecimalOpcode[2].add("F7E0");
        this.tabela.add("div");//4
            this.operadoresOpCode[3].add("SI");
            this.operadoresOpCode[3].add("AX");
            this.hexdecimalOpcode[3].add("F7F6");
            this.hexdecimalOpcode[3].add("F7F0");
        this.tabela.add("and");//5
            this.operadoresOpCode[4].add("AX,DX");
            this.operadoresOpCode[4].add("AX,AX");
            this.hexdecimalOpcode[4].add("23C2");
            this.hexdecimalOpcode[4].add("23C0");
            this.hexdecimalOpcode[4].add("25");
        this.tabela.add("or");//6
            this.operadoresOpCode[5].add("AX,DX");
            this.operadoresOpCode[5].add("AX,AX");
            this.hexdecimalOpcode[5].add("0BC2");
            this.hexdecimalOpcode[5].add("0BC0");
            this.hexdecimalOpcode[5].add("0D");
        this.tabela.add("xor");//7
            this.operadoresOpCode[6].add("AX,DX");
            this.operadoresOpCode[6].add("AX,AX");
            this.hexdecimalOpcode[6].add("33C2");
            this.hexdecimalOpcode[6].add("33C0");
            this.hexdecimalOpcode[6].add("25");
        this.tabela.add("not");//8
            this.operadoresOpCode[7].add("AX") ;
            this.hexdecimalOpcode[7].add("F7D0");
        this.tabela.add("cmp");//9
            this.operadoresOpCode[8].add("AX,DX");
            this.hexdecimalOpcode[8].add("3DC2");
            this.hexdecimalOpcode[8].add("3D");
        this.tabela.add("jmp");//10
            this.operadoresOpCode[9] = null;
            this.hexdecimalOpcode[9].add("EB");
        this.tabela.add("je");//11
            this.operadoresOpCode[10] = null;
            this.hexdecimalOpcode[10].add("74");
        this.tabela.add("jnz");//12
            this.operadoresOpCode[11] = null;
            this.hexdecimalOpcode[11].add("75");
        this.tabela.add("jz");//13
            this.operadoresOpCode[12] = null;
            this.hexdecimalOpcode[12].add("74");
        this.tabela.add("jp");//14
            this.operadoresOpCode[13] = null;
            this.hexdecimalOpcode[13].add("7A");
        this.tabela.add("mov");//15
            this.operadoresOpCode[14].add("SP,AX");
            this.operadoresOpCode[14].add("SS,AX");
            this.operadoresOpCode[14].add("DS,AX");
            this.operadoresOpCode[14].add("AX,SP");
            this.operadoresOpCode[14].add("AX,SS");
            this.operadoresOpCode[14].add("AX,DS");
            this.operadoresOpCode[14].add("AX,CS");
            this.operadoresOpCode[14].add("AX,DX");
            this.operadoresOpCode[14].add("AX,SI");
            this.operadoresOpCode[14].add("AX,[SI]");
            this.operadoresOpCode[14].add("DX,AX");
            this.operadoresOpCode[14].add("SI,AX");
            this.operadoresOpCode[14].add("[SI],AX");
            this.hexdecimalOpcode[14].add("8BE0");//SP,AX
            this.hexdecimalOpcode[14].add("8ED0");//SS,AX
            this.hexdecimalOpcode[14].add("8ED8");//DS,AX
            this.hexdecimalOpcode[14].add("8BC4");//AX,SP
            this.hexdecimalOpcode[14].add("8CD0");//AX,SS
            this.hexdecimalOpcode[14].add("8CD8");//AX,DS
            this.hexdecimalOpcode[14].add("8CC8");//AX,CS
            this.hexdecimalOpcode[14].add("8BC2");//AX,DX
            this.hexdecimalOpcode[14].add("8BC6");//AX,SI
            this.hexdecimalOpcode[14].add("8B04");//AX,[SI]
            this.hexdecimalOpcode[14].add("8BDO");//DX,AX
            this.hexdecimalOpcode[14].add("8BF0") ;//SI,AX
            this.hexdecimalOpcode[14].add("8904");//[SI],AX
            this.hexdecimalOpcode[14].add("A1");//AX,men --13
            this.hexdecimalOpcode[14].add("8B84");//AX,men[SI] --14
            this.hexdecimalOpcode[14].add("A3");//men,AX --15
            this.hexdecimalOpcode[14].add("8984");//men[SI],AX --16
            this.hexdecimalOpcode[14].add("B8");//AX,cte

        this.tabela.add("int");//16
            this.operadoresOpCode[15] = null;
            this.hexdecimalOpcode[15].add("CD");
        this.tabela.add("pop");//17
             this.operadoresOpCode[16].add("AX") ;
            this.hexdecimalOpcode[16].add("58");
        this.tabela.add("push");//18
            this.operadoresOpCode[17].add("AX") ;
            this.hexdecimalOpcode[17].add("50");
        this.tabela.add("popf");//19
            this.operadoresOpCode[18] = null;
            this.hexdecimalOpcode[18].add("9D");
        this.tabela.add("pushf");//19
            this.operadoresOpCode[18] = null;
            this.hexdecimalOpcode[18].add("9C");
        this.operadores = "SP,AX SS,AX DS,AX AX,SP AX,SS AX,DS AX,CS AX,DX AX,SI AX,[SI] DX,AX SI,AX [SI],AX AX,AX AX,DX";  
    }
    public boolean isInstrução(String s){
        return this.tabela.contains(s);
    }
    public boolean isOperador(String s){
        return this.operadores.contains(s);
    }
    public String getHexdecimal(String opcode,String operador){
      int i = this.tabela.indexOf(opcode);
      int j = this.operadoresOpCode[i].indexOf(operador);
      if(j!=-1) return this.hexdecimalOpcode[i].get(j);
      else return this.hexdecimalOpcode[i].get(this.hexdecimalOpcode[i].size()-1);

    }
    public String getHexdecimal(String opcode){
      int i = this.tabela.indexOf(opcode);
      return this.hexdecimalOpcode[i].get(this.hexdecimalOpcode[i].size()-1);
    }
    public String getHexdecimalEspecila(String opcode,int a){
       int i = this.tabela.indexOf(opcode);
       return this.hexdecimalOpcode[i].get(a);
    }
}
