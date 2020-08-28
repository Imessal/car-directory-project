package models
import models.CarHandler.{Number, Car}
import org.scalatest.FunSuite

class CarHandlerSpec extends FunSuite {
  val n1 = Number("AAA", "001", 96)
  val n2 = Number("AAA", "001", 96)
  val n3 = Number("AAB", "002", 98)

  val car1 = Car(0, n1.toString, "Bentley", "Continental", "Black", 200, 1, 2015, "now")
  val car2 = Car(0, n2.toString, "Bentley", "Continental", "Black", 200, 1, 2015, "now")
  val car3 = Car(1, n3.toString, "Volkswagen", "Tiguan", "White", 200, 2, 2014, "later")
  val car4 = Car(2, Number("ABC", "322", 99).toString, "Renault", "Logan", "Silver", 150, 3, 2005, "sometime")


  test("toStringEquality") {
    assert(n1.toString == "A001AA96")
    assert(n3.toString == "A002AB98")
  }

  test("numbersEquality") {
    assert(n1 == n2)
    assert(n1 != n3)
    assert(n2 != n3)
  }

  test("carEquality") {
    assert(car1 == car2)
    assert(car1 != car3)
    assert(car2 != car4)
  }
}
