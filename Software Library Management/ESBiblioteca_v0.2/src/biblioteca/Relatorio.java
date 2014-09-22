package biblioteca;

import java.util.GregorianCalendar;
import java.util.ArrayList;
import java.util.Date;

public class Relatorio {
        private int id;
	private Date data;
        private ArrayList<String> info;
        Relatorio(ArrayList<String> in){
            
            GregorianCalendar temp = new GregorianCalendar();
            this.data= temp.getTime();
            setInfo(in);
        }
	
        
        /**
         * 
         * @return int id do relatorio
         */
        public int getId() {
            return id;
        }
        
        /**
         * 
         * @param id int id do relatorio
         */
        public void setId(int id) {
            this.id = id;
        }
        
        /**
         * 
         * @return Date data do relatorio
         */
        public Date getData() {
		return data;
	}

        /**
         * 
         * @param data Date data do relatorio
         */
        public void setData(Date data) {
                this.data = data;
	}
        
        /**
         * 
         * @return ArrayList de Strings com info
         */
        public ArrayList <String> getInfo() {
            return info;
        }
        
        /**
         * 
         * @param in ArrayList String info
         */
        public void setInfo(ArrayList<String> in) {
            this.info = in;
        }
}