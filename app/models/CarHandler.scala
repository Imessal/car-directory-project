package models

object  CarHandler {
  case class Car(number: String, brand: String, model: String, color: String, year: String)

  case class CarSet(cars: List[Car]) {
    def add(car: Car): CarSet = {
      CarSet(car :: cars)
    }

//    def delete()

    def get(number:String) : Option[Car] = cars.find(_.number == number)
  }
}

