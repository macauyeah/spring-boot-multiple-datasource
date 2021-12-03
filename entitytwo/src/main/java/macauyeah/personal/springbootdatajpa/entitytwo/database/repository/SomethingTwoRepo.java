package macauyeah.personal.springbootdatajpa.entitytwo.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import macauyeah.personal.springbootdatajpa.entitytwo.database.entity.SomethingTwo;

import java.util.Collection;
import java.util.List;
import java.math.BigInteger;

@Repository
public interface SomethingTwoRepo extends JpaRepository<SomethingTwo, BigInteger> {
    List<SomethingTwo> findAllByColumnOneIn(Collection<Integer> columnOne);
}
