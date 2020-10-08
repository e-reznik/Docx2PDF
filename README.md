# Example usage
   
    String docIn = "/home/user/docs/sample.docx";
    String docOut = "/home/user/docs/out.pdf";
    String fontsPath = "/home/user/docs/fonts/";

    Converter app = new Converter(docIn, docOut, fontsPath);
    
    // fontsPath can be null. In this case, a standard font will be used: Helvetica
    // Converter app = new Converter(docIn, docOut, null);

# Supported elements
- text
  - formatting
- images
- tables
- ~~background color~~
- ~~headings~~
- ~~basic shapes~~

# Supported text formatting
- Font family
- Font size
- <strong>bold</strong>
- <em>italic</em>
- <ins>underline</ins>
- ~~strikethrough~~
- colors &#x1F534; &#x1F535;
- highlighting
