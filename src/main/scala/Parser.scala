import org.json4s._
import org.json4s.jackson.JsonMethods._

object Parser {
  def booleanExpressionToJson(booleanExpression: BooleanExpression):JValue = {
    booleanExpression match {
      case True => JString("True")
      case False => JString("False")
      case Variable(symbol) => JString(symbol)
      case Not(e) => JObject(JField("Not", booleanExpressionToJson(e)))
      case And(e1, e2) => JObject(JField("And", JArray(List(booleanExpressionToJson(e1), booleanExpressionToJson(e2)))))
      case Or(e1, e2) => JObject(JField("Or", JArray(List(booleanExpressionToJson(e1), booleanExpressionToJson(e2)))))
    }
  }
  def jsonToBooleanExpression(JValue: JValue):BooleanExpression = {
    JValue match {
      case JString("True") => True
      case JString("False") => False
      case JString(symbol) => Variable(symbol)
      case JObject(List(JField("Not", e))) => Not(jsonToBooleanExpression(e))
      case JObject(List(JField("And", JArray(List(e1, e2))))) => And(jsonToBooleanExpression(e1), jsonToBooleanExpression(e2))
      case JObject(List(JField("Or", JArray(List(e1, e2))))) => Or(jsonToBooleanExpression(e1), jsonToBooleanExpression(e2))

    }
  }
  def jsonToString(JObject: JValue): String = {
  pretty(render(JObject))
}
  def stringToJson(string:String):JValue ={
    try {
      parse(string).asInstanceOf[JObject]
    }catch {
      case e: Exception => throw ParseException("Error on JSON parser. " + e )
    }
  }

  case class ParseException(str: String) extends Exception(str)


}
