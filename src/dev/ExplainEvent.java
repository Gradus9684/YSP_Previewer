package dev;

import java.util.EventListener;
import java.util.EventObject;

public class ExplainEvent extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */

    private String[][] messages;

    public ExplainEvent(Object source, String[][] input) {
        super(source);
        this.messages = input;
    }

    public String[][] getMessages(){
        return messages;
    }
}

