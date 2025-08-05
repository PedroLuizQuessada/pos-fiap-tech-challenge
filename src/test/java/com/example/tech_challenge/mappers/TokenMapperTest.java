package com.example.tech_challenge.mappers;

import com.example.tech_challenge.dtos.responses.TokenResponse;
import com.example.tech_challenge.entities.Token;
import com.example.tech_challenge.exceptions.BadArgumentException;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class TokenMapperTest {

    @Test
    void toResponse_mapsFieldsCorrectly() {
        Token token = new Token("abc123", "alice");
        TokenResponse response = TokenMapper.toResponse(token);
        assertThat(response.token()).isEqualTo("abc123");
        assertThat(response.login()).isEqualTo("alice");
    }

    @Nested
    class TokenValidationTests {

        @Test
        void constructor_nullToken_throwsBadArgumentException() {
            assertThatThrownBy(() -> new Token(null, "user"))
                    .isInstanceOf(BadArgumentException.class)
                    .hasMessageContaining("token deve possuir um valor");
        }

        @Test
        void constructor_emptyToken_throwsBadArgumentException() {
            assertThatThrownBy(() -> new Token("", "user"))
                    .isInstanceOf(BadArgumentException.class)
                    .hasMessageContaining("token deve possuir um valor");
        }

        @Test
        void constructor_nullLogin_throwsBadArgumentException() {
            assertThatThrownBy(() -> new Token("tok", null))
                    .isInstanceOf(BadArgumentException.class)
                    .hasMessageContaining("login do token deve possuir um valor");
        }

        @Test
        void constructor_emptyLogin_throwsBadArgumentException() {
            assertThatThrownBy(() -> new Token("tok", ""))
                    .isInstanceOf(BadArgumentException.class)
                    .hasMessageContaining("login do token deve possuir um valor");
        }

        @Test
        void constructor_tokenAndLoginInvalid_combinesMessages() {
            assertThatThrownBy(() -> new Token("", ""))
                    .isInstanceOf(BadArgumentException.class)
                    .hasMessageContaining("token deve possuir um valor")
                    .hasMessageContaining("login do token deve possuir um valor");
        }
    }
}
