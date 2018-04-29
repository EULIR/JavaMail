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
import java.util.Properties;

public class Mail
{
	private String[] to;
	private String from;
	private String host;
	private String password;

	private String[] CC;
	private String[] BCC;

	private String subject;
	private String text;
	private boolean addAttachment;
	private String fileName;

	public Mail()
	{
		this.to = null;
		this.from = null;
		this.host = null;
	}

	public void addTo(String[] to)
	{
		this.to = to;
	}

	public void addFrom(String from)
	{
		this.from = from;
		this.host = "smtp." + from.substring(from.indexOf("@") + 1);
	}

	public void enterPassword(String password)
	{
		this.password = password;
	}

	public void addSubject(String subject)
	{
		this.subject = subject;
	}

	public void addText(String text)
	{
		this.text = text;
	}

	public void setCC(String[] CC)
	{
		this.CC = CC;
	}

	public void setBCC(String[] BCC)
	{
		this.BCC = BCC;
	}

	public void addAttachment(boolean addAttachment)
	{
		this.addAttachment = addAttachment;
	}

	public void addAttachment(String fileName)
	{
		this.fileName = fileName;
	}

	public void send() throws GeneralSecurityException
	{
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
				return new PasswordAuthentication(from, password);
			}
		});
		try
		{
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			for (String aTo : to)
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(aTo));
			if (this.CC.length != 0)
				for (String aCC : this.CC)
					message.addRecipient(Message.RecipientType.CC, new InternetAddress(aCC));
			if (this.BCC.length != 0)
				for (String aBCC : this.BCC)
					message.addRecipient(Message.RecipientType.BCC, new InternetAddress(aBCC));
			message.setSubject(subject);
			if (this.addAttachment)
			{
				BodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart.setText(text);
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart);
				messageBodyPart = new MimeBodyPart();
				DataSource source = new FileDataSource(fileName);
				messageBodyPart.setDataHandler(new DataHandler(source));
				messageBodyPart.setFileName(fileName);
				multipart.addBodyPart(messageBodyPart);
				message.setContent(multipart);
			} else message.setText(text);
			Transport.send(message);
			System.out.println("Sent message successfully");
		} catch (MessagingException mex)
		{
			mex.printStackTrace();
		}
	}
}