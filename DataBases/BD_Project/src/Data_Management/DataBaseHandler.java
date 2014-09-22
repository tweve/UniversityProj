/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Data_Management;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class DataBaseHandler {
    
       //CONNECTION TO ORACLE DATABASE
    final String username = "bd1";//username of the DB (YOU MIGHT NEED TO CHANGE THIS IF YOU USERNAME IS DIFFERENT!)
    final String password = "bd1";//password of the DB (YOU MIGHT NEED TO CHANGE THIS IF YOU PASSWORD IS DIFFERENT!)
    final String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl"; //you might need to change this URL... it ends with orcl (instead of xe) if you are using Oracle. If you are using Oracle Express use xe in the end instead of orcl
    private Connection conn; //connection to the database
    private int idUser =-1;
    
    public DataBaseHandler(){
        
        try {
            //Connect to the database
            //The following snippet registers the driver and gets a connection to the database, in the case of a normal java application.
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            //instantiate oracle.jdbc.driver.OracleDriver
            //in other words... load the driver
            Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("consegue conectar");
        
        } catch (SQLException e1) {
                System.out.println("n consegue conectar");
                e1.printStackTrace();
        } catch (InstantiationException e) {
                System.out.println("n consegue conectar");
                e.printStackTrace();
        } catch (IllegalAccessException e) {
                System.out.println("n consegue conectar");
                e.printStackTrace();
        } catch (ClassNotFoundException e) {
                System.out.println("n consegue conectar");
                e.printStackTrace();
        }
    }
    
    /**
     * Closes the connection with the database
     */
    public void close(){
        try {
            conn.close();//close the connection to the database
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertClient(String nome, String usern, String pass, String carg, String esp, boolean check, float salario) {
        
        try {
           
            Statement stmt;
            
            stmt = conn.createStatement();
            if(check == true) {
                System.out.println("insert into utilizador (id_user,nome,username,password) values( SEQ_USER_ID.nextval,'"+nome+"','"+usern+"','"+pass+"')");
                stmt.executeQuery("insert into utilizador (id_user,nome,username,password) values( USER_ID_SEQ.nextval,'"+nome+"','"+usern+"','"+pass+"')");
                
                stmt.executeQuery("insert into funcionario (id_user, horas_trab,salario_base) values('"+getUserID(usern)+"','"+0+"',"+salario+")");
                this.conn.commit();
            }
            else {
                
                 stmt.executeQuery("insert into utilizador (id_user,nome,username,password) values( USER_ID_SEQ.nextval,'"+nome+"','"+usern+"','"+pass+"')");
                 stmt.executeQuery("insert into administrador (id_user, cargo, especializacao) values('"+getUserID(usern)+"','"+carg+"','"+esp+"')");
                 this.conn.commit();
            }
        } catch (SQLException ex) {
             Logger.getLogger(DataBaseHandler.class.getName()).log(Level.SEVERE, null, ex);

        }
    }
    
    public void insertFerias(int d1,int m1, int y1, int d2, int m2, int y2) {
        
        try {
            
     
            String DATE_FORMAT = "dd/MM/yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Statement stm;
            GregorianCalendar dx = new GregorianCalendar();
            dx.getTime();
            dx.set(y1, m1, d1);
            GregorianCalendar dx2 = new GregorianCalendar();
            dx2.getTime();
            dx2.set(y2, m2, d2);
            stm = conn.createStatement();
            
            String data1 = d1+"/"+m1+"/"+y1;
            String data2 = d2+"/"+m2+"/"+y2;
            
            
  
            System.out.println(data1);
            
  
            System.out.println(data2);
            
            System.out.println("insert into ferias (id_ferias, id_user, data_inicio, data_fim,confirmado) values (FERIAS_ID_SEQ.nextval,"+idUser+",to_date('"+data1+"','dd/mm/yyyy'),to_date('"+data2+"','dd/mm/yyyy'))");
            stm.executeQuery("insert into ferias (id_ferias, id_user, data_inicio, data_fim,confirmado) values (FERIAS_ID_SEQ.nextval,"+idUser+",to_date('"+data1+"','dd/mm/yyyy'),to_date('"+data2+"','dd/mm/yyyy'),'nao')");
            this.conn.commit();
        }catch( SQLException ex) {
            
            Logger.getLogger(DataBaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void removeClient(int client_id) {
        
        try {
           
            Statement stmt;
            System.out.println("\nPerforming DELETE > USER");
            stmt = conn.createStatement();
            /*if( !isAdmin(client_id)){
                
                System.out.println("delete from funcionario where id_user = "+client_id);
                stmt.executeQuery("delete from funcionario where id_user = "+client_id);
                
            }else if(isAdmin(client_id)) {
                
                System.out.println("delete from administrador where id_user = "+client_id);
                stmt.executeQuery("delete from administrador where id_user = "+client_id);
                
            }*/
            stmt.executeQuery("delete from utilizador where id_user = "+client_id);
            this.conn.commit();
            
        } catch (SQLException ex) {
             Logger.getLogger(DataBaseHandler.class.getName()).log(Level.SEVERE, null, ex);

        }
    }
    
    public int login(String username,String password){
        
        int id= -1;
        
        try {
            // Execute statement
            Statement stmt;
            
            stmt = conn.createStatement();
            System.out.println("vai fazer query");
            ResultSet rset = stmt.executeQuery("SELECT id_user FROM UTILIZADOR where username="+"'"+username+"'"+" AND password = '"+password+"'");
            
            System.out.println("fez a query");

            while (rset.next()) {
              
                id = rset.getInt("id_user");
                System.out.println(id);

            }

            stmt.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("N tem ligaçao a base de dados!");
            return -1;
        }
        idUser = id;
        return id;
        
    }
    
    public boolean isAdmin(int id) {
        Statement stmt;
        int idrec=0;
        
        try {


            stmt = conn.createStatement();

            ResultSet rset = stmt.executeQuery("SELECT * FROM ADMINISTRADOR where id_user=" + "'" + id + "'");


            while (rset.next()) {

                idrec = rset.getInt("id_user");
                System.out.println(idrec);

            }


            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("N tem ligaçao a base de dados!");
            return false;
        }
        if (idrec== id)
            return true;

        return false;
    }
    
    public ArrayList<Utilizador> getUtilizadores(){
        
        ArrayList<Utilizador> utilizadores = new ArrayList<Utilizador>();
        Utilizador aux;
        System.out.print("\n[Performing getUtilizadores] ... ");
        try {
            // Execute statement
            Statement stmt;
            
            stmt = conn.createStatement();
            System.out.println("vai fazer query");
            ResultSet rset = stmt.executeQuery("SELECT * FROM UTILIZADOR");
            System.out.println("fez a query");

            while (rset.next()) {
           
                aux = new Funcionario(rset.getInt("ID_USER"),rset.getString("nome"), rset.getString("username"), rset.getString("password"));
                utilizadores.add(aux);
                //System.out.println("pw:" + aux.toString());
                
            }
            stmt.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("N tem ligaçao a base de dados!");
            return null;
        }
        return utilizadores;
    }
    
    public ArrayList<Mensagem> getMensagens(){
        
        ArrayList<Mensagem> mensagens = new ArrayList<Mensagem>();
        Mensagem aux;
        System.out.print("\n[Performing getMensagens] ... ");
        try {
            // Execute statement
            Statement stmt;
            
            stmt = conn.createStatement();
            System.out.println("vai fazer query");
            ResultSet rset = stmt.executeQuery("SELECT getUsername(mensagem.id_user) as nome,assunto,texto from le,mensagem,utilizador where le.id_mensagem = mensagem.id_mensagem AND le.id_user = utilizador.id_user AND le.id_user = "+ idUser);
            System.out.println("fez a query");

            while (rset.next()) {
           
                aux = new Mensagem(rset.getString("NOME"),rset.getString("ASSUNTO"), rset.getString("TEXTO"));
                mensagens.add(aux);

                //System.out.println("pw:" + aux.toString());
                
            }
            stmt.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("N tem ligaçao a base de dados!");
            return null;
        }
        return mensagens;
    }
    
    public void insertMessage(String msg, String subj, String destination){
     
        int id;
        try {
           
            Statement stmt;
            
            stmt = conn.createStatement();
            System.out.println(idUser);
            
            /************************/
            // TRANSAÇÃO porque queremos que o valor da sequencia message_id_seq ainda seja o mesmo par ainserir na tabela LE, o quie poderia nao acontecer num ambiente concorrente
            /************************/
            
            
            conn.setAutoCommit(false);
            
            ResultSet rset = stmt.executeQuery("INSERT into Mensagem (id_mensagem,id_user,texto,assunto) values (MESSAGE_ID_SEQ.NEXTVAL,"+idUser+",'"+msg+"','"+subj+"')");
            
            if (destination.equals("allusers")){
                ArrayList<Integer> lista = new ArrayList<Integer>();
                rset = stmt.executeQuery("Select id_user from utilizador");
                while (rset.next()) {
                    int temp = rset.getInt("id_user");
                    
                    lista.add(temp);
                    System.out.println(temp);
                    
                }
                for (int i = 0;i<lista.size();i++){
                    rset = stmt.executeQuery("INSERT into LE values ("+ lista.get(i) +",MESSAGE_ID_SEQ.CURRVAL)");
                }
            }
            else {
                id = getUserID(destination);
                if (id == -1){
                    JOptionPane.showMessageDialog(null, "Invalid receiver username.");
                    return;
                }
                    
                rset = stmt.executeQuery("INSERT into LE values ("+id+",MESSAGE_ID_SEQ.CURRVAL)");
            }
            conn.commit();
            conn.setAutoCommit(true);
            
             /************************/
            // TRANSAÇÃO
            /************************/
            
            stmt.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("N tem ligaçao a base de dados!");

        }
        
    }

    public int getUserID(String username) {

        int id = -1;

        try {

            CallableStatement cs;


            cs = conn.prepareCall("{?=call getUserID(?)}");
            cs.registerOutParameter(1, Types.INTEGER);
            cs.setString(2, username);

            cs.execute();
            id = cs.getInt(1);


            cs.close();

        } catch (SQLException ex) {

            

        }
        return id;
    }
    
    public void picaPonto(){
        try {
            CallableStatement cs;
            cs = conn.prepareCall("{call picaPonto(?)}");
            cs.setInt(1,idUser);   
            System.out.println("id: "+idUser);
            cs.execute();
            this.conn.commit();
            cs.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Impossivel picar ponto.");
        }
    }
    public ArrayList<Recibo> getRecibos(){
         
        ArrayList<Recibo> recibos = new ArrayList<Recibo>();
        Recibo aux;
        System.out.print("\n[Performing getRecibos] ... ");
        try {
            // Execute statement
            Statement stmt;
            
            stmt = conn.createStatement();
            ResultSet rset;
            
            if (this.isAdmin(idUser))
                rset = stmt.executeQuery("SELECT * from recibo");  
            else
                rset = stmt.executeQuery("SELECT * from recibo where id_user = "+ idUser);


            while (rset.next()) {
           
                aux = new Recibo(rset.getInt("ID_RECIBO"),rset.getString("DATA_RECIBO"),rset.getDouble("QUANTIA"), rset.getInt("HORAS_TRAB"));
                recibos.add(aux);

                
            }
            stmt.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("N tem ligaçao a base de dados!");
            return null;
        }
        return recibos;
    }
    
    public String getPicagens(){
        String picagens = "";
        try {
            Statement stmt;
            stmt = conn.createStatement();
          
            ResultSet rset = stmt.executeQuery("select * from dia_trabalho where to_char(data_dia) like to_char(sysdate) and id_user= "+ idUser);
            
                while (rset.next()) {
                   
                    
                    
                       
                        if (rset.getTimestamp("HORA_SAIDA") == null){
                             picagens = "Picou para entrada às : "+rset.getTimestamp("HORA_ENTRADA").toString()+"\n";
                        }
                        else{
                            picagens = "Picou para entrada às : "+rset.getTimestamp("HORA_ENTRADA").toString()+"\n";
                            picagens = picagens+ "Picou para saida às : "+rset.getTimestamp("HORA_SAIDA").toString()+"\n";
                            picagens= picagens+ "Recibo automáticamente gerado, consulte os recibos.\n";
                        }
                }
              stmt.close();
        }
          catch (Exception ex ){
               Logger.getLogger(DataBaseHandler.class.getName()).log(Level.SEVERE, null, ex);
          }
            
            
            
            return picagens.toString();
    }
    
    public  ArrayList<Ferias> getFerias(){
        ArrayList<Ferias> ferias = new ArrayList<Ferias>();
        Ferias aux;
        System.out.print("\n[Performing getFerias] ... ");
        try {
            // Execute statement
            Statement stmt;
            
            stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery("SELECT id_ferias,nome,data_inicio,data_fim,confirmado from ferias,utilizador where ferias.id_user = utilizador.id_user");


            while (rset.next()) {
           
                aux = new Ferias(rset.getInt("ID_FERIAS"),rset.getString("NOME"),rset.getString("DATA_INICIO"),rset.getString("DATA_FIM"),rset.getString("CONFIRMADO"));
                ferias.add(aux);

                
            }
            stmt.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("N tem ligaçao a base de dados!");
            return null;
        }
        return ferias;
    }
    public void confirmaFerias(int id_ferias){
        Statement stmt;

        try {
            stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery("UPDATE ferias set confirmado = 'sim' where id_ferias = "+id_ferias);
            stmt.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public  ArrayList<Requisicao> getRequisicoes(){
        ArrayList<Requisicao> reqs = new ArrayList<Requisicao>();
        Requisicao aux;
        System.out.print("\n[Performing getREQ] ... ");
        try {
            // Execute statement
            Statement stmt;
            
            stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery("SELECT nome,nome_sala,hora_inicio,hora_fim from requisicao,utilizador where requisicao.id_user = utilizador.id_user");


            while (rset.next()) {
           
                aux = new Requisicao(rset.getString("NOME"),rset.getString("NOME_SALA"),rset.getString("HORA_INICIO"),rset.getString("HORA_FIM"));
                reqs.add(aux);

                
            }
            stmt.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("N tem ligaçao a base de dados!");
            return null;
        }
        return reqs;
    }
    
    public void requisitaSala(String sala, String inicio, String fim){

        System.out.println(sala);
        
        System.out.println(inicio);
        
        System.out.println(fim);
        
        try {

            CallableStatement cs;


            cs = conn.prepareCall("{call reqSala(?,?,?,?)}");

            
            cs.setInt(1,idUser);
            cs.setString(2,sala);
            cs.setString(3,inicio);
            cs.setString(4,fim);
            
            

            cs.execute();


            cs.close();

        } 
        catch (SQLException ex) {
            Logger.getLogger(DataBaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Invalid info.");

        }
    }
  
}
