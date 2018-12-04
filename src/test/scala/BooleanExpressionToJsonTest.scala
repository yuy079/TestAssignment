import org.json4s._
import org.scalatest.FunSuite

class BooleanExpressionToJsonTest extends FunSuite {

  test("Simple boolean"){
    val e1 = True
    val e2 = False
    assert(Parser.booleanExpressionToJson(e1) == JObject(List(("Boolean",JBool(true)))))
    assert(Parser.booleanExpressionToJson(e2) == JObject(List(("Boolean",JBool(false)))))
  }

  test("Simple variable"){
    val e = Variable("a")
    assert(Parser.booleanExpressionToJson(e) == JObject(List(("Variable", JString("a")))))

  }
  test("Simple NOT"){
    val e = Not(Variable("a"))
    assert(Parser.booleanExpressionToJson(e) == JArray(List(JString("NOT"), JObject(List(("Variable",JString("a")))))))
  }
  test("Simple AND"){
    val e = And(True, False)
    assert(Parser.booleanExpressionToJson(e) == JArray(List(JString("AND"),
      JObject(List(("Boolean",JBool(true)))), JObject(List(("Boolean",JBool(false)))))))
  }
  test("Simple OR"){
    val e = Or(True, Variable("a"))
    assert(Parser.booleanExpressionToJson(e) == JArray(List(JString("OR"),
      JObject(List(("Boolean",JBool(true)))), JObject(List(("Variable",JString("a")))))))
  }
  test("Complicated Tests") {
    val e1 = Not(Not(Not(True)))
    val e2 = And(And(True, False), And(False, And(True, True)))
    val e3 = Or(Or(True, False), Or(False, Or(True, True)))
    val e4 = And(Or(True, False), Or(False, And(True, True)))
    val e5 = Not(And(Or(True, False), Or(False, And(True, Variable("a")))))
    val e6 = And(Or(Variable("a"), Variable("b")), Or(Not(Variable("a")), Variable("c")))

    assert(Parser.booleanExpressionToJson(e1) == JArray(List(JString("NOT"),
      JArray(List(JString("NOT"), JArray(List(JString("NOT"),
      JObject(List(("Boolean",JBool(true)))))))))))
    assert(Parser.booleanExpressionToJson(e2) == JArray(List(JString("AND"),
      JArray(List(JString("AND"), JObject(List(("Boolean",JBool(true)))),
      JObject(List(("Boolean",JBool(false)))))), JArray(List(JString("AND"),
      JObject(List(("Boolean",JBool(false)))), JArray(List(JString("AND"),
      JObject(List(("Boolean",JBool(true)))), JObject(List(("Boolean",JBool(true)))))))))))
    assert(Parser.booleanExpressionToJson(e3) == JArray(List(JString("OR"),
      JArray(List(JString("OR"), JObject(List(("Boolean",JBool(true)))),
      JObject(List(("Boolean",JBool(false)))))), JArray(List(JString("OR"),
      JObject(List(("Boolean",JBool(false)))), JArray(List(JString("OR"),
      JObject(List(("Boolean",JBool(true)))), JObject(List(("Boolean",JBool(true))))))))))
    )
    assert(Parser.booleanExpressionToJson(e4) == JArray(List(JString("AND"),
      JArray(List(JString("OR"), JObject(List(("Boolean",JBool(true)))),
      JObject(List(("Boolean",JBool(false)))))), JArray(List(JString("OR"),
      JObject(List(("Boolean",JBool(false)))), JArray(List(JString("AND"),
      JObject(List(("Boolean",JBool(true)))), JObject(List(("Boolean",JBool(true))))))))))
    )
    assert(Parser.booleanExpressionToJson(e5) == JArray(List(JString("NOT"),
      JArray(List(JString("AND"), JArray(List(JString("OR"),
      JObject(List(("Boolean",JBool(true)))), JObject(List(("Boolean",JBool(false)))))),
      JArray(List(JString("OR"), JObject(List(("Boolean",JBool(false)))),
      JArray(List(JString("AND"), JObject(List(("Boolean",JBool(true)))),
      JObject(List(("Variable",JString("a"))))))))))))
    )
    assert(Parser.booleanExpressionToJson(e6) == JArray(List(JString("AND"),
      JArray(List(JString("OR"), JObject(List(("Variable",JString("a")))),
      JObject(List(("Variable",JString("b")))))), JArray(List(JString("OR"),
      JArray(List(JString("NOT"), JObject(List(("Variable",JString("a")))))),
      JObject(List(("Variable",JString("c"))))))))
    )
  }

}
