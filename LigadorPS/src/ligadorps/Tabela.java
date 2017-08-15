package ligadorps;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author João Pedro Bretanha
 */
public class Tabela {
    ArrayList<String> tabela;
    ArrayList<Integer> tabela1;
    ArrayList<Integer> tabela2;
    ArrayList<Integer> tabela3;
    ArrayList<String> extern;
    ArrayList<Integer> valor;
    
    public Tabela(){
        this.tabela = new ArrayList();
        this.tabela1= new ArrayList();
        this.tabela2= new ArrayList();
        this.tabela3= new ArrayList();
        this.extern = new ArrayList();
        this.valor = new ArrayList();
        }
    public void putLabelExtern(String s,int a){
        this.extern.add(s);
        this.valor.add(a);
    }

    public void putLabel(String s,int a,int b,int c){
        StringTokenizer st = new StringTokenizer(s,":");
        s = (String)st.nextToken();
       if(haselementoExtern(s)==false){
        if(this.tabela.contains(s)){
            int i = this.tabela.indexOf(s);
            if(this.tabela1.get(i)!=1){
                this.tabela1.set(i, a);
                this.tabela2.set(i, b);
                this.tabela3.set(i, c);
                }
        }else{
            this.tabela.add(s);
            this.tabela1.add(a);
            this.tabela2.add(b);
            this.tabela3.add(c);
        }
       }
    }
    public boolean haselementoExtern(String s){
        if(this.extern.isEmpty()) return false;
        else{
            if(this.extern.contains(s)) return true;
            else return false;
        }

    }

    public void putLabel(String s,int a,int b){
        StringTokenizer st = new StringTokenizer(s,":");
        s = (String)st.nextToken();
         if(haselementoExtern(s)==false){
        if(this.tabela.contains(s)){
            int i = this.tabela.indexOf(s);
            if(this.tabela1.get(i)!=1){
                this.tabela1.set(i, a);
                this.tabela2.set(i, b);
                this.tabela3.set(i, 1);
                }
        }else{
            this.tabela.add(s);
            this.tabela1.add(a);
            this.tabela2.add(b);
            this.tabela3.add(1);
        }
         }
    }
    public void setLabel(String s,int a,int b, int c){
        int i = this.tabela.indexOf(s);    
        this.tabela1.set(i, a);//1 para definido -0 para indefinido
        this.tabela2.set(i, b);//valor na memoria
        this.tabela3.set(i, c);//1 para realocavel -0 não realocavel
    } 
    public int getAddressLabel(String s){
        if(haselementoExtern(s)==false){
            int i = this.tabela.indexOf(s);  
            return this.tabela2.get(i);
        }else return 0;
    }
    public boolean contLabel(String s){
        return this.tabela.contains(s);
    }

    public String removerLastElemento(){
        String d;
        int i=this.tabela.size();
        i--;
        d=this.tabela.remove(i);
        this.tabela1.remove(i);
        this.tabela2.remove(i);
        this.tabela3.remove(i);
        return d;
    }
    public boolean definidoLabel(String s){
        //if(this.tabela.isEmpty()==true) return false;
        if(haselementoExtern(s)==false){
            int i = this.tabela.indexOf(s);    
            return tabela1.get(i) == 1 ? true : false;
        }else return true;
    }
    public boolean isConst(String s){
        int i = this.tabela.indexOf(s);
        boolean a = tabela1.get(i) == 1 ? true : false;
        boolean b = tabela3.get(i) == 1 ? true : false;
        if(a==true && b==false) return true;
            else return false;
        }
    public boolean realocavelLabel(String s){
        if(haselementoExtern(s)==false){
            int i = this.tabela.indexOf(s);    
            return tabela3.get(i) == 1 ? true : false;
        }else{
         int i = this.extern.indexOf(s);    
         return this.valor.get(i) == 1 ? true : false;
        }
    }
    public void putEQU(String s,int a){
        int i = this.tabela.indexOf(s);
        this.tabela1.set(i, 1);//1 para definido -0 para indefinido
        this.tabela2.set(i, a);//valor na memoria ou seu valor --depende do proximo oarametro
        this.tabela3.set(i, 0);
    }
    public String toString(){
        String s = "";
        for(int i=0;i<tabela.size();i++)
           s = s+tabela.get(i)+" "+tabela1.get(i)+" "+tabela2.get(i)+" "+tabela3.get(i)+"\n";
        s+="\n";
        for(int i=0;i<this.extern.size();i++)
           s = s+this.extern.get(i)+" "+this.valor.get(i)+"\n";

        return s;
    }
    public boolean isNumero(String s){
        String q;
        q = s.substring(0, 1);

        if(q.equals("0")||q.equals("1")||q.equals("2")||q.equals("3")||q.equals("4")||q.equals("5")||q.equals("6")||q.equals("7")||q.equals("8")||q.equals("9")) 
            return true;
        return false;
    }
    public int size(){
        return this.tabela.size();
    }

    public String getElemento(int i){
        return this.tabela.get(i);
    }
}
