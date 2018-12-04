import org.json4s._

import org.scalatest.FunSuite

class JsonToBooleanExpressionTest extends FunSuite {

  test("Simple boolean"){
    val json1 = JObject(List(("Boolean",JBool(true))))
    val json2 =  JObject(List(("Boolean",JBool(false))))

    assert(Parser.jsonToBooleanExpression(json1) == True)
    assert(Parser.jsonToBooleanExpression(json2) == False)
  }

  test("Simple variable"){
    val json = JObject(List(("Variable", JString("a"))))
    assert(Parser.jsonToBooleanExpression(json) == Variable("a"))
  }

  test("Simple NOT"){
    val json = JArray(List(JString("NOT"), JObject(List(("Variable",JString("a"))))))
    assert(Parser.jsonToBooleanExpression(json) == Not(Variable("a")))
  }

  test("Simple AND"){
    val json = JArray(List(JString("AND"),JObject(List(("Boolean",JBool(true)))),
      JObject(List(("Boolean",JBool(false))))))
    assert(Parser.jsonToBooleanExpression(json) == And(True, False))
  }

  test("Simple OR"){
    val json = JArray(List(JString("OR"), JObject(List(("Boolean",JBool(true)))),
      JObject(List(("Variable",JString("a"))))))
    assert(Parser.jsonToBooleanExpression(json) == Or(True, Variable("a")))
  }

  test("Complicated Tests") {

    val json1 = JArray(List(JString("NOT"), JArray(List(JString("NOT"),
      JArray(List(JString("NOT"), JObject(List(("Boolean",JBool(true))))))))))
    val json2 = JArray(List(JString("AND"), JArray(List(JString("AND"),
      JObject(List(("Boolean",JBool(true)))), JObject(List(("Boolean",JBool(false)))))),
      JArray(List(JString("AND"), JObject(List(("Boolean",JBool(false)))),
      JArray(List(JString("AND"), JObject(List(("Boolean",JBool(true)))),
      JObject(List(("Boolean",JBool(true))))))))))
    val json3 = JArray(List(JString("OR"), JArray(List(JString("OR"),
      JObject(List(("Boolean",JBool(true)))), JObject(List(("Boolean",JBool(false)))))),
      JArray(List(JString("OR"), JObject(List(("Boolean",JBool(false)))),
      JArray(List(JString("OR"), JObject(List(("Boolean",JBool(true)))),
      JObject(List(("Boolean",JBool(true))))))))))
    val json4 = JArray(List(JString("AND"), JArray(List(JString("OR"),
      JObject(List(("Boolean",JBool(true)))), JObject(List(("Boolean",JBool(false)))))),
      JArray(List(JString("OR"), JObject(List(("Boolean",JBool(false)))),
      JArray(List(JString("AND"), JObject(List(("Boolean",JBool(true)))),
      JObject(List(("Boolean",JBool(true))))))))))
    val json5 = JArray(List(JString("NOT"), JArray(List(JString("AND"),
      JArray(List(JString("OR"), JObject(List(("Boolean",JBool(true)))),
      JObject(List(("Boolean",JBool(false)))))), JArray(List(JString("OR"),
      JObject(List(("Boolean",JBool(false)))), JArray(List(JString("AND"),
      JObject(List(("Boolean",JBool(true)))), JObject(List(("Variable",JString("a"))))))))))))
    val json6 = JArray(List(JString("AND"), JArray(List(JString("OR"),
      JObject(List(("Variable",JString("a")))), JObject(List(("Variable",JString("b")))))),
      JArray(List(JString("OR"), JArray(List(JString("NOT"),
      JObject(List(("Variable",JString("a")))))), JObject(List(("Variable",JString("c"))))))))
    assert(Parser.jsonToBooleanExpression(json1) == Not(Not(Not(True))))
    assert(Parser.jsonToBooleanExpression(json2) == And(And(True, False), And(False, And(True, True))))
    assert(Parser.jsonToBooleanExpression(json3) == Or(Or(True, False), Or(False, Or(True, True))))
    assert(Parser.jsonToBooleanExpression(json4) == And(Or(True, False), Or(False, And(True, True))))
    assert(Parser.jsonToBooleanExpression(json5) == Not(And(Or(True, False), Or(False, And(True, Variable("a"))))))
    assert(Parser.jsonToBooleanExpression(json6) == And(Or(Variable("a"), Variable("b")), Or(Not(Variable("a")), Variable("c"))))
  }

}
