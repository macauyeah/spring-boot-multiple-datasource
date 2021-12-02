package macauyeah.personal.springbootdatajpa.entityone.database.entity.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    private SomethingOneRepo empStaffRepo;

    // @Autowired
    // private DataSource dataSource;

    @Test
    public void test(){
        assertNotNull(empStaffRepo);
        if (empStaffRepo.count() > 1){
            empStaffRepo.findAll();
        }
        
    }
}