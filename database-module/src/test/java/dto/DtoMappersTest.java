package dto;


import com.pagacz.database.dto.FlatOfferDto;
import com.pagacz.database.mapper.DtoMappers;
import com.pagacz.database.model.FlatOffer;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;


class DtoMappersTest {

    DtoMappers dtoMappers = Mappers.getMapper(DtoMappers.class);

    @Test
    void givenFlatOfferEntityToOffer_whenMaps_thenCorrect() {
        FlatOffer offer = new FlatOffer();
        offer.setId(1L);
        offer.setTitle("title");
        offer.setAddress("Address");
        offer.setLink("link");
        offer.setComment("comment");
        offer.setSource("source");
        offer.setPrice(10);
        offer.setOriginalPrice(12);
        offer.setSpace(13.22);

        FlatOfferDto flatOfferDto = dtoMappers.offerToDto(offer);

        assertEquals(flatOfferDto.getId(), offer.getId());
        assertEquals(flatOfferDto.getTitle(), offer.getTitle());
        assertEquals(flatOfferDto.getAddress(), offer.getAddress());
        assertEquals(flatOfferDto.getLink(), offer.getLink());
        assertEquals(flatOfferDto.getComment(), offer.getComment());
        assertEquals(flatOfferDto.getSource(), offer.getSource());
        assertEquals(flatOfferDto.getPrice(), offer.getPrice());
        assertEquals(flatOfferDto.getOriginalPrice(), offer.getOriginalPrice());
        assertEquals(flatOfferDto.getSpace(), offer.getSpace());
    }

}
