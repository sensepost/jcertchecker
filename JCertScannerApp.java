/*    */ package jcertscanner;
/*    */ 
/*    */ import java.awt.Window;
/*    */ import org.jdesktop.application.Application;
/*    */ import org.jdesktop.application.SingleFrameApplication;
/*    */ 
/*    */ public class JCertScannerApp extends SingleFrameApplication
/*    */ {
/*    */   protected void startup()
/*    */   {
/* 19 */     show(new JCertScannerView(this));
/*    */   }
/*    */ 
/*    */   protected void configureWindow(Window root)
/*    */   {
/*    */   }
/*    */ 
/*    */   public static JCertScannerApp getApplication()
/*    */   {
/* 35 */     return (JCertScannerApp)Application.getInstance(JCertScannerApp.class);
/*    */   }
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 42 */     JCertScannerSplash ss = new JCertScannerSplash(6000);
/* 43 */     launch(JCertScannerApp.class, args);
/*    */   }
/*    */ }

/* Location:           /home/trowalts/tools/java/jd-gui-0.3.3.linux.i686/source/dist/jCertChecker.jar
 * Qualified Name:     jcertscanner.JCertScannerApp
 * JD-Core Version:    0.6.0
 */