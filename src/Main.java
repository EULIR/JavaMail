import mail.Mail;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws GeneralSecurityException {
		Scanner s = new Scanner(System.in);
		Mail mail = new Mail();
		System.out.println("Who would you like to send the email for?");
		mail.addTo(s.nextLine());
		System.out.println("What is your email address?");
		mail.addFrom(s.nextLine());
		System.out.println("Please enter the password of your email.");
		mail.enterPassword(s.nextLine());
		System.out.println("Do you want to send a copy to anyone else? Y/N");
		if (s.nextLine().equals("Y")) {
			System.out.println("Please enter email addresses(split with \",\")");
			String[] cc = s.nextLine().split(",");
			ArrayList<String> scc = new ArrayList<>();
			Collections.addAll(scc, cc);
			mail.setCC(scc);
		} else mail.setCC(new ArrayList<>());
		System.out.println("Do you want sent a copy blindly? Y/N");
		if (s.nextLine().equals("Y")) {
			System.out.println("Please enter email addresses(split with \",\").");
			String[] bcc = s.nextLine().split(",");
			ArrayList<String> sbcc = new ArrayList<>();
			Collections.addAll(sbcc, bcc);
			mail.setCC(sbcc);;
		} else mail.setBCC(new ArrayList<>());
		System.out.println("What is your subject of the email?");
		mail.addSubject(s.nextLine());
		System.out.println("Enter the content of your email.");
		mail.addText(s.nextLine());
		System.out.println("Do you want to add an attachment? Y/N");
		if (s.nextLine().equals("Y")) {
			mail.addAttachment(true);
			System.out.println("Please enter the absolute path of your file.");
			mail.addAttachment(s.nextLine());
		} else mail.addAttachment(false);
		System.out.println("You are almost done. Please wait patiently.");
		mail.send();
	}
}
