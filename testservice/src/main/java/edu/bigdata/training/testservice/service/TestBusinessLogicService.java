package edu.bigdata.training.testservice.service;

import edu.bigdata.training.testservice.controller.model.Person;
import edu.bigdata.training.testservice.dao.TestServiceRepository;
import edu.bigdata.training.testservice.model.PersonEntity;

import java.util.List;
import java.util.UUID;

public class TestBusinessLogicService {

    private TestServiceRepository testServiceRepository;

    public TestBusinessLogicService(TestServiceRepository testServiceRepository) {
        this.testServiceRepository = testServiceRepository;
    }

    public PersonEntity processCreate(Person person){
        PersonEntity personEntity = new PersonEntity(person.getName());
        testServiceRepository.save(personEntity);
        return personEntity;
    }

    public PersonEntity processUpdate(Person person, String id){
        PersonEntity personEntity_curr = testServiceRepository.get(UUID.fromString(id));
        personEntity_curr.setName(person.getName());
        testServiceRepository.update(personEntity_curr, UUID.fromString(id));
        return personEntity_curr;
    }

    public PersonEntity processGet(String id){
        return testServiceRepository.get(UUID.fromString(id));
    }

    public void processDel(String id){
        testServiceRepository.del(UUID.fromString(id));
    }

    public List<PersonEntity> processGetAll(){
        return testServiceRepository.getAll();
    }
}
