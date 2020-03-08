package om.frankc.csc435.compiler.ast.visit.irgen;

import om.frankc.csc435.compiler.ir.IrLabel;

public class LabelFactory {

    private int mCounter = 0;

    public IrLabel get() {
        return new IrLabel(mCounter++);
    }

}
