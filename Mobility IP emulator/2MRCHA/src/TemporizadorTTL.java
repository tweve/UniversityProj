
public class TemporizadorTTL extends Thread {

	@Override
	public void run() {
		while(true){

			HomeAgentGUI.text.setText(HomeAgentGUI.text.getText()+"\n"+"Dispara temporizador TTL");

			for( MobilityBindingTableEntry mbte :HomeAgentGUI.MBT){
				mbte.setLifeTime(mbte.getLifeTime()-1);
				
			}
			
			try{
				for(int i = 0; i <HomeAgentGUI.MBT.size();i++){
					if (HomeAgentGUI.MBT.get(i).getLifeTime() == 0){
						HomeAgentGUI.MBT.remove(i);
					}
				}
			}catch(Exception e){
				
			}
			HomeAgentGUI.table.setModel(new javax.swing.table.DefaultTableModel(
					HomeAgentGUI.getTable(),
	                new String [] {
	                    "Node Name","Home Address", "Care of Address", "TTL"
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
