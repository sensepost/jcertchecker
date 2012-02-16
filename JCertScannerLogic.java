/*     */ package jcertscanner;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.KeyManagementException;
/*     */ import java.security.KeyStore;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.Principal;
/*     */ import java.security.SecureRandom;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.CertificateExpiredException;
/*     */ import java.security.cert.CertificateNotYetValidException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.Hashtable;
/*     */ import java.util.List;
/*     */ import javax.net.SocketFactory;
/*     */ import javax.net.ssl.SSLContext;
/*     */ import javax.net.ssl.SSLSession;
/*     */ import javax.net.ssl.SSLSocket;
/*     */ import javax.net.ssl.SSLSocketFactory;
/*     */ import javax.net.ssl.TrustManager;
/*     */ import javax.net.ssl.TrustManagerFactory;
/*     */ import javax.net.ssl.X509TrustManager;
/*     */ 
/*     */ public class JCertScannerLogic
/*     */ {
/*  31 */   private static List<String> weakCiphers = null;
/*     */ 
/*     */   public static void setWeakCiphers(List<String> weakCiphers) {
/*  34 */     weakCiphers = weakCiphers;
/*     */   }
/*     */ 
/*     */   private static Certificate[] getCertFromHost(String hostName) throws NoSuchAlgorithmException, KeyManagementException, IOException
/*     */   {
/*  39 */     Certificate[] resultCert = null;
/*  40 */     System.out.println(String.format("Getting cert: %s", new Object[] { hostName }));
/*  41 */     TrustManager[] trustAllCerts = { new X509TrustManager()
/*     */     {
/*     */       public X509Certificate[] getAcceptedIssuers() {
/*  44 */         return null;
/*     */       }
/*     */ 
/*     */       public void checkClientTrusted(X509Certificate[] certs, String authType)
/*     */       {
/*     */       }
/*     */ 
/*     */       public void checkServerTrusted(X509Certificate[] certs, String authType)
/*     */       {
/*     */       }
/*     */     }
/*     */      };
/*  56 */     SSLContext sc = SSLContext.getInstance("SSL");
/*  57 */     sc.init(null, trustAllCerts, new SecureRandom());
/*  58 */     SocketFactory factory = sc.getSocketFactory();
/*     */ 
/*  60 */     SSLSocket httpsCon = (SSLSocket)factory.createSocket();
/*  61 */     httpsCon.setSoTimeout(20000);
/*  62 */     httpsCon.connect(new InetSocketAddress(hostName, 443), 10000);
/*  63 */     httpsCon.startHandshake();
/*  64 */     resultCert = httpsCon.getSession().getPeerCertificates();
/*     */ 
/*  66 */     return resultCert;
/*     */   }
/*     */ 
/*     */   private static boolean checkForWeakCipher(String hostName)
/*     */   {
/*     */     try {
/*  72 */       SSLSocketFactory f = (SSLSocketFactory)SSLSocketFactory.getDefault();
/*  73 */       SSLSocket sslSoc = (SSLSocket)f.createSocket(hostName, 443);
/*     */ 
/*  76 */       String[] supportedCiphers = sslSoc.getSupportedCipherSuites();
/*     */ 
/*  78 */       sslSoc.getEnabledProtocols();
/*  79 */       for (int weakIdx = 0; weakIdx < weakCiphers.size(); weakIdx++) {
/*  80 */         boolean canKeep = false;
/*     */ 
/*  82 */         for (int supIdx = 0; supIdx < supportedCiphers.length; supIdx++) {
/*  83 */           if (((String)weakCiphers.get(weakIdx)).compareTo(supportedCiphers[supIdx]) > 0) {
/*  84 */             canKeep = true;
/*  85 */             break;
/*     */           }
/*     */         }
/*  88 */         if (!canKeep) {
/*  89 */           System.out.println("Unsupported weak cipher: " + (String)weakCiphers.get(weakIdx) + " disregarding...");
/*  90 */           weakCiphers.remove(weakIdx);
/*     */         }
/*     */       }
/*  93 */       String[] weakCiphersList = (String[])weakCiphers.toArray(new String[weakCiphers.size()]);
/*  94 */       sslSoc.setEnabledCipherSuites(weakCiphersList);
/*  95 */       sslSoc.startHandshake();
/*     */ 
/*  97 */       return true;
/*     */     } catch (Exception ex) {
/*  99 */       System.out.println(ex.toString());
/*     */     }
/* 101 */     return false;
/*     */   }
/*     */ 
/*     */   public static JCertScannerResult checkCert(String host) throws NoSuchAlgorithmException, KeyManagementException, IOException {
/* 105 */     JCertScannerResult result = null;
/* 106 */     Certificate[] certs = getCertFromHost(host);
/* 107 */     int cIdx = 0; if (cIdx < certs.length) {
/* 108 */       X509Certificate cert = (X509Certificate)certs[cIdx];
/* 109 */       System.out.println("SubjectDN: " + cert.getSubjectDN().getName());
/* 110 */       System.out.println("IssuerDN : " + cert.getIssuerDN().getName());
/* 111 */       result = new JCertScannerResult(host);
/* 112 */       result.setExpireDate(cert.getNotAfter());
/* 113 */       result.setIssueDate(cert.getNotBefore());
/* 114 */       Hashtable certDetail = JCertScannerUtils.hastTableFromString(((X509Certificate)certs[0]).getSubjectDN().getName());
/*     */ 
/* 116 */       Hashtable issuerDetail = JCertScannerUtils.hastTableFromString(((X509Certificate)certs[0]).getIssuerDN().getName());
/*     */ 
/* 118 */       result.setCommonName((String)certDetail.get("CN"));
/* 119 */       result.setOrganization((String)certDetail.get("O"));
/* 120 */       result.setOrganizationUnit((String)certDetail.get("OU"));
/*     */       try {
/* 122 */         cert.checkValidity();
/* 123 */         result.setValid(Boolean.TRUE);
/*     */       } catch (CertificateExpiredException ceEx) {
/* 125 */         System.out.println("Cert invalid");
/*     */       } catch (CertificateNotYetValidException cnyvEx) {
/* 127 */         System.out.println("Cert invalid");
/*     */       }
/* 129 */       result.setCountry((String)certDetail.get("C"));
/* 130 */       result.setIssuerCommonName((String)issuerDetail.get("CN"));
/* 131 */       result.setIssuerOrginazation((String)issuerDetail.get("O"));
/* 132 */       result.setIssuerOrginazationUnit((String)issuerDetail.get("OU"));
/* 133 */       result.setIssuerCountry((String)issuerDetail.get("C"));
/*     */       try {
/* 135 */         result.setWeakCipher(Boolean.valueOf(checkForWeakCipher(result.getHostName())));
/*     */       } catch (Exception ex) {
/* 137 */         System.out.println(ex.toString());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 142 */     return result;
/*     */   }
/*     */   private class CertChecker implements X509TrustManager {
/*     */     private final X509TrustManager defaultTM;
/*     */ 
/*     */     public CertChecker() throws GeneralSecurityException {
/* 149 */       TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
/* 150 */       tmf.init((KeyStore)null);
/* 151 */       this.defaultTM = ((X509TrustManager)tmf.getTrustManagers()[0]);
/*     */     }
/*     */ 
/*     */     public void checkServerTrusted(X509Certificate[] certs, String authType) {
/* 155 */       if (this.defaultTM != null)
/*     */         try {
/* 157 */           this.defaultTM.checkServerTrusted(certs, authType);
/* 158 */           System.out.println("Certificate valid");
/*     */         } catch (CertificateException ex) {
/* 160 */           System.out.println("Certificate invalid: " + ex.getMessage());
/*     */         }
/*     */     }
/*     */ 
/*     */     public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException
/*     */     {
/* 166 */       throw new UnsupportedOperationException("Not supported yet.");
/*     */     }
/*     */ 
/*     */     public X509Certificate[] getAcceptedIssuers() {
/* 170 */       return null;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/trowalts/tools/java/jd-gui-0.3.3.linux.i686/source/dist/jCertChecker.jar
 * Qualified Name:     jcertscanner.JCertScannerLogic
 * JD-Core Version:    0.6.0
 */