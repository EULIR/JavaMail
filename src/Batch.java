import mail.Mail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

public class Batch {
	public static ArrayList<String[]> readAddress(String fileName) {
		ArrayList<String[]> list = new ArrayList<>();
		File file = new File(fileName);
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String tempString;
			while ((tempString = reader.readLine()) != null) {
				list.add(tempString.split(" "));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static String readFile(String fileName) {
		StringBuilder sb = new StringBuilder();
		File file = new File(fileName);
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String tempString;
			while ((tempString = reader.readLine()) != null) {
				sb.append(tempString).append("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static void writeFile(String name) throws IOException {
		String content = readFile("mail_info/content.txt");
		content = content.substring(content.indexOf('\n') + 1);
		Files.write(Paths.get("mail_info/content.txt"), ("Dear " + name + ":\n\n" + content).getBytes());
	}

	public static String readText(String fileName) {
		StringBuilder sb = new StringBuilder();
		File file = new File(fileName);
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String tempString;
			while ((tempString = reader.readLine()) != null) {
				sb.append(tempString).append("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		ArrayList<String[]> to = readAddress("mail_info/to.txt");
		String subject = readText("mail_info/subject.txt");
		to.forEach(aTo -> {
			Mail mail = new Mail();
			mail.isHTML(false);
			mail.addFrom("");
			mail.enterPassword("");
			mail.addSubject(subject);
			try {
				writeFile(aTo[1]);
			} catch (IOException e) {
				e.printStackTrace();
			}
			mail.addText(readText("mail_info/content.txt"));
			mail.setCC(new ArrayList<>());
			mail.setBCC(new ArrayList<>());
			mail.addTo(aTo[0]);
			mail.addAttachment("mail_info/attachment.txt");
			try {
				mail.send();
			} catch (GeneralSecurityException e) {
				e.printStackTrace();
			}
		});
	}
}
