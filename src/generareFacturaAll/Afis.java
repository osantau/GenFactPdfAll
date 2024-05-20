/*    */ package generareFacturaAll;
/*    */ 
/*    */ import java.io.BufferedWriter;
/*    */ import java.io.FileWriter;
/*    */ import java.io.IOException;
/*    */ import javax.swing.JFrame;
/*    */ import javax.xml.parsers.ParserConfigurationException;
/*    */ import net.sf.jasperreports.engine.JRException;
/*    */ import org.xml.sax.SAXException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Afis
/*    */   extends JFrame
/*    */ {
/*    */   public static void main(String[] args) {
/* 38 */     GenFactura apl2 = new GenFactura();
/* 39 */     String declaratie = "";
/* 40 */     String erori = "";
/*    */     try {
             if (args.length >0) 
             {                 
                 apl2.generarePDF(args[0],true);
              } else{
               apl2.generarePDF();  
             }
/* 42 */       
/* 43 */       FileWriter fstream = new FileWriter("Rezultate.txt");
/* 44 */       BufferedWriter out = new BufferedWriter(fstream);
/* 45 */       out.write(apl2.name);
/* 46 */       out.close();
/* 47 */       if (apl2.erori2 != null && apl2.erori2.length() > 1)
/* 48 */       { FileWriter fstream2 = new FileWriter("Erori.txt");
/* 49 */         BufferedWriter out2 = new BufferedWriter(fstream2);
/* 50 */         out2.write(apl2.erori2);
/* 51 */         out2.close();
/* 52 */         fstream2.close(); } 
/* 53 */     } catch (ParserConfigurationException ex) {
/* 54 */       erori = erori + ex.toString() + " ";
/* 55 */     } catch (SAXException ex) {
/* 56 */       erori = erori + ex.toString() + " ";
/* 57 */     } catch (IOException ex) {
/* 58 */       erori = erori + ex.toString() + " ";
/* 59 */     } catch (JRException ex) {
/* 60 */       erori = erori + ex.toString() + " ";
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\e-facturare\GenerareFacturaAll\dist\generareFacturaAll.jar!\generareFacturaAll\Afis.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */