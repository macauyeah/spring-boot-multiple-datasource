package macauyeah.personal.springbootdatajpa.entitytwo.database.specification;

public class IntegerBetweenSearchRequest extends BetweenSearchRequest<Integer> {
    public IntegerBetweenSearchRequest() {
    }

    public IntegerBetweenSearchRequest(Integer lowerBound, Integer upperBound) {
        this.setLowerBound(lowerBound);
        this.setUpperBound(upperBound);
    }
}
