package macauyeah.personal.springbootdatajpa.entitytwo.database.specification;

public class Ref2Filter extends OneToManySearchRequest {
    private Integer columnOne;
    private String columnTwo;
    private String columnThree;

    public Integer getColumnOne() {
        return columnOne;
    }

    public void setColumnOne(Integer columnOne) {
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
