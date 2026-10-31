package org.github.httpcode202.spring;

import org.github.httpcode202.spring.datasource1.model.DataSource1Human;
import org.github.httpcode202.spring.datasource1.repository.Datasource1HumanRepository;
import org.github.httpcode202.spring.datasource2.model.DataSource2Human;
import org.github.httpcode202.spring.datasource2.repository.DataSource2HumanRepository;
import org.github.httpcode202.spring.migration.service.MigrationDataSource1To2Service;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = Application.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ApplicationTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationTest.class);

    @Autowired
    private Datasource1HumanRepository datasource1HumanRepository;
    @Autowired
    private DataSource2HumanRepository dataSource2HumanRepository;
    @Autowired
    private MigrationDataSource1To2Service migrationDataSource1To2Service;

    @Test
    void migrateWithoutTransaction() {
        DataSource1Human dataSource1Human1 = new DataSource1Human("Red", "Orange");
        DataSource1Human dataSource1Human2 = new DataSource1Human("Blue", null);
        DataSource1Human dataSource1Human3 = new DataSource1Human("Yellow", "Purple");
        DataSource2Human dataSource2Human1 = new DataSource2Human("EARTH-1", "Red", "Orange");
        Sort sort = Sort.by(Sort.Direction.ASC, "id");

        datasource1HumanRepository.save(dataSource1Human1);
        datasource1HumanRepository.save(dataSource1Human2);
        datasource1HumanRepository.save(dataSource1Human3);

        dataSource1HumanAssertEquals(List.of(dataSource1Human1, dataSource1Human2, dataSource1Human3), datasource1HumanRepository.findAll(sort));
        assertEquals(0, dataSource2HumanRepository.count());

        try {
            migrationDataSource1To2Service.migrateWithoutTransaction();
        } catch (Exception exception) {
            LOGGER.error(exception.getMessage(), exception);
        }

        dataSource1HumanAssertEquals(List.of(dataSource1Human2, dataSource1Human3), datasource1HumanRepository.findAll(sort));
        dataSource2HumanAssertEquals(List.of(dataSource2Human1), dataSource2HumanRepository.findAll(sort));
    }

    @Test
    void migrateWithTransactionCase1() {
        DataSource1Human dataSource1Human1 = new DataSource1Human("Red", "Orange");
        DataSource1Human dataSource1Human2 = new DataSource1Human("Blue", null);
        DataSource1Human dataSource1Human3 = new DataSource1Human("Yellow", "Purple");
        DataSource2Human dataSource2Human1 = new DataSource2Human("EARTH-1", "Red", "Orange");
        Sort sort = Sort.by(Sort.Direction.ASC, "id");

        datasource1HumanRepository.save(dataSource1Human1);
        datasource1HumanRepository.save(dataSource1Human2);
        datasource1HumanRepository.save(dataSource1Human3);

        dataSource1HumanAssertEquals(List.of(dataSource1Human1, dataSource1Human2, dataSource1Human3), datasource1HumanRepository.findAll(sort));
        assertEquals(0, dataSource2HumanRepository.count());

        try {
            migrationDataSource1To2Service.migrateWithTransactionCase1();
        } catch (Exception exception) {
            LOGGER.error(exception.getMessage(), exception);
        }

        dataSource1HumanAssertEquals(List.of(dataSource1Human1, dataSource1Human2, dataSource1Human3), datasource1HumanRepository.findAll(sort));
        dataSource2HumanAssertEquals(List.of(dataSource2Human1), dataSource2HumanRepository.findAll(sort));
    }

    @Test
    void migrateWithTransactionCase2() {
        DataSource1Human dataSource1Human1 = new DataSource1Human("Red", "Orange");
        DataSource1Human dataSource1Human2 = new DataSource1Human("Blue", null);
        DataSource1Human dataSource1Human3 = new DataSource1Human("Yellow", "Purple");
        Sort sort = Sort.by(Sort.Direction.ASC, "id");

        datasource1HumanRepository.save(dataSource1Human1);
        datasource1HumanRepository.save(dataSource1Human2);
        datasource1HumanRepository.save(dataSource1Human3);

        dataSource1HumanAssertEquals(List.of(dataSource1Human1, dataSource1Human2, dataSource1Human3), datasource1HumanRepository.findAll(sort));
        assertEquals(0, dataSource2HumanRepository.count());

        try {
            migrationDataSource1To2Service.migrateWithTransactionCase2();
        } catch (Exception exception) {
            LOGGER.error(exception.getMessage(), exception);
        }

        dataSource1HumanAssertEquals(List.of(dataSource1Human2, dataSource1Human3), datasource1HumanRepository.findAll(sort));
        assertEquals(0, dataSource2HumanRepository.count());
    }

    private void dataSource1HumanAssertEquals(List<DataSource1Human> expecteds, List<DataSource1Human> actuals) {
        assertEquals(expecteds.size(), actuals.size());

        for (int i = 0; i < expecteds.size(); i++) {
            DataSource1Human expected = expecteds.get(i);
            DataSource1Human actual = actuals.get(i);

            assertEquals(expected.id(), actual.id());
            assertEquals(expected.firstname(), actual.firstname());
            assertEquals(expected.lastname(), actual.lastname());
        }
    }

    private void dataSource2HumanAssertEquals(List<DataSource2Human> expecteds, List<DataSource2Human> actuals) {
        assertEquals(expecteds.size(), actuals.size());

        for (int i = 0; i < expecteds.size(); i++) {
            DataSource2Human expected = expecteds.get(i);
            DataSource2Human actual = actuals.get(i);

            assertEquals(expected.id(), actual.id());
            assertEquals(expected.firstname(), actual.firstname());
            assertEquals(expected.lastname(), actual.lastname());
        }
    }
}