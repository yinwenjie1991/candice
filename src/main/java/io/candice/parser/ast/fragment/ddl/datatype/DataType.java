package io.candice.parser.ast.fragment.ddl.datatype;

import io.candice.parser.ast.ASTNode;
import io.candice.parser.ast.expression.Expression;
import io.candice.parser.ast.expression.primary.Identifier;
import io.candice.parser.visitor.SQLASTVisitor;

import java.util.List;

public class DataType implements ASTNode {
    public static enum DataTypeName {
        BIT,
        TINYINT,
        SMALLINT,
        MEDIUMINT,
        INT,
        BIGINT,
        REAL,
        DOUBLE,
        FLOAT,
        DECIMAL,
        DATE,
        TIME,
        TIMESTAMP,
        DATETIME,
        YEAR,
        CHAR,
        VARCHAR,
        BINARY,
        VARBINARY,
        TINYBLOB,
        BLOB,
        MEDIUMBLOB,
        LONGBLOB,
        TINYTEXT,
        TEXT,
        MEDIUMTEXT,
        LONGTEXT,
        ENUM,
        SET
    }

    //              BIT[(length)]
    //            | TINYINT[(length)] [UNSIGNED] [ZEROFILL]
    //            | SMALLINT[(length)] [UNSIGNED] [ZEROFILL]
    //            | MEDIUMINT[(length)] [UNSIGNED] [ZEROFILL]
    //            | INT[(length)] [UNSIGNED] [ZEROFILL]
    //            | INTEGER[(length)] [UNSIGNED] [ZEROFILL]
    //            | BIGINT[(length)] [UNSIGNED] [ZEROFILL]
    //            | DOUBLE[(length,decimals)] [UNSIGNED] [ZEROFILL]
    //            | REAL[(length,decimals)] [UNSIGNED] [ZEROFILL]
    //            | FLOAT[(length,decimals)] [UNSIGNED] [ZEROFILL]
    //            | DECIMAL[(length[,decimals])] [UNSIGNED] [ZEROFILL]
    //            | NUMERIC[(length[,decimals])] [UNSIGNED] [ZEROFILL] 同上
    //            | DATE
    //            | TIME
    //            | TIMESTAMP
    //            | DATETIME
    //            | YEAR
    //            | CHAR[(length)][CHARACTER SET charset_name] [COLLATE collation_name]
    //            | VARCHAR(length)[CHARACTER SET charset_name] [COLLATE collation_name]
    //            | BINARY[(length)]
    //            | VARBINARY(length)
    //            | TINYBLOB
    //            | BLOB
    //            | MEDIUMBLOB
    //            | LONGBLOB
    //            | TINYTEXT [BINARY][CHARACTER SET charset_name] [COLLATE collation_name]
    //            | TEXT [BINARY][CHARACTER SET charset_name] [COLLATE collation_name]
    //            | MEDIUMTEXT [BINARY][CHARACTER SET charset_name] [COLLATE collation_name]
    //            | LONGTEXT [BINARY][CHARACTER SET charset_name] [COLLATE collation_name]
    //            | ENUM(value1,value2,value3,...)[CHARACTER SET charset_name] [COLLATE collation_name]
    //            | SET(value1,value2,value3,...)[CHARACTER SET charset_name] [COLLATE collation_name]
    //            | spatial_type 不支持

    private final DataTypeName typeName;
    private final boolean unsigned;
    private final boolean zerofill;
    /** for text only */
    private final boolean binary;
    private final Expression length;
    private final Expression decimals;
    private final Identifier charSet;
    private final Identifier collation;
    private final List<Expression> collectionVals;

    public DataType(DataTypeName typeName, boolean unsigned, boolean zerofill, boolean binary, Expression length,
                    Expression decimals, Identifier charSet, Identifier collation, List<Expression> collectionVals) {
        if (typeName == null) throw new IllegalArgumentException("typeName is null");
        this.typeName = typeName;
        this.unsigned = unsigned;
        this.zerofill = zerofill;
        this.binary = binary;
        this.length = length;
        this.decimals = decimals;
        this.charSet = charSet;
        this.collation = collation;
        this.collectionVals = collectionVals;
    }

    public DataTypeName getTypeName() {
        return typeName;
    }

    public boolean isUnsigned() {
        return unsigned;
    }

    public boolean isZerofill() {
        return zerofill;
    }

    public boolean isBinary() {
        return binary;
    }

    public Expression getLength() {
        return length;
    }

    public Expression getDecimals() {
        return decimals;
    }

    public Identifier getCharSet() {
        return charSet;
    }

    public Identifier getCollation() {
        return collation;
    }

    public List<Expression> getCollectionVals() {
        return collectionVals;
    }

    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }
}
