package macauyeah.personal.springbootdatajpa.entityone.database.entity.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
// import static org.junit.jupiter.api.Assertions.assertTrue;

// import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import macauyeah.personal.springbootdatajpa.entityone.database.entity.SomethingOne;
import macauyeah.personal.springbootdatajpa.entityone.database.repository.SomethingOneRepo;

@ActiveProfiles("test")
@SpringBootTest
public class EntityOneConnectionTest {
    @Autowired
    private SomethingOneRepo oneRepo;

    @Test
    // @Transactional
    public void test() {
        assertNotNull(oneRepo);
        if (oneRepo.count() > 1) {
            oneRepo.findAll();
        }
        SomethingOne one = new SomethingOne();
        one.setColumnOne(1);
        oneRepo.save(one);

        one.setColumnOne(2);
        oneRepo.save(one);

        assertFalse(TransactionSynchronizationManager.isActualTransactionActive());
        // assertTrue(TransactionSynchronizationManager.isActualTransactionActive());
        if (!TransactionSynchronizationManager.isActualTransactionActive()){
            assertThrows(ObjectOptimisticLockingFailureException.class, () -> {
                one.setVersion(0);
                oneRepo.save(one); // if you mark the test as transactional, no exception will be flow
            });
        }
    }
}