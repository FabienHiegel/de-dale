package com.dedale.calculator;

public class StringCalculator {
    
    public Integer calc(String sentence) {
        for (StringToIntegerOperation operation : StringToIntegerOperation.values()) {
            if (operation.mayApplyOperation(sentence)) {
                return operation.apply(sentence, this::calc);
            }
        }
        return Integer.parseInt(sentence);
    }
    
}
