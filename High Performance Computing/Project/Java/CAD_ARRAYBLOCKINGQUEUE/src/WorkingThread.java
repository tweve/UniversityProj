import java.util.concurrent.ArrayBlockingQueue;


public class WorkingThread extends Thread {

	ArrayBlockingQueue<int[]> queue;
	Node raiz;

	public WorkingThread(ArrayBlockingQueue<int[]> queue, Node raiz) {
		this.queue = queue;
		this.raiz = raiz;
	}

	public void run(){

		int[] input;
		while(true){
			try {
				input = queue.take();
				if (input[0] == -1){
					break;
				}
				searchRule(raiz, input, 0);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}


	void searchRule(Node raiz, int[] input, int pos) {
		if (pos == Main.RULE_SIZE-1){
			if (raiz.map == null){
				String toWrite = "";

				for (Node link:raiz.links){
					for (int i = 0;i<Main.INPUT_SIZE;i++){
						toWrite += (input[i]+",");
					}
					toWrite += (link.Element+"\n");
					try{
						Singleton.getInstance().writeToFile(toWrite);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
			return;
		}

		int toSearch = input[pos];

		if (raiz.map == null){
			for (Node link:raiz.links){
				int linkValue = link.getElement();
				if (linkValue == toSearch || linkValue == 0){		
					searchRule(link,input,pos+1 );
				}
			}
		}
		else{
			Node temp = raiz.map.get((short)toSearch);
			if (temp != null)
				searchRule(temp,input,pos+1 );
			temp = raiz.map.get((short)0);
			if (temp != null)
				searchRule(temp,input,pos+1 );
		}

	}

}