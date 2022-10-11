package macauyeah.personal.springbootdatajpa.entitytwo.database.specification;

import macauyeah.personal.springbootdatajpa.searchspecification.OperatorSearchRequest;
import macauyeah.personal.springbootdatajpa.searchspecification.JoinSearchRequest;

public class Ref1Filter implements JoinSearchRequest {
    private OperatorSearchRequest<Integer> columnOne;
    private String columnTwo;
    private String columnThree;

    public OperatorSearchRequest<Integer> getColumnOne() {
        return columnOne;
    }

    public void setColumnOne(OperatorSearchRequest<Integer> columnOne) {
        this.columnOne = columnOne;
    }

    public String getColumnTwo() {
        return columnTwo;
    }

    public void setColumnTwo(String columnTwo) {
        this.columnTwo = columnTwo;
    }

    public String getColumnThree() {
        return columnThree;
    }

    public void setColumnThree(String columnThree) {
        this.columnThree = columnThree;
    }

}
