package org.github.httpcode202.spring.migration.service;

import org.github.httpcode202.spring.datasource1.model.DataSource1Human;
import org.github.httpcode202.spring.datasource1.repository.Datasource1HumanRepository;
import org.github.httpcode202.spring.datasource2.model.DataSource2Human;
import org.github.httpcode202.spring.datasource2.repository.DataSource2HumanRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MigrationDataSource1To2Service {
    private static final Sort SORT_ASC_ID = Sort.by(Sort.Direction.ASC, "id");

    private final Datasource1HumanRepository datasource1HumanRepository;
    private final DataSource2HumanRepository dataSource2HumanRepository;

    public MigrationDataSource1To2Service(Datasource1HumanRepository datasource1HumanRepository, DataSource2HumanRepository dataSource2HumanRepository) {
        this.datasource1HumanRepository = datasource1HumanRepository;
        this.dataSource2HumanRepository = dataSource2HumanRepository;
    }

    public void migrateWithoutTransaction() {
        List<DataSource1Human> humans = datasource1HumanRepository.findAll(SORT_ASC_ID);

        for (DataSource1Human human : humans) {
            migrate(human);
        }
    }

    @Transactional("ds1TransactionManager")
    public void migrateWithTransactionCase1() {
        migrateWithoutTransaction();
    }

    @Transactional("ds2TransactionManager")
    public void migrateWithTransactionCase2() {
        migrateWithoutTransaction();
    }

    private void migrate(DataSource1Human dataSource1Human) {
        DataSource2Human human = new DataSource2Human(String.format("EARTH-%d", dataSource1Human.id()), dataSource1Human.firstname(), dataSource1Human.lastname());

        dataSource2HumanRepository.save(human);

        datasource1HumanRepository.delete(dataSource1Human);
    }
}
