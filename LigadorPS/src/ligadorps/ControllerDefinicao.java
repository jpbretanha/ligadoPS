/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ligadorps;

/**
 *
 * @author João Pedro Bretanha
 */
public class ControllerDefinicao {
    String simbolo;
    int valor;
    char reloc;

    public ControllerDefinicao(String simbolo, int valor, char reloc) {
        this.simbolo = simbolo;
        this.valor = valor;
        this.reloc = reloc;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public char getReloc() {
        return reloc;
    }

    public void setReloc(char reloc) {
        this.reloc = reloc;
    }
}
