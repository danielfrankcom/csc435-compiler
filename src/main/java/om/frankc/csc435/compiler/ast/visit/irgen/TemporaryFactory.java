package om.frankc.csc435.compiler.ast.visit.irgen;

import com.google.common.collect.ImmutableList;
import om.frankc.csc435.compiler.ir.IrTemp;
import om.frankc.csc435.compiler.ir.IrType;

import java.util.LinkedList;
import java.util.List;

public class TemporaryFactory {

    private final List<IrTemp> mReturned = new LinkedList<>();
    private int mCounter = 0;

    public IrTemp get(IrType type, IrTemp.Category category) {
        final IrTemp temporary = new IrTemp(mCounter++, type, category);
        mReturned.add(temporary);
        return temporary;
    }

    public ImmutableList<IrTemp> getReturned() {
        return ImmutableList.copyOf(mReturned);
    }

}
