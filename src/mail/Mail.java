package mail;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Properties;

public class Mail {
	private String to;
	private String from;
	private String host;
	private String password;

	private ArrayList<String> CC;
	private ArrayList<String> BCC;

	private String subject;
	private String text;
	private boolean addAttachment;
	private String fileName;
	private boolean html;

	public Mail() {
		this.to = null;
		this.from = null;
		this.host = null;
	}

	public void addTo(String to) {
		this.to = to;
	}

	public void addFrom(String from) {
		this.from = from;
		this.host = "smtp." + from.substring(from.indexOf("@") + 1);
	}

	public void enterPassword(String password) {
		this.password = password;
	}

	public void addSubject(String subject) {
		this.subject = subject;
	}

	public void addText(String text) {
		this.text = text;
	}

	public void setCC(ArrayList<String> CC) {
		this.CC = CC;
	}

	public void setBCC(ArrayList<String> BCC) {
		this.BCC = BCC;
	}

	public void addAttachment(boolean addAttachment) {
		this.addAttachment = addAttachment;
	}

	public void addAttachment(String fileName) {
		this.fileName = fileName;
	}

	public void isHTML(boolean is) {
		html = is;
	}

	public void send() throws GeneralSecurityException {
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		properties.put("mail.smtp.auth", "true");
		MailSSLSocketFactory sf = new MailSSLSocketFactory();
		sf.setTrustAllHosts(true);
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.ssl.socketFactory", sf);
		Session session = Session.getDefaultInstance(properties, new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		});
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			if (this.CC.size() != 0)
				for (String aCC : this.CC)
					message.addRecipient(Message.RecipientType.CC, new InternetAddress(aCC));
			if (this.BCC.size() != 0)
				for (String aBCC : this.BCC)
					message.addRecipient(Message.RecipientType.BCC, new InternetAddress(aBCC));
			message.setSubject(subject);
			if (this.addAttachment) {
				BodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart.setText(text);
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart);
				messageBodyPart = new MimeBodyPart();
				DataSource source = new FileDataSource(fileName);
				messageBodyPart.setDataHandler(new DataHandler(source));
				messageBodyPart.setFileName(fileName);
				multipart.addBodyPart(messageBodyPart);
				if (html) message.setContent(multipart, "text/html");
				else message.setContent(multipart);
			} else {
				Multipart multipart = new MimeMultipart();
				BodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart.setText(text);
				multipart.addBodyPart(messageBodyPart);
				if (html) message.setContent(multipart, "text/html");
				else message.setContent(multipart);
			}
			Transport.send(message);
			System.out.println("Sent message successfully to " + to);
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}