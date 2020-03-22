package ca.frankcom.csc435.compiler.jasmin;

import ca.frankcom.csc435.compiler.jasmin.visit.IJasminVisitor;

public interface IJasminNode {

    <T> T accept(IJasminVisitor<T> visitor);

}
