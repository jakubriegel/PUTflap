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

import java.io.*;
import java.util.Map;

/**
 * This is the codec for reading and writing JFLAP structures as serialized
 * objects.
 * 
 * @author Thomas Finley
 */

public class SerializedCodec extends Codec {
	/**
	 * Instantiates a new serialized codec. Serialization as a saving method is
	 * now deprecated. The new method is XML, so when opening a jflap.file with a
	 * serialized codec the corresponding encoder is actually a {@link XMLCodec}.
	 * 
	 * @param xmlcodec
	 *            the xml codec as the corresponding encoder for this codec
	 */
	public SerializedCodec(XMLCodec xmlcodec) {
		this.xmlcodec = xmlcodec;
	}

	/**
	 * Given a jflap.file, this will return a JFLAP structure associated with that
	 * jflap.file. This method should always return a structure, or throw a
	 * {@link ParseException} in the event of failure with a message detailing
	 * the nature of why the decoder failed.
	 * 
	 * @param file
	 *            the jflap.file to decode into a structure
	 * @param parameters
	 *            implementors have the option of accepting custom parameters in
	 *            the form of a map
	 * @return a JFLAP structure resulting from the interpretation of the jflap.file
	 * @throws ParseException
	 *             if there was a problem reading the jflap.file
	 */
	public Serializable decode(File file, Map<?, ?> parameters) {
		Serializable object = null;
		try {
			ObjectInputStream stream = new ObjectInputStream(
					new BufferedInputStream(new FileInputStream(file)));
			object = (Serializable) stream.readObject();
			stream.close();
		} catch (IOException e) {
			throw new ParseException("Could not open jflap.file to read!");
		} catch (ClassCastException e) {
			throw new ParseException("Bad Class Read!");
		} catch (ClassNotFoundException e) {
			throw new ParseException("Unrecognized Class Read!");
		}
		return object;
	}

	/**
	 * Given a structure, this will attempt to write the structure as a
	 * serialized object to a jflap.file.
	 * 
	 * @param structure
	 *            the structure to encode
	 * @param file
	 * @param parameters
	 *            implementors have the option of accepting custom parameters in
	 *            the form of a map
	 * @return the jflap.file to which the structure was written
	 * @throws EncodeException
	 *             if there was a problem writing the jflap.file
	 */
	public File encode(Serializable structure, File file, Map<?, ?> parameters) {
		try {
			ObjectOutputStream stream = new ObjectOutputStream(
					new BufferedOutputStream(new FileOutputStream(file)));
			stream.writeObject(structure);
			stream.flush();
			stream.close();
			return file;
		} catch (NotSerializableException e) {
			throw new EncodeException("Object Not Serializable");
		} catch (IOException e) {
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
		return false; // Serialization is deprecated as a saving method.
	}

	/**
	 * Returns the description of this codec.
	 * 
	 * @return the description of this codec
	 */
	public String getDescription() {
		return "JFLAP 4 Beta File";
	}

	/**
	 * Returns an instance of a corresponding encoder, the {@link XMLCodec}.
	 * 
	 * @return the {@link XMLCodec}
	 */
	public Encoder correspondingEncoder() {
		// return this;
		return xmlcodec;
	}

	/** The XML codec. */
	private XMLCodec xmlcodec;
}
