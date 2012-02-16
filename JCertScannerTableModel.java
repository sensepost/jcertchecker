/*    */ package jcertscanner;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.swing.table.DefaultTableModel;
/*    */ 
/*    */ public class JCertScannerTableModel extends DefaultTableModel
/*    */ {
/* 17 */   String[] columnNames = { "Hostname", "Common name", "Organization", "Organization Unit", "Valid", "Weak Ciphers", "Days left" };
/*    */ 
/* 21 */   List<JCertScannerResult> data = new ArrayList();
/*    */ 
/*    */   public int getRowCount()
/*    */   {
/* 25 */     if (this.data == null) {
/* 26 */       this.data = new ArrayList();
/*    */     }
/* 28 */     return this.data.size();
/*    */   }
/*    */ 
/*    */   public int getColumnCount()
/*    */   {
/* 33 */     return this.columnNames.length;
/*    */   }
/*    */ 
/*    */   public Object getValueAt(int rowIndex, int columnIndex)
/*    */   {
/* 38 */     JCertScannerResult row = (JCertScannerResult)this.data.get(rowIndex);
/* 39 */     if (row == null) {
/* 40 */       return null;
/*    */     }
/* 42 */     switch (columnIndex) { case 0:
/* 43 */       return row.getHostName();
/*    */     case 1:
/* 44 */       return row.getCommonName();
/*    */     case 2:
/* 45 */       return row.getOrganization();
/*    */     case 3:
/* 46 */       return row.getOrganizationUnit();
/*    */     case 4:
/* 47 */       return row.getValid();
/*    */     case 5:
/* 48 */       return row.getWeakCipher();
/*    */     case 6:
/* 49 */       return Long.valueOf(row.daysStillValid()); }
/* 50 */     return null;
/*    */   }
/*    */ 
/*    */   public void addRow(JCertScannerResult item)
/*    */   {
/* 55 */     this.data.add(item);
/* 56 */     fireTableDataChanged();
/*    */   }
/*    */ 
/*    */   public JCertScannerResult valueAt(int rowIndex) {
/* 60 */     return (JCertScannerResult)this.data.get(rowIndex);
/*    */   }
/*    */ 
/*    */   public String getColumnName(int col)
/*    */   {
/* 65 */     return this.columnNames[col];
/*    */   }
/*    */ }

/* Location:           /home/trowalts/tools/java/jd-gui-0.3.3.linux.i686/source/dist/jCertChecker.jar
 * Qualified Name:     jcertscanner.JCertScannerTableModel
 * JD-Core Version:    0.6.0
 */