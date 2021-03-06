package gui;
import biblioteca.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
public class MenuCoordGUI extends javax.swing.JFrame {
    private static DefaultListModel listModel;
    private Utente loggedUser;
    LoginGUI log;
    public ArrayList <String> listTemp;

    
    /** Creates new form MenuFuncionario */
    public MenuCoordGUI(LoginGUI log,Utente logged) {
       listModel = new DefaultListModel();
       initComponents();
               addWindowListener(new WindowAdapter(){ 
            public void windowClosing(WindowEvent e){ 
                Biblioteca.saveData();
                System.out.println("data saved");
                System.exit(0);
            }}); 
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
        jButton1 = new javax.swing.JButton();
        devolver = new javax.swing.JButton();
        levantar = new javax.swing.JButton();
        consultarBut = new javax.swing.JButton();
        inserirBut = new javax.swing.JButton();
        actualizarBut = new javax.swing.JButton();
        eliminarBut = new javax.swing.JButton();
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
        eliminarRes = new javax.swing.JButton();
        rel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Reservar Livro");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        devolver.setText("Devolver Livro");
        devolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                devolverActionPerformed(evt);
            }
        });

        levantar.setText("Levantar Livro");
        levantar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                levantarActionPerformed(evt);
            }
        });

        consultarBut.setText("Consultar Livro");
        consultarBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consultarButActionPerformed(evt);
            }
        });

        inserirBut.setText("Inserir Livro");
        inserirBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inserirButActionPerformed(evt);
            }
        });

        actualizarBut.setText("Actualizar Livro");
        actualizarBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actualizarButActionPerformed(evt);
            }
        });

        eliminarBut.setText("Eliminar Livro");
        eliminarBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarButActionPerformed(evt);
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

        eliminarRes.setText("Eliminar Reserva");
        eliminarRes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarResActionPerformed(evt);
            }
        });

        rel.setText("Relatório");
        rel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                relActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rel, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                    .addComponent(eliminarRes, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                    .addComponent(devolver, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                    .addComponent(levantar, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                    .addComponent(consultarBut, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                    .addComponent(inserirBut, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                    .addComponent(actualizarBut, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                    .addComponent(eliminarBut, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
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
                                .addComponent(toSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pesquisar)
                                .addContainerGap())))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pesquisar)
                    .addComponent(toSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(eliminarRes, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(devolver)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(levantar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(consultarBut)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(inserirBut)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(actualizarBut)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(eliminarBut)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
         int i = list.getSelectedIndex();
        
        if (i == -1){
            JOptionPane.showMessageDialog(this, "Tens de seleccionar um livro.");
        }
        else {
            
             Utente aux2 = null;
             String s = (String)JOptionPane.showInputDialog(this, "Nome de utilizador?", "Utilizador", JOptionPane.PLAIN_MESSAGE, null, null, "");
            boolean found = false;
            for (int j = 0;j<Biblioteca.listaUtentes.size();j++){
                aux2 = Biblioteca.listaUtentes.get(j);
                if (aux2.getUsername().equalsIgnoreCase(s)){
                    found = true;
                    break;
                }
            }
            
            if (found == false){
                JOptionPane.showMessageDialog(this, "Nome de utilizador nao encontrado no sistema");
                return;
            }
            
        
            
            if (aux2.getNReservas()== aux2.getLimiteReservas()){
                JOptionPane.showMessageDialog(this, "Utilizador atingiu limite de reservas");
                return;
            }
                
            
            Livro temp = Biblioteca.listaLivros.get(i);
            
             if (temp.getBibliotecaCondicionada()){
                JOptionPane.showMessageDialog(this, "Este livro pertence à biblioteca condicionada");
                return;
            }
            
                // Nao pode haver reservas desse livro para esse utilizador
                for (int k = 0;k < Biblioteca.fifoReservas.size();k++){
                if (Biblioteca.fifoReservas.get(k).getUtente().equals(aux2) && Biblioteca.fifoReservas.get(k).getLivro().equals(temp) ){
                     JOptionPane.showMessageDialog(this, "Este utilizador já reservou este livro.");
                    return;
                }
            }
                // Esse livro nao pode estar emprestado a esse utilizador
               for (int k = 0; k<Biblioteca.listaEmprestimos.size();k++){
                        if (Biblioteca.listaEmprestimos.get(k).getLivro().equals(temp) && Biblioteca.listaEmprestimos.get(k).getUtente().equals(aux2 )){
                            JOptionPane.showMessageDialog(this, "Este Utilizador está na posse deste livro.");
                            return;
                        }
                    }
            
                
               
                Object[] options = {"Sim","Não"};
                int n = JOptionPane.showOptionDialog(this,
               "Tem a certeza?",
                "System",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
                
                if (n == 0){//aceita
                    

                    /**********************************************
                    for (int k = 0; k<Biblioteca.fifoReservas.size();k++){
                        if(Biblioteca.fifoReservas.get(k).getUtente().equals(aux2) && 
                           Biblioteca.fifoReservas.get(k).getUtente().getMulta()){
                            
                           JOptionPane.showMessageDialog(this, "tem"+Biblioteca.fifoReservas.get(k).getUtente().getLivrosNAtrasados()+
                                   "livro(s) em atraso");

                        }
                        
                    }*****************************************/
                    
                    System.out.println(temp.getDisponivel());
                    
                    if (temp.getDisponivel()>0){
                        Reserva reserva = new Reserva(i,aux2,new GregorianCalendar());
                        Biblioteca.fifoReservas.add(reserva);
                        JOptionPane.showMessageDialog(this, "Criada uma nova reserva a contar a partir do momento actual.");
                    }
                    else{
                         Reserva reserva = new Reserva(i,aux2);
                         Biblioteca.fifoReservas.add(reserva);
                         JOptionPane.showMessageDialog(this, "Criada uma nova reserva, porém ainda não começou a contar o tempo.");
                    }
                    aux2.setNReservas(aux2.getNReservas()+1);
                    temp.setDisponivel(temp.getDisponivel()-1);
                    
                    System.out.println(temp.getDisponivel());
                }
                
                
            
                
        }
        refreshList();
    }//GEN-LAST:event_jButton1ActionPerformed

    
    
    
    private void devolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_devolverActionPerformed
       int i = list.getSelectedIndex();
        
        if (i == -1){
            JOptionPane.showMessageDialog(this, "Tens de seleccionar um livro.");
        }
        else{
            Utente aux2 = null;
             String s = (String)JOptionPane.showInputDialog(this, "Nome de utilizador?", "Utilizador", JOptionPane.PLAIN_MESSAGE, null, null, "");
            boolean found = false;
            for (int j = 0;j<Biblioteca.listaUtentes.size();j++){
                aux2 = Biblioteca.listaUtentes.get(j);
                if (aux2.getUsername().equalsIgnoreCase(s)){
                    found = true;
                    break;
                }
            }
            
            if (found == false){
                JOptionPane.showMessageDialog(this, "Nome de utilizador nao encontrado no sistema");
                return;
            }
            
            Livro livro = Biblioteca.listaLivros.get(i);
            Biblioteca.listaLivros.get(i).setQuantidade(Biblioteca.listaLivros.get(i).getDisponivel()+1);
            boolean encontrado = false;
            for (int j =0;j<Biblioteca.listaEmprestimos.size();j++){
                Emprestimo emp = Biblioteca.listaEmprestimos.get(j);
                if (emp.getLivro().getIsbn().equals(livro.getIsbn())&& emp.getUtente().getUsername().equals(aux2.getUsername())){
                    encontrado = true;
                    
                    GregorianCalendar dataEmp = emp.getDataEmprestimo();
                    GregorianCalendar dataActual = new GregorianCalendar();
                     
                    int diasEmp = dataActual.get(GregorianCalendar.DAY_OF_YEAR) - dataEmp.get(GregorianCalendar.DAY_OF_YEAR);
                    
                    if (aux2.getLimiteReservas()==3){//é aluno
                        if (diasEmp-3 >0)
                            JOptionPane.showMessageDialog(this, "Tem de pagar "+(diasEmp-3) +" euros de multa");
                    }
                    else if(aux2.getLimiteReservas()==5){   // é docente
                          if (diasEmp-5 >0)
                        JOptionPane.showMessageDialog(this, "Tem de pagar "+(diasEmp-5) +" euros de multa");
                    }
                        
                    Biblioteca.listaEmprestimos.remove(j);
                    
                    
                    
                    
                    
                    livro.setDisponivel(livro.getDisponivel()+1);
                    aux2.setNEmprestimos(aux2.getNEmprestimos()-1);
                   // emp.getUtente().setLivrosNAtrasados();//***********************************
                    
                    //devolvidos
                    Biblioteca.addDevolucao(livro);
                    JOptionPane.showMessageDialog(this, "Livro devolvido com sucesso.");
                        
                        for (int k =0;k<Biblioteca.fifoReservas.size();k++){
                            if (Biblioteca.fifoReservas.get(j).getLivro().getIsbn().equals(livro.getIsbn())){
                                Biblioteca.fifoReservas.get(j).setData(new GregorianCalendar());
                                break;
                            }
                        }   
                        break;
                }
                
            }
            if (encontrado == false){
                JOptionPane.showMessageDialog(this, "O utilizador não está na posse do livro");
            }
            
        }
        
        refreshList();
    }//GEN-LAST:event_devolverActionPerformed

    private void levantarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_levantarActionPerformed
       int i = list.getSelectedIndex();
       Utente aux2 = null;
        
        if (i == -1){
            JOptionPane.showMessageDialog(this, "Tens de seleccionar um livro.");
        }
        else {
            String s = (String)JOptionPane.showInputDialog(this, "Nome de utilizador?", "Utilizador", JOptionPane.PLAIN_MESSAGE, null, null, "");
            boolean found = false;
            for (int j = 0;j<Biblioteca.listaUtentes.size();j++){
                aux2 = Biblioteca.listaUtentes.get(j);
                if (aux2.getUsername().equalsIgnoreCase(s)){
                    found = true;
                    break;
                }
            }
            
            if (found == false){
                JOptionPane.showMessageDialog(this, "Nome de utilizador nao encontrado no sistema");
                return;
            }
            
            Livro aux = Biblioteca.listaLivros.get(i);
            
             if (aux2.getNEmprestimos()== aux2.getLimiteEmprestimos()){
                JOptionPane.showMessageDialog(this, "Utilizador atingiu limite de emprestimos");
                return;
            }
             
            
            // Esse livro nao pode estar emprestado a esse utilizador
               for (int k = 0; k<Biblioteca.listaEmprestimos.size();k++){
                        if (Biblioteca.listaEmprestimos.get(k).getLivro().getIsbn().equals(aux.getIsbn()) && Biblioteca.listaEmprestimos.get(k).getUtente().getUsername().equals(aux2.getUsername() )){
                            JOptionPane.showMessageDialog(this, "Este Utilizador está na posse deste livro.");
                            return;
                        }
                    }
            
             if (aux.getBibliotecaCondicionada()){
                JOptionPane.showMessageDialog(this, "Este livro pertence à biblioteca condicionada");
                return;
            }
           
            // se n pertencer à biblioteca condicionada, podemos emprestar
            
                int numeroReservasOutrosUsers = 0;  //variavel que nos indica quantos utilizadores estão a frente do que tenta levantar o livro
                boolean tinhaReserva = false;
                
                // Percorre lista de reservas
                int j = 0;
                for (;j< Biblioteca.fifoReservas.size();j++){                                              
                    // se existem reservas daquele livro
                    if (Biblioteca.fifoReservas.get(j).getLivro().getIsbn().equals(Biblioteca.listaLivros.get(i).getIsbn())){           
                        // se for para aquele utilizador
                        if (Biblioteca.fifoReservas.get(j).getUtente().getUsername().equals(aux2.getUsername())){
                            
                            tinhaReserva = true;
                            break;
                        }
                        // reserva para outro utilizador
                        else if (!Biblioteca.fifoReservas.get(j).getUtente().getUsername().equals(aux2.getUsername())){
                            numeroReservasOutrosUsers++;
                        }
                        
                    }
                  

                }
                System.out.println(numeroReservasOutrosUsers);
                
                if (tinhaReserva){
                    if ( aux.getDisponivel()-numeroReservasOutrosUsers>=0){
                        
                        
                            Emprestimo emp = new Emprestimo(aux,aux2);
                            Biblioteca.listaEmprestimos.add(emp);
                            aux2.setNReservas(aux2.getNReservas()-1);   //decrementa reservas
                            Biblioteca.fifoReservas.remove(j);
                            JOptionPane.showMessageDialog(this, "Livro emprestado com sucesso. Reserva apagada.");
                    }
                }
                else{
                    if ( aux.getDisponivel()>0){
                        // pode emprestar o livro


                        Emprestimo emp = new Emprestimo(aux,aux2);
                        Biblioteca.listaEmprestimos.add(emp);
                        JOptionPane.showMessageDialog(this, "Livro emprestado com sucesso.");

                        aux.setDisponivel(aux.getDisponivel()-1);
                        

                    }
                    else{
                        JOptionPane.showMessageDialog(this, "Livro indisponível, porém poderá fazer a sua reserva.");
                    }
                }
            }
        
        refreshList();
    }//GEN-LAST:event_levantarActionPerformed

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

    private void inserirButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inserirButActionPerformed
        InserirLivroGUI inserir = new InserirLivroGUI(this,true);
        inserir.setVisible(true);
        
    }//GEN-LAST:event_inserirButActionPerformed

    private void actualizarButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actualizarButActionPerformed
        
        int i = list.getSelectedIndex();
        
        if (i == -1){
            JOptionPane.showMessageDialog(this, "Tens de seleccionar um livro.");
        }
        else {
           ActualizarLivroGUI actualizar = new ActualizarLivroGUI(this,true,i);
           actualizar.setVisible(true);
        }
    }//GEN-LAST:event_actualizarButActionPerformed

    private void eliminarButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarButActionPerformed
        int i = list.getSelectedIndex();
        
        if (i == -1){
            JOptionPane.showMessageDialog(this, "Tens de seleccionar um livro.");
        }
        else {
            String s = (String)JOptionPane.showInputDialog(this, "Quantos livros queres eliminar:\n", "Eliminar Livros", JOptionPane.PLAIN_MESSAGE, null, null, "");

        if ((s != null) && (s.length() > 0)){
            if (Biblioteca.deleteBook(list.getSelectedIndex(), Integer.parseInt(s)) == 1)
                refreshList();
            else
                JOptionPane.showMessageDialog(this, "Não existem tantos livros disponiveis para serem eliminados.");
        }
    
   }
    
        
//If you're here, the return value was null/empty.
//setLabel("Come on, finish the sentence!");
        refreshList();
    }//GEN-LAST:event_eliminarButActionPerformed

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

private void eliminarResActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarResActionPerformed
    EliminarReservaGUI eliminar = new EliminarReservaGUI(this,false);
     eliminar.setVisible(true);
       
}//GEN-LAST:event_eliminarResActionPerformed

    private void relActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_relActionPerformed
         double total= 0.0;
    listTemp= new ArrayList<String>();
    String s ="";
    list.removeAll();
    for (int i = 0;i< Biblioteca.listaDevolucoes.size();i++){
        Livro temp= Biblioteca.listaDevolucoes.get(i);
        listModel = new DefaultListModel();
        s= temp.getNome()+ "Preço: "+ temp.getCustoAquisicao();
        listModel.addElement(s);
        total+= temp.getCustoAquisicao();
        listTemp.add(s);
    }
        listTemp.add(""+total);
        listModel.addElement("Total investido"+ total);
       list.setModel(listModel);
    }//GEN-LAST:event_relActionPerformed
    public static void refreshList(){
        ArrayList<String> strings = Biblioteca.search();
        
        listModel = new DefaultListModel();
            for (int i = 0;i<strings.size();i++){
                listModel.addElement(strings.get(i));
            }
        list.setModel(listModel);
    }
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton actualizarBut;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton classificar;
    private javax.swing.JButton comentar;
    private javax.swing.JTextField comentario;
    private javax.swing.JButton consultarBut;
    private javax.swing.JButton devolver;
    private javax.swing.JButton eliminarBut;
    private javax.swing.JButton eliminarRes;
    private javax.swing.JButton inserirBut;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton levantar;
    private static javax.swing.JList list;
    private javax.swing.JButton pesquisar;
    private javax.swing.JRadioButton r1;
    private javax.swing.JRadioButton r2;
    private javax.swing.JRadioButton r3;
    private javax.swing.JRadioButton r4;
    private javax.swing.JRadioButton r5;
    private javax.swing.JButton rel;
    private javax.swing.JTextField toSearch;
    // End of variables declaration//GEN-END:variables
}
