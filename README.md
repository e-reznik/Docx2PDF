<p align="center">
        <img src="https://img.shields.io/github/commit-activity/y/e-reznik/Docx2PDF" />
        <img src="https://img.shields.io/github/last-commit/e-reznik/Docx2PDF" />
</p>

# Info
Converts Docx-files based on OOXML (i.e. created with Microsoft Word 2007 or newer) into PDF.  
First, the Docx-archive is read and mapped into POJOs with [DocxJavaMapper](https://github.com/e-reznik/DocxJavaMapper). For the final conversion iText7 is used.

# See it in Action!

https://github.com/e-reznik/Docx2Pdf-Gui-Swing/releases/download/v0.1-alpha/Docx2Pdf-Gui-Swing-1.0-SNAPSHOT.jar

# Example usage
```java
String docIn = "/home/user/docs/sample.docx";
String docOut = "/home/user/docs/out.pdf";
String fontsPath = "/home/user/docs/fonts/";

Converter app = new Converter(docIn, docOut, fontsPath);

// fontsPath can be null. In this case, a standard font will be used: Helvetica
// Converter app = new Converter(docIn, docOut, null);
```

# Example GUI-Project

- An example project with __Swing__ can be found here: https://github.com/e-reznik/Docx2Pdf-Gui-Swing
- Another GUI made with __JavaFX GUI__ can be found here: https://github.com/e-reznik/Docx2Pdf-Gui

# Supported elements
- text
  - formatting
- images
- tables

## Supported text formatting
- Font family
- Font size
- text alignment
- <strong>bold</strong>
- <em>italic</em>
- <ins>underline</ins>
- ~~strikethrough~~
- colors &#x1F534; &#x1F535;
- [hyperlinks](https://github.com/e-reznik/Docx2PDF2/)
- highlighting

---

## Coming soon...
- lists
- background color
- headings
- basic shapes

# Detailed exception handling
```java
Oct 08, 2020 4:33:04 PM Main main
SEVERE: File not found
java.io.FileNotFoundException: The Docx document doesn't exist: /home/user/docs/examaple.docx
Oct 08, 2020 4:28:44 PM Helper loadFont
WARNING: Font "Times New Roman" could not be found in /home/user/docs/fonts/
Oct 08, 2020 4:28:44 PM Converter highlightText
WARNING: java.lang.NumberFormatException: darkCyan could not be recognized as a valid color
```
