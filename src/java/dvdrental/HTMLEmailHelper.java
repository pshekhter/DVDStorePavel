
package dvdrental;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author susie
 */
public class HTMLEmailHelper {
    
    static final String email = "maryannsummer18@gmail.com";
    static final String username = "maryannsummer18@gmail.com";
    static final String password = "summeristhebestseason";
    static final String host = "smtp.gmail.com";
    static final int port = 465;
    
    private static Properties props = null;
    private static Session ses = null;
    private static SMTPAuthenticator auth = null;
    
    public static int send (String to, String subject, String body){
        int result = 0;
        
        props = new Properties();
        
        auth = new SMTPAuthenticator();
        
        ses = Session.getInstance(props, auth);
        
        MimeMessage msg = new MimeMessage(ses);
        try {
            msg.setContent(body, "text/html");
            msg.setSubject(subject);
            msg.setFrom(new InternetAddress(email));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            
            Transport transport = ses.getTransport("smtps");
            transport.connect(host, port, username, password);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
            
            result = 1;
        } catch (MessagingException e){
            e.printStackTrace();
        }
        
        return result;
    }
    
    public static class SMTPAuthenticator extends Authenticator {
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(HTMLEmailHelper.username, HTMLEmailHelper.password);
        }
    }
    
}
