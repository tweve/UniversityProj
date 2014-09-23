import java.io.IOException;

import mpi.MPI;

public class Buffer {

	int[][] matrix;
	int position;

	public Buffer() {
		matrix = new int[Main.BUFFERWINDOW][Main.RULE_SIZE];
		position = 0;
	}

	public boolean isFull() {
		if (position == matrix.length-1){
			return true;
		}
		return false;
	}

	public void setEmpty() {
		position = 0;
	}

	public void add(int[] input, int cathegory) {
		for (int i = 0;i<Main.INPUT_SIZE;i++){
			matrix[position][i] = input[i];
		}
		matrix[position][Main.INPUT_SIZE] = cathegory;
		position++;
	}

	public void send() {

		for (int pos = 0; pos<position;pos++){
			MPI.COMM_WORLD.Send(matrix[pos],0,Main.RULE_SIZE,MPI.INT,0,99);
		}
	}

}