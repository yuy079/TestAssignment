import org.json4s._
import org.json4s.jackson.JsonMethods._
import org.scalatest.FunSuite

class JsonToBooleanExpressionTest extends FunSuite {

  test("testJsonToBooleanExpression") {
    val s = Parser.booleanExpressionToJson(Not(And(True, Or(Variable("a"), Variable("b")))))
    print(Parser.jsonToString(s))

    println(Parser.jsonToBooleanExpression(s))
  }

}
