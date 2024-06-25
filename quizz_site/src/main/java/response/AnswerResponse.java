package response;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class AnswerResponse implements Response {
    private final List<String> allAnswers;
    public AnswerResponse(List<String> allAnswers) {
        this.allAnswers = allAnswers;
    }
    @Override
    public Iterator<String> getAllAnswers() {
        return allAnswers.iterator();
    }
}
