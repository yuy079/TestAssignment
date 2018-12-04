import org.json4s._
import org.json4s.jackson.JsonMethods._
import org.scalatest.FunSuite

class stringToJsonTest extends FunSuite {

  test("Simple boolean") {
    //True
    val json = JObject(JField("Boolean", JBool(true)))
    val str = pretty(render(json))
    assert(Parser.stringToJson(str)== json)

  }
  test("Simple variable") {
    //Variable("a")
    val json = JObject(List(JField("Variable", JString("a"))))
    val str = pretty(render(json))
    assert(Parser.stringToJson(str)== json)
  }

}
