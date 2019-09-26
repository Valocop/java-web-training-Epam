package by.training.module1.validator;

import by.training.module1.builder.DecorType;

import java.util.Optional;

public class ValidatorFactory {

    public Validator getValidator(String type) {
        Optional<DecorType> optionalType = DecorType.fromString(type);

        if (!optionalType.isPresent()) {
            return null;
        }

        switch (optionalType.get()) {
            case AMBER:
                return new AmberValidator();
            case PEARL:
                return new PearlValidator();
            default:
                return null;
        }
    }
}
