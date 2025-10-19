package io.github.mrergos.dialect;

import org.hibernate.dialect.DatabaseVersion;
import org.hibernate.dialect.HSQLDialect;
import org.hibernate.engine.jdbc.dialect.spi.DialectResolutionInfo;

public class MSAccessBracketDialect extends HSQLDialect {

    public MSAccessBracketDialect(DialectResolutionInfo info) {
        super(info);
    }

    public MSAccessBracketDialect(DatabaseVersion version) {
        super(version);
    }

    public MSAccessBracketDialect() {
        super();
    }

    @Override
    public char openQuote() {
        return '[';
    }

    @Override
    public char closeQuote() {
        return ']';
    }
}
