package gui;

import biblioteca.*;
import javax.swing.JOptionPane;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * InserirLivroGUI.java
 *
 * Created on 16/Nov/2011, 14:45:57
 */
/**
 *
 * @author Tweve
 */
public class ConsultarLivroGUI extends javax.swing.JDialog {

    int id;
    int i_comment = 0;
    /** Creates new form InserirLivroGUI */
    public ConsultarLivroGUI(java.awt.Frame parent, boolean modal, int i) {
        super(parent, modal);
        initComponents();
        this.id = i;
        this.isbn.setText(Biblioteca.listaLivros.get(id).getIsbn());
        this.nome.setText(Biblioteca.listaLivros.get(id).getNome());
        this.autores.setText(Biblioteca.listaLivros.get(id).getAutores());
        this.edicao.setText(Integer.toString (Biblioteca.listaLivros.get(id).getEdicao()));
        this.custo.setText(Double.toString(Biblioteca.listaLivros.get(id).getCustoAquisicao()));
        this.quantidade.setText(Integer.toString(Biblioteca.listaLivros.get(id).getQuantidade()));
        this.checkCondicionada.setSelected(Biblioteca.listaLivros.get(id).getBibliotecaCondicionada());
        this.checkCondicionada.setEnabled(false);
        this.previous.setEnabled(false);
        
        try {
            this.comentario.setText(Biblioteca.listaLivros.get(id).getComentario(i_comment));
            try{
                Biblioteca.listaLivros.get(id).getComentario(i_comment+1);
            }
            catch(Exception e2){
                this.next.setEnabled(false);
            }
        }
        catch(Exception e ){
            
            this.next.setEnabled(false);
            this.previous.setEnabled(false);
            
        }
        
        this.classificacao.setText(Double.toString(Biblioteca.getClassificacao(Biblioteca.listaLivros.get(id).getNome())));
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ok = new javax.swing.JToggleButton();
        checkCondicionada = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        isbn = new javax.swing.JLabel();
        nome = new javax.swing.JLabel();
        autores = new javax.swing.JLabel();
        edicao = new javax.swing.JLabel();
        custo = new javax.swing.JLabel();
        quantidade = new javax.swing.JLabel();
        comentario = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        next = new javax.swing.JButton();
        previous = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        classificacao = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        ok.setText("Voltar");
        ok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okActionPerformed(evt);
            }
        });

        checkCondicionada.setText("Biblioteca Condicionada");
        checkCondicionada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkCondicionadaActionPerformed(evt);
            }
        });

        jLabel1.setText("ISBN:");

        jLabel2.setText("Nome:");

        jLabel3.setText("Autores:");

        jLabel4.setText("Edição:");

        jLabel5.setText("Custo:");

        jLabel6.setText("Qtd:");

        isbn.setText("jLabel7");

        nome.setText("jLabel7");

        autores.setText("jLabel8");

        edicao.setText("jLabel9");

        custo.setText("jLabel7");

        quantidade.setText("jLabel7");

        comentario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comentarioActionPerformed(evt);
            }
        });

        jLabel7.setText("Comentários:");

        next.setText(">");
        next.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextActionPerformed(evt);
            }
        });

        previous.setText("<");
        previous.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                previousActionPerformed(evt);
            }
        });

        jLabel8.setText("Classificação:");

        classificacao.setText("jLabel7");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(checkCondicionada)
                            .addComponent(jLabel7)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(previous)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comentario, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(next))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addGap(42, 42, 42)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(nome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(autores, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(edicao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(custo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(quantidade, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(isbn))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(ok, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(classificacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(225, 225, 225))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(isbn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nome)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(autores)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(edicao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(custo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(quantidade)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(classificacao))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(next, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(checkCondicionada)
                        .addGap(5, 5, 5)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(previous, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comentario, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ok)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void okActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okActionPerformed
        // TODO add your handling code here:
      
            this.setVisible(false);
      
    }//GEN-LAST:event_okActionPerformed

    private void checkCondicionadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkCondicionadaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkCondicionadaActionPerformed

    private void comentarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comentarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comentarioActionPerformed

    private void nextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextActionPerformed
        
        try {
            
        i_comment++;
        this.comentario.setText(Biblioteca.listaLivros.get(id).getComentario(i_comment));
        
            this.previous.setEnabled(true);
            Biblioteca.listaLivros.get(id).getComentario(i_comment+1);
            
        }
        catch (Exception e){
            this.next.setEnabled(false);
        }
        
        
    }//GEN-LAST:event_nextActionPerformed

    private void previousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_previousActionPerformed
        try{
            
        
        i_comment--;
        this.comentario.setText(Biblioteca.listaLivros.get(id).getComentario(i_comment));
            this.next.setEnabled(true);
            Biblioteca.listaLivros.get(id).getComentario(i_comment-1);
            
        }
        catch (Exception e){
            this.previous.setEnabled(false);
            
        }
    }//GEN-LAST:event_previousActionPerformed

    /**
     * @param args the command line arguments
     */
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel autores;
    private javax.swing.JCheckBox checkCondicionada;
    private javax.swing.JLabel classificacao;
    private javax.swing.JTextField comentario;
    private javax.swing.JLabel custo;
    private javax.swing.JLabel edicao;
    private javax.swing.JLabel isbn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JButton next;
    private javax.swing.JLabel nome;
    private javax.swing.JToggleButton ok;
    private javax.swing.JButton previous;
    private javax.swing.JLabel quantidade;
    // End of variables declaration//GEN-END:variables
}