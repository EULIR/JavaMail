import mail.Mail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

public class Batch {
	public static ArrayList<String> readAddress(String fileName) {
		ArrayList<String> list = new ArrayList<>();
		File file = new File(fileName);
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String tempString;
			while ((tempString = reader.readLine()) != null) {
				list.add(tempString);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
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
		ArrayList<String> to;
		to = readAddress("/mail_info/to.txt");
		String subject = readText("/mail_info/subject.txt");
		String content = readText("mail_info/content.txt");
		to.forEach(aTo -> {
			Mail mail = new Mail();
			mail.isHTML(false);
			mail.addFrom("");
			mail.enterPassword("");
			mail.addSubject(subject);
			mail.addText(content);
			mail.setCC(new ArrayList<>());
			mail.setBCC(new ArrayList<>());
			mail.addTo(aTo);
			mail.addAttachment("/mail_info/");
			try {
				mail.send();
			} catch (GeneralSecurityException e) {
				e.printStackTrace();
			}
		});
	}
}
