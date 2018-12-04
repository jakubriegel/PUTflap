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

import java.io.File;
import java.io.Serializable;
import java.util.Map;

/**
 * This specifies the common interface for objects that parse documents and
 * produce a corresponding structure. Ideally the <CODE>toString</CODE> method
 * should be implemented with a brief description of the format of jflap.file this can
 * encode.
 * 
 * @author Thomas Finley
 */

public interface Encoder {
	/**
	 * Given a structure, this will attempt to write the structure to a jflap.file.
	 * This method should always return a jflap.file, or throw an
	 * {@link EncodeException} in the event of failure with a message detailing
	 * the nature of why the encoding failed.
	 * 
	 * @param structure
	 *            the structure to encode
	 * @param file
	 *            the jflap.file to save to
	 * @param parameters
	 *            implementors have the option of accepting custom parameters in
	 *            the form of a map
	 * @return the jflap.file to which the structure was written
	 * @throws EncodeException
	 *             if there was a problem writing the jflap.file
	 */
	public File encode(Serializable structure, File file, Map<?, ?> parameters);

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
	public boolean canEncode(Serializable structure);

	/**
	 * Proposes a jflap.file name for a given structure. This encoder should return
	 * either the jflap.file name, or a jflap.file name more amenable to the format this
	 * encoder will encode in. The jflap.file name suggested should be a fixed point
	 * for this method, i.e.
	 * <code>x.proposeFilename(x.proposeFilename(name,S),S)</code> should
	 * always equal <code>x.proposeFilename(name,S)</code>, where
	 * <code>S</code> is any structure.
	 * 
	 * @param filename
	 *            the proposed jflap.file name
	 * @param structure
	 *            the structure that will be saved
	 * @return the jflap.file name, either original or modified
	 */
	public String proposeFilename(String filename, Serializable structure);
}
