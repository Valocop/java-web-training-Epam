package by.training.module3.builder;

import by.training.module3.entity.Medicine;

public final class MedicineBuilderFactory {

    private MedicineBuilderFactory() {}

    public static Builder<Medicine> getBuilder(ParserType parserType) {
        switch (parserType) {
            case DOM:
                return new MedicineDOMBuilder();
            case SAX:
                return new MedicineSAXBuilder();
            case StAX:
                return new MedicineStAXBuilder();
            default:
                throw new IllegalArgumentException("Builder not found.");
        }
    }
}
