package simplenlg.italian;

import org.junit.Assert;
import org.junit.Test;
import simplenlg.features.Feature;
import simplenlg.features.Tense;
import simplenlg.framework.DocumentElement;
import simplenlg.framework.NLGFactory;
import simplenlg.lexicon.Lexicon;
import simplenlg.lexicon.italian.ITXMLLexicon;
import simplenlg.phrasespec.SPhraseSpec;
import simplenlg.realiser.Realiser;

import static org.hamcrest.core.Is.is;

public class MultiLingualTest {

    @Test
    public void test() {

        Lexicon englishLexicon = new simplenlg.lexicon.english.XMLLexicon();
        NLGFactory englishFactory = new NLGFactory(englishLexicon);
        Lexicon frenchLexicon = new simplenlg.lexicon.french.XMLLexicon();
        NLGFactory frenchFactory = new NLGFactory(frenchLexicon);
        Lexicon italianLexicon = new ITXMLLexicon();
        NLGFactory italianFactory = new NLGFactory(italianLexicon);

        Realiser realiser = new Realiser();
        //realiser.setDebugMode(true);
        String output = null;

        SPhraseSpec clauseIt = italianFactory.createClause("Paolo", "amare", "Freancesca");
        clauseIt.setFeature(Feature.TENSE, Tense.PAST);

        SPhraseSpec clauseEn = englishFactory.createClause("John", "love", "Mary");
        clauseEn.setFeature(Feature.TENSE, Tense.PAST);

        SPhraseSpec clauseFr = frenchFactory.createClause("Pierre", "aimer", "Sophie");
        clauseFr.setFeature(Feature.TENSE, Tense.PAST);


        DocumentElement paragraph = englishFactory.createParagraph();
        paragraph.addComponent(clauseIt);
        paragraph.addComponent(clauseEn);
        paragraph.addComponent(clauseFr);
        DocumentElement document = englishFactory.createDocument("Trilingual love\n");
        document.addComponent(paragraph);

        String outString = realiser.realise(document).getRealisation();
        Assert.assertThat("Trilingual love\n\nPaolo amava Freancesca. John loved Mary. Pierre a aimé Sophie.\n\n", is(outString));
        System.out.print(outString);
    }
}
