/*     */ package generareFacturaAll;
/*     */ 
/*     */ import java.awt.Desktop;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.URLDecoder;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.Paths;
import java.util.ArrayList;
/*     */ import java.util.HashMap;
import java.util.List;
/*     */ import java.util.Map;
import java.util.stream.Collectors;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.xpath.XPath;
/*     */ import javax.xml.xpath.XPathConstants;
/*     */ import javax.xml.xpath.XPathExpressionException;
/*     */ import javax.xml.xpath.XPathFactory;
/*     */ import net.sf.jasperreports.engine.JRException;
/*     */ import net.sf.jasperreports.engine.JasperExportManager;
/*     */ import net.sf.jasperreports.engine.JasperFillManager;
/*     */ import net.sf.jasperreports.engine.JasperPrint;
/*     */ import net.sf.jasperreports.engine.util.JRLoader;
/*     */ import net.sf.jasperreports.engine.util.JRXmlUtils;
import org.apache.commons.io.FileUtils;
/*     */ import org.bouncycastle.util.encoders.Base64;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GenFactura
/*     */ {
/*     */   private String error;
/*     */   String erori2;
/*     */   String name;
/*     */   private HashMap<String, String> tipuriDeclaratii;
/*     */   private HashMap<String, String> tipuriDeclaratii2;
/*     */   String ddeclaratie;
/*     */   
/*     */   public String getError() {
/*  66 */     return this.error;
/*     */   }
/*     */   public String getTipDeclaratie() {
/*  69 */     return this.ddeclaratie;
/*     */   }
/*     */   
/*     */   public GenFactura() {
/*  73 */     this.tipuriDeclaratii = new HashMap<>();
/*  74 */     this.tipuriDeclaratii.put("Invoice", "Factura");
/*  75 */     this.tipuriDeclaratii2 = new HashMap<>();
/*  76 */     this.tipuriDeclaratii2.put("CreditNote", "Nota de Creditare");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void scrieErr(String nui, String crt, String cale) {
/*     */     try {
/*  84 */       if ((new File(cale + nui + ".txt")).exists()) {
/*     */         return;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*  90 */       PrintWriter pw = new PrintWriter(cale + nui + "_err" + ".txt");
/*  91 */       pw.append(crt);
/*  92 */       pw.close();
/*  93 */     } catch (Throwable throwable) {}
/*     */   }
/*     */ 
/*     */ public void generarePDF() throws ParserConfigurationException, SAXException, IOException, JRException {
/* 109 */     String cale = findPath();
/*     */ 
/*     */     
/* 112 */     this.error = null;
/* 113 */     this.name = " cale: " + cale;
/* 114 */     File folder = new File(cale);
/* 115 */     File[] listOfFiles = folder.listFiles();
/*     */ 
/*     */     
/* 118 */     this.erori2 = "";
/* 119 */     for (File file : listOfFiles) {
/* 120 */       if (file.isFile()) {
/* 121 */         String numeXML = file.getName();
/* 122 */         int w = numeXML.lastIndexOf(".");
/* 123 */         if (w > 1)
/*     */         {
/* 125 */           if (file.getName().substring(w, file.getName().length()).equals(".xml") && !numeXML.equals("build.xml")) {
/*     */             
/*     */             try {
/* 128 */               this.name += "\n" + " Fisier: " + numeXML;
/* 129 */               String tipDeclaratie = identificaDeclaratie(numeXML);
/*     */               
/* 131 */               Map<String, Object> parameters = new HashMap<>();
/*     */               
/* 133 */               Document document = JRXmlUtils.parse(JRLoader.getLocationInputStream(numeXML));
/* 134 */               parameters.put("XML_DATA_DOCUMENT", document);
/* 135 */               parameters.put("SUBREPORT_DIR", cale + "/jasper//");
/*     */               
/* 137 */               int p = numeXML.lastIndexOf(".");
/*     */ 
/*     */               
/* 140 */               String numeATT = numeXML.substring(0, p) + "_ATAS";
/*     */ 
/*     */               
/* 143 */               returnString(numeXML, numeATT, tipDeclaratie);
/*     */               
/* 145 */               numeXML = numeXML.substring(0, p);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 154 */               numeXML = numeXML + ".pdf";
/*     */ 
/*     */ 
/*     */               
/* 158 */               if ("Factura".equals(tipDeclaratie)) {
/*     */ 
/*     */                 
/* 161 */                 JasperPrint jasperPrint = JasperFillManager.fillReport(cale + "/jasper//factura_complet2.jasper", parameters);
/*     */                 
/* 163 */                 JasperExportManager.exportReportToPdfFile(jasperPrint, numeXML);
/*     */               } 
/*     */ 
/*     */               
/* 167 */               if ("Nota de Creditare".equals(tipDeclaratie))
/*     */               {
/*     */                 
/* 170 */                 JasperPrint jasperPrint = JasperFillManager.fillReport(cale + "/jasper//factura_complet2_c.jasper", parameters);
/*     */                 
/* 172 */                 JasperExportManager.exportReportToPdfFile(jasperPrint, numeXML);
/*     */ 
/*     */ 
/*     */               
/*     */               }
/*     */ 
/*     */ 
/*     */             
/*     */             }
/* 181 */             catch (Throwable e) {
/*     */ 
/*     */               
/* 184 */               this.error = e.toString();
/* 185 */               scrieErr(numeXML, e.toString(), cale);
/* 186 */               this.erori2 += " Pt fisierul " + numeXML + " eroare: " + " Fisierul PDF nu a putut fi generat!" + "\n";
/*     */             } 
/*     */           }
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void generarePDF(String xmlFolderPath, boolean extractAttachments) throws ParserConfigurationException, SAXException, IOException, JRException {
/* 109 */     String cale = findPath();
/*     */ 
/*     */     
/* 112 */     this.error = null;
/* 113 */     this.name = " cale: " + cale;
/* 114 */     File folder = new File(xmlFolderPath);
/* 115 */     List<File> listOfFiles = FileUtils.listFiles(folder, new String[]{"xml"}, false)
                            .stream().filter(f->!f.getAbsolutePath().contains("semnatura")).collect(Collectors.toList());
/* 118 */     this.erori2 = "";
/* 119 */     for (File file : listOfFiles) {
/* 120 */       if (file.isFile()) {
/* 121 */         String numeXML = file.getName();
                  String numeXMLFullPath = file.getAbsolutePath();
/* 122 */         int w = numeXML.lastIndexOf(".");
/* 123 */         if (w > 1)
/*     */         {
///* 125 */           if (file.getName().substring(w, file.getName().length()).equals(".xml") && !numeXML.equals("build.xml")) {
/*     */            System.out.println(file.getAbsolutePath());
/*     */             try {
/* 128 */               this.name += "\n" + " Fisier: " + numeXML;
/* 129 */               String tipDeclaratie = identificaDeclaratie(numeXMLFullPath);
/*     */               System.out.println(tipDeclaratie+" : "+numeXMLFullPath);
/* 131 */               Map<String, Object> parameters = new HashMap<>();
/*     */               
/* 133 */               Document document = JRXmlUtils.parse(JRLoader.getLocationInputStream(numeXMLFullPath));
/* 134 */               parameters.put("XML_DATA_DOCUMENT", document);
/* 135 */               parameters.put("SUBREPORT_DIR", cale + "/jasper//");
/*     */               
/* 137 */               int p = numeXML.lastIndexOf(".");
/*     */ 
/*     */               
/* 140 */               String numeATT = numeXML.substring(0, p) + "_ATAS";
/*     */ 
/*     */               if(extractAttachments) {
/* 143 */               returnString(numeXMLFullPath, numeATT, tipDeclaratie);
/*     */               }
/* 145 */               numeXML = numeXML.substring(0, p);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 154 */               numeXML = numeXML + ".pdf";
/*     */ 
/*     */ 
/*     */               
/* 158 */               if ("Factura".equals(tipDeclaratie)) {
/*     */ 
/*     */                 
/* 161 */                 JasperPrint jasperPrint = JasperFillManager.fillReport(cale + "/jasper//factura_complet2.jasper", parameters);
/*     */                 
/* 163 */                 JasperExportManager.exportReportToPdfFile(jasperPrint, xmlFolderPath+"/"+numeXML);
/*     */               } 
/*     */ 
/*     */               
/* 167 */               if ("Nota de Creditare".equals(tipDeclaratie))
/*     */               {
/*     */                 
/* 170 */                 JasperPrint jasperPrint = JasperFillManager.fillReport(cale + "/jasper//factura_complet2_c.jasper", parameters);
/*     */                 
/* 172 */                 JasperExportManager.exportReportToPdfFile(jasperPrint,xmlFolderPath+"/"+numeXML);
/*     */ 
/*     */ 
/*     */               
/*     */               }
/*     */ 
/*     */ 
/*     */             
/*     */             }
/* 181 */             catch (Throwable e) {
/*     */ 
/*     */               
/* 184 */               this.error = e.toString();
/* 185 */               scrieErr(numeXML, e.toString(), cale);
/* 186 */               this.erori2 += " Pt fisierul " + numeXML + " eroare: " + " Fisierul PDF nu a putut fi generat!" + "\n";
/*     */             } 
///*     */           }
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String arataCui(String numeXML) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
/* 214 */     FileInputStream fileIS = new FileInputStream(new File(numeXML));
/*     */ 
/*     */     
/* 217 */     String s = "";
/* 218 */     String extensie = "";
/* 219 */     DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
/* 220 */     DocumentBuilder builder = builderFactory.newDocumentBuilder();
/* 221 */     Document xmlDocument = builder.parse(fileIS);
/* 222 */     XPath xPath = XPathFactory.newInstance().newXPath();
/* 223 */     String expression = "";
/* 224 */     expression = " /*[local-name()='Invoice']/*[local-name()='AccountingSupplierParty']/*[local-name()='AccountingSupplierParty']/*[local-name()='Party']/*[local-name()='PartyLegalEntity']/*[local-name()='CompanyID']";
/*     */ 
/*     */     
/* 227 */     NodeList nodeList = (NodeList)xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
/*     */ 
/*     */ 
/*     */     
/* 231 */     for (int i = 0; i < nodeList.getLength(); i++) {
/*     */       
/* 233 */       extensie = "";
/*     */ 
/*     */       
/* 236 */       NodeList childNodesList = nodeList.item(i).getChildNodes();
/* 237 */       for (int j = 0; j < childNodesList.getLength(); j++) {
/* 238 */         System.out.println(s);
/*     */       }
/*     */     } 
/* 241 */     fileIS.close();
/* 242 */     return s;
/*     */   }
/*     */ 
/*     */   
/*     */   public String returnString(String numeXML, String numeATT, String tipDeclaratie) throws FileNotFoundException, ParserConfigurationException, SAXException, IOException, XPathExpressionException {
/* 247 */     FileInputStream fileIS = new FileInputStream(new File(numeXML));
/*     */ 
/*     */     
/* 250 */     String s = "";
/* 251 */     String extensie = "";
/* 252 */     DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
/* 253 */     DocumentBuilder builder = builderFactory.newDocumentBuilder();
/* 254 */     Document xmlDocument = builder.parse(fileIS);
/* 255 */     XPath xPath = XPathFactory.newInstance().newXPath();
/* 256 */     String expression = "";
/* 257 */     if ("Factura".equals(tipDeclaratie))
/* 258 */       expression = "/*[local-name()='Invoice']/*[local-name()='AdditionalDocumentReference']/*[local-name()='Attachment']"; 
/* 259 */     if ("Nota de Creditare".equals(tipDeclaratie)) {
/* 260 */       expression = "/*[local-name()='CreditNote']/*[local-name()='AdditionalDocumentReference']/*[local-name()='Attachment']";
/*     */     }
/*     */     
/* 263 */     NodeList nodeList = (NodeList)xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
/*     */ 
/*     */ 
/*     */     
/* 267 */     for (int i = 0; i < nodeList.getLength(); i++) {
/*     */       
/* 269 */       extensie = "";
/* 270 */       String numeAtas = numeATT;
/*     */       
/* 272 */       NodeList childNodesList = nodeList.item(i).getChildNodes();
/* 273 */       for (int j = 0; j < childNodesList.getLength(); j++) {
/*     */ 
/*     */         
/* 276 */         if (childNodesList.item(j).getNodeName().contains("EmbeddedDocumentBinaryObject")) {
/*     */           
/* 278 */           extensie = childNodesList.item(j).getAttributes().getNamedItem("mimeCode").getNodeValue();
/* 279 */           if (extensie.contains("pdf")) {
/* 280 */             extensie = "pdf";
/*     */           }
/* 282 */           if (extensie.contains("png")) {
/* 283 */             extensie = "png";
/*     */           }
/* 285 */           if (extensie.contains("jpeg")) {
/* 286 */             extensie = "jpeg";
/*     */           }
/* 288 */           if (extensie.contains("csv")) {
/* 289 */             extensie = "csv";
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 299 */       numeAtas = numeAtas + "_" + (i + 1) + "." + extensie;
/*     */       
/* 301 */       s = nodeList.item(i).getTextContent();
/* 302 */       Path currentRelativePath = Paths.get(numeXML, new String[0]);
/* 303 */       String ss = currentRelativePath.toAbsolutePath().toString();
/*     */       
/* 305 */       byte[] data = Base64.decode(s);
/*     */       
/* 307 */       int k = data.length;
/*     */       String parentFolder = new File(numeXML).getParent();
/* 309 */       try (OutputStream stream = new FileOutputStream(parentFolder+"/"+numeAtas)) {
/* 310 */         stream.write(data);
/* 311 */       } catch (Throwable e) {
/*     */         
/* 313 */         this.error = "Atentie ! Generarea atasamentului nu a putut fi executata !";
/*     */         
/* 315 */         return null;
/*     */       } 
/*     */     } 
/* 318 */     fileIS.close();
/* 319 */     return s;
/*     */   }
/*     */ 
/*     */   
/*     */   private String findPath() {
/* 324 */     String path = System.getProperty("user.dir");
/* 325 */    
/*     */     try {
/* 333 */       return URLDecoder.decode(path, "UTF-8");
/* 334 */     } catch (UnsupportedEncodingException ex) {
/* 335 */       return ex.toString();
/*     */     } 
/*     */   }
/*     */   
/*     */   public String identificaDeclaratie(String numeFisier) {
/* 340 */     this.error = null;
/* 341 */     DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
/*     */     try {
/* 343 */       DocumentBuilder db = dbf.newDocumentBuilder();
/* 344 */       Document doc = db.parse(new File(numeFisier));
/* 345 */       Element root = doc.getDocumentElement();
/*     */       String rootTagName = root.getTagName();
                if(rootTagName.contains("Invoice"))
                {
                    rootTagName = "Invoice";
                }
                if(rootTagName.contains("CreditNote"))
                {
                    rootTagName = "CreditNote";
                }
                System.out.println(rootTagName);
/* 347 */       if (this.tipuriDeclaratii.containsKey(rootTagName)) {
/* 348 */         this.ddeclaratie = "Invoice";
/* 349 */         return this.tipuriDeclaratii.get(rootTagName);
/*     */       } 
/* 351 */       if (this.tipuriDeclaratii2.containsKey(rootTagName)) {
/*     */         
/* 353 */         this.ddeclaratie = "CreditNote";
/* 354 */         return this.tipuriDeclaratii2.get(rootTagName);
/*     */       } 
/*     */ 
/*     */       
/* 358 */       this.error = "Tip declaratie necunoscut";
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 363 */     catch (ParserConfigurationException|SAXException|IOException ex) {
/* 364 */       this.error = "Eroare citire fisier XML: " + ex;
/*     */     } 
/*     */ 
/*     */     
/* 368 */     return this.error;
/*     */   }
/*     */   
/*     */   public void deschidePDF(String fisier) {
/* 372 */     this.error = null;
/* 373 */     if (!Desktop.isDesktopSupported()) {
/* 374 */       this.error = "Versiunea sistemului de operare nu permite deschiderea fisierului";
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 381 */       Desktop.getDesktop().open(new File(fisier));
/* 382 */     } catch (IOException ex) {
/* 383 */       this.error = ex.toString();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\e-facturare\GenerareFacturaAll\dist\generareFacturaAll.jar!\generareFacturaAll\GenFactura.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */