
package biblioteca;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @description thread responsavel por eliminar as reservas quando passam 24 horas desde que começam a contar
 */
public class Verifica extends Thread{
    
    
    
    int compareDate(GregorianCalendar inputA, GregorianCalendar inputB)
	{
		int difDays = inputA.get(Calendar.DAY_OF_YEAR) - inputB.get(Calendar.DAY_OF_YEAR);
		int difHours = inputA.get(Calendar.HOUR_OF_DAY) - inputB.get(Calendar.HOUR_OF_DAY);
		int diferenca = difHours + difDays*24;
		return diferenca;
	}
    
    
    public void run(){
        while (true){

            
            
            // Verifica se existem reservas que já expiraram
            for (int i = 0;i<Biblioteca.fifoReservas.size(); i++){
                Reserva reservaTemp = Biblioteca.fifoReservas.get(i);
                
                if (reservaTemp.getData()!= null && compareDate(reservaTemp.getData(),new GregorianCalendar())>= 24){    // se passaram 24 horas
                    //decrementa reservas do user
                    reservaTemp.getUtente().setNReservas(reservaTemp.getUtente().getNReservas()-1);
                    Biblioteca.fifoReservas.remove(i);
                    
                }
            }
            try {
                Thread.sleep(3600000);  // Adormece durante uma hora
            } catch (InterruptedException ex) {
                Logger.getLogger(Verifica.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
