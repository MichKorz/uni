package eu.jpereira.trainings.designpatterns.structural.decorator.channel.decorator;

import eu.jpereira.trainings.designpatterns.structural.decorator.channel.SocialChannel;

import java.util.ArrayList;

public class WordCensor extends SocialChannelDecorator {
    ArrayList<String> censoredWords;

    public WordCensor() {
        censoredWords = new ArrayList<>();
        censoredWords.add("Microsoft");
        censoredWords.add("Google");
        censoredWords.add("Facebook");
        censoredWords.add("Twitter");
    }

    /*
     * (non-Javadoc)
     *
     * @see eu.jpereira.trainings.designpatterns.structural.decorator.channel.
     * SocialChannel#deliverMessage(java.lang.String)
     */
    @Override
    public void deliverMessage(String message) {
        message = censorMessage(message);
        delegate.deliverMessage(message);

    }

    public String censorMessage(String message) {
        for (String word : censoredWords) {
            word = "(?i)"+word;
            message = message.replaceAll(word, "###");
        }
        return message;
    }

}