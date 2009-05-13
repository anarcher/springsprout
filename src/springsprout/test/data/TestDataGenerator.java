package springsprout.test.data;

import java.io.File;
import java.io.RandomAccessFile;

public class TestDataGenerator {

	public static void main(String[] args) throws Exception {
		for (int i = 0; i < 553; i++) {
			String data = i + ",\"whiteship200" + i + "\",\"keesun100" + i
					+ "\",\"123\"\n";
			append("./test/db/testDB.data", data);
		}
		System.out.println("complete!");
	}

	public static void append(String fileName, String text) throws Exception {
		File f = new File(fileName);
		long fileLength = f.length();
		RandomAccessFile raf = new RandomAccessFile(f, "rw");
		raf.seek(fileLength);
		raf.writeBytes(text);
		raf.close();
	}

}
