package utilities;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class Mail {


    public void sendMail() {

        String to = PathAndVariable.email;
        String from = "idhawan@twilio.com";
        String host = "";
        Properties properties = System.getProperties();

        properties.setProperty("mail.smtp.host", host);

        Session session = Session.getDefaultInstance(properties);
        try {

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            String filename = PathAndVariable.report_Name + "/" + PathAndVariable.tags + ".zip";
            String[] arr = filename.split("/");
            message.setSubject("Result for " + PathAndVariable.tags);
            BodyPart messageBodyPart1 = new MimeBodyPart();
            messageBodyPart1.setContent(Reporting.mailBody, "text/html");
            BodyPart messageBodyPart2 = new MimeBodyPart();
            DataSource source = new FileDataSource(filename);
            messageBodyPart2.setDataHandler(new DataHandler(source));
            messageBodyPart2.setFileName(arr[arr.length - 1]);
            String logFileName = PathAndVariable.log_name + "/" + PathAndVariable.tags + ".txt";
            String[] logArr = logFileName.split("/");
            Log4j2Config.logger.info("log file path.    "+logFileName);
            MimeBodyPart attachmentPart = new MimeBodyPart();
            DataSource source1 = new FileDataSource(logFileName);
            attachmentPart.setDataHandler(new DataHandler(source1));
            attachmentPart.setFileName(logArr[logArr.length - 1]);
            Multipart multipartObject = new MimeMultipart();
            multipartObject.addBodyPart(messageBodyPart1);
            multipartObject.addBodyPart(messageBodyPart2);
            multipartObject.addBodyPart(attachmentPart);
            if(Browser.isScreenShotAttach) {
                for (int i = 0; i< Browser.errorScreenShot_name.size(); i++) {
                    String ScreenshotName = Browser.errorScreenShot_name.get(i);
                    String[] screenshotArr = ScreenshotName.split("/");
                    Log4j2Config.logger.info("ScreenShot file path.    "+ScreenshotName);
                    MimeBodyPart screenShot = new MimeBodyPart();
                    DataSource source2 = new FileDataSource(ScreenshotName);
                    screenShot.setDataHandler(new DataHandler(source2));
                    screenShot.setFileName(screenshotArr[screenshotArr.length - 1]);
                    multipartObject.addBodyPart(screenShot);
                }
            }
            message.setContent(multipartObject);
            Transport.send(message);
            Log4j2Config.logger.info("Sent email successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}