package com.gmail.maxsvynarchuk.service;

import com.gmail.maxsvynarchuk.persistence.dao.FrequencyDao;
import com.gmail.maxsvynarchuk.persistence.dao.PeriodicalDao;
import com.gmail.maxsvynarchuk.persistence.dao.PeriodicalTypeDao;
import com.gmail.maxsvynarchuk.persistence.dao.PublisherDao;
import com.gmail.maxsvynarchuk.persistence.entity.Frequency;
import com.gmail.maxsvynarchuk.persistence.entity.Periodical;
import com.gmail.maxsvynarchuk.persistence.entity.PeriodicalType;
import com.gmail.maxsvynarchuk.persistence.entity.Publisher;
import com.gmail.maxsvynarchuk.util.type.PeriodicalStatus;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@SpringBootTest
class PeriodicalServiceTest {
    @InjectMocks
    private PeriodicalService periodicalService;
    @Mock
    private PeriodicalDao periodicalDao;
    @Mock
    private PeriodicalTypeDao periodicalTypeDao;
    @Mock
    private FrequencyDao frequencyDao;
    @Mock
    private PublisherDao publisherDao;

    @Test
    void createPeriodicalTest() {
        Long periodicalId = 1L;
        Periodical periodical = Periodical.builder()
                .publisher(Publisher.builder().build())
                .frequency(Frequency.builder().build())
                .periodicalType(PeriodicalType.builder().build())
                .build();
        when(periodicalDao.insert(periodical)).then((Answer<Periodical>) invocationOnMock -> {
            Periodical toReturn = invocationOnMock.getArgument(0);
            toReturn.setId(periodicalId);
            return toReturn;
        });
        when(periodicalService.existPeriodicalTypeById(any())).thenReturn(true);
        when(periodicalService.existFrequencyById(any())).thenReturn(true);
        when(periodicalService.existPublisherById(any())).thenReturn(true);

        Periodical actual = periodicalService.createPeriodical(periodical);

        assertEquals(periodicalId, actual.getId());
        assertEquals(PeriodicalStatus.ACTIVE, actual.getStatus());
        verify(periodicalDao, times(1)).insert(periodical);
        verify(publisherDao, times(1)).exist(any());
        verify(periodicalTypeDao, times(1)).exist(any());
        verify(frequencyDao, times(1)).exist(any());
    }

    @Test
    void updatePeriodicalTest() {
        Periodical periodical = Periodical.builder()
                .id(1L)
                .publisher(Publisher.builder().build())
                .frequency(Frequency.builder().build())
                .periodicalType(PeriodicalType.builder().build())
                .build();
        when(periodicalService.existPeriodicalTypeById(any())).thenReturn(true);
        when(periodicalService.existFrequencyById(any())).thenReturn(true);
        when(periodicalService.existPublisherById(any())).thenReturn(true);

        periodicalService.updatePeriodical(periodical);
        verify(periodicalDao, times(1)).update(periodical);
        verify(publisherDao, times(1)).exist(any());
        verify(periodicalTypeDao, times(1)).exist(any());
        verify(frequencyDao, times(1)).exist(any());
    }

    @Test
    void changeStatusOnAnotherStatusTest() {
        Periodical periodical = Periodical.builder()
                .id(1L)
                .publisher(Publisher.builder().build())
                .frequency(Frequency.builder().build())
                .periodicalType(PeriodicalType.builder().build())
                .build();
        when(periodicalService.existPeriodicalTypeById(any())).thenReturn(true);
        when(periodicalService.existFrequencyById(any())).thenReturn(true);
        when(periodicalService.existPublisherById(any())).thenReturn(true);

        periodicalService.changeStatus(periodical, PeriodicalStatus.SUSPENDED);

        assertEquals(periodical.getStatus(), PeriodicalStatus.SUSPENDED);
        verify(periodicalDao, times(1)).update(periodical);
        verify(publisherDao, times(1)).exist(any());
        verify(periodicalTypeDao, times(1)).exist(any());
        verify(frequencyDao, times(1)).exist(any());
    }


    @Test
    void changeStatusOnSameStatusTest() {
        Periodical periodical = Periodical.builder()
                .id(1L)
                .status(PeriodicalStatus.ACTIVE)
                .build();

        periodicalService.changeStatus(periodical, PeriodicalStatus.ACTIVE);

        assertEquals(periodical.getStatus(), PeriodicalStatus.ACTIVE);
        verify(periodicalDao, never()).update(periodical);
    }

