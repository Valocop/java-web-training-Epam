package by.training.module3.handler;

import org.xml.sax.helpers.DefaultHandler;

import java.util.List;

public abstract class Handler<T> extends DefaultHandler {
    public abstract List<T> getEntities();
}
