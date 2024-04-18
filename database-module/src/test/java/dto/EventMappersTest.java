package dto;

import com.pagacz.database.mapper.CharSequenceMapper;
import com.pagacz.database.mapper.EventMappers;
import com.pagacz.database.model.FlatOffer;
import com.pagacz.kafka.listener.model.generated.FlatData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EventMappersTest {

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Spy
    @InjectMocks
    EventMappers eventMappers = Mappers.getMapper(EventMappers.class);

    @Spy
    CharSequenceMapper charSequenceMapper = Mappers.getMapper(CharSequenceMapper.class);

    @Test
    void givenFlatDataToOffer_whenMaps_thenCorrect() {
        FlatData offer = new FlatData();
        offer.setTitle("title");
        offer.setAddress("Address");
        offer.setLink("link");
        offer.setComment("comment");
        offer.setSource("source");
        offer.setPrice("10");
        offer.setOriginalPrice("10");

        FlatOffer offerDto = eventMappers.flatDataToOffer(offer);

        assertEquals(offerDto.getTitle(), offer.getTitle());
        assertEquals(offerDto.getAddress(), offer.getAddress());
        assertEquals(offerDto.getLink(), offer.getLink());
        assertEquals(offerDto.getComment(), offer.getComment());
        assertEquals(offerDto.getSource(), offer.getSource());
        assertEquals(offerDto.getPrice(), Integer.valueOf(String.valueOf(offer.getPrice())));
        assertEquals(offerDto.getOriginalPrice(), Integer.valueOf(String.valueOf(offer.getOriginalPrice())));
    }
}
