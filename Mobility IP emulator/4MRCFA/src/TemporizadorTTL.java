public class TemporizadorTTL extends Thread {

	@Override
	public void run() {
		while(true){

			FAGUI.textArea1.setText(FAGUI.textArea1.getText()+"\n"+"Dispara Temporizador TTL");
			for( VisitorListEntry vle :FAGUI.VL){
				vle.setTTL(vle.getTTL()-1);
			}
			try{
				for(int i = 0; i <FAGUI.VL.size();i++){
					if (FAGUI.VL.get(i).getTTL()==0)
						FAGUI.VL.remove(i);
				}
			}catch(Exception e){
				
			}
			
			 FAGUI.table.setModel(new javax.swing.table.DefaultTableModel(
			          
			        	FAGUI.getTable(),
			            new String [] {
			                "Home Address", "Home Agent Adress", "MN Mac Address", "LifeTime", "Temporary"
			            }
			        ));
			try {
				sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}

