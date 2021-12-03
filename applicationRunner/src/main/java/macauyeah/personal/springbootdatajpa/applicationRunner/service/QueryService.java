package macauyeah.personal.springbootdatajpa.applicationRunner.service;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import macauyeah.personal.springbootdatajpa.entityone.database.repository.SomethingOneRepo;
import macauyeah.personal.springbootdatajpa.entitytwo.database.entity.SomethingTwo;
import macauyeah.personal.springbootdatajpa.entitytwo.database.repository.SomethingTwoRepo;

@Service
public class QueryService {
    private static Logger LOG = LoggerFactory.getLogger(QueryService.class);
    @Autowired
    private SomethingOneRepo somethingOneRepo;
    @Autowired
    private SomethingTwoRepo somethingTwoRepo;

    @Transactional(value = "entityTwoTransactionManager")
    public void selectOneCommitTwo() {
        somethingOneRepo.count();
        SomethingTwo two = new SomethingTwo();
        two.setColumnOne(2);
        somethingTwoRepo.save(two);
    }
}
