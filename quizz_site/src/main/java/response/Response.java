package response;

import java.util.Iterator;

/**
 * This interface defines the contract for a Response,
 * which is expected to provide an iterator over a collection of answers.
 */
public interface Response {
    Iterator<String> getAllAnswers();
}
