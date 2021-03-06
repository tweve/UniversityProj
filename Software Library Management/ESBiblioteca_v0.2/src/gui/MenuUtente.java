package gui;
import biblioteca.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MenuFuncionario.java
 *
 * Created on 16/Nov/2011, 14:38:57
 */
/**
 *
 * @author Tweve
 */
public class MenuUtente extends javax.swing.JFrame {
    private static DefaultListModel listModel;
    private Utente loggedUser;
    LoginGUI log;
    
    /** Creates new form MenuFuncionario */
    public MenuUtente(LoginGUI log,Utente logged) {
       listModel = new DefaultListModel();
       initComponents();
       this.loggedUser = logged;
       this.log = log;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        consultarBut = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        list = new javax.swing.JList();
        pesquisar = new javax.swing.JButton();
        toSearch = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        comentar = new javax.swing.JButton();
        classificar = new javax.swing.JButton();
        r5 = new javax.swing.JRadioButton();
        r1 = new javax.swing.JRadioButton();
        r2 = new javax.swing.JRadioButton();
        r3 = new javax.swing.JRadioButton();
        r4 = new javax.swing.JRadioButton();
        comentario = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        consultarBut.setText("Consultar Livro");
        consultarBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consultarButActionPerformed(evt);
            }
        });

        list.setModel(listModel);
        jScrollPane1.setViewportView(list);

        pesquisar.setText("Pesquisar");
        pesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pesquisarActionPerformed(evt);
            }
        });

        toSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toSearchActionPerformed(evt);
            }
        });

        jButton5.setText("Logout");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        comentar.setText("Comentar");
        comentar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comentarActionPerformed(evt);
            }
        });

        classificar.setText("Classificar");
        classificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                classificarActionPerformed(evt);
            }
        });

        buttonGroup1.add(r5);

        buttonGroup1.add(r1);

        buttonGroup1.add(r2);

        buttonGroup1.add(r3);

        buttonGroup1.add(r4);

        jLabel1.setText("Inserir comentário:");

        jLabel2.setText("Classificar:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(consultarBut, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(comentar)
                                .addComponent(comentario, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(jLabel1)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(r1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(r2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(r3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(r4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(r5))
                                    .addComponent(classificar)))
                            .addComponent(jLabel2))
                        .addGap(28, 28, 28))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(toSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pesquisar)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pesquisar)
                    .addComponent(toSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)))
                    .addComponent(consultarBut))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comentario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(r3)
                            .addComponent(r4)
                            .addComponent(r5))
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comentar)
                            .addComponent(jButton5)
                            .addComponent(classificar)))
                    .addComponent(r1)
                    .addComponent(r2))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    
    
    private void consultarButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consultarButActionPerformed
        int i = list.getSelectedIndex();
        
        if (i == -1){
            JOptionPane.showMessageDialog(this, "Tens de seleccionar um livro.");
        }
        else {
            ConsultarLivroGUI consultar = new ConsultarLivroGUI(this,true,i);
            consultar.setVisible(true);
        }
    }//GEN-LAST:event_consultarButActionPerformed

    private void toSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_toSearchActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        this.setVisible(false);
        this.log.setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void pesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pesquisarActionPerformed
        // TODO add your handling code here:
        ArrayList<String> strings = Biblioteca.searchByName(toSearch.getText());
        
        listModel = new DefaultListModel();
        
            for (int i = 0;i<strings.size();i++){
                listModel.addElement(strings.get(i));
            }
        list.setModel(listModel);
    }//GEN-LAST:event_pesquisarActionPerformed

    private void comentarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comentarActionPerformed
        
        int i = list.getSelectedIndex();
        
        if (i == -1){
            JOptionPane.showMessageDialog(this, "Tens de seleccionar um livro.");
        }
        else {
          
            String text = comentario.getText();
            if (text != null && text != ""){
                Biblioteca.listaLivros.get(i).adicionaComentario(text);
                JOptionPane.showMessageDialog(this, "Comentário inserido com sucesso.");
                this.comentario.setText("");
            }
        }
        
        
    }//GEN-LAST:event_comentarActionPerformed

    private void classificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_classificarActionPerformed
        
         int i = list.getSelectedIndex();
        
        if (i == -1){
            JOptionPane.showMessageDialog(this, "Tens de seleccionar um livro.");
        }
        else {
            
        int value = 0;
        
        if (r1.isSelected()){
            value = 1;
        }
        else if (r2.isSelected()){
            value = 2;
        }
        else if (r3.isSelected()){
            value = 3;
        }
        else if (r4.isSelected()){
            value = 4;
        }
        else if (r5.isSelected()){
            value = 5;
        }
        else{
            JOptionPane.showMessageDialog(this, "Não selecionou a pontuação.");
            return;
        }
        
       
        int s =  Biblioteca.classifica(loggedUser.getUsername(),i,value);
        if (s == -1)
            JOptionPane.showMessageDialog(this, "Já classificou este livro.");
        else
            JOptionPane.showMessageDialog(this, "Classificado com sucesso!");
        }
        
        
        
        
      
        
    }//GEN-LAST:event_classificarActionPerformed
    public static void refreshList(){
        ArrayList<String> strings = Biblioteca.search();
        
        listModel = new DefaultListModel();
            for (int i = 0;i<strings.size();i++){
                listModel.addElement(strings.get(i));
            }
        list.setModel(listModel);
    }
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton classificar;
    private javax.swing.JButton comentar;
    private javax.swing.JTextField comentario;
    private javax.swing.JButton consultarBut;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private static javax.swing.JList list;
    private javax.swing.JButton pesquisar;
    private javax.swing.JRadioButton r1;
    private javax.swing.JRadioButton r2;
    private javax.swing.JRadioButton r3;
    private javax.swing.JRadioButton r4;
    private javax.swing.JRadioButton r5;
    private javax.swing.JTextField toSearch;
    // End of variables declaration//GEN-END:variables
}
