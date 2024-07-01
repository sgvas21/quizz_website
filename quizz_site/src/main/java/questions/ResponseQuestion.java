package questions;

import response.Response;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class ResponseQuestion extends ResponseFill_InTheBlankQuestion {
    public ResponseQuestion(String question, List<String> legalAnswers, int id) {
        super(question, legalAnswers, id);
    }
    @Override
    public double getScore(Response response) {
        Iterator<String> iterator = response.getAllAnswers();
        while(iterator.hasNext()) {
            if (getLegalAnswers().contains(iterator.next())) return 1;
        }
        return 0;
    }

}
