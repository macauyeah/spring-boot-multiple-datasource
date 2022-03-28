package macauyeah.personal.springbootdatajpa.applicationRunner.service;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import macauyeah.personal.springbootdatajpa.entityone.database.repository.SomethingOneRepo;
import macauyeah.personal.springbootdatajpa.entitytwo.database.entity.*;
import macauyeah.personal.springbootdatajpa.entitytwo.database.repository.*;

@Service
public class QueryService {
    private static Logger LOG = LoggerFactory.getLogger(QueryService.class);
    @Autowired
    private Ref1Repo ref1Repo;
    @Autowired
    private Ref2Repo ref2Repo;
    @Autowired
    private SomethingOneRepo somethingOneRepo;
    @Autowired
    private SomethingTwoRepo somethingTwoRepo;

    @Transactional(value = "entityTwoTransactionManager")
    public void selectOneCommitTwo() {
        somethingOneRepo.count();
        Ref1 ref1 = new Ref1();
        ref1Repo.save(ref1);
        Ref2 ref2 = new Ref2();
        ref2Repo.save(ref2);
        SomethingTwo two = new SomethingTwo();
        two.setColumnOne(2);
        two.setRef1(ref1);
        two.addRef2(ref2);
        somethingTwoRepo.save(two);
    }
}
