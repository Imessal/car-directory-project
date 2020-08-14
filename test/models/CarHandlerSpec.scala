package models
import models.CarHandler.{Number, Car, CarSet}
import org.scalatest.FunSuite

class CarHandlerSpec extends FunSuite {
  val n1 = Number("AAA", "001", 96)
  val n2 = Number("AAA", "001", 96)
  val n3 = Number("AAB", "002", 98)

  val car1 = Car(n1, "Bentley", "Continental", "Black", 2002)
  val car2 = Car(n2, "Bentley", "Continental", "Black", 2002)
  val car3 = Car(n3, "Volkswagen", "Tiguan", "White", 2014)
  val car4 = Car(Number("ABC", "322", 99), "Renault", "Logan", "Silver", 2005)

  val set1 = CarSet(List(car1, car3, car4))
  val set2 = CarSet(List(car1, car3))

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

  test("carSetEquality") {
    assert(set1 == set2.add(car4))
    assert(set1.listAll() == set2.add(car4).listAll())
    assert(set1.delete(n1.toString) == CarSet(List(car3, car4)))
  }
}
