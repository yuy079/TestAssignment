import org.scalatest.FunSuite

class ExpressionEvaluationTest extends FunSuite {

  test("No simplifications") {
    val booleanExpression1: BooleanExpression = True
    val booleanExpression2: BooleanExpression = False
    val booleanExpression3: BooleanExpression = Variable("a")
    val booleanExpression4: BooleanExpression = Not(Variable("a"))
    val booleanExpression5: BooleanExpression = And(Variable("a"), Variable("b"))
    val booleanExpression6: BooleanExpression = Or(Variable("a"), Variable("b"))
    assert(ExpressionEvaluation.evaluateBooleanExpression(booleanExpression1) == True)
    assert(ExpressionEvaluation.evaluateBooleanExpression(booleanExpression2) == False)
    assert(ExpressionEvaluation.evaluateBooleanExpression(booleanExpression3) == Variable("a"))
    assert(ExpressionEvaluation.evaluateBooleanExpression(booleanExpression4) == Not(Variable("a")))
    assert(ExpressionEvaluation.evaluateBooleanExpression(booleanExpression5) == And(Variable("a"), Variable("b")))
    assert(ExpressionEvaluation.evaluateBooleanExpression(booleanExpression6) == Or(Variable("a"), Variable("b")))
  }

  test("One step simplifications") {
    val booleanExpression1: BooleanExpression = Not(Not(Variable("a")))
    val booleanExpression2: BooleanExpression = And(Variable("a"), Variable("a"))
    val booleanExpression3: BooleanExpression = And(Variable("a"),True)
    val booleanExpression4: BooleanExpression = And(Variable("a"),False)
    val booleanExpression5: BooleanExpression = Or(Variable("a"), Variable("a"))
    val booleanExpression6: BooleanExpression = Or(Variable("a"), True)
    val booleanExpression7: BooleanExpression = Or(False, Variable("a"))
    val booleanExpression8: BooleanExpression = Not(True)
    val booleanExpression9: BooleanExpression = Not(False)
    val booleanExpression10: BooleanExpression = And(True, True)
    val booleanExpression11: BooleanExpression = And(True, False)
    val booleanExpression12: BooleanExpression = Or(False, True)
    val booleanExpression13: BooleanExpression = Or(False, False)

    assert(ExpressionEvaluation.evaluateBooleanExpression(booleanExpression1) == Variable("a"))
    assert(ExpressionEvaluation.evaluateBooleanExpression(booleanExpression2) == Variable("a"))
    assert(ExpressionEvaluation.evaluateBooleanExpression(booleanExpression3) == Variable("a"))
    assert(ExpressionEvaluation.evaluateBooleanExpression(booleanExpression4) == False)
    assert(ExpressionEvaluation.evaluateBooleanExpression(booleanExpression5) == Variable("a"))
    assert(ExpressionEvaluation.evaluateBooleanExpression(booleanExpression6) == True)
    assert(ExpressionEvaluation.evaluateBooleanExpression(booleanExpression7) == Variable("a"))
    assert(ExpressionEvaluation.evaluateBooleanExpression(booleanExpression8) == False)
    assert(ExpressionEvaluation.evaluateBooleanExpression(booleanExpression9) == True)
    assert(ExpressionEvaluation.evaluateBooleanExpression(booleanExpression10) == True)
    assert(ExpressionEvaluation.evaluateBooleanExpression(booleanExpression11) == False)
    assert(ExpressionEvaluation.evaluateBooleanExpression(booleanExpression12) == True)
    assert(ExpressionEvaluation.evaluateBooleanExpression(booleanExpression13) == False)
  }

  test("Complicated simplifications") {
    val booleanExpression1: BooleanExpression = Not(Not(Not(True)))
    val booleanExpression2: BooleanExpression = And(Variable("a"), And(True, True))
    val booleanExpression3: BooleanExpression = And(And(False,True),True)
    val booleanExpression4: BooleanExpression = And(Variable("a"),Not(Not(Variable("a"))))
    val booleanExpression5: BooleanExpression = Or(Variable("a"), Or(False, False))
    val booleanExpression6: BooleanExpression = Or(Or(False,True),True)
    val booleanExpression7: BooleanExpression = Or(Variable("a"),Not(Not(Variable("a"))))
    val booleanExpression8: BooleanExpression = Not(And(Variable("a"), True))
    val booleanExpression9: BooleanExpression = Not(Or(Variable("a"), False))

    assert(ExpressionEvaluation.evaluateBooleanExpression(booleanExpression1) == False)
    assert(ExpressionEvaluation.evaluateBooleanExpression(booleanExpression2) == Variable("a"))
    assert(ExpressionEvaluation.evaluateBooleanExpression(booleanExpression3) == False)
    assert(ExpressionEvaluation.evaluateBooleanExpression(booleanExpression4) == Variable("a"))
    assert(ExpressionEvaluation.evaluateBooleanExpression(booleanExpression5) == Variable("a"))
    assert(ExpressionEvaluation.evaluateBooleanExpression(booleanExpression6) == True)
    assert(ExpressionEvaluation.evaluateBooleanExpression(booleanExpression7) == Variable("a"))
    assert(ExpressionEvaluation.evaluateBooleanExpression(booleanExpression8) == Not(Variable("a")))
    assert(ExpressionEvaluation.evaluateBooleanExpression(booleanExpression9) == Not(Variable("a")))

  }
}
