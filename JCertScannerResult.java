/*     */ package jcertscanner;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ 
/*     */ public class JCertScannerResult
/*     */ {
/*  16 */   private Date issueDate = null;
/*  17 */   private Date expireDate = null;
/*  18 */   private String hostName = "";
/*  19 */   private String commonName = "";
/*  20 */   private String organization = "";
/*  21 */   private String organizationUnit = "";
/*  22 */   private String country = "";
/*  23 */   private Boolean isValid = Boolean.valueOf(false);
/*  24 */   private Boolean isWeakCipher = Boolean.valueOf(false);
/*  25 */   private String issuerCommonName = "";
/*  26 */   private String issuerOrganization = "";
/*  27 */   private String issuerOrganizationalUnit = "";
/*  28 */   private String issuerCountry = "";
/*     */ 
/*     */   public JCertScannerResult(String hostName) {
/*  31 */     this.hostName = hostName;
/*     */   }
/*     */ 
/*     */   public String getHostName() {
/*  35 */     return this.hostName;
/*     */   }
/*     */ 
/*     */   public String getCommonName() {
/*  39 */     return this.commonName;
/*     */   }
/*     */ 
/*     */   public String getOrganization() {
/*  43 */     return this.organization;
/*     */   }
/*     */ 
/*     */   public String getOrganizationUnit() {
/*  47 */     return this.organizationUnit;
/*     */   }
/*     */ 
/*     */   public Boolean getValid() {
/*  51 */     return this.isValid;
/*     */   }
/*     */ 
/*     */   public Date getIssueDate() {
/*  55 */     return this.issueDate;
/*     */   }
/*     */ 
/*     */   public Date getExpireDate() {
/*  59 */     return this.expireDate;
/*     */   }
/*     */ 
/*     */   public Boolean getWeakCipher() {
/*  63 */     return this.isWeakCipher;
/*     */   }
/*     */ 
/*     */   public String getCountry() {
/*  67 */     return this.country;
/*     */   }
/*     */ 
/*     */   public String getIssuerCommonName() {
/*  71 */     return this.issuerCommonName;
/*     */   }
/*     */ 
/*     */   public String getIssuerOrganization() {
/*  75 */     return this.issuerOrganization;
/*     */   }
/*     */ 
/*     */   public String getIssuerOrganizationUnit() {
/*  79 */     return this.issuerOrganizationalUnit;
/*     */   }
/*     */ 
/*     */   public String getIssuerCountry() {
/*  83 */     return this.issuerCountry;
/*     */   }
/*     */ 
/*     */   public void setHostName(String hostName) {
/*  87 */     this.hostName = hostName;
/*     */   }
/*     */ 
/*     */   public void setCommonName(String commonName) {
/*  91 */     this.commonName = commonName;
/*     */   }
/*     */ 
/*     */   public void setOrganization(String organization) {
/*  95 */     this.organization = organization;
/*     */   }
/*     */ 
/*     */   public void setOrganizationUnit(String organizationUnit) {
/*  99 */     this.organizationUnit = organizationUnit;
/*     */   }
/*     */ 
/*     */   public void setValid(Boolean isValid) {
/* 103 */     this.isValid = isValid;
/*     */   }
/*     */ 
/*     */   public void setIssueDate(Date issueDate) {
/* 107 */     this.issueDate = issueDate;
/*     */   }
/*     */ 
/*     */   public void setExpireDate(Date expireDate) {
/* 111 */     this.expireDate = expireDate;
/*     */   }
/*     */ 
/*     */   public void setWeakCipher(Boolean isWeakCipher) {
/* 115 */     this.isWeakCipher = isWeakCipher;
/*     */   }
/*     */ 
/*     */   public void setCountry(String country) {
/* 119 */     this.country = country;
/*     */   }
/*     */ 
/*     */   public void setIssuerCommonName(String issuerName) {
/* 123 */     this.issuerCommonName = issuerName;
/*     */   }
/*     */ 
/*     */   public void setIssuerOrginazation(String orginazation) {
/* 127 */     this.issuerOrganization = orginazation;
/*     */   }
/*     */ 
/*     */   public void setIssuerOrginazationUnit(String orginazationalUnit) {
/* 131 */     this.issuerOrganizationalUnit = orginazationalUnit;
/*     */   }
/*     */ 
/*     */   public void setIssuerCountry(String country) {
/* 135 */     this.issuerCountry = country;
/*     */   }
/*     */ 
/*     */   public long daysStillValid() {
/* 139 */     if (!this.isValid.booleanValue()) {
/* 140 */       return 0L;
/*     */     }
/* 142 */     Calendar calendar1 = Calendar.getInstance();
/* 143 */     Calendar calendar2 = Calendar.getInstance();
/*     */ 
/* 145 */     calendar2.setTime(this.expireDate);
/*     */ 
/* 147 */     long milliseconds1 = calendar1.getTimeInMillis();
/* 148 */     long milliseconds2 = calendar2.getTimeInMillis();
/* 149 */     long diff = milliseconds2 - milliseconds1;
/*     */ 
/* 151 */     return diff / 86400000L;
/*     */   }
/*     */ 
/*     */   public String[] resultsToArray() {
/* 155 */     System.out.println(daysStillValid());
/* 156 */     String[] result = { this.hostName, this.commonName, this.organization, this.organizationUnit, this.issueDate.toString(), this.expireDate.toString(), this.isValid.toString(), this.isWeakCipher.toString() };
/*     */ 
/* 165 */     return result;
/*     */   }
/*     */ 
/*     */   public String getCertInfo() {
/* 169 */     String result = "";
/* 170 */     if (this.commonName.compareTo(this.hostName) != 0) {
/* 171 */       result = result + "[Medium] Hostname and Common name does not match.\n";
/*     */     }
/* 173 */     if (!this.isValid.booleanValue()) {
/* 174 */       result = result + "[High] Certificate has expired/is not valid.\n";
/*     */     }
/* 176 */     if (this.isWeakCipher.booleanValue()) {
/* 177 */       result = result + "[Medium] Weak SSL Ciphers supported.\n";
/*     */     }
/* 179 */     return result;
/*     */   }
/*     */ }

/* Location:           /home/trowalts/tools/java/jd-gui-0.3.3.linux.i686/source/dist/jCertChecker.jar
 * Qualified Name:     jcertscanner.JCertScannerResult
 * JD-Core Version:    0.6.0
 */