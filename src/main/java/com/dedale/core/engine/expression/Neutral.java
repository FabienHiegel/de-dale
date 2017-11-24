package com.dedale.core.engine.expression;

import com.dedale.core.engine.ExecutionContext;

final class Neutral implements Expression {
    
    Neutral() {
    }

    @Override
    public Neutral execute(ExecutionContext context) {
        return this;
    }

    @Override
    public Expression then(Expression after) {
        return after;
    }

    @Override
    public String toString() {
        return "<neutral>";
    }

}