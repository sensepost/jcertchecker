/*    */ package jcertscanner;
/*    */ 
/*    */ import java.awt.Container;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Toolkit;
/*    */ import java.net.URL;
/*    */ import javax.swing.ImageIcon;
/*    */ import javax.swing.JLabel;
/*    */ import javax.swing.JScrollPane;
/*    */ import javax.swing.JWindow;
/*    */ 
/*    */ public class JCertScannerSplash extends JWindow
/*    */ {
/*    */   public JCertScannerSplash(int time)
/*    */   {
/*    */     try
/*    */     {
/* 23 */       URL imageURL = getClass().getResource("splash.png");
/* 24 */       ImageIcon ii = new ImageIcon(imageURL);
/* 25 */       JScrollPane jsp = new JScrollPane(new JLabel(ii));
/* 26 */       getContentPane().add(jsp);
/* 27 */       setSize(387, 309);
/* 28 */       centerScreen();
/* 29 */       setVisible(true);
/* 30 */       if (time != 0)
/*    */         try {
/* 32 */           Thread.sleep(time);
/* 33 */           dispose();
/*    */         } catch (InterruptedException ie) {
/*    */         }
/*    */     }
/*    */     catch (Exception e) {
/* 38 */       dispose();
/*    */     }
/*    */   }
/*    */ 
/*    */   private void centerScreen() {
/* 43 */     Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
/* 44 */     int x = (int)((d.getWidth() - getWidth()) / 2.0D);
/* 45 */     int y = (int)((d.getHeight() - getHeight()) / 2.0D);
/* 46 */     setLocation(x, y);
/*    */   }
/*    */ }

/* Location:           /home/trowalts/tools/java/jd-gui-0.3.3.linux.i686/source/dist/jCertChecker.jar
 * Qualified Name:     jcertscanner.JCertScannerSplash
 * JD-Core Version:    0.6.0
 */