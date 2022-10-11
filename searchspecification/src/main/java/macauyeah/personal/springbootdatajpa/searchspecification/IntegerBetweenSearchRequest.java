package macauyeah.personal.springbootdatajpa.searchspecification;

public class IntegerBetweenSearchRequest extends BetweenSearchRequest<Integer> {
    public IntegerBetweenSearchRequest() {
    }

    public IntegerBetweenSearchRequest(Integer lowerBound, Integer upperBound) {
        this.setLowerBound(lowerBound);
        this.setUpperBound(upperBound);
    }
}
