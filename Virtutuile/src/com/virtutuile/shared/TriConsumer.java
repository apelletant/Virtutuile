package com.virtutuile.shared;

import com.virtutuile.afficheur.tools.ValidationsException;

@FunctionalInterface
public interface TriConsumer<In1, In2, In3> {
    public void apply(In1 in1, In2 in2, In3 in3) throws ValidationsException;
}