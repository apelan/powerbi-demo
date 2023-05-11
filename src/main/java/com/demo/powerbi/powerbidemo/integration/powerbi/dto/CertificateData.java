package com.demo.powerbi.powerbidemo.integration.powerbi.dto;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CertificateData {

    private X509Certificate certificate;
    private PrivateKey privateKey;

}
