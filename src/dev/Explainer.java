package dev;

import java.util.ArrayList;

public class Explainer {
    private ArrayList<ExplainEventListener> listeners = new ArrayList<ExplainEventListener>();
    public void addExplainEventListener(ExplainEventListener lis){
        listeners.add(lis);
    }

    //移除Listener
    public void removeExplainEventListener(ExplainEventListener lis){
        listeners.remove(lis);
    }

    //产生事件,可以类比JButton的点击
    public void generateEvent(){
        ExplainEvent me = new ExplainEvent(this,textpad.TextList);
        notifyListener(me);
    }

    //通知所有的Listener，发生了事件
    public void notifyListener(ExplainEvent ea){
        for(ExplainEventListener listener : listeners){
            listener.showMessage(ea);
        }
    }
    
}
