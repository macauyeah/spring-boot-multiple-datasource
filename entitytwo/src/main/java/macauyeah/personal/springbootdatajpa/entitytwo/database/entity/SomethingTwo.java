package macauyeah.personal.springbootdatajpa.entitytwo.database.entity;

import java.math.BigInteger;

import javax.persistence.*;

@Entity
public class SomethingTwo {
    // #region attribute
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger id;
    private Integer columnOne;
    // #endregion attribute

    // #region getter setter
    public BigInteger getId() {
        return this.id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public Integer getColumnOne() {
        return this.columnOne;
    }

    public void setColumnOne(Integer columnOne) {
        this.columnOne = columnOne;
    }
    // #endregion getter setter
}