    @Test
    void findPeriodicalByIdWithExistingPeriodicalTest() {
        Long periodicalId = 1L;
        Optional<Periodical> expected = Optional.of(
                Periodical.builder()
                        .id(periodicalId)
                        .build());
        when(periodicalDao.findOne(periodicalId)).thenReturn(expected);

        Optional<Periodical> actual = periodicalService.findPeriodicalById(periodicalId);

        assertEquals(expected, actual);
        verify(periodicalDao, times(1)).findOne(periodicalId);
    }

    @Test
    void findPeriodicalByIdWithNotExistingPeriodicalTest() {
        Long periodicalId = 1L;
        when(periodicalDao.findOne(periodicalId)).thenReturn(Optional.empty());

        Optional<Periodical> periodicalOpt = periodicalService.findPeriodicalById(periodicalId);

        assertFalse(periodicalOpt.isPresent());
        verify(periodicalDao, times(1)).findOne(periodicalId);
    }

    @Test
    void findAllPeriodicalsTest() {
        int page = 0;
        int size = 5;
        List<Periodical> periodicals = new ArrayList<Periodical>() {{
            add(Periodical.builder().id(1L).build());
            add(Periodical.builder().id(2L).build());
            add(Periodical.builder().id(3L).build());
        }};
        Page<Periodical> expected = new PageImpl<>(periodicals);
        PageRequest pageable = PageRequest.of(page, size, Sort.by(
                Sort.Order.asc("status"),
                Sort.Order.desc("id")));
        when(periodicalDao.findAll(pageable)).thenReturn(expected);

        Page<Periodical> actual = periodicalService.findAllPeriodicals(page, size);

        assertEquals(3, actual.getContent().size());
        verify(periodicalDao, times(1)).findAll(pageable);
    }

    @Test
    void findAllPeriodicalsByStatusTest() {
        int page = 0;
        int size = 3;
        PeriodicalStatus status = PeriodicalStatus.ACTIVE;
        List<Periodical> periodicals = new ArrayList<Periodical>() {{
            add(Periodical.builder().id(1L).build());
            add(Periodical.builder().id(2L).build());
            add(Periodical.builder().id(3L).build());
        }};
        Page<Periodical> expected = new PageImpl<>(periodicals);
        PageRequest pageable = PageRequest.of(page, size, Sort.by(
                Sort.Order.asc("status"),
                Sort.Order.desc("id")));
        when(periodicalDao.findByStatus(status, pageable))
                .thenReturn(expected);

        Page<Periodical> actual = periodicalService.findAllPeriodicalsByStatus(status, page, size);

        assertEquals(3, actual.getContent().size());
        verify(periodicalDao, times(1)).findByStatus(status, pageable);
    }

    @Test
    void findAllPeriodicalTypesTest() {
        List<PeriodicalType> expected = new ArrayList<PeriodicalType>() {{
            add(PeriodicalType.builder().id(1).build());
            add(PeriodicalType.builder().id(2).build());
            add(PeriodicalType.builder().id(3).build());
        }};
        when(periodicalTypeDao.findAll()).thenReturn(expected);

        List<PeriodicalType> actual = periodicalService.findAllPeriodicalTypes();

        assertEquals(3, actual.size());
        verify(periodicalTypeDao, times(1)).findAll();
    }

    @Test
    void findAllFrequenciesTest() {
        List<Frequency> expected = new ArrayList<Frequency>() {{
            add(Frequency.builder().id(1).build());
            add(Frequency.builder().id(2).build());
            add(Frequency.builder().id(3).build());
        }};
        when(frequencyDao.findAll()).thenReturn(expected);

        List<Frequency> actual = periodicalService.findAllFrequencies();

        assertEquals(3, actual.size());
        verify(frequencyDao, times(1)).findAll();
    }

    @Test
    void findAllPublishersTest() {
        List<Publisher> expected = new ArrayList<Publisher>() {{
            add(new Publisher(1L, "Publisher 1"));
            add(new Publisher(2L, "Publisher 2"));
            add(new Publisher(3L, "Publisher 3"));
        }};
        when(publisherDao.findAll()).thenReturn(expected);

        List<Publisher> actual = periodicalService.findAllPublishers();

        assertEquals(3, actual.size());
        verify(publisherDao, times(1)).findAll();
    }
}