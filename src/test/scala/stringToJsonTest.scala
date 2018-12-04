import org.scalatest.FunSuite

class stringToJsonTest extends FunSuite {

  test("testStringToJson") {
    val s = "{\n  \"not\" : {\n    \"Not\" : \"True\"\n  }\n}"
    println(Parser.stringToJson(s))
  }

}
