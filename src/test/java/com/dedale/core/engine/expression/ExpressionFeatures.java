package com.dedale.core.engine.expression;

import static com.dedale.core.engine.expression.Expression.NEUTRAL;
import static com.dedale.core.engine.expression.ExpressionSamples.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import com.dedale.core.engine.ExecutionContext;
import com.dedale.core.engine.expression.ConcatCommand;
import com.dedale.core.engine.expression.Expression;
import com.dedale.core.engine.expression.ExpressionPrinter;
import com.dedale.core.engine.expression.TextExpression;

public class ExpressionFeatures {

    private ExecutionContext context = mock(ExecutionContext.class);

    @Test
    public void evaluate_neutral_will_return_initial_expression() throws Exception {
        Expression expression = anExpression.then(Expression.NEUTRAL);
        assertThat(expression).isEqualTo(anExpression);
    }

    @Test
    public void neutral_evaluate_expression_will_return_second_expression() throws Exception {
        Expression expression = NEUTRAL.then(anExpression);
        assertThat(expression).isEqualTo(anExpression);
    }

    @Test
    public void evaluate_two_text_expressions_will_return_expression_concatenation() throws Exception {
        // Given
        TextExpression first = new TextExpression("one");
        TextExpression second = new TextExpression("sentence");

        // When
        Expression expression = first.then(second);
        String print = print(expression);

        // Then
        assertThat(print).isEqualTo("one sentence");
    }

    @Test
    public void print_two_integer_expressions_an_one_operation_will_return_readable_result() throws Exception {
        // When
        Expression result = one.then(add).then(two);

        // Then
        assertThat(print(result)).isEqualTo("1+2");
    }

    @Test
    public void evaluate_two_integer_expressions_an_one_operation_will_return_readable_result() throws Exception {
        // When
        Expression result = one.then(add).then(two).execute(context);

        // Then
        assertThat(print(result)).isEqualTo("3");
    }

    @Test
    public void evaluate_two_integer_expressions_will_return_concat_result() throws Exception {
        // When
        Expression result = one.then(two).execute(context);

        // Then
        assertThat(print(result)).isEqualTo("1 2");
    }

    @Test
    public void evaluate_two_integer_expressions_will_return_concat_statement() throws Exception {
        // When
        Expression concat = one.then(two);

        // Then
        assertThat(concat).isInstanceOf(ConcatCommand.class);
    }

    @Test
    public void prints_composite_sum() throws Exception {

        // When
        Expression result = one.then(add).then(two).then(add).then(four);

        // Then
        assertThat(print(result)).isEqualTo("1+2+4");
    }

    @Test
    public void one_plus_two_plus_four_equals_seven() throws Exception {

        // When
        Expression notEvaluated = one.then(add).then(two).then(add).then(four);
        Expression result = notEvaluated.execute(context);

        // Then
        assertThat(print(notEvaluated)).isEqualTo("1+2+4");
        assertThat(print(result)).isEqualTo("7");
    }

    @Test
    public void one_plus_two_multiply_three_equals_seven() throws Exception {
        // When
        Expression notEvaluated = one.then(add).then(two).then(multiply).then(three);
        Expression result = notEvaluated.execute(context);

        // Then
        assertThat(print(notEvaluated)).isEqualTo("1+2*3");
        assertThat(print(result)).isEqualTo("7");
    }

    @Test
    public void one_plus_two_multiply_three_multiply_four_equals_twenty_five() throws Exception {
        // When
        Expression notEvaluated = one.then(add).then(two).then(multiply).then(three).then(multiply).then(four);
        Expression result = notEvaluated.execute(context);

        // Then
        assertThat(print(notEvaluated)).isEqualTo("1+2*3*4");
        assertThat(print(result)).isEqualTo("25");
    }

    @Test
    public void one_plus_two_multiply_three_plus_four_equals_eleven() throws Exception {
        // When
        Expression notEvaluated = one.then(add).then(two).then(multiply).then(three).then(add).then(four);
        Expression result = notEvaluated.execute(context);

        // Then
        assertThat(print(notEvaluated)).isEqualTo("1+2*3+4");
        assertThat(print(result)).isEqualTo("11");
    }

    @Test
    public void seven_minus_two_multiply_three_equals_one() throws Exception {
        // When
        Expression notEvaluated = seven.then(minus).then(two).then(multiply).then(three);
        Expression result = notEvaluated.execute(context);

        // Then
        assertThat(print(notEvaluated)).isEqualTo("7-2*3");
        assertThat(print(result)).isEqualTo("1");
    }

    @Test
    public void two_power_three_multiply_two_power_two_equals_thirty_two() throws Exception {
        // When
        Expression notEvaluated = two.then(power).then(three).then(multiply).then(two).then(power).then(two);
        Expression result = notEvaluated.execute(context);

        // Then
        assertThat(print(notEvaluated)).isEqualTo("2^3*2^2");
        assertThat(print(result)).isEqualTo("32");
    }

    private String print(Expression expression) {
        ExpressionPrinter printer = new ExpressionPrinter();
        expression.accept(printer);
        return printer.print();
    }

}
