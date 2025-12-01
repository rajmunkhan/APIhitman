package com.urlconsumer.urlconsume;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.*;

@Configuration
public class DisableSSL {
  @Bean
  public void disableSSLValidation() throws Exception {
    TrustManager[] trustAllCerts = new TrustManager[]{
        new X509TrustManager() {
          public java.security.cert.X509Certificate[] getAcceptedIssuers() { return null; }
          public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {}
          public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {}
        }
    };

    SSLContext sc = SSLContext.getInstance("SSL");
    sc.init(null, trustAllCerts, new java.security.SecureRandom());
    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
  }

}
