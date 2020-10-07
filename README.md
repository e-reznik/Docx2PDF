# Example usage
   
    String docIn = "myDoc.docx";
    String docOut = "myPdf.pdf";
    
    Converter c = new Converter();
    c.convert(docIn, docOut);

# Supported elements
- text
  - formatting
- images
- tables
  - text formatting
- ~~headings~~
- ~~basic shapes~~

# Supported text formatting
- <strong>bold</strong>
- <em>italic</em>
- <ins>underline</ins>
- ~~strikethrough~~
- colors &#x1F534; &#x1F535;
- highlighting
