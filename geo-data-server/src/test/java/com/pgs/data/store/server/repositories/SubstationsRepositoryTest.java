/**
 * Copyright (c) 2019, RTE (http://www.rte-france.com)
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.pgs.data.store.server.repositories;

import com.pgs.data.store.server.CassandraConfig;
import org.cassandraunit.spring.CassandraDataSet;
import org.cassandraunit.spring.CassandraUnitDependencyInjectionTestExecutionListener;
import org.cassandraunit.spring.EmbeddedCassandra;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Chamseddine Benhamed <chamseddine.benhamed at rte-france.com>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CassandraConfig.class)
@TestExecutionListeners({ CassandraUnitDependencyInjectionTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class })
@CassandraDataSet(value = "geo_data.cql", keyspace = "geo_data")
@EmbeddedCassandra
public class SubstationsRepositoryTest {

    @Autowired
    private SubstationsRepository repository;

    @Test
    public void test() throws InterruptedException {
        repository.save(SubstationEntity.builder()
                .country("FR")
                .substationID("ID")
                .coordinate(CoordinateEntity.builder().lat(3).lon(2).build())
                .voltages(Arrays.asList(225, 63))
                .build());

        List<SubstationEntity> substations = repository.findAll();

        assertEquals(1, substations.size());
        assertEquals("FR", substations.get(0).getCountry());
        assertEquals("ID", substations.get(0).getSubstationID());
        assertEquals(3, substations.get(0).getCoordinate().getLat(), 0);
        assertEquals(2, substations.get(0).getCoordinate().getLon(), 0);

        assertEquals(2, substations.get(0).getVoltages().size());
    }
}
