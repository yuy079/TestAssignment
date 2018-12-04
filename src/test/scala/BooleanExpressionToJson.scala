import org.scalatest.FunSuite

class BooleanExpressionToJson extends FunSuite {

  test("testBooleanExpressionToJson") {
    val e = Not(Not(Not(True)))
    println(Parser.booleanExpressionToJson(e))
    println(Parser.jsonToString(Parser.booleanExpressionToJson(e)))
  }

}
