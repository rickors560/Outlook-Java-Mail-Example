package org.rick;

import com.microsoft.aad.msal4j.ClientCredentialFactory;
import com.microsoft.aad.msal4j.ClientCredentialParameters;
import com.microsoft.aad.msal4j.ConfidentialClientApplication;
import com.microsoft.aad.msal4j.IAuthenticationResult;

import java.net.MalformedURLException;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author : Ritik Sharma
 * @created : 28-12-2022
 */
public class OAuthTokenGenerator {

    private static final String authUrl = "https://login.microsoftonline.com/";
    private static final String scope = "https://outlook.office365.com/.default";

    private OAuthTokenGenerator() {
    }

    public static String generateAccessToken(String clientId, String clientSecret, String tenantId) throws ExecutionException, InterruptedException, MalformedURLException {
        ConfidentialClientApplication app = ConfidentialClientApplication
                .builder(clientId, ClientCredentialFactory
                        .createFromSecret(clientSecret))
                .authority(authUrl + tenantId).build();

        ClientCredentialParameters clientCredentialParam = ClientCredentialParameters.builder(Set.of(scope)).build();
        CompletableFuture<IAuthenticationResult> future = app.acquireToken(clientCredentialParam);
        IAuthenticationResult result = future.get();
        return Objects.isNull(result) ? "" : result.accessToken();
    }
}
