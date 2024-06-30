package response;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * This class implements the Response interface, providing a concrete implementation
 * that holds a list of answers and allows iteration over them.
 */
public class AnswerResponse implements Response {
    private final List<String> allAnswers;

    /**
     * Constructor to initialize the AnswerResponse with a list of answers.
     *
     * @param allAnswers The list of answers to be stored.
     */
    public AnswerResponse(List<String> allAnswers) {
        this.allAnswers = allAnswers;
    }

    /**
     * Retrieves an iterator over all answers.
     *
     * @return An Iterator<String> over the list of all answers.
     */
    @Override
    public Iterator<String> getAllAnswers() {
        return allAnswers.iterator();
    }
}
