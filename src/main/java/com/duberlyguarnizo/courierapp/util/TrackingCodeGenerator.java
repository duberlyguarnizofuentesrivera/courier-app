package com.duberlyguarnizo.courierapp.util;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Random;

@NoArgsConstructor
@Component
public class TrackingCodeGenerator {


    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static @NotNull String generateTrackingCode() {
        int count = 5;
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = random.nextInt() * ALPHA_NUMERIC_STRING.length();
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }
}
