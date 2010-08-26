package com.nacre.service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

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
		Map<String, Decoration> decorations = new HashMap<String,Decoration>();
		String locatorKey;
		@Override
		public ContentHandler getContentHandler(
				AnnotationContext ctx, String parent,
				ErrorHandler errorHandler, EntityResolver entityResolver) {
			System.out.println("got an annotation, parent [" + parent + "] type [" + ctx.toString() + "]");
			return new DefaultHandler() {
				public void setDocumentLocator(Locator locator) {
					System.out.println("Annotation location: " + locator.getLineNumber() + ":" + locator.getColumnNumber());
					locatorKey = locatorKey(locator);
				}

				public void startElement(String uri,
						String localName, String qName,
						Attributes atts) throws SAXException {
					System.out.println("Got annotation for " + localName + " qname " + qName);
					if (uri.equals("http://www.nacre.com/decorations")) {
						if (localName.equals("label")) {
							Decoration decoration = new Decoration();
							decoration.setLabel(atts.getValue("label"));
							decorations.put(locatorKey, decoration);
						}
					}
				}
			}; 				
		}

		@Override
		public Object getResult(Object o) {
			return decorations;
		}
		
	};
	
	public static String locatorKey(Locator l) {
		return l.getPublicId() + ":" + l.getLineNumber() + ":" + l.getColumnNumber();
	}
	
}
