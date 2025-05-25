package be.nicholasmeyers.headoftp.common.domain.validation;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = PRIVATE)
public class Error {
    private final String key;
    private final List<String> parameters;

    public Error(String key, String ...parameters) {
        this.key = key;
        this.parameters = Arrays.asList(parameters);
    }
}
