/*     */ package jcertscanner;
/*     */ 
/*     */ import java.awt.Container;
/*     */ import java.awt.Font;
/*     */ import java.awt.Frame;
/*     */ import javax.swing.ActionMap;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.GroupLayout.Alignment;
/*     */ import javax.swing.GroupLayout.ParallelGroup;
/*     */ import javax.swing.GroupLayout.SequentialGroup;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JRootPane;
/*     */ import javax.swing.LayoutStyle.ComponentPlacement;
/*     */ import org.jdesktop.application.Action;
/*     */ import org.jdesktop.application.Application;
/*     */ import org.jdesktop.application.ApplicationContext;
/*     */ import org.jdesktop.application.ResourceMap;
/*     */ 
/*     */ public class JCertScannerAboutBox extends JDialog
/*     */ {
/*     */   private JButton closeButton;
/*     */ 
/*     */   public JCertScannerAboutBox(Frame parent)
/*     */   {
/*  12 */     super(parent);
/*  13 */     initComponents();
/*  14 */     getRootPane().setDefaultButton(this.closeButton);
/*     */   }
/*  18 */   @Action
/*     */   public void closeAboutBox() { dispose();
/*     */   }
/*     */ 
/*     */   private void initComponents()
/*     */   {
/*  29 */     this.closeButton = new JButton();
/*  30 */     JLabel appTitleLabel = new JLabel();
/*  31 */     JLabel versionLabel = new JLabel();
/*  32 */     JLabel appVersionLabel = new JLabel();
/*  33 */     JLabel vendorLabel = new JLabel();
/*  34 */     JLabel appVendorLabel = new JLabel();
/*  35 */     JLabel homepageLabel = new JLabel();
/*  36 */     JLabel appHomepageLabel = new JLabel();
/*  37 */     JLabel appDescLabel = new JLabel();
/*  38 */     JLabel imageLabel = new JLabel();
/*     */ 
/*  40 */     setDefaultCloseOperation(2);
/*  41 */     ResourceMap resourceMap = ((JCertScannerApp)Application.getInstance(JCertScannerApp.class)).getContext().getResourceMap(JCertScannerAboutBox.class);
/*  42 */     setTitle(resourceMap.getString("title", new Object[0]));
/*  43 */     setModal(true);
/*  44 */     setName("aboutBox");
/*  45 */     setResizable(false);
/*     */ 
/*  47 */     ActionMap actionMap = ((JCertScannerApp)Application.getInstance(JCertScannerApp.class)).getContext().getActionMap(JCertScannerAboutBox.class, this);
/*  48 */     this.closeButton.setAction(actionMap.get("closeAboutBox"));
/*  49 */     this.closeButton.setName("closeButton");
/*     */ 
/*  51 */     appTitleLabel.setFont(appTitleLabel.getFont().deriveFont(appTitleLabel.getFont().getStyle() | 0x1, appTitleLabel.getFont().getSize() + 4));
/*  52 */     appTitleLabel.setText(resourceMap.getString("Application.title", new Object[0]));
/*  53 */     appTitleLabel.setName("appTitleLabel");
/*     */ 
/*  55 */     versionLabel.setFont(versionLabel.getFont().deriveFont(versionLabel.getFont().getStyle() | 0x1));
/*  56 */     versionLabel.setText(resourceMap.getString("versionLabel.text", new Object[0]));
/*  57 */     versionLabel.setName("versionLabel");
/*     */ 
/*  59 */     appVersionLabel.setText(resourceMap.getString("Application.version", new Object[0]));
/*  60 */     appVersionLabel.setName("appVersionLabel");
/*     */ 
/*  62 */     vendorLabel.setFont(vendorLabel.getFont().deriveFont(vendorLabel.getFont().getStyle() | 0x1));
/*  63 */     vendorLabel.setText(resourceMap.getString("vendorLabel.text", new Object[0]));
/*  64 */     vendorLabel.setName("vendorLabel");
/*     */ 
/*  66 */     appVendorLabel.setText(resourceMap.getString("Application.vendor", new Object[0]));
/*  67 */     appVendorLabel.setName("appVendorLabel");
/*     */ 
/*  69 */     homepageLabel.setFont(homepageLabel.getFont().deriveFont(homepageLabel.getFont().getStyle() | 0x1));
/*  70 */     homepageLabel.setText(resourceMap.getString("homepageLabel.text", new Object[0]));
/*  71 */     homepageLabel.setName("homepageLabel");
/*     */ 
/*  73 */     appHomepageLabel.setText(resourceMap.getString("Application.homepage", new Object[0]));
/*  74 */     appHomepageLabel.setName("appHomepageLabel");
/*     */ 
/*  76 */     appDescLabel.setText(resourceMap.getString("appDescLabel.text", new Object[0]));
/*  77 */     appDescLabel.setName("appDescLabel");
/*     */ 
/*  79 */     imageLabel.setIcon(resourceMap.getIcon("imageLabel.icon"));
/*  80 */     imageLabel.setName("imageLabel");
/*     */ 
/*  82 */     GroupLayout layout = new GroupLayout(getContentPane());
/*  83 */     getContentPane().setLayout(layout);
/*  84 */     layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(imageLabel).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(versionLabel).addComponent(vendorLabel).addComponent(homepageLabel)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(appVersionLabel).addComponent(appVendorLabel).addComponent(appHomepageLabel))).addComponent(appTitleLabel, GroupLayout.Alignment.LEADING).addComponent(appDescLabel, GroupLayout.Alignment.LEADING, -1, 317, 32767).addComponent(this.closeButton)).addContainerGap()));
/*     */ 
/* 105 */     layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(imageLabel, -2, 304, 32767).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(appTitleLabel).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(appDescLabel, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(versionLabel).addComponent(appVersionLabel)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(vendorLabel).addComponent(appVendorLabel)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(homepageLabel).addComponent(appHomepageLabel)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 150, 32767).addComponent(this.closeButton).addContainerGap()));
/*     */ 
/* 130 */     pack();
/*     */   }
/*     */ }

/* Location:           /home/trowalts/tools/java/jd-gui-0.3.3.linux.i686/source/dist/jCertChecker.jar
 * Qualified Name:     jcertscanner.JCertScannerAboutBox
 * JD-Core Version:    0.6.0
 */