
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import docxjavamapper.DocxJM;
import docxjavamapper.model.DJMDocument;
import docxjavamapper.model.DJMParagraph;
import docxjavamapper.model.DJMRun;
import docxjavamapper.model.DJMTable;
import docxjavamapper.model.drawing.DJMAnchor;
import docxjavamapper.model.table.DJMTableCell;
import docxjavamapper.model.table.DJMTableRow;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Converter {

    private final static Logger LOGGER = Logger.getLogger(Converter.class.getName());
    private final File docx;

    DocxJM mapper = new DocxJM();

    public Converter(String in, String out) {
        docx = new File(in);

        try {
            InputStream str = Helper.getDocument(docx);
            DJMDocument djmDoc = mapper.map(str);
            
            convert(djmDoc, out);
        } catch (IOException ex) {
            Logger.getLogger(Converter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void convert(DJMDocument djmDoc, String docOut) throws FileNotFoundException, IOException {
        OutputStream out;
        out = new FileOutputStream(new File(docOut));
        PdfDocument pdfDocument;
        Document pdfDoc;

        PdfWriter writer = new PdfWriter(docOut);
        pdfDocument = new PdfDocument(writer);
        pdfDoc = new Document(pdfDocument);

        djmDoc.getBody().getParagraphs().forEach(djmp -> {
            pdfDoc.add(processParagraph(djmp));
        });

        djmDoc.getBody().getTables().forEach(djmt -> {
            pdfDoc.add(processTable(djmt));
        });

        pdfDocument.close();
        pdfDoc.close();
        out.close();
    }

    private Paragraph processParagraph(DJMParagraph djmp) {
        Paragraph paragraph = new Paragraph();

        djmp.getRuns().stream().map(djmr -> {
            Text text;

            // Text formatting
            text = formatText(djmr);

            // Text color
            text = colorText(djmr, text);

            // Adding images
            try {
                Image image = extractPictures(djmr);
                if (image != null) {
                    paragraph.add(image);
                }
            } catch (IOException ex) {
                // TODO: Show error message in PDF, if image couldn't be processed
                LOGGER.log(Level.SEVERE, "Error while processing image", ex);
            }

            return text;
        }).forEachOrdered(text -> {
            paragraph.add(text);
        });

        return paragraph;
    }

    private Table processTable(DJMTable djmt) {
        int numCells = djmt.getTableRows().get(0).getTableCell().size();

        Table table = new Table(numCells);
        Cell cell;

        for (DJMTableRow r : djmt.getTableRows()) {
            for (DJMTableCell c : r.getTableCell()) {
                cell = new Cell();
                for (DJMParagraph p : c.getParagraph()) {
                    cell.add(processParagraph(p));
                }
                table.addCell(cell);
            }
        }

        return table;
    }

    private Text formatText(DJMRun djmr) {
        if (djmr.getText() == null) {
            return new Text("");
        }
        Text text = new Text(djmr.getText());
        if (djmr.getRunProperties().isBold()) {
            text.setBold();
        }
        if (djmr.getRunProperties().isItalic()) {
            text.setItalic();
        }
        return text;
    }

    private Text colorText(DJMRun djmr, Text text) {
        if (djmr.getText() == null) {
            return new Text("");
        }
        try {
            Color color = Helper.hexToRgb(djmr.getRunProperties().getColor().getValue());
            text.setFontColor(color);
        } catch (NumberFormatException ex) {
            LOGGER.log(Level.FINE, ex.toString());
        } catch (Exception ex) {
            LOGGER.log(Level.FINE, "Color not found", ex);
        }
        return text;
    }

    /**
     * Extracts possible images.
     *
     * @param run
     * @return Extracted image
     * @throws IOException
     */
    private Image extractPictures(DJMRun run) throws IOException {
        if (run.getDrawing() == null) {
            return null;
        }
        Image image;
        DJMAnchor anchor = run.getDrawing().getAnchor();

        String name = anchor.getDocPr().getName(); // file name
        int posH = anchor.getPositionH().getPosOffset(); // x coordinate
        int posV = anchor.getPositionV().getPosOffset(); // y coordinate
        int cx = anchor.getExtent().getCx(); // width
        int cy = anchor.getExtent().getCy(); // height

        InputStream is = null;
        try {
            is = Helper.getImage(docx, name);
        } catch (Exception ex) {
            throw new IOException("Error while processing image", ex);
        }

        // English Metric Units
        final int EMU = 9525;

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        BufferedImage bi = ImageIO.read(is);
        ImageIO.write(bi, "png", bos);
        ImageData id = ImageDataFactory.create(bos.toByteArray());

        posH = posH / EMU;
        posV = posV / EMU;
        cx = cx / EMU;
        cy = cy / EMU;

        image = new Image(id);
        image.setWidth(cx);
        image.setHeight(cy);
        image.setMarginTop(posV);
        image.setMarginLeft(posH);

        return image;
    }
}
