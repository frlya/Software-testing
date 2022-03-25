package cvs.cv5.lab04;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author balikm1
 */
public class MailHelper {
   
    public void createAndSendMail(String to, String subject, String body) {
        Mail mail = new Mail(to, subject, body, false);
        DBManager.saveMail(mail);
        debug(mail);
    }

    private void debug(Mail mail) {
      if (!Configuration.isDebug) {
        (new Thread(() -> {
          sendMail(mail.getMailId());
        })).start();
      }
    }

    public Mail sendMail(int mailId)
    {
        try
        {
            // get entity
            Mail mail = DBManager.findMail(mailId);
            if (mail.isSent()) { return mail; }
            String from = "user@fel.cvut.cz";
            String smtpHostServer = "smtp.cvut.cz";
            Properties props = System.getProperties();
            props.put("mail.smtp.host", smtpHostServer);
            Session session = Session.getInstance(props, null);
            MimeMessage message = new MimeMessage(session);

            message.setFrom(from);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.getTo(), false));
            message.setSubject(mail.getSubject());
            message.setText(mail.getBody(), "UTF-8");

            // send
            Transport.send(message);
            mail.setIsSent(true);
            DBManager.saveMail(mail);
            return mail;
        }
        catch (Exception e) {
          e.printStackTrace();
        }
        return null;
    }
    
}
