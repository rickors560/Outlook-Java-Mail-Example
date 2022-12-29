package org.rick;

import javax.mail.*;
import java.net.MalformedURLException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * @author : Ritik Sharma
 * @created : 28-12-2022
 */
public class JavaMailExample {
    public static void main(String[] args) throws MessagingException, MalformedURLException, ExecutionException, InterruptedException {
        final String outlookHost = "outlook.office365.com";

        final String clientId = "<YOUR_CLIENT_ID>";
        final String clientSecret = "<YOUR_CLIENT_SECRET>";
        final String tenantId = "<YOUR_TENANT_ID>";
        final String email = "<YOUR_EMAIL_ADDRESS>";

        String accessToken = OAuthTokenGenerator.generateAccessToken(clientId, clientSecret, tenantId);

        Store store = getIMAPStore();
        store.connect(outlookHost, email, accessToken);

        Folder folder = store.getFolder("Inbox");
        folder.open(Folder.READ_ONLY);
        int messageCount = folder.getMessageCount();
        System.out.printf("Total messages present in the folder 'Inbox' are %d.%n", messageCount);

        folder.close(false);
        store.close();
    }

    private static Store getIMAPStore() throws NoSuchProviderException {
        Properties props = getMailIMAPProperties();
        Session session = Session.getInstance(props);
        session.setDebug(true);
        return session.getStore("imaps");
    }

    private static Properties getMailIMAPProperties() {
        Properties props = new Properties();
        props.put("mail.imaps.auth.mechanisms", "XOAUTH2");
        props.put("mail.imaps.auth.plain.disable", "true");
        props.put("mail.imaps.auth.xoauth2.disable", "false");
        return props;
    }
}
