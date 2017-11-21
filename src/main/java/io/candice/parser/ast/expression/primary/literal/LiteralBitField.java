
package io.candice.parser.ast.expression.primary.literal;

import io.candice.parser.visitor.SQLASTVisitor;

public class LiteralBitField extends Literal {
    private final String text;
    private final String introducer;

    /**
     * @param introducer e.g. "_latin1"
     * @param bitFieldText e.g. "01010"
     */
    public LiteralBitField(String introducer, String bitFieldText) {
        super();
        if (bitFieldText == null) throw new IllegalArgumentException("bit text is null");
        this.introducer = introducer;
        this.text = bitFieldText;
    }

    public String getText() {
        return text;
    }

    public String getIntroducer() {
        return introducer;
    }

    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }
}
