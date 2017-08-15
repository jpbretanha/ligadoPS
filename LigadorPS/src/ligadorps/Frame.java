package ligadorps;

import javax.swing.JFileChooser;

/**
 *
 * @author henrique
 */
public class Frame extends javax.swing.JFrame {

    /**
     * Creates new form Frame
     */
    private LigadorPS ligador;
    
    public Frame(LigadorPS ligador) {
        super("Ligador Z808");
        initComponents();
        this.ligador = ligador;
    }
    public Frame() {
        super("Ligador Z808");
        initComponents();
        this.ligador = null;
    }

    public LigadorPS getLigador() {
        return ligador;
    }

    public void setLigador(LigadorPS ligador) {
        this.ligador = ligador;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        AreaTSG = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        AreaLig = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        AbrirBotao = new javax.swing.JButton();
        LigarBotao = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        ArqvLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        AreaTSG.setEditable(false);
        AreaTSG.setColumns(20);
        AreaTSG.setRows(5);
        jScrollPane1.setViewportView(AreaTSG);

        jLabel1.setText("Tabela de Símbolos Globais");

        AreaLig.setEditable(false);
        AreaLig.setColumns(20);
        AreaLig.setRows(5);
        jScrollPane2.setViewportView(AreaLig);

        jLabel2.setText("Código fonte final (segmentos ligados)");

        AbrirBotao.setText("Abrir arqvs. montados");
        AbrirBotao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AbrirBotaoActionPerformed(evt);
            }
        });

        LigarBotao.setText("Ligar");
        LigarBotao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LigarBotaoActionPerformed(evt);
            }
        });

        jLabel3.setText("Arquivo salvo em:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ArqvLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addComponent(jLabel1))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(AbrirBotao)
                                .addGap(85, 85, 85)
                                .addComponent(LigarBotao)))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(56, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(AbrirBotao)
                            .addComponent(LigarBotao))
                        .addGap(6, 6, 6))
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(ArqvLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void AbrirBotaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AbrirBotaoActionPerformed
        JFileChooser jFileChooser1, jFileChooser2;
        jFileChooser1 = new JFileChooser("saidas");   //cria o jfilechooser
        jFileChooser1.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);  //mostrar apenas diretorios e arquivos
        int returnval = jFileChooser1.showOpenDialog(null); //mostra a janela para abrir arquivos
        if( (returnval == JFileChooser.CANCEL_OPTION) || returnval == JFileChooser.ERROR_OPTION){
            ligador.setMont1(null);
        }
        else{
           ligador.setMont1(jFileChooser1.getSelectedFile());
        }
        
        jFileChooser2 = new JFileChooser("saidas");   //cria o jfilechooser
        jFileChooser2.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);  //mostrar apenas diretorios e arquivos
        returnval = jFileChooser2.showOpenDialog(null); //mostra a janela para abrir arquivos
        if( (returnval == JFileChooser.CANCEL_OPTION) || returnval == JFileChooser.ERROR_OPTION){
            ligador.setMont2(null);
        }
        else{
           ligador.setMont2(jFileChooser2.getSelectedFile());
           
        }
    }//GEN-LAST:event_AbrirBotaoActionPerformed

    private void LigarBotaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LigarBotaoActionPerformed
       if(ligador.getMont1()!=null && ligador.getMont2()!=null){
         
         this.ligador.constroiTSG();
         this.ligador.imprimeTSG(AreaTSG);
         this.ligador.ajusta_TabsUso();
         this.ligador.imprime_seg1(AreaLig);
         this.ligador.imprime_seg2(AreaLig);
         ArqvLabel.setText(ligador.getMont1().getParent()+"/saidas/lig.txt");
       }
    }//GEN-LAST:event_LigarBotaoActionPerformed

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AbrirBotao;
    private javax.swing.JTextArea AreaLig;
    private javax.swing.JTextArea AreaTSG;
    private javax.swing.JLabel ArqvLabel;
    private javax.swing.JButton LigarBotao;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
