package macauyeah.personal.springbootdatajpa.entitytwo.database.specification;

public class BetweenSearchRequest<X extends Comparable<? super X>> {
    private X lowerBound;
    private X upperBound;

    public X getLowerBound() {
        return lowerBound;
    }
    public void setLowerBound(X lowerBound) {
        this.lowerBound = lowerBound;
    }
    public X getUpperBound() {
        return upperBound;
    }
    public void setUpperBound(X upperBound) {
        this.upperBound = upperBound;
    }    
}
