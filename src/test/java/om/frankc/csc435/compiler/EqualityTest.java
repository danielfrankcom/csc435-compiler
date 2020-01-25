package om.frankc.csc435.compiler;

import om.frankc.csc435.compiler.ast.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class EqualityTest {

    private static void assertObjectsEqual(Object o1, Object o2) {
        assertEquals(o1, o2);
        assertEquals(o1.hashCode(), o2.hashCode());

        assertNotEquals(o1, null);
        assertNotEquals(null, o2);

        assertNotEquals(o1, new Object());
        assertNotEquals(new Object(), o2);
    }

    private static void assertObjectsNotEqual(Object... objects) {
        for (Object left : objects) {
            for (Object right : objects) {
                if (left == right) {
                    // References are the same, cannot compare.
                    return;
                }

                assertNotEquals(left, right);
                // Duplicate hashes are valid so we do not check.

                assertNotEquals(left, null);
                assertNotEquals(null, right);

                assertNotEquals(left, new Object());
                assertNotEquals(new Object(), right);
            }
        }
    }

    @Test
    public void testTypeEquality() {
        for (Type.Name name : Type.Name.values()) {
            final AstNode type1 = new Type(name, 1, 0);
            final AstNode type2 = new Type(name, 1, 0);
            assertObjectsEqual(type1, type2);
        }

        assertObjectsNotEqual(
                new Type(Type.Name.Boolean, 1, 0),
                new Type(Type.Name.Boolean, 2, 0),
                new Type(Type.Name.Boolean, 1, 2),
                new Type(Type.Name.Void, 1, 0),
                new Type(Type.Name.Integer, 2, 0)
        );
    }

    @Test
    public void testProgramEquality() {
        final AstNode program1 = new Program(
                new FunctionList(
                        new ArrayList<>() {{
                            add(new Function(
                                            new FunctionDeclaration(
                                                    new Type(Type.Name.Boolean, 1, 0),
                                                    new Identifier("foo", 1, 5),
                                                    new FormalParameterList(Collections.emptyList(), 1, 0),
                                                    4,
                                                    0
                                            ),
                                            new FunctionBody(
                                                    new VariableDeclarationList(
                                                            new ArrayList<>() {{
                                                                add(new VariableDeclaration(
                                                                                new Type(Type.Name.Integer, 5, 0),
                                                                                new Identifier("variable", 1, 0),
                                                                                1,
                                                                                1
                                                                        )
                                                                );
                                                            }},
                                                            1,
                                                            0),
                                                    new StatementList(Collections.emptyList(), 1, 0),
                                                    1,
                                                    0
                                            ),
                                            1,
                                            0
                                    )
                            );
                        }},
                        9,
                        8
                ),
                100,
                0
        );
        final AstNode program2 = new Program(
                new FunctionList(
                        new ArrayList<>() {{
                            add(new Function(
                                            new FunctionDeclaration(
                                                    new Type(Type.Name.Boolean, 1, 0),
                                                    new Identifier("foo", 1, 5),
                                                    new FormalParameterList(Collections.emptyList(), 1, 0),
                                                    4,
                                                    0
                                            ),
                                            new FunctionBody(
                                                    new VariableDeclarationList(
                                                            new ArrayList<>() {{
                                                                add(new VariableDeclaration(
                                                                                new Type(Type.Name.Integer, 5, 0),
                                                                                new Identifier("variable", 1, 0),
                                                                                1,
                                                                                1
                                                                        )
                                                                );
                                                            }},
                                                            1,
                                                            0),
                                                    new StatementList(Collections.emptyList(), 1, 0),
                                                    1,
                                                    0
                                            ),
                                            1,
                                            0
                                    )
                            );
                        }},
                        9,
                        8
                ),
                100,
                0
        );
        assertObjectsEqual(program1, program2);
    }

    @Test
    public void testProgramInequality() {
        assertObjectsNotEqual(
                new Program(
                        new FunctionList(
                                new ArrayList<>() {{
                                    add(new Function(
                                                    new FunctionDeclaration(
                                                            new Type(Type.Name.Boolean, 1, 0),
                                                            new Identifier("foo", 1, 5),
                                                            new FormalParameterList(Collections.emptyList(), 1, 0),
                                                            4,
                                                            0
                                                    ),
                                                    new FunctionBody(
                                                            new VariableDeclarationList(
                                                                    new ArrayList<>() {{
                                                                        add(new VariableDeclaration(
                                                                                        new Type(Type.Name.Integer, 5, 0),
                                                                                        new Identifier("variable", 1, 0),
                                                                                        1,
                                                                                        1
                                                                                )
                                                                        );
                                                                    }},
                                                                    1,
                                                                    0),
                                                            new StatementList(Collections.emptyList(), 1, 0),
                                                            1,
                                                            0
                                                    ),
                                                    1,
                                                    0
                                            )
                                    );
                                }},
                                9,
                                8
                        ),
                        100,
                        0
                ),
                new Program(
                        new FunctionList(
                                new ArrayList<>() {{
                                    add(new Function(
                                                    new FunctionDeclaration(
                                                            new Type(Type.Name.Boolean, 1, 0),
                                                            new Identifier("foo", 1, 5),
                                                            new FormalParameterList(Collections.emptyList(), 1, 0),
                                                            4,
                                                            0
                                                    ),
                                                    new FunctionBody(
                                                            new VariableDeclarationList(
                                                                    new ArrayList<>() {{
                                                                        add(new VariableDeclaration(
                                                                                        new Type(Type.Name.Integer, 5, 0),
                                                                                        new Identifier("variables", 1, 0),
                                                                                        1,
                                                                                        1
                                                                                )
                                                                        );
                                                                    }},
                                                                    1,
                                                                    0),
                                                            new StatementList(Collections.emptyList(), 1, 0),
                                                            1,
                                                            0
                                                    ),
                                                    1,
                                                    0
                                            )
                                    );
                                }},
                                9,
                                8
                        ),
                        100,
                        0
                ),
                new Program(
                        new FunctionList(
                                new ArrayList<>() {{
                                    add(new Function(
                                                    new FunctionDeclaration(
                                                            new Type(Type.Name.Boolean, 1, 0),
                                                            new Identifier("foo", 1, 5),
                                                            new FormalParameterList(Collections.emptyList(), 1, 0),
                                                            4,
                                                            0
                                                    ),
                                                    new FunctionBody(
                                                            new VariableDeclarationList(
                                                                    new ArrayList<>() {{
                                                                        add(new VariableDeclaration(
                                                                                        new Type(Type.Name.Integer, 5, 0),
                                                                                        new Identifier("variable", 1, 0),
                                                                                        1,
                                                                                        1
                                                                                )
                                                                        );
                                                                    }},
                                                                    1,
                                                                    0),
                                                            new StatementList(Collections.emptyList(), 1, 2),
                                                            1,
                                                            0
                                                    ),
                                                    1,
                                                    0
                                            )
                                    );
                                }},
                                9,
                                8
                        ),
                        100,
                        0
                ),
                new Program(
                        new FunctionList(
                                new ArrayList<>() {{
                                    add(new Function(
                                                    new FunctionDeclaration(
                                                            new Type(Type.Name.Void, 1, 0),
                                                            new Identifier("foo", 1, 5),
                                                            new FormalParameterList(Collections.emptyList(), 1, 0),
                                                            4,
                                                            0
                                                    ),
                                                    new FunctionBody(
                                                            new VariableDeclarationList(
                                                                    new ArrayList<>() {{
                                                                        add(new VariableDeclaration(
                                                                                        new Type(Type.Name.Integer, 5, 0),
                                                                                        new Identifier("variable", 1, 0),
                                                                                        1,
                                                                                        1
                                                                                )
                                                                        );
                                                                    }},
                                                                    1,
                                                                    0),
                                                            new StatementList(Collections.emptyList(), 1, 0),
                                                            1,
                                                            0
                                                    ),
                                                    1,
                                                    0
                                            )
                                    );
                                }},
                                9,
                                8
                        ),
                        100,
                        0
                ),
                new Program(
                        new FunctionList(
                                new ArrayList<>() {{
                                    add(new Function(
                                                    new FunctionDeclaration(
                                                            new Type(Type.Name.Void, 1, 0),
                                                            new Identifier("foo", 1, 5),
                                                            new FormalParameterList(Collections.emptyList(), 1, 0),
                                                            4,
                                                            0
                                                    ),
                                                    new FunctionBody(
                                                            new VariableDeclarationList(
                                                                    new ArrayList<>() {{
                                                                        add(new VariableDeclaration(
                                                                                        new Type(Type.Name.Integer, 5, 0),
                                                                                        new Identifier("variable", 1, 0),
                                                                                        1,
                                                                                        1
                                                                                )
                                                                        );
                                                                        add(new VariableDeclaration(
                                                                                        new Type(Type.Name.Integer, 5, 0),
                                                                                        new Identifier("variable", 1, 0),
                                                                                        1,
                                                                                        1
                                                                                )
                                                                        );
                                                                    }},
                                                                    1,
                                                                    0),
                                                            new StatementList(Collections.emptyList(), 1, 0),
                                                            1,
                                                            0
                                                    ),
                                                    1,
                                                    0
                                            )
                                    );
                                }},
                                9,
                                8
                        ),
                        100,
                        0
                )
        );
    }

}
