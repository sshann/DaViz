package com.aexiz.daviz.simulation.algorithm.wave.tarry;

import com.aexiz.daviz.simulation.algorithm.Assumption;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface TarryAssumption {
    @NotNull
    @Contract(" -> new")
    static Assumption makeAssumption() {
        return new Assumption() {
            {
                centralized_user = true;
            }
        };
    }
}
