package com.aexiz.daviz.simulation.util;

import com.aexiz.daviz.frege.simulation.Set;
import com.aexiz.daviz.frege.simulation.Set.TSet;
import com.aexiz.daviz.simulation.Simulation;
import com.aexiz.daviz.simulation.viewpoint.Channel;
import com.aexiz.daviz.simulation.viewpoint.Node;
import com.aexiz.daviz.util.OrderedSetList;
import frege.prelude.PreludeBase.TList;
import frege.prelude.PreludeBase.TList.DCons;
import frege.prelude.PreludeBase.TTuple2;

import java.util.ArrayList;

public class FregeHelper {

    private Simulation simulation;

    public FregeHelper(Simulation sim) {
        simulation = sim;
    }

    public int getIdByNode(Node node) {
        return node.gethId();
    }

    public Node getNodeById(int id) {
        for (Node p : simulation.getNetwork().getNodes()) {
            if (p.ishIdEqual(id))
                return p;
        }
        throw new Error("Network and Haskell out-of-sync");
    }

    public Channel getChannelByIds(int from, int to) {
        for (Channel c : simulation.getNetwork().getChannels()) {
            if (c.from.ishIdEqual(from) && c.to.ishIdEqual(to))
                return c;
        }
        throw new Error("Network and Haskell out-of-sync");
    }

    public Channel getChannelByTuple(TTuple2<Integer, Integer> tuple) {
        int from = tuple.mem1.call().intValue();
        int to = tuple.mem2.call().intValue();
        return getChannelByIds(from, to);
    }

    public ArrayList<Channel> forEdgeSet(TSet<TTuple2<Integer, Integer>> set) {
        ArrayList<Channel> result = new OrderedSetList<>();
        TList<TTuple2<Integer, Integer>> l = Set.glueTuple2IntNormalS(set);
        while (l.asCons() != null) {
            DCons<TTuple2<Integer, Integer>> c = l.asCons();
            TTuple2<Integer, Integer> t = c.mem1.call();
            result.add(getChannelByTuple(t));
            l = c.mem2.call();
        }
        return result;
    }

    public ArrayList<Node> forVertexSet(TSet<Integer> set) {
        ArrayList<Node> result = new OrderedSetList<>();
        TList<Integer> l = Set.glueIntNormalS(set);
        while (l.asCons() != null) {
            DCons<Integer> c = l.asCons();
            result.add(getNodeById(c.mem1.call()));
            l = c.mem2.call();
        }
        return result;
    }

}

