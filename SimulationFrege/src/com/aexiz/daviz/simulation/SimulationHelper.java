package com.aexiz.daviz.simulation;

import com.aexiz.daviz.frege.simulation.Set;
import com.aexiz.daviz.frege.simulation.Set.TSet;
import frege.prelude.PreludeBase.TList;
import frege.prelude.PreludeBase.TList.DCons;
import frege.prelude.PreludeBase.TTuple2;

import java.util.ArrayList;

public class SimulationHelper {

    private Simulation simulation;

    SimulationHelper(Simulation sim) {
        simulation = sim;
    }

    public int getIdByNode(Viewpoint.Node node) {
        return node.hId;
    }

    public Viewpoint.Node getNodeById(int id) {
        for (Viewpoint.Node p : simulation.getNetwork().getNodes()) {
            if (p.hId == id)
                return p;
        }
        throw new Error("Network and Haskell out-of-sync");
    }

    public Viewpoint.Channel getChannelByIds(int from, int to) {
        for (Viewpoint.Channel c : simulation.getNetwork().getChannels()) {
            if (c.from.hId == from && c.to.hId == to)
                return c;
        }
        throw new Error("Network and Haskell out-of-sync");
    }

    public Viewpoint.Channel getChannelByTuple(TTuple2<Integer, Integer> tuple) {
        int from = tuple.mem1.call().intValue();
        int to = tuple.mem2.call().intValue();
        return getChannelByIds(from, to);
    }

    public ArrayList<Viewpoint.Channel> forEdgeSet(TSet<TTuple2<Integer, Integer>> set) {
        ArrayList<Viewpoint.Channel> result = new OrderedSetList<>();
        TList<TTuple2<Integer, Integer>> l = Set.glueTuple2IntNormalS(set);
        while (l.asCons() != null) {
            DCons<TTuple2<Integer, Integer>> c = l.asCons();
            TTuple2<Integer, Integer> t = c.mem1.call();
            result.add(getChannelByTuple(t));
            l = c.mem2.call();
        }
        return result;
    }

    public ArrayList<Viewpoint.Node> forVertexSet(TSet<Integer> set) {
        ArrayList<Viewpoint.Node> result = new OrderedSetList<>();
        TList<Integer> l = Set.glueIntNormalS(set);
        while (l.asCons() != null) {
            DCons<Integer> c = l.asCons();
            result.add(getNodeById(c.mem1.call()));
            l = c.mem2.call();
        }
        return result;
    }

}

class OrderedSetList<T> extends ArrayList<T> {

    private static final long serialVersionUID = -7310476084643000609L;

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        boolean first = true;
        for (T c : this) {
            if (first) first = false;
            else sb.append(',');
            sb.append(c);
        }
        sb.append('}');
        return sb.toString();
    }

}
