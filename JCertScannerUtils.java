/*    */ package jcertscanner;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.FileReader;
/*    */ import java.io.IOException;
/*    */ import java.io.PrintStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Hashtable;
/*    */ import java.util.List;
/*    */ 
/*    */ public class JCertScannerUtils
/*    */ {
/*    */   public static List<String> CreateListFromFile(String Filename)
/*    */   {
/* 21 */     List result = new ArrayList();
/*    */     try {
/* 23 */       BufferedReader input = new BufferedReader(new FileReader(Filename));
/*    */       try {
/* 25 */         String line = null;
/* 26 */         while ((line = input.readLine()) != null)
/* 27 */           result.add(line);
/*    */       }
/*    */       finally
/*    */       {
/* 31 */         input.close();
/*    */       }
/*    */     }
/*    */     catch (IOException ex) {
/* 35 */       ex.printStackTrace();
/*    */     }
/* 37 */     return result;
/*    */   }
/*    */ 
/*    */   public static List<String> CreateListFromSingleEntry(String Username) {
/* 41 */     List result = new ArrayList();
/* 42 */     result.add(Username);
/* 43 */     return result;
/*    */   }
/*    */ 
/*    */   public static String[] splitToPairs(String Line) {
/* 47 */     Line = Line.replace(" + ", ", ");
/* 48 */     ArrayList result = new ArrayList();
/* 49 */     boolean foundQwote = false;
/* 50 */     String pair = "";
/* 51 */     for (int idx = 0; idx < Line.length(); idx++) {
/* 52 */       if (Line.charAt(idx) == '"')
/* 53 */         foundQwote = !foundQwote;
/* 54 */       else if (Line.charAt(idx) == ',') {
/* 55 */         if (!foundQwote) {
/* 56 */           result.add(pair.trim());
/* 57 */           pair = "";
/*    */         } else {
/* 59 */           pair = pair + Line.charAt(idx);
/*    */         }
/*    */       }
/* 62 */       else pair = pair + Line.charAt(idx);
/*    */ 
/*    */     }
/*    */ 
/* 66 */     result.add(pair.trim());
/* 67 */     return (String[])result.toArray(new String[result.size()]);
/*    */   }
/*    */ 
/*    */   public static Hashtable<String, String> hastTableFromString(String certLine) {
/* 71 */     Hashtable result = new Hashtable();
/* 72 */     String[] pairs = splitToPairs(certLine);
/* 73 */     for (int idx = 0; idx < pairs.length; idx++) {
/*    */       try
/*    */       {
/* 76 */         String[] keyVal = pairs[idx].split("=");
/* 77 */         keyVal[0] = keyVal[0].trim();
/* 78 */         keyVal[1] = keyVal[1].trim();
/* 79 */         String line = keyVal[1];
/* 80 */         if (result.containsKey(keyVal[0])) {
/* 81 */           line = (String)result.get(keyVal[0]) + ", " + line;
/*    */         }
/* 83 */         result.put(keyVal[0], line);
/*    */       } catch (Exception ex) {
/* 85 */         System.out.println("ParseException ============================");
/* 86 */         System.out.println(pairs[idx]);
/* 87 */         System.out.println(ex.toString());
/*    */       }
/*    */     }
/* 90 */     return result;
/*    */   }
/*    */ }

/* Location:           /home/trowalts/tools/java/jd-gui-0.3.3.linux.i686/source/dist/jCertChecker.jar
 * Qualified Name:     jcertscanner.JCertScannerUtils
 * JD-Core Version:    0.6.0
 */