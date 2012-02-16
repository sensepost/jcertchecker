/*      */ package jcertscanner;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.awt.event.KeyAdapter;
/*      */ import java.awt.event.KeyEvent;
/*      */ import java.awt.event.MouseAdapter;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.io.BufferedWriter;
/*      */ import java.io.File;
/*      */ import java.io.FileWriter;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Date;
/*      */ import java.util.List;
/*      */ import javax.swing.ActionMap;
/*      */ import javax.swing.BorderFactory;
/*      */ import javax.swing.ButtonGroup;
/*      */ import javax.swing.GroupLayout;
/*      */ import javax.swing.GroupLayout.Alignment;
/*      */ import javax.swing.GroupLayout.ParallelGroup;
/*      */ import javax.swing.GroupLayout.SequentialGroup;
/*      */ import javax.swing.Icon;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JDialog;
/*      */ import javax.swing.JFileChooser;
/*      */ import javax.swing.JFrame;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JMenu;
/*      */ import javax.swing.JMenuBar;
/*      */ import javax.swing.JMenuItem;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.JPopupMenu.Separator;
/*      */ import javax.swing.JProgressBar;
/*      */ import javax.swing.JRadioButton;
/*      */ import javax.swing.JScrollPane;
/*      */ import javax.swing.JSeparator;
/*      */ import javax.swing.JSplitPane;
/*      */ import javax.swing.JTabbedPane;
/*      */ import javax.swing.JTable;
/*      */ import javax.swing.JTextArea;
/*      */ import javax.swing.JTextField;
/*      */ import javax.swing.LayoutStyle.ComponentPlacement;
/*      */ import javax.swing.ListSelectionModel;
/*      */ import javax.swing.SwingWorker;
/*      */ import javax.swing.Timer;
/*      */ import javax.swing.table.DefaultTableCellRenderer;
/*      */ import javax.swing.table.DefaultTableModel;
/*      */ import javax.swing.table.TableCellRenderer;
/*      */ import javax.swing.table.TableColumn;
/*      */ import javax.swing.table.TableColumnModel;
/*      */ import javax.swing.table.TableModel;
/*      */ import javax.swing.text.Document;
/*      */ import org.jdesktop.application.Action;
/*      */ import org.jdesktop.application.Application;
/*      */ import org.jdesktop.application.ApplicationContext;
/*      */ import org.jdesktop.application.FrameView;
/*      */ import org.jdesktop.application.ResourceMap;
/*      */ import org.jdesktop.application.SingleFrameApplication;
/*      */ import org.jdesktop.application.TaskMonitor;
/*      */ 
/*      */ public class JCertScannerView extends FrameView
/*      */ {
/*   39 */   private boolean mustStop = false;
/*      */   private ButtonGroup bgHostOptions;
/*      */   private JButton btnSave;
/*      */   private JButton btnSelectFile;
/*      */   private JButton btnStart;
/*      */   private JMenuItem exportMenuItem;
/*      */   private JLabel jLabel1;
/*      */   private JLabel jLabel10;
/*      */   private JLabel jLabel11;
/*      */   private JLabel jLabel12;
/*      */   private JLabel jLabel13;
/*      */   private JLabel jLabel2;
/*      */   private JLabel jLabel3;
/*      */   private JLabel jLabel4;
/*      */   private JLabel jLabel5;
/*      */   private JLabel jLabel6;
/*      */   private JLabel jLabel7;
/*      */   private JLabel jLabel8;
/*      */   private JLabel jLabel9;
/*      */   private JPanel jPanel1;
/*      */   private JPanel jPanel2;
/*      */   private JPanel jPanel3;
/*      */   private JPanel jPanel4;
/*      */   private JPanel jPanel5;
/*      */   private JPanel jPanel6;
/*      */   private JPanel jPanel7;
/*      */   private JPanel jPanel8;
/*      */   private JPanel jPanel9;
/*      */   private JScrollPane jScrollPane1;
/*      */   private JScrollPane jScrollPane2;
/*      */   private JPopupMenu.Separator jSeparator1;
/*      */   private JSplitPane jSplitPane1;
/*      */   private JTabbedPane jTabbedPane1;
/*      */   private JLabel lbCN;
/*      */   private JLabel lbCountry;
/*      */   private JLabel lbDaysLeft;
/*      */   private JLabel lbExpiresOn;
/*      */   private JLabel lbHostname;
/*      */   private JLabel lbIssuedOn;
/*      */   private JLabel lbIssuerCN;
/*      */   private JLabel lbIssuerCountry;
/*      */   private JLabel lbIssuerO;
/*      */   private JLabel lbIssuerOU;
/*      */   private JLabel lbO;
/*      */   private JLabel lbOU;
/*      */   private JPanel mainPanel;
/*      */   private JMenuBar menuBar;
/*      */   private JProgressBar progressBar;
/*      */   private JRadioButton rbHostList;
/*      */   private JRadioButton rbSingleHost;
/*      */   private JScrollPane spDebug;
/*      */   private JLabel statusAnimationLabel;
/*      */   private JLabel statusMessageLabel;
/*      */   private JPanel statusPanel;
/*      */   private JTable tblResults;
/*      */   private JTextArea txtCertInfo;
/*      */   private JTextArea txtDebug;
/*      */   private JTextField txtHostInput;
/*      */   private final Timer messageTimer;
/*      */   private final Timer busyIconTimer;
/*      */   private final Icon idleIcon;
/*  872 */   private final Icon[] busyIcons = new Icon[15];
/*  873 */   private int busyIconIndex = 0;
/*      */   private JDialog aboutBox;
/*      */ 
/*      */   public JCertScannerView(SingleFrameApplication app)
/*      */   {
/*   41 */     super(app);
/*      */ 
/*   43 */     initComponents();
/*      */ 
/*   45 */     for (int cnt = 0; cnt < this.tblResults.getColumnModel().getColumnCount(); cnt++) {
/*   46 */       this.tblResults.getColumnModel().getColumn(cnt).setCellRenderer(new CertStateRenderer());
/*      */     }
/*   48 */     this.bgHostOptions.add(this.rbHostList);
/*   49 */     this.bgHostOptions.add(this.rbSingleHost);
/*      */ 
/*   52 */     ResourceMap resourceMap = getResourceMap();
/*   53 */     int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout").intValue();
/*   54 */     this.messageTimer = new Timer(messageTimeout, new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*   56 */         JCertScannerView.this.statusMessageLabel.setText("");
/*      */       }
/*      */     });
/*   59 */     this.messageTimer.setRepeats(false);
/*   60 */     int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate").intValue();
/*   61 */     for (int i = 0; i < this.busyIcons.length; i++) {
/*   62 */       this.busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
/*      */     }
/*   64 */     this.busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*   66 */         JCertScannerView.access$102(JCertScannerView.this, (JCertScannerView.this.busyIconIndex + 1) % JCertScannerView.this.busyIcons.length);
/*   67 */         JCertScannerView.this.statusAnimationLabel.setIcon(JCertScannerView.this.busyIcons[JCertScannerView.this.busyIconIndex]);
/*      */       }
/*      */     });
/*   70 */     this.idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
/*   71 */     this.statusAnimationLabel.setIcon(this.idleIcon);
/*   72 */     this.progressBar.setVisible(false);
/*      */ 
/*   75 */     TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
/*   76 */     taskMonitor.addPropertyChangeListener(new PropertyChangeListener() {
/*      */       public void propertyChange(PropertyChangeEvent evt) {
/*   78 */         String propertyName = evt.getPropertyName();
/*   79 */         if ("started".equals(propertyName)) {
/*   80 */           if (!JCertScannerView.this.busyIconTimer.isRunning()) {
/*   81 */             JCertScannerView.this.statusAnimationLabel.setIcon(JCertScannerView.this.busyIcons[0]);
/*   82 */             JCertScannerView.access$102(JCertScannerView.this, 0);
/*   83 */             JCertScannerView.this.busyIconTimer.start();
/*      */           }
/*   85 */           JCertScannerView.this.progressBar.setVisible(true);
/*   86 */           JCertScannerView.this.progressBar.setIndeterminate(true);
/*   87 */         } else if ("done".equals(propertyName)) {
/*   88 */           JCertScannerView.this.busyIconTimer.stop();
/*   89 */           JCertScannerView.this.statusAnimationLabel.setIcon(JCertScannerView.this.idleIcon);
/*   90 */           JCertScannerView.this.progressBar.setVisible(false);
/*   91 */           JCertScannerView.this.progressBar.setValue(0);
/*   92 */         } else if ("message".equals(propertyName)) {
/*   93 */           String text = (String)(String)evt.getNewValue();
/*   94 */           JCertScannerView.this.statusMessageLabel.setText(text == null ? "" : text);
/*   95 */           JCertScannerView.this.messageTimer.restart();
/*   96 */         } else if ("progress".equals(propertyName)) {
/*   97 */           int value = ((Integer)(Integer)evt.getNewValue()).intValue();
/*   98 */           JCertScannerView.this.progressBar.setVisible(true);
/*   99 */           JCertScannerView.this.progressBar.setIndeterminate(false);
/*  100 */           JCertScannerView.this.progressBar.setValue(value);
/*      */         }
/*      */       } } );
/*      */   }
/*      */ 
/*      */   @Action
/*      */   public void showAboutBox() {
/*  108 */     if (this.aboutBox == null) {
/*  109 */       JFrame mainFrame = JCertScannerApp.getApplication().getMainFrame();
/*  110 */       this.aboutBox = new JCertScannerAboutBox(mainFrame);
/*  111 */       this.aboutBox.setLocationRelativeTo(mainFrame);
/*      */     }
/*  113 */     JCertScannerApp.getApplication().show(this.aboutBox);
/*      */   }
/*      */ 
/*      */   private void initComponents()
/*      */   {
/*  125 */     this.mainPanel = new JPanel();
/*  126 */     this.jPanel1 = new JPanel();
/*  127 */     this.rbSingleHost = new JRadioButton();
/*  128 */     this.rbHostList = new JRadioButton();
/*  129 */     this.jLabel1 = new JLabel();
/*  130 */     this.txtHostInput = new JTextField();
/*  131 */     this.btnSelectFile = new JButton();
/*  132 */     this.btnStart = new JButton();
/*  133 */     this.btnSave = new JButton();
/*  134 */     this.jPanel2 = new JPanel();
/*  135 */     this.jSplitPane1 = new JSplitPane();
/*  136 */     this.jPanel3 = new JPanel();
/*  137 */     this.jScrollPane1 = new JScrollPane();
/*  138 */     this.tblResults = new JTable();
/*  139 */     this.jPanel4 = new JPanel();
/*  140 */     this.jTabbedPane1 = new JTabbedPane();
/*  141 */     this.jPanel5 = new JPanel();
/*  142 */     this.jPanel7 = new JPanel();
/*  143 */     this.jLabel2 = new JLabel();
/*  144 */     this.lbHostname = new JLabel();
/*  145 */     this.jLabel3 = new JLabel();
/*  146 */     this.jLabel4 = new JLabel();
/*  147 */     this.jLabel5 = new JLabel();
/*  148 */     this.lbCN = new JLabel();
/*  149 */     this.lbO = new JLabel();
/*  150 */     this.lbOU = new JLabel();
/*  151 */     this.jLabel6 = new JLabel();
/*  152 */     this.jLabel7 = new JLabel();
/*  153 */     this.jLabel8 = new JLabel();
/*  154 */     this.lbDaysLeft = new JLabel();
/*  155 */     this.lbIssuedOn = new JLabel();
/*  156 */     this.lbExpiresOn = new JLabel();
/*  157 */     this.jLabel13 = new JLabel();
/*  158 */     this.lbCountry = new JLabel();
/*  159 */     this.jPanel8 = new JPanel();
/*  160 */     this.jLabel10 = new JLabel();
/*  161 */     this.jLabel11 = new JLabel();
/*  162 */     this.jLabel12 = new JLabel();
/*  163 */     this.lbIssuerCN = new JLabel();
/*  164 */     this.lbIssuerO = new JLabel();
/*  165 */     this.lbIssuerOU = new JLabel();
/*  166 */     this.jLabel9 = new JLabel();
/*  167 */     this.lbIssuerCountry = new JLabel();
/*  168 */     this.jPanel9 = new JPanel();
/*  169 */     this.jScrollPane2 = new JScrollPane();
/*  170 */     this.txtCertInfo = new JTextArea();
/*  171 */     this.jPanel6 = new JPanel();
/*  172 */     this.spDebug = new JScrollPane();
/*  173 */     this.txtDebug = new JTextArea();
/*  174 */     this.menuBar = new JMenuBar();
/*  175 */     JMenu fileMenu = new JMenu();
/*  176 */     this.exportMenuItem = new JMenuItem();
/*  177 */     this.jSeparator1 = new JPopupMenu.Separator();
/*  178 */     JMenuItem exitMenuItem = new JMenuItem();
/*  179 */     JMenu helpMenu = new JMenu();
/*  180 */     JMenuItem aboutMenuItem = new JMenuItem();
/*  181 */     this.statusPanel = new JPanel();
/*  182 */     JSeparator statusPanelSeparator = new JSeparator();
/*  183 */     this.statusMessageLabel = new JLabel();
/*  184 */     this.statusAnimationLabel = new JLabel();
/*  185 */     this.progressBar = new JProgressBar();
/*  186 */     this.bgHostOptions = new ButtonGroup();
/*      */ 
/*  188 */     this.mainPanel.setName("mainPanel");
/*      */ 
/*  190 */     ResourceMap resourceMap = ((JCertScannerApp)Application.getInstance(JCertScannerApp.class)).getContext().getResourceMap(JCertScannerView.class);
/*  191 */     this.jPanel1.setBorder(BorderFactory.createTitledBorder(resourceMap.getString("jPanel1.border.title", new Object[0])));
/*  192 */     this.jPanel1.setName("jPanel1");
/*      */ 
/*  194 */     this.rbSingleHost.setSelected(true);
/*  195 */     this.rbSingleHost.setText(resourceMap.getString("rbSingleHost.text", new Object[0]));
/*  196 */     this.rbSingleHost.setName("rbSingleHost");
/*      */ 
/*  198 */     this.rbHostList.setText(resourceMap.getString("rbHostList.text", new Object[0]));
/*  199 */     this.rbHostList.setName("rbHostList");
/*      */ 
/*  201 */     this.jLabel1.setText(resourceMap.getString("jLabel1.text", new Object[0]));
/*  202 */     this.jLabel1.setName("jLabel1");
/*      */ 
/*  204 */     this.txtHostInput.setText(resourceMap.getString("txtHostInput.text", new Object[0]));
/*  205 */     this.txtHostInput.setName("txtHostInput");
/*      */ 
/*  207 */     this.btnSelectFile.setText(resourceMap.getString("btnSelectFile.text", new Object[0]));
/*  208 */     this.btnSelectFile.setName("btnSelectFile");
/*  209 */     this.btnSelectFile.addMouseListener(new MouseAdapter() {
/*      */       public void mouseClicked(MouseEvent evt) {
/*  211 */         JCertScannerView.this.btnSelectFileMouseClicked(evt);
/*      */       }
/*      */     });
/*  215 */     this.btnStart.setText(resourceMap.getString("btnStart.text", new Object[0]));
/*  216 */     this.btnStart.setName("btnStart");
/*  217 */     this.btnStart.addMouseListener(new MouseAdapter() {
/*      */       public void mouseClicked(MouseEvent evt) {
/*  219 */         JCertScannerView.this.btnStartMouseClicked(evt);
/*      */       }
/*      */     });
/*  223 */     this.btnSave.setText(resourceMap.getString("btnSave.text", new Object[0]));
/*  224 */     this.btnSave.setName("btnSave");
/*  225 */     this.btnSave.addMouseListener(new MouseAdapter() {
/*      */       public void mouseClicked(MouseEvent evt) {
/*  227 */         JCertScannerView.this.btnSaveMouseClicked(evt);
/*      */       }
/*      */     });
/*  231 */     GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
/*  232 */     this.jPanel1.setLayout(jPanel1Layout);
/*  233 */     jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.rbSingleHost).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.rbHostList)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel1).addGap(6, 6, 6).addComponent(this.txtHostInput, -2, 301, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.btnSelectFile).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.btnStart).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.btnSave))).addContainerGap(350, 32767)));
/*      */ 
/*  254 */     jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.rbHostList).addComponent(this.rbSingleHost)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.txtHostInput, -2, -1, -2).addComponent(this.btnSelectFile).addComponent(this.btnStart).addComponent(this.jLabel1).addComponent(this.btnSave)).addContainerGap(-1, 32767)));
/*      */ 
/*  270 */     this.jPanel2.setBorder(BorderFactory.createEtchedBorder());
/*  271 */     this.jPanel2.setName("jPanel2");
/*      */ 
/*  273 */     this.jSplitPane1.setOrientation(0);
/*  274 */     this.jSplitPane1.setResizeWeight(1.0D);
/*  275 */     this.jSplitPane1.setName("jSplitPane1");
/*      */ 
/*  277 */     this.jPanel3.setMinimumSize(new Dimension(0, 100));
/*  278 */     this.jPanel3.setName("jPanel3");
/*  279 */     this.jPanel3.setPreferredSize(new Dimension(682, 150));
/*      */ 
/*  281 */     this.jScrollPane1.setName("jScrollPane1");
/*      */ 
/*  283 */     this.tblResults.setModel(new JCertScannerTableModel());
/*  284 */     this.tblResults.setColumnSelectionAllowed(true);
/*  285 */     this.tblResults.setName("tblResults");
/*  286 */     this.tblResults.addMouseListener(new MouseAdapter() {
/*      */       public void mouseClicked(MouseEvent evt) {
/*  288 */         JCertScannerView.this.tblSelectionChanged(evt);
/*      */       }
/*      */     });
/*  291 */     this.tblResults.addPropertyChangeListener(new PropertyChangeListener() {
/*      */       public void propertyChange(PropertyChangeEvent evt) {
/*  293 */         JCertScannerView.this.tblResultsPropertyChange(evt);
/*      */       }
/*      */     });
/*  296 */     this.tblResults.addKeyListener(new KeyAdapter() {
/*      */       public void keyTyped(KeyEvent evt) {
/*  298 */         JCertScannerView.this.tblResultsKeyTyped(evt);
/*      */       }
/*      */       public void keyPressed(KeyEvent evt) {
/*  301 */         JCertScannerView.this.tblResultsKeyPressed(evt);
/*      */       }
/*      */       public void keyReleased(KeyEvent evt) {
/*  304 */         JCertScannerView.this.tblResultsKeyReleased(evt);
/*      */       }
/*      */     });
/*  307 */     this.jScrollPane1.setViewportView(this.tblResults);
/*  308 */     this.tblResults.getColumnModel().getSelectionModel().setSelectionMode(1);
/*      */ 
/*  310 */     GroupLayout jPanel3Layout = new GroupLayout(this.jPanel3);
/*  311 */     this.jPanel3.setLayout(jPanel3Layout);
/*  312 */     jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 915, 32767).addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1, GroupLayout.Alignment.TRAILING, -1, 915, 32767)));
/*      */ 
/*  318 */     jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 167, 32767).addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1, GroupLayout.Alignment.TRAILING, -1, 167, 32767)));
/*      */ 
/*  325 */     this.jSplitPane1.setLeftComponent(this.jPanel3);
/*      */ 
/*  327 */     this.jPanel4.setName("jPanel4");
/*      */ 
/*  329 */     this.jTabbedPane1.setName("jTabbedPane1");
/*      */ 
/*  331 */     this.jPanel5.setName("jPanel5");
/*      */ 
/*  333 */     this.jPanel7.setBorder(BorderFactory.createTitledBorder(resourceMap.getString("jPanel7.border.title", new Object[0])));
/*  334 */     this.jPanel7.setName("jPanel7");
/*      */ 
/*  336 */     this.jLabel2.setText(resourceMap.getString("jLabel2.text", new Object[0]));
/*  337 */     this.jLabel2.setName("jLabel2");
/*      */ 
/*  339 */     this.lbHostname.setFont(resourceMap.getFont("lbOU.font"));
/*  340 */     this.lbHostname.setText(resourceMap.getString("lbHostname.text", new Object[0]));
/*  341 */     this.lbHostname.setName("lbHostname");
/*      */ 
/*  343 */     this.jLabel3.setText(resourceMap.getString("jLabel3.text", new Object[0]));
/*  344 */     this.jLabel3.setName("jLabel3");
/*      */ 
/*  346 */     this.jLabel4.setText(resourceMap.getString("jLabel4.text", new Object[0]));
/*  347 */     this.jLabel4.setName("jLabel4");
/*      */ 
/*  349 */     this.jLabel5.setText(resourceMap.getString("jLabel5.text", new Object[0]));
/*  350 */     this.jLabel5.setName("jLabel5");
/*      */ 
/*  352 */     this.lbCN.setFont(resourceMap.getFont("lbOU.font"));
/*  353 */     this.lbCN.setText(resourceMap.getString("lbCN.text", new Object[0]));
/*  354 */     this.lbCN.setName("lbCN");
/*      */ 
/*  356 */     this.lbO.setFont(resourceMap.getFont("lbOU.font"));
/*  357 */     this.lbO.setText(resourceMap.getString("lbO.text", new Object[0]));
/*  358 */     this.lbO.setMaximumSize(new Dimension(350, 13));
/*  359 */     this.lbO.setName("lbO");
/*      */ 
/*  361 */     this.lbOU.setFont(resourceMap.getFont("lbOU.font"));
/*  362 */     this.lbOU.setText(resourceMap.getString("lbOU.text", new Object[0]));
/*  363 */     this.lbOU.setName("lbOU");
/*  364 */     this.lbOU.setPreferredSize(new Dimension(350, 13));
/*      */ 
/*  366 */     this.jLabel6.setText(resourceMap.getString("jLabel6.text", new Object[0]));
/*  367 */     this.jLabel6.setName("jLabel6");
/*      */ 
/*  369 */     this.jLabel7.setText(resourceMap.getString("jLabel7.text", new Object[0]));
/*  370 */     this.jLabel7.setName("jLabel7");
/*      */ 
/*  372 */     this.jLabel8.setText(resourceMap.getString("jLabel8.text", new Object[0]));
/*  373 */     this.jLabel8.setName("jLabel8");
/*      */ 
/*  375 */     this.lbDaysLeft.setFont(resourceMap.getFont("lbOU.font"));
/*  376 */     this.lbDaysLeft.setText(resourceMap.getString("lbDaysLeft.text", new Object[0]));
/*  377 */     this.lbDaysLeft.setName("lbDaysLeft");
/*      */ 
/*  379 */     this.lbIssuedOn.setFont(resourceMap.getFont("lbOU.font"));
/*  380 */     this.lbIssuedOn.setText(resourceMap.getString("lbIssuedOn.text", new Object[0]));
/*  381 */     this.lbIssuedOn.setName("lbIssuedOn");
/*      */ 
/*  383 */     this.lbExpiresOn.setFont(resourceMap.getFont("lbOU.font"));
/*  384 */     this.lbExpiresOn.setText(resourceMap.getString("lbExpiresOn.text", new Object[0]));
/*  385 */     this.lbExpiresOn.setName("lbExpiresOn");
/*      */ 
/*  387 */     this.jLabel13.setText(resourceMap.getString("jLabel13.text", new Object[0]));
/*  388 */     this.jLabel13.setName("jLabel13");
/*      */ 
/*  390 */     this.lbCountry.setFont(resourceMap.getFont("lbOU.font"));
/*  391 */     this.lbCountry.setText(resourceMap.getString("lbCountry.text", new Object[0]));
/*  392 */     this.lbCountry.setName("lbCountry");
/*      */ 
/*  394 */     GroupLayout jPanel7Layout = new GroupLayout(this.jPanel7);
/*  395 */     this.jPanel7.setLayout(jPanel7Layout);
/*  396 */     jPanel7Layout.setHorizontalGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel7Layout.createSequentialGroup().addContainerGap().addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(jPanel7Layout.createSequentialGroup().addGap(21, 21, 21).addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jLabel2).addComponent(this.jLabel3).addComponent(this.jLabel4)).addGap(18, 18, 18).addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.lbHostname, -1, 353, 32767).addComponent(this.lbO, -1, -1, 32767).addComponent(this.lbCN, -1, -1, 32767))).addGroup(jPanel7Layout.createSequentialGroup().addComponent(this.jLabel5).addGap(18, 18, 18).addComponent(this.lbOU, -2, -1, -2))).addGap(31, 31, 31).addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jLabel7).addComponent(this.jLabel8).addComponent(this.jLabel6).addComponent(this.jLabel13)).addGap(18, 18, 18).addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.lbDaysLeft).addComponent(this.lbIssuedOn, -2, 227, -2).addComponent(this.lbExpiresOn, -2, 227, -2).addComponent(this.lbCountry)).addContainerGap()));
/*      */ 
/*  430 */     jPanel7Layout.setVerticalGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel7Layout.createSequentialGroup().addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel7Layout.createSequentialGroup().addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel6).addComponent(this.lbDaysLeft)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel7).addComponent(this.lbIssuedOn)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel8).addComponent(this.lbExpiresOn)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel13).addComponent(this.lbCountry))).addGroup(jPanel7Layout.createSequentialGroup().addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.lbHostname).addComponent(this.jLabel2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3).addComponent(this.lbCN)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel4).addComponent(this.lbO, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel5).addComponent(this.lbOU, -2, -1, -2)))).addContainerGap()));
/*      */ 
/*  469 */     this.jPanel8.setBorder(BorderFactory.createTitledBorder(resourceMap.getString("jPanel8.border.title", new Object[0])));
/*  470 */     this.jPanel8.setName("jPanel8");
/*      */ 
/*  472 */     this.jLabel10.setText(resourceMap.getString("jLabel10.text", new Object[0]));
/*  473 */     this.jLabel10.setName("jLabel10");
/*      */ 
/*  475 */     this.jLabel11.setText(resourceMap.getString("jLabel11.text", new Object[0]));
/*  476 */     this.jLabel11.setName("jLabel11");
/*      */ 
/*  478 */     this.jLabel12.setText(resourceMap.getString("jLabel12.text", new Object[0]));
/*  479 */     this.jLabel12.setName("jLabel12");
/*      */ 
/*  481 */     this.lbIssuerCN.setFont(resourceMap.getFont("lbOU.font"));
/*  482 */     this.lbIssuerCN.setText(resourceMap.getString("lbIssuerCN.text", new Object[0]));
/*  483 */     this.lbIssuerCN.setName("lbIssuerCN");
/*      */ 
/*  485 */     this.lbIssuerO.setFont(resourceMap.getFont("lbOU.font"));
/*  486 */     this.lbIssuerO.setText(resourceMap.getString("lbIssuerO.text", new Object[0]));
/*  487 */     this.lbIssuerO.setName("lbIssuerO");
/*      */ 
/*  489 */     this.lbIssuerOU.setFont(resourceMap.getFont("lbOU.font"));
/*  490 */     this.lbIssuerOU.setText(resourceMap.getString("lbIssuerOU.text", new Object[0]));
/*  491 */     this.lbIssuerOU.setName("lbIssuerOU");
/*      */ 
/*  493 */     this.jLabel9.setText(resourceMap.getString("jLabel9.text", new Object[0]));
/*  494 */     this.jLabel9.setName("jLabel9");
/*      */ 
/*  496 */     this.lbIssuerCountry.setFont(resourceMap.getFont("lbOU.font"));
/*  497 */     this.lbIssuerCountry.setText(resourceMap.getString("lbIssuerCountry.text", new Object[0]));
/*  498 */     this.lbIssuerCountry.setName("lbIssuerCountry");
/*      */ 
/*  500 */     GroupLayout jPanel8Layout = new GroupLayout(this.jPanel8);
/*  501 */     this.jPanel8.setLayout(jPanel8Layout);
/*  502 */     jPanel8Layout.setHorizontalGroup(jPanel8Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel8Layout.createSequentialGroup().addGap(34, 34, 34).addGroup(jPanel8Layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jLabel11).addComponent(this.jLabel10)).addGap(18, 18, 18).addGroup(jPanel8Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.lbIssuerO, -2, 296, -2).addComponent(this.lbIssuerCN, -2, 296, -2)).addGap(18, 18, 18).addGroup(jPanel8Layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jLabel12).addComponent(this.jLabel9)).addGap(18, 18, 18).addGroup(jPanel8Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.lbIssuerCountry).addComponent(this.lbIssuerOU, -2, 167, -2)).addContainerGap(68, 32767)));
/*      */ 
/*  523 */     jPanel8Layout.setVerticalGroup(jPanel8Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel8Layout.createSequentialGroup().addGroup(jPanel8Layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(jPanel8Layout.createSequentialGroup().addGroup(jPanel8Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel12).addComponent(this.lbIssuerOU)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel8Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel9).addComponent(this.lbIssuerCountry))).addGroup(jPanel8Layout.createSequentialGroup().addGroup(jPanel8Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel10).addComponent(this.lbIssuerCN)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel8Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel11).addComponent(this.lbIssuerO)))).addContainerGap(-1, 32767)));
/*      */ 
/*  546 */     this.jPanel9.setBorder(BorderFactory.createTitledBorder(resourceMap.getString("jPanel9.border.title", new Object[0])));
/*  547 */     this.jPanel9.setName("jPanel9");
/*      */ 
/*  549 */     this.jScrollPane2.setName("jScrollPane2");
/*      */ 
/*  551 */     this.txtCertInfo.setColumns(20);
/*  552 */     this.txtCertInfo.setFont(resourceMap.getFont("txtCertInfo.font"));
/*  553 */     this.txtCertInfo.setRows(5);
/*  554 */     this.txtCertInfo.setName("txtCertInfo");
/*  555 */     this.jScrollPane2.setViewportView(this.txtCertInfo);
/*      */ 
/*  557 */     GroupLayout jPanel9Layout = new GroupLayout(this.jPanel9);
/*  558 */     this.jPanel9.setLayout(jPanel9Layout);
/*  559 */     jPanel9Layout.setHorizontalGroup(jPanel9Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane2, -1, 891, 32767));
/*      */ 
/*  563 */     jPanel9Layout.setVerticalGroup(jPanel9Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane2, -1, 80, 32767));
/*      */ 
/*  568 */     GroupLayout jPanel5Layout = new GroupLayout(this.jPanel5);
/*  569 */     this.jPanel5.setLayout(jPanel5Layout);
/*  570 */     jPanel5Layout.setHorizontalGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jPanel7, -1, -1, 32767).addComponent(this.jPanel9, GroupLayout.Alignment.TRAILING, -1, -1, 32767).addComponent(this.jPanel8, -1, -1, 32767));
/*      */ 
/*  576 */     jPanel5Layout.setVerticalGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel5Layout.createSequentialGroup().addContainerGap().addComponent(this.jPanel7, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jPanel8, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jPanel9, -1, -1, 32767)));
/*      */ 
/*  587 */     this.jTabbedPane1.addTab(resourceMap.getString("jPanel5.TabConstraints.tabTitle", new Object[0]), this.jPanel5);
/*      */ 
/*  589 */     this.jPanel6.setName("jPanel6");
/*      */ 
/*  591 */     this.spDebug.setAutoscrolls(true);
/*  592 */     this.spDebug.setDoubleBuffered(true);
/*  593 */     this.spDebug.setFont(resourceMap.getFont("spDebug.font"));
/*  594 */     this.spDebug.setName("spDebug");
/*      */ 
/*  596 */     this.txtDebug.setColumns(20);
/*  597 */     this.txtDebug.setEditable(false);
/*  598 */     this.txtDebug.setFont(resourceMap.getFont("txtDebug.font"));
/*  599 */     this.txtDebug.setRows(5);
/*  600 */     this.txtDebug.setMaximumSize(new Dimension(2147483647, 280));
/*  601 */     this.txtDebug.setName("txtDebug");
/*  602 */     this.spDebug.setViewportView(this.txtDebug);
/*      */ 
/*  604 */     GroupLayout jPanel6Layout = new GroupLayout(this.jPanel6);
/*  605 */     this.jPanel6.setLayout(jPanel6Layout);
/*  606 */     jPanel6Layout.setHorizontalGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 903, 32767).addGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.spDebug, -1, 903, 32767)));
/*      */ 
/*  612 */     jPanel6Layout.setVerticalGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 353, 32767).addGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel6Layout.createSequentialGroup().addContainerGap().addComponent(this.spDebug, -1, 341, 32767))));
/*      */ 
/*  621 */     this.jTabbedPane1.addTab(resourceMap.getString("jPanel6.TabConstraints.tabTitle", new Object[0]), this.jPanel6);
/*      */ 
/*  623 */     GroupLayout jPanel4Layout = new GroupLayout(this.jPanel4);
/*  624 */     this.jPanel4.setLayout(jPanel4Layout);
/*  625 */     jPanel4Layout.setHorizontalGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 915, 32767).addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jTabbedPane1, -1, 915, 32767)));
/*      */ 
/*  631 */     jPanel4Layout.setVerticalGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 394, 32767).addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jTabbedPane1, -1, 394, 32767)));
/*      */ 
/*  638 */     this.jSplitPane1.setRightComponent(this.jPanel4);
/*      */ 
/*  640 */     GroupLayout jPanel2Layout = new GroupLayout(this.jPanel2);
/*  641 */     this.jPanel2.setLayout(jPanel2Layout);
/*  642 */     jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jSplitPane1, GroupLayout.Alignment.TRAILING, -1, 915, 32767));
/*      */ 
/*  646 */     jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jSplitPane1, GroupLayout.Alignment.TRAILING, -1, 567, 32767));
/*      */ 
/*  651 */     GroupLayout mainPanelLayout = new GroupLayout(this.mainPanel);
/*  652 */     this.mainPanel.setLayout(mainPanelLayout);
/*  653 */     mainPanelLayout.setHorizontalGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jPanel1, -1, -1, 32767).addComponent(this.jPanel2, -1, -1, 32767));
/*      */ 
/*  658 */     mainPanelLayout.setVerticalGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(mainPanelLayout.createSequentialGroup().addComponent(this.jPanel1, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jPanel2, -1, -1, 32767)));
/*      */ 
/*  666 */     this.menuBar.setName("menuBar");
/*      */ 
/*  668 */     fileMenu.setText(resourceMap.getString("fileMenu.text", new Object[0]));
/*  669 */     fileMenu.setName("fileMenu");
/*      */ 
/*  671 */     this.exportMenuItem.setText(resourceMap.getString("exportMenuItem.text", new Object[0]));
/*  672 */     this.exportMenuItem.setName("exportMenuItem");
/*  673 */     fileMenu.add(this.exportMenuItem);
/*      */ 
/*  675 */     this.jSeparator1.setName("jSeparator1");
/*  676 */     fileMenu.add(this.jSeparator1);
/*      */ 
/*  678 */     ActionMap actionMap = ((JCertScannerApp)Application.getInstance(JCertScannerApp.class)).getContext().getActionMap(JCertScannerView.class, this);
/*  679 */     exitMenuItem.setAction(actionMap.get("quit"));
/*  680 */     exitMenuItem.setName("exitMenuItem");
/*  681 */     fileMenu.add(exitMenuItem);
/*      */ 
/*  683 */     this.menuBar.add(fileMenu);
/*      */ 
/*  685 */     helpMenu.setText(resourceMap.getString("helpMenu.text", new Object[0]));
/*  686 */     helpMenu.setName("helpMenu");
/*      */ 
/*  688 */     aboutMenuItem.setAction(actionMap.get("showAboutBox"));
/*  689 */     aboutMenuItem.setName("aboutMenuItem");
/*  690 */     helpMenu.add(aboutMenuItem);
/*      */ 
/*  692 */     this.menuBar.add(helpMenu);
/*      */ 
/*  694 */     this.statusPanel.setName("statusPanel");
/*      */ 
/*  696 */     statusPanelSeparator.setName("statusPanelSeparator");
/*      */ 
/*  698 */     this.statusMessageLabel.setName("statusMessageLabel");
/*      */ 
/*  700 */     this.statusAnimationLabel.setHorizontalAlignment(2);
/*  701 */     this.statusAnimationLabel.setName("statusAnimationLabel");
/*      */ 
/*  703 */     this.progressBar.setName("progressBar");
/*      */ 
/*  705 */     GroupLayout statusPanelLayout = new GroupLayout(this.statusPanel);
/*  706 */     this.statusPanel.setLayout(statusPanelLayout);
/*  707 */     statusPanelLayout.setHorizontalGroup(statusPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(statusPanelSeparator, -1, 919, 32767).addGroup(statusPanelLayout.createSequentialGroup().addContainerGap().addComponent(this.statusMessageLabel).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 733, 32767).addComponent(this.progressBar, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.statusAnimationLabel).addContainerGap()));
/*      */ 
/*  719 */     statusPanelLayout.setVerticalGroup(statusPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(statusPanelLayout.createSequentialGroup().addComponent(statusPanelSeparator, -2, 2, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, 32767).addGroup(statusPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.statusMessageLabel).addComponent(this.statusAnimationLabel).addComponent(this.progressBar, -2, -1, -2)).addGap(3, 3, 3)));
/*      */ 
/*  731 */     setComponent(this.mainPanel);
/*  732 */     setMenuBar(this.menuBar);
/*  733 */     setStatusBar(this.statusPanel);
/*      */   }
/*      */ 
/*      */   private void btnStartMouseClicked(MouseEvent evt)
/*      */   {
/*  739 */     if (this.btnStart.getText().compareTo("Stop []") == 0) {
/*  740 */       this.mustStop = true;
/*  741 */       return;
/*      */     }
/*      */ 
/*  744 */     if (this.txtHostInput.getText().isEmpty()) {
/*  745 */       return;
/*      */     }
/*  747 */     List targetList = null;
/*  748 */     if (this.rbHostList.isSelected())
/*  749 */       targetList = JCertScannerUtils.CreateListFromFile(this.txtHostInput.getText());
/*      */     else {
/*  751 */       targetList = JCertScannerUtils.CreateListFromSingleEntry(this.txtHostInput.getText());
/*      */     }
/*  753 */     JCertScannerLogic.setWeakCiphers(LoadTextResource("weaklist.txt"));
/*  754 */     new certWorker(targetList).execute();
/*      */   }
/*      */ 
/*      */   private void btnSelectFileMouseClicked(MouseEvent evt)
/*      */   {
/*  759 */     this.txtHostInput.setText(SelectFile());
/*      */   }
/*      */ 
/*      */   private void btnSaveMouseClicked(MouseEvent evt) {
/*  763 */     String fileName = SaveFile();
/*  764 */     if (!fileName.isEmpty()) {
/*  765 */       TableModel model = this.tblResults.getModel();
/*  766 */       int rowCount = ((DefaultTableModel)model).getRowCount();
/*  767 */       int colCount = ((DefaultTableModel)model).getColumnCount();
/*      */       try {
/*  769 */         BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
/*  770 */         addDebug(String.format("Start writing to [%s]", new Object[] { fileName }));
/*  771 */         for (int rowIdx = 0; rowIdx < rowCount; rowIdx++) {
/*  772 */           String rowVal = "";
/*  773 */           for (int colIdx = 0; colIdx < colCount; colIdx++) {
/*  774 */             if (rowVal.isEmpty()) {
/*  775 */               rowVal = "\"" + ((DefaultTableModel)model).getValueAt(rowIdx, colIdx).toString() + "\"";
/*      */             }
/*  777 */             rowVal = rowVal + ",\"" + ((DefaultTableModel)model).getValueAt(rowIdx, colIdx) + "\"";
/*      */           }
/*  779 */           out.write(rowVal + "\n");
/*      */         }
/*  781 */         out.close();
/*  782 */         addDebug(String.format("Done writing to [%s]", new Object[] { fileName }));
/*      */       } catch (IOException ex) {
/*  784 */         addDebug(ex.toString());
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void tblResultsPropertyChange(PropertyChangeEvent evt)
/*      */   {
/*      */   }
/*      */ 
/*      */   private void tblSelectionChanged(MouseEvent evt) {
/*  794 */     setRowSelected();
/*      */   }
/*      */ 
/*      */   private void tblResultsKeyPressed(KeyEvent evt)
/*      */   {
/*      */   }
/*      */ 
/*      */   private void tblResultsKeyTyped(KeyEvent evt)
/*      */   {
/*      */   }
/*      */ 
/*      */   private void tblResultsKeyReleased(KeyEvent evt) {
/*  806 */     setRowSelected();
/*      */   }
/*      */ 
/*      */   public void addResult(JCertScannerResult result)
/*      */   {
/*  878 */     if (result != null) {
/*  879 */       TableModel model = this.tblResults.getModel();
/*  880 */       ((JCertScannerTableModel)model).addRow(result);
/*  881 */       this.tblResults.repaint();
/*      */     }
/*      */   }
/*      */ 
/*      */   public synchronized void scanDone() {
/*  886 */     this.btnStart.setText("Start >>");
/*  887 */     this.mustStop = false;
/*      */   }
/*      */ 
/*      */   public synchronized void addDebug(String message) {
/*  891 */     this.txtDebug.append(message + "\n");
/*  892 */     this.txtDebug.setCaretPosition(this.txtDebug.getDocument().getLength() - 1);
/*  893 */     System.out.println(message);
/*      */   }
/*      */ 
/*      */   private String SelectFile() {
/*  897 */     JFileChooser dlgFile = new JFileChooser();
/*  898 */     int returnVal = dlgFile.showOpenDialog(this.btnSelectFile);
/*  899 */     if (returnVal == 0) {
/*  900 */       File file = dlgFile.getSelectedFile();
/*  901 */       return file.getAbsolutePath();
/*      */     }
/*  903 */     return "";
/*      */   }
/*      */ 
/*      */   private String SaveFile() {
/*  907 */     JFileChooser dlgFile = new JFileChooser();
/*  908 */     int returnVal = dlgFile.showSaveDialog(this.btnSelectFile);
/*  909 */     if (returnVal == 0) {
/*  910 */       File file = dlgFile.getSelectedFile();
/*  911 */       return file.getAbsolutePath();
/*      */     }
/*  913 */     return "";
/*      */   }
/*      */ 
/*      */   private void setRowSelected() {
/*  917 */     this.txtCertInfo.setText("");
/*  918 */     JCertScannerTableModel model = (JCertScannerTableModel)this.tblResults.getModel();
/*  919 */     JCertScannerResult cert = model.valueAt(this.tblResults.getSelectedRow());
/*  920 */     this.lbHostname.setText(cert.getHostName());
/*  921 */     this.lbCN.setText(cert.getCommonName());
/*  922 */     this.lbO.setText(cert.getOrganization());
/*  923 */     this.lbOU.setText(cert.getOrganizationUnit());
/*  924 */     Color foreColor = Color.BLACK;
/*  925 */     if (cert.daysStillValid() <= 7L)
/*  926 */       foreColor = Color.RED;
/*  927 */     else if (cert.daysStillValid() <= 30L) {
/*  928 */       foreColor = Color.ORANGE;
/*      */     }
/*  930 */     this.lbDaysLeft.setForeground(foreColor);
/*  931 */     this.lbDaysLeft.setText(String.valueOf(cert.daysStillValid()));
/*  932 */     this.lbIssuedOn.setText(cert.getIssueDate().toString());
/*  933 */     this.lbExpiresOn.setText(cert.getExpireDate().toString());
/*  934 */     this.lbCountry.setText(cert.getCountry());
/*  935 */     this.lbIssuerCN.setText(cert.getIssuerCommonName());
/*  936 */     this.lbIssuerO.setText(cert.getIssuerOrganization());
/*  937 */     this.lbIssuerOU.setText(cert.getOrganizationUnit());
/*  938 */     this.lbIssuerCountry.setText(cert.getIssuerCountry());
/*  939 */     this.txtCertInfo.setText(cert.getCertInfo());
/*      */   }
/*      */ 
/*      */   private List<String> LoadTextResource(String resourceName)
/*      */   {
/* 1010 */     byte[] buf = new byte[8192];
/* 1011 */     List result = new ArrayList();
/*      */     try
/*      */     {
/* 1014 */       InputStream in = getClass().getResourceAsStream(resourceName);
/*      */       try {
/* 1016 */         int total = 0;
/*      */         while (true) {
/* 1018 */           int numRead = in.read(buf, total, buf.length - total);
/* 1019 */           if (numRead <= 0) {
/*      */             break;
/*      */           }
/* 1022 */           total += numRead;
/*      */         }
/* 1024 */         String[] content = new String(buf).trim().split("\n");
/* 1025 */         for (int idx = 0; idx < content.length; idx++)
/* 1026 */           result.add(content[idx]);
/*      */       }
/*      */       finally {
/* 1029 */         in.close();
/*      */       }
/*      */     }
/*      */     catch (IOException ex) {
/* 1033 */       ex.printStackTrace();
/*      */     }
/* 1035 */     return result;
/*      */   }
/*      */ 
/*      */   class certWorker extends SwingWorker<Object, Object>
/*      */   {
/*  980 */     private List<String> targets = null;
/*      */ 
/*      */     public certWorker() {
/*  983 */       this.targets = Targets;
/*      */     }
/*      */ 
/*      */     protected String doInBackground() throws Exception
/*      */     {
/*  988 */       JCertScannerView.this.btnStart.setText("Stop []");
/*  989 */       for (int idx = 0; (idx < this.targets.size()) && 
/*  990 */         (!JCertScannerView.this.mustStop); idx++)
/*      */       {
/*      */         try
/*      */         {
/*  994 */           JCertScannerResult result = JCertScannerLogic.checkCert(((String)this.targets.get(idx)).toString());
/*  995 */           JCertScannerView.this.addResult(result);
/*      */         } catch (Exception ex) {
/*  997 */           JCertScannerView.this.addDebug("Exception: [" + ((String)this.targets.get(idx)).toString() + "] : " + ex.toString());
/*      */         }
/*      */       }
/* 1000 */       return "Done";
/*      */     }
/*      */ 
/*      */     protected void done()
/*      */     {
/* 1005 */       JCertScannerView.this.scanDone();
/*      */     }
/*      */   }
/*      */ 
/*      */   class CertStateRenderer
/*      */     implements TableCellRenderer
/*      */   {
/*  943 */     public final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();
/*      */ 
/*      */     CertStateRenderer() {  }
/*      */ 
/*  946 */     public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) { Component renderer = this.DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
/*      */ 
/*  948 */       Color foreground = Color.BLACK;
/*  949 */       Color background = Color.WHITE;
/*  950 */       renderer.setForeground(foreground);
/*  951 */       renderer.setBackground(background);
/*      */ 
/*  953 */       JCertScannerTableModel model = (JCertScannerTableModel)JCertScannerView.this.tblResults.getModel();
/*  954 */       JCertScannerResult cert = model.valueAt(row);
/*  955 */       if (cert == null) {
/*  956 */         return renderer;
/*      */       }
/*  958 */       if (cert.getHostName().compareTo(cert.getCommonName()) != 0) {
/*  959 */         foreground = Color.ORANGE;
/*      */       }
/*  961 */       if (cert.getWeakCipher().booleanValue()) {
/*  962 */         foreground = Color.ORANGE;
/*      */       }
/*  964 */       if (cert.daysStillValid() < 30L) {
/*  965 */         foreground = Color.MAGENTA;
/*      */       }
/*  967 */       if (cert.daysStillValid() < 8L) {
/*  968 */         foreground = Color.ORANGE;
/*      */       }
/*  970 */       if (!cert.getValid().booleanValue()) {
/*  971 */         foreground = Color.RED;
/*      */       }
/*  973 */       renderer.setForeground(foreground);
/*  974 */       renderer.setBackground(background);
/*  975 */       return renderer;
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/trowalts/tools/java/jd-gui-0.3.3.linux.i686/source/dist/jCertChecker.jar
 * Qualified Name:     jcertscanner.JCertScannerView
 * JD-Core Version:    0.6.0
 */