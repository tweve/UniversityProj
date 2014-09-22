package desnecessarias;

import biblioteca.Emprestimo;
import java.util.GregorianCalendar;

public class Multa {
	private GregorianCalendar data;
	private float valor;
        private Emprestimo emp;

        Multa(GregorianCalendar d,float v ,Emprestimo emp){
            this.data= d;
            this.valor= v;
            this.emp = emp;
        }
        
        public Emprestimo getEmp(){
            return this.emp;
        }
        
	public GregorianCalendar getData() {
		return this.data;
	}

	public void setData(GregorianCalendar data) {
                this.data = data;
	}

	public float getValor() {
                return this.valor;
	}

	public void setValor(float valor) {
                this.valor = valor;
  	}
}