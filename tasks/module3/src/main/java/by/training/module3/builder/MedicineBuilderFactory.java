package by.training.module3.builder;

public final class MedicineBuilderFactory {

    private MedicineBuilderFactory() {}

    public static Builder getBuilder(ParserType parserType) {
        switch (parserType) {
            case DOM:
                return new MedicineDOMBuilder();
            case SAX:
                return new MedicineSAXBuilder();
            default:
                throw new IllegalArgumentException("Builder not found.");
        }
    }
}
