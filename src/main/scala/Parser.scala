import org.json4s._
import org.json4s.jackson.JsonMethods._

object Parser {
  def booleanExpressionToJson(booleanExpression: BooleanExpression):JValue = {
    booleanExpression match {
      case True => JObject(List(("Boolean", JBool(true))))
      case False => JObject(List(("Boolean", JBool(false))))
      case Variable(symbol) => JObject(List(("Variable", JString(symbol))))
//      case Not(e) => JObject(List(("Not", booleanExpressionToJson(e))))
//      case And(e1, e2) => JObject(List(("And", JArray(List(booleanExpressionToJson(e1), booleanExpressionToJson(e2))))))
//      case Or(e1, e2) => JObject(List(("Or", JArray(List(booleanExpressionToJson(e1), booleanExpressionToJson(e2))))))
      case Not(e) => JArray(List(JString("NOT"), booleanExpressionToJson(e)))
      case And(e1, e2) => JArray(List(JString("AND"), booleanExpressionToJson(e1), booleanExpressionToJson(e2)))
      case Or(e1, e2) => JArray(List(JString("OR"), booleanExpressionToJson(e1), booleanExpressionToJson(e2)))


    }
  }
  def jsonToBooleanExpression(JValue: JValue):BooleanExpression = {
    JValue match {
      case JObject(List(("Boolean", JBool(true)))) => True
      case JObject(List(("Boolean", JBool(false)))) => False
      case JObject(List(("Variable", JString(symbol))))=> Variable(symbol)
      case JArray(List(JString("NOT"), e)) => Not(jsonToBooleanExpression(e))
      case JArray(List(JString("AND"),e1, e2)) => And(jsonToBooleanExpression(e1), jsonToBooleanExpression(e2))
      case JArray(List(JString("OR"), e1, e2)) => Or(jsonToBooleanExpression(e1), jsonToBooleanExpression(e2))
      case _ => throw ParseException("Not a valid JSON format")
    }
  }
  def jsonToString(JObject: JValue): String = {
    try {
      pretty(render(JObject))
    }catch {
      case e: Exception => throw ParseException("Error parsing JSON to string : " + e)
    }
  }
  def stringToJson(string:String):JValue ={
    try {
      parse(string).asInstanceOf[JObject]
    }catch {
      case e: Exception => throw ParseException("Error parsing string to JSON : " + e )
    }
  }

  case class ParseException(str: String) extends Exception(str)


}
