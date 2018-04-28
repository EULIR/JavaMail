package mail;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Properties;

public class SendEmail
{
	public static void main(String[] args) throws GeneralSecurityException
	{
		String to = "";
		String from = "";
		String host = "smtp.163.com";
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		properties.put("mail.smtp.auth", "true");
		MailSSLSocketFactory sf = new MailSSLSocketFactory();
		sf.setTrustAllHosts(true);
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.ssl.socketFactory", sf);
		Session session = Session.getDefaultInstance(properties, new Authenticator()
		{
			public PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication("", "");
			}
		});
		try
		{
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.addRecipient(Message.RecipientType.CC, new InternetAddress(from));
			message.setSubject("This is the Subject Line!");
			message.setText("This is actual message");
			Transport.send(message);
			System.out.println("Sent message successfully");
		} catch (MessagingException mex)
		{
			mex.printStackTrace();
		}
	}
}