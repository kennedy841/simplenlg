package simplenlg.italian;

import org.junit.Test;
import simplenlg.features.Feature;
import simplenlg.features.Tense;
import simplenlg.framework.NLGFactory;
import simplenlg.lexicon.Lexicon;
import simplenlg.lexicon.italian.ITXMLLexicon;
import simplenlg.phrasespec.SPhraseSpec;
import simplenlg.realiser.Realiser;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TestsimplenlgTest {

    @Test
    public void test() {
        Lexicon lexIta = new ITXMLLexicon();
        NLGFactory nlgFactory = new NLGFactory(lexIta);
        Realiser realiser = new Realiser();

        //realiser.setDebugMode(true);
        String output = null;

        SPhraseSpec clause = nlgFactory.createClause("loro", "essere", "bello");
        clause.setFeature(Feature.TENSE, Tense.PAST);
        //clause.setFeature(Feature.PERFECT, true);
        output = realiser.realiseSentence(clause);
        System.out.println(output);

        assertThat("Loro erano belli.", is(output));

    }

}
