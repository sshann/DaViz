package com.aexiz.daviz.simulation.algorithm.wave;

import com.aexiz.daviz.frege.simulation.Process.TProcessDescription;
import com.aexiz.daviz.frege.simulation.algorithm.wave.Cidon.TMS;
import com.aexiz.daviz.frege.simulation.algorithm.wave.Cidon.TPS;
import com.aexiz.daviz.frege.simulation.algorithm.wave.Cidon.TRRUI;
import com.aexiz.daviz.simulation.*;
import com.aexiz.daviz.simulation.algorithm.information.message.MessageInformation;
import com.aexiz.daviz.simulation.algorithm.information.result.ResultInformation;
import com.aexiz.daviz.simulation.algorithm.information.state.PropertyVisitor;
import com.aexiz.daviz.simulation.algorithm.information.state.StateInformation;
import com.aexiz.daviz.simulation.algorithm.wave.cidon.*;
import frege.prelude.PreludeBase.TMaybe.DJust;
import frege.prelude.PreludeBase.TTuple2;
import frege.run8.Thunk;

import java.util.List;

import static com.aexiz.daviz.frege.simulation.algorithm.wave.Cidon.procDesc;

public class Cidon extends AbstractFregeBasicAlgorithm {

    public Cidon() {
        assumption = CidonAssumption.makeAssumption();
    }

    @Override
    public Integer getMaxRounds(Network network) {
        return (network.getNodes().length + network.getChannels().length) * 15;
    }

    @Override
    public MessageInformation makeAndUnloadMessage(FregeHelper helper, Object o) {
        FregeAlgorithm.validateParameters(helper, o);

        short t = (Short) o;

        if (t == TMS.Token) return new CidonToken();
        if (t == TMS.Inf) return new CidonInfo();
        throw new Error("Unknown message");
    }

    @Override
    public StateInformation makeAndUnloadState(FregeHelper helper, Object o) {
        FregeAlgorithm.validateParameters(helper, o);

        TPS st = (TPS) o;
        TRRUI rrui = st.mem$state.call();
        CidonState cidonState = new CidonState();
        PropertyVisitor state;
        if (rrui.asReceived() != null) {
            state = new CidonReceived(helper.getChannelByTuple(rrui.asReceived().mem1.call()));
        } else if (rrui.asReplied() != null) {
            state = new CidonReplied(helper.getChannelByTuple(rrui.asReplied().mem1.call()));
        } else if (rrui.asUndefined() != null) {
            state = new CidonUndefined();
        } else if (rrui.asInitiator() != null) {
            state = new CidonInitiator();
        } else {
            throw new Error("Invalid RRUI value");
        }

        DJust<TTuple2<Integer, Integer>> in;
        in = st.mem$intention.call().asJust();
        boolean hasToken = st.mem$hasToken.call();
        Channel intention = in == null ? null : helper.getChannelByTuple(in.mem1.call());
        List<Channel> forward = helper.forEdgeSet(st.mem$forward.call());
        List<Channel> info = helper.forEdgeSet(st.mem$info.call());

        cidonState.setState(state);
        cidonState.setForward(forward);
        cidonState.setInfo(info);
        cidonState.setHasToken(hasToken);
        cidonState.setIntention(intention);
        cidonState.makeProperties();
        return cidonState;
    }

    @Override
    public ResultInformation makeAndUnloadResult(FregeHelper helper, Object o) {
        FregeAlgorithm.validateParameters(helper, o);
        return (Boolean) o ? new CidonTerminated() : new CidonDecided();
    }

    @Override
    public TProcessDescription<Object, Object, Object, Object> getProcessDescription(FregeHelper helper) {
        return procDesc(Thunk.lazy(helper.getIdByNode(assumption.getInitiator()))).simsalabim();
    }

}
