import org.scalatest.FunSuite

class ExpressionEvaluationTest extends FunSuite {

  test("testEvaluateBooleanExpression") {
    val booleanExpression: BooleanExpression = True
    assert(ExpressionEvaluation.evaluateBooleanExpression(booleanExpression) == True)
  }

  test("testEvaluateBooleanExpression2") {
    val booleanExpression: BooleanExpression = Not(Or(Not(Variable("a")), False))
    assert(ExpressionEvaluation.evaluateBooleanExpression(booleanExpression) == Variable("a"))
  }

  test("testEvaluateBooleanExpression3") {
    val booleanExpression: BooleanExpression = Not(And(Not(Variable("a")), True))
    assert(ExpressionEvaluation.evaluateBooleanExpression(booleanExpression) == Variable("a"))
  }

}
