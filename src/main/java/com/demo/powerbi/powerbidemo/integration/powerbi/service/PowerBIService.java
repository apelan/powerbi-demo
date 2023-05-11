package com.demo.powerbi.powerbidemo.integration.powerbi.service;

import com.demo.powerbi.powerbidemo.integration.powerbi.client.PowerBIClient;
import com.demo.powerbi.powerbidemo.integration.powerbi.dto.CertificateData;
import com.demo.powerbi.powerbidemo.integration.powerbi.dto.request.PowerBITokenRequest;
import com.demo.powerbi.powerbidemo.integration.powerbi.dto.response.PowerBIEmbedToken;
import com.demo.powerbi.powerbidemo.integration.powerbi.utility.PowerBIProperties;
import com.microsoft.aad.msal4j.ClientCredentialFactory;
import com.microsoft.aad.msal4j.ClientCredentialParameters;
import com.microsoft.aad.msal4j.ConfidentialClientApplication;
import com.microsoft.aad.msal4j.IAuthenticationResult;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.Iterator;

import jakarta.xml.bind.DatatypeConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PowerBIService {

    private final PowerBIProperties powerBIProperties;
    private final PowerBIClient powerBIClient;

    public PowerBIEmbedToken getToken(PowerBITokenRequest powerBITokenRequest) {
        return powerBIClient.getAccessToken(getMicrosoftAccessToken(), powerBITokenRequest);
    }

    private String getMicrosoftAccessToken() {
        CertificateData certificateData = getCertificateData(powerBIProperties.getCertificatePath());

        if(certificateData == null) {
            throw new RuntimeException("Certificate not found");
        }

        try {
            ConfidentialClientApplication app = ConfidentialClientApplication
                    .builder(powerBIProperties.getClientId(), ClientCredentialFactory
                            .createFromCertificate(certificateData.getPrivateKey(), certificateData.getCertificate()))
                    .authority(powerBIProperties.getAuthorityUrl() + powerBIProperties.getTenantId())
                    .build();

            ClientCredentialParameters clientCredentials = ClientCredentialParameters
                    .builder(Collections.singleton(powerBIProperties.getApiScope()))
                    .build();

            IAuthenticationResult result = app.acquireToken(clientCredentials).get();

            if(result != null && result.accessToken() != null) {
                return result.accessToken();
            }
        } catch (Exception e) {
            log.error("Error while retrieving Microsoft access token {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }

        return null;
    }

    private CertificateData getCertificateData(String filepath) {
        log.info("Extracting certificate data");

        try {
            String password = "";
            KeyStore keystore = KeyStore.getInstance("PKCS12");
            keystore.load(getInputStream(filepath), password.toCharArray());

            Iterator<String> iterator = keystore.aliases().asIterator();
            while (iterator.hasNext()) {
                String alias = iterator.next();

                CertificateData certificateData = new CertificateData();

                Certificate certificate = keystore.getCertificate(alias);
                if(certificate instanceof X509Certificate) {
                    log.info("Certificate successfully extracted");
                    certificateData.setCertificate((X509Certificate) certificate);
                }

                Key key = keystore.getKey(alias, password.toCharArray());
                if (key instanceof PrivateKey) {
                    log.info("Private key successfully extracted");
                    certificateData.setPrivateKey((PrivateKey) key);
                }

                if(certificateData.getCertificate() != null && certificateData.getPrivateKey() != null) {
                    return certificateData;
                }
            }
        } catch (Exception e) {
            log.error("Error while extracting certificate data {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }

        return null;
    }

    private InputStream getInputStream(String filepath) {
        ClassPathResource classPathResource = new ClassPathResource(filepath);
        try {
            return classPathResource.getInputStream();
        } catch (IOException e) {
            log.error("Can't read input stream from file {}", filepath);
            throw new RuntimeException(e);
        }
    }

    private static String getThumbprint(X509Certificate cert) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(cert.getEncoded());
        return DatatypeConverter.printHexBinary(md.digest()).toLowerCase();
    }

}
