package macauyeah.personal.springbootdatajpa.entitytwo.database.specification;

public class SomethingTwoFilter {
    private Integer columnOne;
    private String columnTwo;
    private String columnThree;
    private ManyToOneSearchRequest ref1;
    private OneToManySearchRequest ref2List;

    public ManyToOneSearchRequest getRef1() {
        return ref1;
    }

    public void setRef1(ManyToOneSearchRequest ref1) {
        this.ref1 = ref1;
    }

    public OneToManySearchRequest getRef2List() {
        return ref2List;
    }

    public void setRef2List(OneToManySearchRequest ref2List) {
        this.ref2List = ref2List;
    }

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
