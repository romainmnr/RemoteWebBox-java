package rwb.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbFileOutputStream;
import rwb.java.divers.Log;


public class CopyFiles {
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args ){
		final String userName = "vbox";
		final String pass = "vbox";
		final String sourcePath = "";
		final String destinationPath =" ";
		try {
			copyFileLocal2Network(userName, pass, sourcePath, destinationPath);
			//copyFileNetwork2Local(userName, password, sourcePath, destinationPath);
		} catch (IOException e) {
			Log.warning(e.getMessage());
		}
		System.out.println("The file has been copied using JCIFS");
	}

	/**
	 * 
	 * @param userName
	 * @param password
	 * @param sourcePath
	 * @param destinationPath
	 * @throws IOException
	 */
	public static void copyFileLocal2Network(final String userName,
			final String password, final String sourcePath,
			final String destinationPath) throws IOException {

		final String user = userName + ":" + password;
		final NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(
				user);
		final SmbFile sFile = new SmbFile(sourcePath, auth);

		final SmbFileOutputStream smbFileOutputStream = new SmbFileOutputStream(
				sFile);
		final FileInputStream fileInputStream = new FileInputStream(new File(
				destinationPath));

		byte[] buf = new byte[16 * 1024 * 1024];
		int len;
		while ((len = fileInputStream.read(buf)) > 0) {
			smbFileOutputStream.write(buf, 0, len);
		}
		fileInputStream.close();
		smbFileOutputStream.close();
	}

	/**
	 * 
	 * @param userName
	 * @param password
	 * @param sourcePath
	 * @param destinationPath
	 * @throws IOException
	 */
	public static void copyFileNetwork2Local(final String userName,
			final String password, final String sourcePath,
			final String destinationPath) throws IOException {

		final String user = userName + ":" + password;
		final NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(
				user);
		final File file = new File(destinationPath);

		final FileOutputStream FileOutputStream = new FileOutputStream(
				file);
		final SmbFileInputStream SmbFileInputStream = new SmbFileInputStream(new SmbFile(sourcePath, auth));

		byte[] buf = new byte[16 * 1024 * 1024];
		int len;
		while ((len = SmbFileInputStream.read(buf)) > 0) {
			FileOutputStream.write(buf, 0, len);
		}
		SmbFileInputStream.close();
		FileOutputStream.close();
	}
}

