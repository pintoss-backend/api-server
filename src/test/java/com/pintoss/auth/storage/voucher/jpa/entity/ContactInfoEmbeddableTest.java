package com.pintoss.auth.storage.voucher.jpa.entity;

import com.pintoss.auth.core.voucher.domain.ContactInfo;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
class ContactInfoEmbeddableTest {

    @Test
    @DisplayName("엔티티를 도메인으로 변환할 수 있다.")
    void givenContactInfoEmbeddable_whenToDomain_thenReturnContactInfo() {
        // given
        ContactInfoEmbeddable contactInfoEmbeddable = new ContactInfoEmbeddable(
                new HomePageEmbeddable("http://example.com"),
                new CsCenterEmbeddable("123-456-7890")
        );

        // when
        ContactInfo contactInfo = contactInfoEmbeddable.toDomain();

        // then
        assertThat(contactInfo.getHomePage().getUrl()).isEqualTo("http://example.com");
        assertThat(contactInfo.getCsCenter().getTel()).isEqualTo("123-456-7890");
    }
}
