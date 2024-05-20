/*    */ package generareFacturaAll;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import javax.swing.JButton;
/*    */ import javax.swing.JLabel;
/*    */ import javax.swing.SwingWorker;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RapProd
/*    */   extends SwingWorker<Boolean, String>
/*    */ {
/* 18 */   private GenFactura apl2 = new GenFactura();
/*    */   
/*    */   private final JLabel label;
/*    */   
/*    */   private final String numeFisierXML;
/*    */   
/*    */   public RapProd(JLabel progres, String numeFisierXML, JButton buton) {
/* 25 */     this.label = progres;
/* 26 */     this.numeFisierXML = numeFisierXML;
/* 27 */     this.buton = buton;
/*    */   }
/*    */   private String numeFisierPDF; private final JButton buton; String tDeclaratie;
/*    */   
/*    */   protected Boolean doInBackground() throws Exception {
/* 32 */     this.label.setForeground(Color.red);
/* 33 */     this.label.setText("Asteptati pana se genereaza fisierul PDF.......");
/* 34 */     this.tDeclaratie = this.apl2.identificaDeclaratie(this.numeFisierXML);
/*    */ 
/*    */     
/* 37 */     return Boolean.valueOf(true);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void done() {
/* 42 */     this.label.setForeground(Color.black);
/* 43 */     if (this.apl2.getError() == null) {
/* 44 */       this.label.setText("Raportul a fost generat cu succes: " + this.numeFisierPDF);
/* 45 */       this.buton.setVisible(true);
/*    */     } else {
/* 47 */       this.label.setText("Eroare generare fisier PDF: " + this.apl2.getError());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\e-facturare\GenerareFacturaAll\dist\generareFacturaAll.jar!\generareFacturaAll\RapProd.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */