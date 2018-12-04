/*
 *  JFLAP - Formal Languages and Automata Package
 * 
 * 
 *  Susan H. Rodger
 *  Computer Science Department
 *  Duke University
 *  August 27, 2009

 *  Copyright (c) 2002-2009
 *  All rights reserved.

 *  JFLAP is open source software. Please see the LICENSE for terms.
 *
 */





package jflap.file;

import jflap.file.xml.DOMPrettier;
import jflap.file.xml.Transducer;
import jflap.file.xml.TransducerFactory;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

/**
 * This is the codec for reading and writing JFLAP structures as XML documents.
 * 
 * @author Thomas Finley, Henry Qin
 */

public class XMLCodec extends Codec {


    /**
      * Determines which files this FileFilter will allow. We are only allowing files with extension XML and jff.
      * 
      */
    @Override
    public boolean accept(File f){
        if (f.isDirectory()) return true;
        if (f.getName().endsWith(".xml") || (f.getName().endsWith(".jff"))) return true;
        return false;
    } 

	/**
	 * Given a jflap.file, this will return a JFLAP structure associated with that
	 * jflap.file.
	 * 
	 * @param file
	 *            the jflap.file to decode into a structure
	 * @param parameters
	 *            these parameters are ignored
	 * @return a JFLAP structure resulting from the interpretation of the jflap.file
	 * @throws ParseException
	 *             if there was a problem reading the jflap.file
	 */
	public Serializable decode(File file, Map<?, ?> parameters) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(file);
			Transducer transducer = TransducerFactory.getTransducer(doc);
			return transducer.fromDOM(doc);
		} catch (ParserConfigurationException e) {
			throw new ParseException("Java could not create the parser!");
		} catch (IOException e) {
			throw new ParseException("Could not open jflap.file to read!");
		} catch (org.xml.sax.SAXException e) {
			throw new ParseException("Could not parse XML!\n" + e.getMessage());
		} catch (ExceptionInInitializerError e) {
			// Hmm. That shouldn't be.
			System.err.println("STATIC INIT:");
			e.getException().printStackTrace();
			throw new ParseException("Unexpected Error!");
		}
	}

	/**
	 * Given a structure, this will attempt to write the structure as a
	 * serialized object to a jflap.file.
	 * 
	 * @param structure
	 *            the structure to encode
	 * @param file
	 *            the jflap.file to save the structure to
	 * @param parameters
	 *            implementors have the option of accepting custom parameters in
	 *            the form of a map
	 * @return the jflap.file to which the structure was written
	 * @throws EncodeException
	 *             if there was a problem writing the jflap.file
	 */
	public File encode(Serializable structure, File file, Map<?, ?> parameters) {
		Transducer transducer = null;
		try {
			transducer = TransducerFactory.getTransducer(structure);
            /*
             * If we are saving a jflap.pumping lemma, the associated structure would
             * actually be a jflap.pumping lemma chooser. Thus, we have to get the
             * lemma from the chooser.
             */
            Document dom;
            if(structure instanceof jflap.gui.pumping.PumpingLemmaChooser)
                dom = transducer.toDOM(((jflap.gui.pumping.PumpingLemmaChooser)structure).getCurrent());
            else
                dom = transducer.toDOM(structure);
            
//			Document dom = transducer.toDOM(structure);    // original line
            
			DOMPrettier.makePretty(dom);
			Source s = new DOMSource(dom);
			Result r = new StreamResult(file);
			Transformer t = TransformerFactory.newInstance().newTransformer();
			t.transform(s, r);
			return file;
		} catch (IllegalArgumentException e) {
			throw new EncodeException(
					"No XML transducer available for this structure!");
		} catch (TransformerConfigurationException e) {
			throw new EncodeException("Could not open jflap.file to write!");
		} catch (TransformerException e) {
			throw new EncodeException("Could not open jflap.file to write!");
		}
	}

	/**
	 * Returns if this type of structure can be encoded with this encoder. This
	 * should not perform a detailed check of the structure, since the user will
	 * have no idea why it will not be encoded correctly if the {@link #encode}
	 * method does not throw a {@link ParseException}.
	 * 
	 * @param structure
	 *            the structure to check
	 * @return if the structure, perhaps with minor changes, could possibly be
	 *         written to a jflap.file
	 */
	public boolean canEncode(Serializable structure) {
		return true;
	}

	/**
	 * Returns the description of this codec.
	 * 
	 * @return the description of this codec
	 */
	public String getDescription() {
		return "JFLAP 4 File";
	}

	/**
	 * Given a proposed filename, returns a new suggested filename. JFLAP 4
	 * saved files have the suffix <CODE>.jf4</CODE> appended to them.
	 * 
	 * @param filename
	 *            the proposed name
	 * @param structure
	 *            the structure that will be saved
	 * @return the new suggestion for a name
	 */
	public String proposeFilename(String filename, Serializable structure) {
		if (!filename.endsWith(SUFFIX))
			return filename + SUFFIX;
		return filename;
	}

	/** The filename suffix. */
	public static final String SUFFIX = ".jff";
}
