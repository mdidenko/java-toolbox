package utils;

/*  MAVEN
    <dependency>
        <groupId>com.sun.mail</groupId>
        <artifactId>javax.mail</artifactId>
        <version>1.6.2</version>
    </dependency>
*/

import javax.mail.*;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.Instant;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

public class EmailUtils {

    public static class ImapsProtocol {

        private final String host, user, password;
        private Store store = null;

        public ImapsProtocol(final String user, final String password, final String host) {
            this.host = host;
            this.user = user;
            this.password = password;
        }

        private void connect() {
            this.disconnect();
            final Properties properties = new Properties();
            properties.put("mail.store.protocol", "imaps");
            try {
                this.store = Session.getInstance(properties).getStore();
                this.store.connect(this.host, this.user, this.password);
            } catch (MessagingException msgExc) {
                throw new RuntimeException("Session initialize error.", msgExc);
            }
        }

        private void disconnect() {
            if (this.store != null) {
                try {
                    this.store.close();
                } catch (MessagingException msgExc) {
                    throw new UnsupportedOperationException("Action on unconnected protocol.", msgExc);
                }
            }
        }

        public String getNewMessageContent(final int maxWaitTimeout) {
            this.connect();
            final String newMessageContent;
            try {
                final Folder inboxFolder = this.store.getFolder("INBOX");
                inboxFolder.open(Folder.READ_ONLY);
                final int inboxMessageCount = inboxFolder.getMessageCount();
                final long messageWaitStartTime = Instant.now().getEpochSecond();

                while (inboxFolder.getMessageCount() <= inboxMessageCount) {
                    if (Instant.now().getEpochSecond() > messageWaitStartTime + maxWaitTimeout) {
                        throw new TimeoutException("New inbox message wait timed out.");
                    }
                }

                final Message newMessage = inboxFolder.getMessage(inboxFolder.getMessageCount());
                final Multipart newMessageMultipart = (Multipart) newMessage.getContent();
                final BodyPart newMessageBodyPart = newMessageMultipart.getBodyPart(0);
                newMessageContent = newMessageBodyPart.getContent().toString();
                inboxFolder.close();
            } catch (MessagingException msgExc) {
                throw new UnsupportedOperationException(msgExc);
            } catch (IOException ioExc) {
                throw new UncheckedIOException("Message content is incorrect.", ioExc);
            } catch (TimeoutException timeOutExc) {
                throw new RuntimeException(timeOutExc);
            }
            this.disconnect();
            return newMessageContent;
        }
    }
}
