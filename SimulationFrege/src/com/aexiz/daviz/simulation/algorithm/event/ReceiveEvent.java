package com.aexiz.daviz.simulation.algorithm.event;

import com.aexiz.daviz.frege.simulation.Event;
import com.aexiz.daviz.simulation.algorithm.FregeAlgorithm;
import com.aexiz.daviz.simulation.algorithm.information.message.MessageInformation;
import com.aexiz.daviz.simulation.algorithm.information.state.StateInformation;
import com.aexiz.daviz.simulation.util.FregeHelper;
import com.aexiz.daviz.simulation.viewpoint.Node;

public class ReceiveEvent extends DefaultEvent implements tReceiveEvent {

    // Haskell dependencies
    transient Event.TEvent.DEReceive<Object, Object, Object> hEvent;

    // Computed properties
    transient MessageInformation message;
    transient StateInformation nextState;
    transient Node sender;

    ReceiveEvent() {
        super();
    }

    @Override
    protected void unload() {
        super.unload();
        hEvent = super.hEvent.asEReceive();
        FregeHelper helper = new FregeHelper(simulation);
        message = ((FregeAlgorithm) simulation.getAlgorithm()).makeAndUnloadMessage(helper, hEvent.mem$msg.call());
        nextState = ((FregeAlgorithm) simulation.getAlgorithm()).makeAndUnloadState(helper, hEvent.mem$next.call());
        sender = simulation.getNetwork().getNodeById(hEvent.mem$send.call());
    }

    @Override
    protected ReceiveEvent clone(DefaultEvent to) {
        super.clone(to);
        ReceiveEvent tor = (ReceiveEvent) to;
        tor.hEvent = this.hEvent;
        tor.message = this.message;
        tor.nextState = this.nextState;
        tor.sender = this.sender;
        return tor;
    }

    @Override
    public ReceiveEvent clone() {
        return clone(new ReceiveEvent());
    }

    @Override
    public boolean hasMessage() {
        return true;
    }

    @Override
    public MessageInformation getMessage() {
        return message;
    }

    @Override
    public boolean hasNextState() {
        return true;
    }

    @Override
    public StateInformation getNextState() {
        return nextState;
    }

    @Override
    public boolean hasSender() {
        return true;
    }

    @Override
    public Node getSender() {
        return sender;
    }

    @Override
    public boolean hasMatchingEvent() {
        return true;
    }

    @Override
    public SendEvent getMatchingEvent() {
        if (matchingEvent == null) throw new Error("Unmatched receive event");
        return (SendEvent) matchingEvent;
    }

    @Override
    public String toString() {
        return "Process " + happensAt.getLabel() + " receives " + message + " from " + sender;
    }

}
