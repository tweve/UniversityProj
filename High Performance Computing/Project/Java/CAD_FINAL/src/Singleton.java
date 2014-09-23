import java.io.IOException;

public class Singleton {
    private static final Singleton inst= new Singleton();

    private Singleton() {
        super();
    }

    public synchronized void writeToFile(String str) {
        try {
			Main.bw.write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    public static Singleton getInstance() {
        return inst;
    }

}