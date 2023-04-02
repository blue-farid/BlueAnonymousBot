package com.blue_farid.blue_anonymous_bot.mapper;

import com.blue_farid.blue_anonymous_bot.model.Gender;
import com.blue_farid.blue_anonymous_bot.telegram.command.CommandConstant;
import com.blue_farid.blue_anonymous_bot.utils.LocaleUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GenderMapper {

    private final MessageSource source;

    private final LocaleUtils localeUtils;

    public Gender persianGenderValueToGender(String value) {
        if (value.equals(CommandConstant.CHOOSE_GENDER_MALE)) {
            return Gender.MALE;
        } else if (value.equals(CommandConstant.CHOOSE_GENDER_FEMALE)) {
            return Gender.FEMALE;
        } else {
            return Gender.BI;
        }
    }
}
