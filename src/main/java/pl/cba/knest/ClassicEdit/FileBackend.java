package pl.cba.knest.ClassicEdit;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.bukkit.craftbukkit.libs.com.google.gson.JsonElement;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonParser;

public class FileBackend extends Backend {

	private File file;

	public FileBackend(File f) {
		this.file = f;
	}

	@Override
	public void close() {
		
	}

	public static Backend create(File datafolder) {
		File f = new File(datafolder, "creations.dat");
		FileBackend b = new FileBackend(f);
		return b;
	}

	public File getFile() {
		return file;
	}
	
	
	@Override
	public void save(String name) {
		try {
			if(!file.exists()){
				file.createNewFile();
			}
			
			FileWriter w = new FileWriter(file);
			w.write(creations.toString());
			w.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void load() {
		try {
			if(!file.exists()){
				file.createNewFile();
				return;
			}
			FileReader r = new FileReader(file);
			JsonParser p = new JsonParser();
			JsonElement e = p.parse(r);
			r.close();
			creations = e.getAsJsonObject();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
