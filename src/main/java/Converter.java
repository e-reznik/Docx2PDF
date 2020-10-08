
import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
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
    private final String fontsFolder;

    DocxJM mapper = new DocxJM();

    /**
     * Starts the converting process with a specified folder with custom fonts
     *
     * @param in
     * @param out
     * @param fontsFolder path to the folder with custom fonts
     * @throws java.io.FileNotFoundException if the Docx file doesn't exist
     */
    public Converter(String in, String out, String fontsFolder) throws FileNotFoundException {
        docx = new File(in);
        /* Checks whether the provided Docx file exist */
        if (!docx.exists() || docx.isDirectory()) {
            throw new FileNotFoundException("The Docx document doesn't exist: " + in);
        }

        /* Checks if a folder for custom fonts has been provided */
        if (fontsFolder != null && !fontsFolder.trim().isEmpty()) {
            this.fontsFolder = fontsFolder;
        } else {
            this.fontsFolder = "";
        }

        try {
            InputStream str = Helper.getDocument(docx);
            DJMDocument djmDoc = mapper.map(str);

            convert(djmDoc, out, fontsFolder);
        } catch (IOException ex) {
            Logger.getLogger(Converter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void convert(DJMDocument djmDoc, String docOut, String fontsFolder) throws FileNotFoundException, IOException {
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

        /* Checks, whether any tables exist */
        if (djmDoc.getBody().getTables() != null) {
            djmDoc.getBody().getTables().forEach(djmt -> {
                pdfDoc.add(processTable(djmt));
            });
        }

        pdfDocument.close();
        pdfDoc.close();
        out.close();
    }

    /**
     * Processes a paragraph.
     *
     * @param djmp the paragraph element
     * @return the processed paragraph
     */
    private Paragraph processParagraph(DJMParagraph djmp) {
        Paragraph paragraph = new Paragraph();

        djmp.getRuns().stream().map(djmr -> {
            Text text;

            // Text formatting
            text = formatText(djmr);
            text = setFontSize(djmr, text);
            text = setFontFamily(djmr, text);
            text = colorText(djmr, text);
            text = highlightText(djmr, text);

            // Adding images
            try {
                Image image = extractPictures(djmr);
                if (image != null) {
                    paragraph.add(image);
                }
            } catch (IOException ex) {
                // TODO: If image couldn't be processed, show error image instead, 
                text = new Text("[Image could not be processed]");
                LOGGER.log(Level.SEVERE, "Error while processing image", ex);
            }

            return text;
        }).forEachOrdered(text -> {
            paragraph.add(text);
        });

        return paragraph;
    }

    /**
     * Processes a table.
     *
     * @param djmt the Table element
     * @return processed table
     */
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

    /**
     * Applies possible text formatting.
     *
     * @param djmr corresponding Run
     * @return formatted text
     */
    private Text formatText(DJMRun djmr) {
        if (djmr.getText() == null) {
            return new Text("");
        }
        Text text = new Text(djmr.getText());
        /* Bold */
        if (djmr.getRunProperties().isBold()) {
            text.setBold();
        }
        /* Italic */
        if (djmr.getRunProperties().isItalic()) {
            text.setItalic();
        }
        /* Underline */
        if (djmr.getRunProperties().isUnderline()) {
            text.setUnderline();
        }
        /* Strike-through */
        if (djmr.getRunProperties().isStrike()) {
            text.setLineThrough();
        }

        return text;
    }

    /**
     * Sets the font size. In OOXML the font size is specified in half-points.
     * iText needs the size in points, so it should be divided by 2.
     *
     * @param djmr
     * @param text
     * @return
     */
    private Text setFontSize(DJMRun djmr, Text text) {
        if (djmr.getText() == null || djmr.getRunProperties().getFontSize() == null) {
            return text;
        }

        try {
            float fontSize = djmr.getRunProperties().getFontSize().getValue();
            text.setFontSize(fontSize / 2);
        } catch (NullPointerException ex) {

        }

        return text;
    }

    /**
     * Sets the font family. The font file should be provided in a TTF format in
     * a font folder. The font name is case sensitive, i.e. it should have
     * exactly the same name like in the DOCX file. Otherwise a standard font
     * (Helvetica) will be used.
     *
     * @param djmr
     * @param text
     * @return
     */
    private Text setFontFamily(DJMRun djmr, Text text) {
        if (djmr.getText() == null || djmr.getRunProperties().getFont() == null) {
            return text;
        }

        if (!fontsFolder.isEmpty()) {
            PdfFont font;
            String fontValue = djmr.getRunProperties().getFont().getValue();
            FontProgram fontProgram = Helper.loadFont(fontValue, fontsFolder);
            font = PdfFontFactory.createFont(fontProgram, PdfEncodings.UTF8, true);
            text.setFont(font);
        }

        return text;
    }

    /**
     * Returns possible text color.
     *
     * @param djmr corresponding Run. The possible color information is stored
     * here.
     * @param text Text to be colored
     * @return colored text
     */
    private Text colorText(DJMRun djmr, Text text) {
        if (djmr.getText() == null || djmr.getRunProperties().getColor() == null) {
            return text;
        }

        try {
            String value = djmr.getRunProperties().getColor().getValue();
            Color color = Helper.convertColor(value);
            text.setFontColor(color);
        } catch (NumberFormatException ex) {
            LOGGER.log(Level.WARNING, ex.toString());
        }
        return text;
    }

    /**
     * Highlights the text.
     *
     * @param djmr corresponding Run. The possible color information is stored
     * @param text Text to be highlighted
     * @return highlighted text
     */
    private Text highlightText(DJMRun djmr, Text text) {
        if (djmr.getText() == null || djmr.getRunProperties().getHighlight() == null) {
            return text;
        }

        try {
            String value = djmr.getRunProperties().getHighlight().getValue();
            Color color = Helper.convertColor(value);
            text.setBackgroundColor(color);
        } catch (NumberFormatException ex) {
            LOGGER.log(Level.WARNING, ex.toString());
        }
        return text;
    }

    /**
     * Extracts possible images. The image needs to be anchored to a paragraph,
     * in order to be positioned correctly.
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
        image.setWidth(cx / 2);
        image.setHeight(cy / 2);
        image.setMarginTop(posV);
        image.setMarginLeft(posH);

        return image;
    }
}
