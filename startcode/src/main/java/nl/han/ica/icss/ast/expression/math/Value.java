package nl.han.ica.icss.ast.expression.math;

import nl.han.ica.icss.ast.Expression;

public record Value<T>(Expression.Type type, T value) {}
