import java.util.Calendar;
import java.util.GregorianCalendar;


public class GenerateSchedule extends Thread{
	
	public void run(){
		
		while (true){
			Speciality spe;
			Doctor doc;
			  
		  	for (int i = 0;i<ServerMain.specialities.size();i++){
		  		spe = ServerMain.specialities.get(i);
		  		
		  		
		  		for (int j = 0;j<spe.docs.size();j++){
		  	  		doc = spe.docs.get(j);
		  	  		
		  	  		if (j == 0){
		  	  			
		  	  			Calendar cal = new GregorianCalendar();
		  	  			
		  	  			while(cal.get(Calendar.DAY_OF_WEEK)!=Calendar.TUESDAY){
		  	  				cal.add(Calendar.DATE, 1);
		  	  			}
		  	  			
		  	  	
		  	  			
		  	  			doc.consultas.add(new Consulta("09:30",cal));
						doc.consultas.add(new Consulta("10:00",cal));
						doc.consultas.add(new Consulta("10:30",cal));
						doc.consultas.add(new Consulta("11:00",cal));
						doc.consultas.add(new Consulta("11:30",cal));
	  	  			
		  	  		}
		  	  		else if (j == 1){
		  	  			
		  	  			Calendar cal = new GregorianCalendar();
		  	  			
		  	  			while(cal.get(Calendar.DAY_OF_WEEK)!=Calendar.WEDNESDAY){
		  	  				cal.add(Calendar.DATE, 1);
		  	  			}
		  	  			
		  	  			doc.consultas.add(new Consulta("14:30",cal));
		  	  			doc.consultas.add(new Consulta("15:00",cal));
		  	  			doc.consultas.add(new Consulta("15:30",cal));
		  	  			doc.consultas.add(new Consulta("16:00",cal));
		  	  			doc.consultas.add(new Consulta("16:30",cal));
		  	  			
		  	  		}
		  	  		else if (j == 2){
		  	  			
		  	  			Calendar cal = new GregorianCalendar();
		  	  			
		  	  			while(cal.get(Calendar.DAY_OF_WEEK)!=Calendar.FRIDAY){
		  	  				cal.add(Calendar.DATE, 1);
		  	  			}
		  	  			
		  	  			doc.consultas.add(new Consulta("09:30",cal));
		  	  			doc.consultas.add(new Consulta("10:00",cal));
		  	  			doc.consultas.add(new Consulta("10:30",cal));
		  	  			doc.consultas.add(new Consulta("11:00",cal));
		  	  			doc.consultas.add(new Consulta("11:30",cal));
		  	  			
		  	  		}
		  	  		else if (j == 3){
		  	  			
		  	  			Calendar cal = new GregorianCalendar();
		  	  			
		  	  			while(cal.get(Calendar.DAY_OF_WEEK)!=Calendar.MONDAY){
		  	  				cal.add(Calendar.DATE, 1);
		  	  			}
		  	  			
		  	  			doc.consultas.add(new Consulta("09:30",cal));
		  	  			doc.consultas.add(new Consulta("10:00",cal));
		  	  			doc.consultas.add(new Consulta("10:30",cal));
		  	  			doc.consultas.add(new Consulta("11:00",cal));
		  	  			doc.consultas.add(new Consulta("11:30",cal));
		  	  			
		  	  		}
			  	  	else if (j == 4){
		  	  			
		  	  			Calendar cal = new GregorianCalendar();
		  	  			
		  	  			while(cal.get(Calendar.DAY_OF_WEEK)!=Calendar.WEDNESDAY){
		  	  				cal.add(Calendar.DATE, 1);
		  	  			}
		  	  			
		  	  			doc.consultas.add(new Consulta("14:30",cal));
		  	  			doc.consultas.add(new Consulta("15:00",cal));
		  	  			doc.consultas.add(new Consulta("15:30",cal));
		  	  			doc.consultas.add(new Consulta("16:00",cal));
		  	  			doc.consultas.add(new Consulta("16:30",cal));
		  	  			
		  	  		}
		  	  	}
		  	}
				
				
				// dorme durante uma semana
				try {
					sleep(604800000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		//	}
			
			
		}
		
	}
	
	
}
