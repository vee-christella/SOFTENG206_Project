package assignment4.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import assignment4.NameSayerApp;

/**
 * Helper functions concerning files
 * 
 * @author Dhruv Phadnis, Vanessa Ciputra
 */
public class FileUtils {
	/**
	 * Reads whole file as string
	 * @param file to read
	 * @return file contents
	 */
	@SuppressWarnings("resource")
	public static String readFile(File file) {
		try (Scanner scanner = new Scanner(file).useDelimiter("\\Z")) { // Reads whole file
			return scanner.next();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Inspired by www.codejava.net/java-se/file-io/programmatically-extract-a-zip-file-using-java
	 */
	public static void unzip(String zipFilePath, String destDirectory) throws IOException {
		File destDir = new File(destDirectory);
		if (!destDir.exists()) {
			destDir.mkdir();
		}
		ZipInputStream zipIn = new ZipInputStream(NameSayerApp.class.getResourceAsStream(zipFilePath));

		ZipEntry entry = zipIn.getNextEntry();
		while (entry != null) {
			String filePath = destDirectory + entry.getName();
			if (!entry.isDirectory()) {
				new File(filePath).getParentFile().mkdirs();
				extractFile(zipIn, filePath);
			} else {
				File dir = new File(filePath);
				dir.mkdirs();
			}
			zipIn.closeEntry();
			entry = zipIn.getNextEntry();
		}
		zipIn.close();
	}

	private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
		byte[] bytesIn = new byte[4 * 1024];
		int read = 0;
		while ((read = zipIn.read(bytesIn)) != -1) {
			bos.write(bytesIn, 0, read);
		}
		bos.close();
	}
}
