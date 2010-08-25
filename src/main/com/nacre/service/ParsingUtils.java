package com.nacre.service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.nacre.service.vo.Decoration;
import com.sun.xml.xsom.parser.AnnotationContext;
import com.sun.xml.xsom.parser.AnnotationParser;
import com.sun.xml.xsom.parser.AnnotationParserFactory;

public class ParsingUtils {
	public static EntityResolver nacreXSOMEntityResolver = new EntityResolver() {
		public InputSource resolveEntity(String publicId, String systemId)
				throws SAXException, IOException {
			System.out.println("looking for sysid: [" + systemId + "]");
			try {
				return new InputSource(new FileReader(new File(new URI(systemId))));
			} catch (URISyntaxException e) {
				System.err.println("failed to locate URI: " + systemId);
				return null;
			}
		}
	};
	public static ErrorHandler nacreXSOMErrorHandler = new ErrorHandler() {
		public void error(SAXParseException exception) throws SAXException {
			log(exception);
		}
		public void fatalError(SAXParseException exception)
				throws SAXException {
			log(exception);			
		}
		public void warning(SAXParseException exception)
				throws SAXException {
			log(exception);
		}
		private void log(SAXParseException exception) {
			System.out.println(exception.getMessage());
			exception.printStackTrace();
		}
	};
	public static AnnotationParserFactory nacreXSOMAnnotationParserFactory = new AnnotationParserFactory() {
		public AnnotationParser create() {
			return nacreXSOMAnnotationParser;
		}
	};
	
	private static AnnotationParser nacreXSOMAnnotationParser = new AnnotationParser() {
		Decoration decoration = new Decoration();
		@Override
		public ContentHandler getContentHandler(
				AnnotationContext ctx, String parent,
				ErrorHandler errorHandler, EntityResolver entityResolver) {
			System.out.println("got an annotation, parent [" + parent + "] type [" + ctx.toString() + "]");
			return new ContentHandler() {

				public void characters(char[] ch, int start,
						int length) throws SAXException {
				}

				public void endDocument() throws SAXException {
				}

				public void endElement(String uri,
						String localName, String qName)
						throws SAXException {
				}

				public void endPrefixMapping(String prefix)
						throws SAXException {
				}

				public void ignorableWhitespace(char[] ch,
						int start, int length) throws SAXException {
				}

				public void processingInstruction(String target,
						String data) throws SAXException {
				}

				public void setDocumentLocator(Locator locator) {
				}

				public void skippedEntity(String name)
						throws SAXException {
				}

				public void startDocument() throws SAXException {
				}

				public void startElement(String uri,
						String localName, String qName,
						Attributes atts) throws SAXException {
					System.out.println("Got annotation for " + localName + " qname " + qName);
					if (uri.equals("http://www.nacre.com/decorations")) {
						if (localName.equals("label")) {
							decoration.setLabel(atts.getValue("label"));
						}
					}
				}

				public void startPrefixMapping(String prefix,
						String uri) throws SAXException {
				}
				
			}; 				
		}

		@Override
		public Object getResult(Object arg0) {
			return decoration;
		}
		
	};
	
}
