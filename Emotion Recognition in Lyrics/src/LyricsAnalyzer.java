import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class LyricsAnalyzer {

	private String[] dividedLyrics;
	private String lyrics;
	private EmotionalReader reader;
	private Window window;
	static ArrayList<Song> songs = new ArrayList<Song>();



	public LyricsAnalyzer(String lyrics, Window mainWindow) {
		this.window = mainWindow;
		this.lyrics = lyrics;
	}

	public void run() {
		reader = new EmotionalReader();
		reader.trainEmotionalReader("DataSet");
		readSongsFile();
		transformLyrics();
		reader.classifyLyrics(lyrics);
		
		writeSongsFile();
	}

	private String[] divideLyrics(String lyrics) {
		String[] dividedLyrics;
		dividedLyrics = lyrics.split("\n");
		return dividedLyrics;
	}

	void transformLyrics(){

		String estrofes[] = lyrics.split("\n\n");
		lyrics = "";
		for (String estrofe:estrofes){
			estrofe = estrofe.replace("\n", " ");
			lyrics += estrofe + "\n";
		}

		System.out.println(lyrics);

	}

	public void writeSongsFile(){

	
		try{
			OutputStream file = new FileOutputStream( "songs.bin" );
			OutputStream buffer = new BufferedOutputStream( file );
			ObjectOutput output = new ObjectOutputStream( buffer );
			try{
				output.writeObject(songs);
			}
			finally{
				output.close();
			}
		}  
		catch(IOException ex){
			JOptionPane.showMessageDialog(window, "Could not save songs list", "Warning", JOptionPane.WARNING_MESSAGE);
		}
	}

	public void readSongsFile(){
		try{
			//use buffering
			InputStream file = new FileInputStream( "songs.bin" );
			InputStream buffer = new BufferedInputStream( file );
			ObjectInput input = new ObjectInputStream ( buffer );
			try{
				songs = (ArrayList<Song>)input.readObject();
				for(Song s:songs){
					System.out.println(s.getName());
				}
			}
			finally{
				input.close();
			}
		}
		catch(Exception ex){
			JOptionPane.showMessageDialog(window, "Could not load songs file", "Warning", JOptionPane.WARNING_MESSAGE);
		}
	}

}
