package nl.han.ica.icss.ast.expression;

import nl.han.ica.icss.ast.Expression;

public record Value<T>(Expression.Type type, T value) {}
