package dev;

import java.util.EventListener;

public interface ExplainEventListener extends EventListener {
    void showMessage(ExplainEvent e);
}
