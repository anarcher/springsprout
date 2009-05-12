package web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ResultPrint extends Thread {
	BufferedReader br = null;

	ResultPrint(InputStream is) {
		br = new BufferedReader(new InputStreamReader(is));
	}

	private void close() {
		try {
			if (br != null)
				br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}
}
