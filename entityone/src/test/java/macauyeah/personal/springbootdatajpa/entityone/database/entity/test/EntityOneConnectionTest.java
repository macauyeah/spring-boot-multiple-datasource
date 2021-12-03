package macauyeah.personal.springbootdatajpa.entityone.database.entity.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import macauyeah.personal.springbootdatajpa.entityone.database.repository.SomethingOneRepo;

@ActiveProfiles("test")
@SpringBootTest
public class EntityOneConnectionTest {
    @Autowired
    private SomethingOneRepo oneRepo;

    @Test
    public void test() {
        assertNotNull(oneRepo);
        if (oneRepo.count() > 1) {
            oneRepo.findAll();
        }

    }
}