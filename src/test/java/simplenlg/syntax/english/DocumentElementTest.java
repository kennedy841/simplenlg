/*
 * The contents of this file are subject to the Mozilla Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * The Original Code is "Simplenlg".
 *
 * The Initial Developer of the Original Code is Ehud Reiter, Albert Gatt and Dave Westwater.
 * Portions created by Ehud Reiter, Albert Gatt and Dave Westwater are Copyright (C) 2010-11 The University of Aberdeen. All Rights Reserved.
 *
 * Contributor(s): Ehud Reiter, Albert Gatt, Dave Wewstwater, Roman Kutlak, Margaret Mitchell, Saad Mahamood.
 */

package simplenlg.syntax.english;

import java.util.Arrays;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simplenlg.framework.DocumentElement;
import simplenlg.phrasespec.NPPhraseSpec;
import simplenlg.phrasespec.SPhraseSpec;

/**
 * Tests for the DocumentElement class.
 * 
 * @author ereiter
 */
public class DocumentElementTest extends SimpleNLG4Test {

	private SPhraseSpec p1, p2, p3;

	/**
	 * Instantiates a new document element test.
	 * 
	 * @param name
	 *            the name
	 */
	public DocumentElementTest(String name) {
		super(name);
	}

	@Before
	public void setUp() {
		super.setUp();
		p1 = this.phraseFactory.createClause("you", "be", "happy");
		p2 = this.phraseFactory.createClause("I", "be", "sad");
		p3 = this.phraseFactory.createClause("they", "be", "nervous");
	}
	
	@Override
	@After
	public void tearDown() {
		super.tearDown();
		this.p1 = null;
		this.p2 = null;
		this.p3 = null;
	}

	/**
	 * Basic tests.
	 */
	@Test
	public void testBasics() {
		DocumentElement s1 = this.phraseFactory.createSentence(p1);
		DocumentElement s2 = this.phraseFactory.createSentence(p2);
		DocumentElement s3 = this.phraseFactory.createSentence(p3);

		DocumentElement par1 = this.phraseFactory.createParagraph(Arrays
				.asList(s1, s2, s3));

		Assert.assertEquals("You are happy. I am sad. They are nervous.\n\n",
				this.realiser.realise(par1).getRealisation());

	}

	/**
	 * Ensure that no extra whitespace is inserted into a realisation if a
	 * constituent is empty. (This is to check for a bug fix for addition of
	 * spurious whitespace).
	 */
	public void testExtraWhitespace() {
		NPPhraseSpec np1 = this.phraseFactory.createNounPhrase("a", "vessel");

		// empty coordinate as premod

		Assert.assertEquals("a vessel", this.realiser.realise(np1)
				.getRealisation());
		
		// empty adjP as premod

		Assert.assertEquals("a vessel", this.realiser.realise(np1)
				.getRealisation());
		
		// empty string

		Assert.assertEquals("a vessel", this.realiser.realise(np1)
				.getRealisation());
		
	}

	/**
	 * test whether sents can be embedded in a section without intervening paras
	 */
	@Test
	public void testEmbedding() {
		DocumentElement sent = phraseFactory.createSentence("This is a test");
		DocumentElement sent2 = phraseFactory.createSentence(phraseFactory
				.createClause("John", "be", "missing"));
		DocumentElement section = phraseFactory.createSection("SECTION TITLE");
		section.addComponent(sent);
		section.addComponent(sent2);

		Assert.assertEquals(
				"SECTION TITLE\nThis is a test.\n\nJohn is missing.\n\n",
				this.realiser.realise(section).getRealisation());
	}



}
