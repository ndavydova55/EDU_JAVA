package edu.bigdata.training.testservice.service;

import edu.bigdata.training.testservice.controller.model.Person;
import edu.bigdata.training.testservice.dao.TestServiceRepository;
import edu.bigdata.training.testservice.model.PersonEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestBusinessLogicServiceTest.TestBusinessLogicServiceTestConfiguration.class})
public class TestBusinessLogicServiceTest {

    @Autowired
    private TestBusinessLogicService testBusinessLogicService;

    @Autowired
    private TestServiceRepository testServiceRepository;

    @Test
    public void testCreate(){
        //create
        Person person = new Person("test");

        PersonEntity personEntity = testBusinessLogicService.processCreate(person);

        Assert.assertEquals(person.getName(), personEntity.getName());
        Mockito.verify(testServiceRepository, Mockito.times(1)).save(personEntity);

    }
    @Test
    public void testGetAll(){

        List<PersonEntity> personEntityList = testBusinessLogicService.processGetAll();

        Assert.assertEquals("name1", personEntityList.get(0).getName());
        Assert.assertEquals("name2", personEntityList.get(1).getName());
        Mockito.verify(testServiceRepository, Mockito.times(1)).getAll();
    }
    @Test
    public void testGet(){

        PersonEntity personEntityList = testBusinessLogicService.processGet(UUID.randomUUID().toString());

        Assert.assertEquals("name", personEntityList.getName());
        Mockito.verify(testServiceRepository, Mockito.times(1)).get(any());

    }

    @Test
    public void testUpdate(){
        Person person = new Person("test");
        Person person_upd = new Person("test_upd");

        PersonEntity personEntity = testBusinessLogicService.processCreate(person);

        PersonEntity personEntity_upd =testBusinessLogicService.processUpdate(person_upd, personEntity.getId().toString());

        Assert.assertEquals(person_upd.getName(), personEntity_upd.getName());
        Mockito.verify(testServiceRepository, Mockito.times(1)).update(personEntity_upd,personEntity.getId());

    }

    @Test
    public void testDelete(){
        Person person = new Person("test_del");
        PersonEntity personEntity = testBusinessLogicService.processCreate(person);
        testBusinessLogicService.processDel(personEntity.getId().toString());
        Mockito.verify(testServiceRepository, Mockito.times(1)).del(personEntity.getId());

    }

    @Configuration
    static class TestBusinessLogicServiceTestConfiguration {

        @Bean
        TestServiceRepository testServiceRepository() {
            TestServiceRepository testServiceRepository = mock(TestServiceRepository.class);
            when(testServiceRepository.get(any())).thenReturn(new PersonEntity("name"));
            when(testServiceRepository.getAll())
                    .thenReturn(Arrays.asList(new PersonEntity("name1"),new PersonEntity("name2")));
            return testServiceRepository;
        }

        @Bean
        TestBusinessLogicService testBusinessLogicService(TestServiceRepository testServiceRepository){
            return new TestBusinessLogicService(testServiceRepository);
        }
    }

}
