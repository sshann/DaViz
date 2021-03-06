package com.aexiz.daviz.simulation.algorithm;

import com.aexiz.daviz.simulation.viewpoint.Node;

import java.util.List;

public class Assumption {
    // Configured by subclass
    protected boolean infiniteGraph;
    protected boolean directedGraph;
    protected boolean acyclicGraph;
    protected boolean dynamicGraph;

    /**
     * True if first-in-first-out channels
     */
    protected boolean fifo_channels;

    /**
     * True if out-of-order channels
     */
    protected boolean ooo_channels;

    /**
     * User-input required
     */
    protected boolean centralized_user;

    /**
     * User-input required
     */
    protected boolean decentralized_user;

    /**
     * No user-input, algorithm decides initiators
     */
    protected boolean decentralized_computed;

    /**
     * No user-input, algorithm decides initiator
     */
    protected boolean centralized_computed;

    // TODO Verify why assumption needs to know who is the initiator node
    /**
     * Given by user or undefined
     */
    Node initiator;

    /**
     * Given by user or undefined
     */
    List<Node> initiators;

    public Node getInitiator() {
        if (initiator == null) throw new Error("Initiator is not set");
        return initiator;
    }

    public void setInitiator(Node node) {
        if (!centralized_user) throw new Error("Algorithm is not centralized and not user-defined");
        initiator = node;
    }

    public List<Node> getInitiators() {
        return initiators;
    }

    public void setInitiators(List<Node> initiators) {
        this.initiators = initiators;
    }

    public boolean isDirectedGraph() {
        return directedGraph;
    }

    public boolean isAcyclicGraph() {
        return acyclicGraph;
    }

    public boolean isCentralized() {
        return centralized_user || centralized_computed;
    }

    public boolean isDecentralized() {
        return decentralized_user || decentralized_computed;
    }

    public boolean isInitiatorUser() {
        return centralized_user || decentralized_user;
    }

    public boolean isCentralized_user() {
        return centralized_user;
    }
}
