import org.json4s._
import org.json4s.jackson.JsonMethods._
import org.scalatest.FunSuite

class JsonToStringTest extends FunSuite {

  test("testJsonToString") {
    val s = JObject(
      JField("Not",
        JObject(
          JField(
            "Not",
            JString("True")
          )
        )
      )
    )
    println(Parser.jsonToString(s))

  }

}
