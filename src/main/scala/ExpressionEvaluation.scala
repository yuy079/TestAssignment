object ExpressionEvaluation {
  def evaluateBooleanExpression(booleanExpression: BooleanExpression): BooleanExpression ={
    booleanExpression match {
      case True => True
      case False => False
      case Variable(symbol) => Variable(symbol)
      case Not(e) =>
        val e0 = evaluateBooleanExpression(e)
        e0 match{
          case True => False
          case False => True
          case Variable(symbol) => Not(Variable(symbol))
          case Not(e1) => evaluateBooleanExpression(e1)
          case Or(e1, e2) => Or(Not(e1), Not(e2))
          case And(e1, e2) => And(Not(e1), Not(e2))
        }
      case Or(e1, e2) =>
        val e3 = evaluateBooleanExpression(e1)
        val e4 = evaluateBooleanExpression(e2)

        if (e3 == True || e4 == True) True
        else if (e3 == False) e4
        else if (e4 == False) e3
        else if (e3 == e4) e3
        else  Or(e3, e4)
      case And(e1, e2) =>
        val e3 = evaluateBooleanExpression(e1)
        val e4 = evaluateBooleanExpression(e2)

        if (e3 == False || e4 == False) False
        else if (e3 == True) e4
        else if (e4 == True) e3
        else if (e3 == e4) e3
        else  And(e3, e4)
    }
  }
}
