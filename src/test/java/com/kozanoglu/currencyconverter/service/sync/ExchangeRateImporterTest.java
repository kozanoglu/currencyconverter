package com.kozanoglu.currencyconverter.service.sync;

import com.kozanoglu.currencyconverter.repository.ExchangeRateRepository;
import com.kozanoglu.currencyconverter.repository.entity.ExchangeRate;
import com.kozanoglu.currencyconverter.service.api.ExchangeRateAPIClient;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:spring/applicationContext.xml"})
public class ExchangeRateImporterTest {

    @Test
    public void shouldScheduleImportTask()
    {
        // Given
        ExchangeRateRepository exchangeRateRepository = Mockito.mock(ExchangeRateRepository.class);
        ExchangeRateAPIClient exchangeRateAPIClient = Mockito.mock(ExchangeRateAPIClient.class);

        long time = 1535193880646L;
        double rate = 1.2;
        when(exchangeRateAPIClient.fetchRateFromAPI()).thenReturn(new ExchangeRate(time, rate)).thenReturn(null);

        ExchangeRateImporter importer = new ExchangeRateImporter(exchangeRateRepository, exchangeRateAPIClient);
        ReflectionTestUtils.setField(importer, "exchangerateApiSyncPeriod", 1L);

        // When
        importer.scheduleTask();

        ArgumentCaptor<ExchangeRate> captor = ArgumentCaptor.forClass(ExchangeRate.class);
        verify(exchangeRateRepository, times(1)).save(captor.capture());

        // Then
        Assertions.assertThat(captor.getAllValues().get(0).getRate()).isEqualTo(rate);
        Assertions.assertThat(captor.getAllValues().get(0).getTimestamp()).isEqualTo(time);
    }
}
